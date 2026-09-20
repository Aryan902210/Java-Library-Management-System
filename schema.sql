-- Main application database
CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

CREATE TABLE IF NOT EXISTS books (
    isbn VARCHAR(20) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    is_available BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS users (
    user_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Test database (used by JUnit tests, kept separate from real data)
CREATE DATABASE IF NOT EXISTS library_test_db;
USE library_test_db;

CREATE TABLE IF NOT EXISTS books (
    isbn VARCHAR(20) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    is_available BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS users (
    user_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);