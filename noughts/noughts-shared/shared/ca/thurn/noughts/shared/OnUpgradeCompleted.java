package ca.thurn.noughts.shared;

import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.ExportedInterface;

@ExportedInterface
@ExportPackage("nts")
public interface OnUpgradeCompleted extends Exportable {
  public void onUpgradeCompleted();

  public void onUpgradeError(String errorMessage);
}
