CREATE TABLE BOARD (
    id bigint NOT NULL AUTO_INCREMENT,
    name varchar(255) DEFAULT NULL,
    description varchar(255) DEFAULT NULL,
    is_active bit(1) DEFAULT NULL,
    created_at datetime(6) DEFAULT NULL,
    created_by varchar(255) DEFAULT NULL,
    updated_at datetime(6) DEFAULT NULL,
    updated_by varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE ROLE (
    id bigint NOT NULL AUTO_INCREMENT,
    name varchar(255) DEFAULT NULL,
    description varchar(255) DEFAULT NULL,
    is_active bit(1) DEFAULT NULL,
    created_at datetime(6) DEFAULT NULL,
    created_by varchar(255) DEFAULT NULL,
    updated_at datetime(6) DEFAULT NULL,
    updated_by varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE TASK (
    id bigint NOT NULL AUTO_INCREMENT,
    name varchar(255) DEFAULT NULL,
    description varchar(255) DEFAULT NULL,
    status varchar(255) DEFAULT NULL,
    owner varchar(255) DEFAULT NULL,
    assignee varchar(255) DEFAULT NULL,
    board_id bigint DEFAULT NULL,
    due_date date DEFAULT NULL,
    is_active bit(1) DEFAULT NULL,
    created_at datetime(6) DEFAULT NULL,
    created_by varchar(255) DEFAULT NULL,
    updated_at datetime(6) DEFAULT NULL,
    updated_by varchar(255) DEFAULT NULL,
    PRIMARY KEY (id),
    KEY FKrar3pm9ixqub1nilws0l8l22w (board_id),
    CONSTRAINT FKrar3pm9ixqub1nilws0l8l22w FOREIGN KEY (board_id) REFERENCES board (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE USER (
    id bigint NOT NULL AUTO_INCREMENT,
    username varchar(255) DEFAULT NULL,
    first_name varchar(255) DEFAULT NULL,
    last_name varchar(255) DEFAULT NULL,
    email varchar(255) DEFAULT NULL,
    password varchar(255) DEFAULT NULL,
    is_super_admin bit(1) DEFAULT NULL,
    is_active bit(1) DEFAULT NULL,
    created_at datetime(6) DEFAULT NULL,
    created_by varchar(255) DEFAULT NULL,
    updated_at datetime(6) DEFAULT NULL,
    updated_by varchar(255) DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY UKsb8bbouer5wak8vyiiy4pf2bx (username)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE USER_BOARD_ROLE (
    id bigint NOT NULL AUTO_INCREMENT,
    board_id bigint DEFAULT NULL,
    user_id bigint DEFAULT NULL,
    role_id bigint DEFAULT NULL,
    is_active bit(1) DEFAULT NULL,
    created_at datetime(6) DEFAULT NULL,
    created_by varchar(255) DEFAULT NULL,
    updated_at datetime(6) DEFAULT NULL,
    updated_by varchar(255) DEFAULT NULL,
    PRIMARY KEY (id),
    KEY FKoyy8uck0w1dfny87fm6yc4pa5 (board_id),
    KEY FK24549fvfkbj6olhnddp7dvf29 (role_id),
    KEY FKhq5wcua4se9axv52dqipeo3wh (user_id),
    CONSTRAINT FK24549fvfkbj6olhnddp7dvf29 FOREIGN KEY (role_id) REFERENCES role (id),
    CONSTRAINT FKhq5wcua4se9axv52dqipeo3wh FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT FKoyy8uck0w1dfny87fm6yc4pa5 FOREIGN KEY (board_id) REFERENCES board (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
