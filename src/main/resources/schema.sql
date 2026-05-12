CREATE TABLE IF NOT EXISTS Users (
                        user_id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100),
                        email VARCHAR(100) UNIQUE,
                        password VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS Task (
                        task_id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100),
                        description VARCHAR(200),
                        status VARCHAR(50),
                        hourlyRate DECIMAL(10,2),
                        FOREIGN KEY (subProject_id) REFERENCES SubProject(subProject_id)
);

CREATE TABLE IF NOT EXISTS SubTask (
                           subTask_id INT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(100),
                           estimated_hours INT,
                           status VARCHAR(50),
                           FOREIGN KEY task_id REFERENCES Task(task_id)
);

CREATE TABLE IF NOT EXISTS SubProject (
                              subTask_id INT AUTO_INCREMENT PRIMARY KEY,
                              name VARCHAR(100),
                              status VARCHAR(50),
                              FOREIGN KEY project_id REFERENCES Project(project_id)
);

CREATE TABLE IF NOT EXISTS Project (
                           project_id INT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(100),
                           description VARCHAR(200),
                           date DATE,
                           status VARCHAR(50),
                           FOREIGN KEY user_id REFERENCES Users(user_id)
);

