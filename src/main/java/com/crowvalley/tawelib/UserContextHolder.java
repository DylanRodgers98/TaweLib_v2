package com.crowvalley.tawelib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class UserContextHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserContextHolder.class);

    private static final String ANOTHER_USER_LOGGED_IN_ERROR_MESSAGE = "Can't log user in as another user is already logged in";

    private static String loggedInUser;

    public static void setLoggedInUser(String username) {
        if (loggedInUser != null) {
            LOGGER.error(ANOTHER_USER_LOGGED_IN_ERROR_MESSAGE);
            throw new IllegalStateException(ANOTHER_USER_LOGGED_IN_ERROR_MESSAGE);
        }
        loggedInUser = username;
        LOGGER.info("{} logged in", username);
    }

    public static String getLoggedInUser() {
        return loggedInUser;
    }

    public static void clear() {
        LOGGER.info("Logging out {}", loggedInUser);
        loggedInUser = null;
    }

}
