package org.avitech.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import org.avitech.exceptions.AvitechException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AvitechDatabase implements AutoCloseable {

  private static final Logger LOGGER = LoggerFactory.getLogger(AvitechDatabase.class);

  private final String url;
  private final Connection dbConnection;

  public AvitechDatabase(final String url, final String username, final String password) {
    this.url = url;
    dbConnection = establishDatabaseConnection(username, password);
  }

  public synchronized Connection getConnection() {
    return Objects.requireNonNull(
        dbConnection,
        () ->
            String.format(
                "Connection to %s not established. Establish the connection first!", url));
  }

  private synchronized Connection establishDatabaseConnection(
      final String username, final String password) {
    try {
      return DriverManager.getConnection(url, username, password);
    } catch (SQLException e) {
      LOGGER.error(
          String.format(
              "Error trying to establish connection to %s with credentials %s %s!",
              url, username, password),
          e);
    }
    throw new AvitechException("Error establishing database connection!");
  }

  @Override
  public void close() {
    try {
      if (Objects.nonNull(dbConnection) && dbConnection.isClosed()) {
        LOGGER.info(String.format("Connection to %s is already closed or was never open.", url));
        return;
      }

      dbConnection.close();
    } catch (SQLException e) {
      LOGGER.error(
          String.format("An SQL error occurred when trying to close connection to %s", url), e);
      throw new AvitechException(e.getMessage());
    }
  }
}
