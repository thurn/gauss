package com.tinlib.jgail.examples.dropswitch;

public class DropswitchAction {

  // Bit layout:
  // 000A BBCC DDEE FFGG
  // A is 1 for a switch action and 0 for a drop action
  // (FF,GG) are (column,row) of a drop action
  // (BB,CC) are (column1, row1) of a switch action
  // (DD,EE) are (column2, row2) of a switch action

  public static long dropAction(int column, int row) {
    return (column << 2) | row;
  }

  public static long switchAction(int column1, int row1, int column2, int row2) {
    return (1 << 12) | (column1 << 10) | (row1 << 8) | (column2 << 6) | (row2 << 4);
  }

  public static boolean isSwitchAction(long action) {
    return ((1 << 12) & action) != 0;
  }

  public static long getDropColumn(long action) {
    return (action >> 2) & 0b11;
  }

  public static long getDropRow(long action) {
    return action & 0b11;
  }

  public static long getSwitchColumn1(long action) {
    return (action >> 10) & 0b11;
  }

  public static long getSwitchRow1(long action) {
    return (action >> 8) & 0b11;
  }

  public static long getSwitchColumn2(long action) {
    return (action >> 6) & 0b11;
  }

  public static long getSwitchRow2(long action) {
    return (action >> 4) & 0b11;
  }
}
