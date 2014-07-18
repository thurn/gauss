// ================================
// GENERATED CODE -- DO NOT MODIFY!
// ================================

package com.tinlib.generated;

import java.util.*;

import com.tinlib.entities.Entity;

/**
 * Description of a player in a game.
 */
public final class Profile extends Entity<Profile> {
  /**
   * Class to create Profile instances from their serialized representation.
   */
  public static final class Deserializer extends EntityDeserializer<Profile> {
    private Deserializer() {
    }

    /**
     * Takes a map (e.g one returned from {@link Profile#serialize()}) and returns a new Profile instance.
     */
    @Override
    public Profile deserialize(Map<String, Object> profileMap) {
      return new Profile(profileMap);
    }
  }

  /**
   * Helper utility class to create new Profile instances.
   */
  public static final class Builder extends EntityBuilder<Profile> {
    private final Profile profile;

    private Builder() {
      this.profile = new Profile();
    }

    private Builder(Profile profile) {
      this.profile = new Profile(profile);
    }

    /**
     * Returns a new immutable Profile instance based on the current state of this Builder.
     */
    @Override
    public Profile build() {
      return new Profile(profile);
    }

    @Override
    protected Profile getInternalEntity() {
      return profile;
    }

    /**
     * Returns true if a value has been set for name
     */
    public boolean hasName() {
      return profile.name != null;
    }

    /**
     * Gets the value of name
     *
     * @return The player's name.
     */
    public String getName() {
      checkNotNull(profile.name);
      return profile.name;
    }

    /**
     * Sets the value of name.
     *
     * @param name The player's name.
     */
    public Builder setName(String name) {
      checkNotNull(name);
      profile.name = name;
      return this;
    }

    /**
     * Unsets the value of name.
     */
    public Builder clearName() {
      profile.name = null;
      return this;
    }

    /**
     * Returns true if a value has been set for imageString
     */
    public boolean hasImageString() {
      return profile.imageString != null;
    }

    /**
     * Gets the value of imageString
     *
     * @return An image representing the player.
     */
    public ImageString getImageString() {
      checkNotNull(profile.imageString);
      return profile.imageString;
    }

    /**
     * setImageString with a Builder argument
     */
    public Builder setImageString(EntityBuilder<ImageString> imageString) {
      return setImageString(imageString.build());
    }
    /**
     * Sets the value of imageString.
     *
     * @param imageString An image representing the player.
     */
    public Builder setImageString(ImageString imageString) {
      checkNotNull(imageString);
      profile.imageString = imageString;
      return this;
    }

    /**
     * Unsets the value of imageString.
     */
    public Builder clearImageString() {
      profile.imageString = null;
      return this;
    }

    /**
     * Returns true if a value has been set for pronoun
     */
    public boolean hasPronoun() {
      return profile.pronoun != null;
    }

    /**
     * Gets the value of pronoun
     *
     * @return The pronoun to use to refer to this player.
     */
    public Pronoun getPronoun() {
      checkNotNull(profile.pronoun);
      return profile.pronoun;
    }

    /**
     * Sets the value of pronoun.
     *
     * @param pronoun The pronoun to use to refer to this player.
     */
    public Builder setPronoun(Pronoun pronoun) {
      checkNotNull(pronoun);
      profile.pronoun = pronoun;
      return this;
    }

    /**
     * Unsets the value of pronoun.
     */
    public Builder clearPronoun() {
      profile.pronoun = null;
      return this;
    }

    /**
     * Returns true if a value has been set for isComputerPlayer
     */
    public boolean hasIsComputerPlayer() {
      return profile.isComputerPlayer != null;
    }

    /**
     * Gets the value of isComputerPlayer
     *
     * @return True if the player is a computer player.
     */
    public boolean getIsComputerPlayer() {
      checkNotNull(profile.isComputerPlayer);
      return profile.isComputerPlayer;
    }

    /**
     * Sets the value of isComputerPlayer.
     *
     * @param isComputerPlayer True if the player is a computer player.
     */
    public Builder setIsComputerPlayer(boolean isComputerPlayer) {
      profile.isComputerPlayer = isComputerPlayer;
      return this;
    }

    /**
     * Unsets the value of isComputerPlayer.
     */
    public Builder clearIsComputerPlayer() {
      profile.isComputerPlayer = null;
      return this;
    }

