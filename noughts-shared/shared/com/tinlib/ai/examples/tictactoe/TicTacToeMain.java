package com.tinlib.ai.examples.tictactoe;

import java.util.ArrayList;
import java.util.List;

import com.tinlib.ai.algorithm.MonteCarloSearch;
import com.tinlib.ai.algorithm.NegamaxSearch;
import com.tinlib.ai.core.Agent;
import com.tinlib.ai.core.TwoPlayerRunner;
import com.tinlib.ai.core.WinLossEvaluator;

public class TicTacToeMain {
  public static void main(String[] args) throws InterruptedException {
    List<Agent> agents = new ArrayList<>();
    agents.add(NegamaxSearch.builder(new TicTacToeState())
        .setEvaluator(new WinLossEvaluator())
        .build());
    agents.add(MonteCarloSearch.builder(new TicTacToeState()).setNumSimulations(10).build());
    TwoPlayerRunner twoPlayerRunner = new TwoPlayerRunner(agents, new TicTacToeState().setToStartingConditions());
    twoPlayerRunner.runTournament(100, 25L);
  }
}