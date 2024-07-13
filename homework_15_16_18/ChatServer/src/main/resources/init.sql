-- Проверка существования и удаление базы данных ChatServer, если она существует
DROP DATABASE IF EXISTS ChatServer;

-- Создание базы данных
CREATE DATABASE ChatServer;

-- Создание таблицы roles
CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       role_name VARCHAR(50) UNIQUE NOT NULL
);

-- Вставка начальных данных в таблицу roles
INSERT INTO roles (role_name) VALUES
                                  ('ADMIN'),
                                  ('USER');

-- Создание таблицы users
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       login VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       username VARCHAR(50) UNIQUE NOT NULL
);

-- Создание промежуточной таблицы user_roles для связывания пользователей и ролей
CREATE TABLE user_roles (
                            user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
                            role_id INTEGER REFERENCES roles(id) ON DELETE CASCADE,
                            PRIMARY KEY (user_id, role_id)
);

-- Вставка начальных данных в таблицу users
INSERT INTO users (login, password, username) VALUES
                                                  ('admin', 'admin', 'admin'),
                                                  ('login1', 'pass1', 'bob'),
                                                  ('login2', 'pass2', 'user2'),
                                                  ('login3', 'pass3', 'user3');

-- Вставка данных в таблицу user_roles для связывания пользователей и ролей
-- Администратор
INSERT INTO user_roles (user_id, role_id) VALUES
    ((SELECT id FROM users WHERE login = 'admin'), (SELECT id FROM roles WHERE role_name = 'ADMIN'));

-- Обычные пользователи
INSERT INTO user_roles (user_id, role_id) VALUES
                                              ((SELECT id FROM users WHERE login = 'login1'), (SELECT id FROM roles WHERE role_name = 'USER')),
                                              ((SELECT id FROM users WHERE login = 'login2'), (SELECT id FROM roles WHERE role_name = 'USER')),
                                              ((SELECT id FROM users WHERE login = 'login3'), (SELECT id FROM roles WHERE role_name = 'USER'));
