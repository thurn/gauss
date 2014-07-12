package com.tinlib.message;

import java.util.Map;

public interface Awaiter {
  public void onResult(Map<String, Object> objects);
}
