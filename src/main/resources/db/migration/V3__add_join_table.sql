create table subscriber_subscription
(
    subscriber_id   bigint not null,
    subscription_id bigint not null,
    primary key (subscription_id, subscriber_id)
) engine = MyISAM;

alter table subscriber_subscription
    add constraint FK6oc85p8dbmfh7374pl71fm3d9
        foreign key (subscription_id)
            references app_user (id);

alter table subscriber_subscription
    add constraint FKqjhvkw8byfqv4343xg7difbag
        foreign key (subscriber_id)
            references app_user (id);