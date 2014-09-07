package com.tinlib.services;

import com.tinlib.core.TinKeys;
import com.tinlib.core.TinKeys2;
import com.tinlib.generated.Action;
import com.tinlib.generated.Command;
import com.tinlib.generated.IndexCommand;
import com.tinlib.infuse.Injector;
import com.tinlib.convey.Bus;
import com.tinlib.convey.Subscriber1;

public class CommandListener {
  private final Bus bus;
  private Action lastAction;

  public CommandListener(Injector injector) {
    bus = injector.get(TinKeys2.BUS2);
    listen();
  }

  private void listen() {
    bus.await(TinKeys.CURRENT_ACTION, new Subscriber1<Action>() {
      @Override
      public void onMessage(Action newAction) {
        // Actions without a player number are newly-created.
        if (newAction.hasPlayerNumber() && lastAction != null &&
            lastAction.getGameId().equals(newAction.getGameId())) {
          if (lastAction.getCommandCount() > newAction.getCommandCount()) {
            int index = lastAction.getCommandCount() - 1;
            while (index >= newAction.getCommandCount()) {
              bus.post(TinKeys.COMMAND_UNDONE,
                  newIndexCommand(index, lastAction.getCommand(index)));
              index--;
            }
          } else if (lastAction.getCommandCount() < newAction.getCommandCount()) {
            int index = newAction.getCommandCount() - 1;
            while (index >= lastAction.getCommandCount()) {
              bus.post(TinKeys.COMMAND_ADDED,
                  newIndexCommand(index, newAction.getCommand(index)));
              index--;
            }
          }

          int minCount = Math.min(lastAction.getCommandCount(), newAction.getCommandCount());
          for (int i = 0; i < minCount; ++i) {
            if (!lastAction.getCommand(i).equals(newAction.getCommand(i))) {
              bus.post(TinKeys.COMMAND_CHANGED,
                  newIndexCommand(i, newAction.getCommand(i)));
            }
          }
        }
        lastAction = newAction;
      }
    });
  }

  private IndexCommand newIndexCommand(int index, Command command) {
    return IndexCommand.newBuilder().setIndex(index).setCommand(command).build();
  }
}