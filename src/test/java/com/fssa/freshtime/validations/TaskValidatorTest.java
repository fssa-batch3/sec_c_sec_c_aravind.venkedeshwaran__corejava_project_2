package com.fssa.freshtime.validations;

import com.fssa.freshtime.enums.TaskPriority;
import com.fssa.freshtime.enums.TaskStatus;
import com.fssa.freshtime.errors.TaskErrors;
import com.fssa.freshtime.exceptions.InvalidInputException;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.*;
import com.fssa.freshtime.models.Task;



import static org.junit.jupiter.api.Assertions.*;

class TaskValidatorTest {


    Task getTask() {
        Task task = new Task();

        task.setTaskName("Write testcase for the validator");
        task.setTaskDescription("Write testcase for the validator, test one valid input and two or three invalid input");
        task.setDueDate(LocalDate.now().plusDays(1));
        task.setPriority(TaskPriority.HIGH);
        task.setTaskStatus(TaskStatus.NOTSTARTED);
        task.setTaskNotes("Write testcase for the validator, test one valid input and two or three invalid input");
        task.setReminder(LocalDateTime.now().plusDays(1).plusMinutes(30));

        return task;
    }

    @Test
    void valid_task_with_valid_input() {
        Task task = getTask();
        assertDoesNotThrow(() -> TaskValidator.validate(task));
    }

    @Test
    void test_valid_task_name() {
        assertDoesNotThrow(() -> TaskValidator.validateTaskName("Task 01"));
    }

