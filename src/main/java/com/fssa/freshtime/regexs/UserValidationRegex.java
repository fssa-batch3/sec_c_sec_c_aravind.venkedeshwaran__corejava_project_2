package com.fssa.freshtime.regexs;

public class UserValidationRegex {
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final String USERNAME_REGEX = "^[A-Za-z]\\w{2,29}$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$";
    public static final String PHONE_NUMBER_REGEX = "^[0-9]{10}$";;
}
