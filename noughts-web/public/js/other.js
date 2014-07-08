goog.provide("other")

goog.require("jsu");
goog.require("nts.Command");
goog.require("nts.AbstractValueListener");
goog.require("nts.AnalyticsService");
goog.require("nts.PushNotificationService");
goog.require("nts.Game");
goog.require("nts.Profile");
goog.require("nts.Games");
goog.require("nts.Model");
goog.require("nts.GameUpdateListener");
goog.require("nts.GameListListener");

other.command = function() {
  alert(nts.Command.newBuilder().setRow(1).setColumn(1).build());
};

/**
 * @suppress {checkTypes}
 * @constructor
 * @implements {nts.ValueListener}
 */
other.TestValueListener = function() {
};

/**
 * @param {string} string What to alert.
 */
other.TestValueListener.prototype.onUpdate = function(string) {
  alert(string);
};

/**
 * @param {?} firebaseError The error that happened.
 */
other.TestValueListener.prototype.onError = function(firebaseError) {
  alert("Error!");
};

/**
 * @constructor
 * @suppress {checkTypes}
 * @implements {nts.AnalyticsService}
 */
other.MyAnalyticsService = function() {
};

other.MyAnalyticsService.prototype.trackEvent = function(name) {
};

other.MyAnalyticsService.prototype.trackEventDimensions = function(name, dimensions) {
};

/**
 * @constructor
 * @suppress {checkTypes}
 * @implements {nts.PushNotificationService}
 */
other.MyPushNotificationsService = function() {
};

other.MyPushNotificationsService.prototype.addChannel = function(channelId) {
};

other.MyPushNotificationsService.prototype.removeChannel = function(channelId) {
};

other.MyPushNotificationsService.prototype.sendPushNotification = function(channelId, data) {
};

/**
 * @constructor
 * @suppress {checkTypes}
 * @implements {nts.GameUpdateListener}
 */
other.MyGameUpdateListener = function() {
};

other.MyGameUpdateListener.prototype.onGameUpdate = function(game) {
  window.console.log("onGameUpdate " + game);
};

other.MyGameUpdateListener.prototype.onCurrentActionUpdate = function(action) {
  window.console.log("onCurrentActionUpdate " + action);
};

other.MyGameUpdateListener.prototype.onGameStatusChanged = function(status) {
  window.console.log("onGameStatusChanged " + status);
};

other.MyGameUpdateListener.prototype.onProfileRequired = function(gameId, name) {
};

/**
 * @constructor
 * @suppress {checkTypes}
 * @implements {nts.GameListListener}
 */
other.MyGameListListener = function() {
};

other.MyGameListListener.prototype.onGameAdded = function(game) {
  window.console.log("onGameAdded " + game);
}

other.MyGameListListener.prototype.onGameChanged = function(game) {
  window.console.log("onGameChanged " + game);
}

other.MyGameListListener.prototype.onGameRemoved = function(gameId) {
  window.console.log("onGameRemoved " + gameId);
}

other.test3 = function() {
  var model = nts.Model.anonymousModel(
      "userId",
      "userKey",
      "https://noughts.firebaseio-demo.com/",
      new other.MyPushNotificationsService(),
      new other.MyAnalyticsService());
  model.setGameListListener(new other.MyGameListListener());
  var gameId = model.newGame(jsu.javaList(), null /* gameId */);
  window.console.log("gameId " + gameId);
  model.setGameUpdateListener(gameId, new other.MyGameUpdateListener(), false /* immediate */);
  var command = nts.Command.newBuilder().
      setRow(1).
      setColumn(1).
      build();
  setTimeout(function() {
    model.addCommandAndSubmit(gameId, command);
  }, 1000);
};

setTimeout(function() {
  other.test3();
}, 1000);
