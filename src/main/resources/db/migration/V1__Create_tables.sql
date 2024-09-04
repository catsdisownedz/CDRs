-- Create the cdrs table
CREATE TABLE cdrs (
    id SERIAL PRIMARY KEY,
    anum VARCHAR(255) NOT NULL,
    bnum VARCHAR(255),
    serviceType VARCHAR(255)NOT NULL,
    usage double precision NOT NULL,
    startDateTime VARCHAR(255) NOT NULL
);

-- Create the users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY ,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);
