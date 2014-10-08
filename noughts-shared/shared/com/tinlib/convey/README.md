Convey is a lightweight event bus and reactive datastore for Java. It lets you publish events and add subscribers which listen for them. Values can also be associated with events, and these values can optionally be stored until they are needed. This makes it easy to write asynchronous loading code.

For example, imagine that you want to run some code after you've finished loading the user's Facebook profile and you've loaded the current game state. When each of the loading operations is finished, you can use `Bus#produce` to produce the result:

```java
bus.produce(Keys.FACEBOOK_PROFILE, profile);
...
bus.produce(Keys.GAME, game);
```

Then, you can schedule some code to run once both keys are produced:

```java
bus.once(Keys.FACEBOOK_PROFILE, Keys.GAME, new Subscriber2<Profile, Game>() {
  @Override
  public void onMessage(String value1, Game value2) {
    // Handle values
  }
});
```
