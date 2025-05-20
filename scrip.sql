-- Database: java-db

-- DROP DATABASE IF EXISTS "java-db";

CREATE DATABASE "java-db"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'es-EC'
    LC_CTYPE = 'es-EC'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

CREATE TABLE persons (
id SERIAL PRIMARY KEY,
identification VARCHAR(20) NOT NULL UNIQUE,
name VARCHAR(100) NOT NULL,
gender VARCHAR(10) NOT NULL,
age INTEGER NOT NULL,
address VARCHAR(200) NOT NULL,
phone VARCHAR(15) NOT NULL
);

CREATE TABLE clients (
id INTEGER PRIMARY KEY REFERENCES persons(id) ON DELETE CASCADE,
client_id VARCHAR(20) NOT NULL UNIQUE,
password VARCHAR(50) NOT NULL,
status BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE accounts (
id SERIAL PRIMARY KEY,
account_number VARCHAR(20) NOT NULL UNIQUE,
type VARCHAR(20) NOT NULL CHECK (type IN ('Savings', 'Checking', 'Current')),
initial_balance DECIMAL(15, 2) NOT NULL,
status BOOLEAN NOT NULL DEFAULT TRUE,
client_id INTEGER NOT NULL REFERENCES clients(id) ON DELETE CASCADE
);

CREATE TABLE transactions (
id SERIAL PRIMARY KEY,
date DATE NOT NULL,
transaction_type VARCHAR(20) NOT NULL CHECK (transaction_type IN ('Deposit', 'Withdrawal')),
amount DECIMAL(15, 2) NOT NULL,
balance DECIMAL(15, 2) NOT NULL,
account_id INTEGER NOT NULL REFERENCES accounts(id) ON DELETE CASCADE
);

CREATE INDEX idx_persons_identification ON persons(identification);
CREATE INDEX idx_clients_client_id ON clients(client_id);
CREATE INDEX idx_accounts_account_number ON accounts(account_number);
CREATE INDEX idx_accounts_client_id ON accounts(client_id);
CREATE INDEX idx_transactions_account_id ON transactions(account_id);
CREATE INDEX idx_transactions_date ON transactions(date);