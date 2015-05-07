/*
Navicat MySQL Data Transfer

Source Server         : 112.74.112.157
Source Server Version : 50621
Source Host           : 112.74.112.157:3307
Source Database       : huaye

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2015-03-20 15:07:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `cloud_column`
-- ----------------------------
DROP TABLE IF EXISTS `cloud_column`;
CREATE TABLE `cloud_column` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父节点外键',
  `parent_ids` varchar(255) DEFAULT NULL COMMENT '根目录开始的所有父节点',
  `name` varchar(255) DEFAULT NULL COMMENT '频道名称',
  `entity_status` varchar(255) DEFAULT NULL COMMENT '实体状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier` varchar(255) DEFAULT NULL COMMENT '修改人姓名',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`id`),
  KEY `fk_column_parent` (`parent_id`),
  CONSTRAINT `fk_column_parent` FOREIGN KEY (`parent_id`) REFERENCES `cloud_column` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cloud_column
-- ----------------------------
INSERT INTO `cloud_column` VALUES ('1', null, null, '园区', null, null, null, null, null, null, null);
INSERT INTO `cloud_column` VALUES ('2', '1', null, '杭州高新区', null, null, null, null, null, null, null);
INSERT INTO `cloud_column` VALUES ('3', '1', null, '浙大科技园', null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `cloud_web_content`
-- ----------------------------
DROP TABLE IF EXISTS `cloud_web_content`;
CREATE TABLE `cloud_web_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `webInfo_id` bigint(20) NOT NULL COMMENT '网站外键',
  `contentId` varchar(255) DEFAULT NULL COMMENT '内容标识id',
  `url` varchar(255) DEFAULT NULL COMMENT '内容地址',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `imageUrl` varchar(255) DEFAULT NULL COMMENT '图片路径',
  `hear` varchar(255) DEFAULT NULL COMMENT '受理',
  `release_date` datetime DEFAULT NULL COMMENT '发布时间',
  `content` text COMMENT '内容',
  `entity_status` varchar(255) DEFAULT NULL COMMENT '实体状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier` varchar(255) DEFAULT NULL COMMENT '修改人姓名',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`id`),
  KEY `fk_WebContent_webInfo` (`webInfo_id`),
  CONSTRAINT `fk_WebContent_webInfo` FOREIGN KEY (`webInfo_id`) REFERENCES `cloud_web_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cloud_web_content
-- ----------------------------
INSERT INTO `cloud_web_content` VALUES ('1', '1', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=4de8023c-7b39-4cdf-8e38-e782f884320f,d955108e-11e4-4522-a8fa-85e83cdbff63,2015-3', '我区召开优秀基层典型座谈会', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:51', null, null, '2015-03-11 17:59:51', null, null);
INSERT INTO `cloud_web_content` VALUES ('2', '1', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=4de8023c-7b39-4cdf-8e38-e782f884320f,a227fcd5-7ae8-462d-97a6-a7bf4d77f58b,2015-3', '区四套班子领导参加义务植树活动', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:51', null, null, '2015-03-11 17:59:51', null, null);
INSERT INTO `cloud_web_content` VALUES ('3', '1', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=4de8023c-7b39-4cdf-8e38-e782f884320f,2dde68be-a2c3-4bbb-b0a6-df2a21b42ae4,2015-3', '我区召开“五水共治”工作专题会', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:51', null, null, '2015-03-11 17:59:51', null, null);
INSERT INTO `cloud_web_content` VALUES ('4', '1', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=4de8023c-7b39-4cdf-8e38-e782f884320f,f0856a68-7584-4262-b14b-9d47e27d5444,2015-3', '全区“三改一拆”工作会议召开', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:51', null, null, '2015-03-11 17:59:51', null, null);
INSERT INTO `cloud_web_content` VALUES ('5', '1', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=4de8023c-7b39-4cdf-8e38-e782f884320f,ba77c12b-7855-464d-a182-6dafec7bacc2,2015-3', '詹敏调研共联协同社区工作', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:51', null, null, '2015-03-11 17:59:51', null, null);
INSERT INTO `cloud_web_content` VALUES ('6', '1', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=4de8023c-7b39-4cdf-8e38-e782f884320f,0fbfe4fd-c0c3-4b89-a252-880c80b73613,2015-3', '詹敏走访区内部分高新企业', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:51', null, null, '2015-03-11 17:59:51', null, null);
INSERT INTO `cloud_web_content` VALUES ('7', '1', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=4de8023c-7b39-4cdf-8e38-e782f884320f,3cd68bcb-8b94-467a-8f5c-0726fbb9aba0,2015-3', '詹敏调研区科技金融服务中心工作', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:51', null, null, '2015-03-11 17:59:51', null, null);
INSERT INTO `cloud_web_content` VALUES ('8', '1', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=4de8023c-7b39-4cdf-8e38-e782f884320f,c6f26414-fc9a-4c1b-ac23-9518e0658113,2015-3', '支持海康威视  共议警企合作', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:51', null, null, '2015-03-11 17:59:51', null, null);
INSERT INTO `cloud_web_content` VALUES ('9', '1', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=4de8023c-7b39-4cdf-8e38-e782f884320f,a0c0f304-5427-4b0f-bb48-e3f1d2eaf807,2015-3', '张耕来我区调研国家自主创新示范区创建准备工作', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:51', null, null, '2015-03-11 17:59:51', null, null);
INSERT INTO `cloud_web_content` VALUES ('10', '1', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=4de8023c-7b39-4cdf-8e38-e782f884320f,e12294cb-c08e-4b31-9ffa-1bc93e0f9e27,2015-3', '我区对“两会”建议提案进行交办', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:51', null, null, '2015-03-11 17:59:51', null, null);
INSERT INTO `cloud_web_content` VALUES ('11', '1', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=4de8023c-7b39-4cdf-8e38-e782f884320f,e206d077-1357-4a1c-b401-93d7dc091be7,2015-2', '詹敏走访调研傅家峙和江二社区工作', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:51', null, null, '2015-03-11 17:59:51', null, null);
INSERT INTO `cloud_web_content` VALUES ('12', '1', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=4de8023c-7b39-4cdf-8e38-e782f884320f,cb2fec54-1d8d-46e4-8f3e-f3d0f03e1759,2015-2', '区领导羊年首个工作日到部分单位走访慰问', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:51', null, null, '2015-03-11 17:59:51', null, null);
INSERT INTO `cloud_web_content` VALUES ('13', '1', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=4de8023c-7b39-4cdf-8e38-e782f884320f,c895cb3d-dacc-4353-93a8-ed606988034a,2015-2', '区纪委四届五次全体会议暨作风建设大会召开', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:51', null, null, '2015-03-11 17:59:51', null, null);
INSERT INTO `cloud_web_content` VALUES ('14', '1', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=4de8023c-7b39-4cdf-8e38-e782f884320f,510c455d-8912-4cf3-8a19-955f3aada644,2015-2', '金志鹏检查全区烟花爆竹安全工作', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:51', null, null, '2015-03-11 17:59:51', null, null);
INSERT INTO `cloud_web_content` VALUES ('15', '1', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=4de8023c-7b39-4cdf-8e38-e782f884320f,f50d1357-d483-4a7f-9877-366356c14a15,2015-2', '袁家军慰问我区检察干警', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:51', null, null, '2015-03-11 17:59:51', null, null);
INSERT INTO `cloud_web_content` VALUES ('16', '2', '', 'http://www.hhtz.gov.cn/DesktopDefault.aspx?tabid=fe3d690e-a6a4-4166-bc4e-e63bd3136c1b', '通知公告', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('17', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,115a6cf4-be74-45dd-a216-28b1ef5cdee1,2015-3', '关于排放污染物申报登记、排污许可证年检办理具体时间的通知', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('18', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,739ca87b-41b5-4856-a891-db9365e98637,2015-3', '关于做好2015年度杭州市外国专家“钱江友谊奖”推荐申报工作的通知', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('19', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,044f4908-f163-4146-a92e-3f4d7ab75593,2015-3', '关于做好2015年度省151人才工程第三层次培养人员推荐工作的通知', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('20', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,d6dcbf16-d546-451c-937d-2607bad93f11,2015-3', ' 关于申报2015年度第一批高校毕业生在杭创业资助资金的通知', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('21', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,83c458e5-4ea3-42a4-acbd-a9bce155e30e,2015-3', '关于开展2015年工资集体协商“集中要约行动”活动的通知', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('22', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,I150309092058756,2015-3', '征稿启事', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('23', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,3937a5e6-bdb0-43f8-9b92-1a6729706663,2015-3', '关于开展二�一五年杭州市物联网推广示范应用项目申报认定工作的通知', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('24', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,I150228165421844,2015-2', '转发杭州市市场监管局《关于开展2015年度“守合同重信用”企业申报培训的通知》的通知', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('25', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,1aa6376f-ea23-490d-8730-e2f3320a6c9e,2015-2', '滨江区享受2015年3月-2015年8月低保、残保困难家庭公示', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('26', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,2dcbafed-4761-4131-8b26-a5c77f676f72,2015-2', '转发省经信委关于组织申报2015年度浙江省优秀工业新产品（新技术）的通知', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('27', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,0d816d4d-475e-4fd7-b827-f7cfc7f59c9a,2015-2', '转发省经信委关于组织开展2015年浙江省企业技术中心认定工作的通知', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('28', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,7be30d9e-8b2d-4411-9cff-a704b9220230,2015-2', '关于组织申报2015年能源自主创新和能源装备专项项目的通知', null, 'No', '2015-03-11 17:59:51', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('29', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,d37e7e13-d01b-431c-8211-472a3877b9a3,2015-2', '关于江南体育中心春节期间暂停营业的通告', null, 'No', '2015-03-11 17:59:52', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('30', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,5ea2ba2c-f9a2-44d3-8596-7374ae5f325e,2015-2', '公      告', null, 'No', '2015-03-11 17:59:52', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('31', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,I150203170249108,2015-2', '关于开展2015年春节前食品安全检查的通知', null, 'No', '2015-03-11 17:59:52', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('32', '3', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1b04a1a0-e479-4c59-9486-741bc53d26cf,a783322b-2231-4582-b617-78d12a9a7162,2015-3', '关于开展2015年新认定国家高新技术企业预申报工作的通知', null, 'No', '2015-03-11 17:59:52', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('33', '3', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1b04a1a0-e479-4c59-9486-741bc53d26cf,12894577-bd01-41d6-a22d-395440aa7a92,2015-2', '关于开展2014年度杭州市“青蓝计划”企业绩效考核工作的通知', null, 'No', '2015-03-11 17:59:52', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('34', '3', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1b04a1a0-e479-4c59-9486-741bc53d26cf,5746d3ae-1a7f-4054-ae91-0f5f032e8cdb,2015-2', '关于开展2014年度杭州市“雏鹰计划”企业绩效考核工作的通知', null, 'No', '2015-03-11 17:59:52', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('35', '3', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1b04a1a0-e479-4c59-9486-741bc53d26cf,14f92453-ec64-42f2-9d36-86601dc11b8b,2015-2', '转发<科技部关于开展“十三五”国家重点研发计划优先启动重点研发任务建议征集工作的通知>', null, 'No', '2015-03-11 17:59:52', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('36', '3', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1b04a1a0-e479-4c59-9486-741bc53d26cf,3f4ad6d2-b181-4cc1-972c-8e3fe28897a9,2015-2', '关于开展杭州市专利试点（示范）企业申报和考核工作的通知', null, 'No', '2015-03-11 17:59:52', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('37', '3', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1b04a1a0-e479-4c59-9486-741bc53d26cf,91d695b1-0ff2-417a-822d-c86dfb7df4b4,2015-2', '关于组织开展涉企科技经费使用情况自查的通知', null, 'No', '2015-03-11 17:59:52', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('38', '3', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1b04a1a0-e479-4c59-9486-741bc53d26cf,b29b774a-c826-40ea-a617-6abdf0a30a28,2015-1', '关于组织开展2015年涉企科技经费使用监督检查第二次培训会的通知', null, 'No', '2015-03-11 17:59:52', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('39', '3', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1b04a1a0-e479-4c59-9486-741bc53d26cf,2ca5429a-35d8-4dc8-8a96-dcfc138b0e51,2015-1', '关于转发征集2015年杭州市科学技术进步奖申奖项目的通知', null, 'No', '2015-03-11 17:59:52', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('40', '3', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1b04a1a0-e479-4c59-9486-741bc53d26cf,72656fdf-303a-4b44-8053-1b2b3fbffa23,2015-1', '关于组织申报第十七届中国专利奖的通知', null, 'No', '2015-03-11 17:59:52', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('41', '3', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1b04a1a0-e479-4c59-9486-741bc53d26cf,56733743-b886-4a8a-befb-8415e9fe0ce3,2015-1', '关于征集2015年度国家知识产权局审查员专业技术知识更新与实践培训企业的通知 ', null, 'No', '2015-03-11 17:59:52', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('42', '3', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1b04a1a0-e479-4c59-9486-741bc53d26cf,I150115111813972,2015-1', '企业、农民专业合作社、个体工商户年度报告公告', null, 'No', '2015-03-11 17:59:52', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('43', '3', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1b04a1a0-e479-4c59-9486-741bc53d26cf,88853e9d-0a25-4bed-a2c3-ff5164848adf,2015-1', '转发浙江省科学技术厅关于开展2015年企业并购海外研发机构申报工作的通知', null, 'No', '2015-03-11 17:59:52', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('44', '3', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1b04a1a0-e479-4c59-9486-741bc53d26cf,daa5af60-8b24-473d-92a1-b1a46adf44a3,2015-1', '关于组织开展2015年涉企科技经费使用监督检查培训会的通知', null, 'No', '2015-03-11 17:59:52', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('45', '3', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1b04a1a0-e479-4c59-9486-741bc53d26cf,686dc8c5-d77d-42c9-ba40-90a07074abd3,2015-1', '关于开展2014年度科技企业孵化器相关情况摸底的通知', null, 'No', '2015-03-11 17:59:52', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('46', '3', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1b04a1a0-e479-4c59-9486-741bc53d26cf,855d94b2-74aa-454c-bf69-f02e7b08f926,2014-10', '关于转发浙江省科学技术厅关于开展2015年网上技术市场成交或竞价（拍卖）产业化项目经费补助申报工作的通知', null, 'No', '2015-03-11 17:59:52', null, 'NORMAL', '2015-03-11 17:59:52', null, null, '2015-03-11 17:59:52', null, null);
INSERT INTO `cloud_web_content` VALUES ('47', '4', '', 'http://www.zjusp.com/display.php?newsId=2060', '杭州市人社局和青岛市人社局在浙大科技园举行大学生创业工作考察交流会', null, 'No', '2015-03-11 17:59:53', null, 'NORMAL', '2015-03-11 17:59:53', null, null, '2015-03-11 17:59:53', null, null);
INSERT INTO `cloud_web_content` VALUES ('48', '4', '', 'http://www.zjusp.com/display.php?newsId=2059', '浙大科技园：解开浙大创业率全国第一的秘密', null, 'No', '2015-03-11 17:59:53', null, 'NORMAL', '2015-03-11 17:59:53', null, null, '2015-03-11 17:59:53', null, null);
INSERT INTO `cloud_web_content` VALUES ('49', '4', '', 'http://www.zjusp.com/display.php?newsId=2053', '杭州大学生创业学院2015年工作座谈会顺利举行', null, 'No', '2015-03-11 17:59:53', null, 'NORMAL', '2015-03-11 17:59:53', null, null, '2015-03-11 17:59:53', null, null);
INSERT INTO `cloud_web_content` VALUES ('50', '4', '', 'http://www.zjusp.com/display.php?newsId=2052', '让大学生的创业梦想落地生根开花', null, 'No', '2015-03-11 17:59:53', null, 'NORMAL', '2015-03-11 17:59:53', null, null, '2015-03-11 17:59:53', null, null);
INSERT INTO `cloud_web_content` VALUES ('51', '4', '', 'http://www.zjusp.com/display.php?newsId=2051', '迎春接福 浙大科技园开工大吉', null, 'No', '2015-03-11 17:59:53', null, 'NORMAL', '2015-03-11 17:59:53', null, null, '2015-03-11 17:59:53', null, null);
INSERT INTO `cloud_web_content` VALUES ('52', '4', '', 'http://www.zjusp.com/display.php?newsId=2046', '杭州市副市长张耕一行踏看浙大科技园', null, 'No', '2015-03-11 17:59:53', null, 'NORMAL', '2015-03-11 17:59:53', null, null, '2015-03-11 17:59:53', null, null);
INSERT INTO `cloud_web_content` VALUES ('53', '4', '', 'http://www.zjusp.com/display.php?newsId=2043', '天津团市委一行来访浙大科技园', null, 'No', '2015-03-11 17:59:53', null, 'NORMAL', '2015-03-11 17:59:53', null, null, '2015-03-11 17:59:53', null, null);
INSERT INTO `cloud_web_content` VALUES ('54', '4', '', 'http://www.zjusp.com/display.php?newsId=2041', '为企业用工风险 “把脉”――浙大科技园举办人力资源私房课系列之“360度解析企业用 ..', null, 'No', '2015-03-11 17:59:53', null, 'NORMAL', '2015-03-11 17:59:53', null, null, '2015-03-11 17:59:53', null, null);
INSERT INTO `cloud_web_content` VALUES ('55', '4', '', 'http://www.zjusp.com/display.php?newsId=2040', '未来之路，我们开启', null, 'No', '2015-03-11 17:59:53', null, 'NORMAL', '2015-03-11 17:59:53', null, null, '2015-03-11 17:59:53', null, null);
INSERT INTO `cloud_web_content` VALUES ('56', '4', '', 'http://www.zjusp.com/display.php?newsId=2039', '感悟创新精神 促进科学发展--浙大科技园党支部赴阿里巴巴、青山湖科技城参观学习', null, 'No', '2015-03-11 17:59:53', null, 'NORMAL', '2015-03-11 17:59:53', null, null, '2015-03-11 17:59:53', null, null);
INSERT INTO `cloud_web_content` VALUES ('57', '4', '', 'http://www.zjusp.com/display.php?newsId=2038', '全国政协委员考察团到浙大科技园调研', null, 'No', '2015-03-11 17:59:53', null, 'NORMAL', '2015-03-11 17:59:53', null, null, '2015-03-11 17:59:53', null, null);
INSERT INTO `cloud_web_content` VALUES ('58', '4', '', 'http://www.zjusp.com/display.php?newsId=2037', '入驻e-WORKS，梦想起航！', null, 'No', '2015-03-11 17:59:53', null, 'NORMAL', '2015-03-11 17:59:53', null, null, '2015-03-11 17:59:53', null, null);
INSERT INTO `cloud_web_content` VALUES ('59', '4', '', 'http://www.zjusp.com/display.php?newsId=2031', '浙大科技园孵化毕业企业浙达精益在“新三板”挂牌上市', null, 'No', '2015-03-11 17:59:53', null, 'NORMAL', '2015-03-11 17:59:53', null, null, '2015-03-11 17:59:53', null, null);
INSERT INTO `cloud_web_content` VALUES ('60', '4', '', 'http://www.zjusp.com/display.php?newsId=2027', '大学生创业 你真的准备好了吗？', null, 'No', '2015-03-11 17:59:53', null, 'NORMAL', '2015-03-11 17:59:53', null, null, '2015-03-11 17:59:53', null, null);
INSERT INTO `cloud_web_content` VALUES ('61', '4', '', 'http://www.zjusp.com/display.php?newsId=2024', '葛朝阳主任在“对接‘两大国家级战略’暨浙江清华长三角研究院杭州分院入驻仪式”上 ..', null, 'No', '2015-03-11 17:59:53', null, 'NORMAL', '2015-03-11 17:59:53', null, null, '2015-03-11 17:59:53', null, null);
INSERT INTO `cloud_web_content` VALUES ('62', '5', '', 'http://www.zjusp.com/display.php?newsId=2057', '第240期浙大科技园创业沙龙', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('63', '5', '', 'http://www.zjusp.com/display.php?newsId=2055', '2015年浙江省引进海外高层次人才公告', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('64', '5', '', 'http://www.zjusp.com/display.php?newsId=2045', '关于做好春节假期期间园区安全工作的通知', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('65', '5', '', 'http://www.zjusp.com/display.php?newsId=2036', '浙大科技园人力资源私房课系列之360度解析企业用工风险', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('66', '5', '', 'http://www.zjusp.com/display.php?newsId=2035', '关于公布浙江大学e-WORKS创业实验室第一批入围项目的通知', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('67', '5', '', 'http://www.zjusp.com/display.php?newsId=2034', '消防联动通知', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('68', '5', '', 'http://www.zjusp.com/display.php?newsId=2013', '浙江大学e-WORKS创业实验室启动仪式暨“移动互联网时代的大学生创业”主题分享会', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('69', '5', '', 'http://www.zjusp.com/display.php?newsId=2009', '关于发布浙大科技园“金融超市”服务项目的通知', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('70', '5', '', 'http://www.zjusp.com/display.php?newsId=2007', '关于第九届拔河比赛的通知', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('71', '5', '', 'http://www.zjusp.com/display.php?newsId=2001', '西湖・浙大科技园海归创业俱乐部会员招募通知', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('72', '5', '', 'http://www.zjusp.com/display.php?newsId=2000', '关于浙江大学e-WORKS创业实验室征集创业项目和团队的通知', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('73', '5', '', 'http://www.zjusp.com/display.php?newsId=1999', '科技园关于举办科技成果转化与产业化政策解读会的通知', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('74', '5', '', 'http://www.zjusp.com/display.php?newsId=1997', '第239期浙大科技园创业沙龙系列之创业门诊（人力资源、企业实战）', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('75', '5', '', 'http://www.zjusp.com/display.php?newsId=1996', '关于征集2014年西湖区企业授权发明专利的通知', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('76', '5', '', 'http://www.zjusp.com/display.php?newsId=1995', '关于举办机器换人智能制造应用专题培训的通知', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('77', '6', '', 'http://www.zjusp.com/display.php?newsId=2048', '土壤很重要，人更重要', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('78', '6', '', 'http://www.zjusp.com/display.php?newsId=2047', '在浙大科技园海归建筑师舒怀眼里 每一个设计都是唯一', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('79', '6', '', 'http://www.zjusp.com/display.php?newsId=2026', '80后王暾：放弃留美读博 情迷创业', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('80', '6', '', 'http://www.zjusp.com/display.php?newsId=1982', '乐港CEO陈博荣获首届“最美西湖企业家”称号', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('81', '6', '', 'http://www.zjusp.com/display.php?newsId=1963', '创业的青春不是梦――访获得“创青春�贝匆荡笕�BA专项金奖的浙大学子', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('82', '6', '', 'http://www.zjusp.com/display.php?newsId=1960', '个推斩获浙江省“火炬杯”创新创业大赛一等奖', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('83', '6', '', 'http://www.zjusp.com/display.php?newsId=1921', '专注的力量――赤霄科技', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('84', '6', '', 'http://www.zjusp.com/display.php?newsId=1920', '“杭州宝泉：让游客将西湖带回家”', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('85', '6', '', 'http://www.zjusp.com/display.php?newsId=1916', '追逐在数据的海洋――追灿的执着', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('86', '6', '', 'http://www.zjusp.com/display.php?newsId=1915', '创业在路上', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('87', '6', '', 'http://www.zjusp.com/display.php?newsId=1924', '浙大科技园企业董事长穿红马甲义务磨剪刀10年', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('88', '6', '', 'http://www.zjusp.com/display.php?newsId=1910', '从不停止折腾――属于王健的自信', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('89', '6', '', 'http://www.zjusp.com/display.php?newsId=1909', '做一个快乐的技术宅――冯少华的而立宣言', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('90', '6', '', 'http://www.zjusp.com/display.php?newsId=1908', '从材料系工科男到母婴产品专家――缪心成的创业成长之路', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('91', '6', '', 'http://www.zjusp.com/display.php?newsId=1862', '车纷享――做一万杭州市民的私家车', null, 'No', '2015-03-11 17:59:54', null, 'NORMAL', '2015-03-11 17:59:54', null, null, '2015-03-11 17:59:54', null, null);
INSERT INTO `cloud_web_content` VALUES ('92', '6', '', 'http://www.zjusp.com/display.php?newsId=1963', '创业的青春不是梦――访获得“创青春”创业大赛MBA专项金奖的浙大学子', null, 'No', '2015-03-12 10:01:49', null, 'NORMAL', '2015-03-12 10:01:49', null, null, '2015-03-12 10:01:49', null, null);
INSERT INTO `cloud_web_content` VALUES ('93', '4', '', 'http://www.zjusp.com/display.php?newsId=2061', '浙师大资产经营公司一行到访浙大科技园', null, 'No', '2015-03-12 17:02:40', null, 'NORMAL', '2015-03-12 17:02:40', null, null, '2015-03-12 17:02:40', null, null);
INSERT INTO `cloud_web_content` VALUES ('94', '3', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1b04a1a0-e479-4c59-9486-741bc53d26cf,56733743-b886-4a8a-befb-8415e9fe0ce3,2015-1', '关于征集2015年度国家知识���稚蟛樵弊ㄒ导际踔�陡�掠胧导�嘌灯笠档耐ㄖ�', null, 'No', '2015-03-12 20:03:01', null, 'NORMAL', '2015-03-12 20:03:01', null, null, '2015-03-12 20:03:01', null, null);
INSERT INTO `cloud_web_content` VALUES ('95', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,28047265-d452-407f-9826-fe7e44253c2f,2015-3', '关于征集第二批“一号工程”项目的通知', null, 'No', '2015-03-13 16:05:28', null, 'NORMAL', '2015-03-13 16:05:28', null, null, '2015-03-13 16:05:28', null, null);
INSERT INTO `cloud_web_content` VALUES ('96', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,af89dc11-5ec3-4155-b8ad-313ee0e83668,2015-3', '杭州高新技术产业开发区（滨江）北塘河畔建设指挥部变更登记公告', null, 'No', '2015-03-13 17:05:35', null, 'NORMAL', '2015-03-13 17:05:35', null, null, '2015-03-13 17:05:35', null, null);
INSERT INTO `cloud_web_content` VALUES ('97', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,b112b330-0c6c-461a-a69f-fc3a0c82f8b3,2015-3', '杭州高新技术产业开发区（滨江）劳动仲裁院变更登记公告', null, 'No', '2015-03-13 17:05:35', null, 'NORMAL', '2015-03-13 17:05:35', null, null, '2015-03-13 17:05:35', null, null);
INSERT INTO `cloud_web_content` VALUES ('98', '4', '', 'http://www.zjusp.com/display.php?newsId=2062', '怀着医生情结在创业，做创业者最好的伙伴', null, 'No', '2015-03-13 17:05:36', null, 'NORMAL', '2015-03-13 17:05:36', null, null, '2015-03-13 17:05:36', null, null);
INSERT INTO `cloud_web_content` VALUES ('99', '4', '', 'http://www.zjusp.com/display.php?newsId=2063', '日本东京大学经济学研究科SHINTAKU教授来浙大科技园访问', null, 'No', '2015-03-13 18:05:47', null, 'NORMAL', '2015-03-13 18:05:47', null, null, '2015-03-13 18:05:47', null, null);
INSERT INTO `cloud_web_content` VALUES ('100', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,I150228165421844,2015-2', '转发杭州市市场监管局《关于开��015年度“守合同重信用”企业申报培训的通知》的通知', null, 'No', '2015-03-13 19:05:50', null, 'NORMAL', '2015-03-13 19:05:50', null, null, '2015-03-13 19:05:50', null, null);
INSERT INTO `cloud_web_content` VALUES ('101', '5', '', 'http://www.zjusp.com/display.php?newsId=2064', '第241期浙大科技园创业沙龙', null, 'No', '2015-03-16 09:20:46', null, 'NORMAL', '2015-03-16 09:20:46', null, null, '2015-03-16 09:20:46', null, null);
INSERT INTO `cloud_web_content` VALUES ('102', '3', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1b04a1a0-e479-4c59-9486-741bc53d26cf,1db33f36-19b1-4884-9b4d-1c326d3085f8,2015-3', '关于举办杭州市专利分析初级实战班的通知', null, 'No', '2015-03-16 10:20:53', null, 'NORMAL', '2015-03-16 10:20:53', null, null, '2015-03-16 10:20:53', null, null);
INSERT INTO `cloud_web_content` VALUES ('103', '3', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1b04a1a0-e479-4c59-9486-741bc53d26cf,2c36d4c9-1563-47d0-907a-8ed7d9c17eb2,2015-3', '转发关于征集2015年杭州市医疗卫生领域科研主动设计项目选题的通知', null, 'No', '2015-03-16 10:20:53', null, 'NORMAL', '2015-03-16 10:20:53', null, null, '2015-03-16 10:20:53', null, null);
INSERT INTO `cloud_web_content` VALUES ('104', '4', '', 'http://www.zjusp.com/display.php?newsId=2065', '台州市副市长李跃程率团考察浙大科技园', null, 'No', '2015-03-16 11:21:01', null, 'NORMAL', '2015-03-16 11:21:01', null, null, '2015-03-16 11:21:01', null, null);
INSERT INTO `cloud_web_content` VALUES ('105', '3', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1b04a1a0-e479-4c59-9486-741bc53d26cf,1b1f3679-eea7-4d30-9957-4b01bb5fa740,2015-3', '杭州高新区（滨江）关于开展2015年高新技术企业认定(复审）等工作的通知', null, 'No', '2015-03-16 14:21:22', null, 'NORMAL', '2015-03-16 14:21:22', null, null, '2015-03-16 14:21:22', null, null);
INSERT INTO `cloud_web_content` VALUES ('106', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,81ba6e6f-294b-4b94-8270-e6a9164f5d35,2015-3', '杭州市滨江区农村多层住宅建设管理中心变更登记公告', null, 'No', '2015-03-16 16:21:37', null, 'NORMAL', '2015-03-16 16:21:37', null, null, '2015-03-16 16:21:37', null, null);
INSERT INTO `cloud_web_content` VALUES ('107', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,044f4908-f163-4146-a92e-3f4d7ab75593,2015-3', '关于做好2015�甓仁�51人才工程第三层次培养人员推荐工作的通知', null, 'No', '2015-03-16 19:43:56', null, 'NORMAL', '2015-03-16 19:43:56', null, null, '2015-03-16 19:43:56', null, null);
INSERT INTO `cloud_web_content` VALUES ('108', '1', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=4de8023c-7b39-4cdf-8e38-e782f884320f,44fbe941-f4ce-4a41-b8de-2e592002b508,2015-3', '区领导调研浦沿街道征地拆迁工作', null, 'No', '2015-03-17 16:49:05', null, 'NORMAL', '2015-03-17 16:49:05', null, null, '2015-03-17 16:49:05', null, null);
INSERT INTO `cloud_web_content` VALUES ('109', '3', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1b04a1a0-e479-4c59-9486-741bc53d26cf,f478a63e-e5f5-49e2-947f-364ab528a43e,2015-3', '关于开展2015年度区科协重点学术活动项目申报工作的通知', null, 'No', '2015-03-17 16:49:06', null, 'NORMAL', '2015-03-17 16:49:06', null, null, '2015-03-17 16:49:06', null, null);
INSERT INTO `cloud_web_content` VALUES ('110', '3', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1b04a1a0-e479-4c59-9486-741bc53d26cf,56733743-b886-4a8a-befb-8415e9fe0ce3,2015-1', '关于征集2015年度国家知识产权局审查员专业技术知识��掠胧导�嘌灯笠档耐ㄖ�', null, 'No', '2015-03-17 17:49:14', null, 'NORMAL', '2015-03-17 17:49:14', null, null, '2015-03-17 17:49:14', null, null);
INSERT INTO `cloud_web_content` VALUES ('111', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,1aa6376f-ea23-490d-8730-e2f3320a6c9e,2015-2', '滨江区�硎�015年3月-2015年8月低保、残保困难家庭公示', null, 'No', '2015-03-17 18:49:21', null, 'NORMAL', '2015-03-17 18:49:21', null, null, '2015-03-17 18:49:21', null, null);
INSERT INTO `cloud_web_content` VALUES ('112', '5', '', 'http://www.zjusp.com/display.php?newsId=2067', '“互联网+”时代已来---2015互联网创新论坛', null, 'No', '2015-03-18 09:52:01', null, 'NORMAL', '2015-03-18 09:52:01', null, null, '2015-03-18 09:52:01', null, null);
INSERT INTO `cloud_web_content` VALUES ('113', '5', '', 'http://www.zjusp.com/display.php?newsId=2068', '浙江大学国家大学科技园关于召开2015年日本新技术推介会的通知', null, 'No', '2015-03-18 10:52:09', null, 'NORMAL', '2015-03-18 10:52:09', null, null, '2015-03-18 10:52:09', null, null);
INSERT INTO `cloud_web_content` VALUES ('114', '1', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=4de8023c-7b39-4cdf-8e38-e782f884320f,ad28770c-359f-4d86-83d8-67f5abda5a4d,2015-3', '区四套班子领导学习互联网时代外贸转型之路', null, 'No', '2015-03-18 17:53:02', null, 'NORMAL', '2015-03-18 17:53:02', null, null, '2015-03-18 17:53:02', null, null);
INSERT INTO `cloud_web_content` VALUES ('115', '4', '', 'http://www.zjusp.com/display.php?newsId=2069', '嘉善团县委一行到浙大科技园调研参观', null, 'No', '2015-03-19 09:55:07', null, 'NORMAL', '2015-03-19 09:55:07', null, null, '2015-03-19 09:55:07', null, null);
INSERT INTO `cloud_web_content` VALUES ('116', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,390ebfc9-ad41-40a7-81b5-d55a12f5cb59,2015-3', '转发关于开展2015年国家百千万人才工程人选推荐工作的通知', null, 'No', '2015-03-19 16:56:01', null, 'NORMAL', '2015-03-19 16:56:01', null, null, '2015-03-19 16:56:01', null, null);
INSERT INTO `cloud_web_content` VALUES ('117', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,efb19040-835e-4bb0-8a77-88d6fe16c8be,2015-3', '关于举办区科技金融服务中心政策宣讲会的通知', null, 'No', '2015-03-19 16:56:01', null, 'NORMAL', '2015-03-19 16:56:01', null, null, '2015-03-19 16:56:01', null, null);
INSERT INTO `cloud_web_content` VALUES ('118', '2', '702073', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?TabID=5305a7c0-9139-44eb-ad68-702073366f71&ID=1ac1c414-e1b8-47f4-9238-e04f406f12fc,ad64b641-3e72-42a7-a6fa-06ce8b3064a7,2015-3', '转发市经信委《关于做好杭商学堂2015年科技型初创企业高管研修班学员推荐工作的通知》', null, 'No', '2015-03-19 17:56:09', null, 'NORMAL', '2015-03-19 17:56:09', null, null, '2015-03-19 17:56:09', null, null);
INSERT INTO `cloud_web_content` VALUES ('119', '5', '', 'http://www.zjusp.com/display.php?newsId=2070', '关于登高爬楼梯比赛的通知', null, 'No', '2015-03-20 09:28:36', null, 'NORMAL', '2015-03-20 09:28:36', null, null, '2015-03-20 09:28:36', null, null);

-- ----------------------------
-- Table structure for `cloud_web_info`
-- ----------------------------
DROP TABLE IF EXISTS `cloud_web_info`;
CREATE TABLE `cloud_web_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父节点外键',
  `parent_ids` varchar(255) DEFAULT NULL COMMENT '根目录开始的所有父节点',
  `column_id` bigint(20) DEFAULT NULL COMMENT '栏目外键id',
  `name` varchar(255) DEFAULT NULL COMMENT '网站名称',
  `sub_name` varchar(255) DEFAULT NULL COMMENT '子栏目名称',
  `params` varchar(255) DEFAULT NULL COMMENT '网址参数',
  `url` varchar(255) DEFAULT NULL COMMENT '网址',
  `begin_flag` varchar(255) DEFAULT NULL COMMENT '截取所有内容起始标志',
  `end_flag` varchar(255) DEFAULT NULL COMMENT '截取所有内容结束标志',
  `base_path` varchar(255) DEFAULT NULL COMMENT '首页链接',
  `item_regex` varchar(255) DEFAULT NULL COMMENT '匹配每个内容标志',
  `id_regex` varchar(255) DEFAULT NULL COMMENT '匹配每个内容ID标志',
  `date_regex` varchar(255) DEFAULT NULL COMMENT '匹配每个内容截取时间',
  `date_pattern` varchar(255) DEFAULT NULL COMMENT '日期格式',
  `isCatLog` varchar(50) DEFAULT NULL COMMENT '是否栏目',
  `period` double DEFAULT NULL COMMENT '抓取间隔',
  `entity_status` varchar(255) DEFAULT NULL COMMENT '实体状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier` varchar(255) DEFAULT NULL COMMENT '修改人姓名',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`id`),
  KEY `fk_webInfo_parent` (`parent_id`),
  KEY `fk_columns_parent` (`column_id`),
  CONSTRAINT `fk_columns_parent` FOREIGN KEY (`column_id`) REFERENCES `cloud_column` (`id`),
  CONSTRAINT `fk_webInfo_parent` FOREIGN KEY (`parent_id`) REFERENCES `cloud_web_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cloud_web_info
-- ----------------------------
INSERT INTO `cloud_web_info` VALUES ('1', null, null, '2', '高新区动态', null, '', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?tabid=6e3d5638-a4f4-4ce9-b9bc-a09c593092df', '<table cellspacing=\"0\" cellpadding=\"0\" rules=\"rows\" border=\"0\" id=\"_ctl3_DataGrid2\" style=\"border-width:0px;border-style:None;width:97%;border-collapse:collapse;\">', '<TD align=\"center\" colspan=\"2\" height=\"35px\" valign=\"middle\">', null, '(<a.+?href=([\\\"\\\']))(.*?)(>.*?</a>)', '[0-9]{6}', '[0-9]{4}_[0-9]+_[0-9]+', '', null, null, null, '2013-12-06 00:00:00', null, null, '2015-03-20 14:29:13', null, null);
INSERT INTO `cloud_web_info` VALUES ('2', null, null, '2', '通知公告', null, '', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?tabid=fe3d690e-a6a4-4166-bc4e-e63bd3136c1b', '<table cellspacing=\"0\" cellpadding=\"0\" rules=\"rows\" border=\"0\" id=\"_ctl3_DataGrid2\" style=\"border-width:0px;border-style:None;width:97%;border-collapse:collapse;\">', '<TD align=\"center\" colspan=\"2\" height=\"35px\" valign=\"middle\">', null, '(<a.+?href=([\\\"\\\']))(.*?)(>.*?</a>)', '[0-9]{6}', '[0-9]{4}_[0-9]+_[0-9]+', '', null, null, null, '2013-12-06 00:00:00', null, null, '2015-03-20 14:29:14', null, null);
INSERT INTO `cloud_web_info` VALUES ('3', null, null, '2', '科技信息', null, '', 'http://www.hhtz.gov.cn/hhtz/DesktopDefault.aspx?tabid=9dfda95d-47f2-4530-965a-937ffc528c25', '<table cellspacing=\"0\" cellpadding=\"0\" rules=\"rows\" border=\"0\" id=\"_ctl3_DataGrid2\" style=\"border-width:0px;border-style:None;width:97%;border-collapse:collapse;\">', '<TD align=\"center\" colspan=\"2\" height=\"35px\" valign=\"middle\">', null, '(<a.+?href=([\\\"\\\']))(.*?)(>.*?</a>)', '[0-9]{6}', '[0-9]{4}_[0-9]+_[0-9]+', '', null, null, null, '2013-12-06 00:00:00', null, null, '2015-03-20 14:29:14', null, null);
INSERT INTO `cloud_web_info` VALUES ('4', null, null, '3', '园区动态', null, '', 'http://www.zjusp.com/index_show.php?id=22', '<table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"margin-top:1px;\">', '<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-top:12px;\">', null, '(<a.+?href=([\\\"\\\']))(.*?)(>.*?</a>)', '[0-9]{6}', '[0-9]{4}_[0-9]{2}_[0-9]{2}', '', null, null, null, '2013-12-06 00:00:00', null, null, '2015-03-20 14:29:14', null, null);
INSERT INTO `cloud_web_info` VALUES ('5', null, null, '3', '通知公告(浙大)', null, '', 'http://www.zjusp.com/index_show.php?id=23', '<table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"margin-top:1px;\">', '<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-top:12px;\">', null, '(<a.+?href=([\\\"\\\']))(.*?)(>.*?</a>)', '[0-9]{6}', '[0-9]{4}_[0-9]{2}_[0-9]{2}', '', null, null, null, '2013-12-06 00:00:00', null, null, '2015-03-20 14:29:15', null, null);
INSERT INTO `cloud_web_info` VALUES ('6', null, null, '3', '企业之窗', null, '', 'http://www.zjusp.com/index_show.php?id=56', '<table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"margin-top:1px;\">', '<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-top:12px;\">', null, '(<a.+?href=([\\\"\\\']))(.*?)(>.*?</a>)', '[0-9]{6}', '[0-9]{4}_[0-9]{2}_[0-9]{2}', '', null, null, null, '2013-12-06 00:00:00', null, null, '2015-03-20 14:29:15', null, null);
