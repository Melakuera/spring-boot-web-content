insert into app_user (first_name, last_name, email, password, role, reset_password_code, enabled)
values ('Альдебаран', 'Вим', 'alvim@mail.com', '$2a$12$axZRjpULGJv8rzQJRKRnOeCnbTV0TdTsMLk0tXfAj0EkkzQdz9Ed2',
        'ROLE_USER', null, true);
insert into app_user (first_name, last_name, email, password, role, reset_password_code, enabled)
values ('Бетельгейзе', 'Уондер', 'lenbou@mail.com', '$2a$12$axZRjpULGJv8rzQJRKRnOeCnbTV0TdTsMLk0tXfAj0EkkzQdz9Ed2',
        'ROLE_USER', null, true);
insert into app_user (first_name, last_name, email, password, role, reset_password_code, enabled)
values ('Андерсон', 'Хоз', 'hozmin@mail.com', '$2a$12$axZRjpULGJv8rzQJRKRnOeCnbTV0TdTsMLk0tXfAj0EkkzQdz9Ed2',
        'ROLE_USER', null, true);
insert into app_user (first_name, last_name, email, password, role, reset_password_code, enabled)
values ('Сэм', 'Ньюэлл', 'newel1l@mail.com', '$2a$12$axZRjpULGJv8rzQJRKRnOeCnbTV0TdTsMLk0tXfAj0EkkzQdz9Ed2',
        'ROLE_USER', null, true);
insert into message (file_name, tag, text, app_user_id)
values (null, 'важное', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and moorem Ipsum.',
        3);
insert into message (file_name, tag, text, app_user_id)
values (null, 'важное', 'It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The pobution of lettng it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for ''lorem ipsum'' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).',
        4);
insert into message (file_name, tag, text, app_user_id)
values (null, 'прочее', 'It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The pobution of lettng it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for ''lorem ipsum'' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).',
        5);
insert into message (file_name, tag, text, app_user_id)
values ('bdaa931c-a366-4a0f-9670-3fcc2c31d31e-project-messager-01.png',
        'тест', 'kages and web page editors now use Lorem Ipsum as their default model text, andll uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).',
        6);
insert into subscriber_subscription values (2,5);
insert into subscriber_subscription values (3,4);
insert into subscriber_subscription values (1,6);

