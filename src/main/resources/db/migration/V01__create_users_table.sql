CREATE TABLE "_user" (
    id BIGSERIAL NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created TIMESTAMP NOT NULL,
    updated TIMESTAMP NOT NULL,
    deleted TIMESTAMP,
    status VARCHAR(255) DEFAULT 'CREATED',
    PRIMARY KEY(id),
    CONSTRAINT uq_email UNIQUE (email)
);

CREATE INDEX idx_user_email ON "_user" (email);
CREATE INDEX idx_user_created ON "_user" (created);
