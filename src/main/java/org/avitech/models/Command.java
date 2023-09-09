package org.avitech.models;

import java.util.List;
import java.util.function.Consumer;

public class Command {

  private final CommandType commandType;
  private final List<String> params;

  private final Consumer<List<String>> action;

  public Command(final CommandType commandType, final Consumer<List<String>> action) {
    this.commandType = commandType;
    this.action = action;
    this.params = List.of();
  }

  public Command(
      final CommandType commandType,
      final Consumer<List<String>> action,
      final List<String> params) {
    this.commandType = commandType;
    this.action = action;
    this.params = params;
  }

  public CommandType getCommandType() {
    return commandType;
  }

  public List<String> getParams() {
    return params;
  }

  public Consumer<List<String>> getAction() {
    return action;
  }

  @Override
  public String toString() {
    return "Command{" + "commandType=" + commandType + '}';
  }
}
