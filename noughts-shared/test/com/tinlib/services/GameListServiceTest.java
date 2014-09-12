package com.tinlib.services;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.tinlib.asynctest.AsyncTestCase;
import com.tinlib.core.TinKeys;
import com.tinlib.error.ErrorHandler;
import com.tinlib.error.TinException;
import com.tinlib.generated.Game;
import com.tinlib.generated.GameListEntry;
import com.tinlib.generated.GameListUpdate;
import com.tinlib.generated.IndexPath;
import com.tinlib.convey.Subscriber1;
import com.tinlib.test.*;
import com.tinlib.time.TimeService;
import com.tinlib.util.Procedure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameListServiceTest extends AsyncTestCase {
  private static final String VIEWER_ID = TestUtils.newViewerId();
  private static final String VIEWER_KEY = TestUtils.newViewerKey();
  private static final String GAME_ID = TestUtils.newGameId();

  private final Game testGame = TestUtils.newGameWithTwoPlayers(VIEWER_ID, GAME_ID).build();
  private TestKeyedListenerService keyedListenerService;
  private ChildEventListener childListener;
  private ValueEventListener valueListener;
  private TestHelper testHelper;
  private GameList gameList;

  @Mock
  TimeService mockTimeService;
  @Mock
  ErrorHandler mockErrorHandler;
  @Mock
  DataSnapshot mockDataSnapshot;

  @Before
  public void setup() {
    keyedListenerService = new TestKeyedListenerService();
  }

  @Test
  public void testGameListAdd() {
    beginAsyncTestBlock();
    addGame(new Procedure<IndexPath>() {
      @Override
      public void run(IndexPath indexPath) {
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
    addGame(new Procedure<IndexPath>() {
      @Override
      public void run(IndexPath indexPath) {
        valueListener.onDataChange(new FakeDataSnapshot(
            testGame.toBuilder().setIsGameOver(true).build().serialize(), GAME_ID));
        testHelper.bus().once(TinKeys.GAME_LIST_MOVE, new Subscriber1<GameListUpdate>() {
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
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testGameListRemove() {
    beginAsyncTestBlock();
    addGame(new Procedure<IndexPath>() {
      @Override
      public void run(final IndexPath indexPath) {
        childListener.onChildRemoved(new FakeDataSnapshot(testGame.serialize(), GAME_ID));
        testHelper.bus().once(TinKeys.GAME_LIST_REMOVE, new Subscriber1<IndexPath>() {
          @Override
          public void onMessage(IndexPath removePath) {
            assertEquals(indexPath, removePath);
            finished();
          }
        });
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testGetGameListEntry() {
    beginAsyncTestBlock();
    addGame(new Procedure<IndexPath>() {
      @Override
      public void run(IndexPath indexPath) {
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
    addGame(new Procedure<IndexPath>() {
      @Override
      public void run(IndexPath indexPath) {
        assertEquals(1, gameList.gameCountForSection(GameList.YOUR_GAMES_SECTION));
        assertEquals(0, gameList.gameCountForSection(GameList.THEIR_GAMES_SECTION));
        assertEquals(0, gameList.gameCountForSection(GameList.GAME_OVER_SECTION));
        finished();
      }
    });
    endAsyncTestBlock();
  }

  @Test(expected=TinException.class)
  public void testInvalidSectionError() {
    beginAsyncTestBlock();
    addGame(new Procedure<IndexPath>() {
      @Override
      public void run(IndexPath indexPath) {
        gameList.getGameListEntry(117, 0);
        finished();
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
        GameListService gameListService = new GameListService(helper.injector());
      }
    });
    endAsyncTestBlock();
  }

  @Test
  public void testGameListValueListenerError() {
    beginAsyncTestBlock();
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.bindInstance(TimeService.class, mockTimeService);
    builder.setFirebase(new ErroringFirebase(TestHelper.FIREBASE_URL,
        "firebaseio-demo.com/games/" + GAME_ID, "addValueEventListener"));
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class,
        TestHelper.finishedErrorHandler(FINISHED_RUNNABLE));
    builder.bindInstance(KeyedListenerService.class, keyedListenerService);
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        GameListService gameListService = new GameListService(helper.injector());
        helper.bus().await(TinKeys.GAME_LIST, new Subscriber1<GameList>() {
          @Override
          public void onMessage(GameList list) {
            childListener = keyedListenerService.getChildEventListenerForKey(GameList.LISTENER_KEY);
            childListener.onChildAdded(new FakeDataSnapshot(testGame.serialize(), GAME_ID), "");
          }
        });
      }
    });
    endAsyncTestBlock();
  }

  private void addGame(final Procedure<IndexPath> onComplete) {
    TestConfiguration.Builder builder = TestConfiguration.newBuilder();
    builder.setAnonymousViewer(VIEWER_ID, VIEWER_KEY);
    builder.setFirebase(new Firebase(TestHelper.FIREBASE_URL));
    builder.setFailOnError(false);
    builder.multibindInstance(ErrorHandler.class, mockErrorHandler);
    builder.bindInstance(TimeService.class, mockTimeService);
    builder.bindInstance(KeyedListenerService.class, keyedListenerService);
    TestHelper.runTest(this, builder.build(), new Procedure<TestHelper>() {
      @Override
      public void run(final TestHelper helper) {
        testHelper = helper;
        GameListService gameListService = new GameListService(helper.injector());
        helper.bus().await(TinKeys.GAME_LIST, new Subscriber1<GameList>() {
          @Override
          public void onMessage(GameList list) {
            gameList = list;
            childListener = keyedListenerService.getChildEventListenerForKey(GameList.LISTENER_KEY);
            childListener.onChildAdded(new FakeDataSnapshot(testGame.serialize(), GAME_ID), "");
            valueListener =
                keyedListenerService.getValueEventListenerForKey(GameList.LISTENER_KEY + GAME_ID);
            valueListener.onDataChange(new FakeDataSnapshot(testGame.serialize(), GAME_ID));
            helper.bus().once(TinKeys.GAME_LIST_ADD, new Subscriber1<IndexPath>() {
              @Override
              public void onMessage(IndexPath indexPath) {
                onComplete.run(indexPath);
              }
            });
          }
        });
      }
    });
  }
}
