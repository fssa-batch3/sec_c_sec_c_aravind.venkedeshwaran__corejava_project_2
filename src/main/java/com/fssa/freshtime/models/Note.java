package com.fssa.freshtime.models;

import java.time.LocalDate;

public class Note {

	private int notesId;
	private int userId;
	private String heading;
	private String notesCategory;
	private String notes;
	private LocalDate createdOn;

	public int getNotesId() {
		return notesId;
	}

	public void setNotesId(int notes_id) {
		this.notesId = notes_id;
	}
	

	public int getUserId() {
		return userId;
	}

	public void setUserId(int user_id) {
		this.userId = user_id;
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

	public LocalDate getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDate createdOn) {
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
