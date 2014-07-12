package com.tinlib.shared;

import ca.thurn.noughts.shared.entities.Game;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.tinlib.inject.Binder;
import com.tinlib.inject.Initializers;
import com.tinlib.inject.Injector;
import com.tinlib.inject.Module;
import com.tinlib.test.ErroringFirebase;
import com.tinlib.test.TinTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CurrentGameTest extends TinTestCase {
  @Override
  public void setUp(final Runnable done) {
    done.run();
  }

  @Override
  public void tearDown(Runnable done) {
    done.run();
  }

  @Test
  public void testLoadGame() {
    beginAsyncTestBlock(2);
    final Game testGame = TestUtils.newGameWithOnePlayer().build();
    Injector injector = newTestInjector();
    TestUtils.registerViewer(injector);
    final CurrentGame currentGame = new CurrentGame(injector);

    expectMessage(TinMessages.CURRENT_GAME_ID, new ValueListener() {
      @Override
      public void onValue(Object object) {
        assertEquals(TestUtils.GAME_ID, object);
        finished();
      }
    });
    expectMessage(TinMessages.CURRENT_GAME, new ValueListener() {
      @Override
      public void onValue(Object object) {
        assertEquals(testGame, object);
        finished();
      }
    });

    FirebaseReferences references = FirebaseReferences.anonymous(TestUtils.VIEWER_KEY, firebase);
    references.gameReference(TestUtils.GAME_ID).setValue(testGame.serialize(),
        new Firebase.CompletionListener() {
      @Override
      public void onComplete(FirebaseError firebaseError, Firebase firebase) {
        currentGame.loadGame(TestUtils.GAME_ID);
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testLoadGameError() {
    beginAsyncTestBlock();
    Injector injector = newErroringFirebaseInjector();
    TestUtils.registerViewer(injector);
    CurrentGame currentGame = new CurrentGame(injector);

    expectMessage(TinMessages.ERROR, new ValueListener() {
      @Override
      public void onValue(Object object) {
        finished();
      }
    });
    currentGame.loadGame(TestUtils.GAME_ID);
    endAsyncTestBlock();
  }
}
