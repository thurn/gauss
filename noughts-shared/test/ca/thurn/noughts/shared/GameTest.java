package ca.thurn.noughts.shared;

import static ca.thurn.noughts.shared.ModelTest.map;
import static ca.thurn.noughts.shared.ModelTest.list;
import ca.thurn.noughts.shared.Game.GameStatus;
import ca.thurn.noughts.shared.ImageString.ImageType;
import ca.thurn.noughts.shared.Profile.Pronoun;
import ca.thurn.testing.SharedTestCase;

public class GameTest extends SharedTestCase {
  public void testSortOrder() {
    Game g1 = new Game("one");
    Game g2 = new Game("two");
    g1.setLastModified(100L);
    g2.setLastModified(200L);
    assertTrue(g1.compareTo(g2) > 0);
  }
  
  public void testCurrentAction() {
    Game test = new Game("foo");
    assertFalse(test.hasCurrentAction());
    test.setCurrentActionNumber(0);
    test.getActionsMutable().add(new Action(0));
    assertTrue(test.hasCurrentAction());
    assertEquals(0, test.currentAction().getPlayerNumber());
  }

  public void testLastUpdatedString() {
    long currentTime = 300000000000L;
    Game testGame = new Game("test");
    Clock.getInstance().setCurrentTimeForTesting(currentTime);
    testGame.setLastModified(currentTime - 157700000000L);
    assertEquals("Updated 5 years ago", testGame.lastUpdatedString(""));
    testGame.setLastModified(currentTime - 31556952000L);
    assertEquals("Updated a year ago", testGame.lastUpdatedString(""));
    testGame.setLastModified(currentTime - 5259490000L);
    assertEquals("Updated 2 months ago", testGame.lastUpdatedString(""));
    testGame.setLastModified(currentTime - 1555200000L);
    assertEquals("Updated 2 weeks ago", testGame.lastUpdatedString(""));
    testGame.setLastModified(currentTime - 432000000L);
    assertEquals("Updated 5 days ago", testGame.lastUpdatedString(""));
    testGame.setLastModified(currentTime - 10800000L);
    assertEquals("Updated 3 hours ago", testGame.lastUpdatedString(""));
    testGame.setLastModified(currentTime - 3600000L);
    assertEquals("Updated an hour ago", testGame.lastUpdatedString(""));
    testGame.setLastModified(currentTime - 240000L);
    assertEquals("Updated 4 minutes ago", testGame.lastUpdatedString(""));
    testGame.setLastModified(currentTime - 3000L);
    assertEquals("Updated 3 seconds ago", testGame.lastUpdatedString(""));
    testGame.setLastModified(currentTime);
    assertEquals("Updated a second ago", testGame.lastUpdatedString(""));
    
    testGame = new Game("test2");
    testGame.setGameOver(true);
    testGame.getPlayersMutable().add("viewerId");
    testGame.getPlayersMutable().add("opponentId");
    testGame.getVictorsMutable().add(0);
    testGame.setLastModified(currentTime);
    assertEquals("You won a second ago", testGame.lastUpdatedString("viewerId"));
    
    testGame = new Game("test3");
    testGame.setGameOver(true);
    testGame.getPlayersMutable().add("viewerId");
    testGame.getPlayersMutable().add("opponentId");
    testGame.getVictorsMutable().add(1);
    testGame.setLastModified(currentTime);
    assertEquals("They won a second ago", testGame.lastUpdatedString("viewerId"));
    
    testGame = new Game("test3");
    testGame.setGameOver(true);
    testGame.getPlayersMutable().add("viewerId");
    testGame.getPlayersMutable().add("opponentId");
    testGame.getVictorsMutable().add(1);
    Profile opponentProfile = new Profile("Opponent");
    opponentProfile.setPronoun(Pronoun.FEMALE);
    testGame.getProfilesMutable().put("opponentId", opponentProfile);
    testGame.setLastModified(currentTime);
    assertEquals("She won a second ago", testGame.lastUpdatedString("viewerId"));
    
    testGame = new Game("test3");
    testGame.setGameOver(true);
    testGame.getPlayersMutable().add("viewerId");
    testGame.getPlayersMutable().add("opponentId");
    testGame.getVictorsMutable().add(0);
    testGame.getVictorsMutable().add(1);
    testGame.setLastModified(currentTime);
    assertEquals("Game tied a second ago", testGame.lastUpdatedString("viewerId"));
  }
  
  public void testGetOpponentPlayerNumber() {
    Game testGame = new Game("one");
    testGame.getPlayersMutable().add("viewerId");
    testGame.getPlayersMutable().add("opponentId");
    assertEquals(1, testGame.opponentPlayerNumber("viewerId"));
    testGame = new Game("two");
    try {
      testGame.opponentPlayerNumber("viewerId");
      fail();
    } catch (IllegalStateException expected) {}
  }
  
  public void testGetOpponentProfile() {
    Game g1 = new Game("g1");
    g1.getPlayersMutable().add("user");
    g1.getPlayersMutable().add("user");
    g1.getProfilesMutable().put("user", new Profile("John"));
    try {
      g1.opponentProfile("user");
      fail();
    } catch (IllegalStateException expected) {}
    
    Game g2 = new Game("g2");
    g2.getPlayersMutable().add("user1");
    g2.getPlayersMutable().add("user2");
    g2.getProfilesMutable().put("user2", new Profile("John"));
    g2.getLocalProfilesMutable().add(null);
    g2.getLocalProfilesMutable().add(new Profile("Jane"));
    assertEquals("Jane", g2.opponentProfile("user1").getName());
    
    Game g3 = new Game("g3");
    g3.getPlayersMutable().add("user1");
    g3.getPlayersMutable().add("user2");
    g3.getProfilesMutable().put("user2", new Profile("John"));
    assertEquals("John", g3.opponentProfile("user1").getName());
    
    try {
      g3.opponentProfile("user2");
      fail();
    } catch (IllegalStateException expected) {}
  }
  
