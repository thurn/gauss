package ca.thurn.noughts.shared;

import java.util.HashMap;
import java.util.Map;

public class Profile extends Entity<Profile> {
  public static class Deserializer extends EntityDeserializer<Profile> {
    private Deserializer() {
    }

    @Override
    Profile deserialize(Map<String, Object> profileMap) {
      return new Profile(profileMap);
    }
  }
  
  public static class Builder implements EntityBuilder<Profile> {
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

    public boolean hasName() {
      return profile.hasName();
    }

    public String getName() {
      return profile.getName();
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
      return profile.hasImageString();
    }

    public ImageString getImageString() {
      return profile.getImageString();
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
      return profile.hasPronoun();
    }

    public Pronoun getPronoun() {
      return profile.getPronoun();
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

    public boolean hasComputerPlayer() {
      return profile.hasComputerPlayer();
    }

    public boolean isComputerPlayer() {
      return profile.isComputerPlayer();
    }
    
    public Builder setComputerPlayer(boolean isComputerPlayer) {
      profile.computerPlayer = isComputerPlayer;
      return this;
    }
    
    public Builder clearComputerPlayer() {
      profile.computerPlayer = null;
      return this;
    }

    public boolean hasComputerDifficultyLevel() {
      return profile.hasComputerDifficultyLevel();
    }

    public int getComputerDifficultyLevel() {
      return profile.getComputerDifficultyLevel();
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
  
  public static Builder newBuilder(Profile profile) {
    return new Builder(profile);
  }
  
  public static Deserializer newDeserializer() {
    return new Deserializer();
  }

  private String name;
  private ImageString imageString;
  private Pronoun pronoun;
  private Boolean computerPlayer;
  private Integer computerDifficultyLevel;
  
  private Profile() {
  }
  
  private Profile(Profile profile) {
    this.name = profile.name;
    this.imageString = profile.imageString;
    this.pronoun = profile.pronoun;
    this.computerPlayer = profile.computerPlayer;
    this.computerDifficultyLevel = profile.computerDifficultyLevel;
  }
  
  @Override
  String entityName() {
    return "Profile";
  }  
  
  private Profile(Map<String, Object> map) {
    this.name = getString(map, "name");
    this.imageString = getEntity(map, "imageString", ImageString.newDeserializer());
    this.pronoun = getEnum(map, "pronoun", Pronoun.class);
    this.computerPlayer = getBoolean(map, "isComputerPlayer");
    this.computerDifficultyLevel = getInteger(map, "computerDifficultyLevel");
  }
  
  @Override
  Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<String, Object>();
    putSerialized(result, "name", name);
    putSerialized(result, "imageString", imageString);
    putSerialized(result, "pronoun", pronoun);
    putSerialized(result, "isComputerPlayer", computerPlayer);
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

  public boolean hasComputerPlayer() {
    return computerPlayer != null;
  }
  
  public boolean isComputerPlayer() {
    checkNotNull(computerPlayer);
    return computerPlayer;
  }

  public boolean hasComputerDifficultyLevel() {
    return computerDifficultyLevel != null;
  }
  
  public int getComputerDifficultyLevel() {
    checkNotNull(computerDifficultyLevel);
    return computerDifficultyLevel;
  }
}
