// ================================
// GENERATED CODE -- DO NOT MODIFY!
// ================================

package com.tinlib.generated;

import java.util.*;

import com.tinlib.beget.Entity;

/**
 * A list section and row number.
 */
public final class IndexPath extends Entity<IndexPath> {
  /**
   * Class to create IndexPath instances from their serialized representation.
   */
  public static final class Deserializer extends EntityDeserializer<IndexPath> {
    private Deserializer() {
    }

    /**
     * Takes a map (e.g one returned from {@link IndexPath#serialize()}) and returns a new IndexPath instance.
     */
    @Override
    public IndexPath deserialize(Map<String, Object> indexPathMap) {
      return new IndexPath(indexPathMap);
    }
  }

  /**
   * Helper utility class to create new IndexPath instances.
   */
  public static final class Builder extends EntityBuilder<IndexPath> {
    private final IndexPath indexPath;

    private Builder() {
      this.indexPath = new IndexPath();
    }

    private Builder(IndexPath indexPath) {
      this.indexPath = new IndexPath(indexPath);
    }

    /**
     * Returns a new immutable IndexPath instance based on the current state of this Builder.
     */
    @Override
    public IndexPath build() {
      return new IndexPath(indexPath);
    }

    @Override
    protected IndexPath getInternalEntity() {
      return indexPath;
    }

    /**
     * Returns true if a value has been set for section
     */
    public boolean hasSection() {
      return indexPath.section != null;
    }

    /**
     * Gets the value of section
     *
     * @return The list section
     */
    public int getSection() {
      checkNotNull(indexPath.section);
      return indexPath.section;
    }

    /**
     * Sets the value of section.
     *
     * @param section The list section
     */
    public Builder setSection(int section) {
      indexPath.section = section;
      return this;
    }

    /**
     * Unsets the value of section.
     */
    public Builder clearSection() {
      indexPath.section = null;
      return this;
    }

    /**
     * Returns true if a value has been set for row
     */
    public boolean hasRow() {
      return indexPath.row != null;
    }

    /**
     * Gets the value of row
     *
     * @return The row number within the section
     */
    public int getRow() {
      checkNotNull(indexPath.row);
      return indexPath.row;
    }

    /**
     * Sets the value of row.
     *
     * @param row The row number within the section
     */
    public Builder setRow(int row) {
      indexPath.row = row;
      return this;
    }

    /**
     * Unsets the value of row.
     */
    public Builder clearRow() {
      indexPath.row = null;
      return this;
    }

  }

  /**
   * Returns a new Builder class to help you create IndexPath instances.
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Returns a new Deserializer class to help you create IndexPath instances from their serialized form.
   */
  public static Deserializer newDeserializer() {
    return new Deserializer();
  }

  private Integer section;
  private Integer row;

  private IndexPath() {
  }

  private IndexPath(IndexPath indexPath) {
    section = indexPath.section;
    row = indexPath.row;
  }

  private IndexPath(Map<String, Object> map) {
    section = get(map, "section", Integer.class);
    row = get(map, "row", Integer.class);
  }

  /**
   * Returns the name of this entity for use in toString().
   */
  @Override
  public String entityName() {
    return "IndexPath";
  }

  /**
   * Creates a Map representation of this IndexPath.
   */
  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<>();
    putSerialized(result, "section", section);
    putSerialized(result, "row", row);
    return result;
  }

  /**
   * Creates a new Builder initialized with the current contents of this IndexPath.
   */
  @Override
  public Builder toBuilder() {
    return new Builder(this);
  }

  /**
   * Returns true if a value has been set for section
   */
  public boolean hasSection() {
    return section != null;
  }

  /**
   * Gets the value of section
   *
   * @return The list section
   */
  public int getSection() {
    checkNotNull(section);
    return section;
  }

  /**
   * Returns true if a value has been set for row
   */
  public boolean hasRow() {
    return row != null;
  }

  /**
   * Gets the value of row
   *
   * @return The row number within the section
   */
  public int getRow() {
    checkNotNull(row);
    return row;
  }

}
