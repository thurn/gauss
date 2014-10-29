package com.tinlib.test;

import com.firebase.client.Firebase;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.MutableClassToInstanceMap;
import com.google.common.collect.SetMultimap;
import com.tinlib.generated.Action;
import com.tinlib.generated.Game;

public class TestConfiguration {
  public static class Builder {
    private ClassToInstanceMap<Object> classMap;
    private SetMultimap<Class<?>, Object> multiClassMap;
    private Firebase firebase;
    private String viewerId;
    private String viewerKey;
    private boolean facebook;
    private Game game;
    private Action action;
    private boolean failOnError;

    private Builder() {
      classMap = MutableClassToInstanceMap.create();
      multiClassMap = HashMultimap.create();
    }

    public <T> Builder bindInstance(Class<T> classObject, T value) {
      classMap.putInstance(classObject, value);
      return this;
    }

    public <T> Builder multibindInstance(Class<T> classObject, T value) {
      multiClassMap.put(classObject, value);
      return this;
    }

    public Builder setFirebase(Firebase firebase) {
      this.firebase = firebase;
      return this;
    }

    public Builder setAnonymousViewer(String viewerId, String viewerKey) {
      this.viewerId = viewerId;
      this.viewerKey = viewerKey;
      this.facebook = false;
      return this;
    }

    public Builder setFacebookViewer(String facebookId) {
      this.viewerId = facebookId;
      this.viewerKey = facebookId;
      this.facebook = true;
      return this;
    }

    public Builder setCurrentGame(Game game) {
      this.game = game;
      return this;
    }

    public Builder setCurrentAction(Action action) {
      this.action = action;
      return this;
    }

    public Builder setFailOnError(boolean failOnError) {
      this.failOnError = failOnError;
      return this;
    }

    public TestConfiguration build() {
      return new TestConfiguration(classMap, multiClassMap, firebase, viewerId, viewerKey,
          facebook, game, action, failOnError);
    }
  }

  private final ClassToInstanceMap<Object> classMap;
  private final SetMultimap<Class<?>, Object> multiClassMap;
  private final Firebase firebase;
  private final String viewerId;
  private final String viewerKey;
  private final boolean facebook;
  private final Game game;
  private final Action action;
  private final boolean failOnError;

  private TestConfiguration(ClassToInstanceMap<Object> classMap,
      SetMultimap<Class<?>, Object> multiClassMap, Firebase firebase, String viewerId,
      String viewerKey, boolean facebook, Game game, Action action, boolean failOnError) {
    this.classMap = classMap;
    this.multiClassMap = multiClassMap;
    this.firebase = firebase;
    this.viewerId = viewerId;
    this.viewerKey = viewerKey;
    this.facebook = facebook;
    this.game = game;
    this.action = action;
    this.failOnError = failOnError;
  }

  public ClassToInstanceMap<Object> getClassMap() {
    return MutableClassToInstanceMap.create(classMap);
  }

  public SetMultimap<Class<?>, Object> getMultiClassMap() {
    return HashMultimap.create(multiClassMap);
  }

  public Firebase getFirebase() {
    return firebase;
  }

  public String getViewerId() {
    return viewerId;
  }

  public String getViewerKey() {
    return viewerKey;
  }

  public boolean isFacebook() {
    return facebook;
  }

  public Game getGame() {
    return game;
  }

  public Action getAction() {
    return action;
  }

  public boolean getFailOnError() {
    return failOnError;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public Builder toBuilder() {
    Builder result = new Builder();
    result.classMap = MutableClassToInstanceMap.create(this.classMap);
    result.multiClassMap = HashMultimap.create(this.multiClassMap);
    result.firebase = this.firebase;
    result.viewerId = this.viewerId;
    result.viewerKey = this.viewerKey;
    result.facebook = this.facebook;
    result.game = this.game;
    result.action = this.action;
    result.failOnError = this.failOnError;
    return result;
  }
}
