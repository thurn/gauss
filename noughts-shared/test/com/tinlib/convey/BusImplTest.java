package com.tinlib.convey;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class BusImplTest {
  private final Key<Void> voidKey = Keys.createVoidKey("voidKey");
  private final Key<String> stringKey = Keys.createKey(String.class, "stringKey");
  private final Key<Integer> intKey = Keys.createKey(Integer.class, "intKey");
  private AtomicBoolean fired;
  private Bus bus;

  @Before
  public void setUp() {
    fired = new AtomicBoolean(false);
    bus = Buses.newBus();
  }

  @Test
  public void testPostVoid() {
    bus.once(voidKey, new Subscriber0() {
      @Override
      public void onMessage() {
        fired.set(true);
      }
    });
    bus.post(voidKey);
    assertTrue(fired.get());
  }

  @Test
  public void testPostString() {
    bus.once(stringKey, new Subscriber1<String>() {
      @Override
      public void onMessage(String value) {
        assertEquals("value", value);
        fired.set(true);
      }
    });
    bus.post(stringKey, "value");
    assertTrue(fired.get());
  }

  @Test
  public void testProduceString() {
    bus.produce(stringKey, "foo");
    bus.once(stringKey, new Subscriber1<String>() {
      @Override
      public void onMessage(String value) {
        assertEquals("foo", value);
        fired.set(true);
      }
    });
    assertTrue(fired.get());
  }

  @Test
  public void testProduceMultiple() {
    bus.newProduction()
        .addKey(stringKey, "one")
        .addKey(intKey, 2)
        .produce();
    bus.once(stringKey, intKey, new Subscriber2<String, Integer>() {
      @Override
      public void onMessage(String string, Integer integer) {
        assertEquals("one", string);
        assertEquals(2, (int) integer);
        fired.set(true);
      }
    });
    assertTrue(fired.get());
  }

  @Test
  public void testProduceBeforeAfter() {
    bus.produce(stringKey, "aString");
    bus.once(stringKey, intKey, new Subscriber2<String, Integer>() {
      @Override
      public void onMessage(String string, Integer integer) {
        assertEquals("aString", string);
        assertEquals(123, (int)integer);
        fired.set(true);
      }
    });
    bus.produce(intKey, 123);
    assertTrue(fired.get());
  }

  @Test
  public void testAwaitMultiple() {
    bus.once(stringKey, intKey, ImmutableList.<Key<?>>of(voidKey),
        new Subscriber2<String, Integer>() {
      @Override
      public void onMessage(String string, Integer integer) {
        assertEquals("pie", string);
        assertEquals(14, (int) integer);
        fired.set(true);
      }
    });
    bus.produce(intKey, 12);
    bus.post(voidKey);
    bus.produce(intKey, 14);
    bus.produce(stringKey, "pie");
    assertTrue(fired.get());
  }

  @Test
  public void testProduceMap() {
    bus.produce(stringKey, "bar");
    bus.produce(intKey, 456);
    bus.once(ImmutableList.<Key<?>>of(stringKey, intKey), new Subscriber() {
      @Override
      public void onMessage(ImmutableMap<Key<?>, Object> map) {
        assertEquals("bar", map.get(stringKey));
        assertEquals(456, map.get(intKey));
        fired.set(true);
      }
    });
    assertTrue(fired.get());
  }

  @Test
  public void testAwait() {
    final AtomicInteger count = new AtomicInteger(0);
    bus.await(voidKey, new Subscriber0() {
      @Override
      public void onMessage() {
        count.incrementAndGet();
      }
    });
    bus.post(voidKey);
    bus.post(voidKey);
    bus.post(voidKey);

    assertEquals(3, count.get());
  }

  @Test
  public void testDerivedKey() {
    bus.produce(intKey, 123, stringKey);
    bus.produce(stringKey, "james");
    bus.once(intKey, new Subscriber1<Integer>() {
      @Override
      public void onMessage(Integer value) {
        assertEquals(234, (int) value);
        fired.set(true);
      }
    });
    bus.produce(intKey, 234, stringKey);
    assertTrue(fired.get());
  }

  @Test
  public void testIndirectlyDerivedKey() {
    Key<String> stringKey2 = Keys.createKey(String.class, "stringKey2");
    bus.produce(stringKey2, "apple");
    bus.produce(stringKey, "banana", stringKey2);
    bus.produce(intKey, 345, stringKey);
    bus.produce(stringKey2, "carrot");
    bus.once(intKey, new Subscriber1<Integer>() {
      @Override
      public void onMessage(Integer value) {
        assertEquals(543, (int) value);
        fired.set(true);
      }
    });
    bus.produce(intKey, 543, stringKey);
    assertTrue(fired.get());
  }
}
