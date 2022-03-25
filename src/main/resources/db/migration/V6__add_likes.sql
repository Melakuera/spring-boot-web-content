alter table app_user add column like_user_id bigint;

alter table app_user
    add constraint FKg7s06n2setijj6reqy67ey50w
        foreign key (like_user_id)
            references message (id);