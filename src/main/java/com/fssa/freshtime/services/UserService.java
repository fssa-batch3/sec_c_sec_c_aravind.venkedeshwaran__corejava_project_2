package com.fssa.freshtime.services;

import com.fssa.freshtime.dao.UserDAO;
import com.fssa.freshtime.exceptions.DAOException;
import com.fssa.freshtime.exceptions.InvalidInputException;
import com.fssa.freshtime.models.User;
import com.fssa.freshtime.validators.UserValidator;

import java.sql.SQLException;

public class UserService {

    //TODO: catch all other exception and pass as service exception
    public boolean userSignUp(User user) throws InvalidInputException, DAOException {
        UserDAO userDAO = new UserDAO();

        if(UserValidator.validateUser(user))
            return userDAO.userRegistration(user.getEmailId(), user.getUserName(), user.getPassword());

        return false;

    }

    public boolean userLogin(String emailId, String password) throws InvalidInputException, DAOException, SQLException {
        UserDAO userDAO = new UserDAO();

        if(UserValidator.validateEmailId(emailId) && UserValidator.validatePassword(password))
            return userDAO.userLogin(emailId, password);

        return false;

    }


//TODO: check the new password is already a old password

    public boolean forgotPassword(String emailId, String newPassword) throws InvalidInputException, DAOException, SQLException {
        UserDAO userDAO = new UserDAO();
        if (UserValidator.validateEmailId(emailId) && UserValidator.validatePassword(newPassword)) {
            return userDAO.forgotPasswordInDB(emailId, newPassword);
        }
        return false;
    }

    public boolean deleteUser(String emailId, String password) throws InvalidInputException, DAOException, SQLException {
        UserDAO userDAO = new UserDAO();

        if(UserValidator.validateEmailId(emailId) && UserValidator.validatePassword(password))
            return userDAO.deleteUser(emailId, password);

        return false;

    }
}
