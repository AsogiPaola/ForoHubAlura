CREATE TABLE IF NOT EXISTS answers(
    id BIGINT AUTO_INCREMENT,
    message VARCHAR(350) NOT NULL,
    topic_id  BIGINT NOT NULL,
    date_creation DATE NOT NULL,
    author_id BIGINT NOT NULL,
    solution BOOLEAN NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(topic_id) REFERENCES topics(id),
    FOREIGN KEY(author_id) REFERENCES users(id)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;