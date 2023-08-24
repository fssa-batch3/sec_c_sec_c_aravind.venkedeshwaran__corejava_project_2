package com.fssa.freshtime.services;

import com.fssa.freshtime.models.enums.TaskPriority;
import com.fssa.freshtime.models.enums.TaskStatus;
import com.fssa.freshtime.exceptions.DAOException;
import com.fssa.freshtime.exceptions.ServiceException;
import com.fssa.freshtime.models.Subtask;
import com.fssa.freshtime.models.Task;
import com.fssa.freshtime.models.Tasktags;
import com.fssa.freshtime.utils.Logger;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    TaskService taskService = new TaskService();

    Task getTask() {
        Task task = new Task();

        task.setTaskName("Write testcase for the validator");
        task.setDescription("Write testcase for the validator, test one valid input and two or three invalid input");
        task.setDueDate(LocalDate.now().plusDays(1));
        task.setPriority(TaskPriority.HIGH);
        task.setStatus(TaskStatus.INPROGRESS);
        task.setNotes("Write testcase for the validator, test one valid input and two or three invalid input");
        task.setReminder(LocalDateTime.now().plusDays(1).plusMinutes(30));

        return task;
    }

    @Test
    void testAddTaskToDB() {
        Task task = getTask();
        assertDoesNotThrow(()-> taskService.addTask(task));
    }

    @Test
    void testAddInvalidTask() {
        TaskService taskService = new TaskService();

        Task invalidTask = new Task();
        invalidTask.setTaskName("");

        assertThrows(ServiceException.class, () -> taskService.addTask(invalidTask));
    }


    @Test
    void testReadTask() {
        
        try {
            List<Task> taskList = taskService.readAllTask();

            assertNotNull(taskList);
            assertFalse(taskList.isEmpty());

            for (Task task : taskList) Logger.info(task);

        } catch (ServiceException e) {
            fail("An ServiceException occurred: " + e.getMessage());
        }
    }

    @Test
    void testUpdateValidTaskName() {
        Task task = getTask();
        task.setTaskId(1);
        task.setTaskName("Create Model Task object");

        assertDoesNotThrow(() ->
                taskService.updateTask(task));

    }


    @Test
    void testUpdateInvalidTaskName() {
        Task task = getTask();
        task.setTaskId(2);
        task.setTaskName("");

        assertThrows(ServiceException.class, () -> {
            taskService.updateTask(task);
        });
    }

    @Test
    void testUpdateValidTaskDescription() {
        Task task = getTask();
        task.setTaskId(2);
        task.setDescription("(Updated) Task Description");

        assertDoesNotThrow(() ->
                taskService.updateTask(task));
    }

    @Test
    void testUpdateInValidTaskDescription() {
        Task task = getTask();
        task.setTaskId(2);
        task.setDescription("");


        assertThrows(ServiceException.class,
                () -> taskService.updateTask(task));
    }

    @Test
    void testUpdateValidDueDate() {
        Task task = getTask();
        task.setTaskId(2);
        task.setDueDate(LocalDate.now());

        assertDoesNotThrow(() ->
                taskService.updateTask(task));
    }

    @Test
    void testUpdateInValidDueDate() {
        Task task = getTask();
        task.setTaskId(2);
        task.setDueDate(LocalDate.now().minusDays(1));

        assertThrows(ServiceException.class, () ->
                taskService.updateTask(task));
    }

    @Test
    void testUpdateValidPriority() {
        Task task = getTask();
        task.setTaskId(2);
        task.setPriority(TaskPriority.MEDIUM);

        assertDoesNotThrow(() ->
                taskService.updateTask(task));
    }


    @Test
    void testDeleteTaskValidId() {
        assertDoesNotThrow(() -> taskService.deleteTask(9));
    }

    @Test
    void testDeleteTaskInValidId()  {
        assertThrows(ServiceException.class, () -> taskService.deleteTask(0));
    }

    @Test
    void testCreateTaskTag(){

        assertDoesNotThrow(() -> taskService.createTaskTag(1, "Validator"));
    }

    @Test
    void testTaskTagInvalidId(){

        assertThrows(ServiceException.class, () -> taskService.createTaskTag(0, "Validator"));
    }

    @Test
    void testTaskTagNullTagName(){
        assertThrows(ServiceException.class, () -> taskService.createTaskTag(1, null));
    }

    @Test
    void testTaskTagInvalidTagName(){
        assertThrows(ServiceException.class, () -> taskService.createTaskTag(1, ""));
    }


    @Test
    void testReadTagsWithTask(){
        try{
            List<Tasktags> taskWithTagsList = taskService.readTaskTags();

            assertNotNull(taskWithTagsList);
            assertFalse(taskWithTagsList.isEmpty());

            for (Tasktags tags : taskWithTagsList){
                Logger.info(tags);
            }
        }
        catch (DAOException e) {
            fail("An DAOException occurred: " + e.getMessage());
        }
    }

    @Test
    void testUpdateTaskTag(){
        
        assertDoesNotThrow(() -> taskService.updateTaskTag("Updated Tag", 1));
    }

    @Test
    void testUpdateTaskTagInvalidId(){

        assertThrows(ServiceException.class, () -> taskService.updateTaskTag("Updated Tag", 0));
    }

    @Test
    void testUpdateNullTaskTag(){

        assertThrows(ServiceException.class, () -> taskService.updateTaskTag(null, 1));
    }

    private Subtask getSubtask() {
        Subtask subtask = new Subtask();

        subtask.setTaskId(1);
        subtask.setSubtaskName("Create Detailed subtask");
        subtask.setDescription("Create Detailed subtask description");
        subtask.setDueDate(LocalDate.now().plusDays(1));
        subtask.setPriority(TaskPriority.MEDIUM);
        subtask.setStatus(TaskStatus.INPROGRESS);
        subtask.setReminder(LocalDateTime.now().plusDays(1).plusMinutes(30));

        return subtask;
    }

    @Test
    void testCreateSubTask(){
        Subtask subtask = getSubtask();

        assertDoesNotThrow(() -> taskService.createSubtask(subtask));
    }

    @Test
    void testCreateInvalidSubTask(){
        Subtask subtask = getSubtask();
        subtask.setSubtaskName("");

        assertThrows(ServiceException.class, () -> taskService.createSubtask(subtask));
    }


    @Test
    void testReadTaskWithSubTask(){
        try{
            List<Subtask> taskWithSubTaskList = taskService.readSubtask();

            assertNotNull(taskWithSubTaskList);
            assertFalse(taskWithSubTaskList.isEmpty());

            for (Subtask subtask : taskWithSubTaskList){
                Logger.info(subtask);
            }
        }
        catch (ServiceException e) {
            fail("An Exception occurred: " + e.getMessage());
        }
    }


    @Test
    void testUpdateValidSubtask(){
        Subtask subtask = getSubtask();
        subtask.setSubtaskId(13);
        subtask.setSubtaskName("(Updated) Create Detailed subtask");

        assertDoesNotThrow(() -> taskService.updateSubtask(subtask));
    }
    @Test
    void testUpdateTaskInvalidTaskId() {
        Subtask subtask = new Subtask();
        subtask.setTaskId(0);
        subtask.setTaskName("Updated Task");

        assertThrows(ServiceException.class, () -> taskService.updateSubtask(subtask));
    }



    @Test
    void testChangeTaskStatusAndInsertProgress() throws DAOException {
        assertTrue(taskService.changeTaskStatus(TaskStatus.COMPLETED, 2));
    }
}
