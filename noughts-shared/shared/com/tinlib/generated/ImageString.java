// ================================
// GENERATED CODE -- DO NOT MODIFY!
// ================================

package com.tinlib.generated;

import java.util.*;

import com.tinlib.entities.Entity;

public final class ImageString extends Entity<ImageString> {
  public static final class Deserializer extends EntityDeserializer<ImageString> {
    private Deserializer() {
    }

    @Override
    public ImageString deserialize(Map<String, Object> imageStringMap) {
      return new ImageString(imageStringMap);
    }
  }

  public static final class Builder extends EntityBuilder<ImageString> {
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

    @Override
    protected ImageString getInternalEntity() {
      return imageString;
    }

    public boolean hasString() {
      return imageString.string != null;
    }

    public String getString() {
      checkNotNull(imageString.string);
      return imageString.string;
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

    public boolean hasType() {
      return imageString.type != null;
    }

    public ImageType getType() {
      checkNotNull(imageString.type);
      return imageString.type;
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

  public static Deserializer newDeserializer() {
    return new Deserializer();
  }

  private String string;
  private ImageType type;

  private ImageString() {
  }

  private ImageString(ImageString imageString) {
    string = imageString.string;
    type = imageString.type;
  }

  private ImageString(Map<String, Object> map) {
    string = get(map, "string", String.class);
    type = getEnum(map, "type", ImageType.class);
  }

  @Override
  public String entityName() {
    return "ImageString";
  }

  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<>();
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

  public boolean hasType() {
    return type != null;
  }

  public ImageType getType() {
    checkNotNull(type);
    return type;
  }

}
