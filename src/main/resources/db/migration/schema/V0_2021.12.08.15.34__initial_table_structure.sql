CREATE TABLE data_point
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    external_id VARCHAR(255) UNIQUE NOT NULL,
    value VARCHAR(255) NOT NULL,
    comment TEXT,
    significance integer NOT NULL DEFAULT 1
);
