package com.tinlib.jgail.core;

/**
 * An {@link Evaluator} which solely evaluates a state based on whether or not
 * the provided player has won the game at that state.
 */
public class WinLossEvaluator implements Evaluator {
  /**
   * {@inheritDoc}
   */
  @Override
  public double evaluate(int player, State state) {
    int winner = state.getWinner();
    if (winner == State.NO_WINNER) {
      return 0.0;
    } else {
      return winner == player ? 1.0 : -1.0;
    }
  }
}
