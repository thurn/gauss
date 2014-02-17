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

  private String name;
  private String photoUrl;
  private Pronoun pronoun;
  
  public Profile() {
    this.pronoun = Pronoun.NEUTRAL;
  }
  
  public Profile(Map<String, Object> map) {
    this.name = getString(map, "name");
    this.photoUrl = getString(map, "photoUrl");
    this.pronoun = Pronoun.valueOf(getString(map, "pronoun"));
  }
  
  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("name", getName());
    result.put("photoUrl", getPhotoUrl());
    result.put("pronoun", getPronoun().name());
    return result;
  }

  @Override
  String entityName() {
    return "Profile";
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }

  public Pronoun getPronoun() {
    return pronoun;
  }

  public void setPronoun(Pronoun pronoun) {
    this.pronoun = pronoun;
  }

}
