package ca.thurn.noughts.shared;

import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.ExportedFunction;

/**
 * An Unsubscriber is a function you invoke to unregister a listener. When listeners are added,
 * they usually return an Unsubscriber so you can later unregister.
 */
@ExportedFunction
@ExportPackage("nts")
public interface Unsubscriber extends Exportable {
  public void unsubscribe();
}
