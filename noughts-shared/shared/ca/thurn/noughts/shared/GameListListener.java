package ca.thurn.noughts.shared;

import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.ExportedInterface;

import ca.thurn.noughts.shared.entities.Game;

@ExportedInterface
@ExportPackage("nts")
public interface GameListListener extends Exportable {
  public void onGameAdded(Game game);

  public void onGameChanged(Game game);

  public void onGameRemoved(String gameId);
}
