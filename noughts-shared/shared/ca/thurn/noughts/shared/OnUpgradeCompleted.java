package ca.thurn.noughts.shared;

public interface OnUpgradeCompleted {
  public void onUpgradeCompleted();

  public void onUpgradeError(String errorMessage);
}
