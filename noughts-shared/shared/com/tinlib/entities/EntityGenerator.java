package com.tinlib.entities;

import com.google.common.base.Charsets;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import com.squareup.javawriter.JavaWriter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

public class EntityGenerator {

  public static final EnumSet<Modifier> PUBLIC = EnumSet.of(Modifier.PUBLIC);
  public static final EnumSet<Modifier> PRIVATE = EnumSet.of(Modifier.PRIVATE);

  private static class FieldDescription {
    private final String name;
    private final String type;
    private final boolean repeated;
    private final String description;

    public FieldDescription(JSONObject object) throws JSONException {
      name = object.getString("name");
      type = object.getString("type");
      repeated = object.optBoolean("repeated");
      description = object.optString("desc");
    }
  }

  private static class EnumValueDescription {
    private final String name;
    private final String description;

    public EnumValueDescription(JSONObject object) throws JSONException {
      name = object.getString("name");
      description = object.optString("desc");
    }
  }

  private static class EntityDescription {
    private final String type;
    private final String name;
    private final String packageString;
    private final String description;
    private final List<FieldDescription> fields = Lists.newArrayList();
    private final List<EnumValueDescription> values = Lists.newArrayList();
    private final File parent;

    public EntityDescription(JSONObject object, File parent) throws JSONException {
      type = object.getString("type");
      name = object.getString("name");
      packageString = object.getString("package");
      description = object.optString("desc");
      switch (type) {
        case "entity":
        case "extension":
          for (int i = 0; i < object.getJSONArray("fields").length(); ++i) {
            fields.add(new FieldDescription(object.getJSONArray("fields").getJSONObject(i)));
          }
          break;
        case "enum":
          for (int i = 0; i < object.getJSONArray("values").length(); ++i) {
            values.add(new EnumValueDescription(object.getJSONArray("values").getJSONObject(i)));
          }
          break;
        default:
          throw new RuntimeException("Unknown entity type " + type);
      }
      this.parent = parent;
    }
  }

