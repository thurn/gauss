// ================================
// GENERATED CODE -- DO NOT MODIFY!
// ================================

package com.tinlib.generated;

import java.util.*;

import com.tinlib.beget.Entity;

/**
 * A description of a game, to appear in the game list.
 */
public final class GameListEntry extends Entity<GameListEntry> {
  /**
   * Class to create GameListEntry instances from their serialized representation.
   */
  public static final class Deserializer extends EntityDeserializer<GameListEntry> {
    private Deserializer() {
    }

    /**
     * Takes a map (e.g one returned from {@link GameListEntry#serialize()}) and returns a new GameListEntry instance.
     */
    @Override
    public GameListEntry deserialize(Map<String, Object> gameListEntryMap) {
      return new GameListEntry(gameListEntryMap);
    }
  }

  /**
   * Helper utility class to create new GameListEntry instances.
   */
  public static final class Builder extends EntityBuilder<GameListEntry> {
    private final GameListEntry gameListEntry;

    private Builder() {
      this.gameListEntry = new GameListEntry();
    }

    private Builder(GameListEntry gameListEntry) {
      this.gameListEntry = new GameListEntry(gameListEntry);
    }

    /**
     * Returns a new immutable GameListEntry instance based on the current state of this Builder.
     */
    @Override
    public GameListEntry build() {
      return new GameListEntry(gameListEntry);
    }

    @Override
    protected GameListEntry getInternalEntity() {
      return gameListEntry;
    }

    /**
     * Returns true if a value has been set for vsString
     */
    public boolean hasVsString() {
      return gameListEntry.vsString != null;
    }

    /**
     * Gets the value of vsString
     *
     * @return A string describing the players in the game.
     */
    public String getVsString() {
      checkNotNull(gameListEntry.vsString);
      return gameListEntry.vsString;
    }

    /**
     * Sets the value of vsString.
     *
     * @param vsString A string describing the players in the game.
     */
    public Builder setVsString(String vsString) {
      checkNotNull(vsString);
      gameListEntry.vsString = vsString;
      return this;
    }

    /**
     * Unsets the value of vsString.
     */
    public Builder clearVsString() {
      gameListEntry.vsString = null;
      return this;
    }

    /**
     * Returns true if a value has been set for modifiedString
     */
    public boolean hasModifiedString() {
      return gameListEntry.modifiedString != null;
    }

    /**
     * Gets the value of modifiedString
     *
     * @return A string describing when the game was last modified.
     */
    public String getModifiedString() {
      checkNotNull(gameListEntry.modifiedString);
      return gameListEntry.modifiedString;
    }

    /**
     * Sets the value of modifiedString.
     *
     * @param modifiedString A string describing when the game was last modified.
     */
    public Builder setModifiedString(String modifiedString) {
      checkNotNull(modifiedString);
      gameListEntry.modifiedString = modifiedString;
      return this;
    }

    /**
     * Unsets the value of modifiedString.
     */
    public Builder clearModifiedString() {
      gameListEntry.modifiedString = null;
      return this;
    }

    /**
     * Returns the size of the imageStringList
     */
    public int getImageStringCount() {
      return gameListEntry.imageStringList.size();
    }

    /**
     * Returns the imageString at the provided index.
     *
     * @return An image associated with this game list entry.
     */
    public ImageString getImageString(int index) {
      return gameListEntry.imageStringList.get(index);
    }

    /**
     * Returns the imageStringList.
     *
     * Values: An image associated with this game list entry.
     */
    public List<ImageString> getImageStringList() {
      return gameListEntry.imageStringList;
    }

    /**
     * setImageString with a Builder argument
     */
    public Builder setImageString(int index, EntityBuilder<ImageString> imageString) {
      return setImageString(index, imageString.build());
    }

    /**
     * Sets the imageString at the given index.
     *
     * @param imageString An image associated with this game list entry.
     */
    public Builder setImageString(int index, ImageString imageString) {
      checkNotNull(imageString);
      gameListEntry.imageStringList.set(index, imageString);
      return this;
    }

    /**
     * addImageString with a Builder argument
     */
    public Builder addImageString(EntityBuilder<ImageString> imageString) {
      return addImageString(imageString.build());
    }

    /**
     * Adds a new imageString to the end of the imageStringList.
     *
     * @param imageString An image associated with this game list entry.
     */
    public Builder addImageString(ImageString imageString) {
      checkNotNull(imageString);
      gameListEntry.imageStringList.add(imageString);
      return this;
    }

    /**
     * Adds all imageString instances from the provided list to the imageStringList.
     *
     * Values: An image associated with this game list entry.
     */
    public Builder addAllImageString(List<ImageString> imageStringList) {
      checkListForNull(imageStringList);
      gameListEntry.imageStringList.addAll(imageStringList);
      return this;
    }

    /**
     * Removes all values from the imageStringList
     */
    public Builder clearImageStringList() {
      gameListEntry.imageStringList.clear();
      return this;
    }

  }

  /**
   * Returns a new Builder class to help you create GameListEntry instances.
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Returns a new Deserializer class to help you create GameListEntry instances from their serialized form.
   */
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

  /**
   * Returns the name of this entity for use in toString().
   */
  @Override
  public String entityName() {
    return "GameListEntry";
  }

  /**
   * Creates a Map representation of this GameListEntry.
   */
  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<>();
    putSerialized(result, "vsString", vsString);
    putSerialized(result, "modifiedString", modifiedString);
    putSerialized(result, "imageString", imageStringList);
    return result;
  }

  /**
   * Creates a new Builder initialized with the current contents of this GameListEntry.
   */
  @Override
  public Builder toBuilder() {
    return new Builder(this);
  }

  /**
   * Returns true if a value has been set for vsString
   */
  public boolean hasVsString() {
    return vsString != null;
  }

  /**
   * Gets the value of vsString
   *
   * @return A string describing the players in the game.
   */
  public String getVsString() {
    checkNotNull(vsString);
    return vsString;
  }

  /**
   * Returns true if a value has been set for modifiedString
   */
  public boolean hasModifiedString() {
    return modifiedString != null;
  }

  /**
   * Gets the value of modifiedString
   *
   * @return A string describing when the game was last modified.
   */
  public String getModifiedString() {
    checkNotNull(modifiedString);
    return modifiedString;
  }

  /**
   * Returns the size of the imageStringList
   */
  public int getImageStringCount() {
    return imageStringList.size();
  }

  /**
   * Returns the imageString at the provided index.
   *
   * @return An image associated with this game list entry.
   */
  public ImageString getImageString(int index) {
    return imageStringList.get(index);
  }

  /**
   * Returns the imageStringList.
   *
   * Values: An image associated with this game list entry.
   */
  public List<ImageString> getImageStringList() {
    return Collections.unmodifiableList(imageStringList);
  }

}
