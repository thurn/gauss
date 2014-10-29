package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;

@Export
@ExportPackage("nts")
public class JavascriptUtils implements Exportable {
  @Export
  public static class MapWrapper<K, V> implements Exportable {
    private final Map<K, V> map;
    private final List<K> keys;

    private MapWrapper(Map<K, V> map) {
      this.map = map;
      this.keys = new ArrayList<K>(map.keySet());
    }

    public void put(K key, V value) {
      this.map.put(key, value);
      this.keys.add(key);
    }

    public K getKey(int index) {
      return keys.get(index);
    }

    public V get(K key) {
      return map.get(key);
    }

    public int getKeyCount() {
      return keys.size();
    }

    public Map<K, V> getMap() {
      return map;
    }
  }

  public static class ListWrapper<T> implements Exportable {
    private final List<T> list;

    private ListWrapper(List<T> list) {
      this.list = list;
    }

    public void add(T value) {
      list.add(value);
    }

    public int size() {
      return list.size();
    }

    public void set(int index, T value) {
      list.set(index, value);
    }

    public T get(int index) {
      return list.get(index);
    }

    public List<T> getList() {
      return list;
    }
  }

  public static <K, V> MapWrapper<K, V> emptyMapWrapper() {
    return new MapWrapper<K, V>(new HashMap<K, V>());
  }

  public static <K, V> MapWrapper<K, V> mapWrapper(Map<K, V> map) {
    return new MapWrapper<K, V>(map);
  }

  public static <T> ListWrapper<T> emptyListWrapper() {
    return new ListWrapper<T>(new ArrayList<T>());
  }

  public static <T> ListWrapper<T> listWrapper(List<T> list) {
    return new ListWrapper<T>(list);
  }
}
