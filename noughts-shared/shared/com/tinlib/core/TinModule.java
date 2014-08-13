package com.tinlib.core;

import com.tinlib.services.*;
import com.tinlib.validator.ActionValidatorService;
import com.tinlib.validator.DefaultActionValidator;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.error.ErrorService;
import com.tinlib.error.PrintlnErrorHandler;
import com.tinlib.error.TrackingErrorHandler;
import com.tinlib.inject.*;
import com.tinlib.message.Buses;
import com.tinlib.push.PushNotificationService;
import com.tinlib.time.LastModifiedService;
import com.tinlib.validator.DefaultJoinGameValidator;
import com.tinlib.validator.JoinGameValidatorService;

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
    binder.bindSingletonKey(TinKeys.LAST_MODIFIED_SERVICE, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new LastModifiedService(injector);
      }
    });
    binder.bindSingletonKey(TinKeys.JOIN_GAME_SERVICE, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new JoinGameService(injector);
      }
    });
    binder.bindSingletonKey(TinKeys.CURRENT_GAME_SERVICE, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new CurrentGameListener(injector);
      }
    });
    binder.bindSingletonKey(TinKeys.JOIN_GAME_VALIDATOR_SERVICE, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new JoinGameValidatorService(injector);
      }
    });
    binder.multibindKey(TinKeys.JOIN_GAME_VALIDATORS,
        Initializers.returnValue(new DefaultJoinGameValidator()));
    binder.bindSingletonKey(TinKeys.VIEWER_SERVICE, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new ViewerService(injector);
      }
    });
    binder.bindSingletonKey(TinKeys.COMMAND_LISTENER, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new CommandListener(injector);
      }
    });
    binder.bindSingletonKey(TinKeys.GAME_OVER_LISTENER, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new GameOverListener(injector);
      }
    });
    binder.bindSingletonKey(TinKeys.SUBMITTED_ACTION_LISTENER, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new SubmittedActionListener(injector);
      }
    });
    binder.bindSingletonKey(TinKeys.ADD_COMMAND_SERVICE, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new AddCommandService(injector);
      }
    });
    binder.bindSingletonKey(TinKeys.SUBMIT_ACTION_SERVICE, new Initializer() {
      @Override
      public Object initialize(Injector injector) {
        return new SubmitActionService(injector);
      }
    });
  }
}
