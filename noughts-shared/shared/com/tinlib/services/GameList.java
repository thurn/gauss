package com.tinlib.services;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.tinlib.core.TinKeys;
import com.tinlib.core.TinMessages;
import com.tinlib.error.ErrorService;
import com.tinlib.error.TinException;
import com.tinlib.generated.Game;
import com.tinlib.generated.GameListEntry;
import com.tinlib.generated.GameListUpdate;
import com.tinlib.generated.IndexPath;
import com.tinlib.inject.Injector;
import com.tinlib.message.Bus;
import com.tinlib.time.TimeService;
import com.tinlib.util.AbstractChildEventListener;
import com.tinlib.util.Games;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GameList {
  public static final String LISTENER_KEY = "tin.GameListService";
  public static final int YOUR_GAMES_SECTION = 0;
  public static final int THEIR_GAMES_SECTION = 1;
  public static final int GAME_OVER_SECTION = 2;
  public static final int NUM_SECTIONS = 3;

  public static int[] allSections() {
    return new int[] {GameList.YOUR_GAMES_SECTION, GameList.THEIR_GAMES_SECTION,
        GameList.GAME_OVER_SECTION};
  }

  private class GameChildListener extends AbstractChildEventListener {
    private GameChildListener() {
      super(errorService, "GameChildListener");
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String previous) {
      String gameId = dataSnapshot.getName();
      keyedListenerService.addValueEventListener(firebaseReferences.gameReference(gameId),
          LISTENER_KEY + gameId, new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          listLock.lock();
          try {
            Game game = Game.newDeserializer().fromDataSnapshot(dataSnapshot);
            Optional<IndexPath> location = findGame(game.getId());
            IndexPath destination = destinationForGame(game);
            if (location.isPresent()) {
              moveGame(location.get(), destination, game);
              bus.produce(TinMessages.GAME_LIST_MOVE,
                  GameListUpdate.newBuilder().setFrom(location.get()).setTo(destination).build());
            } else {
              addGame(destination, game);
              bus.produce(TinMessages.GAME_LIST_ADD, destination);
            }
          } finally {
            listLock.unlock();
          }
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
          errorService.error("Game value listener cancelled. %s", firebaseError);
        }
      });
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
      listLock.lock();
      try {
        String gameId = dataSnapshot.getName();
        Optional<IndexPath> location = findGame(gameId);
        if (location.isPresent()) {
          removeGame(location.get());
          bus.produce(TinMessages.GAME_LIST_REMOVE, location.get());
        }
      } finally {
        listLock.unlock();
      }
    }
  }

  private final Lock listLock = new ReentrantLock();
  private final List<Game> yourTurn = Lists.newArrayList();
  private final List<Game> theirTurn = Lists.newArrayList();
  private final List<Game> gameOver = Lists.newArrayList();
  private final Bus bus;
  private final ErrorService errorService;
  private final KeyedListenerService keyedListenerService;
  private final TimeService timeService;
  private final FirebaseReferences firebaseReferences;
  private final String viewerId;

  public GameList(Injector injector, String viewerId, FirebaseReferences references) {
    bus = injector.get(TinKeys.BUS);
    errorService = injector.get(TinKeys.ERROR_SERVICE);
    keyedListenerService = injector.get(TinKeys.KEYED_LISTENER_SERVICE);
    timeService = injector.get(TinKeys.TIME_SERVICE);
    this.viewerId = viewerId;
    this.firebaseReferences = references;
    keyedListenerService.addChildEventListener(references.userGames(), LISTENER_KEY,
        new GameChildListener());
  }

  public Game getGame(int section, int row) {
    return gameListForSection(section).get(row);
  }

  public GameListEntry getGameListEntry(int section, int row) {
    return Games.gameListEntry(timeService, getGame(section, row), viewerId);
  }

  public int gameCountForSection(int section) {
    return gameListForSection(section).size();
  }

  public Lock getListLock() {
    return listLock;
  }

  public String nameForSection(int section) {
    switch (section) {
      case YOUR_GAMES_SECTION:
        return "Games - Your Turn";
      case THEIR_GAMES_SECTION:
        return "Games - Their Turn";
      case GAME_OVER_SECTION:
        return "Games - Game Over";
      default:
        throw new TinException("Unknown game list section '%s'", section);
    }
  }

  private List<Game> gameListForSection(int section) {
    switch (section) {
      case YOUR_GAMES_SECTION:
        return yourTurn;
      case THEIR_GAMES_SECTION:
        return theirTurn;
      case GAME_OVER_SECTION:
        return gameOver;
      default:
        throw errorService.newTinException("Unknown game list section '%s'", section);
    }
  }

  private Optional<IndexPath> findGame(String gameId) {
    for (int section : allSections()) {
      Optional<IndexPath> result = findGameInSection(gameId, section);
      if (result.isPresent()) return result;
    }
    return Optional.absent();
  }

  private Optional<IndexPath> findGameInSection(String gameId, int section) {
    List<Game> gameList = gameListForSection(section);
    for (int i = 0; i < gameList.size(); ++i) {
      if (gameList.get(i).getId().equals(gameId)) {
        return Optional.of(IndexPath.newBuilder().setRow(i).setSection(section).build());
      }
    }
    return Optional.absent();
  }

  private IndexPath destinationForGame(Game game) {
    int section = sectionForGame(game);
    int row = rowForGame(gameListForSection(section), game);
    return IndexPath.newBuilder().setRow(row).setSection(section).build();
  }

  private int sectionForGame(Game game) {
    if (game.getIsGameOver()) {
      return GAME_OVER_SECTION;
    }
    if (game.getIsLocalMultiplayer() ||
        (Games.hasCurrentPlayer(game) && Games.currentPlayerId(game).equals(viewerId))) {
      return YOUR_GAMES_SECTION;
    } else {
      return THEIR_GAMES_SECTION;
    }
  }

  private int rowForGame(List<Game> gameList, Game game) {
    int row = 0;
    while (row < gameList.size() && gameList.get(row).getLastModified() > game.getLastModified()) {
      row++;
    }
    return row;
  }

  private void addGame(IndexPath location, Game game) {
    gameListForSection(location.getSection()).add(location.getRow(), game);
  }

  private void removeGame(IndexPath location) {
    gameListForSection(location.getSection()).remove(location.getRow());
  }

  private void moveGame(IndexPath fromLocation, IndexPath toLocation, Game game) {
    removeGame(fromLocation);
    addGame(toLocation, game);
  }
}