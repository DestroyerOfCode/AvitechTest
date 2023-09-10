package org.avitech.models.command;

import java.util.List;
import java.util.function.Consumer;

public class Command {

  private final CommandType commandType;
  private final List<String> params;
  private final Consumer<List<String>> action;

  public Command(
      final CommandType commandType,
      final Consumer<List<String>> action,
      final List<String> params) {
    this.commandType = commandType;
    this.action = action;
    this.params = params;
  }

  public void execute() {
    action.accept(params);
  }

  public CommandType getCommandType() {
    return commandType;
  }

  public List<String> getParams() {
    return params;
  }

  public static class Builder {

    private CommandType type;
    private List<String> params = List.of();
    private Consumer<List<String>> action;

    public Builder withType(final CommandType type) {
      this.type = type;
      return this;
    }

    public Builder withParams(final List<String> params) {
      this.params = params;
      return this;
    }

    public Builder withAction(final Consumer<List<String>> action) {
      this.action = action;
      return this;
    }

    public Command build() {
      return new Command(type, action, params);
    }
  }
}
