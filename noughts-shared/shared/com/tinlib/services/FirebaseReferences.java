package com.tinlib.services;

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

  public Firebase games() {
    return firebase.child("games");
  }

  /**
   * @param gameId A game ID
   * @return A Firebase reference for this game in the master game list.
   */
  public Firebase gameReference(String gameId) {
    return games().child(gameId);
  }

  public Firebase gameSubmittedActionsReference(String gameId) {
    return gameReference(gameId).child("submittedAction");
  }

  public Firebase userGames() {
    Firebase users = firebase.child(isFacebookUser ? "facebookUsers" : "users");
    return users.child(userKey).child("games");
  }

  public Firebase userReferenceForGame(String gameId) {
    return userGames().child(gameId);
  }

  public Firebase currentActionReferenceForGame(String gameId) {
    return userReferenceForGame(gameId).child("currentAction");
  }

  public Firebase commandsReferenceForCurrentAction(String gameId) {
    return currentActionReferenceForGame(gameId).child("command");
  }

  public Firebase futureCommandsReferenceForCurrentAction(String gameId) {
    return currentActionReferenceForGame(gameId).child("futureCommand");
  }
  public Firebase requestReference(String requestId) {
    return firebase.child("requests").child("r" + requestId);
  }
}
