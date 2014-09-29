package com.tinlib.services;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.tinlib.asynctest.AsyncTestCase;
import com.tinlib.convey.Subscriber1;
import com.tinlib.core.TinKeys;
import com.tinlib.error.ErrorHandler;
import com.tinlib.error.TinException;
import com.tinlib.erroringfirebase.ErroringFirebase;
import com.tinlib.generated.Game;
import com.tinlib.generated.GameListEntry;
import com.tinlib.generated.GameListUpdate;
import com.tinlib.generated.IndexPath;
import com.tinlib.test.TestConfiguration;
import com.tinlib.test.TestHelper;
import com.tinlib.test.TestUtils;
import com.tinlib.time.TimeService;
import com.tinlib.util.Procedure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameListServiceTest extends AsyncTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  private static interface OnAdd {
    public void onAdd(TestHelper helper, GameList gameList, IndexPath indexPath);
  }

  @Mock
  TimeService mockTimeService;
  @Mock
  ErrorHandler mockErrorHandler;
  @Mock
  DataSnapshot mockDataSnapshot;

  @Test
  public void testGameListAdd() {
    beginAsyncTestBlock();
    Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    runWithGame(testGame, new OnAdd() {
      @Override
      public void onAdd(TestHelper helper, GameList gameList, IndexPath indexPath) {
        IndexPath expected = IndexPath.newBuilder()
            .setSection(GameList.YOUR_GAMES_SECTION)
            .setRow(0)
            .build();
        assertEquals(expected, indexPath);
        finished();
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testGameListMove() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    runWithGame(testGame, new OnAdd() {
      @Override
      public void onAdd(TestHelper helper, GameList gameList, IndexPath indexPath) {
        helper.bus().once(TinKeys.GAME_LIST_MOVE, new Subscriber1<GameListUpdate>() {
          @Override
          public void onMessage(GameListUpdate update) {
            IndexPath expectedFrom = IndexPath.newBuilder()
                .setSection(GameList.YOUR_GAMES_SECTION)
                .setRow(0)
                .build();
            IndexPath expectedTo = IndexPath.newBuilder()
                .setSection(GameList.GAME_OVER_SECTION)
                .setRow(0)
                .build();
            assertEquals(expectedFrom, update.getFrom());
            assertEquals(expectedTo, update.getTo());
            finished();
          }
        });
        helper.references().gameReference(GAME_ID).setValue(
            testGame.toBuilder().setIsGameOver(true).build().serialize());
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testGameListRemove() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    runWithGame(testGame, new OnAdd() {
      @Override
      public void onAdd(TestHelper helper, GameList gameList, final IndexPath indexPath) {
        helper.bus().once(TinKeys.GAME_LIST_REMOVE, new Subscriber1<IndexPath>() {
          @Override
          public void onMessage(IndexPath removePath) {
            assertEquals(indexPath, removePath);
            finished();
          }
        });
        helper.references().userReferenceForGame(GAME_ID).removeValue();
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testGetGameListEntry() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    runWithGame(testGame, new OnAdd() {
      @Override
      public void onAdd(TestHelper helper, GameList gameList, IndexPath indexPath) {
        when(mockTimeService.currentTimeMillis()).thenReturn(333L);
        GameListEntry entry = gameList.getGameListEntry(indexPath.getSection(), indexPath.getRow());
        assertEquals("vs. Opponent", entry.getVsString());
        finished();
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testGetGameCountForSection() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    runWithGame(testGame, new OnAdd() {
      @Override
      public void onAdd(TestHelper helper, GameList gameList, IndexPath indexPath) {
        assertEquals(1, gameList.gameCountForSection(GameList.YOUR_GAMES_SECTION));
        assertEquals(0, gameList.gameCountForSection(GameList.THEIR_GAMES_SECTION));
        assertEquals(0, gameList.gameCountForSection(GameList.GAME_OVER_SECTION));
        finished();
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testInvalidSectionError() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    runWithGame(testGame, new OnAdd() {
      @Override
      public void onAdd(TestHelper helper, GameList gameList, IndexPath indexPath) {
        try {
          gameList.getGameListEntry(117, 0);
          fail("Expected exception");
        } catch (TinException exception) {
          finished();
        }
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testGameListChildListenerError() {
    beginAsyncTestBlock();
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.bindInstance(TimeService.class, mockTimeService);
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "users/" + VIEWER_KEY + "/games", "addChildEventListener"));
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class,
        TestHelper.finishedErrorHandler(FINISHED_RUNNABLE));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        GameListService gameListService = helper.injector().get(GameListService.class);
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testGameListValueListenerError() {
    beginAsyncTestBlock();
    final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.bindInstance(TimeService.class, mockTimeService);
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "firebaseio-demo.com/games/" + GAME_ID, "addValueEventListener"));
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class,
        TestHelper.finishedErrorHandler(FINISHED_RUNNABLE));
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        GameListService gameListService = helper.injector().get(GameListService.class);
        helper.bus().await(TinKeys.GAME_LIST, new Subscriber1<GameList>() {
          @Override
          public void onMessage(GameList list) {
            helper.references().userReferenceForGame(GAME_ID).setValue(testGame.serialize());
          }
        });
      }
    });
    endAsyncTestBlock();
  }

  private void runWithGame(final Game game, final OnAdd onAdd) {
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class, mockErrorHandler);
    builder.bindInstance(TimeService.class, mockTimeService);
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        helper.bus().once(TinKeys.GAME_LIST, new Subscriber1<GameList>() {
          @Override
          public void onMessage(final GameList gameList) {
            helper.bus().once(TinKeys.GAME_LIST_ADD, new Subscriber1<IndexPath>() {
              @Override
              public void onMessage(IndexPath indexPath) {
                onAdd.onAdd(helper, gameList, indexPath);
              }
            });
            helper.references().userReferenceForGame(GAME_ID).setValue(game.serialize());
            helper.references().gameReference(GAME_ID).setValue(game.serialize());
          }
        });
      }
    });
  }
}
