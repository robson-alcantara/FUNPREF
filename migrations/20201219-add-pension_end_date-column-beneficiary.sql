ALTER TABLE `funpref`.`beneficiary` 
ADD COLUMN `pension_end_date` DATE NULL DEFAULT NULL AFTER `grant_of_benefit_date`;
