package ca.thurn.noughts.shared;

/**
 * A string which represents an image, either a URL or a local resource.
 */
public class PhotoString {
  private final String string;
  private final boolean isLocal;
  
  private PhotoString(String string, boolean isLocal) {
    this.string = string;
    this.isLocal = isLocal;
  }

  /**
   * @return The string itself.
   */
  public String getString() {
    return string;
  }

  /**
   * @return True if the string represents a local resource image, false if
   *     the string represents a remote resource.
   */
  public boolean isLocal() {
    return isLocal;
  }
}
