CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password_hash VARCHAR(128) NOT NULL
);

CREATE TABLE groups (
    group_id SERIAL PRIMARY KEY,
    group_name VARCHAR(100) NOT NULL,
    created_by INT REFERENCES users(user_id)
);

CREATE TABLE group_members (
    group_member_id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(user_id),
    group_id INT REFERENCES groups(group_id)
);

CREATE TABLE expenses (
    expense_id SERIAL PRIMARY KEY,
    description VARCHAR(200) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payer_id INT REFERENCES users(user_id),
    group_id INT REFERENCES groups(group_id),
    date DATE NOT NULL
);

CREATE TABLE expense_shares (
    expense_share_id SERIAL PRIMARY KEY,
    expense_id INT REFERENCES expenses(expense_id),
    user_id INT REFERENCES users(user_id),
    share_amount DECIMAL(10, 2) NOT NULL
);

