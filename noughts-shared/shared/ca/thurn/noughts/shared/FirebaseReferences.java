package ca.thurn.noughts.shared;

import com.firebase.client.Firebase;

public class FirebaseReferences {
  public static FirebaseReferences anonymous(String userKey, Firebase firebase) {
    return new FirebaseReferences(userKey, false /* isFacebookUser */, firebase);
  }

  public static FirebaseReferences facebook(String facebookId, Firebase firebase) {
    return new FirebaseReferences(facebookId, true /* isFacebookuser */, firebase);
  }

  private final String userKey;
  private final boolean isFacebookUser;
  private final Firebase firebase;

  private FirebaseReferences(String userKey, boolean isFacebookUser, Firebase firebase) {
    this.userKey = userKey;
    this.isFacebookUser = isFacebookUser;
    this.firebase = firebase;
  }

  /**
   * @param gameId A game ID
   * @return A Firebase reference for this game in the master game list.
   */
  Firebase gameReference(String gameId) {
    return firebase.child("games").child(gameId);
  }

  Firebase gameSubmittedActionsReference(String gameId) {
    return gameReference(gameId).child("submittedActionList");
  }

  Firebase userGamesReference() {
    Firebase users = firebase.child(isFacebookUser ? "facebookUsers" : "users");
    return users.child(userKey).child("games");
  }

  Firebase userReferenceForGame(String gameId) {
    return userGamesReference().child(gameId);
  }

  Firebase currentActionReferenceForGame(String gameId) {
    return userReferenceForGame(gameId).child("currentAction");
  }

  Firebase commandsReferenceForCurrentAction(String gameId) {
    return currentActionReferenceForGame(gameId).child("commandList");
  }

  Firebase requestReference(String requestId) {
    return firebase.child("requests").child("r" + requestId);
  }
}
