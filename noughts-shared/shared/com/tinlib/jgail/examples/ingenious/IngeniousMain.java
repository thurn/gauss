package com.tinlib.jgail.examples.ingenious;

import java.util.ArrayList;
import java.util.List;

import com.tinlib.jgail.algorithm.NegamaxSearch;
import com.tinlib.jgail.algorithm.UctSearch;
import com.tinlib.jgail.core.Agent;
import com.tinlib.jgail.core.TwoPlayerRunner;

public class IngeniousMain {
  public static void main(String[] args) throws InterruptedException {
    List<Agent> agents = new ArrayList<>();
    agents.add(UctSearch.builder(new IngeniousState())
        .setNumSimulations(10000)
        .setNumInitialVisits(5)
        .build());
    agents.add(NegamaxSearch.builder(new IngeniousState())
        .setEvaluator(new IngeniousState.LowestScoreEvaluator())
        .setSearchDepth(2)
        .build());    
    TwoPlayerRunner twoPlayerRunner = new TwoPlayerRunner(agents, new IngeniousState().setToStartingConditions());
    twoPlayerRunner.runMatch(1000L);
  }
}
