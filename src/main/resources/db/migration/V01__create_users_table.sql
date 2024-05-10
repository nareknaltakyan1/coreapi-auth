CREATE TABLE "_user" (
    id BIGSERIAL,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created TIMESTAMP NOT NULL,
    deleted TIMESTAMP NOT NULL,
    updated TIMESTAMP NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT nn_email_unique UNIQUE (email)
);

CREATE INDEX idx_user_email ON "_user" (email);
CREATE INDEX idx_user_created ON "_user" (created);
