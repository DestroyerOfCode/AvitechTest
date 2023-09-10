package org.avitech;

import static org.avitech.models.command.Command.registerAction;
import static org.avitech.models.command.CommandFactory.create;
import static org.avitech.models.command.CommandType.ADD;
import static org.avitech.models.command.CommandType.DELETE_ALL;
import static org.avitech.models.command.CommandType.PRINT_ALL;

import java.util.List;
import java.util.Objects;
import org.avitech.business.Buffer;
import org.avitech.business.SynchronizedBuffer;
import org.avitech.exceptions.AvitechException;
import org.avitech.models.command.Command;
import org.avitech.repositories.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
  private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
  private final UserRepository userRepository;

  public Application(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public final void init() {

    registerCommandActions();

    final Buffer<Command> buffer = new SynchronizedBuffer<>();
    final Thread putThread = new Thread(putThreadRunnable(buffer));
    final Thread getThread = new Thread(getThreadRunnable(buffer));

    startThreads(putThread, getThread);
  }

  private void registerCommandActions() {
    registerAction(ADD, userRepository::addUser);
    registerAction(PRINT_ALL, userRepository::printUsers);
    registerAction(DELETE_ALL, userRepository::deleteUsers);
  }

  private static void startThreads(final Thread putThread, final Thread getThread) {
    try {
      putThread.start();
      getThread.start();
      putThread.join();
      getThread.join();

      return;
    } catch (InterruptedException e) {
      LOGGER.error(String.format("An error occurred on thread %s", putThread.getName()), e);
    }

    throw new AvitechException("A thread-related error occurred on one of the buffer threads");
  }

  private Runnable getThreadRunnable(final Buffer<Command> buffer) {
    return () -> {
      while (true) {
        final Command command = buffer.blockingGet();
        if (Objects.isNull(command)) {
          continue;
        }
        command.execute();
      }
    };
  }

  private Runnable putThreadRunnable(final Buffer<Command> buffer) {
    return () -> {
      buffer.blockingPut(create(ADD, List.of("1", "a1", "Robert")));
      buffer.blockingPut(create(ADD, List.of("2", "a2", "Martin")));
      buffer.blockingPut(create(PRINT_ALL));
      buffer.blockingPut(create(DELETE_ALL));
      buffer.blockingPut(create(PRINT_ALL));
    };
  }
}
