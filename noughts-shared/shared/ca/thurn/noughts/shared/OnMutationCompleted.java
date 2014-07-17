package ca.thurn.noughts.shared;

import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.ExportedFunction;

import com.tinlib.generated.Game;

@ExportedFunction
@ExportPackage("nts")
public interface OnMutationCompleted extends Exportable {
  public void onMutationCompleted(Game game);
}
