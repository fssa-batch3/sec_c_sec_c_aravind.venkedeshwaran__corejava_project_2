package com.fssa.freshtime.services;

import java.util.List;

import com.fssa.freshtime.dao.NoteDAO;
import com.fssa.freshtime.exceptions.DAOException;
import com.fssa.freshtime.exceptions.InvalidInputException;
import com.fssa.freshtime.exceptions.ServiceException;
import com.fssa.freshtime.models.Note;
import com.fssa.freshtime.validators.NotesValidator;

public class NoteService {

    public static boolean createNote(Note note) throws ServiceException {
        try {
			if (NotesValidator.validateNotes(note)) {
			    return NoteDAO.createNote(note);
			}
		} catch (InvalidInputException | DAOException e) {
			
			throw new ServiceException(e.getMessage());

		}
		return false;
    }

    public static List<Note> readNotes(String category) throws ServiceException {
        try {
			if (NotesValidator.validateCategory(category)) {
			    return NoteDAO.readNote(category);
			}
		} 
        catch (InvalidInputException | DAOException e) {
			throw new ServiceException(e.getMessage());
		}
        
		return null;
    }

    public static boolean updateNote(Note note) throws ServiceException {
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

    public static boolean deleteNotes(int notesId) throws ServiceException {
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
}