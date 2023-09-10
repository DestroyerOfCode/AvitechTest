package org.avitech.models.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class Command {

  private static final Map<CommandType, Consumer<List<String>>> actionMap = new HashMap<>();

  private final CommandType commandType;
  private final List<String> params;

  public static void registerAction(final CommandType type, final Consumer<List<String>> action) {
    actionMap.put(type, action);
  }

  public Command(final CommandType commandType, final List<String> params) {
    this.commandType = commandType;
    this.params = params;
  }

  public void execute() {
    Consumer<List<String>> action = actionMap.get(commandType);
    if (Objects.isNull(action)) {
      throw new UnsupportedOperationException("Unsupported command: " + commandType);
    }
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

    public Builder withType(final CommandType type) {
      this.type = type;
      return this;
    }

    public Builder withParams(final List<String> params) {
      this.params = params;
      return this;
    }

    public Command build() {
      return new Command(type, params);
    }
  }
}
