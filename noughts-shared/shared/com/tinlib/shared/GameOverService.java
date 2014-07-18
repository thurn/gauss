package com.tinlib.shared;

import com.google.common.base.Optional;
import com.tinlib.generated.Action;
import com.tinlib.generated.Game;

import java.util.List;

public interface GameOverService {
  public Optional<List<Integer>> computeVictors(Game game, Action action);
}
