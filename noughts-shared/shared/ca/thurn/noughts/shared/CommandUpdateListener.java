package ca.thurn.noughts.shared;

import ca.thurn.noughts.shared.entities.Action;
import ca.thurn.noughts.shared.entities.Command;
import ca.thurn.noughts.shared.entities.Game;

public interface CommandUpdateListener {
  /**
   * Called with the initial value of the game when the listener is first
   * attached.
   *
   * @param viewerId The player ID of the viewer
   * @param game The initial game value.
   * @param currentAction The viewer's currentAction, or null if there is no
   *     current action.
   */
  public void onRegistered(String viewerId, Game game, Action currentAction);

  /**
   * Called when a command is added to the current action.
   *
   * @param action The action the command was added to.
   * @param command The newly added command.
   */
  public void onCommandAdded(Action action, Command command);

  /**
   * Called when a command is removed from the current action.
   *
   * @param action The action the command was removed from.
   * @param command The removed command.
   */
  public void onCommandRemoved(Action action, Command command);

  /**
   * Called when a command in the current action is changed.
   *
   * @param action The action where the command was changed.
   * @param oldCommand The previous value of the command.
   * @param newCommand The new value of the command.
   */
  public void onCommandChanged(Action action, Command oldCommand, Command newCommand);

  /**
   * Called when an action is submitted.
   *
   * @param action The action for the command.
   * @param byViewer True if the action was owned by the viewer.
   */
  public void onActionSubmitted(Action action, boolean byViewer);

  /**
   * Called when the game ends.
   *
   * @param game The final game state.
   */
  public void onGameOver(Game game);
}