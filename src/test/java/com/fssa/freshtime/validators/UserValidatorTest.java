package com.fssa.freshtime.validators;

import com.fssa.freshtime.exceptions.InvalidInputException;
import com.fssa.freshtime.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {
    
    User getUser(){
        User user = new User();

        user.setUserName("Aravind");
        user.setEmailId("aravind@freshword.com");
        user.setPassword("Aravind@21");

        return user;
    }

    @Test
    void testValidUser(){
        User user = getUser();

        assertDoesNotThrow(() -> UserValidator.validateUser(user));
    }

    @Test
    void testNullUser(){
        assertThrows(InvalidInputException.class, () -> UserValidator.validateUser(null));
    }

    @Test
    void testValidEmail() {
        assertDoesNotThrow(() -> UserValidator.validateEmailId("aravind@gmail.com"));
    }

    @Test
    void testInvalidEmail() {
        assertThrows(InvalidInputException.class, () -> {
            UserValidator.validateEmailId("WrongEmail");
        });
    }

    @Test
    void testEmptyEmailId(){
        assertThrows(InvalidInputException.class, ()-> UserValidator.validateEmailId(""));
    }

    @Test
    void testValidUserName() {
        assertDoesNotThrow(() -> UserValidator.validateUserName("     joe      "));
    }

    @Test
    void testInvalidUserNameWithSpaces() {
        assertDoesNotThrow( () -> UserValidator.validateUserName("Aravind Ram"));
    }

    @Test
    void testInvalidUserNameWithNumber() {
        assertThrows(InvalidInputException.class, () -> UserValidator.validateUserName("Aravind21"));
    }

    @Test
    void testInvalidUserNameLessThanThreeChar(){
        assertThrows(InvalidInputException.class, () -> UserValidator.validateUserName("jo"));
    }
    @Test
    void testEmptyUserName(){
        assertThrows(InvalidInputException.class, ()-> UserValidator.validateUserName(""));
    }

    @Test
    void testValidPassword() {
        assertDoesNotThrow(() -> UserValidator.validatePassword("P@ssw0rd"));
    }

    @Test
    void testInvalidPasswordLessThanEightChar() {
        assertThrows(InvalidInputException.class, () -> UserValidator.validatePassword("Hel1@"));
    }

    @Test
    void testInvalidPasswordMoreThanTwentyChar() {
        assertThrows(InvalidInputException.class, () -> UserValidator.validatePassword("Hel1@ThisHasMoreThanTwemtyChar"));
    }

    @Test
    void testInvalidPasswordNoDigit() {
        assertThrows(InvalidInputException.class, () -> UserValidator.validatePassword("WrongPassword@"));
    }

    @Test
    void testInvalidPasswordNoSplChar() {
        assertThrows(InvalidInputException.class, () -> UserValidator.validatePassword("WrongPassword1"));
    }

    @Test
    void testInvalidPasswordNoLower() {
        assertThrows(InvalidInputException.class, () -> UserValidator.validatePassword("WRONGPASS@1"));
    }

    @Test
    void testInvalidPasswordNoUpper() {
        assertThrows(InvalidInputException.class, () -> UserValidator.validatePassword("wrongpass@1"));
    }

    @Test
    void testEmptyPassword(){
        assertThrows(InvalidInputException.class, ()-> UserValidator.validatePassword(""));
    }


}
