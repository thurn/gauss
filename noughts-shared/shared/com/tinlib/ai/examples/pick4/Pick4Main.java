package com.tinlib.ai.examples.pick4;

import com.google.common.collect.Lists;
import com.tinlib.ai.algorithm.MonteCarloSearch;
import com.tinlib.ai.algorithm.NegamaxSearch;
import com.tinlib.ai.core.Agent;
import com.tinlib.ai.core.TwoPlayerRunner;
import com.tinlib.ai.core.WinLossEvaluator;

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
