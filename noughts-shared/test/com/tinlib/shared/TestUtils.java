package com.tinlib.shared;

import ca.thurn.noughts.shared.entities.*;
import com.google.common.collect.Lists;
import com.tinlib.inject.Injector;

public class TestUtils {
  public static final String VIEWER_ID = "viewerId";
  public static final String VIEWER_KEY = "viewerKey";
  public static final String GAME_ID = "gameId";

  public static void registerViewer(Injector injector) {
    new Viewer(injector).setViewerAnonymousId(VIEWER_ID, VIEWER_KEY);
  }

  public static Action action(int player, int column, int row, boolean submitted) {
    return Action.newBuilder()
        .setPlayerNumber(player)
        .setIsSubmitted(submitted)
        .addCommand(Command.newBuilder()
            .setColumn(column)
            .setRow(row))
        .build();
  }

  public static Action newEmptyAction(String gameId) {
    return Action.newBuilder()
        .setGameId(gameId)
        .setIsSubmitted(false)
        .build();
  }

  public static Action.Builder newUnsubmittedActionWithCommand(String gameId) {
    return Action.newBuilder()
        .setPlayerNumber(0)
        .setIsSubmitted(false)
        .setGameId(gameId)
        .addCommand(Command.newBuilder().setColumn(0).setRow(0));
  }

  public static Game.Builder newGameWithOnePlayer() {
    return Game.newBuilder()
        .setId("gameId")
        .setCurrentPlayerNumber(0)
        .setIsGameOver(false)
        .setIsLocalMultiplayer(false)
        .addPlayer("opponentId")
        .addProfile(Profile.newBuilder()
            .setIsComputerPlayer(false)
            .setName("Opponent")
            .setPronoun(Pronoun.NEUTRAL))
        .setLastModified(123);
  }

  public static Game.Builder newGameWithTwoPlayers() {
    return Game.newBuilder()
        .setId("gameId")
        .setCurrentPlayerNumber(0)
        .addAllPlayer(Lists.newArrayList(VIEWER_ID, "opponentId"))
        .setIsGameOver(false)
        .setIsLocalMultiplayer(false)
        .addProfile(Profile.newBuilder()
            .setIsComputerPlayer(false)
            .setName("User")
            .setPronoun(Pronoun.MALE))
        .addProfile(Profile.newBuilder()
            .setIsComputerPlayer(false)
            .setName("Opponent")
            .setPronoun(Pronoun.FEMALE))
        .setLastModified(123);
  }
}
