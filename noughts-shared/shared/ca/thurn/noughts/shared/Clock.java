package ca.thurn.noughts.shared;

class Clock {

  private static Clock INSTANCE = new Clock();

  private long currentTimeForTesting;
  private boolean useCurrentTimeForTesting = false;

  private Clock() {}

  public static Clock getInstance() {
    return INSTANCE;
  }

  void setCurrentTimeForTesting(long time) {
    currentTimeForTesting = time;
    useCurrentTimeForTesting = true;
  }

  public long currentTimeMillis() {
    if (useCurrentTimeForTesting) {
      return currentTimeForTesting;
    } else {
      return System.currentTimeMillis();
    }
  }

}
