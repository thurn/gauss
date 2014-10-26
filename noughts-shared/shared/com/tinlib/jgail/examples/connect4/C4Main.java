package com.tinlib.jgail.examples.connect4;

import com.tinlib.jgail.algorithm.MonteCarloSearch;
import com.tinlib.jgail.algorithm.NegamaxSearch;
import com.tinlib.jgail.core.Agent;
import com.tinlib.jgail.core.TwoPlayerRunner;

import java.util.HashMap;
import java.util.Map;

public class C4Main {
  public static void main(String[] args) throws InterruptedException {
    Map<Integer, Agent> agents = new HashMap<>();
    agents.put(0, NegamaxSearch.builder(new C4State()).build());
    agents.put(1, MonteCarloSearch.builder(new C4State()).build());
    TwoPlayerRunner twoPlayerRunner = new TwoPlayerRunner(
        new C4State().setToStartingConditions());
    twoPlayerRunner.runTournament(agents, 10, 1000L);
  }
}
