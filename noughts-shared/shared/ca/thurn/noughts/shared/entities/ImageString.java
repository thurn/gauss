package ca.thurn.noughts.shared.entities;

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
    public ImageString deserialize(Map<String, Object> map) {
      return new ImageString(map);
    }
  }

  public static class Builder extends EntityBuilder<ImageString> {
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

    @Override protected ImageString getInternalEntity() {
      return imageString;
    }

    public boolean hasSmallString() {
      return imageString.hasSmallString();
    }

    public String getSmallString() {
      return imageString.getSmallString();
    }

    public Builder setSmallString(String smallString) {
      checkNotNull(smallString);
      imageString.smallString = smallString;
      return this;
    }

    public Builder clearSmallString() {
      imageString.smallString = null;
      return this;
    }

    public boolean hasMediumString() {
      return imageString.hasMediumString();
    }

    public String getMediumString() {
      return imageString.getMediumString();
    }

    public Builder setMediumString(String string) {
      checkNotNull(string);
      imageString.mediumString = string;
      return this;
    }

    public Builder clearMediumString() {
      imageString.mediumString = null;
      return this;
    }

    public boolean hasLargeString() {
      return imageString.hasLargeString();
    }

    public String getLargeString() {
      return imageString.getLargeString();
    }

    public Builder setLargeString(String string) {
      checkNotNull(string);
      imageString.largeString = string;
      return this;
    }

    public Builder clearLargeString() {
      imageString.largeString = null;
      return this;
    }

    public boolean hasType() {
      return imageString.hasType();
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

  // Small version of the image, 20x20
  private String smallString;

  // Medium version of the image, 40x40
  private String mediumString;

  // Large version of the image, 100x100
  private String largeString;

  // Type of image
  private ImageType type;

  private ImageString() {
  }

  private ImageString(ImageString imageString) {
    this.smallString = imageString.smallString;
    this.mediumString = imageString.mediumString;
    this.largeString = imageString.largeString;
    this.type = imageString.type;
  }

  private ImageString(Map<String, Object> map) {
    this.smallString = getString(map, "smallString");
    this.mediumString = getString(map, "mediumString");
    this.largeString = getString(map, "largeString");
    this.type = getEnum(map, "type", ImageType.class);
  }

  @Override
  public String entityName() {
    return "ImageString";
  }

  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<String, Object>();
    putSerialized(result, "smallString", smallString);
    putSerialized(result, "mediumString", mediumString);
    putSerialized(result, "largeString", largeString);
    putSerialized(result, "type", type);
    return result;
  }

  @Override
  public Builder toBuilder() {
    return new Builder(this);
  }

  public boolean hasSmallString() {
    return smallString != null;
  }

  public String getSmallString() {
    checkNotNull(smallString);
    return smallString;
  }

  public boolean hasMediumString() {
    return mediumString != null;
  }

  public String getMediumString() {
    checkNotNull(mediumString);
    return mediumString;
  }

  public boolean hasLargeString() {
    return largeString != null;
  }

  public String getLargeString() {
    checkNotNull(largeString);
    return largeString;
  }

  public boolean hasType() {
    return type != null;
  }

  public ImageType getType() {
    checkNotNull(type);
    return type;
  }
}
