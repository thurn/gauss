package com.tinlib.util;

import java.util.UUID;

public class Identifiers {
  public static String id(String base) {
    return (base + "/" + UUID.randomUUID().toString()).intern();
  }
}
