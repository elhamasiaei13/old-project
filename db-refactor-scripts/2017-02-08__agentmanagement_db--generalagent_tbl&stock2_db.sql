update agentmanagement_db.generalagent_tbl set branch = 0 where branch is null;
update agentmanagement_db.generalagent_tbl set active = 0 where active is null;
ALTER TABLE `agentmanagement_db`.`generalagent_tbl` 
CHANGE COLUMN `active` `active` BIT(1) NOT NULL ,
CHANGE COLUMN `branch` `branch` BIT(1) NOT NULL ;


ALTER TABLE stock2_db.waybillnumberchangestatecommand_tbl 
CHANGE COLUMN remarks remarks LONGTEXT CHARACTER SET 'utf8' NULL DEFAULT NULL ;

ALTER TABLE stock2_db.waybillnumberchangeassigneecommand_tbl 
CHANGE COLUMN remarks remarks LONGTEXT CHARACTER SET 'utf8' NULL DEFAULT NULL ;

ALTER TABLE stock2_db.waybillnumberupdatecommand_tbl 
CHANGE COLUMN remarks remarks LONGTEXT CHARACTER SET 'utf8' NULL DEFAULT NULL ;

ALTER TABLE stock2_db.waybillnumberdisplay_tbl 
CHANGE COLUMN allRemarks allRemarks LONGTEXT CHARACTER SET 'utf8' NULL DEFAULT NULL ;



