package com.tinlib.core;

import com.tinlib.services.*;
import com.tinlib.validator.ActionValidatorService;
import com.tinlib.validator.DefaultActionValidator;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.error.ErrorService;
import com.tinlib.error.PrintlnErrorHandler;
import com.tinlib.error.TrackingErrorHandler;
import com.tinlib.infuse.*;
import com.tinlib.convey.Buses;
import com.tinlib.push.PushNotificationService;
import com.tinlib.time.LastModifiedService;
import com.tinlib.validator.DefaultJoinGameValidator;
import com.tinlib.validator.JoinGameValidatorService;

public class TinModule implements Module {
  @Override
  public void configure(Binder binder) {
    binder.bindSingletonKey(TinKeys2.BUS, Initializers.returnValue(Buses.newBus()));
    binder.bindSingletonKey(TinKeys2.KEYED_LISTENER_SERVICE,
        Initializers.returnValue(new KeyedListenerService()));
    binder.bindSingletonKey(TinKeys2.GAME_MUTATOR, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new GameMutator(injector);
      }
    });
    binder.bindSingletonKey(TinKeys2.PUSH_NOTIFICATION_SERVICE, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new PushNotificationService(injector);
      }
    });
    binder.bindSingletonKey(TinKeys2.ANALYTICS_SERVICE, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new AnalyticsService(injector);
      }
    });
    binder.bindSingletonKey(TinKeys2.ERROR_SERVICE, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new ErrorService(injector);
      }
    });
    binder.multibindClass(TinKeys2.ERROR_HANDLERS, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new TrackingErrorHandler(injector);
      }
    });
    binder.multibindClass(TinKeys2.ERROR_HANDLERS,
        Initializers.returnValue(new PrintlnErrorHandler()));
    binder.bindSingletonKey(TinKeys2.ACTION_VALIDATOR_SERVICE, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new ActionValidatorService(injector);
      }
    });
    binder.multibindClass(TinKeys2.ACTION_VALIDATORS,
        Initializers.returnValue(new DefaultActionValidator()));
    binder.bindSingletonKey(TinKeys2.LAST_MODIFIED_SERVICE, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new LastModifiedService(injector);
      }
    });
    binder.bindSingletonKey(TinKeys2.JOIN_GAME_SERVICE, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new JoinGameService(injector);
      }
    });
    binder.bindSingletonKey(TinKeys2.CURRENT_GAME_SERVICE, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new CurrentGameListener(injector);
      }
    });
    binder.bindSingletonKey(TinKeys2.JOIN_GAME_VALIDATOR_SERVICE, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new JoinGameValidatorService(injector);
      }
    });
    binder.multibindClass(TinKeys2.JOIN_GAME_VALIDATORS,
        Initializers.returnValue(new DefaultJoinGameValidator()));
    binder.bindSingletonKey(TinKeys2.VIEWER_SERVICE, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new ViewerService(injector);
      }
    });
    binder.bindSingletonKey(TinKeys2.COMMAND_LISTENER, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new CommandListener(injector);
      }
    });
    binder.bindSingletonKey(TinKeys2.GAME_OVER_LISTENER, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new GameOverListener(injector);
      }
    });
    binder.bindSingletonKey(TinKeys2.SUBMITTED_ACTION_LISTENER, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new SubmittedActionListener(injector);
      }
    });
    binder.bindSingletonKey(TinKeys2.ADD_COMMAND_SERVICE, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new AddCommandService(injector);
      }
    });
    binder.bindSingletonKey(TinKeys2.SUBMIT_ACTION_SERVICE, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new SubmitActionService(injector);
      }
    });
  }
}
