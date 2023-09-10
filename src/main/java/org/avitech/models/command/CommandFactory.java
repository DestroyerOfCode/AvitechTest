package org.avitech.models.command;

import java.util.List;
import java.util.function.Consumer;

public class CommandFactory {

  public static Command create(
      final CommandType type, final Consumer<List<String>> action, final List<String> params) {

    return new Command.Builder().withType(type).withAction(action).withParams(params).build();
  }

  public static Command create(final CommandType type, final Consumer<List<String>> action) {
    return create(type, action, List.of());
  }
}
