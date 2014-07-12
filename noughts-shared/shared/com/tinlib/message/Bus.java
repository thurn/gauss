package com.tinlib.message;

import java.util.List;

public interface Bus {
  /**
   * Broadcasts a message with the specified name and associated value. Any
   * {@link Subscriber}s registered through
   * {@link Bus#register(Subscriber, java.util.List)} will be notified.
   *
   * @param name The name of the message to broadcast.
   */
  public void post(String name);

  /**
   * Similar to {@link Bus#post(String)}, but includes an associated value with
   * the message which will be passed to the {@link Subscriber}s.
   *
   * @param name The name of the message to broadcast.
   * @param value A value associated with the event. Cannot be null.
   */
  public void post(String name, Object value);

  /**
   * Similar to {@link Bus#post(String, Object)}, except that it also causes
   * the associated value to be retained. Future {@link Subscriber}s who
   * register for this event will immediately be sent the most recently
   * produced value in {@link Subscriber#onMessage(String, Object)}.
   *
   * @param name The name of the message to broadcast.
   * @param value A value associated with the event. Cannot be null.
   */
  public void produce(String name, Object value);

  /**
   * Adds a new {@link Subscriber} to the Bus's subscriber pool with a list of
   * message names which this Subscriber should be called for. The added
   * Subscriber will immediately be called with any stored values added to the
   * Bus via {@link Bus#produce(String, Object)}, and will subsequently be
   * called with any values from either {@link Bus#post(String, Object)} or
   * {@link Bus#produce(String, Object)}.
   *
   * @param subscriber The subscriber to add.
   * @param messages A list of message names. When a message with one of these names
   *     is created by the post() or produce() method, the Subscriber's
   *     {@link Subscriber#onMessage(String, Object)} method will be invoked.
   */
  public void register(Subscriber subscriber, List<String> messages);

  /**
   * Waits until values have been produced for <b>all</b> of the provided
   * messages, and then calls {@link Awaiter#onResult(java.util.Map)}. The
   * onResult() method will then subsequently be called when a new value is
   * produced for <b>any</b> of the associated messages. The onResult() method
   * will be passed a map from message names to associated objects.
   *
   * @param awaiter The awaiter to add.
   * @param messages A list of message names to await.
   */
  public void await(Awaiter awaiter, List<String> messages);

  /**
   * Removes a {@link Subscriber} from the Bus's subscriber pool. Future
   * messages will not cause invocations of this Subscriber's
   * {@link Subscriber#onMessage(String, Object)} method.
   *
   * @param subscriber The Subscriber to remove. Must be the same Subscriber
   *     initially passed to {@link Bus#register(Subscriber, java.util.List)}
   *     as defined by {@link Object#equals(Object).
   */
  public void unregister(Subscriber subscriber);

  /**
   * Removes a {@link Awaiter} from the Bus's Awaiter pool.
   *
   * @param awaiter The Awaiter to remove. Must be the same Awaiter initially
   *     passed to {@link Bus#await(Awaiter, java.util.List)}.
   */
  public void unregister(Awaiter awaiter);
}
