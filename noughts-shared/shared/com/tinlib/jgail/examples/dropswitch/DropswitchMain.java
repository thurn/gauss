package com.tinlib.jgail.examples.dropswitch;

import com.tinlib.jgail.algorithm.NegamaxSearch;import com.tinlib.jgail.algorithm.UctSearch;
import com.tinlib.jgail.core.Agent;
import com.tinlib.jgail.core.TwoPlayerRunner;import com.tinlib.jgail.core.WinLossEvaluator;

import java.util.HashMap;
import java.util.Map;

public class DropswitchMain {
  public static void main(String[] args) throws InterruptedException {
    Map<Integer, Agent> agents = new HashMap<>();
    agents.put(DropswitchState.PLAYER_ONE, UctSearch.builder(new DropswitchState())
        .setDiscountRate(0.99)
        .build());
    agents.put(DropswitchState.PLAYER_TWO, UctSearch.builder(new DropswitchState())
        .setDiscountRate(0.99)
        .build());
    TwoPlayerRunner twoPlayerRunner = new TwoPlayerRunner(
        new DropswitchState().setToStartingConditions());
    for (int i = 0; i < 10; ++i) {
      int winner = twoPlayerRunner.runMatch(agents, 500L, false);
      System.out.println(winner);
    }
  }
}
