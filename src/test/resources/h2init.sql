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

INSERT INTO Users (user_id, name, email, password)
VALUES (1, 'Test User', 'test@mail.com', '1234');

INSERT INTO Users (user_id, name, email, password)
VALUES (2, 'Malak', 'malak@mail.com', 'abcd');