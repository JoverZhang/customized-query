USE example;

CREATE TABLE demo_user (
    id           BIGINT UNSIGNED PRIMARY KEY,
    name         CHAR(255)      NOT NULL,
    balance      DECIMAL(12, 2) NOT NULL,
    birthday     DATE           NOT NULL,
    deleted      BOOLEAN        NOT NULL DEFAULT FALSE,
    gmt_create   DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    gmt_modified DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE INDEX uk_name (name),
    INDEX idx_birthday (birthday)
) DEFAULT CHARSET utf8mb4;

INSERT INTO demo_user(id, name, balance, birthday)
VALUES (1, 'little_a', 10, '1970-01-01'),
       (2, 'little_c', 20, '1970-01-02'),
       (3, 'little_b', 10, '1970-01-03'),
       (4, 'little_d', 20, '1970-01-04');
