package ca.thurn.noughts.shared;

import ca.thurn.noughts.shared.entities.Pronoun;

public class Pronouns {
  /**
   * @param pronoun The pronoun.
   * @param capitalize Whether to capitalize the word.
   * @return The correct singular nominative pronoun for this user.
   */
  public static String getNominativePronoun(Pronoun pronoun, boolean capitalize) {
    switch (pronoun) {
      case MALE: {
        return capitalize ? "He" : "he";
      }
      case FEMALE: {
        return capitalize ? "She" : "she";
      }
      default: {
        return capitalize ? "They" : "they";
      }
    }
  }
  
  /**
   * @param pronoun The pronoun.
   * @param capitalize Whether to capitalize the word.
   * @return The correct singular objective pronoun for this user.
   */  
  public static String getObjectivePronoun(Pronoun pronoun, boolean capitalize) {
    switch (pronoun) {
      case MALE: {
        return capitalize ? "Him" : "him";
      }
      case FEMALE: {
        return capitalize ? "Her" : "her";
      }
      default: {
        return capitalize ? "Them" : "them";
      }
    }
  }
}
