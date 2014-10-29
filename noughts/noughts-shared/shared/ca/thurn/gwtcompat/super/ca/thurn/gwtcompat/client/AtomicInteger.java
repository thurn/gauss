package ca.thurn.gwtcompat.client;

public class AtomicInteger {
  private int atomicInteger;

  public AtomicInteger() {
    atomicInteger = 0;
  }

  public AtomicInteger(int initialValue) {
    atomicInteger = initialValue;
  }

  public int get() {
    return atomicInteger;
  }

  public int decrementAndGet() {
    return --atomicInteger;
  }

  int incrementAndGet() {
    return ++atomicInteger;
  }

  void set(int newValue) {
    atomicInteger = newValue;
  }
}
