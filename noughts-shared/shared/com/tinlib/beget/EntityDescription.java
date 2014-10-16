package com.tinlib.beget;

import com.google.common.collect.Lists;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

class EntityDescription {
  private final EntityType type;
  private final String name;
  private final String packageString;
  private final String description;
  private final List<FieldDescription> fields = Lists.newArrayList();
  private final List<EnumValueDescription> values = Lists.newArrayList();
  private final File parent;

  public EntityDescription(EntityType type, String name, String packageString, String description,
      File parentFile) {
    this.type = type;
    this.name = name;
    this.packageString = packageString;
    this.description = description;
    this.parent = parentFile;
  }

  public EntityDescription(JSONObject object, File parent) throws JSONException {
    type = object.getString("type").equals("entity") ? EntityType.ENTITY : EntityType.ENUM;
    name = object.getString("name");
    packageString = object.getString("package");
    description = object.optString("desc");
    switch (type) {
      case ENTITY:
        for (int i = 0; i < object.getJSONArray("fields").length(); ++i) {
          fields.add(new FieldDescription(packageString,
              object.getJSONArray("fields").getJSONObject(i)));
        }
        break;
      case ENUM:
        for (int i = 0; i < object.getJSONArray("values").length(); ++i) {
          values.add(new EnumValueDescription(object.getJSONArray("values").getJSONObject(i)));
        }
        break;
      default:
        throw new RuntimeException("Unknown entity type " + type);
    }
    this.parent = parent;
  }

  public void addField(FieldDescription fieldDescription) {
    fields.add(fieldDescription);
  }

  public void addEnumValue(EnumValueDescription enumValueDescription) {
    values.add(enumValueDescription);
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

  public List<FieldDescription> getFields() {
    return fields;
  }

  public List<EnumValueDescription> getValues() {
    return values;
  }

  public File getParent() {
    return parent;
  }

  public boolean hasParent() {
    return parent != null;
  }
}
