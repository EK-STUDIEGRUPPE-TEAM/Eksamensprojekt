DROP TABLE IF EXISTS SubTask;
DROP TABLE IF EXISTS Task;
DROP TABLE IF EXISTS SubProject;
DROP TABLE IF EXISTS Project;
DROP TABLE IF EXISTS Users;

CREATE TABLE IF NOT EXISTS Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(50)
    );

CREATE TABLE IF NOT EXISTS Project (
    project_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    description VARCHAR(200),
    date DATE,
    deadline DATE NOT NULL,
    status VARCHAR(50),
    budget DECIMAL(10, 2) DEFAULT 0.00,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS SubProject (
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