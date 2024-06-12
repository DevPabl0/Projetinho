DROP DATABASE BikesDB;

CREATE DATABASE BikesDB;

USE BikesDB;

CREATE TABLE Bikes(
    bike_id int not null auto_increment primary key,
    nome varchar(30) not null,
    modelo varchar(50) not null,
    valor decimal(5, 2) not null
);

CREATE TABLE Users(
    user_id int not null auto_increment primary key,
    nome varchar(15) not null,
    email varchar(30) not null,
    senha VARCHAR(255) not null
);

INSERT INTO Users(nome, email, senha) VALUES ("rpa", "rpa@gmail.com", "123");
