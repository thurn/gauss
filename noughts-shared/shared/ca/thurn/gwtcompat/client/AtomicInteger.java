package ca.thurn.gwtcompat.client;

public class AtomicInteger {
  private final java.util.concurrent.atomic.AtomicInteger atomicInteger;

  public AtomicInteger() {
    atomicInteger = new java.util.concurrent.atomic.AtomicInteger();
  }

  public AtomicInteger(int initialValue) {
    atomicInteger = new java.util.concurrent.atomic.AtomicInteger(initialValue);
  }

  public int get() {
    return atomicInteger.get();
  }

  public int decrementAndGet() {
    return atomicInteger.decrementAndGet();
  }

  int incrementAndGet() {
    return atomicInteger.incrementAndGet();
  }

  void set(int newValue) {
    atomicInteger.set(newValue);
  }
}
