CREATE TABLE types (
    id serial primary key,
    name varchar(100)
);

CREATE TABLE rules (
    id serial primary key,
    name varchar(100)
);

CREATE TABLE accident (
    id serial primary key,
    name varchar(100),
    text varchar(2000),
    address varchar(100),
    type_id int references types
);

CREATE TABLE accident_rules (
    accident_id int references accident,
    rules_id int references rules
);

INSERT INTO rules (name) VALUES ('Статья. 1'), ('Статья. 2'), ('Статья. 3');
INSERT INTO types (name) VALUES ('Две машины'), ('Машина и человек'), ('Машина и велосипед');

CREATE TABLE authorities (
    id serial primary key,
    authority VARCHAR(50) NOT NULL unique
);

CREATE TABLE users (
    id serial primary key,
    username VARCHAR(50) NOT NULL unique,
    password VARCHAR(100) NOT NULL,
    enabled boolean default true,
    authority_id int not null references authorities(id)
);

insert into authorities (authority) values ('ROLE_USER');
insert into authorities (authority) values ('ROLE_ADMIN');

insert into users (username, enabled, password, authority_id)
values ('root', true, '$2a$10$wY1twJhMQjGVxv4y5dBC5ucCBlzkzT4FIGa4FNB/pS9GaXC2wm9/W',
        (select id from authorities where authority = 'ROLE_ADMIN'));