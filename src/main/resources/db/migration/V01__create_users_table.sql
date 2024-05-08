CREATE TABLE "_user" (
    id BIGSERIAL,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(20),
    created TIMESTAMP,
    deleted TIMESTAMP,
    updated TIMESTAMP,
    PRIMARY KEY(id),
    CONSTRAINT nn_email_not_null CHECK (email IS NOT NULL),
    CONSTRAINT nn_firstname_not_null CHECK (firstname IS NOT NULL),
    CONSTRAINT nn_lastname_not_null CHECK (lastname IS NOT NULL),
    CONSTRAINT nn_password_not_null CHECK (password IS NOT NULL),
    CONSTRAINT nn_role_not_null CHECK (role IS NOT NULL),
    CONSTRAINT nn_created_not_null CHECK (created IS NOT NULL),
    CONSTRAINT nn_updated_not_null CHECK (updated IS NOT NULL),
    CONSTRAINT nn_email_unique UNIQUE (email)
);

CREATE INDEX idx_user_email ON "_user" (email);
CREATE INDEX idx_user_created ON "_user" (created);
