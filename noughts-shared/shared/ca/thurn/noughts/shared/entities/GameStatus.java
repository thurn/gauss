package ca.thurn.noughts.shared.entities;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the current status of the game.
 */
public class GameStatus extends Entity<GameStatus> {
  public static class Deserializer extends EntityDeserializer<GameStatus> {
    private Deserializer() {
    }
    
    @Override
    public GameStatus deserialize(Map<String, Object> map) {
      return new GameStatus(map);
    }
  }
  
  public static class Builder extends EntityBuilder<GameStatus> {
    private final GameStatus status;
    
    private Builder() {
      this.status = new GameStatus();
    }
    
    private Builder(GameStatus status) {
      this.status = new GameStatus(status);
    }
    
    @Override public GameStatus build() {
      return new GameStatus(status);
    }
    
    @Override protected GameStatus getInternalEntity() {
      return status;
    }

    public boolean hasStatusString() {
      return status.hasStatusString();
    }

    public String getStatusString() {
      return status.getStatusString();
    }
    
    public GameStatus.Builder setStatusString(String statusString) {
      checkNotNull(statusString);
      status.statusString = statusString;
      return this;
    }
    
    public GameStatus.Builder clearStatusString() {
      status.statusString = null;
      return this;
    }

    public boolean hasStatusImageString() {
      return status.hasStatusImageString();
    }

    public ImageString getStatusImageString() {
      return status.getStatusImageString();
    }
    
    public GameStatus.Builder setStatusImageString(ImageString statusImageString) {
      checkNotNull(statusImageString);
      status.statusImageString = statusImageString;
      return this;
    }
    
    public GameStatus.Builder clearStatusImageString() {
      status.statusImageString = null;
      return this;
    }

    public boolean hasStatusPlayer() {
      return status.hasStatusPlayer();
    }

    public int getStatusPlayer() {
      return status.getStatusPlayer();
    }
    
    public GameStatus.Builder setStatusPlayer(int player) {
      status.statusPlayer = player;
      return this;
    }
    
    public GameStatus.Builder clearStatusPlayer() {
      status.statusPlayer = null;
      return this;
    }
    
    public boolean hasIsComputerThinking() {
      return status.hasIsComputerThinking();
    }
    
    public boolean isComputerThinking() {
      return status.isComputerThinking();
    }
    
    public Builder setIsComputerThinking(boolean computerThinking) {
      status.computerThinking = computerThinking;
      return this;
    }
    
    public Builder clearIsComputerThinking() {
      status.computerThinking = null;
      return this;
    }
  }
  
  public static GameStatus.Builder newBuilder() {
    return new Builder();
  }
  
  public static GameStatus.Builder newBuilder(GameStatus status) {
    return new Builder(status);
  }
  
  public static GameStatus.Deserializer newDeserializer() {
    return new Deserializer();
  }
  
  private String statusString;
  private ImageString statusImageString;
  private Integer statusPlayer;
  private Boolean computerThinking;
  
  private GameStatus() {
  }
  
  private GameStatus(GameStatus status) {
    this.statusString = status.statusString;
    this.statusImageString = status.statusImageString;
    this.statusPlayer = status.statusPlayer;
    this.computerThinking = status.computerThinking;
  }
  
  private GameStatus(Map<String, Object> map) {
    statusString = getString(map, "statusString");
    statusImageString = getEntity(map, "statusImageString", ImageString.newDeserializer());
    statusPlayer = getInteger(map, "statusPlayer");
    computerThinking = getBoolean(map, "computerThinking");
  }

  @Override
  public String entityName() {
    return "GameStatus";
  }
  
  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<String, Object>();
    putSerialized(result, "statusString", statusString);
    putSerialized(result, "statusImageString", statusImageString);
    putSerialized(result, "statusPlayer", statusPlayer);
    putSerialized(result, "computerThinking", computerThinking);
    return result;
  }

  @Override
  public GameStatus.Builder toBuilder() {
    return new Builder(this);
  }
  
  public boolean hasStatusString() {
    return statusString != null;
  }

  public String getStatusString() {
    checkNotNull(statusString);
    return statusString;
  }    
  
  public boolean hasStatusImageString() {
    return statusImageString != null;
  }
  
  public ImageString getStatusImageString() {
    checkNotNull(statusImageString);
    return statusImageString;
  }    
  
  public boolean hasStatusPlayer() {
    return statusPlayer != null;
  }

  public int getStatusPlayer() {
    checkNotNull(statusPlayer);
    return statusPlayer;
  }
  
  public boolean hasIsComputerThinking() {
    return computerThinking != null;
  }
  
  public boolean isComputerThinking() {
    checkNotNull(computerThinking);
    return computerThinking;
  }
}