package org.avitech.business;

import static org.avitech.models.command.CommandFactory.create;
import static org.avitech.models.command.CommandType.ADD;
import static org.avitech.models.command.CommandType.DELETE_ALL;
import static org.avitech.models.command.CommandType.PRINT_ALL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.avitech.models.command.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SynchronizedBufferTest {
  private SynchronizedBuffer<Command> buffer;

  @BeforeEach
  public void setUp() {
    buffer = new SynchronizedBuffer<>();
  }

  @Test
  public void shouldHaveEmptyQueue_WhenSameNumberOfInputsAndOutputs() throws InterruptedException {

    // given
    Thread producer =
        new Thread(
            () -> {
              for (int i = 1; i <= 5; i++) {
                buffer.blockingPut(create(ADD, List.of("Systems functional")));
              }
            });

    Thread consumer =
        new Thread(
            () -> {
              for (int i = 1; i <= 5; i++) {
                Command command = buffer.blockingGet();
                assertEquals(command.getCommandType(), ADD);
                assertEquals("Systems functional", command.getParams().get(0));
              }
            });

    // when
    producer.start();
    consumer.start();

    producer.join();
    consumer.join();

    // then
    assertTrue(buffer.getQueue().isEmpty());
  }

  @Test
  public void shouldHave5CommandsInQueue_whenCallingGet5LessTimes() throws InterruptedException {
    // given
    Thread producer =
        new Thread(
            () -> {
              for (int i = 1; i <= 15; i++) {
                buffer.blockingPut(create(PRINT_ALL));
              }
            });

    Thread consumer =
        new Thread(
            () -> {
              for (int i = 1; i <= 10; i++) {
                Command command = buffer.blockingGet();
                assertEquals(PRINT_ALL, command.getCommandType());
                assertTrue(command.getParams().isEmpty());
              }
            });

    // when
    producer.start();
    consumer.start();

    producer.join();
    consumer.join();

    // then
    assertEquals(5, buffer.getQueue().size()); // Buffer size should be capped at 10
  }

  @Test
  public void shouldLeaveTheConsumerThreadHanging_WhenQueueEmpty() throws InterruptedException {
    // given
    Thread producer =
        new Thread(
            () -> {
              for (int i = 1; i <= 10; i++) {
                buffer.blockingPut(create(DELETE_ALL));
              }
            });

    Thread hangingConsumer =
        new Thread(
            () -> {
              for (int i = 1; i <= 15; i++) {
                Command command = buffer.blockingGet();
                assertEquals(DELETE_ALL, command.getCommandType());
                assertTrue(command.getParams().isEmpty());
              }
            });

    // when
    producer.start();
    hangingConsumer.start();

    producer.join();
    hangingConsumer.join(5000);

    // then
    assertEquals(0, buffer.getQueue().size()); // Buffer size should be capped at 10
  }
}
