INSERT INTO users (id, username, password) VALUES
    (default, 'user', '$2a$12$s.zqhXspLQkmj7wFjCDNfeGTnykviA5a9ZVZWysi4ydFcCRJbmLxi'),
    (default, 'admin', '$2a$12$JdObYrGEN4C2t2YnntNKVe.ZhvWtJoO7hNLUDpiCypJti3Qe/BJq.');

INSERT INTO user_authorities (user_id, authorities) VALUES
    (1, 'USER'),
    (2, 'USER'),
    (2, 'ADMIN');

INSERT INTO tag (id, name) VALUES
    (default, 'High priority'),
    (default, 'Medium priority'),
    (default, 'Low priority'),
    (default, 'Personal'),
    (default, 'Work');

INSERT INTO task (id, name, description, timestamp, is_done) VALUES
    (default, 'Shopping', 'Buy some new Jeans', TIMESTAMP '2023-04-16 02:00:00', TRUE),
    (default, 'Clean up', 'Clean up apartment', TIMESTAMP '2023-04-17 02:00:00', FALSE),
    (default, 'Coding', 'Code a pet project',   TIMESTAMP '2023-04-18 02:00:00', TRUE);

INSERT INTO task_tag (task_id, tag_id) VALUES
    (1, 3),
    (1, 4),
    (2, 2),
    (3, 1),
    (3, 5);

INSERT INTO task_user (task_id, user_id) VALUES
     (1, 1),
     (2, 2),
     (3, 2);
