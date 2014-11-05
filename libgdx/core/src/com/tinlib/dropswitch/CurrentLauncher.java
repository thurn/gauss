package com.tinlib.dropswitch;

public class CurrentLauncher {
  private static Launcher currentLauncher;

  public static Launcher get() {
    return currentLauncher;
  }

  public static void set(Launcher launcher) {
    currentLauncher = launcher;
  }
}
