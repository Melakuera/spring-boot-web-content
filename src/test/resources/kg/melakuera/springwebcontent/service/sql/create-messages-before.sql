DELETE FROM message;

INSERT INTO message (id, file_name, tag, text, app_user_id)
VALUES (1, null, 'test', 'важное', 1),
       (2, null, 'test', 'прочее', 2),
       (3, null, 'важное', 'важное', 2),
       (4, null, 'важное', 'прочее', 1);

ALTER TABLE message AUTO_INCREMENT = 5;