// ================================
// GENERATED CODE -- DO NOT MODIFY!
// ================================

package com.tinlib.generated;

import java.util.*;

import com.tinlib.entities.Entity;

public final class Profile extends Entity<Profile> {
  public static final class Deserializer extends EntityDeserializer<Profile> {
    private Deserializer() {
    }

    @Override
    public Profile deserialize(Map<String, Object> profileMap) {
      return new Profile(profileMap);
    }
  }

  public static final class Builder extends EntityBuilder<Profile> {
    private final Profile profile;

    private Builder() {
      this.profile = new Profile();
    }

    private Builder(Profile profile) {
      this.profile = new Profile(profile);
    }

    @Override
    public Profile build() {
      return new Profile(profile);
    }

    @Override
    protected Profile getInternalEntity() {
      return profile;
    }

    public boolean hasName() {
      return profile.name != null;
    }

    public String getName() {
      checkNotNull(profile.name);
      return profile.name;
    }

    public Builder setName(String name) {
      checkNotNull(name);
      profile.name = name;
      return this;
    }

    public Builder clearName() {
      profile.name = null;
      return this;
    }

    public boolean hasImageString() {
      return profile.imageString != null;
    }

    public ImageString getImageString() {
      checkNotNull(profile.imageString);
      return profile.imageString;
    }

    public Builder setImageString(EntityBuilder<ImageString> imageString) {
      return setImageString(imageString.build());
    }
    public Builder setImageString(ImageString imageString) {
      checkNotNull(imageString);
      profile.imageString = imageString;
      return this;
    }

    public Builder clearImageString() {
      profile.imageString = null;
      return this;
    }

    public boolean hasPronoun() {
      return profile.pronoun != null;
    }

    public Pronoun getPronoun() {
      checkNotNull(profile.pronoun);
      return profile.pronoun;
    }

    public Builder setPronoun(Pronoun pronoun) {
      checkNotNull(pronoun);
      profile.pronoun = pronoun;
      return this;
    }

    public Builder clearPronoun() {
      profile.pronoun = null;
      return this;
    }

    public boolean hasIsComputerPlayer() {
      return profile.isComputerPlayer != null;
    }

    public boolean getIsComputerPlayer() {
      checkNotNull(profile.isComputerPlayer);
      return profile.isComputerPlayer;
    }

    public Builder setIsComputerPlayer(boolean isComputerPlayer) {
      profile.isComputerPlayer = isComputerPlayer;
      return this;
    }

    public Builder clearIsComputerPlayer() {
      profile.isComputerPlayer = null;
      return this;
    }

    public boolean hasComputerDifficultyLevel() {
      return profile.computerDifficultyLevel != null;
    }

    public int getComputerDifficultyLevel() {
      checkNotNull(profile.computerDifficultyLevel);
      return profile.computerDifficultyLevel;
    }

    public Builder setComputerDifficultyLevel(int computerDifficultyLevel) {
      profile.computerDifficultyLevel = computerDifficultyLevel;
      return this;
    }

    public Builder clearComputerDifficultyLevel() {
      profile.computerDifficultyLevel = null;
      return this;
    }

  }

  public static Builder newBuilder() {
    return new Builder();
  }

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

  @Override
  public String entityName() {
    return "Profile";
  }

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

  @Override
  public Builder toBuilder() {
    return new Builder(this);
  }

  public boolean hasName() {
    return name != null;
  }

  public String getName() {
    checkNotNull(name);
    return name;
  }

  public boolean hasImageString() {
    return imageString != null;
  }

  public ImageString getImageString() {
    checkNotNull(imageString);
    return imageString;
  }

  public boolean hasPronoun() {
    return pronoun != null;
  }

  public Pronoun getPronoun() {
    checkNotNull(pronoun);
    return pronoun;
  }

  public boolean hasIsComputerPlayer() {
    return isComputerPlayer != null;
  }

  public boolean getIsComputerPlayer() {
    checkNotNull(isComputerPlayer);
    return isComputerPlayer;
  }

  public boolean hasComputerDifficultyLevel() {
    return computerDifficultyLevel != null;
  }

  public int getComputerDifficultyLevel() {
    checkNotNull(computerDifficultyLevel);
    return computerDifficultyLevel;
  }

}
