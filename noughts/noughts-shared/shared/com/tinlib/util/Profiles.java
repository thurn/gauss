package com.tinlib.util;

import com.tinlib.generated.Profile;
import com.tinlib.generated.Pronoun;

public class Profiles {
  public static Profile newEmptyProfile() {
    return Profile.newBuilder()
        .setIsComputerPlayer(false)
        .setPronoun(Pronoun.NEUTRAL)
        .build();
  }
}
