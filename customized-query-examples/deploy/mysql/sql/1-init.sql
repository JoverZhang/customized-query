CREATE DATABASE IF NOT EXISTS example;
GRANT SELECT, INSERT, DELETE, UPDATE ON example.* TO 'root'@'%';
FLUSH PRIVILEGES;