CREATE SCHEMA IF NOT EXISTS avitech;

CREATE TABLE IF NOT EXISTS avitech.SUSERS (
                                USER_ID INT NOT NULL,
                                USER_GUID VARCHAR(255) NOT NULL,
                                USER_NAME VARCHAR(255) NOT NULL,
                                PRIMARY KEY (USER_ID)
);
