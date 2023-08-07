use aravind_venkedeshwaran_corejava_project;

CREATE TABLE tasks(
	taskId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    taskName VARCHAR(255) NOT NULL,
    taskDescription VARCHAR(255) NOT NULL,
    dueDate date NOT NULL,
    priority VARCHAR(50) NOT NULL,
    taskStatus VARCHAR(50) NOT NULL,
    taskNotes VARCHAR(255) NOT NULL,
    reminder DATETIME NOT NULL,
    createdDate DATE NOT NULL,
    createdTime DATETIME NOT NULL
    );

CREATE TABLE taskTags (
    tagId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    taskId INT NOT NULL,
    tagName VARCHAR(255) NOT NULL,
    FOREIGN KEY (taskId) REFERENCES tasks(taskId)
);

INSERT INTO tasks (taskName, taskDescription, dueDate, priority, taskStatus, taskNotes, reminder, createdDate, createdTime)
VALUES
    ('Create Model Task object', 'Description of Create Model Task object', '2023-08-05', 'High', 'Not Started', 'Notes for Create Model Task object', '2023-08-05 16:00:00', '2023-08-05', '2023-08-05 14:15:00'),
    ('Write validator for the task object', 'Description of Write validator for the task object', '2023-08-05', 'Medium', 'Not Started', 'Notes for Write validator for the task object', '2023-08-05 16:00:00', '2023-08-05', '2023-08-05 14:15:00'),
    ('Test the task validator', 'Description of Test the task validator', '2023-08-05', 'Low', 'Not Started', 'Notes for Test the task validator', '2023-08-05 16:00:00', '2023-08-05', '2023-08-05 14:15:00'),
    ('Write query in DAOLayer to store tasks in database', 'Description of Task 4', '2023-08-06', 'High', 'Not Started', 'Notes for Task 4', '2023-08-06 16:00:00', '2023-08-05', '2023-08-05 14:15:00'),
    ('Write serviceLayer to validate task and insert the task data', 'Description of Task 5', '2023-08-06', 'Medium', 'Not Started', 'Notes for Task 5', '2023-08-06 16:00:00', '2023-08-05', '2023-08-05 14:15:00'),
    ('Write testcase for the service layer', 'Description of Task 6', '2023-08-06', 'Low', 'Not Started', 'Notes for Task 6', '2023-08-06 16:00:00', '2023-08-05', '2023-08-05 14:15:00'),
    ('Create Model Habit object', 'Description of Create Model Habit object', '2023-08-05', 'High', 'Not Started', 'Notes for Create Model Habit object', '2023-08-05 16:00:00', '2023-08-05', '2023-08-05 14:15:00'),
    ('Write validator for the habit object', 'Description of Write validator for the habit object', '2023-08-05', 'Medium', 'Not Started', 'Notes for Write validator for the habit object', '2023-08-05 16:00:00', '2023-08-05', '2023-08-05 14:15:00'),
    ('Test the habit validator', 'Description of Test the habit validator', '2023-08-05', 'Low', 'Not Started', 'Notes for Test the habit validator', '2023-08-05 16:00:00', '2023-08-05', '2023-08-05 14:15:00'),
    ('Write query in DAOLayer to store habit in database', 'Description of Task 10', '2023-08-06', 'High', 'Not Started', 'Notes for Task 10', '2023-08-06 16:00:00', '2023-08-05', '2023-08-05 14:15:00'),
    ('Write serviceLayer to validate habit and insert the habit data', 'Description of Task 5', '2023-08-06', 'Medium', 'Not Started', 'Notes for Task 5', '2023-08-06 16:00:00', '2023-08-05', '2023-08-05 14:15:00'),
    ('Write testcase for the service layer', 'Description of Task 6', '2023-08-06', 'Low', 'Not Started', 'Notes for Task 6', '2023-08-06 16:00:00', '2023-08-05', '2023-08-05 14:15:00'); 
SELECT * FROM tasks;


INSERT INTO taskTags (taskId, tagName) VALUES (1, 'task'), (2, 'task'), (3, 'task'), (4, 'task'), (5, 'task'), (6, 'task'), (7, 'habit'), (8, 'habit'), (9, 'habit'), (10, 'habit'), (11, 'habit'), (12, 'habit');

--SELECT
--     t.taskId,
--    t.taskName,
--    t.taskDescription,
--    t.dueDate,
--    t.priority,
--    t.taskStatus,
--    t.taskNotes,
--    t.reminder,
--    t.createdDate,
--    t.createdTime,
--    tt.tagName
--FROM
--    tasks t
--JOIN
--    taskTags tt ON t.taskId = tt.taskId;

SELECT * FROM taskTags; 


    