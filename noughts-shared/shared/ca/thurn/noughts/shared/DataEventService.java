package ca.thurn.noughts.shared;

import ca.thurn.noughts.shared.entities.AbstractPreviousValueListener;
import ca.thurn.noughts.shared.entities.AbstractValueListener;
import com.tinlib.generated.Action;
import ca.thurn.noughts.shared.entities.ChildListener;
import ca.thurn.noughts.shared.entities.ChildListenerAdapter;
import com.tinlib.generated.Command;
import com.tinlib.generated.Game;
import com.tinlib.generated.GameStatus;
import ca.thurn.noughts.shared.entities.PreviousValueListener;
import ca.thurn.noughts.shared.entities.PreviousValueListenerAdapter;
import ca.thurn.noughts.shared.entities.ValueListener;
import ca.thurn.noughts.shared.entities.ValueListenerAdapter;

import com.firebase.client.ChildEventListener;
import com.firebase.client.ValueEventListener;
import com.tinlib.shared.FirebaseReferences;

public class DataEventService {
  private final String userId;
  private final FirebaseReferences firebaseReferences;
  private final GameList gameList;

  DataEventService(String userId, FirebaseReferences firebaseReferences, GameList gameList) {
    this.userId = userId;
    this.firebaseReferences = firebaseReferences;
    this.gameList = gameList;
  }

  /**
   * Adds a listener which is invoked when games for the current user are
   * added, changed, or removed.
   *
   * @param listener Listener to add.
   * @return Unsubscriber function to unregister this listener.
   */
  public Unsubscriber addGameListListener(ChildListener<Game> listener) {
    return gameList.addGameListListener(listener);
  }

  /**
   * Adds a listener which is invoked with the value of the game with the
   * provided ID, initially and whenever it changes.
   *
   * @param gameId The game ID.
   * @param listener Listener to add.
   * @return Unsubscriber function to unregister this listener.
   */
  public Unsubscriber addGameValueListener(final String gameId, ValueListener<Game> listener) {
    final ValueEventListener valueListener =
        firebaseReferences.gameReference(gameId).addValueEventListener(
            new ValueListenerAdapter<Game>(Game.newDeserializer(), listener));
    return new Unsubscriber() {
      @Override
      public void unsubscribe() {
        firebaseReferences.gameReference(gameId).removeEventListener(valueListener);
      }
    };
  }

  /**
   * Adds a listener which is invoked when submitted actions for the game with
   * the provided ID are added, changed, or removed.
   *
   * @param gameId The game ID.
   * @param listener Listener to add.
   * @return Unsubscriber function to unregister this listener.
   */
  public Unsubscriber addSubmittedActionListener(final String gameId,
      ChildListener<Action> listener) {
    final ChildEventListener childListener =
        firebaseReferences.gameSubmittedActionsReference(gameId).addChildEventListener(
            new ChildListenerAdapter<Action>(Action.newDeserializer(), listener));
    return new Unsubscriber() {
      @Override
      public void unsubscribe() {
        firebaseReferences.gameSubmittedActionsReference(gameId).removeEventListener(
            childListener);
      }
    };
  }

  /**
   * Adds a listener which is invoked with the value of the user's current
   * action in the provided game, initially and whenever it changes.
   *
   * @param gameId The game ID.
   * @param listener Listener to add.
   * @return Unsubscriber function to unregister this listener.
   */
  public Unsubscriber addCurrentActionValueListener(final String gameId,
      ValueListener<Action> listener) {
    final ValueEventListener valueListener =
        firebaseReferences.currentActionReferenceForGame(gameId).addValueEventListener(
            new ValueListenerAdapter<Action>(Action.newDeserializer(), listener));
    return new Unsubscriber() {
      @Override
      public void unsubscribe() {
        firebaseReferences.currentActionReferenceForGame(gameId).removeEventListener(
            valueListener);
      }
    };
  }

  /**
   * Adds a listener which is invoked when commands are added, changed, or
   * removed for the current action of the game with the provided ID.
   *
   * @param gameId The game ID.
   * @param listener Listener to add.
   * @return Unsubscriber function to unregister this listener.
   */
  public Unsubscriber addCurrentActionCommandListener(final String gameId,
      ChildListener<Command> listener) {
    final ChildEventListener childListener =
        firebaseReferences.commandsReferenceForCurrentAction(gameId).addChildEventListener(
            new ChildListenerAdapter<Command>(Command.newDeserializer(), listener));
    return new Unsubscriber() {
      @Override
      public void unsubscribe() {
        firebaseReferences.commandsReferenceForCurrentAction(gameId).removeEventListener(
            childListener);
      }
    };
  }

  /**
   * Adds a listener which is invoked when the game with the provided ID
   * transitions to the game over state.
   *
   * @param gameId The game ID.
   * @param listener Listener to add.
   * @return Unsubscriber function to unregister this listener.
   */
  public Unsubscriber addGameOverListener(String gameId, final ValueListener<Game> listener) {
    return addGamePreviousValueListener(gameId,
        new AbstractPreviousValueListener<Game>() {
      @Override
      public void onUpdate(Game game, Game oldGame) {
        if (game.isGameOver() && !oldGame.isGameOver()) {
          listener.onUpdate(game);
        }
      }
    });
  }

  /**
   * Adds a listener which is invoked with the status of the game with the
   * provided ID, initially and whenever it changes.
   *
   * @param gameId The game ID.
   * @param listener Listener to add.
   * @return Unsubscriber function to unregister this listener.
   */
  public Unsubscriber addGameStatusListener(String gameId,
      final ValueListener<GameStatus> listener) {
    return addGamePreviousValueListener(gameId,
        new AbstractPreviousValueListener<Game>() {
      @Override
      public void onInitialValue(Game game) {
        listener.onUpdate(Games.gameStatus(game));
      }

      @Override
      public void onUpdate(Game game, Game oldGame) {
        if (Games.differentStatus(oldGame, game)) {
          listener.onUpdate(Games.gameStatus(game));
        }
      }
    });
  }

  /**
   * Adds a listener which is invoked initially and whenever the game with the
   * provided ID changes if the current user does not have a complete profile
   * in this game.
   *
   * @param gameId The game ID.
   * @param listener Listener to add.
   * @return Unsubscriber function to unregister this listener.
   */
  public Unsubscriber addProfileRequiredListener(final String gameId,
      final ValueListener<Game> listener) {
    final ValueEventListener valueListener =
        firebaseReferences.gameReference(gameId).addValueEventListener(
            new ValueListenerAdapter<Game>(Game.newDeserializer(),
                new AbstractValueListener<Game>() {
                  @Override
                  public void onUpdate(Game game) {
                    if (Games.profileRequired(game, userId)) {
                      listener.onUpdate(game);
                    }
                  }
            }));
    return new Unsubscriber() {
      @Override
      public void unsubscribe() {
        firebaseReferences.gameReference(gameId).removeEventListener(valueListener);
      }
    };
  }

  /**
   * Adds a PreviousValueListener to the game with the provided ID
   */
  private Unsubscriber addGamePreviousValueListener(final String gameId,
      PreviousValueListener<Game> previousValueListener) {
    final ValueEventListener valueListener =
        firebaseReferences.gameReference(gameId).addValueEventListener(
            new PreviousValueListenerAdapter<Game>(
                Game.newDeserializer(),
                previousValueListener));
    return new Unsubscriber() {
      @Override
      public void unsubscribe() {
        firebaseReferences.gameReference(gameId).removeEventListener(
            valueListener);
      }
    };
  }
}
