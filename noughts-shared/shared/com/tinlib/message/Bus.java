package com.tinlib.message;

import java.util.List;

public interface Bus {
  public void await(String message, Subscriber0 subscriber);

  public <A> void await(String message, Subscriber1<A> subscriber);

  public <A,B> void await(String message1, String message2, Subscriber2<A,B> subscriber);

  public <A,B,C> void await(String message1, String message2, String message3,
      Subscriber3<A,B,C> subscriber);

  public <A,B,C,D> void await(String message1, String message2, String message3, String message4,
      Subscriber4<A,B,C,D> subscriber);

  public <A,B,C,D,E> void await(String message1, String message2, String message3, String message4,
      String message5, Subscriber5<A,B,C,D,E> subscriber);

  public void await(List<String> messages, Subscriber subscriber);

  public void listen(List<String> messages, Listener listener);

  public void once(String message, Subscriber0 subscriber);

  public <A> void once(String message, Subscriber1<A> subscriber);

  public <A,B> void once(String message1, String message2, Subscriber2<A,B> subscriber);

  public <A,B,C> void once(String message1, String message2, String message3,
      Subscriber3<A,B,C> subscriber);

  public <A,B,C,D> void once(String message1, String message2, String message3, String message4,
      Subscriber4<A,B,C,D> subscriber);

  public <A,B,C,D,E> void once(String message1, String message2, String message3, String message4,
      String message5, Subscriber5<A,B,C,D,E> subscriber);

  public void once(List<String> messages, Subscriber subscriber);

  public void produce(String message);

  public void produce(String message, Object value);

  public void unregister(AnySubscriber subscriber);

  public void unregister(Listener listener);

  public void invalidate(String message);

  public void clearAll();
}
