package com.tinlib.beget;

import org.json.JSONException;
import org.json.JSONObject;

/**
* Created by dthurn on 8/16/14.
*/
class EnumValueDescription {
  private final String name;
  private final String description;

  public EnumValueDescription(JSONObject object) throws JSONException {
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
