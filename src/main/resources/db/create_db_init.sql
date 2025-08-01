create table t_team
(
    id               BIGINT DEFAULT random() primary key,
    name             varchar(20) not null,
    product          varchar(20) not null,
    created_at       DATE,
    modified_at      DATE,
    default_location varchar(20) not null
);

CREATE SEQUENCE IF NOT EXISTS SEQ_TEAM_ID;
CREATE TABLE T_RACK
(
    id               BIGINT DEFAULT random() PRIMARY KEY,
    serial_number    text        not null unique,
    status           varchar(10),
    team_id          BIGINT      NOT NULL REFERENCES T_TEAM (id),
    default_location varchar(10) not null,
    created_at       TIMESTAMP default current_timestamp,
    modified_at      TIMESTAMP,
    assembled_at     TIMESTAMP default current_timestamp
);
CREATE SEQUENCE IF NOT EXISTS SEQ_RACK_ID;

CREATE TABLE T_TEAM_MEMBER
(
    id          BIGINT DEFAULT random() PRIMARY KEY,
    team_id     BIGINT,
    ctw_id      text,
    name        text,
    created_at  TIMESTAMP,
    modified_at TIMESTAMP,
    FOREIGN KEY (team_id) REFERENCES T_TEAM (id)
);
CREATE SEQUENCE IF NOT EXISTS SEQ_TEAM_MEMBER_ID;

CREATE TABLE T_RACK_ASSET
(
    id        BIGINT DEFAULT random() PRIMARY KEY,
    asset_tag text,
    rack_id   BIGINT DEFAULT random(),
    FOREIGN KEY (rack_id) REFERENCES T_RACK (id)
);
CREATE SEQUENCE IF NOT EXISTS SEQ_RACK_ASSET_ID;

CREATE TABLE T_BOOKING
(
    id            BIGINT DEFAULT random() PRIMARY KEY,
    rack_id       BIGINT,
    requester_id  BIGINT,
    book_from     TIMESTAMP,
    book_to       TIMESTAMP,
    created_at    TIMESTAMP,
    modified_at   TIMESTAMP,
    FOREIGN KEY (requester_id) REFERENCES T_TEAM_MEMBER (id),
    FOREIGN KEY (rack_id) REFERENCES T_RACK (id)
);
CREATE SEQUENCE IF NOT EXISTS SEQ_BOOKING_ID;




