package ca.thurn.noughts.shared;

import static ca.thurn.noughts.shared.ModelTest.map;
import static ca.thurn.noughts.shared.ModelTest.list;
import ca.thurn.noughts.shared.entities.Action;
import ca.thurn.noughts.shared.entities.Game;
import ca.thurn.noughts.shared.entities.GameStatus;
import ca.thurn.noughts.shared.entities.ImageString;
import ca.thurn.noughts.shared.entities.ImageType;
import ca.thurn.noughts.shared.entities.Profile;
import ca.thurn.noughts.shared.entities.Pronoun;
import ca.thurn.testing.SharedTestCase;

public class GameTest extends SharedTestCase {
  public void testSortOrder() {
    Game.Builder g1 = Game.newBuilder().setId("one");
    Game.Builder g2 = Game.newBuilder().setId("two");
    g1.setLastModified(100L);
    g2.setLastModified(200L);
    assertTrue(Games.compareGames(g1.build(), (g2.build())) > 0);
  }

  public void testLastUpdatedString() {
    long currentTime = 300000000000L;
    Game.Builder testGame = Game.newBuilder().setId("test");
    testGame.setIsGameOver(false);
    testGame.setIsLocalMultiplayer(false);
    Clock.getInstance().setCurrentTimeForTesting(currentTime);
    testGame.setLastModified(currentTime - 157700000000L);
    assertEquals("Updated 5 years ago", Games.lastUpdatedString(testGame.build(), ""));
    testGame.setLastModified(currentTime - 31556952000L);
    assertEquals("Updated a year ago", Games.lastUpdatedString(testGame.build(), ""));
    testGame.setLastModified(currentTime - 5259490000L);
    assertEquals("Updated 2 months ago", Games.lastUpdatedString(testGame.build(), ""));
    testGame.setLastModified(currentTime - 1555200000L);
    assertEquals("Updated 2 weeks ago", Games.lastUpdatedString(testGame.build(), ""));
    testGame.setLastModified(currentTime - 432000000L);
    assertEquals("Updated 5 days ago", Games.lastUpdatedString(testGame.build(), ""));
    testGame.setLastModified(currentTime - 10800000L);
    assertEquals("Updated 3 hours ago", Games.lastUpdatedString(testGame.build(), ""));
    testGame.setLastModified(currentTime - 3600000L);
    assertEquals("Updated an hour ago", Games.lastUpdatedString(testGame.build(), ""));
    testGame.setLastModified(currentTime - 240000L);
    assertEquals("Updated 4 minutes ago", Games.lastUpdatedString(testGame.build(), ""));
    testGame.setLastModified(currentTime - 3000L);
    assertEquals("Updated 3 seconds ago", Games.lastUpdatedString(testGame.build(), ""));
    testGame.setLastModified(currentTime);
    assertEquals("Updated a second ago", Games.lastUpdatedString(testGame.build(), ""));
    
    testGame = Game.newBuilder().setId("test2");
    testGame.setIsGameOver(true);
    testGame.setIsLocalMultiplayer(false);
    testGame.addPlayer("viewerId");
    testGame.addPlayer("opponentId");
    testGame.addVictor(0);
    testGame.setLastModified(currentTime);
    assertEquals("You won a second ago", Games.lastUpdatedString(testGame.build(), "viewerId"));
    
    testGame = Game.newBuilder().setId("test3");
    testGame.setIsGameOver(true);
    testGame.setIsLocalMultiplayer(false);
    testGame.addPlayer("viewerId");
    testGame.addPlayer("opponentId");
    testGame.addVictor(1);
    testGame.setLastModified(currentTime);
    assertEquals("They won a second ago", Games.lastUpdatedString(testGame.build(), "viewerId"));
    
    testGame = Game.newBuilder().setId("test3");
    testGame.setIsGameOver(true);
    testGame.setIsLocalMultiplayer(false);
    testGame.addPlayer("viewerId");
    testGame.addPlayer("opponentId");
    testGame.addVictor(1);
    Profile.Builder opponentProfile = Profile.newBuilder();
    opponentProfile.setName("Opponent");
    opponentProfile.setPronoun(Pronoun.FEMALE);
    testGame.putProfile("opponentId", opponentProfile.build());
    testGame.setLastModified(currentTime);
    assertEquals("She won a second ago", Games.lastUpdatedString(testGame.build(), "viewerId"));
    
    testGame = Game.newBuilder().setId("test3");
    testGame.setIsGameOver(true);
    testGame.setIsLocalMultiplayer(false);
    testGame.addPlayer("viewerId");
    testGame.addPlayer("opponentId");
    testGame.addVictor(0);
    testGame.addVictor(1);
    testGame.setLastModified(currentTime);
    assertEquals("Game tied a second ago", Games.lastUpdatedString(testGame.build(), "viewerId"));
  }
  
  public void testGetOpponentPlayerNumber() {
    Game.Builder testGame = Game.newBuilder().setId("one");
    testGame.addPlayer("viewerId");
    testGame.addPlayer("opponentId");
    assertEquals(1, Games.opponentPlayerNumber(testGame.build(), ("viewerId")));
    testGame = Game.newBuilder().setId("two");
    try {
      Games.opponentPlayerNumber(testGame.build(),("viewerId"));
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
      Games.opponentProfile(g1.build(),("user"));
      fail();
    } catch (IllegalStateException expected) {}
    
    Game.Builder g2 = Game.newBuilder().setId("g2");
    g2.addPlayer("user1");
    g2.addPlayer("user2");
    g2.putProfile("user2", john);
    g2.addLocalProfile(Profile.newBuilder().build());
    g2.addLocalProfile(Profile.newBuilder().setName("Jane").build());
    assertEquals("Jane", Games.opponentProfile(g2.build(), "user1").getName());
    
    Game.Builder g3 = Game.newBuilder().setId("g3");
    g3.addPlayer("user1");
    g3.addPlayer("user2");
    g3.putProfile("user2", john);
    assertEquals("John", Games.opponentProfile(g3.build(), "user1").getName());
    
    try {
      Games.opponentProfile(g3.build(), "user2");
      fail();
    } catch (IllegalStateException expected) {}
  }
  
