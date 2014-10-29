package com.tinlib.util;

import com.google.common.base.Preconditions;

import java.util.List;

public class ListUtil {
  public static <T> void addOrSet(List<T> list, int index, T value, T defaultValue) {
    Preconditions.checkArgument(index > 0);
    if (index < list.size()) {
      list.set(index, value);
    } else {
      while (index > list.size()) {
        list.add(defaultValue);
      }
      list.add(value);
    }
  }
}
