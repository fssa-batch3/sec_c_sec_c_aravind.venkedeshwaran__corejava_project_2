package com.fssa.freshtime.services;

import com.fssa.freshtime.exceptions.DAOException;
import com.fssa.freshtime.exceptions.InvalidInputException;
import com.fssa.freshtime.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

//    TODO: create user already in database and check login, delete and forgot password.

    static String currentUserEmail;
    static String currentUserPass;

    public static void setCurrentUserEmail(String currentUserEmail) {
        UserServiceTest.currentUserEmail = currentUserEmail;
    }

    public static void setCurrentUserPass(String currentUserPass) {
        UserServiceTest.currentUserPass = currentUserPass;
    }

    public static String getCurrentUserEmail() {
        return currentUserEmail;
    }

    public static String getCurrentUserPass() {
        return currentUserPass;
    }

    private User getUser() {
        User user = new User();
        user.setUserName("TestName");
        long nanoTime = System.nanoTime();
        user.setEmailId("test" + nanoTime + "@test.com");
        user.setPassword("TestP@$$W0rd");

        return user;
    }

    @Test
    void testUserSignUpValidInput() {
        UserService userService = new UserService();
        User user = getUser();

        assertDoesNotThrow(() -> userService.userSignUp(user));

        System.out.println("User Email: " + user.getEmailId());
        System.out.println("User Password: " + user.getPassword());
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

//        System.out.println();
//        System.out.println();
        String getCurrentUserPass;

//        assertDoesNotThrow(() -> userService.userLogin(currentUserEmail, currentUserPass));
    }

    @Test
    void testUserLoginInvalidInput() {
        UserService userService = new UserService();

        assertThrows(InvalidInputException.class, () -> userService.userLogin("", "weakPassword"));
    }

    @Test
    void testForgotPasswordValidInput() {
        UserService userService = new UserService();

        User user = getUser();

        assertDoesNotThrow(() -> userService.forgotPassword(user.getEmailId(), "ChangedP@$$w0rd"));
    }

    @Test
    void testForgotPasswordInvalidInput() {
        UserService userService = new UserService();

        assertThrows(DAOException.class, () -> userService.forgotPassword("notmyemail@gmail.com", "Aravind@21"));
    }


    @Test
    void testDeleteUserValidInput() {
        UserService userService = new UserService();
        User user = getUser();

        assertDoesNotThrow(() -> userService.deleteUser(user.getEmailId(), "ChangedP@$$w0rd"));
    }

    @Test
    void testDeleteUserInvalidInput() {
        UserService userService = new UserService();

        assertThrows(InvalidInputException.class, () -> userService.deleteUser("sample@gmail.com", "WrongPassword"));
    }


    public static void main(String[] args) {
        System.out.println("User Email: " + currentUserEmail);
        System.out.println("User Password: " + currentUserPass);
    }

}

