package ca.thurn.noughts.shared;

import java.util.HashMap;
import java.util.Map;

public class Profile extends Entity {
  public static class ProfileDeserializer extends EntityDeserializer<Profile> {
    @Override
    Profile deserialize(Map<String, Object> profileMap) {
      return new Profile(profileMap);
    }
  }
  
  public static enum Pronoun {
    MALE,
    FEMALE,
    NEUTRAL
  }

  private final String name;
  private ImageString imageString;
  private Pronoun pronoun;
  private boolean isComputerPlayer;
  private Integer computerDifficultyLevel;
  
  public Profile(String name) {
    this.pronoun = Pronoun.NEUTRAL;
    this.name = name;
  }
  
  private Profile(Map<String, Object> map) {
    checkExists(map, "name");
    this.name = getString(map, "name");
    this.imageString = getEntity(map, "imageString", new ImageString.ImageStringDeserializer());
    this.pronoun = Pronoun.valueOf(getString(map, "pronoun"));
    this.isComputerPlayer = getBoolean(map, "isComputerPlayer");
    this.computerDifficultyLevel = getInteger(map, "computerDifficultyLevel");
  }
  
  @Override
  Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("name", getName());
    if (imageString != null) {
      result.put("imageString", getImageString().serialize());
    }
    result.put("pronoun", getPronoun().name());
    result.put("isComputerPlayer", isComputerPlayer());
    result.put("computerDifficultyLevel", getComputerDifficultyLevel());
    return result;
  }

  @Override
  String entityName() {
    return "Profile";
  }

  public String getName() {
    return name;
  }

  public ImageString getImageString() {
    return imageString;
  }

  public Profile setImageString(ImageString imageString) {
    this.imageString = imageString;
    return this;
  }

  public Pronoun getPronoun() {
    return pronoun;
  }
  
  /**
   * @param capitalize Whether to capitalize the word.
   * @return The correct singular nominative pronoun for this user.
   */
  public String getNominativePronoun(boolean capitalize) {
    switch (pronoun) {
      case MALE: {
        return capitalize ? "He" : "he";
      }
      case FEMALE: {
        return capitalize ? "She" : "she";
      }
      default: {
        return capitalize ? "They" : "they";
      }
    }
  }
  
  /**
   * @param capitalize Whether to capitalize the word.
   * @return The correct singular objective pronoun for this user.
   */  
  public String getObjectivePronoun(boolean capitalize) {
    switch (pronoun) {
      case MALE: {
        return capitalize ? "Him" : "him";
      }
      case FEMALE: {
        return capitalize ? "Her" : "her";
      }
      default: {
        return capitalize ? "Them" : "them";
      }
    }
  }

  public Profile setPronoun(Pronoun pronoun) {
    this.pronoun = pronoun;
    return this;
  }

  public boolean isComputerPlayer() {
    return isComputerPlayer;
  }

  public Profile setIsComputerPlayer(boolean isComputerPlayer) {
    this.isComputerPlayer = isComputerPlayer;
    return this;
  }

  public Integer getComputerDifficultyLevel() {
    return computerDifficultyLevel;
  }

  public Profile setComputerDifficultyLevel(int computerDifficultyLevel) {
    this.computerDifficultyLevel = computerDifficultyLevel;
    return this;
  }

}
