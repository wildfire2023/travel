-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: travel
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sys_permission`
--

DROP TABLE IF EXISTS `sys_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限主键',
  `code` varchar(20) NOT NULL DEFAULT '' COMMENT '权限码',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '权限名',
  `url` varchar(100) NOT NULL DEFAULT '' COMMENT '权限URL',
  `type` tinyint(2) NOT NULL DEFAULT '2' COMMENT '类型: 1,菜单;2,按钮',
  `status` tinyint(2) DEFAULT '1' COMMENT '用户状态: 1,正常;2,冻结',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `operator` varchar(20) DEFAULT '' COMMENT '操作人',
  `operator_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `operator_ip` varchar(16) DEFAULT '0.0.0.0' COMMENT '操作人ip地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_permission`
--

LOCK TABLES `sys_permission` WRITE;
/*!40000 ALTER TABLE `sys_permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色主键',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '角色名',
  `type` tinyint(2) NOT NULL DEFAULT '2' COMMENT '角色类型: 1,管理员;2,用户',
  `status` tinyint(2) DEFAULT '1' COMMENT '用户状态: 1,正常;2,冻结',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `operator` varchar(20) DEFAULT '' COMMENT '操作人',
  `operator_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `operator_ip` varchar(16) DEFAULT '0.0.0.0' COMMENT '操作人ip地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_permission`
--

DROP TABLE IF EXISTS `sys_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色-权限主键',
  `role_id` int(11) NOT NULL COMMENT '角色主键',
  `permission_id` int(11) NOT NULL COMMENT '权限主键',
  `operator` varchar(20) DEFAULT '' COMMENT '操作人',
  `operator_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `operator_ip` varchar(16) DEFAULT '0.0.0.0' COMMENT '操作人ip地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_permission`
--

LOCK TABLES `sys_role_permission` WRITE;
/*!40000 ALTER TABLE `sys_role_permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `username` varchar(40) NOT NULL COMMENT '登录用户名',
  `password` char(32) NOT NULL COMMENT '用户密码',
  `nickname` varchar(20) DEFAULT '' COMMENT '用户昵称',
  `email` varchar(40) DEFAULT '' COMMENT '用户邮箱',
  `phone` char(11) DEFAULT '' COMMENT '用户手机号',
  `status` tinyint(2) DEFAULT '1' COMMENT '用户状态: 1,正常;2,冻结',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `operator` varchar(20) DEFAULT '' COMMENT '操作人',
  `operator_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `operator_ip` varchar(16) DEFAULT '0.0.0.0' COMMENT '操作人ip地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户-角色主键',
  `user_id` int(11) NOT NULL COMMENT '用户主键',
  `role_id` int(11) NOT NULL COMMENT '角色主键',
  `operator` varchar(20) DEFAULT '' COMMENT '操作人',
  `operator_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `operator_ip` varchar(16) DEFAULT '0.0.0.0' COMMENT '操作人ip地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `travel_answer`
--

DROP TABLE IF EXISTS `travel_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `travel_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '答案主键',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父级评论,默认为0表示无父级评论',
  `sys_user_id` int(11) NOT NULL COMMENT '回答者主键',
  `content` text NOT NULL COMMENT '答案内容',
  `content_img_url` varchar(200) DEFAULT '' COMMENT '内容图片',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '回答时间',
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '逻辑删除;1,删除;0,保留',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='答案表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `travel_answer`
--

LOCK TABLES `travel_answer` WRITE;
/*!40000 ALTER TABLE `travel_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `travel_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `travel_comment`
--

DROP TABLE IF EXISTS `travel_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `travel_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论主键',
  `parent_id` int(11) DEFAULT '0' COMMENT '父级评论,默认为0表示无父级评论',
  `sys_user_id` int(11) NOT NULL COMMENT '评论者主键',
  `content` varchar(200) NOT NULL DEFAULT '' COMMENT '评论内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '逻辑删除;1,删除;0,保留',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `travel_comment`
--

LOCK TABLES `travel_comment` WRITE;
/*!40000 ALTER TABLE `travel_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `travel_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `travel_essay`
--

DROP TABLE IF EXISTS `travel_essay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `travel_essay` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '游记主键',
  `sys_user_id` int(11) NOT NULL COMMENT '游记发布者主键',
  `title` varchar(100) NOT NULL DEFAULT '' COMMENT '游记标题',
  `head_img_url` varchar(100) NOT NULL DEFAULT '' COMMENT '游记头图',
  `content` text NOT NULL COMMENT '游记内容',
  `content_img_url` varchar(200) DEFAULT NULL COMMENT '游记内容图片',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '游记发布时间',
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '逻辑删除;1,删除;0,保留',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='游记';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `travel_essay`
--

LOCK TABLES `travel_essay` WRITE;
/*!40000 ALTER TABLE `travel_essay` DISABLE KEYS */;
/*!40000 ALTER TABLE `travel_essay` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `travel_essay_comment`
--

DROP TABLE IF EXISTS `travel_essay_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `travel_essay_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '游记评论主键',
  `travel_essay_id` int(11) NOT NULL COMMENT '游记主键',
  `travel_comment_id` int(11) NOT NULL COMMENT '评论主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='游记评论关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `travel_essay_comment`
--

LOCK TABLES `travel_essay_comment` WRITE;
/*!40000 ALTER TABLE `travel_essay_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `travel_essay_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `travel_link`
--

DROP TABLE IF EXISTS `travel_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `travel_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '友链主键',
  `title` varchar(100) NOT NULL DEFAULT '' COMMENT '友链标题',
  `img_url` varchar(100) DEFAULT NULL COMMENT '友链图片',
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '逻辑删除;1,删除;0,保留',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='友链表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `travel_link`
--

LOCK TABLES `travel_link` WRITE;
/*!40000 ALTER TABLE `travel_link` DISABLE KEYS */;
/*!40000 ALTER TABLE `travel_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `travel_question`
--

DROP TABLE IF EXISTS `travel_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `travel_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '问题主键',
  `sys_user_id` int(11) NOT NULL COMMENT '问题者主键',
  `title` varchar(100) NOT NULL COMMENT '问题标题',
  `content` text NOT NULL COMMENT '问题内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '问题发布时间',
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '逻辑删除;1,删除;0,保留',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='问题表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `travel_question`
--

LOCK TABLES `travel_question` WRITE;
/*!40000 ALTER TABLE `travel_question` DISABLE KEYS */;
/*!40000 ALTER TABLE `travel_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `travel_question_answer`
--

DROP TABLE IF EXISTS `travel_question_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `travel_question_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '问题答案主键',
  `travel_question_id` int(11) NOT NULL COMMENT '问题主键',
  `travel_answer_id` int(11) NOT NULL COMMENT '答案主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='问题答案关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `travel_question_answer`
--

LOCK TABLES `travel_question_answer` WRITE;
/*!40000 ALTER TABLE `travel_question_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `travel_question_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `travel_user`
--

DROP TABLE IF EXISTS `travel_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `travel_user` (
  `sys_user_id` int(11) NOT NULL COMMENT '系统用户主键',
  `nickname` varchar(20) DEFAULT '' COMMENT '用户昵称',
  `sex` tinyint(2) DEFAULT '0' COMMENT '用户性别; 1,男;2,女',
  `city` varchar(20) DEFAULT '' COMMENT '用户居住城市',
  `birth` datetime DEFAULT NULL COMMENT '出生日期',
  `intro` varchar(200) DEFAULT '' COMMENT '个人简介',
  `img_url` varchar(100) DEFAULT NULL COMMENT '个人头像',
  `cover` varchar(100) DEFAULT NULL COMMENT '个人封面',
  PRIMARY KEY (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='业务用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `travel_user`
--

LOCK TABLES `travel_user` WRITE;
/*!40000 ALTER TABLE `travel_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `travel_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-23  1:21:08
