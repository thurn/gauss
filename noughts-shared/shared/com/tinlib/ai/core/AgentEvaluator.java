package com.tinlib.ai.core;


/**
 * An Evaluator which relies on an underlying Agent to perform evaluation.
 */
public class AgentEvaluator implements Evaluator {
  private final Agent agent;

  /**
   * Constructs a new AgentEvaluator.
   *
   * @param agent Underlying agent to perform evaluations.
   */
  public AgentEvaluator(Agent agent) {
    this.agent = agent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double evaluate(int player, State state) {
    if (state.isTerminal()) {
      return state.getWinner() == player ? 1.0 : -1.0;
    } else {
      State represented = agent.getStateRepresentation().initializeFrom(state);
      return agent.pickAction(player, represented).getScore();
    }
  }

  @Override
  public String toString() {
    return "AgentEvaluator [agent=" + agent + "]";
  }

}
