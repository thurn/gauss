package com.tinlib.generated;

import com.tinlib.entities.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a game entry in the game list.
 */
public final class GameListEntryx extends Entity<GameListEntryx> {
  public static class Deserializer extends EntityDeserializer<GameListEntryx> {
    private Deserializer() {
    }

    @Override
    public GameListEntryx deserialize(Map<String, Object> map) {
      return new GameListEntryx(map);
    }
  }

  public static class Builder extends EntityBuilder<GameListEntryx> {
    private final GameListEntryx GameListEntryx;

    private Builder() {
      this.GameListEntryx = new GameListEntryx();
    }

    private Builder(GameListEntryx GameListEntryx) {
      this.GameListEntryx = new GameListEntryx(GameListEntryx);
    }

    @Override
    public GameListEntryx build() {
      return new GameListEntryx(GameListEntryx);
    }

    @Override protected GameListEntryx getInternalEntity() {
      return GameListEntryx;
    }

    public boolean hasVsString() {
      return GameListEntryx.hasVsString();
    }

    public String getVsString() {
      return GameListEntryx.getVsString();
    }

    public Builder setVsString(String vsString) {
      checkNotNull(vsString);
      GameListEntryx.vsString = vsString;
      return this;
    }

    public Builder clearVsString() {
      GameListEntryx.vsString = null;
      return this;
    }

    public boolean hasModifiedString() {
      return GameListEntryx.hasModifiedString();
    }

    public String getModifiedString() {
      return GameListEntryx.getModifiedString();
    }

    public Builder setModifiedString(String modifiedString) {
      checkNotNull(modifiedString);
      GameListEntryx.modifiedString = modifiedString;
      return this;
    }

    public Builder clearModifiedString() {
      GameListEntryx.modifiedString = null;
      return this;
    }

    public int getImageStringCount() {
      return GameListEntryx.getImageStringCount();
    }

    public ImageString getImageString(int index) {
      return GameListEntryx.getImageString(index);
    }

    public List<ImageString> getImageStringList() {
      return GameListEntryx.imageStringList;
    }

    public Builder setImageString(int index, EntityBuilder<ImageString> imageString) {
      return setImageString(index, imageString.build());
    }

    public Builder setImageString(int index, ImageString imageString) {
      checkNotNull(imageString);
      GameListEntryx.imageStringList.set(index, imageString);
      return this;
    }

    public Builder addImageString(EntityBuilder<ImageString> imageString) {
      return addImageString(imageString.build());
    }

    public Builder addImageString(ImageString imageString) {
      checkNotNull(imageString);
      GameListEntryx.imageStringList.add(imageString);
      return this;
    }

    public Builder addAllImageString(List<ImageString> imageStrings) {
      checkListForNull(imageStrings);
      GameListEntryx.imageStringList.addAll(imageStrings);
      return this;
    }

    public Builder clearImageStringList() {
      GameListEntryx.imageStringList.clear();
      return this;
    }
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static Deserializer newDeserializer() {
    return new Deserializer();
  }

  private String vsString;
  private String modifiedString;
  private final List<ImageString> imageStringList;

  private GameListEntryx() {
    this.imageStringList = new ArrayList<ImageString>();
  }

  private GameListEntryx(GameListEntryx GameListEntryx) {
    this.vsString = GameListEntryx.vsString;
    this.modifiedString = GameListEntryx.modifiedString;
    this.imageStringList = GameListEntryx.imageStringList;
  }

  private GameListEntryx(Map<String, Object> map) {
    vsString = getString(map, "vsString");
    modifiedString = getString(map, "modifiedString");
    imageStringList = getEntities(map, "imageStringList", ImageString.newDeserializer());
  }

  @Override
  public String entityName() {
    return "GameListEntryx";
  }

  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<String, Object>();
    putSerialized(result, "vsString", vsString);
    putSerialized(result, "modifiedString", modifiedString);
    putSerialized(result, "imageStringList", imageStringList);
    return result;
  }

  @Override
  public Builder toBuilder() {
    return new Builder(this);
  }

  public boolean hasVsString() {
    return vsString != null;
  }

  public String getVsString() {
    checkNotNull(vsString);
    return vsString;
  }

  public boolean hasModifiedString() {
    return modifiedString != null;
  }

  public String getModifiedString() {
    checkNotNull(modifiedString);
    return modifiedString;
  }

  public int getImageStringCount() {
    return imageStringList.size();
  }

  public ImageString getImageString(int index) {
    return imageStringList.get(index);
  }

  public List<ImageString> getImageStringList() {
    return Collections.unmodifiableList(imageStringList);
  }
}