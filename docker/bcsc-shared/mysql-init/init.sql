grant all privileges on *.* to 'ROOT'@'%' identified by 'Password*8' with grant option;
FLUSH PRIVILEGES;
create user 'supplier'@'localhost' identified by 'Password*8';
grant all privileges on *.* to 'supplier'@'localhost' identified by 'Password*8' with grant option;
create user 'supplier'@'%' identified by 'Password*8';
grant all privileges on *.* to 'supplier'@'%' identified by 'Password*8' with grant option;
FLUSH PRIVILEGES;
create database db_supplier;
use db_supplier;

DROP PROCEDURE IF EXISTS PROC_DROP_FOREIGN_KEY;
DELIMITER $$
CREATE PROCEDURE PROC_DROP_FOREIGN_KEY(IN tableName VARCHAR(64), IN constraintName VARCHAR(64))
BEGIN
    IF EXISTS(
        SELECT * FROM information_schema.table_constraints
        WHERE 
            table_schema    = DATABASE()     AND
            table_name      = tableName      AND
            constraint_name = constraintName AND
            constraint_type = 'FOREIGN KEY')
    THEN
        SET @query = CONCAT('ALTER TABLE ', tableName, ' DROP FOREIGN KEY ', constraintName, ';');
        PREPARE stmt FROM @query; 
        EXECUTE stmt; 
        DEALLOCATE PREPARE stmt; 
    END IF; 
END$$
DELIMITER ;