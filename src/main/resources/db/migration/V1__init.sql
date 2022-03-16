create table app_user
(
    id         bigint       not null auto_increment,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    email      varchar(255) not null,
    password   varchar(255) not null,
    role       varchar(255) not null,
    enabled    bit,
    primary key (id)
) engine=MyISAM;

create table app_user_messages
(
    app_user_id bigint not null,
    messages_id bigint not null
) engine=MyISAM;

create table confirmation_code
(
    id          bigint not null auto_increment,
    code        varchar(255),
    created_at  datetime,
    expired_at  datetime,
    app_user_id bigint,
    primary key (id)
) engine=MyISAM;

create table message
(
    id          bigint        not null auto_increment,
    file_name   varchar(255),
    tag         varchar(255),
    text        varchar(1024) not null,
    app_user_id bigint,
    primary key (id)
) engine=MyISAM;

alter table app_user_messages
    add constraint UK_il0aeh1ovhi8mnw4o6u8i5929 unique (messages_id);

alter table app_user_messages
    add constraint FK395pat5j1m2qcxdm1ho9wdc4r
        foreign key (messages_id)
            references message (id);

alter table app_user_messages
    add constraint FKsx2oh9eamr3qd73320gqqoe0d
        foreign key (app_user_id)
            references app_user (id);

alter table confirmation_code
    add constraint FKjb84qpvq1bsyxkb5p3vjg8a4q
        foreign key (app_user_id)
            references app_user (id);

alter table message
    add constraint FKc2ywiws1akffvmh7sf8dmthbc
        foreign key (app_user_id)
            references app_user (id);

insert into app_user (first_name, last_name, email, password, role, enabled)
values ('Эльдияр', 'Муса', 'musabaeveldiar@gmail.com', 
        '$2a$12$3leiaZVW2JNMfU52dCOMsu24N/6.dF8L6Nlh6GFd2LCsn5E4Utv3u', 'ROLE_ADMIN', true);