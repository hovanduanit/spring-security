create table roles
(
    id   bigint auto_increment
        primary key,
    name varchar(255) not null
);

create table users
(
    id                  bigint                  not null
        primary key,
    username            varchar(100) default '' null,
    phone_number        varchar(10)             not null,
    address             varchar(200) default '' null,
    password            varchar(200)            not null,
    created_at          datetime                null,
    updated_at          datetime                null,
    is_active           tinyint(1)   default 1  null,
    date_of_birth       datetime(6)             null,
    facebook_account_id int          default 0  null,
    google_account_id   int          default 0  null,
    role_id             bigint                  null,
    constraint FK_Role
        foreign key (role_id) references shopapp.roles (id)
);

