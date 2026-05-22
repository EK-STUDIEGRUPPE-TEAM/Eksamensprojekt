DROP TABLE IF EXISTS SubTask;
DROP TABLE IF EXISTS Task;
DROP TABLE IF EXISTS SubProject;
DROP TABLE IF EXISTS Project;
DROP TABLE IF EXISTS Users;

CREATE TABLE Users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(100),
                       email VARCHAR(100) UNIQUE,
                       password VARCHAR(50)
);

CREATE TABLE Project (
                         project_id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(100),
                         description VARCHAR(200),
                         date DATE,
                         status VARCHAR(50),
                         user_id INT,
                         FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

INSERT INTO Users (user_id, name, email, password)
VALUES (1, 'Test', 'test@mail.com', '1234');

INSERT INTO Users (user_id, name, email, password)
VALUES (2, 'Abbas', 'abbas@mail.com', 'AH123');

INSERT INTO Project (project_id, name, description, date, status, user_id)
VALUES (1, 'Test Project 1', 'Test description 1', '2026-05-22', 'TODO', 1);

INSERT INTO Project (project_id, name, description, date, status, user_id)
VALUES (2, 'Test Project 2', 'Test description 2', '2026-05-23', 'IN_PROGRESS', 1);