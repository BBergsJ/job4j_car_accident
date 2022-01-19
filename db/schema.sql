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

CREATE TABLE users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled boolean default true,
    PRIMARY KEY (username)
);

CREATE TABLE authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES users(username)
);