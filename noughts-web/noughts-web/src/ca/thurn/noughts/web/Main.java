package ca.thurn.noughts.web;

import org.eclipse.xtext.xbase.lib.Procedures;

import ca.thurn.noughts.shared.Model;

import com.firebase.client.Firebase;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
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
    Firebase firebase = new Firebase("https://incrementer.firebaseio-demo.com");
    final Model model = new Model(firebase);
    button.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        model.increment(new Procedures.Procedure1<Long>() {
          @Override
          public void apply(Long numIncrements) {
            Window.alert(numIncrements + " increments!");
          }
        });
      }
    });
  }
}