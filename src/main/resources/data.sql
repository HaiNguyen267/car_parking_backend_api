CREATE TABLE IF NOT EXISTS user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    is_locked BOOLEAN DEFAULT FALSE,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS user_log (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    event VARCHAR(255) NOT NULL,
    created_at VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS zone (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL,
    parking_fee FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS spot (
    id INT PRIMARY KEY AUTO_INCREMENT,
    zone_name VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    code INT NOT NULL,
    start_parking_time VARCHAR(255),
    status VARCHAR(255) NOT NULL,
    FOREIGN KEY (zone_name) REFERENCES zone(name),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS spot_log (
    id INT PRIMARY KEY AUTO_INCREMENT,
    spot_id INT NOT NULL,
    user_id INT NOT NULL,
    event VARCHAR(255) NOT NULL,
    created_at VARCHAR(255) NOT NULL,
    FOREIGN KEY (spot_id) REFERENCES spot(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);