package com.tinlib.util;

import com.google.common.collect.Lists;
import com.tinlib.test.TinTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ListUtilTest extends TinTestCase {
  @Test
  public void testAddOrSet() {
    List<String> list = Lists.newArrayList();
    ListUtil.addOrSet(list, 2, "foo", "");
    assertEquals(Lists.newArrayList("", "", "foo"), list);

    ListUtil.addOrSet(list, 3, "bar", "");
    assertEquals(Lists.newArrayList("", "", "foo", "bar"), list);

    ListUtil.addOrSet(list, 2, "baz", "");
    assertEquals(Lists.newArrayList("", "", "baz", "bar"), list);
  }
}