    /**
     * Returns true if a value has been set for computerDifficultyLevel
     */
    public boolean hasComputerDifficultyLevel() {
      return profile.computerDifficultyLevel != null;
    }

    /**
     * Gets the value of computerDifficultyLevel
     *
     * @return The difficulty level of this computer player.
     */
    public int getComputerDifficultyLevel() {
      checkNotNull(profile.computerDifficultyLevel);
      return profile.computerDifficultyLevel;
    }

    /**
     * Sets the value of computerDifficultyLevel.
     *
     * @param computerDifficultyLevel The difficulty level of this computer player.
     */
    public Builder setComputerDifficultyLevel(int computerDifficultyLevel) {
      profile.computerDifficultyLevel = computerDifficultyLevel;
      return this;
    }

    /**
     * Unsets the value of computerDifficultyLevel.
     */
    public Builder clearComputerDifficultyLevel() {
      profile.computerDifficultyLevel = null;
      return this;
    }

  }

  /**
   * Returns a new Builder class to help you create Profile instances.
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Returns a new Deserializer class to help you create Profile instances from their serialized form.
   */
  public static Deserializer newDeserializer() {
    return new Deserializer();
  }

  private String name;
  private ImageString imageString;
  private Pronoun pronoun;
  private Boolean isComputerPlayer;
  private Integer computerDifficultyLevel;

  private Profile() {
  }

  private Profile(Profile profile) {
    name = profile.name;
    imageString = profile.imageString;
    pronoun = profile.pronoun;
    isComputerPlayer = profile.isComputerPlayer;
    computerDifficultyLevel = profile.computerDifficultyLevel;
  }

  private Profile(Map<String, Object> map) {
    name = get(map, "name", String.class);
    imageString = get(map, "imageString", ImageString.newDeserializer());
    pronoun = getEnum(map, "pronoun", Pronoun.class);
    isComputerPlayer = get(map, "isComputerPlayer", Boolean.class);
    computerDifficultyLevel = get(map, "computerDifficultyLevel", Integer.class);
  }

  /**
   * Returns the name of this entity for use in toString().
   */
  @Override
  public String entityName() {
    return "Profile";
  }

  /**
   * Creates a Map representation of this Profile.
   */
  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<>();
    putSerialized(result, "name", name);
    putSerialized(result, "imageString", imageString);
    putSerialized(result, "pronoun", pronoun);
    putSerialized(result, "isComputerPlayer", isComputerPlayer);
    putSerialized(result, "computerDifficultyLevel", computerDifficultyLevel);
    return result;
  }

  /**
   * Creates a new Builder initialized with the current contents of this Profile.
   */
  @Override
  public Builder toBuilder() {
    return new Builder(this);
  }

  /**
   * Returns true if a value has been set for name
   */
  public boolean hasName() {
    return name != null;
  }

  /**
   * Gets the value of name
   *
   * @return The player's name.
   */
  public String getName() {
    checkNotNull(name);
    return name;
  }

  /**
   * Returns true if a value has been set for imageString
   */
  public boolean hasImageString() {
    return imageString != null;
  }

  /**
   * Gets the value of imageString
   *
   * @return An image representing the player.
   */
  public ImageString getImageString() {
    checkNotNull(imageString);
    return imageString;
  }

  /**
   * Returns true if a value has been set for pronoun
   */
  public boolean hasPronoun() {
    return pronoun != null;
  }

  /**
   * Gets the value of pronoun
   *
   * @return The pronoun to use to refer to this player.
   */
  public Pronoun getPronoun() {
    checkNotNull(pronoun);
    return pronoun;
  }

  /**
   * Returns true if a value has been set for isComputerPlayer
   */
  public boolean hasIsComputerPlayer() {
    return isComputerPlayer != null;
  }

  /**
   * Gets the value of isComputerPlayer
   *
   * @return True if the player is a computer player.
   */
  public boolean getIsComputerPlayer() {
    checkNotNull(isComputerPlayer);
    return isComputerPlayer;
  }

  /**
   * Returns true if a value has been set for computerDifficultyLevel
   */
  public boolean hasComputerDifficultyLevel() {
    return computerDifficultyLevel != null;
  }

  /**
   * Gets the value of computerDifficultyLevel
   *
   * @return The difficulty level of this computer player.
   */
  public int getComputerDifficultyLevel() {
    checkNotNull(computerDifficultyLevel);
    return computerDifficultyLevel;
  }

}
