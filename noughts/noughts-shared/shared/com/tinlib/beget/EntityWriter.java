package com.tinlib.beget;

import com.google.common.collect.ImmutableList;
import com.squareup.javawriter.JavaWriter;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Map;

class EntityWriter {
  private static final EnumSet<Modifier> PUBLIC = EnumSet.of(Modifier.PUBLIC);
  private static final EnumSet<Modifier> PRIVATE = EnumSet.of(Modifier.PRIVATE);
  private final Map<String, EntityType> entityTypes;

  public EntityWriter(Map<String, EntityType> entityTypes) {
    this.entityTypes = entityTypes;
  }

  public void writeEntityDescription(JavaWriter writer, EntityInfo entityInfo)
      throws IOException {
    switch (entityInfo.getType()) {
      case ENTITY:
        writeEntity(writer, entityInfo);
        break;
      case ENUM:
        writeEnum(writer, entityInfo);
        break;
      default:
        throw new RuntimeException("Unknown entity type: " + entityInfo.getType());
    }
  }

  private void writeEntity(JavaWriter writer, EntityInfo description)
      throws IOException {
    writePreface(writer, description);
    writer.emitImports(ImmutableList.of("java.util.*"));
    writer.emitEmptyLine();
    writer.emitImports(ImmutableList.of("com.tinlib.entities.Entity"));
    writer.emitEmptyLine();
    writer.emitJavadoc(description.getDescription());
    writer.beginType(description.getName(), "class", EnumSet.of(Modifier.PUBLIC, Modifier.FINAL),
        "Entity<" + description.getName() + ">");
    writeDeserializer(writer, description.getName());
    writeBuilder(writer, description);
    writeCommonStaticMethods(writer, description);
    writeFields(writer, description);
    writeConstructors(writer, description);
    writeCommonMethods(writer, description);
    writeFieldMethods(writer, description);
    writer.endType();
    writer.close();
  }

  private void writePreface(JavaWriter writer, EntityInfo description) throws IOException {
    writer.emitSingleLineComment("================================");
    writer.emitSingleLineComment("GENERATED CODE -- DO NOT MODIFY!");
    writer.emitSingleLineComment("================================");
    writer.emitEmptyLine();
    writer.emitPackage(description.getPackageString());
  }

