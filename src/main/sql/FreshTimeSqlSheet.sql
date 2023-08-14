use aravind_venkedeshwaran_corejava_project;

-- use freshtime;

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
    createdTime DATETIME NOT NULL,
    taskStatusUpdatedTime DATETIME
    );

CREATE TABLE taskTags (
    tagId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    taskId INT NOT NULL,
    tagName VARCHAR(255) NOT NULL,
    FOREIGN KEY (taskId) REFERENCES tasks(taskId)
);

CREATE TABLE subTasks (
	subtaskId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    taskId INT NOT NULL,
    subtask VARCHAR(255) NOT NULL,
    FOREIGN KEY (taskId) REFERENCES tasks(taskId)
);

CREATE TABLE dailyProgress(
	progressId INT PRIMARY KEY AUTO_INCREMENT,
    date DATE NOT NULL,
    totalNoOfTask int DEFAULT 0,
    completedTask int DEFAULT 0,
    progress int
    );

CREATE TABLE weeklyProgress(
	progressId INT PRIMARY KEY AUTO_INCREMENT,
    weekNo int DEFAULT 0,
    startOfWeeK int DEFAULT 0,
    endOfWeek int DEFAULT 0,
    totalNoOfTask int DEFAULT 0,
    completedTask int DEFAULT 0,
    progress int
    );

CREATE TABLE overallProgress(
	progressId INT PRIMARY KEY AUTO_INCREMENT,
    totalNoOfTask int DEFAULT 0,
    completedTask int DEFAULT 0,
    progress int
    );

INSERT INTO overallProgress (progress) VALUES (0);

CREATE TABLE users(
	userId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    emailId VARCHAR(255) UNIQUE NOT NULL,
    userName VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
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


INSERT INTO taskTags (taskId, tagName) VALUES (1, 'task'), (2, 'task'), (3, 'task'), (4, 'task'), (5, 'task'), (6, 'task'),
(7, 'habit'), (8, 'habit'), (9, 'habit'), (10, 'habit'), (11, 'habit'), (12, 'habit');


SELECT * FROM tasks;

SELECT * FROM taskTags;

SELECT * FROM subTasks;

SELECT * FROM dailyProgress;

SELECT * FROM weeklyProgress;

SELECT * FROM overallProgress;

SELECT * FROM users;


SELECT t.taskId, t.taskName, s.subtask, tt.tagName
FROM tasks t
LEFT JOIN taskTags tt ON t.taskId = tt.taskId
LEFT JOIN subtasks s ON t.taskId = s.taskId;


    