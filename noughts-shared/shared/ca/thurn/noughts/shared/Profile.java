package ca.thurn.noughts.shared;

import java.util.HashMap;
import java.util.Map;

public class Profile extends Entity {
  public static class ProfileDeserializer extends EntityDeserializer<Profile> {
    @Override
    public Profile deserialize(Map<String, Object> map) {
      return new Profile(map);
    }
  }
  
  public static enum Pronoun {
    MALE,
    FEMALE,
    NEUTRAL
  }

  private final String name;
  private String photoString;
  private Pronoun pronoun;
  private boolean isComputerPlayer;
  private Integer computerDifficultyLevel;
  
  public Profile(String name) {
    this.pronoun = Pronoun.NEUTRAL;
    this.name = name;
  }
  
  public Profile(Map<String, Object> map) {
    this.name = getString(map, "name");
    this.photoString = getString(map, "photoString");
    this.pronoun = Pronoun.valueOf(getString(map, "pronoun"));
    this.isComputerPlayer = getBoolean(map, "isComputerPlayer");
    this.computerDifficultyLevel = getInteger(map, "computerDifficultyLevel");
  }
  
  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("name", getName());
    result.put("photoString", getPhotoString());
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

  public String getPhotoString() {
    return photoString;
  }

  public Profile setPhotoString(String photoString) {
    this.photoString = photoString;
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
