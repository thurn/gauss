package com.tinlib.shared;

import com.firebase.client.Firebase;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus;

import java.util.Map;

/**
 * Manages the state of the current viewer and allows you to change the current viewer.
 *
 * <h1>Dependencies</h1>
 * <ul>
 *   <li>{@link com.tinlib.core.TinKeys#BUS}</li>
 *   <li>{@link com.tinlib.core.TinKeys#FIREBASE}</li>
 * </ul>
 *
 * <h1>Output Messages</h1>
 * <ul>
 *   <li>{@link com.tinlib.core.TinMessages#FIREBASE_REFERENCES}</li>
 *   <li>{@link com.tinlib.core.TinMessages#VIEWER_ID}</li>
 * </ul>
 */
public class Viewer {
  public static interface Function {
    public void apply(Map<String, Object> map, String viewerId, FirebaseReferences references);
  }

  private final Bus bus;
  private final Firebase firebase;

  public Viewer(Injector injector) {
    bus = injector.get(TinKeys.BUS);
    firebase = injector.get(TinKeys.FIREBASE);
  }

  /**
   * Sets the current viewer to be an anonymous user with the provided
   * viewerId. The "viewerKey" parameter must be a secret string unique to this
   * viewer.
   */
  public void setViewerAnonymousId(String viewerId, String viewerKey) {
    bus.produce(TinMessages.FIREBASE_REFERENCES, FirebaseReferences.anonymous(viewerKey, firebase));
    bus.produce(TinMessages.VIEWER_ID, viewerId);
  }

  /**
   * Sets the current viewer to be a Facebook user with the provided Facebook
   * ID.
   */
  public void setViewerFacebookId(String facebookId) {
    bus.produce(TinMessages.FIREBASE_REFERENCES, FirebaseReferences.facebook(facebookId, firebase));
    bus.produce(TinMessages.VIEWER_ID, facebookId);
  }
}
