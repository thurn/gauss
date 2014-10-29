package com.tinlib.jgail.examples.dropswitch;

import com.tinlib.jgail.core.ActionScore;
import com.tinlib.jgail.core.Agent;
import com.tinlib.jgail.core.State;

import java.util.Scanner;

public class DropswitchHumanAgent implements Agent {
  private final Scanner in = new Scanner(System.in);
  private final State stateRepresentation;

  public DropswitchHumanAgent(State state) {
    this.stateRepresentation = state;
  }

  @Override
  public ActionScore pickActionBlocking(int player, State rootNode) {
    System.out.println("Enter 1 to drop or 2 to switch");
    int action = in.nextInt();
    if (action == 1) {
      System.out.println("Enter drop column #");
      int column = in.nextInt();
      System.out.println("Enter drop row #");
      int row = in.nextInt();
      return new ActionScore(DropswitchAction.dropAction(column, row), 0.0);
    } else if (action == 2) {
      System.out.println("Enter switch column1 #");
      int column1 = in.nextInt();
      System.out.println("Enter switch row1 #");
      int row1 = in.nextInt();
      System.out.println("Enter switch column2 #");
      int column2 = in.nextInt();
      System.out.println("Enter switch row2 #");
      int row2 = in.nextInt();
      return new ActionScore(DropswitchAction.switchAction(column1, row1, column2, row2), 0.0);
    } else {
      throw new RuntimeException("Illegal action");
    }
  }

  @Override
  public State getStateRepresentation() {
    return stateRepresentation;
  }

  @Override
  public String toString() {
    return "Human";
  }
}
