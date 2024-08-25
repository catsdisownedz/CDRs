CREATE TABLE cdr (
    id SERIAL PRIMARY KEY,
    anum VARCHAR(255),
    bnum VARCHAR(255),
    service_type VARCHAR(255),
    usage DOUBLE PRECISION,
    start_date_time TIMESTAMP
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);
