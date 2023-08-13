package com.fssa.freshtime.services;

import com.fssa.freshtime.dao.UserDAO;
import com.fssa.freshtime.exceptions.DAOException;
import com.fssa.freshtime.exceptions.InvalidInputException;
import com.fssa.freshtime.models.User;
import com.fssa.freshtime.validations.UserValidator;

public class UserService {
    public boolean userSignUp(User user) throws InvalidInputException, DAOException {
        UserDAO userDAO = new UserDAO();

        if(UserValidator.validateUser(user))
            return userDAO.userRegistration(user.getEmailId(), user.getUserName(), user.getPassword());

        return false;

    }

    public boolean userLogin(String emailId, String password) throws InvalidInputException, DAOException {
        UserDAO userDAO = new UserDAO();

        if(UserValidator.validateEmailId(emailId) && UserValidator.validatePassword(password))
            return userDAO.userLogin(emailId, password);

        return false;

    }

    public boolean forgotPassword(String emailId, String password) throws InvalidInputException, DAOException {
        UserDAO userDAO = new UserDAO();

        if(UserValidator.validateEmailId(emailId) && UserValidator.validatePassword(password))
            return userDAO.forgotPassword(emailId, password);

        return false;

    }

    public boolean deleteUser(String emailId, String password) throws InvalidInputException, DAOException {
        UserDAO userDAO = new UserDAO();

        if(UserValidator.validateEmailId(emailId) && UserValidator.validatePassword(password))
            return userDAO.deleteUser(emailId, password);

        return false;

    }
}
