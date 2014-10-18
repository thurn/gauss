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

  private static class ObjectWithFile {
    private final JSONObject object;
    private final File file;

    private ObjectWithFile(JSONObject object, File file) {
      this.object = object;
      this.file = file;
    }
  }

  private final List<ObjectWithFile> jsonObjects = Lists.newArrayList();
  private final List<JSONObject> jsonExtensions = Lists.newArrayList();
  private final Map<String, EntityType> entityTypes = Maps.newHashMap();
  private final Map<String, EntityDescription> descriptions = Maps.newHashMap();

  public ReadResult read(List<String> inputFiles) throws JSONException, IOException {
    Map<String, EntityType> entityTypes = Maps.newHashMap();
    List<EntityDescription> descriptions = Lists.newArrayList();

    for (String arg : inputFiles) {
      File file = new File(arg);
      String json = Files.toString(file, Charsets.UTF_8);
      JSONArray array = new JSONArray(json);
      for (int i = 0; i < array.length(); ++i) {
        readObject(file, array.getJSONObject(i));
      }
    }

    loadJsonObjects();
    loadJsonExtensions();

    return new ReadResult(entityTypes, descriptions);
  }

  private void readObject(File file, JSONObject object) throws JSONException {
    switch (object.getString("type")) {
      case "entity":
      case "enum":
        jsonObjects.add(new ObjectWithFile(object, file.getParentFile()));
        break;
      case "entity_extension":
      case "enum_extension":
        jsonExtensions.add(object);
        break;
      default:
        throw new IllegalArgumentException("Unknown input type " + object.getString("type"));
    }
  }

  private void loadJsonObjects() throws JSONException {
    for (ObjectWithFile objectWithFile : jsonObjects) {
      JSONObject object = objectWithFile.object;
      EntityType type = object.getString("type").equals("entity") ?
          EntityType.ENTITY : EntityType.ENUM;
      String name = object.getString("name");
      String packageString = object.getString("package");
      String description = object.optString("desc");
      String fullyQualifiedName = packageString + "." + name;
      EntityDescription entityDescription = new EntityDescription(type, name, packageString,
          description, objectWithFile.file);
      if (type == EntityType.ENTITY) {
        addFields(entityDescription, object);
      }
      if (type == EntityType.ENUM) {
        addEnumValues(entityDescription, object);
      }
      descriptions.put(fullyQualifiedName, entityDescription);
    }
  }

  private void loadJsonExtensions() throws JSONException {
    for (JSONObject object : jsonExtensions) {
      String name = object.getString("name");
      String packageString = object.getString("package");
      String type = object.getString("type");
      String fullyQualifiedName = packageString + "." + name;
      if (!descriptions.containsKey(fullyQualifiedName)) {
        throw new IllegalArgumentException("Unable to find base implementation for extension " +
            fullyQualifiedName);
      }
      EntityDescription entityDescription = descriptions.get(fullyQualifiedName);
      if (type.equals("entity_extension")) {
        addFields(entityDescription, object);
      }
      if (type.equals("enum_extension")) {
        addEnumValues(entityDescription, object);
      }
    }
  }

  private void addFields(EntityDescription description, JSONObject entity) throws JSONException {
  }

  private void addEnumValues(EntityDescription description, JSONObject entity)
      throws JSONException {
  }
}
