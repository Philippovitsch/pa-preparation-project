INSERT INTO tag (id, name) VALUES
    (default, 'High priority'),
    (default, 'Medium priority'),
    (default, 'Low priority'),
    (default, 'Personal'),
    (default, 'Work');

INSERT INTO task (id, username, name, description, timestamp) VALUES
    (default, 'user', 'Shopping', 'Buy some new Jeans', TIMESTAMP '2023-04-16 02:00:00'),
    (default, 'admin', 'Clean up', 'Clean up apartment', TIMESTAMP '2023-04-17 02:00:00'),
    (default, 'admin', 'Coding', 'Code a pet project',   TIMESTAMP '2023-04-18 02:00:00');

INSERT INTO task_tag (task_id, tag_id) VALUES
    (1, 3),
    (1, 4),
    (2, 2),
    (3, 1),
    (3, 5);
