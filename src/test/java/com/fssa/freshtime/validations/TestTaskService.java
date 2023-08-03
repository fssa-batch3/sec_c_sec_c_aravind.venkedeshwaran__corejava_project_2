package com.fssa.freshtime.validations;

import com.fssa.freshtime.dao.TaskDAO;
import com.fssa.freshtime.errors.TaskErrors;
import com.fssa.freshtime.exceptions.DAOException;
import com.fssa.freshtime.exceptions.InvalidInputException;
import com.fssa.freshtime.services.TaskService;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fssa.freshtime.models.Task;
import com.fssa.freshtime.services.TaskService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestTaskService {


    Task getTask(){
        Task task = new Task();
        task.setTaskId(1);
        task.setTaskName("Test Task");
        task.setTaskDescription("TestCase for sample task");
        task.setDueDate(LocalDate.now().plusDays(5));
        task.setPriority("High");
        task.setTaskStatus("In Progress");
        task.setTaskNotes("This is the sample note for test task");
        task.setReminder(LocalDateTime.now().plusMinutes(30));
        task.setTag("Test Case");
        return task;
    }

    @Test
    void testAddTaskToDB(){
        Task task = getTask();
        TaskService taskService = new TaskService();
        assertDoesNotThrow(() -> taskService.addTaskToDB(task));
    }

    @Test
    void testUpdateValidTaskName() {
        Task task = getTask();
        task.setTaskName("Updated Task");
        TaskService taskService = new TaskService();

        assertDoesNotThrow(() -> taskService.updateTaskName(task));
    }

    @Test
    void testUpdateInvalidTaskName() {
        Task task = getTask();
        task.setTaskName("");
        TaskService taskService = new TaskService();

        assertThrows(InvalidInputException.class, () -> taskService.updateTaskName(task));
    }

    @Test
    void testUpdateValidTaskDescription() {
        Task task = getTask();
        task.setTaskDescription("This is Updated Task Description");
        TaskService taskService = new TaskService();

        assertDoesNotThrow(() -> taskService.updateTaskDescription(task));
    }

    @Test
    void testUpdateInvalidTaskDescription() {
        Task task = getTask();
        task.setTaskDescription("");
        TaskService taskService = new TaskService();

        assertThrows(InvalidInputException.class, () -> taskService.updateTaskDescription(task));
    }

    @Test
    void testUpdateValidTaskDueDate() {
        Task task = getTask();
        task.setDueDate(LocalDate.now().plusDays(3));
        TaskService taskService = new TaskService();

        assertDoesNotThrow(() -> taskService.updateTaskDueDate(task));
    }

    @Test
    void testUpdateInValidTaskDueDate() {
        Task task = getTask();
        task.setDueDate(LocalDate.now().minusDays(3));
        TaskService taskService = new TaskService();

        assertThrows(InvalidInputException.class, () -> taskService.updateTaskDueDate(task));
    }


    @Test
    void test_updateTaskPriority_validInput() {
        Task task = getTask();
        TaskService taskService = new TaskService();
        task.setPriority("Low");
        assertDoesNotThrow(() -> taskService.updateTaskPriority(task));
    }

    @Test
    void test_updateTaskPriority_invalidInput() {
        Task task = getTask();
        TaskService taskService = new TaskService();
        task.setPriority("Very Important");
        assertThrows(InvalidInputException.class, () -> taskService.updateTaskPriority(task));
    }

    @Test
    void test_updateTaskStatus_validInput() {
        Task task = getTask();
        TaskService taskService = new TaskService();
        task.setTaskStatus("Completed");
        assertDoesNotThrow(() -> taskService.updateTaskStatus(task));
    }

    @Test
    void test_updateTaskStatus_invalidInput() {
        Task task = getTask();
        TaskService taskService = new TaskService();
        task.setTaskStatus("Started");
        assertThrows(InvalidInputException.class, () -> taskService.updateTaskStatus(task));
    }

    @Test
    void test_updateTaskNotes_validInput() {
        Task task = getTask();
        TaskService taskService = new TaskService();
        task.setTaskNotes("This is the updated note for service test task");
        assertDoesNotThrow(() -> taskService.updateTaskNotes(task));
    }

    @Test
    void test_updateTaskNotes_invalidInput() {
        Task task = getTask();
        TaskService taskService = new TaskService();
        task.setTaskNotes("updated");
        assertThrows(InvalidInputException.class, () -> taskService.updateTaskNotes(task));
    }

    @Test
    void test_updateTaskReminder_validInput() {
        Task task = getTask();
        TaskService taskService = new TaskService();
        task.setReminder(LocalDateTime.now().plusDays(3));
        assertDoesNotThrow(() -> taskService.updateTaskReminder(task));
    }

    @Test
    void test_updateTaskReminder_invalidInput() {
        Task task = getTask();
        TaskService taskService = new TaskService();
        task.setReminder(LocalDateTime.now().minusDays(1));
        assertThrows(InvalidInputException.class, () -> taskService.updateTaskReminder(task));
    }

    @Test
    void testDeleteInValidTask(){
        Task task = getTask();
        task.setTaskId(0);
        TaskService taskService = new TaskService();
        assertThrows(InvalidInputException.class, () -> taskService.deleteTask(task));
    }

    @Test
    void testDeleteValidTask(){
        Task task = getTask();
        TaskService taskService = new TaskService();
        assertDoesNotThrow(() -> taskService.deleteTask(task));
    }

    @Test
    void testReadTask() throws DAOException, InvalidInputException {
        Task task = getTask();
        TaskService taskService = new TaskService();
        assertDoesNotThrow(() -> taskService.readTask(task));
        System.out.println(taskService.readTask(task));
    }

    @Test
    void testCreateTaskTag() {
        Task task = new Task();
        task.setTaskId(3);
        task.setTaskName("Test Task");
        task.setTaskDescription("TestCase for sample task");
        task.setDueDate(LocalDate.now().plusDays(5));
        task.setPriority("High");
        task.setTaskStatus("In Progress");
        task.setTaskNotes("This is the sample note for test task");
        task.setReminder(LocalDateTime.now().plusMinutes(30));
        task.setTag("Test Case");


        TaskService taskService = new TaskService();
        assertDoesNotThrow(() -> taskService.createTaskTag(task));
    }

}