  public void testGetPlayerProfile() {
    Game.Builder g1 = Game.newBuilder().setId("g1");
    g1.addPlayer("user");
    g1.addPlayer("opponent");        
    g1.putProfile("user",  Profile.newBuilder().setName("John").build());
    assertEquals("John", Games.playerProfile(g1.build(),0).getName());
    try {
      Games.playerProfile(g1.build(),1);
      fail();
    } catch (IllegalArgumentException expected) {}
    g1.addLocalProfile(Profile.newBuilder().setName("James").build());
    assertEquals("James", Games.playerProfile(g1.build(),0).getName());
  }
  
  public void testGetPlayerNumbersForPlayerId() {
    Game.Builder test = Game.newBuilder().setId("one");
    test.addPlayer("viewerid");
    test.addPlayer("viewerid");
    assertDeepEquals(list(0, 1), Games.playerNumbersForPlayerId(test.build(),"viewerid"));
  }

  public void testVsString() {
    Game.Builder testGame = Game.newBuilder().setId("one");
    testGame.setIsLocalMultiplayer(true);
    assertEquals("Local Multiplayer Game", Games.vsString(testGame.build(),""));
    testGame = Game.newBuilder().setId("two");
    testGame.setIsLocalMultiplayer(false);
    testGame.addPlayer("one");
    assertEquals("vs. (No Opponent Yet)", Games.vsString(testGame.build(),""));
    testGame = Game.newBuilder().setId("four");
    testGame.setIsLocalMultiplayer(false);    
    testGame.addPlayer("one");
    testGame.addPlayer("two");
    Profile profile = Profile.newBuilder().setName("GivenName").build();
    testGame.putProfile("two", profile);
    assertEquals("vs. GivenName", Games.vsString(testGame.build(),"one"));
  }
  
  public void testMinimalGame() {
    Game.Builder testGame = Game.newBuilder().setId("id");
    testGame.addSubmittedAction(Action.newBuilder().build());
    assertEquals("id", Games.minimalGame(testGame.build()).getId());
    assertEquals(0, Games.minimalGame(testGame.build()).getSubmittedActionCount());
  }
  
  public void testHasOpponent() {
    Game.Builder test = Game.newBuilder().setId("id");
    assertFalse(Games.hasOpponent(test.build(),"user"));
    test.addPlayer("user");
    assertFalse(Games.hasOpponent(test.build(),"user"));
    test.addPlayer("user");
    assertFalse(Games.hasOpponent(test.build(),"user"));
    test.clearPlayerList();
    test.addPlayer("user");
    test.addPlayer("other");
    assertTrue(Games.hasOpponent(test.build(),"user"));
  }
  
  public void testGetImageList() {
    Game.Builder testGame = getTestGame();
    ImageString string = newImageString("thestring");
    assertDeepEquals(list(string), Games.imageList(testGame.build(),"userid"));
  }
  
  public void testGetImageListLocalMultiplayer() {
    Game.Builder testGame = getTestGame();
    testGame.setIsLocalMultiplayer(true);
    ImageString image1 = newImageString("one");
    testGame.addLocalProfile(
        Profile.newBuilder().setName("Alpha").setImageString(image1).build());
    ImageString image2 = newImageString("two");
    testGame.addLocalProfile(
        Profile.newBuilder().setName("Beta").setImageString(image2).build());
    assertDeepEquals(list(image1, image2), Games.imageList(testGame.build(),"userid"));
  }

  private Game.Builder getTestGame() {
    return Game.newDeserializer().deserialize(map(
        "id", "gameId",
        "playerList", list("userid", "opponentid"),
        "currentPlayerNumber", 0,
        "profileMap", map(
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
    testGame.setIsLocalMultiplayer(false);
    testGame.addPlayer("userid");
    assertEquals(list(Games.NO_OPPONENT_IMAGE_STRING), Games.imageList(testGame.build(),"userid"));
  }
  
  public void testGameStatus() {
    Game.Builder testGame = getTestGame();
    GameStatus status = Games.gameStatus(testGame.build());
    assertEquals("Player 1's turn", status.getStatusString());
    assertEquals(0, status.getStatusPlayer());
    assertEquals(newImageString("otherstring"), status.getStatusImageString());
  }
  
  public void testGameStatusGameOver() {
    Game.Builder testGame = getTestGame();
    testGame.setIsGameOver(true);
    testGame.addVictor(1);
    GameStatus winnerStatus = Games.gameStatus(testGame.build());
    assertEquals("Player 2 won the game!", winnerStatus.getStatusString());
    assertEquals(newImageString("thestring"),
        winnerStatus.getStatusImageString());
    assertEquals(1, winnerStatus.getStatusPlayer());
    
    testGame.addVictor(0);
    GameStatus drawStatus = Games.gameStatus(testGame.build());
    assertEquals("Game drawn.", drawStatus.getStatusString());
    assertEquals(Games.GAME_OVER_IMAGE_STRING, drawStatus.getStatusImageString());
    assertFalse(drawStatus.hasStatusPlayer());
  }
  
  private ImageString newImageString(String name) {
    return ImageString.newBuilder().setString(name).setType(ImageType.LOCAL).build();
  }
}
