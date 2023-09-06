package com.fssa.freshtime.services;

import com.fssa.freshtime.exceptions.ServiceException;
import com.fssa.freshtime.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserService userService = new UserService();

    private User getUser() {
        User user = new User();
        user.setUserName("TestName");
        long nanoTime = System.nanoTime();
        user.setEmailId("test" + nanoTime + "@test.com");
        user.setPassword("TestP@$$W0rd");

        return user;
    }

    @Test
    void testValidUserSignUp() {
        User user = getUser();
        assertDoesNotThrow(() -> userService.userSignUp(user));
    }

    @Test
    void testUserSignUpInvalidUserName() {
        User user = getUser();
        user.setUserName("Aravind21");
        assertThrows(ServiceException.class, () -> userService.userSignUp(user));
    }

    @Test
    void testValidUserLogin() {
    	
    	assertDoesNotThrow(() -> userService.userLogin("testuser01@gmail.com", "P@$$w0rd"));
    }

    @Test
    void testUserLoginInvalidInput() {
        assertThrows(ServiceException.class, () -> userService.userLogin("", "weakPassword"));
    }

    @Test
    void testForgotPasswordValidInput() {

        assertDoesNotThrow(() -> userService.forgotPassword("testuser01@gmail.com", "ChangedP@$$w0rd"));
    }

    @Test
    void testForgotPasswordInvalidInput() {
        

        assertThrows(ServiceException.class, () -> userService.forgotPassword("notmyemail@gmail.com", "Aravind@21"));
    }


    @Test
    void testDeleteUserValidInput() {
       
        assertDoesNotThrow(() -> userService.deleteUser("testDelete@gmail.com"));
    }

    @Test
    void testDeleteUserInvalidInput() {
        
        assertThrows(ServiceException.class, () -> userService.deleteUser("notmyemail@gmail.com"));
    }



}

