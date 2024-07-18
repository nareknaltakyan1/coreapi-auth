CREATE TABLE "verification" (
    id BIGSERIAL NOT NULL,
    userid BIGINT NOT NULL,
    code BIGINT,
    created TIMESTAMP NOT NULL,
    verified BOOLEAN DEFAULT FALSE,
    PRIMARY KEY(id),
    FOREIGN KEY (userid) REFERENCES _user(id)
);

CREATE INDEX idx_verification_user_id ON "verification" (userid);
CREATE INDEX idx_verification_created ON "verification" (created);
CREATE INDEX idx_verification_code ON "verification" (code);
