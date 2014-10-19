package com.tinlib.beget;

import com.google.common.collect.ImmutableList;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EntityReaderTest {
  @Mock
  private File mockFile;

  @Test
  public void testReadEmptyEntity() {
    EntityReader.ReadResult readResult = readString(
        "[{" +
            "'type':'entity'," +
            "'name':'TestEntity'," +
            "'package':'com.example'," +
            "'desc':'test entity description'," +
            "'fields': []" +
        "}]"
    );
    Map<String, EntityType> types = readResult.getEntityTypes();
    assertEquals(EntityType.ENTITY, types.get("com.example.TestEntity"));

    EntityInfo entityInfo = readResult.getEntityInformation().iterator().next();
    assertEquals("TestEntity", entityInfo.getName());
    assertEquals("com.example", entityInfo.getPackageString());
    assertEquals("test entity description", entityInfo.getDescription());
    assertEquals(mockFile, entityInfo.getParent());
    assertEquals(0, entityInfo.getFields().size());
    assertEquals(0, entityInfo.getEnumValues().size());
  }

  @Test
  public void testReadEntityWithFields() {
    EntityReader.ReadResult readResult = readString(
        "[{" +
            "'type':'entity'," +
            "'name':'TestEntity'," +
            "'package':'com.example'," +
            "'desc':'test entity description'," +
            "'fields': [" +
                "{'name':'fieldOne', 'type':'TypeOne', 'desc':'field description'}," +
                "{'name':'fieldTwo', 'type': 'Integer', 'repeated': true, 'package':'com.foo'}" +
            "]" +
        "}]"
    );

    EntityInfo entityInfo = readResult.getEntityInformation().iterator().next();
    FieldInfo field1 = entityInfo.getFields().get(0);
    FieldInfo field2 = entityInfo.getFields().get(1);

    assertEquals("fieldOne", field1.getName());
    assertEquals("TypeOne", field1.getType());
    assertEquals("field description", field1.getDescription());

    assertEquals("fieldTwo", field2.getName());
    assertEquals("Integer", field2.getType());
    assertEquals(true, field2.isRepeated());
    assertEquals("com.foo", field2.getPackageString());
  }

  private EntityReader.ReadResult readString(String json) {
    EntityReader entityReader = new EntityReader();
    try {
      return entityReader.read(ImmutableList.of(json), ImmutableList.of(mockFile));
    } catch (JSONException exception) {
      throw new RuntimeException(exception);
    }
  }
}
