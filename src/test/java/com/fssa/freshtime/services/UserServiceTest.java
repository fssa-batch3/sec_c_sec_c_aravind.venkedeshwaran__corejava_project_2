package com.fssa.freshtime.services;

import com.fssa.freshtime.exceptions.InvalidInputException;
import com.fssa.freshtime.models.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    User getUser(){
        User user = new User();

        user.setUserName("Aravind");
        user.setEmailId("aravind@freshword.com");
        user.setPassword("Aravind@21");

        return user;
    }

    @Test
    void testUserSignUpValidInput() {

        UserService userService = new UserService();
        User user = getUser();

        assertDoesNotThrow(() -> userService.userSignUp(user));
    }

    @Test
    void testUserSignUpInvalidInput() {
        UserService userService = new UserService();
        User user = getUser();

        user.setUserName("Aravind21");

        assertThrows(InvalidInputException.class, () -> userService.userSignUp(user));
    }

    @Test
    void testUserLoginValidInput() {
        UserService userService = new UserService();

        assertDoesNotThrow(() -> userService.userLogin("aravindram@gmail.com", "Aravind@21"));
    }

    @Test
    void testUserLoginInvalidInput() {
        UserService userService = new UserService();

        assertThrows(InvalidInputException.class, () -> userService.userLogin("", "weakPassword"));
    }

    @Test
    void testForgotPasswordValidInput() {
        UserService userService = new UserService();

        assertDoesNotThrow(() -> userService.userLogin("aravindram@gmail.com", "P@$$w0rd"));
    }

    @Test
    void testForgotPasswordInvalidInput() {
        UserService userService = new UserService();

        assertThrows(InvalidInputException.class, () -> userService.forgotPassword("notmyemail@gmail.com", "Aravind@21"));
    }

    @Test
    void testDeleteUserValidInput() {
        UserService userService = new UserService();

        assertDoesNotThrow(() -> userService.deleteUser("aravindram@gmail.com", "P@$$w0rd"));
    }

    @Test
    void testDeleteUserInvalidInput() {
        UserService userService = new UserService();

        assertThrows(InvalidInputException.class, () -> userService.deleteUser("aravindram@gmail.com", "WrongPassword"));
    }
}

