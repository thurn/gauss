package com.tinlib.core;

import com.tinlib.convey.Bus;
import com.tinlib.error.ErrorHandler;
import com.tinlib.jgail.service.AIService;
import com.tinlib.services.*;
import com.tinlib.time.DefaultTimeService;
import com.tinlib.time.TimeService;
import com.tinlib.validator.*;
import com.tinlib.analytics.AnalyticsService;
import com.tinlib.error.ErrorService;
import com.tinlib.error.PrintlnErrorHandler;
import com.tinlib.error.TrackingErrorHandler;
import com.tinlib.infuse.*;
import com.tinlib.convey.Buses;
import com.tinlib.push.PushNotificationService;
import com.tinlib.time.LastModifiedService;

public class TinModule implements Module {
  @Override
  public void configure(Binder binder) {
    binder.bindClass(Bus.class,
        Initializers.returnValue(Buses.newBus()));
    binder.bindClass(KeyedListenerService.class,
        Initializers.returnValue(new KeyedListenerService()));
    binder.bindClass(GameMutator.class, new Initializer<GameMutator>() {
      @Override
      public GameMutator initialize(Injector injector) {
        return new GameMutator(injector);
      }
    });
    binder.bindClass(PushNotificationService.class, new Initializer<PushNotificationService>() {
      @Override
      public PushNotificationService initialize(Injector injector) {
        return new PushNotificationService(injector);
      }
    });
    binder.bindClass(AnalyticsService.class, new Initializer<AnalyticsService>() {
      @Override
      public AnalyticsService initialize(Injector injector) {
        return new AnalyticsService(injector);
      }
    });
    binder.bindClass(ErrorService.class, new Initializer<ErrorService>() {
      @Override
      public ErrorService initialize(Injector injector) {
        return new ErrorService(injector);
      }
    });
    binder.multibindClass(ErrorHandler.class, new Initializer<ErrorHandler>() {
      @Override
      public ErrorHandler initialize(Injector injector) {
        return new TrackingErrorHandler(injector);
      }
    });
    binder.multibindClass(ErrorHandler.class,
        Initializers.<ErrorHandler>returnValue(new PrintlnErrorHandler()));
    binder.bindClass(ActionValidatorService.class, new Initializer<ActionValidatorService>() {
      @Override
      public ActionValidatorService initialize(Injector injector) {
        return new ActionValidatorService(injector);
      }
    });
    binder.multibindClass(ActionValidator.class,
        Initializers.<ActionValidator>returnValue(new DefaultActionValidator()));
    binder.bindClass(LastModifiedService.class, new Initializer<LastModifiedService>() {
      @Override
      public LastModifiedService initialize(Injector injector) {
        return new LastModifiedService(injector);
      }
    });
    binder.bindClass(JoinGameService.class, new Initializer<JoinGameService>() {
      @Override
      public JoinGameService initialize(Injector injector) {
        return new JoinGameService(injector);
      }
    });
    binder.bindClass(CurrentGameListener.class, new Initializer<CurrentGameListener>() {
      @Override
      public CurrentGameListener initialize(Injector injector) {
        return new CurrentGameListener(injector);
      }
    });
    binder.bindClass(CurrentActionListener.class, new Initializer<CurrentActionListener>() {
      @Override
      public CurrentActionListener initialize(Injector injector) {
        return new CurrentActionListener(injector);
      }
    });
    binder.bindClass(JoinGameValidatorService.class, new Initializer<JoinGameValidatorService>() {
      @Override
      public JoinGameValidatorService initialize(Injector injector) {
        return new JoinGameValidatorService(injector);
      }
    });
    binder.multibindClass(JoinGameValidator.class,
        Initializers.<JoinGameValidator>returnValue(new DefaultJoinGameValidator()));
    binder.bindClass(ViewerService.class, new Initializer<ViewerService>() {
      @Override
      public ViewerService initialize(Injector injector) {
        return new ViewerService(injector);
      }
    });
    binder.bindClass(CommandListener.class, new Initializer<CommandListener>() {
      @Override
      public CommandListener initialize(Injector injector) {
        return new CommandListener(injector);
      }
    });
    binder.bindClass(GameOverListener.class, new Initializer<GameOverListener>() {
      @Override
      public GameOverListener initialize(Injector injector) {
        return new GameOverListener(injector);
      }
    });
    binder.bindClass(SubmittedActionListener.class, new Initializer<SubmittedActionListener>() {
      @Override
      public SubmittedActionListener initialize(Injector injector) {
        return new SubmittedActionListener(injector);
      }
    });
    binder.bindClass(AddCommandService.class, new Initializer<AddCommandService>() {
      @Override
      public AddCommandService initialize(Injector injector) {
        return new AddCommandService(injector);
      }
    });
    binder.bindClass(SubmitActionService.class, new Initializer<SubmitActionService>() {
      @Override
      public SubmitActionService initialize(Injector injector) {
        return new SubmitActionService(injector);
      }
    });
    binder.bindClass(TimeService.class,
        Initializers.<TimeService>returnValue(new DefaultTimeService()));
    binder.bindClass(AIService.class, new Initializer<AIService>() {
      @Override
      public AIService initialize(Injector injector) {
        return new AIService(injector);
      }
    });
    binder.bindClass(ArchiveGameService.class, new Initializer<ArchiveGameService>() {
      @Override
      public ArchiveGameService initialize(Injector injector) {
        return new ArchiveGameService(injector);
      }
    });
    binder.bindClass(FacebookService.class, new Initializer<FacebookService>() {
      @Override
      public FacebookService initialize(Injector injector) {
        return new FacebookService(injector);
      }
    });
    binder.bindClass(GameListService.class, new Initializer<GameListService>() {
      @Override
      public GameListService initialize(Injector injector) {
        return new GameListService(injector);
      }
    });
    binder.bindClass(GameStatusListener.class, new Initializer<GameStatusListener>() {
      @Override
      public GameStatusListener initialize(Injector injector) {
        return new GameStatusListener(injector);
      }
    });
    binder.bindClass(NewGameService.class, new Initializer<NewGameService>() {
      @Override
      public NewGameService initialize(Injector injector) {
        return new NewGameService(injector);
      }
    });
    binder.bindClass(ProfileService.class, new Initializer<ProfileService>() {
      @Override
      public ProfileService initialize(Injector injector) {
        return new ProfileService(injector);
      }
    });
    binder.bindClass(ResignGameService.class, new Initializer<ResignGameService>() {
      @Override
      public ResignGameService initialize(Injector injector) {
        return new ResignGameService(injector);
      }
    });
    binder.bindClass(UndoService.class, new Initializer<UndoService>() {
      @Override
      public UndoService initialize(Injector injector) {
        return new UndoService(injector);
      }
    });
  }
}
