/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : meilian

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-03-29 18:01:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for meilian_feedback
-- ----------------------------
DROP TABLE IF EXISTS `meilian_feedback`;
CREATE TABLE `meilian_feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL COMMENT '管理员，意见已反馈时才会出现',
  `suggestionid` int(11) NOT NULL COMMENT '意见ID',
  `content` text NOT NULL COMMENT '反馈的内容',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '反馈时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for meilian_suggestion
-- ----------------------------
DROP TABLE IF EXISTS `meilian_suggestion`;
CREATE TABLE `meilian_suggestion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `img` longtext,
  `userid` int(11) NOT NULL COMMENT '提意见的人',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交意见时间',
  `isFeedback` int(1) DEFAULT '0' COMMENT '是否被回复 0 否 1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for meilian_user
-- ----------------------------
DROP TABLE IF EXISTS `meilian_user`;
CREATE TABLE `meilian_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(100) NOT NULL COMMENT '微信openid 对同一公众号是唯一的',
  `nickname` varchar(20) NOT NULL,
  `phone` varchar(15) DEFAULT NULL COMMENT '电话号码',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间(注册本应用，并非注册微信)',
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '适用于用户之前匿名注册过，关注公众号后重新修改了用户的信息',
  `wechat_account` varchar(50) DEFAULT '' COMMENT '微信号',
  `wechat_nickname` varchar(20) DEFAULT '' COMMENT '微信昵称',
  `wechat_sex` varchar(5) DEFAULT NULL COMMENT '性别 男/女',
  `wechat_country` varchar(10) DEFAULT NULL COMMENT '国家',
  `wechat_province` varchar(10) DEFAULT NULL COMMENT '省份',
  `wechat_city` varchar(10) DEFAULT '' COMMENT '城市',
  `wechat_headimgurl` blob COMMENT '头像图片',
  `wechat_privilege` varchar(255) DEFAULT NULL COMMENT '用户特权信息，json 数组',
  `wechat_unionid` varchar(255) DEFAULT NULL COMMENT '只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
