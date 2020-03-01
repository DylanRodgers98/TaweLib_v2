package com.crowvalley.tawelib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserContextHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserContextHolder.class);

    private static String loggedInUser;

    public static void setLoggedInUser(String username) {
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
