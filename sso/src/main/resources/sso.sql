/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.6.37-log : Database - dw_oauth_server
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`sso` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `dw_oauth_server`;

/*Table structure for table `client` */

DROP TABLE IF EXISTS `client`;

CREATE TABLE `client` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `access_token` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `access_token_validity_seconds` int(11) DEFAULT NULL,
  `authorities` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `authorized_grant_types` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `client_id` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `client_secret` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_scoped` bit(1) NOT NULL,
  `is_secret_required` bit(1) NOT NULL,
  `modify_time` datetime DEFAULT NULL,
  `refresh_access_token` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `refresh_token_validity_seconds` int(11) DEFAULT NULL,
  `registered_redirect_uri` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `resource_ids` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `scopes` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `auto_approve_scopes` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `client` */

insert  into `client`(`id`,`access_token`,`access_token_validity_seconds`,`authorities`,`authorized_grant_types`,`client_id`,`client_secret`,`create_time`,`is_scoped`,`is_secret_required`,`modify_time`,`refresh_access_token`,`refresh_token_validity_seconds`,`registered_redirect_uri`,`resource_ids`,`scopes`,`auto_approve_scopes`) values (1,NULL,NULL,NULL,'authorization_code,password,implicit,refresh_token,client_credentials','test','test','2017-10-25 11:38:40','','\0','2017-10-25 11:38:52',NULL,NULL,'https://baidu.com',NULL,'test,info',NULL);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `birthday` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `nickname` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `idCard` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `bankCrad` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `sex` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `status` int COLLATE utf8mb4_bin DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `photo_url` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Data for the table `user` */

insert  into `user`(`id`,`birthday`,`create_time`,`email`,`modify_time`,`nickname`,`password`,`phone`,`sex`,`status`,`username`,`photo_url`) values (1,NULL,'2017-10-08 08:35:00','511980432@qq.com','2017-10-08 08:34:57','tupengxiong','$2a$10$73KBfz6Z3aEMm7oNlli6E.6wqEmAX0Y4x/ta/XddpIod8wPJQX2vG','17710482932',NULL,NULL,'user1','https://image.lovesher.com/71d1c3b5ada170327fda35e47995b19d'),(2,NULL,'2017-10-12 13:09:47','294118639@qq.com','2017-10-12 13:09:59','user2','$2a$10$73KBfz6Z3aEMm7oNlli6E.6wqEmAX0Y4x/ta/XddpIod8wPJQX2vG','18370960877',NULL,NULL,'songhuan','https://image.lovesher.com/1dba335cdab5b651d64285a3d89120a1');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
