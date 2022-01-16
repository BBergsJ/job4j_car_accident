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