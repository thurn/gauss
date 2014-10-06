package com.tinlib.beget;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class EntityTest {
  private static enum TestEnum {
    VALUE
  }

  private static class TestEntityBuilder extends Entity.EntityBuilder<TestEntity> {
    private TestEntity testEntity;

    public TestEntityBuilder(TestEntity testEntity) {
      this.testEntity = testEntity;
    }

    @Override
    public TestEntity build() {
      return testEntity;
    }

    @Override
    protected TestEntity getInternalEntity() {
      return testEntity;
    }
  }

  private static class TestEntityDeserializer extends Entity.EntityDeserializer<TestEntity> {
    @Override
    public TestEntity deserialize(Map<String, Object> map) {
      return new TestEntity(map);
    }
  }

  private static class TestEntity extends Entity<TestEntity> {
    public Map<String, Object> map;

    public TestEntity() {
      this(ImmutableMap.<String, Object>of());
    }

    public TestEntity(Map<String, Object> map) {
      this.map = map;
    }

    @Override
    public String entityName() {
      return "TestEntity";
    }

    @Override
    public Map<String, Object> serialize() {
      return map;
    }

    @Override
    public EntityBuilder<TestEntity> toBuilder() {
      return new TestEntityBuilder(this);
    }
  }

  @Test(expected = NullPointerException.class)
  public void testCheckNotNull() {
    assertEquals("foo", Entity.checkNotNull("foo"));
    Entity.checkNotNull(null);
  }

  @Test
  public void testCheckListForNull() {
    Entity.checkListForNull(ImmutableList.of("foo"));
    List<String> nullList = Lists.newArrayList();
    nullList.add("foo");
    nullList.add(null);
    try {
      Entity.checkListForNull(nullList);
      fail("Exception expected");
    } catch (NullPointerException ignored) {}
  }

  @Test
  public void testGetInteger() {
    assertEquals(new Integer(4), Entity.get(map("key", 4), "key", Integer.class));
  }

  @Test
  public void testGetIntegerToLong() {
    assertEquals(new Long(4), Entity.get(map("key", 4), "key", Long.class));
  }

  @Test
  public void testGetString() {
    assertEquals("foo", Entity.get(map("key", "foo"), "key", String.class));
  }

  @Test
  public void testGetNull() {
    assertNull(Entity.get(map("key", null), "key", String.class));
  }

  @Test
  public void testGetMissing() {
    assertNull(Entity.get(map("key", null), "other", String.class));
  }

  @Test
  public void testGetEntity() {
    TestEntity testEntity = new TestEntity(map("foo", 1));
    assertEquals(testEntity, Entity.get(map("key", testEntity.serialize()), "key",
        new TestEntityDeserializer()));
  }

  @Test
  public void testGetEntityMissing() {
    assertNull(Entity.get(map("other", 12), "key", new TestEntityDeserializer()));
  }

  @Test
  public void testGetRepeated() {
    assertEquals(list(12), Entity.getRepeated(map("key", list(12)), "key", Integer.class));
  }

  @Test
  public void testGetRepeatedEntity() {
    TestEntity testEntity = new TestEntity(map("foo", 1));
    assertEquals(list(testEntity), Entity.getRepeated(map("key", list(testEntity.serialize())),
        "key", new TestEntityDeserializer()));
  }

  @Test
  public void testGetEnum() {
    assertEquals(TestEnum.VALUE, Entity.getEnum(map("key", "VALUE"), "key", TestEnum.class));
  }

  @Test
  public void testGetEnumNull() {
    assertNull(Entity.getEnum(map("key", null), "key", TestEnum.class));
  }

  @Test
  public void testPutSerializedNumber() {
    Map<String, Object> testMap = map();
    Entity.putSerialized(testMap, "key", 12);
    assertEquals(map("key", 12), testMap);
  }

  @Test
  public void testPutSerializedNull() {
    Map<String, Object> testMap = map();
    Entity.putSerialized(testMap, "key", (Number)null);
    assertEquals(map(), testMap);
  }

  @Test
  public void testPutSerializedString() {
    Map<String, Object> testMap = map();
    Entity.putSerialized(testMap, "key", "twelve");
    assertEquals(map("key", "twelve"), testMap);
  }

  @Test
  public void testPutSerializedBoolean() {
    Map<String, Object> testMap = map();
    Entity.putSerialized(testMap, "key", true);
    assertEquals(map("key", true), testMap);
  }

  @Test
  public void testPutSerializedEntity() {
    Map<String, Object> testMap = map();
    Entity.putSerialized(testMap, "key", new TestEntity(map("foo", "bar")));
    assertEquals(map("key", map("foo", "bar")), testMap);
  }

  @Test
  public void testPutSerializedEntityNull() {
    Map<String, Object> testMap = map();
    Entity.putSerialized(testMap, "key", (Entity<?>)null);
    assertEquals(map(), testMap);
  }

  @Test
  public void testPutSerializedEnum() {
    Map<String, Object> testMap = map();
    Entity.putSerialized(testMap, "key", TestEnum.VALUE);
    assertEquals(map("key", "VALUE"), testMap);
  }

  @Test
  public void testPutSerializedEnumNull() {
    Map<String, Object> testMap = map();
    Entity.putSerialized(testMap, "key", (Enum<?>)null);
    assertEquals(map(), testMap);
  }

  @Test
  public void testPutSerializedStringList() {
    Map<String, Object> testMap = map();
    Entity.putSerialized(testMap, "key", list("foo"));
    assertEquals(map("key", list("foo")), testMap);
  }

  @Test
  public void testPutSerializedEntityList() {
    Map<String, Object> testMap = map();
    Entity.putSerialized(testMap, "key", list(new TestEntity(map("foo", "bar"))));
    assertEquals(map("key", list(map("foo", "bar"))), testMap);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPutSerializedMixedListExeption() {
    Map<String, Object> testMap = map();
    List<Object> testList = Lists.newArrayList();
    testList.add("foo");
    testList.add(new TestEntity(map("bar", 12)));
    Entity.putSerialized(testMap, "key", testList);
  }

  private Map<String, Object> map() {
    return Maps.newHashMap();
  }

  private Map<String, Object> map(String key, Object value) {
    Map<String, Object> result = Maps.newHashMap();
    result.put(key, value);
    return result;
  }

  private <T> List<T> list(T object) {
    List<T> result = Lists.newArrayList();
    result.add(object);
    return result;
  }
}
