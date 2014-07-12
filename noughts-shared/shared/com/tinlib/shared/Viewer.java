package com.tinlib.shared;

import com.tinlib.message.Bus;
import com.tinlib.inject.Injector;
import com.firebase.client.Firebase;

/**
 * Manages the state of the current viewer and allows you to change the current viewer.
 *
 * <h1>Dependencies</h1>
 * <ul>
 *   <li>TinKeys.BUS</li>
 *   <li>TinKeys.FIREBASE</li>
 * </ul>
 *
 * <h1>Output Events</h1>
 * <ul>
 *   <li>TinMessages.FIREBASE_REFERENCES</li>
 *   <li>TinMessages.VIEWER_ID</li>
 * </ul>
 */
public class Viewer {
  private final Bus bus;
  private final Firebase firebase;

  public Viewer(Injector injector) {
    bus = (Bus)injector.get(TinKeys.BUS);
    firebase = (Firebase)injector.get(TinKeys.FIREBASE);
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
