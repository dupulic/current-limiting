/*
SQLyog Job Agent Version 8.14 Copyright(c) Webyog Softworks Pvt. Ltd. All Rights Reserved.


MySQL - 5.7.20-log : Database - demo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`demo` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `demo`;

/*Table structure for table `cy_categories` */

DROP TABLE IF EXISTS `cy_categories`;

CREATE TABLE `cy_categories` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '父级分类ID，0为顶级分类',
  `cate_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分类名称',
  `sort` smallint(5) unsigned NOT NULL DEFAULT '99' COMMENT '排序字段',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `categories_cate_name_unique` (`cate_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `cy_categories` */

/*Table structure for table `cy_goods` */

DROP TABLE IF EXISTS `cy_goods`;

CREATE TABLE `cy_goods` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品名称',
  `cate_id` int(10) unsigned NOT NULL COMMENT '分类ID',
  `price` bigint(20) unsigned NOT NULL,
  `original` bigint(20) unsigned NOT NULL COMMENT '商品原价',
  `tags` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品标签',
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品内容',
  `summary` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品描述',
  `is_sale` tinyint(4) NOT NULL COMMENT '上架状态: 1是0是',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `goods_cate_id_foreign` (`cate_id`),
  CONSTRAINT `goods_cate_id_foreign` FOREIGN KEY (`cate_id`) REFERENCES `cy_categories` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `cy_goods` */

/*Table structure for table `cy_goods_sku` */

DROP TABLE IF EXISTS `cy_goods_sku`;

CREATE TABLE `cy_goods_sku` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` int(10) unsigned NOT NULL COMMENT '商品ID',
  `title` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '规格名称',
  `num` int(10) unsigned NOT NULL COMMENT 'SKU库存',
  `price` bigint(20) unsigned NOT NULL COMMENT '商品售价',
  `properties` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品属性，以逗号分隔',
  `bar_code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '条码',
  `goods_code` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '商品码',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态:1启用,0禁用',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `goods_sku_goods_id_foreign` (`goods_id`),
  CONSTRAINT `goods_sku_goods_id_foreign` FOREIGN KEY (`goods_id`) REFERENCES `cy_goods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `cy_goods_sku` */

/*Table structure for table `rd_request_count` */

DROP TABLE IF EXISTS `rd_request_count`;

CREATE TABLE `rd_request_count` (
  `ip` varchar(64) NOT NULL COMMENT '用户ip',
  `success_count` int(10) DEFAULT '0' COMMENT '用户请求成功次数',
  `failure_count` int(10) DEFAULT '0' COMMENT '用户请求失败次数',
  `is_close` char(1) DEFAULT '0' COMMENT '是否加入黑名单:1是,0否',
  PRIMARY KEY (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `rd_request_count` */

insert  into `rd_request_count` values ('0:0:0:0:0:0:0:1:/access/accessLimit',199,18,'1');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
