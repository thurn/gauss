package ca.thurn.noughts.shared;

import java.util.HashMap;
import java.util.Map;

/**
 * A string which represents an image, either a URL or a local resource.
 */
public class ImageString extends Entity {
  public static enum ImageType {
    LOCAL
  }
  private final String string;
  private final ImageType type;
  
  public static class ImageStringDeserializer extends EntityDeserializer<ImageString> {
    @Override
    ImageString deserialize(Map<String, Object> map) {
      return new ImageString(map);
    }
  }
  
  ImageString(String string, ImageType type) {
    this.string = string;
    this.type = type;
  }
  
  private ImageString(Map<String, Object> map) {
    checkExists(map, "string");
    this.string = getString(map, "string");
    checkExists(map, "type");
    this.type = ImageType.valueOf(getString(map, "type"));
  }

  /**
   * @return The string itself.
   */
  public String getString() {
    return string;
  }

  /**
   * @return The type of resource this string represents.
   */
  public ImageType getType() {
    return type;
  }

  @Override
  Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("string", string);
    result.put("type", type.name());
    return result;
  }

  @Override
  String entityName() {
    return "imageString";
  }
}
