package com.tinlib.services;

import com.firebase.client.Firebase;
import com.tinlib.convey.Bus;
import com.tinlib.core.TinKeys;
import com.tinlib.infuse.Injector;

import java.util.Map;

/**
 * Manages the state of the current viewer and allows you to change the current viewer.
 *
 * <h1>Dependencies</h1>
 * <ul>
 *   <li>{@link Bus}</li>
 *   <li>{@link Firebase}</li>
 * </ul>
 *
 * <h1>Output Messages</h1>
 * <ul>
 *   <li>{@link TinKeys#FIREBASE_REFERENCES}</li>
 *   <li>{@link TinKeys#VIEWER_ID}</li>
 * </ul>
 */
public class ViewerService {
  public static interface Function {
    public void apply(Map<String, Object> map, String viewerId, FirebaseReferences references);
  }

  private final Bus bus;
  private final Firebase firebase;

  public ViewerService(Injector injector) {
    bus = injector.get(Bus.class);
    firebase = injector.get(Firebase.class);
  }

  /**
   * Sets the current viewer to be an anonymous user with the provided
   * viewerId. The "viewerKey" parameter must be a secret string unique to this
   * viewer.
   */
  public void setViewerAnonymousId(String viewerId, String viewerKey) {
    bus.newProduction()
        .addKey(TinKeys.VIEWER_ID, viewerId)
        .addKey(TinKeys.FIREBASE_REFERENCES, FirebaseReferences.anonymous(viewerKey, firebase))
        .produce();
  }

  /**
   * Sets the current viewer to be a Facebook user with the provided Facebook
   * ID.
   */
  public void setViewerFacebookId(String facebookId) {
    bus.newProduction()
        .addKey(TinKeys.VIEWER_ID, facebookId)
        .addKey(TinKeys.FIREBASE_REFERENCES, FirebaseReferences.facebook(facebookId, firebase))
        .produce();
  }
}
