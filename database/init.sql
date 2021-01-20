CREATE DATABASE IF NOT EXISTS chairvise4
    DEFAULT CHARACTER SET utf8;

USE chairvise4;

DROP TABLE IF EXISTS presentation;
DROP TABLE IF EXISTS conference;
DROP TABLE IF EXISTS person;

CREATE TABLE person
(
    id    INT(16) PRIMARY KEY AUTO_INCREMENT,
    name  VARCHAR(128),
    email VARCHAR(128) UNIQUE NOT NULL
);

CREATE UNIQUE INDEX email_idx ON person (email);

CREATE TABLE conference
(
    id          INT(16) PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(128),
    year        INT(4),
    description VARCHAR(1024),
    chair_id    INT,
    records     LONGTEXT,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (chair_id) REFERENCES person (id)
);

CREATE UNIQUE INDEX name_year_chair_idx ON conference (name, year, chair_id);

CREATE TABLE presentation
(
    id            INT(16) PRIMARY KEY AUTO_INCREMENT,
    name          VARCHAR(128),
    description   VARCHAR(1024),
    conference_id INT REFERENCES conference (id),
    is_public     BOOLEAN DEFAULT false,
    chair_id      INT,
    chart_list    LONGTEXT,
    chart_raw     LONGTEXT,
    created_at    DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (chair_id) REFERENCES person (id)
);