package com.tinlib.services;

import com.firebase.client.DataSnapshot;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages;
import com.tinlib.error.ErrorService;
import com.tinlib.generated.Action;
import com.tinlib.generated.Command;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus;
import com.tinlib.message.Subscriber3;
import com.tinlib.util.AbstractChildEventListener;

import static com.tinlib.util.Identifiers.id;

public class GameStateListener {
  public static final String ACTIONS_LISTENER_KEY =
      id("GameStateListener.ACTIONS_LISTENER_KEY");
  public static final String COMMANDS_LISTENER_KEY =
      id("GameStateListener.COMMANDS_LISTENER_KEY");
  public static final String FUTURE_COMMANDS_LISTENER_KEY =
      id("GameStateListener.FUTURE_COMMANDS_LISTENER_KEY");

  private class Listener implements Subscriber3<String, FirebaseReferences, String> {
    @Override
    public void onMessage(String viewerId, FirebaseReferences references, String currentGameId) {
      keyedListenerService.addChildEventListener(
          references.gameSubmittedActionsReference(currentGameId),
          ACTIONS_LISTENER_KEY, new ActionListener());
      keyedListenerService.addChildEventListener(
          references.commandsReferenceForCurrentAction(currentGameId),
          COMMANDS_LISTENER_KEY, new CommandListener());
      keyedListenerService.addChildEventListener(
          references.futureCommandsReferenceForCurrentAction(currentGameId),
          FUTURE_COMMANDS_LISTENER_KEY, new FutureCommandListener());
    }
  }

  private class ActionListener extends AbstractChildEventListener {
    private ActionListener() {
      super(errorService, "ActionListener");
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String previous) {
      Action action = Action.newDeserializer().fromDataSnapshot(dataSnapshot);
      bus.produce(TinMessages.ACTION_SUBMITTED, action);
      System.out.println(">>>>>>>>>>>>>> ACTION_SUBMITTED " + action);
      for (Command command : action.getCommandList()) {
        bus.produce(TinMessages.COMMAND_SUBMITTED, command);
        System.out.println(">>>>>>>>>>>>>> COMMAND_SUBMITTED " + command);
      }
    }
  }

  private class CommandListener extends AbstractChildEventListener {
    private CommandListener() {
      super(errorService, "CommandListener");
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String previous) {
      int index = Integer.parseInt(dataSnapshot.getName());
      bus.produce(TinMessages.COMMAND_ADDED,
          Command.newDeserializer().fromDataSnapshot(dataSnapshot));
      System.out.println(">>>>>>>>>>>>>> COMMAND_ADDED " +
          Command.newDeserializer().fromDataSnapshot(dataSnapshot));
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String previous) {
      int index = Integer.parseInt(dataSnapshot.getName());
      bus.produce(TinMessages.COMMAND_CHANGED,
          Command.newDeserializer().fromDataSnapshot(dataSnapshot));
      System.out.println(">>>>>>>>>>>>> COMMAND_CHANGED " +
          Command.newDeserializer().fromDataSnapshot(dataSnapshot));
    }
  }

  private class FutureCommandListener extends AbstractChildEventListener {
    private FutureCommandListener() {
      super(errorService, "FutureCommandListener");
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String previous) {
      int index = Integer.parseInt(dataSnapshot.getName());
      bus.produce(TinMessages.COMMAND_UNDONE,
          Command.newDeserializer().fromDataSnapshot(dataSnapshot));
      System.out.println(">>>>>>>>>>>>>> COMMAND_UNDONE " +
          Command.newDeserializer().fromDataSnapshot(dataSnapshot));
    }
  }

  private final Bus bus;
  private final ErrorService errorService;
  private final KeyedListenerService keyedListenerService;

  public GameStateListener(Injector injector) {
    bus = injector.get(TinKeys.BUS);
    errorService = injector.get(TinKeys.ERROR_SERVICE);
    keyedListenerService = injector.get(TinKeys.KEYED_LISTENER_SERVICE);
    bus.await(TinMessages.VIEWER_ID, TinMessages.FIREBASE_REFERENCES,
        TinMessages.CURRENT_GAME_ID, new Listener());
  }
}
