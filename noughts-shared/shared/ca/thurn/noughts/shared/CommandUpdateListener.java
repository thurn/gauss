package ca.thurn.noughts.shared;

import ca.thurn.noughts.shared.entities.Action;
import ca.thurn.noughts.shared.entities.Command;
import ca.thurn.noughts.shared.entities.Game;

public interface CommandUpdateListener {
  /**
   * Called with the initial value of the game when the listener is first
   * attached.
   *
   * @param game The initial game value.
   */
  public void onRegistered(Game game);
  
  /**
   * Called whenever commands are added to an action.
   *
   * @param action The action the command was added to.
   * @param command The newly added command.
   */
  public void onCommandAdded(Action action, Command command);
  
  /**
   * Called whenever commands are removed from an action.
   *
   * @param action The action the command was removed from.
   * @param command The removed command.
   */
  public void onCommandRemoved(Action action, Command command);

  /**
   * Called whenever commands are changed in a current action.
   *
   * @param action The action where the command was changed.
   * @param command The changed command.
   */
  public void onCommandChanged(Action action, Command command);
  
  /**
   * Called whenever commands are submitted.
   *
   * @param action The action for the command.
   * @param command The command that was submitted.
   */
  public void onCommandSubmitted(Action action, Command command);
  
  /**
   * Called when the game ends.
   *
   * @param game The final game state.
   */
  public void onGameOver(Game game);
}