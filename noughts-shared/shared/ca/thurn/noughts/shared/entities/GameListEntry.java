package ca.thurn.noughts.shared.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

/**
 * Represents a game entry in the game list.
 */
@Export
@ExportPackage("nts")
public final class GameListEntry extends Entity<GameListEntry> implements Exportable {
  @Export
  public static class Deserializer extends EntityDeserializer<GameListEntry> implements Exportable {
    private Deserializer() {
    }

    @Override
    public GameListEntry deserialize(Map<String, Object> map) {
      return new GameListEntry(map);
    }
  }

  @Export
  public static class Builder extends EntityBuilder<GameListEntry> implements Exportable {
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

    @Override protected GameListEntry getInternalEntity() {
      return gameListEntry;
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
      return gameListEntry.imageStringList;
    }

    @NoExport
    public Builder setImageString(int index, EntityBuilder<ImageString> imageString) {
      return setImageString(index, imageString.build());
    }

    public Builder setImageString(int index, ImageString imageString) {
      checkNotNull(imageString);
      gameListEntry.imageStringList.set(index, imageString);
      return this;
    }

    @NoExport
    public Builder addImageString(EntityBuilder<ImageString> imageString) {
      return addImageString(imageString.build());
    }

    public Builder addImageString(ImageString imageString) {
      checkNotNull(imageString);
      gameListEntry.imageStringList.add(imageString);
      return this;
    }

    public Builder addAllImageString(List<ImageString> imageStrings) {
      checkListForNull(imageStrings);
      gameListEntry.imageStringList.addAll(imageStrings);
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
    this.imageStringList = new ArrayList<ImageString>();
  }

  private GameListEntry(GameListEntry gameListEntry) {
    this.vsString = gameListEntry.vsString;
    this.modifiedString = gameListEntry.modifiedString;
    this.imageStringList = gameListEntry.imageStringList;
  }

  private GameListEntry(Map<String, Object> map) {
    vsString = getString(map, "vsString");
    modifiedString = getString(map, "modifiedString");
    imageStringList = getEntities(map, "imageStringList", ImageString.newDeserializer());
  }

  @Override
  public String entityName() {
    return "GameListEntry";
  }

  @Override
  @NoExport
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