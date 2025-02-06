CREATE TABLE IF NOT EXISTS permission (
    id BIGSERIAL PRIMARY KEY,
    "description" VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    profile_picture TEXT,
    user_name VARCHAR(32) NOT NULL UNIQUE,
    email VARCHAR(180) NOT NULL UNIQUE,
    "password" VARCHAR(64) NOT NULL,
    "enabled" BOOLEAN DEFAULT NULL,
    banned BOOLEAN DEFAULT NULL,
    account_non_expired BOOLEAN DEFAULT NULL,
    account_non_locked BOOLEAN DEFAULT NULL,
    credentials_non_expired BOOLEAN DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS user_permission_link (
    id_user BIGINT NOT NULL,
    id_permission BIGINT NOT NULL,
    PRIMARY KEY (id_user, id_permission),
    CONSTRAINT fk_user_permission FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_permission_permission FOREIGN KEY (id_permission) REFERENCES permission(id) ON DELETE CASCADE
);

CREATE INDEX idx_user_permission_permission ON user_permission_link (id_permission);
