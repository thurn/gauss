package com.tinlib.jgail.core;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * A helper class for running games & sets of games between multiple Agents.
 */
public class TwoPlayerRunner {
  private final State initialState;
  private final Random random = new Random();

  /**
   * Constructs a new Two Player Runner.
   *
   * @param canonicalState The state to use as the canonical game state. It is
   *     responsible for keeping track of actual actions in the game and is not
   *     directly given to any agent to make their action determination. It is
   *     the responsibility of the caller to ensure that this state is in the
   *     appropriate initial state for this game.
   */
  public TwoPlayerRunner(State canonicalState) {
    this.initialState = canonicalState;
  }
  
  /**
   * Run a series of matches between the agents and then report the results.
   *
   * @param tournamentSize The number of matches to run.
   * @param perMoveTimeBudget Amount of time to allow for each agent to pick a
   *     move, if they are AsynchronousAgents.
   * @throws InterruptedException 
   */
  public void runTournament(Map<Integer, Agent> agentMap, int tournamentSize, long perMoveTimeBudget)
      throws InterruptedException {
    long startTime = System.currentTimeMillis();
    Map<Agent, Integer> wins = new HashMap<>();
    int draws = 0;
    List<Map.Entry<Integer, Agent>> list = new ArrayList<>(agentMap.entrySet());

    for (int i = 0; i < tournamentSize; ++i) {
      Collections.shuffle(list);
      Map<Integer, Agent> agents = new HashMap<>();
      for (Map.Entry<Integer, Agent> entry : list) {
        agents.put(entry.getKey(), entry.getValue());
      }
      int winner = playGame(agents, false /* isInteractive */, perMoveTimeBudget);
      System.out.print(".");
      if (winner == State.NO_WINNER) {
        draws++;
      } else {
        Agent winningAgent = agents.get(winner);
        if (wins.containsKey(winningAgent)) {
          wins.put(winningAgent, wins.get(winningAgent) + 1);
        } else {
          wins.put(winningAgent, 1);
        }
      }

      if (i >= 10 && i % (tournamentSize / 10) == 0) {
        // Print intermediate results
        printTournamentResults(wins, draws);        
      }
    }
    
    printTournamentResults(wins, draws);
    
    long duration = System.currentTimeMillis() - startTime;
    String elapsed = new SimpleDateFormat("mm:ss").format(new Date(duration));
    String perTournament = new SimpleDateFormat("mm:ss").format(
        new Date(duration / tournamentSize));
    System.out.println("Tournament finished in " + elapsed + " (" + perTournament + 
        " per tournament)");    
  }

  public int runMatch(Map<Integer, Agent> agents, long perMoveTimeBudget)
      throws InterruptedException {
    return runMatch(agents, perMoveTimeBudget, true /* interactive */);
  }

  /**
   * Run a single match between the first two provided agents, printing out the
   * current game state between each move.
   * 
   * @param perMoveTimeBudget Amount of time to allow for each agent to pick a
   *     move, if they are AsynchronousAgents.
   * @throws InterruptedException
   * @return The player number of the winner of this match, or State.NO_WINNER if
   *     there was no winner.
   */
  public int runMatch(Map<Integer, Agent> agents, long perMoveTimeBudget, boolean interactive)
      throws InterruptedException {
    int winner = playGame(agents, interactive, perMoveTimeBudget);
    if (interactive) {
      if (winner != State.NO_WINNER) {
        System.out.println("Player " + winner + ", " + agents.get(winner) + " wins!");
      } else {
        System.out.println("Game drawn.");
      }
    }
    return winner;
  }
  
  /**
   * Play a match between the supplied agents.
   *
   * @param agentMap A mapping from player numbers in the game to the agents who will
   *     represent them.
   * @param isInteractive If true, print out intermediate game state
   *     information.
   * @return The winner of the game as defined by the canonical state's
   *     {@link State#getWinner()} method.
   * @throws InterruptedException 
   */
  private int playGame(Map<Integer, Agent> agentMap, boolean isInteractive,
      long perMoveTimeBudget) throws InterruptedException {
    State canonicalState = initialState.copy();
    while (!canonicalState.isTerminal()) {
      if (isInteractive) {
        System.out.println(canonicalState);
      }
      Agent agent = agentMap.get(canonicalState.getCurrentPlayer());
      long action;
      if (agent instanceof AsynchronousAgent) {
        AsynchronousAgent async = (AsynchronousAgent)agent;
        async.beginAsynchronousSearch(canonicalState.getCurrentPlayer(),
            async.getStateRepresentation().initializeFrom(canonicalState));
        Thread.sleep(perMoveTimeBudget);
        ActionScore pair = async.getAsynchronousSearchResult();
        if (pair == null) {
          throw new RuntimeException("Agent " + async + " needed more time.");
        }
        action = pair.getAction();
      } else {
        action = agent.pickActionBlocking(canonicalState.getCurrentPlayer(),
            agent.getStateRepresentation().initializeFrom(canonicalState)).getAction();
      }
      if (isInteractive) {
        System.out.println(agent + " picked action " + canonicalState.actionToString(action));
      }
      canonicalState.perform(action);
    }
    if (isInteractive) {
      System.out.println(canonicalState);
    }
    return canonicalState.getWinner();
  }
  
  /**
   * Prints out the results of a tournament.
   *
   * @param wins Array counting wins for each player number.
   * @param draws Number of draws in the tournament.
   */
  private void printTournamentResults(Map<Agent, Integer> wins, int draws) {
    System.out.println("===== Tournament Results ======");
    for (Entry<Agent, Integer> entry : wins.entrySet()) {
      System.out.println(entry.getKey() + " : " + entry.getValue() + " wins");
    }
    System.out.println(draws + " draws");
  }
  
}
