package com.tinlib.jgail.examples.tictactoe;

import java.util.ArrayList;
import java.util.List;

import com.tinlib.jgail.algorithm.MonteCarloSearch;
import com.tinlib.jgail.algorithm.NegamaxSearch;
import com.tinlib.jgail.core.Agent;
import com.tinlib.jgail.core.TwoPlayerRunner;
import com.tinlib.jgail.core.WinLossEvaluator;

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