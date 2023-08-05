package com.fssa.freshtime.validations;

import com.fssa.freshtime.errors.TaskErrors;
import com.fssa.freshtime.exceptions.InvalidInputException;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.*;
import com.fssa.freshtime.models.Task;



import static org.junit.jupiter.api.Assertions.*;

class TaskValidatorTest {

    @Test
    void valid_task_with_valid_input() {
        Task task = new Task();
        task.setTaskName("Test Task");
        task.setTaskDescription("TestCase for sample task");
        task.setDueDate(LocalDate.now().plusDays(5));
        task.setPriority("High");
        task.setTaskStatus("In Progress");
        task.setTaskNotes("This is the sample note for test task");
        task.setReminder(LocalDateTime.now().plusMinutes(30));

        assertDoesNotThrow(() -> TaskValidator.validate(task));
    }

    @Test
    void invalid_task_with_invalid_taskName() {
        Task task = new Task();
        task.setTaskName("Do");
        task.setTaskDescription("TestCase for sample task");
        task.setDueDate(LocalDate.now().plusDays(5));
        task.setPriority("High");
        task.setTaskStatus("In Progress");
        task.setTaskNotes("This is the sample note for test task");
        task.setReminder(LocalDateTime.now().plusMinutes(30));

        assertThrows(InvalidInputException.class, () -> TaskValidator.validate(task));
    }

    @Test
    void invalid_task_with_invalid_dueDate() {
        Task task = new Task();
        task.setTaskName("Do");
        task.setTaskDescription("TestCase for sample task");
        task.setDueDate(LocalDate.now().minusDays(1));
        task.setPriority("High");
        task.setTaskStatus("In Progress");
        task.setTaskNotes("This is the sample note for test task");
        task.setReminder(LocalDateTime.now().plusMinutes(30));

        assertThrows(InvalidInputException.class, () -> TaskValidator.validate(task));
    }

    @Test
    void invalid_task_with_invalid_status() {
        Task task = new Task();
        task.setTaskName("Do");
        task.setTaskDescription("TestCase for sample task");
        task.setDueDate(LocalDate.now().plusDays(5));
        task.setPriority("Waste Of Time");
        task.setTaskStatus("In Progress");
        task.setTaskNotes("This is the sample note for test task");
        task.setReminder(LocalDateTime.now().plusMinutes(30));

        assertThrows(InvalidInputException.class, () -> TaskValidator.validate(task));
    }

    @Test
    void invalid_task_with_invalid_reminder() {
        Task task = new Task();
        task.setTaskName("Do");
        task.setTaskDescription("TestCase for sample task");
        task.setDueDate(LocalDate.now().plusDays(5));
        task.setPriority("High");
        task.setTaskStatus("In Progress");
        task.setTaskNotes("This is the sample note for test task");
        task.setReminder(LocalDateTime.now());

        assertThrows(InvalidInputException.class, () -> TaskValidator.validate(task));
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
    void test_valid_priority_high() {
        assertDoesNotThrow(() -> TaskValidator.validatePriority("High"));
    }

    @Test
    void test_valid_priority_medium() {
        assertDoesNotThrow(() -> TaskValidator.validatePriority("Medium"));
    }

    @Test
    void test_valid_priority_low() {
        assertDoesNotThrow(() -> TaskValidator.validatePriority("Low"));
    }

    @Test
    void test_invalid_priority() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validatePriority("Very Important"), TaskErrors.INVALID_PRIORITY);
    }

    @Test
    void test_invalid_priority_with_empty_space() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validatePriority(""), TaskErrors.INVALID_PRIORITY);
    }

    @Test
    void test_invalid_priority_with_numbers() {
        assertThrows(InvalidInputException.class, () -> TaskValidator.validatePriority("124"), TaskErrors.INVALID_PRIORITY);
    }

    @Test
    void test_valid_status_Not_started(){
        assertDoesNotThrow(() -> TaskValidator.validateStatus("Not started"));
    }

    @Test
    void test_valid_status_Scheduled(){
        assertDoesNotThrow(() -> TaskValidator.validateStatus("Scheduled"));
    }

    @Test
    void test_valid_status_In_progress(){
        assertDoesNotThrow(() -> TaskValidator.validateStatus("In progress"));
    }

    @Test
    void test_valid_status_Overdue(){
        assertDoesNotThrow(() -> TaskValidator.validateStatus("Overdue"));
    }

    @Test
    void test_valid_status_Completed(){
        assertDoesNotThrow(() -> TaskValidator.validateStatus("Completed"));
    }

    @Test
    void test_valid_status_Cancelled(){
        assertDoesNotThrow(() -> TaskValidator.validateStatus("Cancelled"));
    }

    @Test
    void test_invalid_status_not_important(){
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateStatus("Not Important"), TaskErrors.INVALID_STATUS);
    }

    @Test
    void test_invalid_status_waste_of_time(){
        assertThrows(InvalidInputException.class, () -> TaskValidator.validateStatus("Waste Of Time"), TaskErrors.INVALID_STATUS);
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

    @Test
    void testAddTaskTag() {
        Task task = new Task();

        String taskName = "Urgent Task Tag Task";
        task.setTaskName(taskName);

        String taskTag = "Test Case";
        task.setTag(taskTag);

        task.setTaskDescription("TestCase for sample task");
        task.setDueDate(LocalDate.now().plusDays(5));
        task.setPriority("High");
        task.setTaskStatus("In Progress");
        task.setTaskNotes("This is the sample note for test task");
        task.setReminder(LocalDateTime.now().plusMinutes(30));


        HashMap<String, ArrayList<String>> taskTagsMap = task.getTaskTagsMap();
        assertTrue(taskTagsMap.containsKey(taskTag));
        assertTrue(taskTagsMap.get(taskTag).contains(taskName));
        System.out.println(task.getTaskTagsMap());

    }

    @Test
    void testAddingMultipleTaskInSingleTag() {
        Task task1 = new Task();
        String task1Name = "Urgent Task Tag Task";
        task1.setTaskName(task1Name);
        String task1Tag = "Test Case";
        task1.setTag(task1Tag);

        Task task2 = new Task();
        String task2Name = "Another Urgent Task Tag Task";
        task2.setTaskName(task2Name);
        String task2Tag = "Test Case";
        task2.setTag(task2Tag);



        HashMap<String, ArrayList<String>> taskTagsMap = task2.getTaskTagsMap();

        assertTrue(taskTagsMap.containsKey(task1Tag));
        assertTrue(taskTagsMap.containsKey(task2Tag));

        assertTrue(taskTagsMap.get(task1Tag).contains(task1Name));
        assertTrue(taskTagsMap.get(task2Tag).contains(task2Name));

        System.out.println(task2.getTaskTagsMap());

    }


}
