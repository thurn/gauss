package com.tinlib.beget;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.squareup.javawriter.JavaWriter;
import org.json.JSONException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class EntityGenerator {
  public static void main(String[] args) throws IOException, JSONException {
    if (args.length == 0) {
      System.out.println("Usage: EntityGenerator entities1.json entities2.json");
      return;
    }

    List<String> jsonStrings = Lists.newArrayList();
    List<File> parentDirectories = Lists.newArrayList();
    for (String arg : args) {
      File file = new File(arg);
      String json = Files.toString(file, Charsets.UTF_8);
      jsonStrings.add(json);
      parentDirectories.add(file.getParentFile());
    }

    EntityReader.ReadResult readResult = new EntityReader().read(jsonStrings, parentDirectories);

    EntityWriter writer = new EntityWriter(readResult.getEntityTypes());
    for (EntityInfo entityInfo : readResult.getEntityInformation()) {
      File output = new File(entityInfo.getParent(), entityInfo.getName() + ".java");
      writer.writeEntityDescription(new JavaWriter(new FileWriter(output)), entityInfo);
    }
  }
}
