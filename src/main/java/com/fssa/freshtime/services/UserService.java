package com.fssa.freshtime.services;

import com.fssa.freshtime.dao.UserDAO;
import com.fssa.freshtime.exceptions.DAOException;
import com.fssa.freshtime.exceptions.InvalidInputException;
import com.fssa.freshtime.exceptions.ServiceException;
import com.fssa.freshtime.models.User;
import com.fssa.freshtime.utils.Logger;
import com.fssa.freshtime.validators.UserValidator;

import java.sql.SQLException;

public class UserService {

	UserDAO userDAO = new UserDAO();

	public boolean userSignUp(User user) throws ServiceException {
		Logger.info("Inserting user in db");
		try {
			if (UserValidator.validateUser(user))
				return userDAO.userRegistration(user);
		} catch (InvalidInputException | DAOException e) {
			throw new ServiceException(e.getMessage());
		}

		return false;

	}

	public boolean deleteUser(String emailId) throws ServiceException{
		Logger.info("Deleting user in db");
		try {
			if (UserValidator.validateEmailId(emailId)) {
				if(userDAO.emailExists(emailId)) {
					return userDAO.deleteUser(emailId);
				}
				else {
					throw new ServiceException("Email Not Found");
				}
			}
				
		} catch (InvalidInputException | DAOException | SQLException e) {
			throw new ServiceException(e.getMessage());
		}

		return false;

	}
	
	public boolean userLogin(String emailId, String password) throws ServiceException {
		Logger.info("checking email and pass in db");
	    try {
			if (UserValidator.validateEmailId(emailId) && UserValidator.validatePassword(password)) {
			    if (userDAO.emailExists(emailId)) {
			        return userDAO.userLogin(emailId, password);
			    } else {
			        throw new DAOException("Email not found: " + emailId);
			    }
			}
		} catch (InvalidInputException | DAOException | SQLException e) {
			throw new ServiceException(e.getMessage());
		}
	    return false;
	}


	public User getUserByEmail(String emailId) throws ServiceException {
		Logger.info("Getting user by email in db");
	    try {
			if (UserValidator.validateEmailId(emailId)) {
			    if (userDAO.emailExists(emailId)) {
			        return userDAO.getUserByEmail(emailId);
			    } else {
			        throw new DAOException("User not found for email: " + emailId);
			    }
			}
		} catch (InvalidInputException | DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	    return null;
	}
	
	public boolean forgotPassword(String emailId, String newPassword)throws ServiceException {
		Logger.info("Changing pass in db");
	    try {
			if (UserValidator.validateEmailId(emailId) && UserValidator.validatePassword(newPassword)) {
				
			    if (userDAO.emailExists(emailId)) {
			        return userDAO.forgotPasswordInDB(emailId, newPassword);
			    } else {
			        throw new DAOException("Email not found: " + emailId);
			    }
			}
		} catch (InvalidInputException | DAOException | SQLException e) {
			throw new ServiceException(e.getMessage());
		}
	    
	    return false;
	}
	

	 public boolean updateUserProfile(User user) throws ServiceException {
		 Logger.info("updating user in db");
	        try {
	            if(UserValidator.validateUser(user)) {
		            if (userDAO.emailExists(user.getEmailId())) {
		                return userDAO.updateUserProfile(user);
		            } else {
		                throw new DAOException("Email not found: " + user.getEmailId());
		            }
	            }
	        }
	        catch (DAOException | InvalidInputException e) {
	            throw new ServiceException("Error updating user profile: " + e.getMessage());
	        }
			return false;
	 }


}
