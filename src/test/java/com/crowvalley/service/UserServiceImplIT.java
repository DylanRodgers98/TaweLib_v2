package com.crowvalley.service;

import com.crowvalley.model.user.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml" })
public class UserServiceImplIT {

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void testCRUDOperationsOnUser() {
        SoftAssertions softly = new SoftAssertions();
        User user = new User("DylanRodgers98", "Dylan", "Rodgers",
                "07866345602", "5 Bowood", "Harford Drive", "Bristol",
                "South Gloucestershire", "BS16 1NS", "");

        //Test Create and Retrieve operations
        userService.save(user);

        softly.assertThat(userService.get("DylanRodgers98").get())
                .as("Retrieve user from database with username DylanRodgers98")
                .isEqualTo(user);

        //Test Update operation
        User userToUpdate = userService.get("DylanRodgers98").get();
        userToUpdate.setPhoneNum("07866 123456");
        userService.update(userToUpdate);

        softly.assertThat(userService.get("DylanRodgers98").get())
                .as("Retrieve user from database with username DylanRodgers98 with updated phone number")
                .isEqualTo(user);

        //Test Delete operation
        userService.delete(user);

        softly.assertThat(userService.get("DylanRodgers98"))
                .as("User DylanRodgers98 deleted")
                .isEqualTo(Optional.empty());
    }
}
