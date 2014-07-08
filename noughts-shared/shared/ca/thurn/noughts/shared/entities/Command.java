package ca.thurn.noughts.shared.entities;

import java.util.HashMap;
import java.util.Map;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.NoExport;

@Export
@ExportPackage("nts")
public final class Command extends Entity<Command> implements Exportable {
  @Export
  public static class Deserializer extends EntityDeserializer<Command> implements Exportable {
    private Deserializer() {
    }

    @Override
    public Command deserialize(Map<String, Object> commandMap) {
      return new Command(commandMap);
    }
  }

  @Export
  public static class Builder extends EntityBuilder<Command> implements Exportable {
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

    @Override protected Command getInternalEntity() {
      return command;
    }

    public boolean hasColumn() {
      return command.hasColumn();
    }

    public int getColumn() {
      return command.getColumn();
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
      return command.hasRow();
    }

    public int getRow() {
      return command.getRow();
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
    this.column = command.column;
    this.row = command.row;
  }

  private Command(Map<String, Object> commandMap) {
    column = getInteger(commandMap, "column");
    row = getInteger(commandMap, "row");
  }

  @Override
  public String entityName() {
    return "Command";
  }

  @Override
  @NoExport
  public Map<String, Object> serialize() {
    Map<String, Object> result = new HashMap<String, Object>();
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
