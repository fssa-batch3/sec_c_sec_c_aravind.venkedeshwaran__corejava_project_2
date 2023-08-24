package com.fssa.freshtime.validators.regexs;

public class UserValidationRegex {
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+[a-zA-Z.-]+$";
    public static final String USERNAME_REGEX = "^[a-zA-Z ]+$";
    public static final String PASS_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$";
}
