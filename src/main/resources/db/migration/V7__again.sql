drop table if exists app_user;

drop table if exists confirmation_code;

drop table if exists message;

drop table if exists message_like_user;

drop table if exists subscriber_subscription;

create table app_user
(
    id                  bigint not null auto_increment,
    email               varchar(255),
    enabled             bit,
    first_name          varchar(30),
    last_name           varchar(30),
    password            varchar(255),
    reset_password_code varchar(255),
    role                varchar(255),
    primary key (id)
) engine = MyISAM;

create table confirmation_code
(
    id          bigint not null auto_increment,
    code        varchar(255),
    created_at  datetime,
    expired_at  datetime,
    app_user_id bigint,
    primary key (id)
) engine = MyISAM;

create table message
(
    id          bigint not null auto_increment,
    file_name   varchar(255),
    tag         varchar(255),
    text        varchar(1024),
    app_user_id bigint,
    primary key (id)
) engine = MyISAM;

create table message_like_user
(
    message_id bigint not null,
    user_id    bigint not null
) engine = MyISAM;

create table subscriber_subscription
(
    subscriber_id   bigint not null,
    subscription_id bigint not null
) engine = MyISAM;

alter table confirmation_code
    add constraint FKjb84qpvq1bsyxkb5p3vjg8a4q
        foreign key (app_user_id)
            references app_user (id);

alter table message
    add constraint FKc2ywiws1akffvmh7sf8dmthbc
        foreign key (app_user_id)
            references app_user (id);

alter table message_like_user
    add constraint FKflk7797ohujurc1hfu9qu6476
        foreign key (user_id)
            references app_user (id);

alter table message_like_user
    add constraint FKhc0tp3h7fx1sgt1tfnr27m9ku
        foreign key (message_id)
            references message (id);

alter table subscriber_subscription
    add constraint FK6oc85p8dbmfh7374pl71fm3d9
        foreign key (subscription_id)
            references app_user (id);

alter table subscriber_subscription
    add constraint FKqjhvkw8byfqv4343xg7difbag
        foreign key (subscriber_id)
            references app_user (id);