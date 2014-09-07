package com.tinlib.jgail.service;

import com.tinlib.jgail.core.Agent;
import com.tinlib.jgail.core.State;
import com.tinlib.generated.Profile;

public interface AIProvider {
  public State provideState(Profile profile);

  public Agent provideAgent(Profile profile);
}