  public static void main(String[] args) throws IOException, JSONException {
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
        descriptions.put(description.name, description);
      }
    }

    for (String key : descriptions.keySet()) {
      EntityDescription canonical = getCanonicalEntity(descriptions.get(key));
      File output = new File(canonical.parent, key + ".java");
      writeEntityDescriptions(new JavaWriter(new FileWriter(output)), descriptions.get(key));
    }
  }

  private static EntityDescription getCanonicalEntity(Collection<EntityDescription> descriptions) {
    EntityDescription result = null;
    for (EntityDescription description : descriptions) {
      if (!description.type.equals("extension")) {
        if (result != null) {
          throw new RuntimeException("Multiple canonical entities for entity " + result.name);
        }
        result = description;
      }
    }
    if (result == null) {
      throw new RuntimeException("No canonical entity for entity "
          + descriptions.iterator().next().name);
    }
    return result;
  }

  private static void writeEntityDescriptions(JavaWriter writer,
      Collection<EntityDescription> descriptions) throws IOException {
    EntityDescription canonical = getCanonicalEntity(descriptions);
    switch (canonical.type) {
      case "entity":
        writeEntity(writer, canonical);
        break;
      case "enum":
        writeEnum(writer, canonical);
        break;
      default:
        throw new RuntimeException("Unknown entity type: " + canonical.type);
    }
  }

  private static void writeEntity(JavaWriter writer, EntityDescription description)
      throws IOException {
    writer.emitSingleLineComment("================================");
    writer.emitSingleLineComment("GENERATED CODE -- DO NOT MODIFY!");
    writer.emitSingleLineComment("================================");
    writer.emitEmptyLine();
    writer.emitPackage(description.packageString);
    writer.emitImports(ImmutableList.of("java.util.*"));
    writer.emitEmptyLine();
    writer.emitImports(ImmutableList.of("com.tinlib.entities.Entity"));
    writer.emitEmptyLine();
    writer.beginType(description.name, "class", EnumSet.of(Modifier.PUBLIC, Modifier.FINAL),
        "Entity<" + description.name + ">");
    writeDeserializer(writer, description.name);
    writeBuilder(writer, description);
    writeCommonStaticMethods(writer, description);
    writeFields(writer, description);
    writeConstructors(writer, description);
    writeCommonMethods(writer, description);
    writeFieldMethods(writer, description);
    writer.endType();
    writer.close();
  }

  private static void writeDeserializer(JavaWriter writer, String name) throws IOException {
    String paramName = decapitalize(name) + "Map";
    writer.beginType("Deserializer", "class",
        EnumSet.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL),
        "EntityDeserializer<" + name + ">");
    writer.beginConstructor(PRIVATE);
    writer.endConstructor();
    writer.emitEmptyLine();
    writer.emitAnnotation("Override");
    writer.beginMethod(name, "deserialize", PUBLIC, "Map<String, Object>",
        paramName);
    writer.emitStatement("return new %s(%s)", name, paramName);
    writer.endMethod();
    writer.endType();
    writer.emitEmptyLine();
  }

  private static void writeBuilder(JavaWriter writer, EntityDescription description)
      throws IOException {
    String name = description.name;
    String paramName = decapitalize(name);
    writer.beginType("Builder", "class",
        EnumSet.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL),
        "EntityBuilder<" + name + ">");
    writer.emitField(name, paramName, EnumSet.of(Modifier.PRIVATE, Modifier.FINAL));
    writer.emitEmptyLine();
    writer.beginConstructor(PRIVATE);
    writer.emitStatement("this.%s = new %s()", paramName, name);
    writer.endConstructor();
    writer.emitEmptyLine();
    writer.beginConstructor(PRIVATE, name, paramName);
    writer.emitStatement("this.%s = new %s(%s)", paramName, name, paramName);
    writer.endConstructor();
    writer.emitEmptyLine();
    writer.emitAnnotation("Override");
    writer.beginMethod(name, "build", PUBLIC);
    writer.emitStatement("return new %s(%s)", name, paramName);
    writer.endMethod();
    writer.emitEmptyLine();
    writer.emitAnnotation("Override");
    writer.beginMethod(name, "getInternalEntity", EnumSet.of(Modifier.PROTECTED));
    writer.emitStatement("return %s", paramName);
    writer.endMethod();
    writer.emitEmptyLine();
    for (FieldDescription field : description.fields) {
      writeAccessors(writer, field, paramName + ".");
      writeMutators(writer, field, paramName);
    }
    writer.endType();
    writer.emitEmptyLine();
  }

  private static void writeCommonStaticMethods(JavaWriter writer, EntityDescription description)
      throws IOException {
    writer.beginMethod("Builder", "newBuilder", EnumSet.of(Modifier.PUBLIC, Modifier.STATIC));
    writer.emitStatement("return new Builder()");
    writer.endMethod();
    writer.emitEmptyLine();

    writer.beginMethod("Deserializer", "newDeserializer",
        EnumSet.of(Modifier.PUBLIC, Modifier.STATIC));
    writer.emitStatement("return new Deserializer()");
    writer.endMethod();
    writer.emitEmptyLine();
  }

  private static void writeFields(JavaWriter writer, EntityDescription description)
      throws IOException {
    for (FieldDescription field : description.fields) {
      if (field.repeated) {
        writer.emitField("List<" + field.type + ">", field.name + "List",
            EnumSet.of(Modifier.PRIVATE, Modifier.FINAL));
      } else {
        writer.emitField(field.type, field.name, PRIVATE);
      }
    }
    writer.emitEmptyLine();
  }

  private static void writeConstructors(JavaWriter writer, EntityDescription description)
      throws IOException {
    String name = description.name;
    String paramName = decapitalize(name);

    writer.beginConstructor(PRIVATE);
    for (FieldDescription field : description.fields) {
      if (field.repeated) {
        writer.emitStatement("%sList = new ArrayList<>()", field.name);
      }
    }
    writer.endConstructor();
    writer.emitEmptyLine();

    writer.beginConstructor(PRIVATE, name, paramName);
    for (FieldDescription field : description.fields) {
      if (field.repeated) {
        writer.emitStatement("%sList = new ArrayList<>(%s.%sList)", field.name, paramName,
            field.name);
      } else {
        writer.emitStatement("%s = %s.%s", field.name, paramName, field.name);
      }
    }
    writer.endConstructor();
    writer.emitEmptyLine();

    writer.beginConstructor(PRIVATE, "Map<String, Object>", "map");
    for (FieldDescription field : description.fields) {
      if (field.repeated) {
        if (isPrimitive(field) || field.type.equals("String")) {
          writer.emitStatement("%sList = getRepeated(map, \"%s\")", field.name, field.name);
        } else {
          writer.emitStatement("%sList = getRepeated(map, \"%s\", %s.newDeserializer())",
              field.name, field.name, field.type);
        }
      } else {
        if (isPrimitive(field) || field.type.equals("String")) {
          writer.emitStatement("%s = get(map, \"%s\")", field.name, field.name);
        } else {
          writer.emitStatement("%s = get(map, \"%s\", %s.newDeserializer())", field.name,
              field.name, field.type);
        }
      }
    }
    writer.endConstructor();
    writer.emitEmptyLine();
  }

  private static void writeCommonMethods(JavaWriter writer, EntityDescription description)
      throws IOException {
    writer.emitAnnotation("Override");
    writer.beginMethod("String", "entityName", PUBLIC);
    writer.emitStatement("return \"%s\"", description.name);
    writer.endMethod();
    writer.emitEmptyLine();

    writer.emitAnnotation("Override");
    writer.beginMethod("Map<String, Object>", "serialize", PUBLIC);
    writer.emitStatement("Map<String, Object> result = new HashMap<>()");
    for (FieldDescription field : description.fields) {
      if (field.repeated) {
        writer.emitStatement("putSerialized(result, \"%s\", %sList)", field.name, field.name);
      } else {
        writer.emitStatement("putSerialized(result, \"%s\", %s)", field.name, field.name);
      }
    }
    writer.emitStatement("return result");
    writer.endMethod();
    writer.emitEmptyLine();

    writer.emitAnnotation("Override");
    writer.beginMethod("Builder", "toBuilder", PUBLIC);
    writer.emitStatement("return new Builder(this)");
    writer.endMethod();
    writer.emitEmptyLine();
  }

  private static void writeFieldMethods(JavaWriter writer, EntityDescription description)
      throws IOException {
    for (FieldDescription field : description.fields) {
      writeAccessors(writer, field, "");
    }
  }

  private static void writeAccessors(JavaWriter writer, FieldDescription field, String accessor)
      throws IOException {
    String name = accessor + field.name;
    String capitalName = capitalize(field.name);

    if (field.repeated) {
      writer.beginMethod("int", "get" + capitalName + "Count", PUBLIC);
      writer.emitStatement("return %sList.size()", name);
      writer.endMethod();
      writer.emitEmptyLine();

      writer.beginMethod(capitalName, "get" + capitalName, PUBLIC, "int",
          "index");
      writer.emitStatement("return %sList.get(index)", name);
      writer.endMethod();
      writer.emitEmptyLine();

      writer.beginMethod("List<" + capitalName + ">", "get" + capitalName + "List",
          PUBLIC);
      writer.emitStatement("return Collections.unmodifiableList(%sList)", name);
      writer.endMethod();
      writer.emitEmptyLine();
    } else {
      writer.beginMethod("boolean", "has" + capitalName, PUBLIC);
      writer.emitStatement("return %s != null", name);
      writer.endMethod();
      writer.emitEmptyLine();

      writer.beginMethod(maybePrimitive(field), "get" + capitalName, PUBLIC);
      writer.emitStatement("checkNotNull(%s)", name);
      writer.emitStatement("return %s", name);
      writer.endMethod();
      writer.emitEmptyLine();
    }
  }

  private static void writeMutators(JavaWriter writer, FieldDescription field, String accessor)
      throws IOException {
    String name = field.name;
    String capitalName = capitalize(field.name);
    if (field.repeated) {
      writer.beginMethod("Builder", "set" + capitalName, PUBLIC, "int", "index", capitalName, name);
      if (!isPrimitive(field)) {
        writer.emitStatement("checkNotNull(%s)", name);
      }
      writer.emitStatement("%s.%sList.set(index, %s)", accessor, name, name);
      writer.emitStatement("return this");
      writer.endMethod();
      writer.emitEmptyLine();

      writer.beginMethod("Builder", "add" + capitalName, PUBLIC, capitalName, name);
      if (!isPrimitive(field)) {
        writer.emitStatement("checkNotNull(%s)", name);
      }
      writer.emitStatement("%s.%sList.add(%s)", accessor, name, name);
      writer.emitStatement("return this");
      writer.endMethod();
      writer.emitEmptyLine();

      writer.beginMethod("Builder", "addAll" + capitalName, PUBLIC, "List<" + capitalName + ">",
          name + "List");
      writer.emitStatement("checkListForNull(%sList)", name);
      writer.emitStatement("%s.%sList.addAll(%sList)", accessor, name, name);
      writer.emitStatement("return this");
      writer.endMethod();
      writer.emitEmptyLine();

      writer.beginMethod("Builder", "clear" + capitalName + "List", PUBLIC);
      writer.emitStatement("%s.%sList.clear()", accessor, name);
      writer.emitStatement("return this");
      writer.endMethod();
      writer.emitEmptyLine();
    } else {
      writer.beginMethod("Builder", "set" + capitalName, PUBLIC, maybePrimitive(field), name);
      if (!isPrimitive(field)) {
        writer.emitStatement("checkNotNull(%s)", name);
      }
      writer.emitStatement("%s.%s = %s", accessor, name, name);
      writer.emitStatement("return this");
      writer.endMethod();
      writer.emitEmptyLine();

      writer.beginMethod("Builder", "clear" + capitalName, PUBLIC);
      writer.emitStatement("%s.%s = null", accessor, name);
      writer.emitStatement("return this");
      writer.endMethod();
      writer.emitEmptyLine();
    }
  }

  private static void writeEnum(JavaWriter writer, EntityDescription description)
      throws IOException {

  }

  private static boolean isPrimitive(FieldDescription field) {
    switch (field.type) {
      case "Byte":
      case "Short":
      case "Integer":
      case "Long":
      case "Boolean":
      case "Character":
      case "Double":
      case "Float":
        return true;
      default:
        return false;
    }
  }

  private static String maybePrimitive(FieldDescription field) {
    switch (field.type) {
      case "Byte":
        return "byte";
      case "Short":
        return "short";
      case "Integer":
        return "int";
      case "Long":
        return "long";
      case "Boolean":
        return "boolean";
      case "Character":
        return "char";
      case "Double":
        return "double";
      case "Float":
        return "float";
      default:
        return field.type;
    }
  }

  private static String capitalize(String string) {
    return Character.toUpperCase(
        string.charAt(0)) + (string.length() > 1 ? string.substring(1) : "");
  }

  private static String decapitalize(String string) {
    return Character.toLowerCase(
        string.charAt(0)) + (string.length() > 1 ? string.substring(1) : "");
  }
}
