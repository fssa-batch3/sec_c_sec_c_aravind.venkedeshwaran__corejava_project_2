package com.fssa.freshtime.validators;

import com.fssa.freshtime.exceptions.InvalidInputException;
import com.fssa.freshtime.models.Note;

public class NotesValidator {
	
	public static boolean validateNotes(Note note) throws InvalidInputException {
	    if (note == null) {
	        throw new InvalidInputException("Note cannot be null");
	    }


	    if (note.getHeading() == null || note.getHeading().isEmpty()) {
	        throw new InvalidInputException("Heading cannot be null or empty");
	    }

	    if (note.getNotes() == null || note.getNotes().isEmpty()) {
	        throw new InvalidInputException("Notes content cannot be null or empty");
	    }
	    
	    return true;
	}


    public static boolean validateCategory(String category) throws InvalidInputException {
    	if (category == null || category.isEmpty()) {
	        throw new InvalidInputException("Notes category cannot be null or empty");
	    }
		return true;
    }

}
