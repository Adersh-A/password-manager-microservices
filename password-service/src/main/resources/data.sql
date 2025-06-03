CREATE TABLE IF NOT EXISTS Passwords (
    id UUID PRIMARY KEY,
    user_email VARCHAR(255) NOT NULL,
    service_name VARCHAR(255) NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

INSERT INTO Passwords (id, user_email, service_name, user_name, password, created_at, updated_at) VALUES
  ('550e8400-e29b-41d4-a716-446655440000', 'adersh0000@gmail.com', 'Facebook', 'user1.fb', 'pass123', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('660e8400-e29b-41d4-a716-446655440111', 'user2@example.com', 'Twitter', 'user2.tw', 'pass456', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('770e8400-e29b-41d4-a716-446655440222', 'adersh0000@gmail.com', 'GitHub', 'user1.gh', 'pass789', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
