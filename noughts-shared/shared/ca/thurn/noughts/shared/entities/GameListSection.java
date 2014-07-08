package ca.thurn.noughts.shared.entities;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;

@Export
@ExportPackage("nts")
public enum GameListSection implements Exportable {
  YOUR_TURN,
  THEIR_TURN,
  GAME_OVER
}
