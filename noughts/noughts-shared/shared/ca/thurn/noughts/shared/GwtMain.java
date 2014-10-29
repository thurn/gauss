package ca.thurn.noughts.shared;

import org.timepedia.exporter.client.ExporterUtil;

import com.google.gwt.core.client.EntryPoint;

public class GwtMain implements EntryPoint {
  @Override
  public void onModuleLoad() {
    ExporterUtil.exportAll();
  }
}