package com.fssa.freshtime.validators.errors;

public class UserErrors {
	
	public static final String INVALID_USER_NULL = "User can not be null";
	
	// For Invalid Email
	public static final String INVALID_EMAIL_NULL = "Email cannot be null.";
	public static final String INVALID_EMAIL_EMPTY = "Email cannot be empty.";
	public static final String INVALID_EMAIL_FORMAT = "Invalid Email Format: Please provide a valid email address.";

	// For Invalid Username
	public static final String INVALID_USERNAME_NULL = "Username cannot be null.";
	public static final String INVALID_USERNAME_EMPTY = "Username cannot be empty.";
	public static final String INVALID_USERNAME_SHORT = "Username too short: Please provide a username with at least three characters.";
	public static final String INVALID_USERNAME_NUMERIC = "Username cannot contains numbers.";
	public static final String INVALID_USERNAME_SPECIAL_CHAR = "Invalid Characters: Username cannot contain special characters.";

	
   //For Invalid Password
	public static final String INVALID_PASSWORD_NULL = "Invalid Password: Password cannot be null.";
	public static final String INVALID_PASSWORD_EMPTY = "Invalid Password: Password cannot be empty.";
	public static final String INVALID_PASSWORD_LENGTH = "Invalid Password: Password must be between 8 characters.";
	public static final String INVALID_PASSWORD_DIGIT = "Invalid Password: Password must contain at least one digit.";
	public static final String INVALID_PASSWORD_UPPERCASE = "Invalid Password: Password must contain at least one uppercase letter.";
	public static final String INVALID_PASSWORD_LOWERCASE = "Invalid Password: Password must contain at least one lowercase letter.";
	public static final String INVALID_PASSWORD_SPECIAL_CHAR = "Invalid Password: Password must contain at least one special character.";
	public static final String INVALID_PASSWORD_WHITESPACE = "Invalid Password: Password cannot contain whitespace.";


	
}
