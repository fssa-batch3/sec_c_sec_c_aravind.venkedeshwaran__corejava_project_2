package com.fssa.freshtime.validators;

import com.fssa.freshtime.models.enums.TaskPriority;
import com.fssa.freshtime.models.enums.TaskStatus;
import com.fssa.freshtime.validators.errors.TaskErrors;
import com.fssa.freshtime.exceptions.InvalidInputException;
import org.junit.jupiter.api.Test;

import java.time.*;
import com.fssa.freshtime.models.Task;



import static org.junit.jupiter.api.Assertions.*;

class TaskValidatorTest {


    Task getTask() {
        Task task = new Task();

        task.setTaskName("Write testcase for the validator");
        task.setDescription("Write testcase for the validator, test one valid input and two or three invalid input");
        task.setDueDate(LocalDate.now().plusDays(1));
        task.setPriority(TaskPriority.HIGH);
        task.setStatus(TaskStatus.NOTSTARTED);
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

    @Test
    void testValidTaskDescription() {
        assertDoesNotThrow(() -> TaskValidator.validateTaskDescription("Test Case For Task 01"));
    }

    @Test
    void testInvalidTaskDescriptionWithEmptySpaces() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateTaskDescription(""), TaskErrors.INVALID_TASK_DESCRIPTION_LESS_THAN_TEN_CHAR);
    }

    @Test
    void testInvalidTaskDescriptionWithLessThanTenChar() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateTaskDescription("Just Test"), TaskErrors.INVALID_TASK_NOTES_LESS_THAN_TEN_CHAR);
    }

    @Test
    void testInvalidTaskDescriptionWithMoreThanFiftyChar() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateTaskDescription("Efficiently manage your tasks with our user-friendly to-do application. Organize, prioritize, and track your daily activities with due dates, reminders, and task notes, ensuring you stay on top of everything effortlessly. Increase productivity and achieve your goals with our feature-rich to-do app designed to simplify task management and keep you focused on what matters most."), TaskErrors.INVALID_TASK_DESCRIPTION_MORE_THAN_ONEFIFTY_CHAR);
    }


    @Test
    void testValidDueDateToday() {
        LocalDate today = LocalDate.now();
        assertDoesNotThrow(() -> TaskValidator.validateDueDate(today));
    }



    @Test
    void testValidDueDateFuture() {
        LocalDate today = LocalDate.now();
        LocalDate tenDaysFromToday = today.plusDays(10);// Add 10 days to the current date

        assertDoesNotThrow(() -> TaskValidator.validateDueDate(tenDaysFromToday));
    }

    @Test
    void testNullDueDate(){
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateDueDate(null));
    }

    @Test
    void testInvalidDueDateYesterday() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateDueDate(yesterday), TaskErrors.INVALID_DUEDATE_BEFORE_DATE);
    }

    @Test
    void testInvalidDueDateBeforeDate() {
        LocalDate today = LocalDate.now();
        LocalDate tenDaysBeforeToday = today.minusDays(10);// Minus 10 days to the current date

        assertThrows(InvalidInputException.class, () -> TaskValidator.validateDueDate(tenDaysBeforeToday), TaskErrors.INVALID_DUEDATE_BEFORE_DATE);
    }

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
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateTaskDescription("Notes"), TaskErrors.INVALID_TASK_NOTES_LESS_THAN_TEN_CHAR);
    }

    @Test
    void testInvalidTaskNotesMoreThanFiftyChar() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateTaskDescription("Efficiently manage your tasks with our user-friendly to-do application. Organize, prioritize, and track your daily activities with due dates, reminders, and task notes, ensuring you stay on top of everything effortlessly. Increase productivity and achieve your goals with our feature-rich to-do app designed to simplify task management and keep you focused on what matters most."), TaskErrors.INVALID_TASK_NOTES_MORE_THAN_ONEFIFTY_CHAR);
    }

    @Test
    void testValidReminderAfterOneMin() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminder_after_1_min = now.plusMinutes(1);

        assertDoesNotThrow(() -> TaskValidator.validateReminder(reminder_after_1_min));
    }

    @Test
    void testValidReminderAfterThirtyMin() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminder_after_30_min = now.plusMinutes(30);

        assertDoesNotThrow(() -> TaskValidator.validateReminder(reminder_after_30_min));
    }

    @Test
    void testInvalidReminderCurTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateReminder(currentTime), TaskErrors.INVALID_REMINDER_CURRENT_TIME);
    }

    @Test
    void testInvalidReminderBeforeAMin() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminder_before_a_min = now.minusMinutes(1);

        assertThrows(InvalidInputException.class, () -> TaskValidator.validateReminder(reminder_before_a_min), TaskErrors.INVALID_REMINDER_BEFORE_DATE_TIME);
    }

    @Test
    void testInvalidReminderBeforeThreeHrs() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminder_before_3_hours = now.minusHours(3);

        assertThrows(InvalidInputException.class, () -> TaskValidator.validateReminder(reminder_before_3_hours), TaskErrors.INVALID_REMINDER_BEFORE_DATE_TIME);
    }

    @Test
    void testInvalidReminderBeforeADay() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime reminder_before_a_day = today.minusDays(1);

        assertThrows(InvalidInputException.class, () -> TaskValidator.validateReminder(reminder_before_a_day), TaskErrors.INVALID_REMINDER_BEFORE_DATE_TIME);
    }

}
