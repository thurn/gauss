package ca.thurn.noughts.shared;

import static ca.thurn.noughts.shared.ModelTest.map;
import static ca.thurn.noughts.shared.ModelTest.list;
import ca.thurn.testing.SharedTestCase;

public class GameTest extends SharedTestCase {
  public void testSortOrder() {
    Game.Builder g1 = Game.newBuilder().setId("one");
    Game.Builder g2 = Game.newBuilder().setId("two");
    g1.setLastModified(100L);
    g2.setLastModified(200L);
    assertTrue(g1.build().compareTo(g2.build()) > 0);
  }
  
  public void testCurrentAction() {
    Game.Builder test = Game.newBuilder().setId("foo");
    assertFalse(test.hasCurrentActionNumber());
    test.setCurrentActionNumber(0);
    test.addAction(Action.newBuilder().setPlayerNumber(0).build());
    assertTrue(test.hasCurrentActionNumber());
    assertEquals(0, test.getAction(test.getCurrentActionNumber()).getPlayerNumber());
  }

  public void testLastUpdatedString() {
    long currentTime = 300000000000L;
    Game.Builder testGame = Game.newBuilder().setId("test");
    testGame.setGameOver(false);
    testGame.setLocalMultiplayer(false);
    Clock.getInstance().setCurrentTimeForTesting(currentTime);
    testGame.setLastModified(currentTime - 157700000000L);
    assertEquals("Updated 5 years ago", testGame.build().lastUpdatedString(""));
    testGame.setLastModified(currentTime - 31556952000L);
    assertEquals("Updated a year ago", testGame.build().lastUpdatedString(""));
    testGame.setLastModified(currentTime - 5259490000L);
    assertEquals("Updated 2 months ago", testGame.build().lastUpdatedString(""));
    testGame.setLastModified(currentTime - 1555200000L);
    assertEquals("Updated 2 weeks ago", testGame.build().lastUpdatedString(""));
    testGame.setLastModified(currentTime - 432000000L);
    assertEquals("Updated 5 days ago", testGame.build().lastUpdatedString(""));
    testGame.setLastModified(currentTime - 10800000L);
    assertEquals("Updated 3 hours ago", testGame.build().lastUpdatedString(""));
    testGame.setLastModified(currentTime - 3600000L);
    assertEquals("Updated an hour ago", testGame.build().lastUpdatedString(""));
    testGame.setLastModified(currentTime - 240000L);
    assertEquals("Updated 4 minutes ago", testGame.build().lastUpdatedString(""));
    testGame.setLastModified(currentTime - 3000L);
    assertEquals("Updated 3 seconds ago", testGame.build().lastUpdatedString(""));
    testGame.setLastModified(currentTime);
    assertEquals("Updated a second ago", testGame.build().lastUpdatedString(""));
    
    testGame = Game.newBuilder().setId("test2");
    testGame.setGameOver(true);
    testGame.setLocalMultiplayer(false);
    testGame.addPlayer("viewerId");
    testGame.addPlayer("opponentId");
    testGame.addVictor(0);
    testGame.setLastModified(currentTime);
    assertEquals("You won a second ago", testGame.build().lastUpdatedString("viewerId"));
    
    testGame = Game.newBuilder().setId("test3");
    testGame.setGameOver(true);
    testGame.setLocalMultiplayer(false);
    testGame.addPlayer("viewerId");
    testGame.addPlayer("opponentId");
    testGame.addVictor(1);
    testGame.setLastModified(currentTime);
    assertEquals("They won a second ago", testGame.build().lastUpdatedString("viewerId"));
    
    testGame = Game.newBuilder().setId("test3");
    testGame.setGameOver(true);
    testGame.setLocalMultiplayer(false);
    testGame.addPlayer("viewerId");
    testGame.addPlayer("opponentId");
    testGame.addVictor(1);
    Profile.Builder opponentProfile = Profile.newBuilder();
    opponentProfile.setName("Opponent");
    opponentProfile.setPronoun(Pronoun.FEMALE);
    testGame.putProfile("opponentId", opponentProfile.build());
    testGame.setLastModified(currentTime);
    assertEquals("She won a second ago", testGame.build().lastUpdatedString("viewerId"));
    
    testGame = Game.newBuilder().setId("test3");
    testGame.setGameOver(true);
    testGame.setLocalMultiplayer(false);
    testGame.addPlayer("viewerId");
    testGame.addPlayer("opponentId");
    testGame.addVictor(0);
    testGame.addVictor(1);
    testGame.setLastModified(currentTime);
    assertEquals("Game tied a second ago", testGame.build().lastUpdatedString("viewerId"));
  }
  
