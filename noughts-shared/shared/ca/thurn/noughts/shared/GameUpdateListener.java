package ca.thurn.noughts.shared;

import javax.annotation.Nullable;

import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.ExportedInterface;

import com.tinlib.generated.Action;
import com.tinlib.generated.Game;
import com.tinlib.generated.GameStatus;

/**
 * Interface to implement to listen for game updates.
 */
@ExportedInterface
@ExportPackage("nts")
public interface GameUpdateListener extends Exportable {
  /**
   * Called with the new value whenever the game changes.
   *
   * @param game New game state.
   */
  public void onGameUpdate(Game game);

  /**
   * Called with the new value whenever the viewer's current action changes.
   *
   * @param currentAction
   */
  public void onCurrentActionUpdate(Action currentAction);

  /**
   * Called when the "status" of the game changes (game ends, new player's
   * turn, etc)
   *
   * @param status Game status object summarizing change.
   */
  public void onGameStatusChanged(GameStatus status);

  /**
   * Called when the viewer needs to be prompted to enter a profile.
   *
   * @param game The game.
   * @param name Optionally, a suggested name for the viewer.
   */
  public void onProfileRequired(String gameId, @Nullable String name);
}