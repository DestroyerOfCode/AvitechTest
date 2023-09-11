package org.avitech.repositories.user;

import static org.avitech.repositories.user.UserQueries.ADD_USER;
import static org.avitech.repositories.user.UserQueries.DELETE_ALL_USERS;
import static org.avitech.repositories.user.UserQueries.SELECT_ALL_USERS;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.avitech.exceptions.AvitechException;
import org.avitech.models.user.User;
import org.avitech.repositories.AvitechDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

public class UserRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

  private final AvitechDatabase db;

  public UserRepository() {
    this.db = establishConnection();
  }

  public UserRepository(final AvitechDatabase db) {
    this.db = db;
  }

  private static Map<String, String> getDbConfigFromYaml() {
    try (FileInputStream fis = new FileInputStream("./src/main/resources/dbConfig.yaml")) {
      final Yaml yaml = new Yaml();

      return yaml.load(fis);
    } catch (IOException e) {
      LOGGER.error("An error occurred when opening the file with DB configs", e);
    }

    throw new AvitechException(
        "An error occurred opening the file dbConfig with config to Avitech db");
  }

  private static void prepareParamsForStatement(final PreparedStatement select, final User user)
      throws SQLException {
    select.setInt(1, user.id());
    select.setString(2, user.guid());
    select.setString(3, user.name());
  }

  public final void deleteUsers(final List<String> params) {
    try {
      final PreparedStatement select = db.getConnection().prepareStatement(DELETE_ALL_USERS);

      select.execute();
      return;
    } catch (SQLException e) {
      LOGGER.error("An error occurred when deleting users", e);
    }

    throw new AvitechException("An error occurred when deleting users");
  }

  public final void addUser(final List<String> params) {
    try {
      final PreparedStatement select = db.getConnection().prepareStatement(ADD_USER);
      final User user = new User(Integer.parseInt(params.get(0)), params.get(1), params.get(2));

      prepareParamsForStatement(select, user);

      select.execute();
      return;

    } catch (SQLException e) {
      LOGGER.error("An error occurred when trying to add user", e);
    } catch (NumberFormatException e) {
      LOGGER.error("An error occurred when trying to parse the Id of params");
    } catch (NullPointerException e) {
      LOGGER.error("An error occurred when getting the elements of list of parameters");
    }

    throw new AvitechException("An error occurred when trying to add user to the SUSERS table");
  }

  public final void printUsers(final List<String> params) {
    try {
      final PreparedStatement select = db.getConnection().prepareStatement(SELECT_ALL_USERS);

      final ResultSet resultSet = select.executeQuery();

      while (resultSet.next()) {
        System.out.println(resultSet.getInt("USER_ID"));
        System.out.println(resultSet.getString("USER_GUID"));
        System.out.println(resultSet.getString("USER_NAME"));
      }

      return;
    } catch (SQLException e) {
      LOGGER.error("An error occurred when trying to select users", e);
    }

    throw new AvitechException("An error occurred when trying to print users");
  }

  public final void closeConnection() {
    db.close();
  }

  private AvitechDatabase establishConnection() {
    final Map<String, String> config = getDbConfigFromYaml();

    try {
      return new AvitechDatabase(config.get("url"), config.get("username"), config.get("password"));
    } catch (AvitechException e) {
      LOGGER.error(String.format("Error establishing connection to db %s", config.get("url")));
    }

    throw new AvitechException();
  }
}
