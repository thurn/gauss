package com.tinlib.jgail.service;

import com.tinlib.generated.Command;

import java.util.List;

public interface AIActionAdapter {
  public List<Command> adaptAction(long action);
}
