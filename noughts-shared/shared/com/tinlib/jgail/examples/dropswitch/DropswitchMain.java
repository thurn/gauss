package com.tinlib.jgail.examples.dropswitch;

import com.tinlib.jgail.core.Agent;
import com.tinlib.jgail.core.TwoPlayerRunner;

import java.util.HashMap;
import java.util.Map;

public class DropswitchMain {
  public static void main(String[] args) throws InterruptedException {
    Map<Integer, Agent> agents = new HashMap<>();
    agents.put(DropswitchState.PLAYER_ONE, new DropswitchHumanAgent(new DropswitchState()));
    agents.put(DropswitchState.PLAYER_TWO, new DropswitchHumanAgent(new DropswitchState()));
    TwoPlayerRunner twoPlayerRunner = new TwoPlayerRunner(agents,
        new DropswitchState().setToStartingConditions());
    twoPlayerRunner.runMatch(1000L);
  }
}
