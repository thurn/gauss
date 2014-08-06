package com.tinlib.services;

import ca.thurn.noughts.shared.entities.AbstractChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.tinlib.core.TinKeys;
import com.tinlib.error.ErrorService;
import com.tinlib.generated.Action;
import com.tinlib.generated.Command;
import com.tinlib.generated.IndexCommand;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus;
import com.tinlib.message.Subscriber3;

import static com.tinlib.util.Identifiers.id;

public class GameStateListener {
  public static final String ACTIONS_LISTENER_KEY = id("GameStateListener.ACTIONS_LISTENER_KEY");
  public static final String COMMANDS_LISTENER_KEY = id("GameStateListener.COMMANDS_LISTENER_KEY");

  private class Listener implements Subscriber3<String, FirebaseReferences, String> {
    @Override
    public void onMessage(String viewerId, FirebaseReferences references, String currentGameId) {
      keyedListenerService.addChildEventListener(
          references.gameSubmittedActionsReference(currentGameId),
          ACTIONS_LISTENER_KEY, new ActionListener());
      keyedListenerService.addChildEventListener(
          references.commandsReferenceForCurrentAction(currentGameId),
          COMMANDS_LISTENER_KEY, new CommandListener());
    }
  }

  private class ActionListener extends AbstractChildEventListener {
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String previous) {
      bus.produce("TinMessages.ACTION_ADDED",
          Action.newDeserializer().fromDataSnapshot(dataSnapshot));
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
      errorService.error("ActionListener cancelled. %s", firebaseError);
    }
  }

  private class CommandListener extends AbstractChildEventListener {
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String previous) {
      fireMessage("TinMessages.COMMAND_ADDED", dataSnapshot);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String previous) {
      fireMessage("TinMessages.COMMAND_CHANGED", dataSnapshot);
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
      fireMessage("TinMessages.COMMAND_REMOVED", dataSnapshot);
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
      errorService.error("CommandListener cancelled. %s", firebaseError);
    }

    private void fireMessage(String message, DataSnapshot dataSnapshot) {
      int index = Integer.parseInt(dataSnapshot.getName());
      bus.produce(message, IndexCommand.newBuilder()
          .setIndex(index)
          .setCommand(Command.newDeserializer().fromDataSnapshot(dataSnapshot))
          .build());
    }
  }

  private final Bus bus;
  private final ErrorService errorService;
  private final KeyedListenerService keyedListenerService;

  public GameStateListener(Injector injector) {
    bus = injector.get(TinKeys.BUS);
    errorService = injector.get(TinKeys.ERROR_SERVICE);
    keyedListenerService = injector.get(TinKeys.KEYED_LISTENER_SERVICE);
  }
}
