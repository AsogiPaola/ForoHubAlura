CREATE TABLE IF NOT EXISTS topics(
    id BIGINT AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL UNIQUE,
    message  VARCHAR(350) NOT NULL UNIQUE,
    date_creation DATE NOT NULL,
    state VARCHAR(30) NOT NULL,
    author_id BIGINT NOT NULL,
    curse_id BIGINT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(author_id) REFERENCES users(id),
    FOREIGN KEY(curse_id) REFERENCES curses(id)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;