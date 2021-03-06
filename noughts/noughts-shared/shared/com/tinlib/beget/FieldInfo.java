package com.tinlib.beget;

import org.json.JSONException;
import org.json.JSONObject;

class FieldInfo {
  private final String name;
  private final String type;
  private final boolean repeated;
  private final String description;
  private final String packageString;

  public FieldInfo(String name, String type, boolean repeated, String description,
      String packageString) {
    this.name = name;
    this.type = type;
    this.repeated = repeated;
    this.description = description;
    this.packageString = packageString;
  }

  public FieldInfo(String packageString, JSONObject object) throws JSONException {
    name = object.getString("name");
    type = object.getString("type");
    repeated = object.optBoolean("repeated");
    description = object.optString("desc");
    if (EntityWriter.isPrimitive(name)) {
      this.packageString = "";
    } else {
      this.packageString = packageString;
    }
  }

  public String fullyQualifiedType() {
    return packageString.equals("") ? type : packageString + "." + type;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public boolean isRepeated() {
    return repeated;
  }

  public String getDescription() {
    return description;
  }

  public String getPackageString() {
    return packageString;
  }
}
