package ca.thurn.noughts.web;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;


public class Main implements EntryPoint {

  @Override
  public void onModuleLoad() {
    ScriptInjector.fromUrl("http://cdn.firebase.com/v0/firebase.js")
    .setCallback(new Callback<Void,Exception>(){
      @Override
      public void onFailure(Exception reason) {
      }
      @Override
      public void onSuccess(Void result) {
        onScriptLoaded();
      }
    })
    .inject();
  }

  public void onScriptLoaded() {
    Button button = new Button("Increment!");
    RootPanel.get().add(button);
  }
}