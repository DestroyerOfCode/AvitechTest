package org.avitech.repositories.user;

public class UserQueries {

  public static final String SELECT_ALL_USERS = "SELECT * FROM avitech.SUSERS;";
  public static final String DELETE_ALL_USERS = "DELETE FROM avitech.SUSERS;";
  public static final String ADD_USER =
      "INSERT INTO avitech.SUSERS (USER_ID, USER_GUID, USER_NAME) VALUES (?, ?, ?);";
}
