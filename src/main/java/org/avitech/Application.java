package org.avitech;

import java.util.List;
import org.avitech.business.Buffer;
import org.avitech.business.SynchronizedBuffer;
import org.avitech.exceptions.AvitechException;
import org.avitech.models.Command;
import org.avitech.models.CommandType;
import org.avitech.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

  private final UserRepository userRepository;

  public Application(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public final void init() {

    final Buffer buffer = new SynchronizedBuffer();
    final Thread putThread = new Thread(putThreadRunnable(buffer));
    final Thread getThread = new Thread(getThreadRunnable(buffer));

    startThreads(putThread, getThread);
  }

  private static void startThreads(final Thread putThread, final Thread getThread) {
    try {
      putThread.start();
      getThread.start();
      putThread.join();

      return;
    } catch (InterruptedException e) {
      LOGGER.error(String.format("An error occurred on thread %s", putThread.getName()), e);
    }

    throw new AvitechException("A thread-related error occurred on one of the buffer threads");
  }

  private Runnable getThreadRunnable(Buffer buffer) {
    return () -> {
      while (true) {
        buffer.blockingGet();
      }
    };
  }

  private Runnable putThreadRunnable(Buffer buffer) {
    return () -> {
      buffer.blockingPut(
          new Command(CommandType.ADD, userRepository::addUser, List.of("1", "a1", "Robert")));
      buffer.blockingPut(
          new Command(CommandType.ADD, userRepository::addUser, List.of("2", "a2", "Martin")));
      buffer.blockingPut(new Command(CommandType.PRINT_ALL, userRepository::printUsers));
      buffer.blockingPut(new Command(CommandType.DELETE_ALL, userRepository::deleteUsers));
      buffer.blockingPut(new Command(CommandType.PRINT_ALL, userRepository::printUsers));
    };
  }
}
