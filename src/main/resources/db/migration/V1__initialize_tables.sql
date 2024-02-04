CREATE TABLE IF NOT EXISTS users
(
    id                                          SERIAL NOT NULL PRIMARY KEY,
    first_name                                  VARCHAR(75) NOT NULL,
    last_name                                   VARCHAR(75) NOT NULL,
    email                                       VARCHAR(150) NOT NULL UNIQUE,
    password                                    VARCHAR(75) NOT NULL,
    active                                      BOOLEAN,
    user_role                                   VARCHAR(30) NOT NULL,
    created_by                                  VARCHAR(150) NOT NULL,
    updated_by                                  VARCHAR(150) NOT NULL,
    created_at                                  TIMESTAMP WITHOUT TIME ZONE,
    updated_at                                  TIMESTAMP WITHOUT TIME ZONE
);


CREATE TABLE IF NOT EXISTS books
(
    id                                          SERIAL NOT NULL PRIMARY KEY,
    title                                       VARCHAR(75) NOT NULL,
    author                                      VARCHAR(75) NOT NULL,
    publisher                                   VARCHAR(75) NOT NULL,
    isbn                                        VARCHAR(75) NOT NULL UNIQUE,
    genre                                       VARCHAR(75) NOT NULL UNIQUE,
    status                                      VARCHAR(30) NOT NULL,
    price                                       DECIMAL NOT NULL,
    created_by                                  VARCHAR(150) NOT NULL,
    updated_by                                  VARCHAR(150) NOT NULL,
    created_at                                  TIMESTAMP WITHOUT TIME ZONE,
    updated_at                                  TIMESTAMP WITHOUT TIME ZONE
);