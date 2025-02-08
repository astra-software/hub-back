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
    FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (id_permission) REFERENCES permission(id) ON DELETE CASCADE
);

CREATE INDEX idx_user_permission_permission ON user_permission_link (id_permission);

DO $$ 
BEGIN 
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'STRUCTUREDENOMINATOR') THEN
        CREATE TYPE STRUCTUREDENOMINATOR AS ENUM ('ARTICLE', 'PROJECT'); 
    END IF;
END $$;

DO $$ 
BEGIN 
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'EVENTLOGGINGDENOMINATOR') THEN
        CREATE TYPE EVENTLOGGINGDENOMINATOR AS ENUM ('USER', 'PROJECT', 'ARTICLE', 'CHAT', 'FEEDBACK', 'DOCUMENTATION'); 
    END IF;
END $$;
DO $$ 
BEGIN 
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'READSTATUS') THEN
        CREATE TYPE READSTATUS AS ENUM ('SENT', 'READ'); 
    END IF;
END $$;

DO $$ 
BEGIN 
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'FEEDBACKTYPE') THEN
        CREATE TYPE FEEDBACKTYPE AS ENUM ('POSITIVE', 'NEGATIVE'); 
    END IF;
END $$;

CREATE TABLE IF NOT EXISTS category (
    id BIGSERIAL PRIMARY KEY,
    structure_denominator STRUCTUREDENOMINATOR NOT NULL,
    label VARCHAR(255),
    "type" TEXT[]
);

CREATE TABLE IF NOT EXISTS feedback_label (
    id BIGSERIAL PRIMARY KEY,
    structure_denominator STRUCTUREDENOMINATOR NOT NULL,
    "type" FEEDBACKTYPE NOT NULL,
    label VARCHAR(180) NOT NULL
);

CREATE TABLE IF NOT EXISTS article (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(180) NOT NULL,
    markdown_content TEXT NOT NULL,
    favorite_counter BIGINT,
    "timestamp" DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS article_category_link (
    id_article BIGINT NOT NULL,
    id_category BIGINT NOT NULL,
    FOREIGN KEY(id_article) REFERENCES article(id) ON DELETE CASCADE,
    FOREIGN KEY(id_category) REFERENCES category(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS project (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(180) NOT NULL,
    "description" TEXT,
    favorite_counter BIGINT,
    "timestamp" DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS project_category_link (
    id_project BIGINT NOT NULL,
    id_category BIGINT NOT NULL,
    FOREIGN KEY(id_project) REFERENCES project(id) ON DELETE CASCADE,
    FOREIGN KEY(id_category) REFERENCES category(id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS user_favorite_articles (
    id_user BIGINT NOT NULL,
    id_project BIGINT NOT NULL,
    FOREIGN KEY(id_user) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY(id_project) REFERENCES project(id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS user_favorite_projects (
    id_user BIGINT NOT NULL,
    id_article BIGINT NOT NULL,
    FOREIGN KEY(id_user) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY(id_article) REFERENCES article(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS feedback (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    article_id BIGINT,
    project_id BIGINT,
    structure_denominator STRUCTUREDENOMINATOR NOT NULL,
    "type" FEEDBACKTYPE NOT NULL,
    content TEXT NOT NULL,
    "timestamp" DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (article_id) REFERENCES article(id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS feedback_label_link (
    id_feedback BIGINT NOT NULL,
    id_label BIGINT NOT NULL,
    FOREIGN KEY(id_feedback) REFERENCES feedback(id) ON DELETE CASCADE,
    FOREIGN KEY(id_label) REFERENCES feedback_label(id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS image (
    id BIGSERIAL PRIMARY KEY,
    article_id BIGINT,
    project_id BIGINT,
    "url" TEXT NOT NULL,
    FOREIGN KEY (article_id) REFERENCES article(id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS util (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL,
    title VARCHAR(180) NOT NULL,
    "url" TEXT NOT NULL,
    FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS documentation_topic (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(180) NOT NULL
);

CREATE TABLE IF NOT EXISTS documentation_page (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    topic_id BIGINT NOT NULL,
    title VARCHAR(180) NOT NULL,
    markdown_content VARCHAR(180) NOT NULL,
    "timestamp" DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (topic_id) REFERENCES documentation_topic(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS chat (
    id VARCHAR(255) PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    recipient_id BIGINT NOT NULL,
    blocked BOOLEAN NOT NULL,
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (recipient_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS message(
    id BIGSERIAL PRIMARY KEY,
    chat_id VARCHAR(255) NOT NULL,
    sender_id BIGINT NOT NULL,
    recipient_id BIGINT NOT NULL,
    content VARCHAR(240) NOT NULL,
    read_status READSTATUS NOT NULL,
    "timestamp" DATE NOT NULL,
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (recipient_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS event_logging(
    id BIGSERIAL PRIMARY KEY,
    structure_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    logging_denominator EVENTLOGGINGDENOMINATOR NOT NULL,
    description VARCHAR(255) NOT NULL,
    reason VARCHAR(255) NOT NULL,
    "timestamp" DATE NOT NULL
);