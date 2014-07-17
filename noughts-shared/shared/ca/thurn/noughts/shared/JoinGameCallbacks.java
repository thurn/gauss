package ca.thurn.noughts.shared;

import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.ExportedInterface;

import com.tinlib.generated.Game;

@ExportedInterface
@ExportPackage("nts")
public interface JoinGameCallbacks extends Exportable {
  /**
   * Called when the user successfully joins a game.
   *
   * @param game Game the user joined.
   */
  public void onJoinedGame(Game game);

  /**
   * Called if there was an error joining the game.
   *
   * @param errorMessage Explanation of the error.
   */
  public void onErrorJoiningGame(String errorMessage);
}
