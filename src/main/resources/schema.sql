--
-- would need to put in some sort of unique constraints on person names and client names, etc.
--
CREATE TABLE person (
    person_id      INTEGER IDENTITY,
    first_name     VARCHAR(50) NOT NULL,
    last_name      VARCHAR(50) NOT NULL,
    email_address  VARCHAR(50) NOT NULL,
    street_address VARCHAR(50) NOT NULL,
    city           VARCHAR(50) NOT NULL,
    state          VARCHAR(2)  NOT NULL,
    zip_code       VARCHAR(5)  NOT NULL
);

CREATE TABLE client (
    client_id      INTEGER IDENTITY,
    name           VARCHAR(50) NOT NULL,
    website_uri    VARCHAR(50) NOT NULL,
    phone_number   VARCHAR(10) NOT NULL,
    street_address VARCHAR(50) NOT NULL,
    city           VARCHAR(50) NOT NULL,
    state          VARCHAR(2)  NOT NULL,
    zip_code       VARCHAR(5)  NOT NULL
);

CREATE TABLE contact (
    contact_id INTEGER IDENTITY,
    person_id  INTEGER NOT NULL,
    client_id  INTEGER NOT NULL
);

-- When editing a person, the user should be able to choose the associated client.
-- Sounds like 1 client for a person so the C_PERSON_TO_CLIENT enforces that. Do not catch the constraint error though!
ALTER TABLE contact ADD CONSTRAINT C_PERSON_TO_CLIENT UNIQUE (person_id);
ALTER TABLE contact ADD CONSTRAINT FK_PERSON_ID FOREIGN KEY (person_id) REFERENCES person (person_id) ON DELETE CASCADE;
ALTER TABLE contact ADD CONSTRAINT FK_CLIENT_ID FOREIGN KEY (client_id) REFERENCES client (client_id) ON DELETE CASCADE;;
