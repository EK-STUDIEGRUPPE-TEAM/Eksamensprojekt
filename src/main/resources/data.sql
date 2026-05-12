-- Users
INSERT INTO User (name, email, password)
VALUES ('Alice Johnson', 'alice@example.com', 'alice123'),
       ('Bob Smith', 'bob@example.com', 'bob123'),
       ('Charlie Brown', 'charlie@example.com', 'charlie123');

-- Projects
INSERT INTO Project (name, description, date, status, user_id)
VALUES ('Website Redesign', 'Redesign company website for better UX', '2026-05-01', 'In Progress', 1),
       ('Mobile App', 'Develop a task tracking mobile app', '2026-05-03', 'Todo', 2),
       ('School Platform', 'Build an online learning platform', '2026-05-05', 'Done', 3);

-- SubProjects
INSERT INTO SubProject (name, status, project_id)
VALUES ('Frontend Module', 'In Progress', 1),
       ('Backend Module', 'Pending', 1),
       ('Authentication System', 'Planning', 2),
       ('Dashboard Features', 'Completed', 3);

-- Tasks
INSERT INTO Task (name, description, status, hourlyRate, subProject_id)
VALUES ('Create Landing Page', 'Design and code landing page UI', 'In Progress', 45.00, 1),
       ('Build API', 'Develop REST API for website', 'In Progress', 60.00, 2),
       ('Login Feature', 'Implement login and registration', 'Todo', 50.00, 3),
       ('Student Dashboard', 'Create dashboard for students', 'done', 55.00, 4);

-- SubTasks
INSERT INTO SubTask (name, estimated_hours, status, task_id)
VALUES ('Design wireframe', 5, 'Done', 1),
       ('Write HTML/CSS', 8, 'In Progress', 1),
       ('Set up database routes', 6, 'In Progress', 2),
       ('Create JWT auth', 4, 'Todo', 3),
       ('Add dashboard charts', 7, 'Done', 4);