package com.tinlib.message;

import java.util.Map;

public interface Subscriber extends AnySubscriber {
  public void onMessage(Map<String, Object> map);
}
