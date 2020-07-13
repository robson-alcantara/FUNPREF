ALTER TABLE `funpref`.`beneficiary` 
DROP FOREIGN KEY `fk_beneficiary_user_registration`;
ALTER TABLE `funpref`.`beneficiary` 
CHANGE COLUMN `ref_id_user_registration` `ref_id_user_registration` INT UNSIGNED NULL ;
ALTER TABLE `funpref`.`beneficiary` 
ADD CONSTRAINT `fk_beneficiary_user_registration`
  FOREIGN KEY (`ref_id_user_registration`)
  REFERENCES `funpref`.`user` (`id_user`);
