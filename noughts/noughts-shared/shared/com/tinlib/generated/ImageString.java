// ================================
// GENERATED CODE -- DO NOT MODIFY!
// ================================

package com.tinlib.generated;

import java.util.*;

import com.tinlib.beget.Entity;

/**
 * The data needed to display an image.
 */
public final class ImageString extends Entity<ImageString> {
  /**
   * Class to create ImageString instances from their serialized representation.
   */
  public static final class Deserializer extends EntityDeserializer<ImageString> {
    private Deserializer() {
    }

    /**
     * Takes a map (e.g one returned from {@link ImageString#serialize()}) and returns a new ImageString instance.
     */
    @Override
    public ImageString deserialize(Map<String, Object> imageStringMap) {
      return new ImageString(imageStringMap);
    }
  }

  /**
   * Helper utility class to create new ImageString instances.
   */
  public static final class Builder extends EntityBuilder<ImageString> {
    private final ImageString imageString;

    private Builder() {
      this.imageString = new ImageString();
    }

    private Builder(ImageString imageString) {
      this.imageString = new ImageString(imageString);
    }

    /**
     * Returns a new immutable ImageString instance based on the current state of this Builder.
     */
    @Override
    public ImageString build() {
      return new ImageString(imageString);
    }

    @Override
    protected ImageString getInternalEntity() {
      return imageString;
    }

    /**
     * Returns true if a value has been set for string
     */
    public boolean hasString() {
      return imageString.string != null;
    }

    /**
     * Gets the value of string
     *
     * @return A string identifying an image.
     */
    public String getString() {
      checkNotNull(imageString.string);
      return imageString.string;
    }

    /**
     * Sets the value of string.
     *
     * @param string A string identifying an image.
     */
    public Builder setString(String string) {
      checkNotNull(string);
      imageString.string = string;
      return this;
    }

    /**
     * Unsets the value of string.
     */
    public Builder clearString() {
      imageString.string = null;
      return this;
    }

    /**
     * Returns true if a value has been set for type
     */
    public boolean hasType() {
      return imageString.type != null;
    }

    /**
     * Gets the value of type
     *
     * @return The type of this ImageString.
     */
    public ImageType getType() {
      checkNotNull(imageString.type);
      return imageString.type;
    }

    /**
     * Sets the value of type.
     *
     * @param type The type of this ImageString.
     */
    public Builder setType(ImageType type) {
      checkNotNull(type);
      imageString.type = type;
      return this;
    }

    /**
     * Unsets the value of type.
     */
    public Builder clearType() {
      imageString.type = null;
      return this;
    }

  }

  /**
   * Returns a new Builder class to help you create ImageString instances.
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Returns a new Deserializer class to help you create ImageString instances from their serialized form.
   */
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

  /**
   * Returns the name of this entity for use in toString().
   */
  @Override
  public String entityName() {
    return "ImageString";
  }

  /**
   * Creates a Map representation of this ImageString.
   */
  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<>();
    putSerialized(result, "string", string);
    putSerialized(result, "type", type);
    return result;
  }

  /**
   * Creates a new Builder initialized with the current contents of this ImageString.
   */
  @Override
  public Builder toBuilder() {
    return new Builder(this);
  }

  /**
   * Returns true if a value has been set for string
   */
  public boolean hasString() {
    return string != null;
  }

  /**
   * Gets the value of string
   *
   * @return A string identifying an image.
   */
  public String getString() {
    checkNotNull(string);
    return string;
  }

  /**
   * Returns true if a value has been set for type
   */
  public boolean hasType() {
    return type != null;
  }

  /**
   * Gets the value of type
   *
   * @return The type of this ImageString.
   */
  public ImageType getType() {
    checkNotNull(type);
    return type;
  }

}
