package com.tinlib.core;

import com.tinlib.action.validator.ActionValidatorService;
import com.tinlib.action.validator.DefaultActionValidator;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.error.ErrorService;
import com.tinlib.error.PrintlnErrorHandler;
import com.tinlib.error.TrackingErrorHandler;
import com.tinlib.inject.*;
import com.tinlib.message.Buses;
import com.tinlib.push.PushNotificationService;
import com.tinlib.shared.GameMutator;
import com.tinlib.shared.KeyedListenerService;

public class TinModule implements Module {
  @Override
  public void configure(Binder binder) {
    binder.bindSingletonKey(TinKeys.BUS,
        Initializers.returnValue(Buses.newBus()));
    binder.bindSingletonKey(TinKeys.KEYED_LISTENER_SERVICE,
        Initializers.returnValue(new KeyedListenerService()));
    binder.bindSingletonKey(TinKeys.GAME_MUTATOR, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new GameMutator(injector);
      }
    });
    binder.bindSingletonKey(TinKeys.PUSH_NOTIFICATION_SERVICE, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new PushNotificationService(injector);
      }
    });
    binder.bindSingletonKey(TinKeys.ANALYTICS_SERVICE, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new AnalyticsService(injector);
      }
    });
    binder.bindSingletonKey(TinKeys.ERROR_SERVICE, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new ErrorService(injector);
      }
    });
    binder.multibindKey(TinKeys.ERROR_HANDLERS, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new TrackingErrorHandler(injector);
      }
    });
    binder.multibindKey(TinKeys.ERROR_HANDLERS,
        Initializers.returnValue(new PrintlnErrorHandler()));
    binder.bindSingletonKey(TinKeys.ACTION_VALIDATOR_SERVICE, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new ActionValidatorService(injector);
      }
    });
    binder.multibindKey(TinKeys.ACTION_VALIDATORS,
        Initializers.returnValue(new DefaultActionValidator()));
  }
}
