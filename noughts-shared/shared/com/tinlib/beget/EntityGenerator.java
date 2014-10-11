package com.tinlib.beget;

import com.google.common.base.Charsets;
import com.google.common.collect.*;
import com.google.common.io.Files;
import com.squareup.javawriter.JavaWriter;
import org.json.JSONArray;
import org.json.JSONException;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;

public class EntityGenerator {
  public Map<String, EntityType> entityTypes = Maps.newHashMap();

  public static void main(String[] args) throws IOException, JSONException {
    EntityGenerator entityGenerator = new EntityGenerator();
    entityGenerator.run(args);
  }

  public void run(String[] args) throws IOException, JSONException {
    if (args.length == 0) {
      System.out.println("Usage: EntityGenerator entities1.json entities2.json");
    }

    Multimap<String, EntityDescription> descriptions = HashMultimap.create();
    for (String arg : args) {
      File file = new File(arg);
      String json = Files.toString(file, Charsets.UTF_8);
      JSONArray array = new JSONArray(json);
      for (int i = 0; i < array.length(); ++i) {
        EntityDescription description = new EntityDescription(array.getJSONObject(i),
            file.getParentFile());
        descriptions.put(description.getName(), description);
        entityTypes.put(description.fullyQualifiedName(), description.getType());
      }
    }

    EntityWriter writer = new EntityWriter(entityTypes);
    for (String key : descriptions.keySet()) {
      EntityDescription canonical = getCanonicalEntity(descriptions.get(key));
      File output = new File(canonical.getParent(), key + ".java");
      writer.writeEntityDescriptions(new JavaWriter(new FileWriter(output)), descriptions.get(key));
    }
  }

  private EntityDescription getCanonicalEntity(Collection<EntityDescription> descriptions) {
    // TODO this
    return descriptions.iterator().next();
  }
}
