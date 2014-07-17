// ================================
// GENERATED CODE -- DO NOT MODIFY!
// ================================

package com.tinlib.generated;

import java.util.*;

import com.tinlib.entities.Entity;

public final class Command extends Entity<Command> {
  public static final class Deserializer extends EntityDeserializer<Command> {
    private Deserializer() {
    }

    @Override
    public Command deserialize(Map<String, Object> commandMap) {
      return new Command(commandMap);
    }
  }

  public static final class Builder extends EntityBuilder<Command> {
    private final Command command;

    private Builder() {
      this.command = new Command();
    }

    private Builder(Command command) {
      this.command = new Command(command);
    }

    @Override
    public Command build() {
      return new Command(command);
    }

    @Override
    protected Command getInternalEntity() {
      return command;
    }

    public boolean hasColumn() {
      return command.column != null;
    }

    public int getColumn() {
      checkNotNull(command.column);
      return command.column;
    }

    public Builder setColumn(int column) {
      command.column = column;
      return this;
    }

    public Builder clearColumn() {
      command.column = null;
      return this;
    }

    public boolean hasRow() {
      return command.row != null;
    }

    public int getRow() {
      checkNotNull(command.row);
      return command.row;
    }

    public Builder setRow(int row) {
      command.row = row;
      return this;
    }

    public Builder clearRow() {
      command.row = null;
      return this;
    }

  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static Deserializer newDeserializer() {
    return new Deserializer();
  }

  private Integer column;
  private Integer row;

  private Command() {
  }

  private Command(Command command) {
    column = command.column;
    row = command.row;
  }

  private Command(Map<String, Object> map) {
    column = get(map, "column", Integer.class);
    row = get(map, "row", Integer.class);
  }

  @Override
  public String entityName() {
    return "Command";
  }

  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<>();
    putSerialized(result, "column", column);
    putSerialized(result, "row", row);
    return result;
  }

  @Override
  public Builder toBuilder() {
    return new Builder(this);
  }

  public boolean hasColumn() {
    return column != null;
  }

  public int getColumn() {
    checkNotNull(column);
    return column;
  }

  public boolean hasRow() {
    return row != null;
  }

  public int getRow() {
    checkNotNull(row);
    return row;
  }

}
