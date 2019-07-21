CREATE TABLE `parking_order` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `car_number` VARCHAR(255),
    `create_time` BIGINT,
    `end_time` BIGINT,
    `status` BOOLEAN DEFAULT TRUE,
    `parking_lot_name` VARCHAR(255) REFERENCES parking_lot(name)
);