  public void testGetPlayerProfile() {
    Game g1 = new Game("g1");
    g1.getPlayersMutable().add("user");
    g1.getPlayersMutable().add("opponent");        
    g1.getProfilesMutable().put("user", new Profile("John"));
    assertEquals("John", g1.playerProfile(0).getName());
    try {
      g1.playerProfile(1);
      fail();
    } catch (IllegalArgumentException expected) {}
    g1.getLocalProfilesMutable().add(new Profile("James"));
    assertEquals("James", g1.playerProfile(0).getName());
  }
  
  public void testGetPlayerNumbersForPlayerId() {
    Game test = new Game("one");
    test.getPlayersMutable().add("viewerid");
    test.getPlayersMutable().add("viewerid");
    assertDeepEquals(list(0, 1), test.playerNumbersForPlayerId("viewerid"));
  }

  public void testVsString() {
    Game testGame = new Game("one");
    testGame.setLocalMultiplayer(true);
    assertEquals("Local Multiplayer Game", testGame.vsString(""));
    testGame = new Game("two");
    testGame.getPlayersMutable().add("one");
    assertEquals("vs. (No Opponent Yet)", testGame.vsString(""));
    testGame = new Game("four");
    testGame.getPlayersMutable().add("one");
    testGame.getPlayersMutable().add("two");
    Profile profile = new Profile("GivenName");
    testGame.getProfilesMutable().put("two", profile);
    assertEquals("vs. GivenName", testGame.vsString("one"));
  }
  
  public void testMinimalGame() {
    Game testGame = new Game("id");
    testGame.getActionsMutable().add(new Action(0));
    assertEquals("id", testGame.minimalGame().getId());
    assertEquals(0, testGame.minimalGame().getActions().size());
  }
  
  public void testHasOpponent() {
    Game test = new Game("id");
    assertFalse(test.hasOpponent("user"));
    test.getPlayersMutable().add("user");
    assertFalse(test.hasOpponent("user"));
    test.getPlayersMutable().add("user");
    assertFalse(test.hasOpponent("user"));
    test.getPlayersMutable().clear();
    test.getPlayersMutable().add("user");
    test.getPlayersMutable().add("other");
    assertTrue(test.hasOpponent("user"));
  }
  
  public void testGetImageList() {
    Game testGame = getTestGame();
    ImageString string = new ImageString("thestring", ImageType.LOCAL);
    assertDeepEquals(list(string), testGame.imageList("userid"));
  }
  
  public void testGetImageListLocalMultiplayer() {
    Game testGame = getTestGame();
    testGame.setLocalMultiplayer(true);
    ImageString image1 = new ImageString("one", ImageType.LOCAL);
    testGame.getLocalProfilesMutable().add(new Profile("Alpha").setImageString(image1));
    ImageString image2 = new ImageString("two", ImageType.LOCAL);
    testGame.getLocalProfilesMutable().add(new Profile("Beta").setImageString(image2));
    assertDeepEquals(list(image1, image2), testGame.imageList("userid"));
  }

  private Game getTestGame() {
    return new Game.GameDeserializer().deserialize(map(
        "entityVersion", 1,
        "id", "gameId",
        "players", list("userid", "opponentid"),
        "currentPlayerNumber", 0,
        "profiles", map(
          "userid", map(
            "entityVersion", 1,
            "pronoun", "FEMALE",
            "imageString", map(
              "entityVersion", 1,
              "string", "otherstring",
              "type", "LOCAL"
            ),
            "name", "Player 1"
           ),            
          "opponentid", map(
            "entityVersion", 1,
            "pronoun", "NEUTRAL",
            "imageString", map(
                "entityVersion", 1,
                "string", "thestring",
                "type", "LOCAL"
              ),
            "name", "Player 2"
          ) 
        )
      ));
  }
  
  public void testImageListNoOpponent() {
    Game testGame = new Game("id");
    testGame.getPlayersMutable().add("userid");
    assertEquals(list(Game.NO_OPPONENT_IMAGE_STRING), testGame.imageList("userid"));
  }
  
  public void testGameStatus() {
    Game testGame = getTestGame();
    GameStatus status = testGame.gameStatus();
    assertEquals("Player 1's turn", status.getStatusString());
    assertEquals(0, status.getStatusPlayer());
    assertEquals(new ImageString("otherstring", ImageType.LOCAL), status.getStatusImageString());
  }
  
  public void testGameStatusGameOver() {
    Game testGame = getTestGame();
    testGame.setGameOver(true);
    testGame.getVictorsMutable().add(1);
    GameStatus winnerStatus = testGame.gameStatus();
    assertEquals("Player 2 won the game!", winnerStatus.getStatusString());
    assertEquals(new ImageString("thestring", ImageType.LOCAL),
        winnerStatus.getStatusImageString());
    assertEquals(1, winnerStatus.getStatusPlayer());
    
    testGame.getVictorsMutable().add(0);
    GameStatus drawStatus = testGame.gameStatus();
    assertEquals("Game drawn.", drawStatus.getStatusString());
    assertEquals(Game.GAME_OVER_IMAGE_STRING, drawStatus.getStatusImageString());
    assertFalse(drawStatus.hasStatusPlayer());
  }
}
