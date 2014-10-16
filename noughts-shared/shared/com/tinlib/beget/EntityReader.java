package com.tinlib.beget;

import com.google.common.base.Charsets;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import com.squareup.javawriter.JavaWriter;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileWriter;
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

  private EntityDescription getCanonicalEntity(Collection<EntityDescription> descriptions) {
    // TODO this
    return descriptions.iterator().next();
  }
}
