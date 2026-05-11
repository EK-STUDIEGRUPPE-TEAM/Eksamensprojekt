CREATE TABLE IF NOT EXISTS User (
                        user_id AUTO_INCREMENT,
                        name VARCHAR(),
                        email VARCHAR() UNIQUE,
                        password VARCHAR(),
                        PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS Task (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(),
                        description VARCHAR(),
                        status VARCHAR(),
                        hourlyRate decimal,
                        subProject_id int,
);

CREATE TABLE IF NOT EXISTS SubTask (
                           subTask_id AUTO_INCREMENT,
                           name VARCHAR(),
                           estimated_hours int,
                           status VARCHAR(),
                           task_id int,
                           PRIMARY KEY (subTask_id)
);

CREATE TABLE IF NOT EXISTS SubProject (
                              subProject_id AUTO_INCREMENT,
                              name VARCHAR(),
                              status VARCHAR(),
                              project_id int,
                              PRIMARY KEY (subProject_id)
);

CREATE TABLE IF NOT EXISTS Project (
                           project_id AUTO_INCREMENT,
                           name VARCHAR(),
                           description VARCHAR(),
                           date LocalDate,
                           status VARCHAR(),
                           user_id int,
                           PRIMARY KEY (project_id)
);

