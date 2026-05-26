DROP TABLE IF EXISTS SubTask;
DROP TABLE IF EXISTS Task;
DROP TABLE IF EXISTS SubProject;
DROP TABLE IF EXISTS Project;
DROP TABLE IF EXISTS Users;

CREATE TABLE IF NOT EXISTS Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL
    );

CREATE TABLE IF NOT EXISTS Project (
    project_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(200),
    date DATE NOT NULL,
    deadline DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    budget DECIMAL(10, 2) DEFAULT 0.00 NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS SubProject (
    subProject_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL,
    project_id INT NOT NULL,
    FOREIGN KEY (project_id) REFERENCES Project(project_id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS Task (
    task_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(200),
    deadline DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    hourlyRate DECIMAL(10,2) NOT NULL,
    subProject_id INT NOT NULL,
    FOREIGN KEY (subProject_id) REFERENCES SubProject(subProject_id) ON DELETE CASCADE
    );


CREATE TABLE IF NOT EXISTS SubTask (
    subTask_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(200),
    estimated_hours INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    task_id INT NOT NULL,
    FOREIGN KEY (task_id) REFERENCES Task(task_id) ON DELETE CASCADE
    );