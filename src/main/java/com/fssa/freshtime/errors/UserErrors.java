package com.fssa.freshtime.errors;

public class UserErrors {

    public static final String INVALID_EMAIL_ID = "Invalid Email Id: Email should can not be empty or null should contain username \"@\" domain name";
    public static final String INVALID_USERNAME = "Invalid User Name: username can not ne empty or null contains at least 3 characters and should not contains number and special character";
    public static final String INVALID_PASSWORD = """
            Invalid Password: It contains at least 8 characters and at most 20 characters.
            It contains at least one digit.
            It contains at least one upper case alphabet.
            It contains at least one lower case alphabet.
            It contains at least one special character.
            It does’t contain any white space.""";
    public static final String INVALID_PHONE_NUMBER = "Invalid Phone Number: Phone Number can not be null or empty. It must have 10 digits.";
}
