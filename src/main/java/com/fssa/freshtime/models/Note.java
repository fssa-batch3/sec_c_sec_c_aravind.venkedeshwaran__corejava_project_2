package com.fssa.freshtime.models;

import java.time.LocalDateTime;

public class Note {

	private int notesId;
	private String heading;
	private String notesCategory;
	private String notes;
	private LocalDateTime createdOn;

	public int getNotesId() {
		return notesId;
	}

	public void setNotesId(int notes_id) {
		this.notesId = notes_id;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getNotesCategory() {
		return notesCategory;
	}

	public void setNotesCategory(String notes_category) {
		this.notesCategory = notes_category;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	@Override
    public String toString() {
        return "Notes{" +
                "\n    notesId=" + notesId +
                "\n    heading=" + heading +
                ",\n    notesCategory='" + notesCategory + '\'' +
                ",\n    notes='" + notes + '\'' +
                ",\n    createdOn=" + createdOn +
                '\n' + '}';
    }
	
	
	

}
