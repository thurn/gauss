package ca.thurn.noughts.shared;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ca.thurn.noughts.shared.entities.AbstractValueEventListener;
import ca.thurn.noughts.shared.entities.ChildListener;
import com.tinlib.generated.Game;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.tinlib.shared.FirebaseReferences;

public class GameList implements ChildEventListener {
  private final FirebaseReferences firebaseReferences;
  private final Map<String, Game> games = new HashMap<String, Game>();
  private final Set<ChildListener<Game>> listeners = new HashSet<ChildListener<Game>>();
  private final String userId;

  GameList(String userId, FirebaseReferences firebaseReferences) {
    this.userId = userId;
    this.firebaseReferences = firebaseReferences;
    firebaseReferences.userGamesReference().addChildEventListener(this);
  }

  /**
   * @return The current {@link GameListPartitions} for this model.
   */
  public GameListPartitions getGameListPartitions() {
    return new GameListPartitions(userId, games.values());
  }

  public Game getGame(String gameId) {
    if (games.containsKey(gameId)) {
      return games.get(gameId);
    } else {
      throw new RuntimeException("Unknown game " + gameId);
    }
  }

  public Unsubscriber addGameListListener(final ChildListener<Game> listener) {
    listeners.add(listener);
    return new Unsubscriber() {
      @Override
      public void unsubscribe() {
        listeners.remove(listener);
      }
    };
  }

  @Override
  public void onCancelled(FirebaseError error) {
    for (ChildListener<Game> childListener : listeners) {
      childListener.onError(error);
    }
  }

  @Override
  public void onChildAdded(DataSnapshot snapshot, final String previous) {
    addGameValueListener(snapshot, new AbstractValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
        Game game = Game.newDeserializer().fromDataSnapshot(snapshot);
        games.put(game.getId(), game);
        for (ChildListener<Game> childListener : listeners) {
          childListener.onChildAdded(game, previous);
        }
      }
    });
  }

  @Override
  public void onChildChanged(DataSnapshot snapshot, final String previous) {
    addGameValueListener(snapshot, new AbstractValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
        Game game = Game.newDeserializer().fromDataSnapshot(snapshot);
        games.put(game.getId(), game);
        for (ChildListener<Game> childListener : listeners) {
          childListener.onChildChanged(game, previous);
        }
      }
    });
  }

  @Override
  public void onChildMoved(DataSnapshot snapshot, final String previous) {
    addGameValueListener(snapshot, new AbstractValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
        Game game = Game.newDeserializer().fromDataSnapshot(snapshot);
        games.put(game.getId(), game);
        for (ChildListener<Game> childListener : listeners) {
          childListener.onChildMoved(game, previous);
        }
      }
    });
  }

  @Override
  public void onChildRemoved(DataSnapshot snapshot) {
    Game game = games.get(snapshot.getName());
    for (ChildListener<Game> childListener : listeners) {
      childListener.onChildRemoved(game);
    }
    games.remove(game.getId());
  }

  private void addGameValueListener(DataSnapshot snapshot, ValueEventListener listener) {
    String gameId = snapshot.getName();
    firebaseReferences.gameReference(gameId).addListenerForSingleValueEvent(listener);
  }
}
