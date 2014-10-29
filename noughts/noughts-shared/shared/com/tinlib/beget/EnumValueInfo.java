package com.tinlib.beget;

import org.json.JSONException;
import org.json.JSONObject;

class EnumValueInfo {
  private final String name;
  private final String description;

  public EnumValueInfo(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public EnumValueInfo(JSONObject object) throws JSONException {
    name = object.getString("name");
    description = object.optString("desc");
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }
}
