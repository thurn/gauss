package ca.thurn.noughts.shared;

import java.util.Map;

import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.ExportedInterface;

@ExportedInterface
@ExportPackage("nts")
public interface AnalyticsService extends Exportable {
  public void trackEvent(String name);

  public void trackEventDimensions(String name, Map<String, String> dimensions);
}
