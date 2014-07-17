// ================================
// GENERATED CODE -- DO NOT MODIFY!
// ================================

package com.tinlib.generated;

import java.util.*;

import com.tinlib.entities.Entity;

public final class GameListEntry extends Entity<GameListEntry> {
  public static final class Deserializer extends EntityDeserializer<GameListEntry> {
    private Deserializer() {
    }

    @Override
    public GameListEntry deserialize(Map<String, Object> gameListEntryMap) {
      return new GameListEntry(gameListEntryMap);
    }
  }

  public static final class Builder extends EntityBuilder<GameListEntry> {
    private final GameListEntry gameListEntry;

    private Builder() {
      this.gameListEntry = new GameListEntry();
    }

    private Builder(GameListEntry gameListEntry) {
      this.gameListEntry = new GameListEntry(gameListEntry);
    }

    @Override
    public GameListEntry build() {
      return new GameListEntry(gameListEntry);
    }

    @Override
    protected GameListEntry getInternalEntity() {
      return gameListEntry;
    }

    public boolean hasVsString() {
      return gameListEntry.vsString != null;
    }

    public String getVsString() {
      checkNotNull(gameListEntry.vsString);
      return gameListEntry.vsString;
    }

    public Builder setVsString(String vsString) {
      checkNotNull(vsString);
      gameListEntry.vsString = vsString;
      return this;
    }

    public Builder clearVsString() {
      gameListEntry.vsString = null;
      return this;
    }

    public boolean hasModifiedString() {
      return gameListEntry.modifiedString != null;
    }

    public String getModifiedString() {
      checkNotNull(gameListEntry.modifiedString);
      return gameListEntry.modifiedString;
    }

    public Builder setModifiedString(String modifiedString) {
      checkNotNull(modifiedString);
      gameListEntry.modifiedString = modifiedString;
      return this;
    }

    public Builder clearModifiedString() {
      gameListEntry.modifiedString = null;
      return this;
    }

    public int getImageStringCount() {
      return gameListEntry.imageStringList.size();
    }

    public ImageString getImageString(int index) {
      return gameListEntry.imageStringList.get(index);
    }

    public List<ImageString> getImageStringList() {
      return Collections.unmodifiableList(gameListEntry.imageStringList);
    }

    public Builder setImageString(int index, EntityBuilder<ImageString> imageString) {
      return setImageString(index, imageString.build());
    }

    public Builder setImageString(int index, ImageString imageString) {
      checkNotNull(imageString);
      gameListEntry.imageStringList.set(index, imageString);
      return this;
    }

    public Builder addImageString(EntityBuilder<ImageString> imageString) {
      return addImageString(imageString.build());
    }

    public Builder addImageString(ImageString imageString) {
      checkNotNull(imageString);
      gameListEntry.imageStringList.add(imageString);
      return this;
    }

    public Builder addAllImageString(List<ImageString> imageStringList) {
      checkListForNull(imageStringList);
      gameListEntry.imageStringList.addAll(imageStringList);
      return this;
    }

    public Builder clearImageStringList() {
      gameListEntry.imageStringList.clear();
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

  private GameListEntry() {
    imageStringList = new ArrayList<>();
  }

  private GameListEntry(GameListEntry gameListEntry) {
    vsString = gameListEntry.vsString;
    modifiedString = gameListEntry.modifiedString;
    imageStringList = new ArrayList<>(gameListEntry.imageStringList);
  }

  private GameListEntry(Map<String, Object> map) {
    vsString = get(map, "vsString", String.class);
    modifiedString = get(map, "modifiedString", String.class);
    imageStringList = getRepeated(map, "imageString", ImageString.newDeserializer());
  }

  @Override
  public String entityName() {
    return "GameListEntry";
  }

  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<>();
    putSerialized(result, "vsString", vsString);
    putSerialized(result, "modifiedString", modifiedString);
    putSerialized(result, "imageString", imageStringList);
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
