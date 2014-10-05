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
    TestEntity testEntity = new TestEntity(map("foo", 1));
    assertNull(Entity.get(map("other", 12), "key", new TestEntityDeserializer()));
  }

  private Map<String, Object> map(String key, Object value) {
    Map<String, Object> result = Maps.newHashMap();
    result.put(key, value);
    return result;
  }
}
