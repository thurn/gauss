package com.tinlib.services;

import com.tinlib.generated.Action;
import com.tinlib.generated.Game;

public interface NextPlayerService {
  public int nextPlayerNumber(Game game, Action action);
}
