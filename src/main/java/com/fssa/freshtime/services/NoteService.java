package com.fssa.freshtime.services;

import java.util.List;

import com.fssa.freshtime.dao.NoteDAO;
import com.fssa.freshtime.dao.TaskDAO;
import com.fssa.freshtime.dao.UserDAO;
import com.fssa.freshtime.exceptions.DAOException;
import com.fssa.freshtime.exceptions.InvalidInputException;
import com.fssa.freshtime.exceptions.ServiceException;
import com.fssa.freshtime.models.Note;
import com.fssa.freshtime.utils.Logger;
import com.fssa.freshtime.validators.NotesValidator;
import com.fssa.freshtime.validators.TaskValidator;

public class NoteService {

    public boolean createNote(Note note) throws ServiceException {
    	Logger.info("Inserting Notes in db");
        try {
			if (NotesValidator.validateNotes(note)) {
			    return NoteDAO.createNote(note);
			}
		} catch (InvalidInputException | DAOException e) {
			
			throw new ServiceException(e.getMessage());

		}
		return false;
    }

    public List<Note> readAllNotesByUser(int userId) throws ServiceException{
    	Logger.info("Reading All Notes in db");
    	try {
    		return NoteDAO.readAllNotesByUser(userId);
    	}
    	catch(DAOException e) {
    		throw new ServiceException(e.getMessage());
    	}
    }
    
    public Note readNotesByNotesId(int notesId) throws ServiceException{
    	Logger.info("Reading Notes by user Id in db");
    	try {
    		return NoteDAO.readNotesByNotesId(notesId);
    	}
    	catch(DAOException e) {
    		throw new ServiceException(e.getMessage());
    	}
    }
    public List<Note> readNotesByCategory(String category, int userId) throws ServiceException {
    	Logger.info("Reading Notes by Category in db");
        try {
			if (NotesValidator.validateCategory(category)) {
			    return NoteDAO.readNoteByCategory(category, userId);
			}
		} 
        catch (InvalidInputException | DAOException e) {
			throw new ServiceException(e.getMessage());
		}
        
		return null;
    }

    public boolean updateNote(Note note) throws ServiceException {
    	Logger.info("Updating Notes");
            try {
            	if (NotesValidator.validateNotes(note)) {
            		return NoteDAO.updateNote(note);
            	}
            } 
            catch (DAOException | InvalidInputException e) {
    			throw new ServiceException(e.getMessage());
    		}
            
			return false;
        
    }

    public boolean deleteNotes(int notesId) throws ServiceException {
    	Logger.info("Deleting Notes in db");
        try {
			if (NoteDAO.getAllIds().contains(notesId)) {
			    try {
			        return NoteDAO.deleteNotes(notesId);
			    } catch (DAOException e) {
			        throw new ServiceException("Error while deleting notes: " + e.getMessage());
			    }
			} else {
			    throw new ServiceException("Invalid notes ID");
			}
		} catch (DAOException | ServiceException e) {
			throw new ServiceException(e.getMessage());
		}
    }
    
    public List<String> getAllCategory() throws ServiceException{
    	Logger.info("Getting Notes category");
    	try {
    		return NoteDAO.getAllCategory();
    	}
    	catch(DAOException e) {
    		throw new ServiceException(e.getMessage());
    	}
    }
}