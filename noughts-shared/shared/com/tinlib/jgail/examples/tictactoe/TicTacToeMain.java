package com.tinlib.jgail.examples.tictactoe;

import java.util.HashMap;
import java.util.Map;

import com.tinlib.jgail.algorithm.MonteCarloSearch;
import com.tinlib.jgail.algorithm.NegamaxSearch;
import com.tinlib.jgail.core.Agent;
import com.tinlib.jgail.core.TwoPlayerRunner;
import com.tinlib.jgail.core.WinLossEvaluator;

public class TicTacToeMain {
  public static void main(String[] args) throws InterruptedException {
    Map<Integer, Agent> agents = new HashMap<>();
    agents.put(0, NegamaxSearch.builder(new TicTacToeState())
        .setEvaluator(new WinLossEvaluator())
        .build());
    agents.put(1, MonteCarloSearch.builder(new TicTacToeState()).setNumSimulations(10).build());
    TwoPlayerRunner twoPlayerRunner = new TwoPlayerRunner(agents,
        new TicTacToeState().setToStartingConditions());
    twoPlayerRunner.runTournament(100, 25L);
  }
}