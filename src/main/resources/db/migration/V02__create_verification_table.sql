CREATE TABLE "_verification" (
    id BIGSERIAL NOT NULL,
    userid BIGINT NOT NULL,
    otp BIGINT,
    created TIMESTAMP NOT NULL,
    verified BOOLEAN DEFAULT FALSE,
    PRIMARY KEY(id),
    FOREIGN KEY (userid) REFERENCES _user(id)
);

CREATE INDEX idx_verification_user_id ON "_verification" (userid);
CREATE INDEX idx_verification_created ON "_verification" (created);
