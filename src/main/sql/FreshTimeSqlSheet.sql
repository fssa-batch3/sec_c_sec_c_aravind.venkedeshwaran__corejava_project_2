-- CREATE DATABASE freshtime;

use aravind_venkedeshwaran_corejava_project;

-- use freshtime;

CREATE TABLE users(
	user_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    email_id VARCHAR(255) UNIQUE NOT NULL, 
    user_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
    );
    
INSERT INTO users (email_id, user_name, password) VALUES 
("testuser01@gmail.com", "TestNameOne",  "P@$$w0rd"),
("testuser02@gmail.com", "TestNameTwo",  "P@$$w0rd"),
("testuser03@gmail.com", "TestNameThree",  "P@$$w0rd"),
("testDelete@gmail.com", "Deletable user",  "P@$$w0rd");

CREATE TABLE tasks(
    task_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    task_name VARCHAR(255) NOT NULL,
    task_description VARCHAR(255),
    due_date DATE,
    priority VARCHAR(50) CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH')),
    task_status VARCHAR(50) CHECK (task_status IN ('NOTSTARTED', 'SCHEDULED', 'INPROGRESS', 'COMPLETED', 'OVERDUE', 'CANCELLED')),
    task_notes VARCHAR(255), 
    reminder DATETIME,
    created_date_time DATETIME DEFAULT NOW() NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);




CREATE TABLE tasktags (
    tag_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    task_id INT NOT NULL,
    tag_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (task_id) REFERENCES tasks(task_id)
);

CREATE TABLE subtasks (
	subtask_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    task_id INT NOT NULL,
    subtask VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    due_date date,
    priority VARCHAR(50),
    status VARCHAR(50),
    reminder DATETIME,
    created_date_time DATETIME DEFAULT NOW() NOT NULL,
    FOREIGN KEY (task_id) REFERENCES tasks(task_id)
);

CREATE TABLE dailyprogress(
	progress_id INT PRIMARY KEY AUTO_INCREMENT,
    date DATE NOT NULL,
    total_no_Of_task int DEFAULT 0, 
    completed_task int DEFAULT 0, 
    progress int
    );
    
CREATE TABLE weeklyprogress(
	progress_id INT PRIMARY KEY AUTO_INCREMENT,
    week_no int DEFAULT 0,
    start_of_week DATE,
    end_of_week DATE,
    total_no_of_task int DEFAULT 0, 
    completed_task int DEFAULT 0, 
    progress int
    );
    
CREATE TABLE overallprogress(
	progress_id INT PRIMARY KEY AUTO_INCREMENT,
    total_task_count int DEFAULT 0, 
    total_completed_task_count int DEFAULT 0, 
    progress int
    );
    
INSERT INTO overallprogress (progress) VALUES (0);
    

INSERT INTO tasks (user_id, task_name, task_description, due_date, priority, task_status, task_notes, reminder, created_date_time)
VALUES
(1,'Create Model Task object', 'Description of Create Model Task object', '2023-08-05', 'HIGH', 'NOTSTARTED', 'Notes for Create Model Task object', '2023-08-05 16:00:00', '2023-08-05 14:15:00'),
(1,'Write validator for the task object', 'Description of Write validator for the task object', '2023-08-05', 'MEDIUM', 'NOTSTARTED', 'Notes for Write validator for the task object', '2023-08-05 16:00:00', '2023-08-05 14:15:00'),
(1,'Test the task validator', 'Description of Test the task validator', '2023-08-05', 'LOW', 'NOTSTARTED', 'Notes for Test the task validator', '2023-08-05 16:00:00', '2023-08-05 14:15:00'),
(2,'Write query in DAOLayer to store tasks in database', 'Description of Task 4', '2023-08-06', 'HIGH', 'NOTSTARTED', 'Notes for Task 4', '2023-08-06 16:00:00', '2023-08-05 14:15:00'),
(2,'Write serviceLayer to validate task and insert the task data', 'Description of Task 5', '2023-08-06', 'MEDIUM', 'NOTSTARTED', 'Notes for Task 5', '2023-08-06 16:00:00', '2023-08-05 14:15:00'),
(2,'Write testcase for the service layer', 'Description of Task 6', '2023-08-06', 'LOW', 'NOTSTARTED', 'Notes for Task 6', '2023-08-06 16:00:00', '2023-08-05 14:15:00');



INSERT INTO tasktags (task_id, tag_name) VALUES (1, 'task'), (2, 'task'), (3, 'task'), (4, 'task'), (5, 'task'), (6, 'task');

INSERT INTO subtasks (task_id, subtask) VALUES 
(1, 'generate getters and setter'), (1, 'generate to string'), 
(2, 'write validator for task name'), (2, 'write validator for due date'), 
(3, 'write test validator for task name'), (3, 'write test validator for due date'), 
(4, 'write query to store task'), (4, 'write query to store subtask'), 
(5, 'write service layer to store task'), (5, 'write service layer to store subtask'), 
(6, 'write testcase for service layer to store task'), (6, 'write testcase for service layer to store subtask');

SELECT * FROM tasks;

SELECT * FROM tasktags;

SELECT * FROM subtasks;

SELECT * FROM dailyprogress;

SELECT * FROM weeklyprogress;

SELECT * FROM overallprogress;

SELECT * FROM users;


-- SELECT t.taskId, t.taskName, s.subtask, tt.tagName
-- FROM tasks t
-- LEFT JOIN taskTags tt ON t.taskId = tt.taskId
-- LEFT JOIN subtasks s ON t.taskId = s.taskId;

SELECT 
tasks.task_id, 
subtasks.subtask_id,
tasks.task_name, 
subtasks.subtask , 
subtasks.description, 
subtasks.due_date, 
subtasks.priority, 
subtasks.status, 
subtasks.reminder 
FROM tasks
LEFT JOIN subtasks ON tasks.task_id = subtasks.task_id;


    