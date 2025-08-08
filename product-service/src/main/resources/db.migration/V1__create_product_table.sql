CREATE TABLE product(
     id SERIAL PRIMARY KEY,
     name VARCHAR(100),
     description TEXT,
     price NUMERIC(19,2) NOT NULL
);