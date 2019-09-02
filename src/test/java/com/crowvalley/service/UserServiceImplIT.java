package com.crowvalley.service;

import static org.assertj.core.api.Assertions.*;

import com.crowvalley.model.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml" })
public class UserServiceImplIT {

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void testCRUDOperationsOnUser() {
        User user = new User("DylanRodgers98", "Dylan", "Rodgers",
                "07866345602", "5 Bowood", "Harford Drive", "Bristol",
                "South Gloucestershire", "BS16 1NS", "");

        userService.save(user);
        Optional<User> userToRetrieve = userService.get("DylanRodgers98");

        assertThat(userToRetrieve.get()).as("Retrieve user from database with username DylanRodgers98")
                .isEqualTo(user);

        User userToUpdate = userToRetrieve.get();
        userToUpdate.setPhoneNum("07866 123456");
        userService.update(userToUpdate);

        assertThat(userToRetrieve.get()).as("Retrieve user from database with username DylanRodgers98 with updated phone number")
                .isEqualTo(user);
        
        userService.delete(user);
        assertThat(userService.getAll()).as("List of all users does not contain user DylanRodgers98 after deleting it")
                .doesNotContain(user);
    }
}
