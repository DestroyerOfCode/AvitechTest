package org.avitech;

import org.avitech.repositories.user.UserRepository;

public class Main {
  public static void main(String[] args) {
    Application application = new Application(new UserRepository());

    application.init();
  }
}
