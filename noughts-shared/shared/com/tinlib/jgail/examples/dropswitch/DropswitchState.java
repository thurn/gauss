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

  public static final int PLAYER_ONE = 1;
  public static final int PLAYER_TWO = 2;

  private static final int EMPTY = 0;
  private final int[][] board;

  private List<Long> possibleActions;
  private int currentPlayer;
  private int winner;
  private boolean gameOver;
  private Random random = new Random();
  private long lastAction;

  /**
   * Null-initializes this state. The state will not be usable until one of
   * initializeFrom() or setToStartingConditions() is called on the result;
   */
  public DropswitchState() {
    this.board = new int[4][4];
  }

  private DropswitchState(int[][] board, List<Long> actions, int currentPlayer, int winner,
      boolean gameOver, long lastAction) {
    this.board = board;
    this.possibleActions = actions;
    this.currentPlayer = currentPlayer;
    this.winner = winner;
    this.gameOver = gameOver;
    this.lastAction = lastAction;
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
    if (DropswitchAction.isSwitchAction(action)) {
      int column1 = DropswitchAction.getSwitchColumn1(action);
      int row1 = DropswitchAction.getSwitchRow1(action);
      int column2 = DropswitchAction.getSwitchColumn2(action);
      int row2 = DropswitchAction.getSwitchRow2(action);
      performSwitch(column1, row1, column2, row2);

      int winner1 = computeWinnerFromLocation(board[column1][row1], column1, row1);
      int winner2 = computeWinnerFromLocation(board[column2][row2], column2, row2);

      if (winner1 != State.NO_WINNER && winner2 != State.NO_WINNER) {
        // Simultaneous win -> draw
        gameOver = true;
        winner = State.NO_WINNER;
      } else if (winner1 != State.NO_WINNER) {
        winner = winner1;
        gameOver = true;
      } else if (winner2 != State.NO_WINNER) {
        winner = winner2;
        gameOver = true;
      }
    } else {
      int column = DropswitchAction.getDropColumn(action);
      int row = DropswitchAction.getDropRow(action);
      board[column][row] = currentPlayer;
      winner = computeWinnerFromLocation(currentPlayer, column, row);
      if (winner != State.NO_WINNER) {
        gameOver = true;
      }
    }
    currentPlayer = playerAfter(currentPlayer);
    long undoToken = lastAction;
    lastAction = action;
    possibleActions = currentlyPossibleActions();
    return undoToken;
  }

  @Override
  public void undo(long action, long undoToken) {
    if (DropswitchAction.isSwitchAction(action)) {
      performSwitch(DropswitchAction.getSwitchColumn1(action),
          DropswitchAction.getSwitchRow1(action),
          DropswitchAction.getSwitchColumn2(action),
          DropswitchAction.getSwitchRow2(action));
    } else {
      board[DropswitchAction.getDropColumn(action)][DropswitchAction.getDropRow(action)] = EMPTY;
    }
    gameOver = false;
    winner = State.NO_WINNER;
    currentPlayer = playerBefore(currentPlayer);
    lastAction = undoToken;
    possibleActions = currentlyPossibleActions();
  }

  private void performSwitch(int column1, int row1, int column2, int row2) {
    int tmp = board[column1][row1];
    board[column1][row1] = board[column2][row2];
    board[column2][row2] = tmp;
  }

  @Override
  public State setToStartingConditions() {
    currentPlayer = PLAYER_ONE;
    for (int[] array : board) {
      Arrays.fill(array, EMPTY);
    }
    winner = State.NO_WINNER;
    possibleActions = currentlyPossibleActions();
    gameOver = false;
    lastAction = -1;
    return this;
  }

  @Override
  public DropswitchState copy() {
    if (possibleActions == null) {
      return new DropswitchState();
    } else {
      return new DropswitchState(copyBoard(), new ArrayList<>(possibleActions), currentPlayer,
          winner, gameOver, lastAction);
    }
  }

  private int[][] copyBoard() {
    int[][] result = new int[4][4];
    for (int i = 0; i < 4; ++i) {
      result[i] = Arrays.copyOf(board[i], 4);
    }
    return result;
  }

  @Override
  public State initializeFrom(Object otherState) {
    DropswitchState state = ((DropswitchState)otherState).copy();
    this.currentPlayer = state.currentPlayer;
    this.winner = state.winner;
    this.possibleActions = state.possibleActions;
    this.gameOver = state.gameOver;
    this.lastAction = state.lastAction;
    for (int i = 0; i < 4; ++i) {
      System.arraycopy(state.board[i], 0, board[i], 0, 4);
    }
    return this;
  }

  @Override
  public boolean isTerminal() {
    return gameOver;
  }

  @Override
  public int getWinner() {
    return winner;
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
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (int row = 0; row < 4; ++row) {
      for (int column = 0; column < 4; ++column) {
        int p = board[column][row];
        if (p == EMPTY) {
          result.append("-");
        } else {
          result.append(p == PLAYER_TWO ? "O" : "X");
        }
      }
      result.append("\n");
    }
    return result.toString();
  }

  @Override
  public String actionToString(long action) {
    if (DropswitchAction.isSwitchAction(action)) {
      return "[switch (" + DropswitchAction.getSwitchColumn1(action) + "," +
          DropswitchAction.getSwitchRow1(action) + ") (" +
          DropswitchAction.getSwitchColumn2(action) + "," +
          DropswitchAction.getSwitchRow2(action) + ")]";
    } else {
      return "[drop (" + DropswitchAction.getDropColumn(action) + "," +
          DropswitchAction.getDropRow(action) + ")]";
    }
  }

  /**
   * Figures out if a player has 4 of their pieces in a row extending in any direction
   * from the provided location.
   *
   * @param player The player to check.
   * @param moveColumn Column of search location.
   * @param moveRow Row of search location.
   * @return The value of "player" if the player has 4 pieces in a row at this location,
   *     otherwise State.NO_WINNER.
   */
  private int computeWinnerFromLocation(int player, int moveColumn, int moveRow) {
    // Vertical win?
    if (countGroupSize(moveColumn, moveRow - 1, Direction.S, player) +
        countGroupSize(moveColumn, moveRow + 1, Direction.N, player) >= 3) {
      return player;
    }

    // Horizontal win?
    if (countGroupSize(moveColumn + 1, moveRow, Direction.E, player) +
        countGroupSize(moveColumn - 1, moveRow, Direction.W, player) >= 3) {
      return player;
    }

    // Diagonal win?
    if (countGroupSize(moveColumn + 1, moveRow + 1, Direction.NE, player) +
        countGroupSize(moveColumn - 1, moveRow - 1, Direction.SW, player) >= 3) {
      return player;
    }
    if (countGroupSize(moveColumn - 1, moveRow + 1, Direction.NW, player) +
        countGroupSize(moveColumn + 1, moveRow - 1, Direction.SE, player) >=3) {
      return player;
    }

    return State.NO_WINNER;
  }

  /**
   * Counts consecutive pieces from the same player.
   *
   * @param col Column number to start counting from.
   * @param row Row number to start counting from.
   * @param dir Direction in which to count.
   * @param player Player whose pieces we are counting.
   * @return The number of pieces belonging to this player, not counting the
   *     provided column and row, which can be found in a line in the provided
   *     direction.
   */
  private int countGroupSize(int col, int row, Direction dir, int player) {
    if (row < 4 && row >= 0 && col < 4 && col >= 0 && board[col][row] == player) {
      switch (dir) {
        case N:
          return 1 + countGroupSize(col, row + 1, dir, player);
        case S:
          return 1 + countGroupSize(col, row - 1, dir, player);
        case E:
          return 1 + countGroupSize(col + 1, row, dir, player);
        case W:
          return 1 + countGroupSize(col - 1, row, dir, player);
        case NE:
          return 1 + countGroupSize(col + 1, row + 1, dir, player);
        case NW:
          return 1 + countGroupSize(col - 1, row + 1, dir, player);
        case SE:
          return 1 + countGroupSize(col + 1, row - 1, dir, player);
        case SW:
          return 1 + countGroupSize(col - 1, row - 1, dir, player);
        default:
          return 0;
      }
    } else {
      return 0;
    }
  }

  private List<Long> currentlyPossibleActions() {
    List<Long> result = new ArrayList<>();

    // Add drop actions
    for (int i = 0; i < 4; ++i) {
      for (int j = 0; j < 4; ++j) {
        if (board[i][j] == EMPTY) {
          long action = DropswitchAction.dropAction(i, j);
          if (action != lastAction) {
            result.add(action);
          }
        }
      }
    }

    // Add switch actions
    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 3; ++j) {
        // Switch with right neighbor
        if (board[i][j] != EMPTY && board[i + 1][j] != EMPTY && board[i + 1][j] != board[i][j]) {
          long action = DropswitchAction.switchAction(i, j, i + 1, j);
          if (action != lastAction) {
            result.add(action);
          }
        }
        // Switch with bottom neighbor
        if (board[i][j] != EMPTY && board[i][j + 1] != EMPTY && board[i][j + 1] != board[i][j]) {
          long action = DropswitchAction.switchAction(i, j, i, j + 1);
          if (action != lastAction) {
            result.add(action);
          }
        }
      }
    }

    return result;
  }
}
