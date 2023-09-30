package com.fssa.freshtime.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fssa.freshtime.exceptions.DAOException;
import com.fssa.freshtime.models.Note;
import com.fssa.freshtime.utils.ConnectionUtil;

public class NoteDAO {

	public static boolean createNote(Note note) throws DAOException {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String insertQuery = "INSERT INTO notes (user_id, notes_category, heading, notes, createdOn) VALUES (?, ?, ?, ?, ?)";
			try (PreparedStatement psmt = connection.prepareStatement(insertQuery)) {

				psmt.setInt(1, note.getUserId());
				psmt.setString(2, note.getNotesCategory());
				psmt.setString(3, note.getHeading());
				psmt.setString(4, note.getNotes());
				psmt.setDate(5,java.sql.Date.valueOf(LocalDate.now()));
				int rowAffected = psmt.executeUpdate();

				return rowAffected > 0;
			}
		} catch (SQLException e) {
			throw new DAOException("Error while creating note: " + e.getMessage());
		}
	}

	public static List<Note> readAllNotesByUser(int userId) throws DAOException {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String selectQuery = "SELECT notes_id, notes_category, heading, notes, createdOn FROM notes WHERE user_id = ?";

			try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {

				psmt.setInt(1, userId);
				
				try (ResultSet rs = psmt.executeQuery()) {
					
					List<Note> notesList = new ArrayList<>();
					while (rs.next()) {
						Note note = new Note();

						note.setNotesId(rs.getInt("notes_id"));
						note.setNotesCategory(rs.getString("notes_category"));
						note.setHeading(rs.getString("heading"));
						note.setNotes(rs.getString("notes"));
						note.setCreatedOn(rs.getDate("createdOn").toLocalDate());

						notesList.add(note);
					}
					return notesList;
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Error while reading note: " + e.getMessage());
		}
	}
	
	
	public static Note readNotesByNotesId(int notesId) throws DAOException {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String selectQuery = "SELECT user_id, notes_category, heading, notes, createdOn FROM notes WHERE notes_id = ?";

			try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {

				psmt.setInt(1, notesId);
				
				try (ResultSet rs = psmt.executeQuery()) {
					
					Note note = new Note();
					
					while (rs.next()) {
						
						note.setUserId(rs.getInt("user_id"));
						note.setNotesCategory(rs.getString("notes_category"));
						note.setHeading(rs.getString("heading"));
						note.setNotes(rs.getString("notes"));
						note.setCreatedOn(rs.getDate("createdOn").toLocalDate());

					}
					return note;
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Error while reading note: " + e.getMessage());
		}
	}
	
	public static List<Note> readNoteByCategory(String category, int userId) throws DAOException {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String selectQuery = "SELECT notes_id, notes_category, heading, notes, createdOn FROM notes WHERE notes_category = ? AND user_id = ?";

			try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {

				psmt.setString(1, category);

				try (ResultSet rs = psmt.executeQuery()) {
					List<Note> notesList = new ArrayList<>();
					while (rs.next()) {
						Note note = new Note();

						note.setNotesId(rs.getInt("notes_id"));
						note.setNotesCategory(rs.getString("notes_category"));
						note.setHeading(rs.getString("heading"));
						note.setNotes(rs.getString("notes"));
						note.setCreatedOn(rs.getDate("createdOn").toLocalDate());

						notesList.add(note);
					}
					return notesList;
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Error while reading note: " + e.getMessage());
		}
	}

	public static boolean updateNote(Note note) throws DAOException {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String insertQuery = "UPDATE notes SET heading = ? , notes = ? WHERE notes_id = ?";
			try (PreparedStatement psmt = connection.prepareStatement(insertQuery)) {

				// psmt.setString(1, note.getNotesCategory());
				psmt.setString(1, note.getHeading());
				psmt.setString(2, note.getNotes());
				psmt.setInt(3, note.getNotesId());

				int rowAffected = psmt.executeUpdate();

				return rowAffected > 0;
			}
		} catch (SQLException e) {
			throw new DAOException("Error while Updating note: " + e.getMessage());
		}
	}

	public static boolean deleteNotes(int notesId) throws DAOException {
		try (Connection connection = ConnectionUtil.getConnection()) {

			String deleteQuery = "DELETE FROM notes WHERE notes_id=?";
			try (PreparedStatement psmt = connection.prepareStatement(deleteQuery)) {
				psmt.setInt(1, notesId);

				int rowAffected = psmt.executeUpdate();

				return rowAffected > 0;
			}
		} catch (SQLException e) {
			throw new DAOException("Error while deleting Notes: " + e.getMessage());
		}
	}

	public static List<Integer> getAllIds() throws DAOException {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String selectQuery = "SELECT notes_id FROM notes";
			try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {
				try (ResultSet rs = psmt.executeQuery()) {
					List<Integer> idList = new ArrayList<>();
					while (rs.next()) {
						idList.add(rs.getInt("notes_id"));
					}
					return idList;
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Error while reading notes: " + e.getMessage());
		}
	}
	
	
    public static List<String> getAllCategory() throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String selectQuery = "SELECT DISTINCT notes_category FROM notes";
            try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {
                try (ResultSet rs = psmt.executeQuery()) {
                    List<String> catList = new ArrayList<>();
                    while (rs.next()) {
                        catList.add(rs.getString("notes_category"));
                    }
                    return catList;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error while reading Category: " + e.getMessage());
        }
    }

}
