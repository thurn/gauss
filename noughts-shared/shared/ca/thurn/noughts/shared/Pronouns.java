package ca.thurn.noughts.shared;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;

import ca.thurn.noughts.shared.entities.Pronoun;

@Export
@ExportPackage("nts")
public class Pronouns implements Exportable {
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