  private void writeDeserializer(JavaWriter writer, String name) throws IOException {
    String paramName = decapitalize(name) + "Map";
    writer.emitJavadoc("Class to create %s instances from their serialized representation.", name);
    writer.beginType("Deserializer", "class",
        EnumSet.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL),
        "EntityDeserializer<" + name + ">");
    writer.beginConstructor(PRIVATE);
    writer.endConstructor();
    writer.emitEmptyLine();
    writer.emitJavadoc("Takes a map (e.g one returned from {@link %s#serialize()}) and returns a " +
        "new %s instance.", name, name);
    writer.emitAnnotation("Override");
    writer.beginMethod(name, "deserialize", PUBLIC, "Map<String, Object>",
        paramName);
    writer.emitStatement("return new %s(%s)", name, paramName);
    writer.endMethod();
    writer.endType();
    writer.emitEmptyLine();
  }

  private void writeBuilder(JavaWriter writer, EntityInfo description)
      throws IOException {
    String name = description.getName();
    String paramName = decapitalize(name);
    writer.emitJavadoc("Helper utility class to create new %s instances.", description.getName());
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
    writer.emitJavadoc("Returns a new immutable %s instance based on the current state of this " +
        "Builder.", description.getName());
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
    for (FieldInfo field : description.getFields()) {
      writeAccessors(writer, field, paramName + ".", true /* inBuilder */);
      writeMutators(writer, field, paramName);
    }
    writer.endType();
    writer.emitEmptyLine();
  }

  private void writeCommonStaticMethods(JavaWriter writer, EntityInfo description)
      throws IOException {
    writer.emitJavadoc("Returns a new Builder class to help you create %s instances.",
        description.getName());
    writer.beginMethod("Builder", "newBuilder", EnumSet.of(Modifier.PUBLIC, Modifier.STATIC));
    writer.emitStatement("return new Builder()");
    writer.endMethod();
    writer.emitEmptyLine();

    writer.emitJavadoc("Returns a new Deserializer class to help you create %s instances " +
        "from their serialized form.", description.getName());
    writer.beginMethod("Deserializer", "newDeserializer",
        EnumSet.of(Modifier.PUBLIC, Modifier.STATIC));
    writer.emitStatement("return new Deserializer()");
    writer.endMethod();
    writer.emitEmptyLine();
  }

  private void writeFields(JavaWriter writer, EntityInfo description)
      throws IOException {
    for (FieldInfo field : description.getFields()) {
      if (field.isRepeated()) {
        writer.emitField("List<" + field.getType() + ">", field.getName() + "List",
            EnumSet.of(Modifier.PRIVATE, Modifier.FINAL));
      } else {
        writer.emitField(field.getType(), field.getName(), PRIVATE);
      }
    }
    writer.emitEmptyLine();
  }

  private void writeConstructors(JavaWriter writer, EntityInfo description)
      throws IOException {
    String name = description.getName();
    String paramName = decapitalize(name);

    writer.beginConstructor(PRIVATE);
    for (FieldInfo field : description.getFields()) {
      if (field.isRepeated()) {
        writer.emitStatement("%sList = new ArrayList<>()", field.getName());
      }
    }
    writer.endConstructor();
    writer.emitEmptyLine();

    writer.beginConstructor(PRIVATE, name, paramName);
    for (FieldInfo field : description.getFields()) {
      if (field.isRepeated()) {
        writer.emitStatement("%sList = new ArrayList<>(%s.%sList)", field.getName(), paramName,
            field.getName());
      } else {
        writer.emitStatement("%s = %s.%s", field.getName(), paramName, field.getName());
      }
    }
    writer.endConstructor();
    writer.emitEmptyLine();

    writer.beginConstructor(PRIVATE, "Map<String, Object>", "map");
    for (FieldInfo field : description.getFields()) {
      if (field.isRepeated()) {
        if (isPrimitive(field.getType()) || field.getType().equals("String")) {
          writer.emitStatement("%sList = getRepeated(map, \"%s\", %s.class)", field.getName(),
              field.getName(), field.getType());
        } else if (entityTypes.get(field.fullyQualifiedType()) == EntityType.ENUM) {
          writer.emitStatement("%sList = getRepeatedEnum(map, \"%s\", %s.class)", field.getName(),
              field.getName(), field.getType());
        } else {
          writer.emitStatement("%sList = getRepeated(map, \"%s\", %s.newDeserializer())",
              field.getName(), field.getName(), field.getType());
        }
      } else {
        if (isPrimitive(field.getType()) || field.getType().equals("String")) {
          writer.emitStatement("%s = get(map, \"%s\", %s.class)", field.getName(), field.getName(),
              field.getType());
        } else if (entityTypes.get(field.fullyQualifiedType()) == EntityType.ENUM) {
          writer.emitStatement("%s = getEnum(map, \"%s\", %s.class)", field.getName(),
              field.getName(), field.getType());
        } else {
          writer.emitStatement("%s = get(map, \"%s\", %s.newDeserializer())", field.getName(),
              field.getName(), field.getType());
        }
      }
    }
    writer.endConstructor();
    writer.emitEmptyLine();
  }

  private void writeCommonMethods(JavaWriter writer, EntityInfo description)
      throws IOException {
    writer.emitJavadoc("Returns the name of this entity for use in toString().");
    writer.emitAnnotation("Override");
    writer.beginMethod("String", "entityName", PUBLIC);
    writer.emitStatement("return \"%s\"", description.getName());
    writer.endMethod();
    writer.emitEmptyLine();

    writer.emitJavadoc("Creates a Map representation of this %s.", description.getName());
    writer.emitAnnotation("Override");
    writer.beginMethod("Map<String, Object>", "serialize", PUBLIC);
    writer.emitStatement("Map<String, Object> result = new HashMap<>()");
    for (FieldInfo field : description.getFields()) {
      if (field.isRepeated()) {
        writer.emitStatement("putSerialized(result, \"%s\", %sList)", field.getName(),
            field.getName());
      } else {
        writer.emitStatement("putSerialized(result, \"%s\", %s)", field.getName(), field.getName());
      }
    }
    writer.emitStatement("return result");
    writer.endMethod();
    writer.emitEmptyLine();

    writer.emitJavadoc("Creates a new Builder initialized with the current contents of this %s.",
        description.getName());
    writer.emitAnnotation("Override");
    writer.beginMethod("Builder", "toBuilder", PUBLIC);
    writer.emitStatement("return new Builder(this)");
    writer.endMethod();
    writer.emitEmptyLine();
  }

  private void writeFieldMethods(JavaWriter writer, EntityInfo description)
      throws IOException {
    for (FieldInfo field : description.getFields()) {
      writeAccessors(writer, field, "", false /* inBuilder */);
    }
  }

  private void writeAccessors(JavaWriter writer, FieldInfo field, String accessor,
                              boolean inBuilder) throws IOException {
    String name = accessor + field.getName();
    String capitalName = capitalize(field.getName());

    if (field.isRepeated()) {
      writer.emitJavadoc("Returns the size of the %sList", field.getName());
      writer.beginMethod("int", "get" + capitalName + "Count", PUBLIC);
      writer.emitStatement("return %sList.size()", name);
      writer.endMethod();
      writer.emitEmptyLine();

      writer.emitJavadoc("Returns the %s at the provided index.\n\n@return %s", field.getName(),
          field.getDescription());
      writer.beginMethod(maybePrimitive(field), "get" + capitalName, PUBLIC, "int",
          "index");
      writer.emitStatement("return %sList.get(index)", name);
      writer.endMethod();
      writer.emitEmptyLine();

      writer.emitJavadoc("Returns the %sList.\n\nValues: %s", field.getName(),
          field.getDescription());
      writer.beginMethod("List<" + field.getType() + ">", "get" + capitalName + "List",
          PUBLIC);
      if (inBuilder) {
        writer.emitStatement("return %sList", name);
      } else {
        writer.emitStatement("return Collections.unmodifiableList(%sList)", name);
      }
      writer.endMethod();
      writer.emitEmptyLine();
    } else {
      writer.emitJavadoc("Returns true if a value has been set for %s", field.getName());
      writer.beginMethod("boolean", "has" + capitalName, PUBLIC);
      writer.emitStatement("return %s != null", name);
      writer.endMethod();
      writer.emitEmptyLine();

      writer.emitJavadoc("Gets the value of %s\n\n@return %s", field.getName(),
          field.getDescription());
      writer.beginMethod(maybePrimitive(field), "get" + capitalName, PUBLIC);
      writer.emitStatement("checkNotNull(%s)", name);
      writer.emitStatement("return %s", name);
      writer.endMethod();
      writer.emitEmptyLine();
    }
  }

  private void writeMutators(JavaWriter writer, FieldInfo field, String accessor)
      throws IOException {
    String name = field.getName();
    String capitalName = capitalize(field.getName());
    if (field.isRepeated()) {
      if (entityTypes.get(field.fullyQualifiedType()) == EntityType.ENTITY) {
        writer.emitJavadoc("set%s with a Builder argument", capitalName);
        writer.beginMethod("Builder", "set" + capitalName, PUBLIC, "int", "index",
            "EntityBuilder<" + field.getType() + ">", name);
        writer.emitStatement("return set%s(index, %s.build())", capitalName, name);
        writer.endMethod();
        writer.emitEmptyLine();
      }

      writer.emitJavadoc("Sets the %s at the given index.\n\n@param %s %s", field.getName(),
          field.getName(), field.getDescription());
      writer.beginMethod("Builder", "set" + capitalName, PUBLIC, "int", "index", maybePrimitive(field),
          name);
      if (!isPrimitive(field.getType())) {
        writer.emitStatement("checkNotNull(%s)", name);
      }
      writer.emitStatement("%s.%sList.set(index, %s)", accessor, name, name);
      writer.emitStatement("return this");
      writer.endMethod();
      writer.emitEmptyLine();

      if (entityTypes.get(field.fullyQualifiedType()) == EntityType.ENTITY) {
        writer.emitJavadoc("add%s with a Builder argument", capitalName);
        writer.beginMethod("Builder", "add" + capitalName, PUBLIC,
            "EntityBuilder<" + field.getType() + ">", name);
        writer.emitStatement("return add%s(%s.build())", capitalName, name);
        writer.endMethod();
        writer.emitEmptyLine();
      }

      writer.emitJavadoc("Adds a new %s to the end of the %sList.\n\n@param %s %s", field.getName(),
          field.getName(), field.getName(), field.getDescription());
      writer.beginMethod("Builder", "add" + capitalName, PUBLIC, maybePrimitive(field), name);
      if (!isPrimitive(field.getType())) {
        writer.emitStatement("checkNotNull(%s)", name);
      }
      writer.emitStatement("%s.%sList.add(%s)", accessor, name, name);
      writer.emitStatement("return this");
      writer.endMethod();
      writer.emitEmptyLine();

      writer.emitJavadoc("Adds all %s instances from the provided list to the %sList.\n\n" +
          "Values: %s", field.getName(), field.getName(), field.getDescription());
      writer.beginMethod("Builder", "addAll" + capitalName, PUBLIC, "List<" + field.getType() + ">",
          name + "List");
      writer.emitStatement("checkListForNull(%sList)", name);
      writer.emitStatement("%s.%sList.addAll(%sList)", accessor, name, name);
      writer.emitStatement("return this");
      writer.endMethod();
      writer.emitEmptyLine();

      writer.emitJavadoc("Removes all values from the %sList", field.getName());
      writer.beginMethod("Builder", "clear" + capitalName + "List", PUBLIC);
      writer.emitStatement("%s.%sList.clear()", accessor, name);
      writer.emitStatement("return this");
      writer.endMethod();
      writer.emitEmptyLine();
    } else {
      if (entityTypes.get(field.fullyQualifiedType()) == EntityType.ENTITY) {
        writer.emitJavadoc("set%s with a Builder argument", capitalName);
        writer.beginMethod("Builder", "set" + capitalName, PUBLIC,
            "EntityBuilder<" + field.getType() + ">", name);
        writer.emitStatement("return set%s(%s.build())", capitalName, name);
        writer.endMethod();
      }

      writer.emitJavadoc("Sets the value of %s.\n\n@param %s %s", field.getName(), field.getName(),
          field.getDescription());
      writer.beginMethod("Builder", "set" + capitalName, PUBLIC, maybePrimitive(field), name);
      if (!isPrimitive(field.getType())) {
        writer.emitStatement("checkNotNull(%s)", name);
      }
      writer.emitStatement("%s.%s = %s", accessor, name, name);
      writer.emitStatement("return this");
      writer.endMethod();
      writer.emitEmptyLine();

      writer.emitJavadoc("Unsets the value of %s.", field.getName());
      writer.beginMethod("Builder", "clear" + capitalName, PUBLIC);
      writer.emitStatement("%s.%s = null", accessor, name);
      writer.emitStatement("return this");
      writer.endMethod();
      writer.emitEmptyLine();
    }
  }

  private void writeEnum(JavaWriter writer, EntityInfo description)
      throws IOException {
    writePreface(writer, description);
    writer.beginType(description.getName(), "enum", PUBLIC);
    for (EnumValueInfo enumDescription : description.getEnumValues()) {
      writer.emitEnumValue(enumDescription.getName());
    }
    writer.endType();
    writer.close();
  }

  public static boolean isPrimitive(String type) {
    switch (type) {
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

  private static String maybePrimitive(FieldInfo field) {
    switch (field.getType()) {
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
        return field.getType();
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
