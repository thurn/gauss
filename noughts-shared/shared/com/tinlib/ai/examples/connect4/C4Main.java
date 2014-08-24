package com.tinlib.ai.examples.connect4;

import java.util.ArrayList;
import java.util.List;

import com.tinlib.ai.algorithm.MonteCarloSearch;
import com.tinlib.ai.algorithm.NegamaxSearch;
import com.tinlib.ai.core.Agent;
import com.tinlib.ai.core.TwoPlayerRunner;

public class C4Main {
  public static void main(String[] args) throws InterruptedException {
    List<Agent> agents = new ArrayList<>();
    agents.add(NegamaxSearch.builder(new C4State()).build());
    agents.add(MonteCarloSearch.builder(new C4State()).build());
    TwoPlayerRunner twoPlayerRunner = new TwoPlayerRunner(agents, new C4State().setToStartingConditions());
    twoPlayerRunner.runTournament(10, 1000L);
  }
}
