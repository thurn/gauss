package com.tinlib.test;

import ca.thurn.noughts.shared.entities.*;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.common.collect.Lists;
import com.tinlib.inject.Injector;
import com.tinlib.message.Subscriber1;
import com.tinlib.shared.CurrentAction;
import com.tinlib.shared.CurrentGame;
import com.tinlib.shared.Viewer;

import java.util.Random;

public class TestUtils {
  public static final String VIEWER_ID = "viewerId";
  public static final String VIEWER_KEY = "viewerKey";
  public static final String GAME_ID = "gameId";

  public static void registerViewer(Injector injector) {
    Viewer viewer = new Viewer(injector);
    viewer.setViewerAnonymousId(VIEWER_ID, VIEWER_KEY);
  }

  public static void registerCurrentGameAndAction(Injector injector) {
    (new CurrentGame(injector)).loadGame(GAME_ID);
    new CurrentAction(injector);
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

  public static Action newEmptyAction() {
    return Action.newBuilder()
        .setGameId(GAME_ID)
        .setIsSubmitted(false)
        .build();
  }

  public static Action newEmptyAction(String gameId) {
    return Action.newBuilder()
        .setGameId(gameId)
        .setIsSubmitted(false)
        .build();
  }

  public static Action.Builder newUnsubmittedActionWithCommand() {
    return Action.newBuilder()
        .setPlayerNumber(0)
        .setIsSubmitted(false)
        .setGameId(GAME_ID)
        .addCommand(Command.newBuilder().setColumn(0).setRow(0));
  }

  public static Game.Builder newGameWithOnePlayer() {
    return Game.newBuilder()
        .setId(GAME_ID)
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

  public static Game.Builder newGameWithOnePlayer(String gameId) {
    return Game.newBuilder()
        .setId(gameId)
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

  public static Game.Builder newGameWithTwoPlayers(String viewerId, String gameId) {
    return Game.newBuilder()
        .setId(gameId)
        .setCurrentPlayerNumber(0)
        .addAllPlayer(Lists.newArrayList(viewerId, "opponentId"))
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

  public static Profile.Builder newTestProfile() {
    return Profile.newBuilder()
        .setName("name")
        .setIsComputerPlayer(false)
        .setPronoun(Pronoun.NEUTRAL);
  }

  public static String newViewerId() {
    return "viewerId" + (new Random()).nextInt();
  }

  public static String newViewerKey() {
    return "viewerKey" + (new Random()).nextInt();
  }

  public static String newGameId() {
    return "gameId" + (new Random()).nextInt();
  }
}
