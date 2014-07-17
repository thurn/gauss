package com.tinlib.test;

import com.google.common.collect.Lists;
import com.tinlib.generated.*;

import java.util.Random;

public class TestUtils {
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
