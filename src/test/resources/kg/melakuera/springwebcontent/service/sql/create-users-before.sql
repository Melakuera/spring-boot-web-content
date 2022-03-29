DELETE FROM app_user;

INSERT INTO app_user (id, email, enabled, first_name, last_name, password, reset_password_code, role)
VALUES (1, 'musabaeveldiar@gmail.com', true, 'eld', 'mus', '$2a$12$DtUqiPCeHZdBCmETSLBJw.YU5BzJo4tv2unIWEQbyC8r6RwgdFPFa',
        null, 'ROLE_ADMIN'),
       (2, 'tom@mail.com', true, 'tom', 'van', '$2a$12$DtUqiPCeHZdBCmETSLBJw.YU5BzJo4tv2unIWEQbyC8r6RwgdFPFa',
        null, 'ROLE_USER');