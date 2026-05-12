CREATE TABLE IF NOT EXISTS User (
                        user_id AUTO_INCREMENT,
                        name VARCHAR(100),
                        email VARCHAR(100) UNIQUE,
                        password VARCHAR(50),
                        PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS Task (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100),
                        description VARCHAR(200),
                        status VARCHAR(),
                        hourlyRate decimal,
                        subProject_id int,
);

CREATE TABLE IF NOT EXISTS SubTask (
                           subTask_id AUTO_INCREMENT,
                           name VARCHAR(100),
                           estimated_hours int,
                           status VARCHAR(),
                           task_id int,
                           PRIMARY KEY (subTask_id)
);

CREATE TABLE IF NOT EXISTS SubProject (
                              subProject_id AUTO_INCREMENT,
                              name VARCHAR(100),
                              status VARCHAR(),
                              project_id int,
                              PRIMARY KEY (subProject_id)
);

CREATE TABLE IF NOT EXISTS Project (
                           project_id AUTO_INCREMENT,
                           name VARCHAR(100),
                           description VARCHAR(200),
                           date LocalDate,
                           status VARCHAR(),
                           user_id int,
                           PRIMARY KEY (project_id)
);

