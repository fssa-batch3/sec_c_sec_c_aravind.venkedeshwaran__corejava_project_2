package com.fssa.freshtime.validations;

import com.fssa.freshtime.errors.UserErrors;
import com.fssa.freshtime.exceptions.InvalidInputException;

import com.fssa.freshtime.regexs.UserValidationRegex;
import org.apache.commons.validator.EmailValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//org.apache.commons.validator.EmailValidator
public class UserValidator {
    public static boolean validateEmailId(String emailId) throws InvalidInputException{
        boolean isMatch = emailId.matches(UserValidationRegex.EMAIL_REGEX);
        if(!isMatch || emailId.trim().isEmpty()){
            throw new InvalidInputException(UserErrors.INVALID_EMAIL_ID);
        }
        else {
            return true;
        }
    }

    public static boolean validateUserName(String userName) throws InvalidInputException{
        boolean isMatch = userName.matches(UserValidationRegex.USERNAME_REGEX);
        if(!isMatch || userName.trim().isEmpty()){
            throw  new InvalidInputException(UserErrors.INVALID_USERNAME);
        }
        else{
            return true;
        }
    }

    public static boolean validatePassword(String password) throws InvalidInputException{;
        boolean isMatch = password.matches(UserValidationRegex.PASSWORD_REGEX);
        if (!isMatch || password.trim().isEmpty()) {
            throw new InvalidInputException(UserErrors.INVALID_PASSWORD);
        }
        else{
            return true;
        }
    }

    public static boolean validatePhoneNumber(String phoneNumber) throws InvalidInputException {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new InvalidInputException(UserErrors.INVALID_PHONE_NUMBER);
        }

        Pattern pattern = Pattern.compile(UserValidationRegex.PHONE_NUMBER_REGEX);
        
        Matcher matcher = pattern.matcher(phoneNumber.trim());

        if (!matcher.matches()) {
            throw new InvalidInputException(UserErrors.INVALID_PHONE_NUMBER);
        }
        return true;
    }
}
