CREATE TABLE employee (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(255) UNIQUE NOT NULL CHECK (email LIKE '%@%'),
    password VARCHAR(255),
    role VARCHAR(50)
);
