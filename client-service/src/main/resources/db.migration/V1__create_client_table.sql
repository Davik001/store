CREATE TABLE client (
   id SERIAL PRIMARY KEY,
   first_name VARCHAR(255),
   last_name VARCHAR(255),
   email VARCHAR(255) UNIQUE NOT NULL CHECK (email LIKE '%@%'),
   phone VARCHAR(255) UNIQUE NOT NULL
);
