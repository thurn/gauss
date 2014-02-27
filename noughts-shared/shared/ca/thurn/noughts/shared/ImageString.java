package ca.thurn.noughts.shared;

import java.util.HashMap;
import java.util.Map;

/**
 * A string which represents an image, either a URL or a local resource.
 */
public class ImageString extends Entity<ImageString> {
  public static class Deserializer extends EntityDeserializer<ImageString> {
    private Deserializer() {
    }

    @Override
    ImageString deserialize(Map<String, Object> map) {
      return new ImageString(map);
    }
  }  
  
  public static class Builder implements EntityBuilder<ImageString> {
    private final ImageString imageString;
    
    private Builder() {
      this.imageString = new ImageString();
    }
    
    private Builder(ImageString imageString) {
      this.imageString = new ImageString(imageString);
    }
    
    @Override
    public ImageString build() {
      return new ImageString(imageString);
    }

    public boolean hasString() {
      return imageString.hasString();
    }

    public String getString() {
      return imageString.getString();
    }
    
    public Builder setString(String string) {
      checkNotNull(string);
      imageString.string = string;
      return this;
    }
    
    public Builder clearString() {
      imageString.string = null;
      return this;
    }

    public boolean hasImageType() {
      return imageString.hasImageType();
    }

    public ImageType getType() {
      return imageString.getType();
    }
    
    public Builder setType(ImageType type) {
      checkNotNull(type);
      imageString.type = type;
      return this;
    }
    
    public Builder clearType() {
      imageString.type = null;
      return this;
    }
  }
  
  public static Builder newBuilder() {
    return new Builder();
  }
  
  public static Builder newBuilder(ImageString imageString) {
    return new Builder(imageString);
  }
  
  public static Deserializer newDeserializer() {
    return new Deserializer();
  }
  
  private String string;
  private ImageType type;
  
  private ImageString() {
  }
  
  private ImageString(ImageString imageString) {
    this.string = imageString.string;
    this.type = imageString.type;
  }
  
  private ImageString(Map<String, Object> map) {
    this.string = getString(map, "string");
    this.type = getEnum(map, "type", ImageType.class);
  }

  @Override
  String entityName() {
    return "imageString";
  }

  @Override
  Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<String, Object>();
    putSerialized(result, "string", string);
    putSerialized(result, "type", type);
    return result;
  }
  
  @Override
  public Builder toBuilder() {
    return new Builder(this);
  }

  public boolean hasString() {
    return string != null;
  }
  
  public String getString() {
    checkNotNull(string);
    return string;
  }
  
  public boolean hasImageType() {
    return type != null;
  }

  public ImageType getType() {
    checkNotNull(type);
    return type;
  }
}
