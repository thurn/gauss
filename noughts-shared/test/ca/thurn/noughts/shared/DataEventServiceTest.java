package ca.thurn.noughts.shared;

import ca.thurn.testing.SharedTestCase;

public class DataEventServiceTest extends SharedTestCase {
//  private Firebase firebase;
//  private String userId;
//  private String userKey;
//  private FirebaseReferences firebaseReferences;
//  private DataEventService dataService;
//  private Runnable cleanupFunction;
//
//  @Override
//  public void sharedSetUp(final Runnable done) {
//    cleanupFunction = null;
//    firebase = new Firebase("https://noughts-test.firebaseio-demo.com");
//    firebase.removeValue(new CompletionListener() {
//      @Override public void onComplete(FirebaseError error, Firebase ref) {
//        userId = "id" + randomInteger();
//        userKey = "K" + userId;
//        firebaseReferences = FirebaseReferences.anonymous(userKey, firebase);
//        dataService = new DataEventService(firebaseReferences);
//        done.run();
//      }
//    });
//  }
//
//  @Override
//  public void sharedTearDown() {
//    if (cleanupFunction != null) {
//      cleanupFunction.run();
//    }
//  }
//
//  public void testGameAdded() {
//    beginAsyncTestBlock();
//    final Game testGame = testGame().build();
//    final ChildUnsubscribeToken token =
//        dataService.addGameListener(new AbstractChildListener<Game>() {
//      @Override
//      public void onChildAdded(Game game, String previousChildName) {
//        assertEquals(testGame, game);
//        finished();
//      }
//    });
//    firebaseReferences.userReferenceForGame(testGame.getId()).setValue(testGame.serialize());
//    cleanupFunction = new Runnable() {
//      @Override
//      public void run() {
//        dataService.removeGameListener(token);
//      }
//    };
//    endAsyncTestBlock();
//  }
//
//  public void testGameValueListener() {
//    beginAsyncTestBlock();
//    final Game testGame = testGame().build();
//    final Game gameTwo = testGame().build();
//    final AtomicInteger callbackCount = new AtomicInteger(0);
//    final ValueUnsubscribeToken token =
//        dataService.addGameValueListener(testGame.getId(), new AbstractValueListener<Game>() {
//          @Override
//          public void onUpdate(Game game) {
//            if (callbackCount.getAndIncrement() == 0) {
//              assertEquals(testGame, game);
//              firebaseReferences.userReferenceForGame(testGame.getId()).setValue(gameTwo);
//            } else {
//              assertEquals(gameTwo, game);
//              finished();
//            }
//
//            finished();
//          }
//        });
//    cleanupFunction = new Runnable() {
//      @Override
//      public void run() {
//        dataService.removeGameValueListener(token);
//      }
//    };
//    firebaseReferences.userReferenceForGame(testGame.getId()).setValue(testGame.serialize());
//    endAsyncTestBlock();
//  }
//
//  private Game.Builder testGame() {
//    return Game.newBuilder()
//        .setId("g" + randomInteger())
//        .setIsGameOver(false)
//        .setIsLocalMultiplayer(false);
//  }

}
