package com.tinlib.services;

import com.firebase.client.Firebase;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinKeys2;
import com.tinlib.infuse.Injector;
import com.tinlib.convey.Bus;

import java.util.Map;

/**
 * Manages the state of the current viewer and allows you to change the current viewer.
 *
 * <h1>Dependencies</h1>
 * <ul>
 *   <li>{@link com.tinlib.core.TinKeys2#BUS}</li>
 *   <li>{@link com.tinlib.core.TinKeys2#FIREBASE}</li>
 * </ul>
 *
 * <h1>Output Messages</h1>
 * <ul>
 *   <li>{@link com.tinlib.core.TinMessages#FIREBASE_REFERENCES}</li>
 *   <li>{@link com.tinlib.core.TinMessages#VIEWER_ID}</li>
 * </ul>
 */
public class ViewerService {
  public static interface Function {
    public void apply(Map<String, Object> map, String viewerId, FirebaseReferences references);
  }

  private final Bus bus;
  private final Firebase firebase;

  public ViewerService(Injector injector) {
    bus = injector.get(TinKeys2.BUS);
    firebase = injector.get(TinKeys2.FIREBASE);
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
