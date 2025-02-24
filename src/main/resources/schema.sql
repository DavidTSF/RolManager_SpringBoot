CREATE TABLE item (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      name VARCHAR(255) NOT NULL,
      description VARCHAR(255),
      type VARCHAR(255),
      "value" INT,
      weight DECIMAL
);