package com.tinlib.beget;

import com.google.common.collect.Lists;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

class EntityInfo {
  private final EntityType type;
  private final String name;
  private final String packageString;
  private final String description;
  private final List<FieldInfo> fields = Lists.newArrayList();
  private final List<EnumValueInfo> values = Lists.newArrayList();
  private final File parent;

  public EntityInfo(EntityType type, String name, String packageString, String description,
      File parentFile) {
    this.type = type;
    this.name = name;
    this.packageString = packageString;
    this.description = description;
    this.parent = parentFile;
  }

  public EntityInfo(JSONObject object, File parent) throws JSONException {
    type = object.getString("type").equals("entity") ? EntityType.ENTITY : EntityType.ENUM;
    name = object.getString("name");
    packageString = object.getString("package");
    description = object.optString("desc");
    switch (type) {
      case ENTITY:
        for (int i = 0; i < object.getJSONArray("fields").length(); ++i) {
          fields.add(new FieldInfo(packageString,
              object.getJSONArray("fields").getJSONObject(i)));
        }
        break;
      case ENUM:
        for (int i = 0; i < object.getJSONArray("values").length(); ++i) {
          values.add(new EnumValueInfo(object.getJSONArray("values").getJSONObject(i)));
        }
        break;
      default:
        throw new RuntimeException("Unknown entity type " + type);
    }
    this.parent = parent;
  }

  public void addField(FieldInfo fieldInfo) {
    fields.add(fieldInfo);
  }

  public void addEnumValue(EnumValueInfo enumValueInfo) {
    values.add(enumValueInfo);
  }

  public String fullyQualifiedName() {
    return packageString + "." + name;
  }

  public EntityType getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public String getPackageString() {
    return packageString;
  }

  public String getDescription() {
    return description;
  }

  public List<FieldInfo> getFields() {
    return fields;
  }

  public boolean hasFieldNamed(String name) {
    for (FieldInfo fieldInfo : fields) {
      if (fieldInfo.getName().equals(name)) return true;
    }
    return false;
  }

  public List<EnumValueInfo> getEnumValues() {
    return values;
  }

  public boolean hasEnumValueNamed(String name) {
    for (EnumValueInfo enumValueInfo : values) {
      if (enumValueInfo.getName().equals(name)) return true;
    }
    return false;
  }

  public File getParent() {
    return parent;
  }
}
