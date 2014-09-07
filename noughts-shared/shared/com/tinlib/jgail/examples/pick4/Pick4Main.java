package com.tinlib.jgail.examples.pick4;

import com.google.common.collect.Lists;
import com.tinlib.jgail.algorithm.MonteCarloSearch;
import com.tinlib.jgail.algorithm.NegamaxSearch;
import com.tinlib.jgail.core.Agent;
import com.tinlib.jgail.core.TwoPlayerRunner;
import com.tinlib.jgail.core.WinLossEvaluator;

import java.util.List;

public class Pick4Main {
  public static void main(String[] args) throws InterruptedException {
    List<Agent> agents = Lists.newArrayList();
    agents.add(MonteCarloSearch.builder(new Pick4State())
        .setEvaluator(new WinLossEvaluator())
        .setNumSimulations(1000)
        .build());
    agents.add(NegamaxSearch.builder(new Pick4State())
        .setEvaluator(new WinLossEvaluator())
        .build());
    TwoPlayerRunner runner = new TwoPlayerRunner(agents, new Pick4State().setToStartingConditions());
    runner.runMatch(500L);
  }
}