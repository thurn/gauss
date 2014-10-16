package com.tinlib.beget;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class EntityReader {
  public static class ReadResult {
    private final Map<String, EntityType> entityTypes;
    private final List<EntityDescription> entityDescriptions;

    public ReadResult(Map<String, EntityType> entityTypes,
        List<EntityDescription> entityDescriptions) {
      this.entityTypes = entityTypes;
      this.entityDescriptions = entityDescriptions;
    }

    public Map<String, EntityType> getEntityTypes() {
      return entityTypes;
    }

    public List<EntityDescription> getEntityDescriptions() {
      return entityDescriptions;
    }
  }

  private final Map<String, EntityType> entityTypes = Maps.newHashMap();
  private final Map<String, EntityDescription> descriptions = Maps.newHashMap();

  public ReadResult read(List<String> files) throws JSONException, IOException {
    Map<String, EntityType> entityTypes = Maps.newHashMap();
    List<EntityDescription> descriptions = Lists.newArrayList();
    for (String arg : files) {
      File file = new File(arg);
      String json = Files.toString(file, Charsets.UTF_8);
      JSONArray array = new JSONArray(json);
      for (int i = 0; i < array.length(); ++i) {
        EntityDescription description =
            new EntityDescription(array.getJSONObject(i), file.getParentFile());
        descriptions.add(description);
        entityTypes.put(description.fullyQualifiedName(), description.getType());
      }
    }
    return new ReadResult(entityTypes, descriptions);
  }

  private void readJsonObject(JSONObject entity, File parentFile) throws JSONException {
    String type = entity.getString("type");
    String name = entity.getString("name");
    String packageString = entity.getString("package");
    String fullyQualifiedName = packageString + "." + name;
    String description = entity.optString("desc");
    switch (type) {
      case "entity":
        mergeEntityDescription(new EntityDescription(EntityType.ENTITY, name, packageString,
            description, parentFile), true /* isCanonical */);
        break;
      case "enum":
        break;
      case "entity_extension":
        mergeEntityDescription(new EntityDescription(EntityType.ENTITY, name, packageString,
            null, null), false /* isCanonical */);
        break;
      case "enum_extension":
        break;
      default:
        throw new IllegalArgumentException("Unknown entity type " + type);
    }
  }

  private void mergeEntityDescription(EntityDescription toAdd, boolean isCanonical) {
    if (descriptions.containsKey(toAdd.fullyQualifiedName())) {
      EntityDescription current = descriptions.get(toAdd.fullyQualifiedName());
      EntityDescription merged = new EntityDescription(toAdd.getType(),
          toAdd.getName(),
          toAdd.getPackageString(),
          isCanonical ? toAdd.getDescription() : null,
          isCanonical ? toAdd.getParent() : null);
      for (FieldDescription fieldDescription : toAdd.getFields()) {
        merged.addField(fieldDescription);
      }
      for (FieldDescription fieldDescription : current.getFields()) {
        merged.addField(fieldDescription);
      }
      descriptions.put(toAdd.fullyQualifiedName(), merged);
    } else {
      descriptions.put(toAdd.fullyQualifiedName(), toAdd);
    }
  }

  private List<FieldDescription> readFields(JSONObject entity) {
    return null;
  }

  private EntityDescription getCanonicalEntity(Collection<EntityDescription> descriptions) {
    // TODO this
    return descriptions.iterator().next();
  }
}
