package com.tinlib.beget;

import com.squareup.javawriter.JavaWriter;
import org.json.JSONException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class EntityGenerator {
  public static void main(String[] args) throws IOException, JSONException {
    if (args.length == 0) {
      System.out.println("Usage: EntityGenerator entities1.json entities2.json");
      return;
    }

    EntityReader.ReadResult readResult = new EntityReader().read(Arrays.asList(args));
    EntityWriter writer = new EntityWriter(readResult.getEntityTypes());
    for (EntityDescription entityDescription : readResult.getEntityDescriptions()) {
      File output = new File(entityDescription.getParent(), entityDescription.getName() + ".java");
      writer.writeEntityDescription(new JavaWriter(new FileWriter(output)), entityDescription);
    }
  }
}
