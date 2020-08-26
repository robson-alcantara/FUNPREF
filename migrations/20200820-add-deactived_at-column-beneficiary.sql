ALTER TABLE `funpref`.`beneficiary` 
ADD COLUMN `deactivated_at` DATETIME NULL DEFAULT NULL AFTER `registered_at`;
