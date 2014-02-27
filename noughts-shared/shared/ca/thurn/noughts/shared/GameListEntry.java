package ca.thurn.noughts.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a game entry in the game list.
 */
public class GameListEntry extends Entity<GameListEntry> {
  public static class Deserializer extends EntityDeserializer<GameListEntry> {
    private Deserializer() {
    }
    
    @Override
    GameListEntry deserialize(Map<String, Object> map) {
      return new GameListEntry(map);
    }
  }
  
  public static class Builder implements EntityBuilder<GameListEntry> {
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

    public boolean hasVsString() {
      return gameListEntry.hasVsString();
    }

    public String getVsString() {
      return gameListEntry.getVsString();
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
      return gameListEntry.hasModifiedString();
    }

    public String getModifiedString() {
      return gameListEntry.getModifiedString();
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
      return gameListEntry.getImageStringCount();
    }

    public ImageString getImageString(int index) {
      return gameListEntry.getImageString(index);
    }

    public List<ImageString> getImageStringList() {
      return gameListEntry.getImageStringList();
    }
    
    public Builder addImageString(EntityBuilder<ImageString> imageString) {
      return addImageString(imageString.build());
    }
    
    public Builder addImageString(ImageString imageString) {
      checkNotNull(imageString);
      gameListEntry.imageStrings.add(imageString);
      return this;
    }
    
    public Builder addAllImageString(List<ImageString> imageStrings) {
      checkListForNull(imageStrings);
      gameListEntry.imageStrings.addAll(imageStrings);
      return this;
    }
    
    public Builder clearImageStringList() {
      gameListEntry.imageStrings.clear();
      return this;
    }
  }
  
  public static Builder newBuilder() {
    return new Builder();
  }
  
  public static Builder newBuilder(GameListEntry gameListEntry) {
    return new Builder(gameListEntry);
  }
  
  public static Deserializer newDeserializer() {
    return new Deserializer();
  }
  
  private String vsString;
  private String modifiedString;
  private final List<ImageString> imageStrings;
  
  private GameListEntry() {
    this.imageStrings = new ArrayList<ImageString>();
  }
  
  private GameListEntry(GameListEntry gameListEntry) {
    this.vsString = gameListEntry.vsString;
    this.modifiedString = gameListEntry.modifiedString;
    this.imageStrings = gameListEntry.imageStrings;
  }
  
  private GameListEntry(Map<String, Object> map) {
    vsString = getString(map, "vsString");
    modifiedString = getString(map, "modifiedString");
    imageStrings = getEntities(map, "imageStrings", ImageString.newDeserializer());
  }
  
  @Override
  public String entityName() {
    return "GameListEntry";
  }
  
  @Override
  Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<String, Object>();
    putSerialized(result, "vsString", vsString);
    putSerialized(result, "modifiedString", modifiedString);
    putSerialized(result, "imageStrings", imageStrings);
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
    return imageStrings.size();
  }
  
  public ImageString getImageString(int index) {
    return imageStrings.get(index);
  }

  public List<ImageString> getImageStringList() {
    return Collections.unmodifiableList(imageStrings);
  }
}