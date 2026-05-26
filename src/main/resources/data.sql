-- Users
INSERT INTO Users (name, email, password)
VALUES ('Oliver', 'Oliver@gmail.com', 'test'),
       ('Bob Smith', 'bob@example.com', 'bob123'),
       ('Charlie Brown', 'charlie@example.com', 'charlie123');

-- Projects
INSERT INTO Project (name, description, date, deadline, status, budget, user_id)
VALUES ('Website Redesign', 'Redesign company website for better UX', '2026-05-01','2026-05-29', 'IN_PROGRESS', 10000, 1),
       ('Mobile App', 'Develop a task tracking mobile app', '2026-05-03','2026-05-30', 'TODO', 3000, 2),
       ('School Platform', 'Build an online learning platform', '2026-05-05', '2026-06-23','DONE', 99999, 3);

-- SubProjects
INSERT INTO SubProject (name, project_id)
VALUES ('Frontend Module', 1),
       ('Backend Module', 1),
       ('Authentication System', 2),
       ('Dashboard Features', 3);

-- Tasks
INSERT INTO Task (name, description, deadline, status, hourlyRate, subProject_id)
VALUES ('Create Landing Page', 'Design and code landing page UI','2026-05-29', 'IN_PROGRESS', 45.00, 1),
       ('Create Login Page', 'Design and code login page UI', '2026-05-29','DONE', 100.00, 1),
       ('Build API', 'Develop REST API for website','2026-05-29', 'IN_PROGRESS', 60.00, 2),
       ('Login Feature', 'Implement login and registration','2026-05-29', 'TODO', 50.00, 3),
       ('Student Dashboard', 'Create dashboard for students', '2026-05-29','DONE', 55.00, 4);

-- SubTasks
INSERT INTO SubTask (name, estimated_hours, status, task_id)
VALUES ('Design wireframe', 5, 'DONE', 1),
       ('Write HTML/CSS', 8, 'IN_PROGRESS', 1),
       ('Write HTML/CSS', 12, 'DONE', 1),
       ('Set up database routes', 6, 'IN_PROGRESS', 2),
       ('Create JWT auth', 4, 'TODO', 3),
       ('Add dashboard charts', 7, 'DONE', 4);