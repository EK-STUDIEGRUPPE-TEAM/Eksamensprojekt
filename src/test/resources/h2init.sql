DROP TABLE IF EXISTS SubTask;
DROP TABLE IF EXISTS Task;
DROP TABLE IF EXISTS SubProject;
DROP TABLE IF EXISTS Project;
DROP TABLE IF EXISTS Users;

CREATE TABLE Users
(
    user_id  INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(100),
    email    VARCHAR(100) UNIQUE,
    password VARCHAR(50)
);

CREATE TABLE Project
(
    project_id  INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100),
    description VARCHAR(200),
    date        DATE,
    deadline DATE,
    budget     DECIMAL(10, 2) DEFAULT 0.00,
    status      VARCHAR(50),
    user_id     INT,
    FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE
);

CREATE TABLE SubProject (
    subProject_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    status VARCHAR(50),
    project_id INT,
    FOREIGN KEY (project_id) REFERENCES Project(project_id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS Task (
    task_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    description VARCHAR(200),
    deadline DATE,
    status VARCHAR(50),
    hourlyRate DECIMAL(10,2),
    subProject_id INT,
    FOREIGN KEY (subProject_id) REFERENCES SubProject(subProject_id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS SubTask (
                                       subTask_id INT AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(100),
    description VARCHAR(200),
    estimated_hours INT,
    status VARCHAR(50),
    task_id INT,
    FOREIGN KEY (task_id) REFERENCES Task(task_id) ON DELETE CASCADE
    );

INSERT INTO Users (user_id, name, email, password)
VALUES (1, 'Test', 'test@mail.com', '1234');

INSERT INTO Users (user_id, name, email, password)
VALUES (2, 'Abbas', 'abbas@mail.com', 'AH123');

INSERT INTO Project (project_id, name, description, date, deadline, status, budget, user_id)
VALUES (1, 'Test Project 1', 'Test description 1', '2026-05-22', '2026-06-21','TODO', 100, 1);

INSERT INTO Project (project_id, name, description, date, deadline, status, budget, user_id)
VALUES (2, 'Test Project 2', 'Test description 2', '2026-05-23', '2026-07-21','IN_PROGRESS', 100, 1);

INSERT INTO SubProject (subProject_id, name, status, project_id)
VALUES (1, 'Test SubProject 1', 'DONE', 1);

INSERT INTO SubProject (subProject_id, name, status, project_id)
VALUES (2, 'Test SubProject 2', 'IN_PROGRESS', 1);

INSERT INTO Task (task_id, name, description, deadline, status, hourlyRate, subProject_id)
VALUES (1, 'Test Task 1', 'Task description 1', '2026-05-30','TODO', 250.00, 1);

INSERT INTO Task (task_id, name, description, deadline, status, hourlyRate, subProject_id)
VALUES (2, 'Test Task 2', 'Task description 2','2026-05-30', 'IN_PROGRESS', 300.00, 1);

INSERT INTO SubTask (subTask_id, name, description, estimated_hours, status, task_id)
VALUES (1, 'Test SubTask 1', 'SubTask description 1', 5, 'TODO', 1);

INSERT INTO SubTask (subTask_id, name, description, estimated_hours, status, task_id)
VALUES (2, 'Test SubTask 2', 'SubTask description 2', 3, 'IN_PROGRESS', 1);