  public void testGetOpponentPlayerNumber() {
    Game.Builder testGame = Game.newBuilder().setId("one");
    testGame.addPlayer("viewerId");
    testGame.addPlayer("opponentId");
    assertEquals(1, testGame.build().opponentPlayerNumber("viewerId"));
    testGame = Game.newBuilder().setId("two");
    try {
      testGame.build().opponentPlayerNumber("viewerId");
      fail();
    } catch (IllegalStateException expected) {}
  }
  
  public void testGetOpponentProfile() {
    Game.Builder g1 = Game.newBuilder().setId("g1");
    g1.addPlayer("user");
    g1.addPlayer("user");
    Profile john = Profile.newBuilder().setName("John").build();
    g1.putProfile("user", john);
    try {
      g1.build().opponentProfile("user");
      fail();
    } catch (IllegalStateException expected) {}
    
    Game.Builder g2 = Game.newBuilder().setId("g2");
    g2.addPlayer("user1");
    g2.addPlayer("user2");
    g2.putProfile("user2", john);
    g2.addLocalProfile(Profile.newBuilder().build());
    g2.addLocalProfile(Profile.newBuilder().setName("Jane").build());
    assertEquals("Jane", g2.build().opponentProfile("user1").getName());
    
    Game.Builder g3 = Game.newBuilder().setId("g3");
    g3.addPlayer("user1");
    g3.addPlayer("user2");
    g3.putProfile("user2", john);
    assertEquals("John", g3.build().opponentProfile("user1").getName());
    
    try {
      g3.build().opponentProfile("user2");
      fail();
    } catch (IllegalStateException expected) {}
  }
  
  public void testGetPlayerProfile() {
    Game.Builder g1 = Game.newBuilder().setId("g1");
    g1.addPlayer("user");
    g1.addPlayer("opponent");        
    g1.putProfile("user",  Profile.newBuilder().setName("John").build());
    assertEquals("John", g1.build().playerProfile(0).getName());
    try {
      g1.build().playerProfile(1);
      fail();
    } catch (IllegalArgumentException expected) {}
    g1.addLocalProfile(Profile.newBuilder().setName("James").build());
    assertEquals("James", g1.build().playerProfile(0).getName());
  }
  
  public void testGetPlayerNumbersForPlayerId() {
    Game.Builder test = Game.newBuilder().setId("one");
    test.addPlayer("viewerid");
    test.addPlayer("viewerid");
    assertDeepEquals(list(0, 1), test.build().playerNumbersForPlayerId("viewerid"));
  }

  public void testVsString() {
    Game.Builder testGame = Game.newBuilder().setId("one");
    testGame.setLocalMultiplayer(true);
    assertEquals("Local Multiplayer Game", testGame.build().vsString(""));
    testGame = Game.newBuilder().setId("two");
    testGame.setLocalMultiplayer(false);
    testGame.addPlayer("one");
    assertEquals("vs. (No Opponent Yet)", testGame.build().vsString(""));
    testGame = Game.newBuilder().setId("four");
    testGame.setLocalMultiplayer(false);    
    testGame.addPlayer("one");
    testGame.addPlayer("two");
    Profile profile = Profile.newBuilder().setName("GivenName").build();
    testGame.putProfile("two", profile);
    assertEquals("vs. GivenName", testGame.build().vsString("one"));
  }
  
