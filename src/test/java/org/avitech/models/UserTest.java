package org.avitech.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UserTest {

  @Test
  void whenCreateUserWithId1_ThenUserIdIs1() {
    // given and when
    final User user = getUser(1, "a1", "György");

    // then
    assertEquals(1, user.id());
  }

  @Test
  void whenCreateUserWithGuida1_ThenUserGuidIsa1() {
    // given and when
    final User user = getUser(1, "a1", "György");

    // then
    assertEquals("a1", user.guid());
  }

  @Test
  void whenCreateUserWithNameGyorgy_ThenUserNameIsGyorgy() {
    // given and when
    final User user = getUser(1, "a1", "György");

    // then
    assertEquals("György", user.name());
  }

  private static User getUser(final Integer id, final String guid, final String name) {
    return new User(id, guid, name);
  }
}
