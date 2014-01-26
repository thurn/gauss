package ca.thurn.noughts.shared;

import java.util.HashMap;
import java.util.Map;

import ca.thurn.noughts.shared.Game.VsType;
import ca.thurn.testing.SharedTestCase;

public class GameTest extends SharedTestCase {
  public void testSortOrder() {
    Game g1 = new Game("one");
    Game g2 = new Game("two");
    g1.setLastModified(100L);
    g2.setLastModified(200L);
    assertTrue(g1.compareTo(g2) > 0);
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
    Map<String, String> profile = new HashMap<String, String>();
    profile.put("gender", "female");
    testGame.getProfilesMutable().put("opponentId", profile);
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
    assertEquals(1, testGame.getOpponentPlayerNumber("viewerId"));
    testGame = new Game("two");
    assertEquals(-1, testGame.getOpponentPlayerNumber("viewerId"));
  }
  
  public void testVsType() {
    Game testGame = new Game("one");
    testGame.setLocalMultiplayer(true);
    assertEquals(VsType.LOCAL_MULTIPLAYER, testGame.getVsType(""));
    testGame = new Game("two");
    testGame.getPlayersMutable().add("one");
    assertEquals(VsType.NO_OPPONENT, testGame.getVsType(""));
    testGame = new Game("three");
    testGame.getPlayersMutable().add("one");
    testGame.getPlayersMutable().add("two");
    assertEquals(VsType.ANONYMOUS_OPPONENT, testGame.getVsType(""));
    testGame = new Game("four");
    testGame.getPlayersMutable().add("one");
    testGame.getPlayersMutable().add("two");
    Map<String, String> profile = new HashMap<String, String>();
    testGame.getProfilesMutable().put("two", profile);
    assertEquals(VsType.OPPONENT_WITH_PROFILE, testGame.getVsType("one"));
  }

  public void testVsString() {
    Game testGame = new Game("one");
    testGame.setLocalMultiplayer(true);
    assertEquals("Local Multiplayer Game", testGame.vsString(""));
    testGame = new Game("two");
    testGame.getPlayersMutable().add("one");
    assertEquals("vs. (No Opponent Yet)", testGame.vsString(""));
    testGame = new Game("three");
    testGame.getPlayersMutable().add("one");
    testGame.getPlayersMutable().add("two");
    assertEquals("vs. Anonymous", testGame.vsString(""));
    testGame = new Game("four");
    testGame.getPlayersMutable().add("one");
    testGame.getPlayersMutable().add("two");
    Map<String, String> profile = new HashMap<String, String>();
    profile.put("givenName", "GivenName");
    testGame.getProfilesMutable().put("two", profile);
    assertEquals("vs. GivenName", testGame.vsString("one"));
  }
}
