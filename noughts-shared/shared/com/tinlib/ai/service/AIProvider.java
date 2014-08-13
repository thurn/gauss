package com.tinlib.ai.service;

import com.tinlib.ai.core.Agent;
import com.tinlib.ai.core.State;
import com.tinlib.generated.Profile;

public interface AIProvider {
  public State provideState(Profile profile);

  public Agent provideAgent(Profile profile);
}
