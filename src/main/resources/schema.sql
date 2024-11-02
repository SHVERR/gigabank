CREATE TABLE IF NOT EXISTS app_user
(
    user_id     BIGINT generated always as identity,
    first_name  VARCHAR(30) NOT NULL,
    middle_name VARCHAR(30) NOT NULL,
    last_name   VARCHAR(30) NOT NULL,
    birthdate   DATE        NOT NULL
);

CREATE TABLE IF NOT EXISTS app_bank_account
(
    bank_account_id BIGINT generated always as identity,
    balance         DECIMAL(15, 2) NOT NULL,
    owner_id        BIGINT REFERENCES app_user (user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS transaction_type
(
    type_id   BIGINT generated always as identity,
    type_name VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS transaction_category
(
    category_id   BIGINT generated always as identity,
    category_name VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS app_transaction
(
    transaction_id  BIGINT generated always as identity,
    value           DECIMAL(15, 2),
    type_id         BIGINT REFERENCES transaction_type (type_id) ON DELETE CASCADE,
    category_id     BIGINT REFERENCES transaction_category (category_id) ON DELETE CASCADE,
    created_date    TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    bank_account_id BIGINT REFERENCES app_bank_account (bank_account_id) ON DELETE CASCADE
);