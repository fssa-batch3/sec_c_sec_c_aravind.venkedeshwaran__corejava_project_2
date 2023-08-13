package com.fssa.freshtime.validations;

import com.fssa.freshtime.exceptions.InvalidInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidatorTest {

    @Test
    public void testValidEmail() {
        assertDoesNotThrow(() -> UserValidator.validateEmailId("aravind@gmail.com"));
    }

    @Test
    public void testInvalidEmail() {
        assertThrows(InvalidInputException.class, () -> {
            UserValidator.validateEmailId("invalid_email");
        });
    }

    @Test
    void testEmptyEmailId(){
        assertThrows(InvalidInputException.class, ()-> UserValidator.validateEmailId(""));
    }

    @Test
    public void testValidUserName() {
        assertDoesNotThrow(() -> UserValidator.validateUserName("aravind"));
    }

    @Test
    public void testInvalidUserName() {
        assertThrows(InvalidInputException.class, () -> {
            UserValidator.validateUserName("Aravind ram");
        });
    }

    @Test
    void testEmptyUserName(){
        assertThrows(InvalidInputException.class, ()-> UserValidator.validateUserName(""));
    }

    @Test
    public void testValidPassword() {
        assertDoesNotThrow(() -> UserValidator.validatePassword("P@ssw0rd"));
    }

    @Test
    public void testInvalidPassword() {
        assertThrows(InvalidInputException.class, () -> {
            UserValidator.validatePassword("weakpassword");
        });
    }

    @Test
    void testEmptyPassword(){
        assertThrows(InvalidInputException.class, ()-> UserValidator.validatePassword(""));
    }


    @Test
    public void testValidPhoneNumber() {
        assertDoesNotThrow(() -> UserValidator.validatePhoneNumber("9080668509"));
    }

    @Test
    public void testInvalidPhoneNumber() {
        assertThrows(InvalidInputException.class, () -> {
            UserValidator.validatePhoneNumber("invalidNumber");
        });
    }

    @Test
    void testEmptyPhoneNumber(){
        assertThrows(InvalidInputException.class, ()-> UserValidator.validatePhoneNumber(""));
    }

}
