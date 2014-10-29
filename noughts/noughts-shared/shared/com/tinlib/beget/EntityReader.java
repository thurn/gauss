package com.tinlib.beget;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

class EntityReader {
  public static class ReadResult {
    private final Map<String, EntityType> entityTypes;
    private final Iterable<EntityInfo> entityInformation;

    public ReadResult(Map<String, EntityType> entityTypes, Iterable<EntityInfo> entityInformation) {
      this.entityTypes = entityTypes;
      this.entityInformation = entityInformation;
    }

    public Map<String, EntityType> getEntityTypes() {
      return entityTypes;
    }

    public Iterable<EntityInfo> getEntityInformation() {
      return entityInformation;
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
  private final Map<String, EntityInfo> entityInformation = Maps.newHashMap();

  public ReadResult read(List<String> jsonStrings, List<File> parentDirectories)
      throws JSONException {
    for (int i = 0; i < jsonStrings.size(); ++i) {
      JSONArray array = new JSONArray(jsonStrings.get(i));
      for (int j = 0; j < array.length(); ++j) {
        readObject(parentDirectories.get(i), array.getJSONObject(j));
      }
    }

    loadJsonObjects();
    loadJsonExtensions();

    return new ReadResult(entityTypes, entityInformation.values());
  }

  private void readObject(File parent, JSONObject object) throws JSONException {
    switch (object.getString("type")) {
      case "entity":
      case "enum":
        jsonObjects.add(new ObjectWithFile(object, parent));
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
      String fullyQualifiedName = packageString + "." + name;
      String description = object.optString("desc");
      EntityInfo entityInfo = new EntityInfo(type, name, packageString,
          description, objectWithFile.file);
      if (type == EntityType.ENTITY) {
        addFields(entityInfo, object);
      }
      if (type == EntityType.ENUM) {
        addEnumValues(entityInfo, object);
      }
      entityInformation.put(fullyQualifiedName, entityInfo);
      entityTypes.put(fullyQualifiedName, type);
    }
  }

  private void loadJsonExtensions() throws JSONException {
    for (JSONObject object : jsonExtensions) {
      String name = object.getString("name");
      String packageString = object.getString("package");
      String fullyQualifiedName = packageString + "." + name;
      String type = object.getString("type");
      if (!entityInformation.containsKey(fullyQualifiedName)) {
        throw new IllegalArgumentException("Unable to find base implementation for extension " +
            fullyQualifiedName);
      }
      EntityInfo entityInfo = entityInformation.get(fullyQualifiedName);
      if (type.equals("entity_extension")) {
        addFields(entityInfo, object);
      }
      if (type.equals("enum_extension")) {
        addEnumValues(entityInfo, object);
      }
      entityInformation.put(fullyQualifiedName, entityInfo);
    }
  }

  private void addFields(EntityInfo entityInfo, JSONObject object) throws JSONException {
    for (int i = 0; i < object.getJSONArray("fields").length(); ++i) {
      JSONObject fieldObject = object.getJSONArray("fields").getJSONObject(i);
      String name = fieldObject.getString("name");
      String type = fieldObject.getString("type");
      boolean repeated = fieldObject.optBoolean("repeated");
      String description = fieldObject.optString("desc");
      String packageString = fieldObject.optString("package");
      if (entityInfo.hasFieldNamed(name)) {
        throw new IllegalArgumentException("Duplicate field name: " + name);
      }
      entityInfo.addField(new FieldInfo(name, type, repeated, description, packageString));
    }
  }

  private void addEnumValues(EntityInfo entityInfo, JSONObject object)
      throws JSONException {
    for (int i = 0; i < object.getJSONArray("values").length(); ++i) {
      JSONObject valueObject = object.getJSONArray("values").getJSONObject(i);
      String name = valueObject.getString("name");
      String description = valueObject.optString("desc");
      if (entityInfo.hasEnumValueNamed(name)) {
        throw new IllegalArgumentException("Duplicate enum value name: " + name);
      }
      entityInfo.addEnumValue(new EnumValueInfo(name, description));
    }
  }
}
