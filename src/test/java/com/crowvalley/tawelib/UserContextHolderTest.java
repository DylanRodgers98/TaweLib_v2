package com.crowvalley.tawelib;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class UserContextHolderTest {

    public static final String USERNAME_1 = "TEST_USER_1";

    public static final String USERNAME_2 = "TEST_USER_2";

    @Before
    public void setUp() {
        UserContextHolder.clear();
    }

    @Test
    public void testSetLoggedInUser() {
        assertThat(UserContextHolder.getLoggedInUser())
                .as("No username returned due to no logged in user")
                .isNull();

        UserContextHolder.setLoggedInUser(USERNAME_1);

        assertThat(UserContextHolder.getLoggedInUser())
                .as("Username of logged in user returned")
                .isEqualTo(USERNAME_1);

        UserContextHolder.clear();

        assertThat(UserContextHolder.getLoggedInUser())
                .as("No username returned after user logs out and context holder is cleared")
                .isNull();
    }

    @Test
    public void testSetLoggedInUser_AnotherUserAlreadyLoggedIn() {
        assertThat(UserContextHolder.getLoggedInUser())
                .as("No username returned due to no logged in user")
                .isNull();

        UserContextHolder.setLoggedInUser(USERNAME_1);

        assertThat(UserContextHolder.getLoggedInUser())
                .as("Username of logged in user returned")
                .isEqualTo(USERNAME_1);

        assertThatCode(() -> UserContextHolder.setLoggedInUser(USERNAME_2))
                .as("Exception thrown because another user is logged in")
                .isInstanceOf(IllegalStateException.class);
    }
}
