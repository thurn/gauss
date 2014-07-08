package ca.thurn.noughts.shared;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;

import ca.thurn.noughts.shared.entities.Game;

@Export
@ExportPackage("nts")
public class AbstractGameListListener implements GameListListener, Exportable {

  @Override
  public void onGameAdded(Game game) {}

  @Override
  public void onGameChanged(Game game) {}

  @Override
  public void onGameRemoved(String gameId) {}

}
