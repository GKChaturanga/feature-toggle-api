 
DROP TABLE IF EXISTS `customer`;
 
CREATE TABLE `customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `last_update` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;
 
DROP TABLE IF EXISTS `customerfeaturetoggle`;
 
CREATE TABLE `customerfeaturetoggle` (
  `id` bigint NOT NULL AUTO_INCREMENT, 
  `created_date` datetime(6) DEFAULT NULL,
  `last_update` datetime(6) DEFAULT NULL,
  `customerid` bigint DEFAULT NULL,
  `featuretoggleid` bigint DEFAULT NULL, 
  PRIMARY KEY (`id`) 
)  ;
 
DROP TABLE IF EXISTS `featuretoggle`;
 
CREATE TABLE `featuretoggle` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `last_update` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `displayname` varchar(255) DEFAULT NULL,
  `expireson` datetime(6) DEFAULT NULL,
  `inverted` bit(1) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `technicalname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;
 