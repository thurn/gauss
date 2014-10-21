package com.tinlib.jgail.examples.dropswitch;

import com.tinlib.jgail.core.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DropswitchState implements State {
  /**
   * Direction on the game board.
   */
  private static enum Direction {
    N, NE, E, SE, S, SW, W, NW
  }

  private final int EMPTY = 0;
  private final int PLAYER_ONE = 1;
  private final int PLAYER_TWO = 2;
  private final int[][] board;

  private List<Long> possibleActions;
  private int currentPlayer;
  private int winner;
  private Random random = new Random();

  private DropswitchState(int[][] board, List<Long> actions, int currentPlayer, int winner) {
    this.board = board;
    this.possibleActions = actions;
    this.currentPlayer = currentPlayer;
    this.winner = winner;
  }

  @Override
  public ActionIterator getActionIterator() {
    return new ActionIteratorFromIterable(possibleActions);
  }

  @Override
  public long getRandomAction() {
    return possibleActions.get(random.nextInt(possibleActions.size()));
  }

  @Override
  public long perform(long action) {
    return 0;
  }

  @Override
  public void undo(long action, long undoToken) {

  }

  @Override
  public State setToStartingConditions() {
    currentPlayer = PLAYER_ONE;
    for (int[] array : board) {
      Arrays.fill(array, EMPTY);
    }
    winner = EMPTY;
    possibleActions = currentlyPossibleActions();
    return this;
  }

  @Override
  public State copy() {
    return new DropswitchState(copyBoard(), new ArrayList<>(possibleActions), currentPlayer,
        winner);
  }

  private int[][] copyBoard() {
    int[][] result = new int[4][4];
    for (int i = 0; i < 4; ++i) {
      result[i] = Arrays.copyOf(board[i], 4);
    }
    return result;
  }

  @Override
  public State initializeFrom(Object state) {
    return null;
  }

  @Override
  public boolean isTerminal() {
    return false;
  }

  @Override
  public int getWinner() {
    return 0;
  }

  @Override
  public int getCurrentPlayer() {
    return currentPlayer;
  }

  @Override
  public int playerAfter(int player) {
    return player == PLAYER_ONE ? PLAYER_TWO : PLAYER_ONE;
  }

  @Override
  public int playerBefore(int player) {
    return player == PLAYER_ONE ? PLAYER_TWO : PLAYER_ONE;
  }

  @Override
  public String actionToString(long action) {
    return null;
  }

  private List<Long> currentlyPossibleActions() {
    return null;
  }
}
