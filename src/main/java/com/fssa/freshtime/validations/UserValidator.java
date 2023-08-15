package com.fssa.freshtime.validations;

import com.fssa.freshtime.constants.ValidatorConstants;
import com.fssa.freshtime.errors.UserErrors;
import com.fssa.freshtime.exceptions.InvalidInputException;

import com.fssa.freshtime.models.User;
import com.fssa.freshtime.regexs.UserValidationRegex;

public class UserValidator {

    public static boolean validateUser(User user) throws InvalidInputException{
        if(user == null){
            throw new InvalidInputException(UserErrors.INVALID_USER_NULL);
        }
        validateEmailId(user.getEmailId());
        validateUserName(user.getUserName());
        validatePassword(user.getPassword());
        return true;
    }
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
        if(!isMatch || userName.trim().length() < ValidatorConstants.VERY_SHORT_MIN_LEN){
            throw  new InvalidInputException(UserErrors.INVALID_USERNAME);
        }
        else{
            return true;
        }
    }

    public static boolean validatePassword(String password) throws InvalidInputException{
        boolean isMatch = password.matches(UserValidationRegex.PASSWORD_REGEX);
        if (!isMatch || password.trim().length() < ValidatorConstants.MIN_PASSWORD_LEN) {
            throw new InvalidInputException(UserErrors.INVALID_PASSWORD);
        }
        else{
            return true;
        }
    }

}
