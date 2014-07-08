package ca.thurn.uct.core;

import ca.thurn.gwtcompat.client.AsyncOperation;
import ca.thurn.gwtcompat.client.AsyncOperation.OnComplete;
import ca.thurn.gwtcompat.client.AsyncOperation.Task;

public abstract class AsynchronousAgent implements Agent {
  /**
   * Instructs the Agent to kick off an asynchronous action search and then
   * return. The agent will invoke onComplete with the result of the search.
   *
   * @param player The player who this Agent is trying to optimize for.
   * @param rootNode The current state of the game.
   * @param onComplete Function to invoke when the search is finished.
   */
  public void beginAsynchronousSearch(final int player, final State rootNode,
      OnComplete<ActionScore> onComplete) {
    new AsyncOperation<ActionScore>(onComplete, new Task<ActionScore>() {
      @Override
      public ActionScore execute() {
        return pickActionBlocking(player, rootNode);
      }
    });
  }
}
