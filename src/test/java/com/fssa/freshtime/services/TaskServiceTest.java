package com.fssa.freshtime.services;

import com.fssa.freshtime.dao.TaskDAO;
import com.fssa.freshtime.enums.TaskPriority;
import com.fssa.freshtime.enums.TaskStatus;
import com.fssa.freshtime.exceptions.DAOException;
import com.fssa.freshtime.exceptions.InvalidInputException;
import com.fssa.freshtime.models.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {


    Task getTask() {
        Task task = new Task();

        task.setTaskName("Write testcase for the validator");
        task.setTaskDescription("Write testcase for the validator, test one valid input and two or three invalid input");
        task.setDueDate(LocalDate.now().plusDays(1));
        task.setPriority(TaskPriority.HIGH);
        task.setTaskStatus(TaskStatus.INPROGRESS);
        task.setTaskNotes("Write testcase for the validator, test one valid input and two or three invalid input");
        task.setReminder(LocalDateTime.now().plusDays(1).plusMinutes(30));

        return task;
    }

    @Test
    void testAddTaskToDB() {
        Task task = getTask();
        TaskService taskService = new TaskService();
        try {
            assertTrue(taskService.addTaskToDB(task));
        }
        catch (DAOException e) {
            fail("An DAOException occurred: " + e.getMessage());
        }
        catch (InvalidInputException e) {
            fail("An Invalid Input exception occurred: " + e.getMessage());
        }
    }

    @Test
    void testReadTask() throws DAOException, InvalidInputException {
        TaskService taskService = new TaskService();
        try {
            ArrayList<Task> taskList = taskService.readAllTask();

            assertNotNull(taskList);
            assertFalse(taskList.isEmpty());

            for (Task task : taskList) System.out.println(task);

        } catch (DAOException e) {
            fail("An exception occurred: " + e.getMessage());
        }
    }

    @Test
    void testUpdateValidTaskName() {
        TaskService taskService = new TaskService();

        assertDoesNotThrow(() ->
                taskService.updateTaskAttribute(7, "taskName","(Updated Task) Create Model Habit object"));

    }


    @Test
    void testUpdateInvalidTaskName() {
        TaskService taskService = new TaskService();

        assertThrows(InvalidInputException.class, () -> {
            taskService.updateTaskAttribute(1, "taskName", "");
        });
    }

    @Test
    void testUpdateValidTaskDescription() {
        TaskService taskService = new TaskService();

        assertDoesNotThrow(() ->
                taskService.updateTaskAttribute(7, "taskDescription", "Updated Task Description"));
    }

    @Test
    void testUpdateInValidTaskDescription() {
        TaskService taskService = new TaskService();

        assertThrows(InvalidInputException.class,
                () -> taskService.updateTaskAttribute(7, "taskDescription", ""));
    }

    @Test
    void testUpdateValidDueDate() {
        TaskService taskService = new TaskService();

        assertDoesNotThrow(() ->
                taskService.updateTaskAttribute(7, "dueDate", LocalDate.now().plusDays(1)));
    }

    @Test
    void testUpdateInValidDueDate() {
        TaskService taskService = new TaskService();

        assertThrows(InvalidInputException.class,
                () -> taskService.updateTaskAttribute(7, "dueDate", LocalDate.now().minusDays(1)));
    }

    @Test
    void testUpdateValidPriority() {
        TaskService taskService = new TaskService();

        assertDoesNotThrow(() ->
                taskService.updateTaskAttribute(7, "priority", "Medium"));
    }

    @Test
    void testUpdateInValidPriority() {
        TaskService taskService = new TaskService();

        assertThrows(InvalidInputException.class,
                () -> taskService.updateTaskAttribute(7, "priority", "Not Important"));
    }

    @Test
    void testUpdateValidStatus() {
        TaskService taskService = new TaskService();

        assertDoesNotThrow(() ->
                taskService.updateTaskAttribute(7, "taskStatus", "In Progress"));
    }

    @Test
    void testUpdateInValidStatus() {
        TaskService taskService = new TaskService();

        assertThrows(InvalidInputException.class,
            () -> taskService.updateTaskAttribute(7, "taskStatus", "Started"));
    }

    @Test
    void testUpdateValidNotes() {
        TaskService taskService = new TaskService();

        assertDoesNotThrow(() ->
                taskService.updateTaskAttribute(7, "taskNotes", "Updated task notes."));
    }

    @Test
    void testUpdateInValidNotes() {
        TaskService taskService = new TaskService();

        assertThrows(InvalidInputException.class,
                () ->taskService.updateTaskAttribute(7, "taskNotes", ""));
    }

    @Test
    void testUpdateValidReminder() {
        TaskService taskService = new TaskService();

        assertDoesNotThrow(() ->
            taskService.updateTaskAttribute(7, "reminder", LocalDateTime.now().plusHours(2)));
    }

    @Test
    void testUpdateInValidReminder() {
        TaskService taskService = new TaskService();

        assertThrows(
            InvalidInputException.class, () ->
                    taskService.updateTaskAttribute(7, "reminder", LocalDateTime.now().minusMinutes(2))
        );
    }


    @Test
    void testDeleteTaskValidId() {
        TaskService taskService = new TaskService();
        assertDoesNotThrow(() -> taskService.deleteTask(14));
    }

    @Test
    void testDeleteTaskInValidId() {
        TaskService taskService = new TaskService();
        try {
            assertFalse(taskService.deleteTask(0));
        }
        catch (DAOException e) {
            fail("An DAOException occurred: " + e.getMessage());
        }
        catch (InvalidInputException e) {
            fail("An Invalid Input exception occurred: " + e.getMessage());
        }

    }

    @Test
    void testCreateTaskTag(){
        TaskService taskService = new TaskService();

        assertDoesNotThrow(() -> taskService.createTaskTag(13, "Validator"));
    }

    @Test
    void testReadTagsWithTask(){
        TaskService taskService = new TaskService();

        try{
            ArrayList<ArrayList<String>> taskWithTagsList = taskService.readTaskWithTags();

            assertNotNull(taskWithTagsList);
            assertFalse(taskWithTagsList.isEmpty());

            for (ArrayList<String> taskWithTags : taskWithTagsList){
                for(String row : taskWithTags){
                    System.out.print(row);
                }
                System.out.println();
            }
        }
        catch (DAOException e) {
            fail("An DAOException occurred: " + e.getMessage());
        }
    }

    @Test
    void testUpdateTaskTag(){
        TaskService taskService = new TaskService();
        assertDoesNotThrow(() -> taskService.updateTaskTag("Another Task", 1));
    }


    @Test
    void testCreateSubTask(){
        TaskService taskService = new TaskService();

        assertDoesNotThrow(() -> taskService.createSubTask(13, "Create Getters and Setters"));
    }

    @Test
    void testReadTaskWithSubTask(){
        TaskService taskService = new TaskService();

        try{
            ArrayList<ArrayList<String>> taskWithSubTaskList = taskService.readTaskWithSubTask();

            assertNotNull(taskWithSubTaskList);
            assertFalse(taskWithSubTaskList.isEmpty());

            for (ArrayList<String> taskWithSubTask : taskWithSubTaskList){
                for(String row : taskWithSubTask){
                    System.out.print(row);
                }
                System.out.println();

            }
        }
        catch (DAOException e) {
            fail("An DAOException occurred: " + e.getMessage());
        }
    }

    @Test
    void testUpdateSubTask(){
        TaskService taskService = new TaskService();
        assertDoesNotThrow(() -> taskService.updateSubTask("Another Sub Task", 2));
    }

    @Test
    void testChangeTaskStatusAndInsertDailyProgress() throws DAOException {
        TaskService taskService = new TaskService();

        boolean result = taskService.changeTaskStatus(TaskStatus.COMPLETED, 6);

        assertTrue(result);
    }




}
