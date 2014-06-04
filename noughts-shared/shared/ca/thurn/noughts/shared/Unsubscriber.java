package ca.thurn.noughts.shared;

/**
 * An Unsubscriber is a function you invoke to unregister a listener. When listeners are added,
 * they usually return an Unsubscriber so you can later unregister.
 */
public interface Unsubscriber {
  public void unsubscribe();
}
