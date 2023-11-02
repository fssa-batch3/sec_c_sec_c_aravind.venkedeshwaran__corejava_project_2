package com.fssa.freshtime.validators;

import java.util.regex.Pattern;

import com.fssa.freshtime.exceptions.InvalidInputException;
import com.fssa.freshtime.models.User;
import com.fssa.freshtime.validators.errors.UserErrors;
import com.fssa.freshtime.validators.regexs.UserValidationRegex;

public class UserValidator {

	public static boolean validateUser(User user) throws InvalidInputException {

		if (user == null) {
			throw new InvalidInputException(UserErrors.INVALID_USER_NULL);
		}

		validateEmailId(user.getEmailId());
		validateUserName(user.getUserName());
		validatePassword(user.getPassword());

		return true;
	}

	public static boolean validateEmailId(String emailId) throws InvalidInputException {
		

		if (emailId == null) {
			throw new InvalidInputException(UserErrors.INVALID_EMAIL_NULL);
		}

		if (emailId.trim().isEmpty()) {
			throw new InvalidInputException(UserErrors.INVALID_EMAIL_EMPTY);
		}

		boolean isMatch = emailId.matches(UserValidationRegex.EMAIL_REGEX);

		if (!isMatch) {
			throw new InvalidInputException(UserErrors.INVALID_EMAIL_FORMAT);
		}

		return true;

	}

	public static boolean validateUserName(String userName) throws InvalidInputException {
		
		userName = userName.trim();
		
		if (userName == null) {
			throw new InvalidInputException(UserErrors.INVALID_USERNAME_NULL);
		}

		if (userName.isEmpty()) {
			throw new InvalidInputException(UserErrors.INVALID_USERNAME_EMPTY);
		}

		if (userName.length() < 3) {
			throw new InvalidInputException(UserErrors.INVALID_USERNAME_SHORT);
		}


		return true;

	}

	public static boolean validatePassword(String password) throws InvalidInputException {
		
	    // At least one uppercase letter
	    Pattern uppercasePattern = Pattern.compile(".*[A-Z].*");

	    // At least one lowercase letter
	    Pattern lowercasePattern = Pattern.compile(".*[a-z].*");

	    // At least one digit
	    Pattern digitPattern = Pattern.compile(".*[0-9].*");

	    // At least one special character
	    Pattern specialCharPattern = Pattern.compile(".*[!@#$%^&*()_+{}\\[\\]:;\"'<>,.?/~].*");

	    // No whitespaces
	    Pattern noWhitespacePattern = Pattern.compile("\\S*");

	    // Match each pattern against the password
	    boolean isUppercaseValid = uppercasePattern.matcher(password).matches();
	    boolean isLowercaseValid = lowercasePattern.matcher(password).matches();
	    boolean isDigitValid = digitPattern.matcher(password).matches();
	    boolean isSpecialCharValid = specialCharPattern.matcher(password).matches();
	    boolean isNoWhitespaceValid = noWhitespacePattern.matcher(password).matches();

	
	
	    if (password == null) {
	        throw new InvalidInputException(UserErrors.INVALID_PASSWORD_NULL);
	    }
	    
	    if(password.isEmpty()) {
	    	throw new InvalidInputException(UserErrors.INVALID_PASSWORD_EMPTY);
	    }
	
	    if (password.length() < 8) {
	        throw new InvalidInputException(UserErrors.INVALID_PASSWORD_LENGTH);
	    }
	    
		if (!isDigitValid) {
			throw new InvalidInputException(UserErrors.INVALID_PASSWORD_DIGIT);
		}

		if (!isSpecialCharValid) {
			throw new InvalidInputException(UserErrors.INVALID_PASSWORD_SPECIAL_CHAR);
		}
		
		if(!isUppercaseValid) {
			throw new InvalidInputException(UserErrors.INVALID_PASSWORD_UPPERCASE);
		}

		if(!isLowercaseValid) {
			throw new InvalidInputException(UserErrors.INVALID_PASSWORD_LOWERCASE);
		}
		
		if(!isNoWhitespaceValid) {
			throw new InvalidInputException(UserErrors.INVALID_PASSWORD_WHITESPACE);
		}
	    
	    return true;
		
	}
		
	
	
}