    @Test
    void test_invalid_task_name_with_empty_spaces() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateTaskName(""), TaskErrors.INVALID_TASK_NAME);
    }

    @Test
    void test_invalid_task_name_less_than_three_characters() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateTaskName("Do"), TaskErrors.INVALID_TASK_NAME);
    }

    @Test
    void test_invalid_task_name_more_than_fifty_characters() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateTaskName("Implement new feature in the application to enhance user experience and improve overall performance"), TaskErrors.INVALID_TASK_NAME);
    }

    @Test
    void test_valid_task_description() {
        assertDoesNotThrow(() -> TaskValidator.validateTaskDescription("Test Case For Task 01"));
    }

    @Test
    void test_invalid_task_description_with_empty_spaces() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateTaskDescription(""), TaskErrors.INVALID_TASK_DESCRIPTION);
    }

    @Test
    void test_invalid_task_description_less_than_ten_characters() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateTaskDescription("Just Test"), TaskErrors.INVALID_TASK_DESCRIPTION);
    }

    @Test
    void test_invalid_task_description_more_than_one_fifty_characters() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateTaskDescription("Efficiently manage your tasks with our user-friendly to-do application. Organize, prioritize, and track your daily activities with due dates, reminders, and task notes, ensuring you stay on top of everything effortlessly. Increase productivity and achieve your goals with our feature-rich to-do app designed to simplify task management and keep you focused on what matters most."), TaskErrors.INVALID_TASK_DESCRIPTION);
    }


    @Test
    void test_valid_due_date_with_today_date() {
        LocalDate today = LocalDate.now();
        assertDoesNotThrow(() -> TaskValidator.validateDueDate(today));
    }

    @Test
    void test_valid_due_date_with_future_date() {
        LocalDate today = LocalDate.now();
        LocalDate tenDaysFromToday = today.plusDays(10);// Add 10 days to the current date

        assertDoesNotThrow(() -> TaskValidator.validateDueDate(tenDaysFromToday));
    }

    @Test
    void test_invalid_due_date_with_yesterday_date() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateDueDate(yesterday), TaskErrors.INVALID_DUEDATE);
    }

    @Test
    void test_invalid_due_date_with_before_date() {
        LocalDate today = LocalDate.now();
        LocalDate tenDaysBeforeToday = today.minusDays(10);// Minus 10 days to the current date

        assertThrows(InvalidInputException.class, () -> TaskValidator.validateDueDate(tenDaysBeforeToday), TaskErrors.INVALID_DUEDATE);
    }

    @Test
    void test_valid_task_notes() {
        assertDoesNotThrow(() -> TaskValidator.validateTaskNotes("Test Note For Task 01"));
    }

    @Test
    void test_invalid_task_notes_with_empty_spaces() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateTaskNotes(""), TaskErrors.INVALID_TASK_NOTES);
    }

    @Test
    void test_invalid_task_notes_less_than_ten_characters() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateTaskDescription("Notes"), TaskErrors.INVALID_TASK_NOTES);
    }

    @Test
    void test_invalid_task_notes_more_than_one_fifty_characters() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateTaskDescription("Efficiently manage your tasks with our user-friendly to-do application. Organize, prioritize, and track your daily activities with due dates, reminders, and task notes, ensuring you stay on top of everything effortlessly. Increase productivity and achieve your goals with our feature-rich to-do app designed to simplify task management and keep you focused on what matters most."), TaskErrors.INVALID_TASK_NOTES);
    }

    @Test
    void test_valid_reminder_after_one_minute() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminder_after_1_min = now.plusMinutes(1);

        assertDoesNotThrow(() -> TaskValidator.validateReminder(reminder_after_1_min));
    }

    @Test
    void test_valid_reminder_after_thirty_minutes() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminder_after_30_min = now.plusMinutes(30);

        assertDoesNotThrow(() -> TaskValidator.validateReminder(reminder_after_30_min));
    }

    @Test
    void test_valid_reminder_after_three_hours() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminder_after_3_hours = now.plusHours(3);

        assertDoesNotThrow(() -> TaskValidator.validateReminder(reminder_after_3_hours));
    }

    @Test
    void test_invalid_reminder_current_time() {
        LocalDateTime currentTime = LocalDateTime.now();
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateReminder(currentTime), TaskErrors.INVALID_REMINDER);
    }

    @Test
    void test_invalid_reminder_before_a_minute_from_now() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminder_before_a_min = now.minusMinutes(1);

        assertThrows(InvalidInputException.class, () -> TaskValidator.validateReminder(reminder_before_a_min), TaskErrors.INVALID_REMINDER);
    }

    @Test
    void test_invalid_reminder_before_three_hours_from_now() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminder_before_3_hours = now.minusHours(3);

        assertThrows(InvalidInputException.class, () -> TaskValidator.validateReminder(reminder_before_3_hours), TaskErrors.INVALID_REMINDER);
    }

    @Test
    void test_invalid_reminder_before_a_day_from_today() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime reminder_before_a_day = today.minusDays(1);

        assertThrows(InvalidInputException.class, () -> TaskValidator.validateReminder(reminder_before_a_day), TaskErrors.INVALID_REMINDER);
    }

    @Test
    void test_valid_created_date(){
        LocalDate today = LocalDate.now();
        assertDoesNotThrow(() -> TaskValidator.validateCreatedDate(today));
    }

    @Test
    void test_invalid_created_date_with_before_date(){
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateCreatedDate(yesterday), TaskErrors.INVALID_CREATEDDATE);
    }

    @Test
    void test_invalid_created_date_with_after_date(){
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateCreatedDate(tomorrow), TaskErrors.INVALID_CREATEDDATE);
    }

    @Test
    void test_valid_created_time(){
        LocalDateTime currentTime = LocalDateTime.now();

        assertDoesNotThrow(() -> TaskValidator.validateCreatedTime(currentTime));
    }

    @Test
    void test_invalid_created_time_thirty_minute_before_now(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime created_before_30_min = currentDateTime.minusMinutes(30);
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateCreatedTime(created_before_30_min), TaskErrors.INVALID_CREATEDTIME);
    }

    @Test
    void test_invalid_created_time_thirty_minute_after_now(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime created_after_thirty_min = currentDateTime.plusMinutes(1);
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateCreatedTime(created_after_thirty_min), TaskErrors.INVALID_CREATEDTIME);
    }
}
