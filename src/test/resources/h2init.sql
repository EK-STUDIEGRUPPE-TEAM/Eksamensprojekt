DROP TABLE IF EXISTS Task

CREATE TABLE Task (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255),
                      description VARCHAR(255),
                      hourlyRate DECIMAL,
                      status VARCHAR(50),
                      subProject_id INT
);

-- data

INSERT INTO Task (id, name, description, hourlyRate, status, subProject_id) VALUES (1, 'Test Task', 'Test af Task Repo', 250.0, DONE, 1);
INSERT INTO Task (id, name, description, hourlyRate, status, subProject_id) VALUES (2, 'Test Task 2', 'Test af Task Repo2', 1050.0, TODO, 2);
