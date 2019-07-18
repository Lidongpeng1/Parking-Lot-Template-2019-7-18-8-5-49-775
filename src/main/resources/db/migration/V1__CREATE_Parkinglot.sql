CREATE TABLE `parking_lot` (
    `name` VARCHAR(255) NOT NULL PRIMARY KEY,
    `capacity` INT DEFAULT 0 CHECK(`capacity` >= 0),
    `location` VARCHAR(255)
);
