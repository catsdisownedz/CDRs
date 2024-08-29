-- Create the cdrs table
CREATE TABLE cdrs (
    id SERIAL PRIMARY KEY,
    anum VARCHAR(255),
    bnum VARCHAR(255),
    service_type VARCHAR(255),
    usage double_precision,
    start_date_time TIMESTAMP
);

-- Create the users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);
