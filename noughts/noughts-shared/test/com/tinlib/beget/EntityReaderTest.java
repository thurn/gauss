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

  @Test
  public void testReadEnum() {
    EntityReader.ReadResult readResult = readString(
        "[{" +
            "'type':'enum'," +
            "'name':'TestEnum'," +
            "'package':'com.example'," +
            "'values': [" +
                "{'name':'VALUE1', 'desc':'field description'}," +
            "]" +
        "}]"
    );
    assertEquals(EntityType.ENUM, readResult.getEntityTypes().get("com.example.TestEnum"));
    EntityInfo entityInfo = readResult.getEntityInformation().iterator().next();
    assertEquals("TestEnum", entityInfo.getName());
    assertEquals("com.example", entityInfo.getPackageString());
    EnumValueInfo enumValueInfo = entityInfo.getEnumValues().get(0);
    assertEquals("VALUE1", enumValueInfo.getName());
    assertEquals("field description", enumValueInfo.getDescription());
  }

  @Test
  public void testExtendEntity() {
    EntityReader.ReadResult readResult = readString(
        "[{" +
            "'type':'entity_extension'," +
            "'name':'TestEntity'," +
            "'package':'com.example'," +
            "'fields': [" +
                "{'name':'extensionField', 'type':'ExtensionFieldType'}," +
            "]" +
        "},{" +
            "'type':'entity'," +
            "'name':'TestEntity'," +
            "'package':'com.example'," +
            "'desc':'test entity description'," +
            "'fields': []" +
        "}]"
    );
    EntityInfo entityInfo = readResult.getEntityInformation().iterator().next();
    assertEquals("TestEntity", entityInfo.getName());
    assertEquals("com.example", entityInfo.getPackageString());
    FieldInfo fieldInfo = entityInfo.getFields().get(0);
    assertEquals("extensionField", fieldInfo.getName());
    assertEquals("ExtensionFieldType", fieldInfo.getType());
  }

  @Test
  public void testExtendEnum() {
    EntityReader.ReadResult readResult = readString(
        "[{" +
            "'type':'enum_extension'," +
            "'name':'TestEnum'," +
            "'package':'com.example'," +
            "'values': [" +
                "{'name':'EXTENSION_VALUE'}," +
            "]" +
        "},{" +
            "'type':'enum'," +
            "'name':'TestEnum'," +
            "'package':'com.example'," +
            "'desc':'test entity description'," +
            "'values': []" +
        "}]"
    );
    EntityInfo entityInfo = readResult.getEntityInformation().iterator().next();
    assertEquals("TestEnum", entityInfo.getName());
    assertEquals("com.example", entityInfo.getPackageString());
    EnumValueInfo enumValueInfo = entityInfo.getEnumValues().get(0);
    assertEquals("EXTENSION_VALUE", enumValueInfo.getName());
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
