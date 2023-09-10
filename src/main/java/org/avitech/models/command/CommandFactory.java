package org.avitech.models.command;

import java.util.List;

public class CommandFactory {
  public static Command create(final CommandType type, final List<String> params) {

    return new Command.Builder().withType(type).withParams(params).build();
  }

  public static Command create(final CommandType type) {
    return create(type, List.of());
  }
}
