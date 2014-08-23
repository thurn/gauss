package com.tinlib.ai.test;

import com.google.common.collect.ImmutableList;
import com.tinlib.ai.core.State;

import java.util.Random;

public class TestState implements State {
  private int currentPlayer;
  private long lastAction;

  @Override
  public ActionIterator getActionIterator() {
    return new ActionIteratorFromIterable(ImmutableList.of(0L, 1L, 2L, 3L, 4L));
  }

  @Override
  public long getRandomAction() {
    return new Random().nextInt(5);
  }

  @Override
  public long perform(long action) {
    lastAction = action;
    currentPlayer = playerAfter(currentPlayer);
    return action;
  }

  @Override
  public void undo(long action, long undoToken) {
    currentPlayer = playerBefore(currentPlayer);
    lastAction = undoToken;
  }

  @Override
  public State setToStartingConditions() {
    lastAction = 0L;
    currentPlayer = 0;
    return this;
  }

  @Override
  public State copy() {
    TestState result = new TestState();
    result.lastAction = lastAction;
    result.currentPlayer = currentPlayer;
    return result;
  }

  @Override
  public State initializeFrom(Object state) {
    lastAction = ((TestState)state).lastAction;
    currentPlayer = ((TestState)state).currentPlayer;
    return this;
  }

  @Override
  public boolean isTerminal() {
    return lastAction == 5L;
  }

  @Override
  public int getWinner() {
    return isTerminal() ? currentPlayer : -1;
  }

  @Override
  public int getCurrentPlayer() {
    return currentPlayer;
  }

  @Override
  public int playerAfter(int player) {
    return  currentPlayer == 0 ? 1 : 0;
  }

  @Override
  public int playerBefore(int player) {
    return currentPlayer == 0 ? 1 : 0;
  }

  @Override
  public String actionToString(long action) {
    return "" + action;
  }
}
