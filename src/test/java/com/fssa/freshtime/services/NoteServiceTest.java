package com.fssa.freshtime.services;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fssa.freshtime.exceptions.ServiceException;
import com.fssa.freshtime.models.Note;
import com.fssa.freshtime.utils.Logger;

public class NoteServiceTest {



    @Test
    public void testCreateValidNote() throws ServiceException {
        Note validNote = new Note();
        validNote.setNotesCategory("Category");
        validNote.setHeading("Sample Note");
        validNote.setNotes("<h1>\nTitle</h1><div><br></div><div>The Test Note</div>");

        assertTrue(NoteService.createNote(validNote));
    }

    @Test
    public void testCreateInvalidNote() {
        Note invalidNote = new Note(); // All fields are null

        assertThrows(ServiceException.class, () -> NoteService.createNote(invalidNote));
    }

    @Test
    public void testReadNotesWithValidCategory() throws ServiceException {
        String validCategory = "Category";
        
        List<Note> notesList = NoteService.readNotes(validCategory);

        assertNotNull(notesList);
        
        Logger.info(notesList);
    }

    @Test
    public void testReadNotesWithInvalidCategory() throws ServiceException {
        String invalidCategory = null; // Invalid category

       assertThrows(ServiceException.class, ()-> NoteService.readNotes(invalidCategory));
    }

    @Test
    public void testUpdateValidNote() throws ServiceException {
        Note validNote = new Note();
        validNote.setNotesId(1);
        validNote.setNotesCategory("Category");
        validNote.setHeading("Heading");
        validNote.setNotes("<h1>\nTitle</h1><div><br></div><div>The Updated Note</div>");

        assertTrue(NoteService.updateNote(validNote));
    }

    @Test
    public void testUpdateInvalidNote() {
        Note invalidNote = new Note(); // All fields are null

        assertThrows(ServiceException.class, ()-> NoteService.updateNote(invalidNote));
    }

    @Test
    public void testDeleteValidNotes() throws ServiceException {
        int validNoteId = 1; // Assuming this is a valid note ID

        assertTrue(NoteService.deleteNotes(validNoteId));
    }

    @Test
    public void testDeleteInvalidNotes() throws ServiceException {
        int invalidNoteId = -1; // Invalid note ID

        assertThrows(ServiceException.class, ()-> NoteService.deleteNotes(invalidNoteId));
    }
}

