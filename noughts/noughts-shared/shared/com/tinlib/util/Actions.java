package com.tinlib.util;

import com.tinlib.generated.Action;

public class Actions {
  public static Action newEmptyAction(String gameId) {
    return Action.newBuilder()
        .setGameId(gameId)
        .setIsSubmitted(false)
        .build();
  }
}