  public void testMinimalGame() {
    Game.Builder testGame = Game.newBuilder().setId("id");
    testGame.addAction(Action.newBuilder().build());
    assertEquals("id", testGame.build().minimalGame().getId());
    assertEquals(0, testGame.build().minimalGame().getActionCount());
  }
  
  public void testHasOpponent() {
    Game.Builder test = Game.newBuilder().setId("id");
    assertFalse(test.build().hasOpponent("user"));
    test.addPlayer("user");
    assertFalse(test.build().hasOpponent("user"));
    test.addPlayer("user");
    assertFalse(test.build().hasOpponent("user"));
    test.clearPlayerList();
    test.addPlayer("user");
    test.addPlayer("other");
    assertTrue(test.build().hasOpponent("user"));
  }
  
  public void testGetImageList() {
    Game.Builder testGame = getTestGame();
    ImageString string = newImageString("thestring");
    assertDeepEquals(list(string), testGame.build().imageList("userid"));
  }
  
  public void testGetImageListLocalMultiplayer() {
    Game.Builder testGame = getTestGame();
    testGame.setLocalMultiplayer(true);
    ImageString image1 = newImageString("one");
    testGame.addLocalProfile(
        Profile.newBuilder().setName("Alpha").setImageString(image1).build());
    ImageString image2 = newImageString("two");
    testGame.addLocalProfile(
        Profile.newBuilder().setName("Beta").setImageString(image2).build());
    assertDeepEquals(list(image1, image2), testGame.build().imageList("userid"));
  }

  private Game.Builder getTestGame() {
    return Game.newDeserializer().deserialize(map(
        "id", "gameId",
        "players", list("userid", "opponentid"),
        "currentPlayerNumber", 0,
        "profiles", map(
          "userid", map(
            "pronoun", "FEMALE",
            "imageString", map(
              "string", "otherstring",
              "type", "LOCAL"
            ),
            "name", "Player 1"
           ),            
          "opponentid", map(
            "pronoun", "NEUTRAL",
            "imageString", map(
                "string", "thestring",
                "type", "LOCAL"
              ),
            "name", "Player 2"
          ) 
        )
      )).toBuilder();
  }
  
  public void testImageListNoOpponent() {
    Game.Builder testGame = Game.newBuilder().setId("id");
    testGame.setLocalMultiplayer(false);
    testGame.addPlayer("userid");
    assertEquals(list(Game.NO_OPPONENT_IMAGE_STRING), testGame.build().imageList("userid"));
  }
  
  public void testGameStatus() {
    Game.Builder testGame = getTestGame();
    GameStatus status = testGame.build().gameStatus();
    assertEquals("Player 1's turn", status.getStatusString());
    assertEquals(0, status.getStatusPlayer());
    assertEquals(newImageString("otherstring"), status.getStatusImageString());
  }
  
  public void testGameStatusGameOver() {
    Game.Builder testGame = getTestGame();
    testGame.setGameOver(true);
    testGame.addVictor(1);
    GameStatus winnerStatus = testGame.build().gameStatus();
    assertEquals("Player 2 won the game!", winnerStatus.getStatusString());
    assertEquals(newImageString("thestring"),
        winnerStatus.getStatusImageString());
    assertEquals(1, winnerStatus.getStatusPlayer());
    
    testGame.addVictor(0);
    GameStatus drawStatus = testGame.build().gameStatus();
    assertEquals("Game drawn.", drawStatus.getStatusString());
    assertEquals(Game.GAME_OVER_IMAGE_STRING, drawStatus.getStatusImageString());
    assertFalse(drawStatus.hasStatusPlayer());
  }
  
  private ImageString newImageString(String name) {
    return ImageString.newBuilder().setString(name).setType(ImageType.LOCAL).build();
  }
}
