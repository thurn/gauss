package com.tinlib.ai.examples.connect4;

import java.util.Scanner;

import com.tinlib.ai.core.ActionScore;
import com.tinlib.ai.core.Agent;
import com.tinlib.ai.core.State;

/**
 * A human player of Connect 4.
 */
public class C4HumanAgent implements Agent {

  private final Scanner in = new Scanner(System.in);
  private final State stateRepresentation;
  
  /**
   * @param stateRepresentation A state representation which will be totally
   *     ignored.
   */
  public C4HumanAgent(State stateRepresentation) {
    this.stateRepresentation = stateRepresentation;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public ActionScore pickActionBlocking(int player, State rootNode) {
    System.out.println("Select a column [0,6]");
    int column = in.nextInt();
    return new ActionScore(C4Action.create(player, column), 0.0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public State getStateRepresentation() {
    return stateRepresentation;
  }
  
  @Override
  public String toString() {
    return "Human";
  }

}
