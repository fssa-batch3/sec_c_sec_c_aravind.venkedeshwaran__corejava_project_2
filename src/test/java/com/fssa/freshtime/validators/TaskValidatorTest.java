package com.fssa.freshtime.validators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fssa.freshtime.exceptions.InvalidInputException;
import com.fssa.freshtime.models.Task;
import com.fssa.freshtime.models.enums.TaskPriority;
import com.fssa.freshtime.models.enums.TaskStatus;
import com.fssa.freshtime.validators.errors.TaskErrors;


class TaskValidatorTest {
	
	LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    String formattedTime = now.format(formatter);
    
    // Parse the formatted string back into a LocalDateTime
    LocalDateTime currentTime = LocalDateTime.parse(formattedTime, formatter);
	

    Task getTask() {
        Task task = new Task();

        task.setTaskName("Write testcase for the validator");
        task.setStartDate(LocalDateTime.now().plusDays(1));
        task.setEndDate(LocalDateTime.now().plusDays(2));
        task.setPriority(TaskPriority.HIGH);
        task.setStatus(TaskStatus.TODO);
        task.setNotes("Write testcase for the validator, test one valid input and two or three invalid input");
        task.setReminder(LocalDateTime.now().plusDays(1).plusMinutes(30));

        return task;
    }

    @Test
    void testValidTask() {
        Task task = getTask();
        assertDoesNotThrow(() -> TaskValidator.validate(task));
    }

    @Test
    void testNullTask(){
        assertThrows(InvalidInputException.class, () -> TaskValidator.validate(null));
    }

    
//  Task Name Validation Test Case  
    @Test
    void testValidateTaskName() {
        assertDoesNotThrow(() -> TaskValidator.validateTaskName("Task 01"));
    }

    @Test
    void testInvalidTaskNameWithEmptySpaces() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateTaskName(""), TaskErrors.INVALID_TASK_NAME_LESS_THAN_THREE_CHAR);
    }

    @Test
    void testInvalidTaskNameWithLessThanThreeChar() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateTaskName("Do"), TaskErrors.INVALID_TASK_NAME_LESS_THAN_THREE_CHAR);
    }

    @Test
    void testInvalidTaskNameWithMoreThanFiftyChar() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateTaskName("Implement new feature in the application to enhance user experience and improve overall performance"), TaskErrors.INVALID_TASK_NAME_MORE_THAN_FIFTY_CHAR);
    }

// 	Start Time Test Cases
    @Test
    void testValidStartDateToday() {
        assertDoesNotThrow(() -> TaskValidator.validateStartDate(currentTime));
    }

    @Test
    void testValidStartDateFuture() {
        
        LocalDateTime tenDaysFromToday = currentTime.plusDays(10);// Add 10 days to the current date

        assertDoesNotThrow(() -> TaskValidator.validateStartDate(tenDaysFromToday));
    }

    @Test
    void testNullStartDate(){
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateStartDate(null));
    }

    @Test
    void testInvalidStartDateYesterday() {
        
        LocalDateTime yesterday = currentTime.minusDays(1);
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateStartDate(yesterday), TaskErrors.INVALID_DATE_BEFORE_DATE);
    }

    @Test
    void testInvalidStartDateBeforeDate() {
        
        LocalDateTime tenDaysBeforeToday = currentTime.minusDays(10);// Minus 10 days to the current date

        assertThrows(InvalidInputException.class, () -> TaskValidator.validateStartDate(tenDaysBeforeToday), TaskErrors.INVALID_DATE_BEFORE_DATE);
    }
    
// 	End Time Test Cases
    @Test
    void testValidEndDateToday() {
        assertDoesNotThrow(() -> TaskValidator.validateEndDate(currentTime));
    }

    @Test
    void testValidEndDateFuture() {
        
        LocalDateTime tenDaysFromToday = currentTime.plusDays(10);// Add 10 days to the current date

        assertDoesNotThrow(() -> TaskValidator.validateEndDate(tenDaysFromToday));
    }

    @Test
    void testNullEndDate(){
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateEndDate(null));
    }

    @Test
    void testInvalidEndDateYesterday() {
        
        LocalDateTime yesterday = currentTime.minusDays(1);
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateEndDate(yesterday), TaskErrors.INVALID_DATE_BEFORE_DATE);
    }

    @Test
    void testInvalidEndDateBeforeDate() {
        
        LocalDateTime tenDaysBeforeToday = currentTime.minusDays(10);// Minus 10 days to the current date

        assertThrows(InvalidInputException.class, () -> TaskValidator.validateEndDate(tenDaysBeforeToday), TaskErrors.INVALID_DATE_BEFORE_DATE);
    }

//  Task Notes Validation Test cases
    @Test
    void testValidTaskNotes() {
        assertDoesNotThrow(() -> TaskValidator.validateTaskNotes("Test Note For Task 01"));
    }

    @Test
    void testInvalidTaskNotesEmptySpaces() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateTaskNotes(""), TaskErrors.INVALID_TASK_NOTES_LESS_THAN_TEN_CHAR);
    }

    @Test
    void testInvalidTaskNotesLessThanTenChar() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateTaskNotes("Notes"), TaskErrors.INVALID_TASK_NOTES_LESS_THAN_TEN_CHAR);
    }

    @Test
    void testInvalidTaskNotesMoreThanFiftyChar() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateTaskNotes("Efficiently manage your tasks with our user-friendly to-do application. Organize, prioritize, and track your daily activities with due dates, reminders, and task notes, ensuring you stay on top of everything effortlessly. Increase productivity and achieve your goals with our feature-rich to-do app designed to simplify task management and keep you focused on what matters most."), TaskErrors.INVALID_TASK_NOTES_MORE_THAN_ONEFIFTY_CHAR);
    }

    
//  Reminder Validator Test Cases
    @Test
    void testValidReminderAfterOneMin() {
        
        LocalDateTime reminder_after_1_min = currentTime.plusMinutes(1);

        assertDoesNotThrow(() -> TaskValidator.validateReminder(reminder_after_1_min));
    }

    @Test
    void testValidReminderAfterThirtyMin() {
        
        LocalDateTime reminder_after_30_min = currentTime.plusMinutes(30);

        assertDoesNotThrow(() -> TaskValidator.validateReminder(reminder_after_30_min));
    } 

    @Test
    void testInvalidReminderCurTime() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateReminder(currentTime), TaskErrors.INVALID_REMINDER_CURRENT_TIME);
    }

    @Test
    void testInvalidReminderBeforeAMin() {
        
        LocalDateTime reminder_before_a_min = currentTime.minusMinutes(1);

        assertThrows(InvalidInputException.class, () -> TaskValidator.validateReminder(reminder_before_a_min), TaskErrors.INVALID_REMINDER_BEFORE_DATE_TIME);
    }

    @Test
    void testInvalidReminderBeforeThreeHrs() {
        
        LocalDateTime reminder_before_3_hours = currentTime.minusHours(3);

        assertThrows(InvalidInputException.class, () -> TaskValidator.validateReminder(reminder_before_3_hours), TaskErrors.INVALID_REMINDER_BEFORE_DATE_TIME);
    }

    @Test
    void testInvalidReminderBeforeADay() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime reminder_before_a_day = currentTime.minusDays(1);

        assertThrows(InvalidInputException.class, () -> TaskValidator.validateReminder(reminder_before_a_day), TaskErrors.INVALID_REMINDER_BEFORE_DATE_TIME);
    }

}
