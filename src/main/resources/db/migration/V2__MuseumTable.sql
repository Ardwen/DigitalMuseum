CREATE TABLE IF NOT EXISTS museum (
    id UUID NOT NULL PRIMARY KEY,
    mu_name VARCHAR(255) NOT NULL UNIQUE,
    location VARCHAR(200),
    category

);