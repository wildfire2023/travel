-- MySQL dump 10.13  Distrib 5.7.21, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: travel
-- ------------------------------------------------------
-- Server version	5.7.21-1

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'管理员',1,1,'','','2019-02-21 21:08:21','0.0.0.0'),(2,'用户',2,1,'','','2019-02-21 22:21:39','0.0.0.0');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `username` varchar(40) DEFAULT NULL COMMENT '登录用户名',
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (4,NULL,'55197ba3f8e315c5b80b526037c9210e','hopes','651833918@qq.com','15310614196',1,'编码阶段','','2019-04-27 17:04:55','0.0.0.0'),(9,NULL,'55197ba3f8e315c5b80b526037c9210e','玖歌','993574726@qq.com','15310614197',1,'','','2019-04-27 16:07:13','0.0.0.0'),(10,NULL,'55197ba3f8e315c5b80b526037c9210e','helloworld','bengangxue@163.com','15310614199',1,'','','2019-04-25 17:00:04','0.0.0.0');
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,4,1,'','2019-02-21 21:06:42','0.0.0.0'),(2,9,2,'','2019-03-02 13:50:01','0.0.0.0'),(3,10,2,'','2019-04-25 16:58:28','0.0.0.0');
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
  `parent_id` int(11) DEFAULT '0' COMMENT '父级评论,默认为0表示无父级评论',
  `sys_user_id` int(11) NOT NULL COMMENT '回答者主键',
  `content` text NOT NULL COMMENT '答案内容',
  `content_img_url` varchar(200) DEFAULT '' COMMENT '内容图片',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '回答时间',
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '逻辑删除;1,删除;0,保留',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COMMENT='答案表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `travel_answer`
--

LOCK TABLES `travel_answer` WRITE;
/*!40000 ALTER TABLE `travel_answer` DISABLE KEYS */;
INSERT INTO `travel_answer` VALUES (25,NULL,9,'还不错的样子',NULL,'2019-04-25 16:51:51',0),(36,NULL,10,'表示看不懂，亚历山大',NULL,'2019-04-25 17:03:06',0),(37,NULL,9,'回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答',NULL,'2019-04-25 18:47:00',0),(38,NULL,9,'测试收藏效果',NULL,'2019-04-27 14:13:22',0);
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
  `content` varchar(1000) NOT NULL DEFAULT '' COMMENT '评论内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '逻辑删除;1,删除;0,保留',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COMMENT='评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `travel_comment`
--

LOCK TABLES `travel_comment` WRITE;
/*!40000 ALTER TABLE `travel_comment` DISABLE KEYS */;
INSERT INTO `travel_comment` VALUES (57,NULL,4,'测试评论','2019-04-25 16:19:34',0),(58,NULL,9,'我来回答呀','2019-04-25 16:44:17',0),(59,NULL,10,'嘿嘿嘿','2019-04-25 17:01:17',0),(60,NULL,4,'哈哈哈哈  关注我 不迷路 认准526李先生','2019-04-25 19:32:16',0);
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
  `push_flag` tinyint(2) DEFAULT '0' COMMENT '发表标志;1,发表;0,保留',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COMMENT='游记';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `travel_essay`
--

LOCK TABLES `travel_essay` WRITE;
/*!40000 ALTER TABLE `travel_essay` DISABLE KEYS */;
INSERT INTO `travel_essay` VALUES (46,4,'测试游记删除','http://travel-1252092877.file.myqcloud.com/1556169613654.jpg','测试游记删除',NULL,'2019-04-25 13:20:21',0,1),(47,9,'洪崖洞游玩攻略','http://travel-1252092877.file.myqcloud.com/1556181816382.jpg','这是一篇假游记!',NULL,'2019-04-25 16:43:52',0,1),(58,10,'这是一片测试游记','http://travel-1252092877.file.myqcloud.com/1556182826521.jpg','<p>哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈<img src=\"http://localhost:8080/layuiadmin/layui/images/face/49.gif\" alt=\"[猪头]\"></p>',NULL,'2019-04-25 17:00:43',0,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COMMENT='游记评论关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `travel_essay_comment`
--

LOCK TABLES `travel_essay_comment` WRITE;
/*!40000 ALTER TABLE `travel_essay_comment` DISABLE KEYS */;
INSERT INTO `travel_essay_comment` VALUES (48,46,57),(49,47,58),(50,46,59),(51,46,60);
/*!40000 ALTER TABLE `travel_essay_comment` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COMMENT='问题表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `travel_question`
--

LOCK TABLES `travel_question` WRITE;
/*!40000 ALTER TABLE `travel_question` DISABLE KEYS */;
INSERT INTO `travel_question` VALUES (9,4,'玩儿网站怎么使用','<p>本网站共分三个模块，其一：推荐可以查看列表；游记：用户可以发布自己的游玩攻略，也可以查看其他用户发表的攻略，同时可以发表自己的评论；</p><p>其三：用户可以在本网站提出与游玩相关的问题，其他用户或管理员将给出加大。</p>','2019-04-25 16:18:44',0),(10,9,'提问测试','怎么提问啊','2019-04-25 16:53:52',0),(11,10,'测试问题','测试问题','2019-04-25 17:01:02',0),(12,9,'测试搜索分页','<p>测试搜索分页</p>','2019-04-26 02:06:01',0),(13,9,'测试搜索分页','<p>测试搜索分页</p>','2019-04-26 02:06:01',0),(14,9,'测试搜索分页','<p>测试搜索分页</p>','2019-04-26 02:06:01',0),(15,9,'测试搜索分页','<p>测试搜索分页</p>','2019-04-26 02:06:02',0),(16,9,'测试搜索分页','<p>测试搜索分页</p>','2019-04-26 02:06:02',0),(17,9,'测试搜索分页','<p>测试搜索分页</p>','2019-04-26 02:06:02',0),(18,9,'测试搜索分页','<p>测试搜索分页</p>','2019-04-26 02:06:02',0),(19,9,'测试搜索分页','<p>测试搜索分页</p>','2019-04-26 02:06:02',0),(20,9,'测试搜索分页','<p>测试搜索分页</p>','2019-04-26 02:06:02',0),(21,9,'测试搜索分页','<p>测试搜索分页</p>','2019-04-26 02:06:03',0),(22,9,'测试搜索分页','<p>测试搜索分页</p>','2019-04-26 02:06:03',0);
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
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COMMENT='问题答案关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `travel_question_answer`
--

LOCK TABLES `travel_question_answer` WRITE;
/*!40000 ALTER TABLE `travel_question_answer` DISABLE KEYS */;
INSERT INTO `travel_question_answer` VALUES (22,9,25),(33,9,36),(34,11,37),(35,9,38);
/*!40000 ALTER TABLE `travel_question_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `travel_recommend`
--

DROP TABLE IF EXISTS `travel_recommend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `travel_recommend` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '推荐主键',
  `title` varchar(100) NOT NULL DEFAULT '' COMMENT '推荐标题',
  `head_img_url` varchar(100) NOT NULL DEFAULT '' COMMENT '推荐头图',
  `content` text NOT NULL COMMENT '推荐内容',
  `content_img_url` varchar(200) DEFAULT NULL COMMENT '推荐内容图片',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '推荐发布时间',
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '逻辑删除;1,删除;0,保留',
  `push_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '发表标志;1,发表;0,保留',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COMMENT='推荐';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `travel_recommend`
--

LOCK TABLES `travel_recommend` WRITE;
/*!40000 ALTER TABLE `travel_recommend` DISABLE KEYS */;
INSERT INTO `travel_recommend` VALUES (1,'是是是','试试','三生三世',NULL,'2019-03-23 19:33:01',1,1),(7,'ss','ss','ss',NULL,'2019-03-29 11:16:38',1,1),(38,'洪崖洞','http://travel-1252092877.file.myqcloud.com/1554028725747.jpg','<p><span style=\"text-align: justify;\">洪崖洞位于重庆市核心商圈解放碑沧白路、长江、嘉陵江两江交汇的滨江地带，坐拥城市旅游景观、商务休闲景观和城市人文景观于一体。以具巴渝传统建筑特色的\"吊脚楼\"风貌为主体，依山就势，沿江而建，解放碑直达江滨。</span></p><p><span style=\"text-align: justify;\"><img src=\"http://travel-1252092877.file.myqcloud.com/1554028748099.jpg\"><br></span></p><p style=\"text-align: justify;\"><span class=\"bjh-p\">内部交通：</span></p><p style=\"text-align: justify;\"><span class=\"bjh-p\">公交111、112路外环，114路到洪崖洞站下车，105、111、112内环，小什字站下车后向西北步行400米。</span></p><p style=\"text-align: justify;\"><span class=\"bjh-p\">地铁轻轨2号线临江门站下车后向东北步行500米即到。</span></p><p style=\"text-align: justify;\"><span class=\"bjh-p\"><br></span></p>',NULL,'2019-03-31 18:39:24',0,1),(40,'雪宝山国家森林公园','http://travel-1252092877.file.myqcloud.com/1554662047488.jpg','<dl><p><b><img src=\"http://travel-1252092877.file.myqcloud.com/1554662051259.jpg\"><br></b></p><p><b>简介：</b></p><dd>重庆市开县雪宝山森林公园是国家林业局2002年批准的国家级森林公园，总面积18408公顷。雪宝山森林公园资源种类齐备，品位较高，特色鲜明，功能众多，林海广袤繁盛，10多万亩保存最原始、最完美的中山、亚高山草原南国第一，溪泉瀑布众多，气候独特，人文景观丰富，具有原始、古朴、粗犷、奇异的特点和很高的生态旅游价值。主要以广阔壮美的森林、种类繁多的生物景观为主体、以险峻雄浑的山岳和奇险幽长的峡谷为骨架，以南国最原始优美的中亚草甸景观、神奇多姿的泉瀑天象为特色、兼有多样的文化、古雅的民风，具有立体观光游览、避暑度假游乐、攀岩探险漂流、水上冰雪游玩、科考科普教育等多种功能，是一个生态多样、特色突出的复合型国家级森林公园。</dd></dl><dl><dt><b>开放时间：</b>全天开放</dt></dl><dl><dt><b>门票信息：</b>无需门票。</dt></dl><dl><dt><b>游玩时长：</b>建议1天</dt></dl><dl><dt><b>贴士：</b>由于雪宝山是未开发的景点，所以请做好充足准备再前往。</dt></dl><dl><dt><b>到达方式：</b>其他交通：乘车路线： 重庆汽车站到开县，再从开县中心客运站坐到白泉的车。</dt></dl>',NULL,'2019-04-08 02:34:14',0,1),(42,'解放碑','http://travel-1252092877.file.myqcloud.com/1554662540576.jpg','<div id=\"baike01\" class=\"item-baike\"><h3 class=\"tit\">解放碑概述</h3><div class=\"mdd_text\"><p>&nbsp;&nbsp;&nbsp;&nbsp;解放碑是重庆市的标志建筑物之一，位于重庆市渝中区商业区中心部位，民族路、民权路、邹容路交汇的十字路口处。纪念碑高27.5米，有旋梯可达顶端；碑顶设时钟、方向标志和风速风向仪。 解放碑最初兴建于民国二十九年（1940年）3月12日孙中山逝世纪念日，于民国三十年（1941年）底落成，命名为“精神堡垒”以激励中华民众奋力抗争以取得胜利，抗战胜利后改名为“抗战胜利纪功碑”。</p><p>&nbsp;&nbsp;&nbsp;&nbsp;如今，解放碑一带已经成为重庆市区最繁华的商贸中心地带。这里高楼大厦林立，交通四通八达，百货商店、影剧院、歌舞厅、副食品市场、书店、宾馆、饭店等鳞次栉比，一应俱全，游客至此，既可观光巴渝风物人情，又可品尝地方名特小吃，还可购买纪念品和其他物品。</p></div></div><div id=\"baike02\" class=\"item-baike\"><h3 class=\"tit\"><i></i>气候、最佳旅行时间</h3><div class=\"mdd_text\"><p>四季适宜</p></div></div><div id=\"baike03\" class=\"item-baike\"><h3 class=\"tit\"><i></i>建议游玩天数、地点</h3><div class=\"mdd_text\"><p><span lucida=\"\" microsoft=\"\" hiragino=\"\" sans=\"\" gb=\"\" pingfang=\"\" font-size:=\"\"></span></p><p>好吃街，这里的八一路好吃街，得意好吃街几乎能吃到所有的重庆美食，也有比较平民的重百大楼和太平洋百货，是逛街的好去处。</p><p>建议游玩时间：2-3小时</p></div></div><div id=\"baike07\" class=\"item-baike\"><h3 class=\"tit\"><i></i>门票</h3><div class=\"mdd_text\"><p>免费</p></div></div><div id=\"baike08\" class=\"item-baike\"><h3 class=\"tit\"><i></i>景点开放时间</h3><div class=\"mdd_text\"><p>全天</p></div></div>',NULL,'2019-04-08 02:42:21',0,1),(43,'大足石刻','http://travel-1252092877.file.myqcloud.com/1554662672240.jpg','<div id=\"baike01\" class=\"item-baike\"><div class=\"mdd_text\"><p class=\"p1\">&nbsp; &nbsp; 大足石刻是重庆市大足县境内主要表现为摩崖造像的石窟艺术的总称。包含宝顶山、北山、南山、石篆山、石门山<span class=\"s1\">5</span>处，一般人们所提及的大足石刻大部分都指的是宝顶山石刻。</p><p class=\"p1\">&nbsp;&nbsp;&nbsp;&nbsp;大足石刻其规模宏大，刻艺精湛，内容丰富，具有鲜明的民族特色，具有很高的历史、科学和艺术价值，在我国古代石窟艺术史上占有举足轻重的地位，被国内外誉为神奇的东方艺术明珠</p><p class=\"p1\">&nbsp;&nbsp;&nbsp;&nbsp;关于大足石刻名称的由来有两种说法，一是大足为传统农业区，气候温和，风调雨顺，收成稳定、人民安居乐业<span class=\"s1\"></span>，为大足大丰之地，故得名。还有就是与佛教传说有关，相传释迦牟尼在涅盘前夕曾到过大足，在其讲佛之地留下了一双大脚，故人们叫当地为大足。</p><p class=\"p1\"><br></p><p class=\"p1\"><b>气候、最佳旅行时间</b></p></div></div><div id=\"baike02\" class=\"item-baike\"><div class=\"mdd_text\"><p style=\"text-align: left;\">四季适宜</p><p style=\"text-align: left;\"><br></p></div></div><div id=\"baike03\" class=\"item-baike\"><h3 class=\"tit\"><i></i>建议游玩天数、地点</h3><div class=\"mdd_text\"><p>建议游玩时间：4-5小时</p><p><br></p></div></div><div id=\"baike07\" class=\"item-baike\"><h3 class=\"tit\"><i></i>门票</h3><div class=\"mdd_text\"><p class=\"p1\"><span>淡季（<span class=\"s1\">12</span>月<span class=\"s1\">1</span>日<span class=\"s1\">——</span>次年<span class=\"s1\">2</span>月<span class=\"s1\">28/29</span>日）</span></p><p class=\"p1\">宝顶山石刻门票：人民币<span class=\"s1\">90</span>元</p><p class=\"p1\">圣寿寺门票：人民币<span class=\"s1\">20</span>元</p><p class=\"p1\">宝顶山石刻、圣寿寺联票：人民币<span class=\"s1\">100</span>元</p><p class=\"p1\">宝顶山、北山石刻联票：人民币<span class=\"s1\">120</span>元</p><p class=\"p1\">宝顶山、北山石刻、圣寿寺联票：人民币<span class=\"s1\">130</span>元</p><p class=\"p1\">北山石刻门票：人民币<span class=\"s1\">70</span>元</p><p class=\"p1\"><span>旺季<span class=\"s2\">（</span>3<span class=\"s2\">月</span>1<span class=\"s2\">日</span>——11<span class=\"s2\">月</span>30<span class=\"s2\">日）</span></span><br></p><p class=\"p1\">宝顶山石刻门票：人民币<span class=\"s1\">120</span>元</p><p class=\"p1\">圣寿寺门票：人民币<span class=\"s1\">20</span>元</p><p class=\"p1\">宝顶山石刻、圣寿寺（广大寺）联票：人民币<span class=\"s1\">130</span>元</p><p class=\"p1\">宝顶山、北山石刻联票：人民币<span class=\"s1\">170</span>元</p><p class=\"p1\">宝顶山、北山石刻、圣寿寺（广大寺）联票：人民币<span class=\"s1\">180</span>元</p><p class=\"p1\">北山石刻门票：人民币<span class=\"s1\">90</span>元</p><p class=\"p1\"><br></p></div></div><div id=\"baike08\" class=\"item-baike\"><h3 class=\"tit\"><i></i>景点开放时间</h3><div class=\"mdd_text\"><p class=\"p1\">8:30-18:00 （<span class=\"s1\">16:30</span>停止售票）</p><p class=\"p1\"><br></p></div></div><div id=\"baike09\" class=\"item-baike\"><h3 class=\"tit\"><i></i>电话/网点</h3><div class=\"mdd_text\"><p><a href=\"https://bbs.youxiake.com/mdd/519.html\" target=\"_self\">https://bbs.youxiake.com/mdd/519.html</a></p></div></div>',NULL,'2019-04-08 02:44:49',0,1),(44,'南山风景区','http://travel-1252092877.file.myqcloud.com/1554662971472.jpg','<div id=\"baike01\" class=\"item-baike\"><h3 class=\"tit\">南山风景区概述</h3><div class=\"mdd_text\"><p>&nbsp; 重庆南山风景区位于重庆长江南岸，北起铜锣峡，南至金竹沟，包括汪山、黄山、袁山、蒋山、岱山、老君山、文峰山等数十座山峰。</p><p>&nbsp; 最高峰春天岭海拔681.5米，从渝中区隔江遥看，峰峦叠嶂，沿江列峙，林木联袂，郁郁苍苍，恰似一道拱卫山城的绿色屏障。</p></div></div><div id=\"baike02\" class=\"item-baike\"><h3 class=\"tit\"><i></i>气候、最佳旅行时间</h3><div class=\"mdd_text\"><p>亚热带，四季皆可</p></div></div><div id=\"baike03\" class=\"item-baike\"><h3 class=\"tit\"><i></i>建议游玩天数、地点</h3><div class=\"mdd_text\"><p>南山植物园</p><p>南山植物园位于汪山之上，内有众多的名卉奇草，有上百个品种的杜鹃、一百余个品种的四川茶花，数十个品种的桂花，还有喜马拉雅山的雪松，东南亚的金鸡纳霜，日本的樱花，墨西哥的万寿菊，英国的桂花等品种的花卉。</p><p><br></p><p>重庆抗战遗址</p><p>景区中的黄山现亦被辟为游乐园地。</p><p>这里有重庆沦为陪都时兴建的各类别墅，堪称陪都别墅游览区。内有蒋介石的云岫别墅，宋美龄的松厅，马歇尔的草亭，美国顾问团住宿的莲青楼等。</p><p><br></p><p>涂山寺</p><p>涂山寺坐落在涂山之上，是现存最古老的寺院，因寺内供有尊武祖师，因此又称为尊武寺。涂山寺庙龄悠久，年代悠久已不可考。</p><p>据查，西汉年间为禹王祠、涂后祠、庙宇供奉大禹与涂后的塑像。唐时，任忠州刺史的大诗人白居易曾写有《涂山寺独游》一诗，可见当时此寺已易名为涂山寺。</p><p><br></p><p>老君洞</p><p>所谓“三清”，系指居于三清天、三清境的三位尊神：即居于清微玉清境的元始天尊（亦称天宝君）、居于禹余天上清境的灵宝天尊（亦称太上道君）和居于大赤天太清境的道德天尊（亦称太上老君）。</p><p><br></p><p>老龙洞</p><p>“老龙洞”窟为正方，长宽皆3米许，刻有石龙一躯，龙无角，身躯矫健，昂视长天，前二爪向上急攀岩顶，后爪攫地，势欲凌空，刀法粗犷、线条苍劲。布局构图精巧，雕刻手法或浅浮雕，或深浮雕，或镂空，立体感强。</p><p><br></p><p>3-6小时<br></p></div></div><div id=\"baike07\" class=\"item-baike\"><h3 class=\"tit\"><i></i>门票</h3><div class=\"mdd_text\"><p>南山植物园展览温室50元，大金鹰观景台12元，蔷薇园10元，梅桂园5元，一棵树观景台门票20元。（持学生证半价）</p></div></div><div id=\"baike08\" class=\"item-baike\"><h3 class=\"tit\"><i></i>景点开放时间</h3><div class=\"mdd_text\"><p>08:30-18:00 (1月1日-12月31日 周一-周五)09:00-17:00 (1月1日-12月31日 周六-周日)</p></div></div><div id=\"baike09\" class=\"item-baike\"><h3 class=\"tit\"><i></i>电话/网点</h3><div class=\"mdd_text\"><p><a href=\"https://bbs.youxiake.com/mdd/5396.html\" target=\"_self\">https://bbs.youxiake.com/mdd/5396.html</a></p></div></div>',NULL,'2019-04-08 02:49:34',0,1),(45,'武陵山','http://travel-1252092877.file.myqcloud.com/1554663197240.jpg','<h3>武陵山森林公园旅游简介</h3><div class=\"jj-sum\"><br>武陵山国家森林公园地处重庆市涪陵区国有大木林场，距重庆市区180千米，距涪陵市区64千米、2001年建园，面积为1633．33公顷。海拔1620~980米。 　　<br><br>武陵山国家森林公园已完成公路、宾馆、高山足球场、百鸟园等基础设施和景区建设。武陵山国家森林公园北抵长江、两临乌江，山上森林茂密，峰峦叠峰，具有中国少有的千顷柳杉林之奇、“鸟鸣谷”之幽、“揽月峰”之雄、“千尺崖”之陆、“常春谷”之野。春可赏花，夏可避暑，秋可观果，冬可滑雪，称得上“五步一个景，十步一重大”，林海茫茫，花果累累、奇峰异洞，风光旖旎，令人种往。武陵山国家森林公园风景优美壮丽、划分为6个大只区、30少景点、3个接待中心、5个游乐场所。<br><br>由于该国家森林公园内呈喀斯特地貌，因此，溶洞众多，位于麻柳槽的美人洞非常适宜开展拓展训练和探险。另外，还有龙王洞、通天洞，洞内分布了形状各异的石钟乳，景色非常迷人。武陵山国家森林公园——跑马场武陵山国家森林公园有丰富的旅游资源，且四季分明：春天，万山林木发新芽，漫山遍野，百花争艳，一片生机。夏天，万木争荣武陵山国家森林公园园景，青山拥翠。最热月（8月）平均气温仅19.7℃，是难得的避暑胜地。秋天，葱茏的林海之中镶嵌褐黄色的水杉林，金黄色的银杏林，红色的枫叶，不是花海，却有“雪叶红于二月花”之美。冬天，大雪飞扬，万山林木戴着冰晶雪帽，玉树琼花，银装素裹。 　　<br><br>武陵山国家森林公园核心景区内，曾是香火旺盛的佛教圣地，蕰藏着丰富的佛教文化和巴人文化。地处古栈道地形险要，两边悬崖峭壁，有“一夫当关，万夫莫入”之势，坡陡、险窄，充分体现了“蜀道难，难于上青天”之雄奇。公园东有丰都鬼城，南有武隆仙女山、芙蓉洞，两有南川多佛册，是旅游带的枢纽、游客的必经之路。<br><b><br>公园主要景点</b><br><br>柳树溪：柳树溪发源于千尺崖的红石头峰的东北麓，由于有三条小山梁聚合汇水，故又称“三岔河”。柳树溪自南向北，流入银矿槽。银矿槽比较开阔，平坦，草地如茵，繁花似锦，两面山坡柳杉林浓密滴翠，溪边有柳杉树错落自然。溪流千迥百转，忽而在潭穴中消失，忽而在石上飞溅。银矿槽，柳树溪，融林海、草地、流泉、奇树、怪石于一体，真是“野芳发而幽香，佳木秀而繁荫，水落而石出者，山间之四时也”。漫步于银矿槽，柳树溪，令人心旷神怡。<br><br>桃花潭：桃花潭隐藏于林场场部下方的密林中。潭水清盈，出于一石穴，穴内深广数丈。由于山水常年冲击，形成武陵山国家森林公园口小腹大坛状的水潭。神话传说，有黑龙潜于潭内镇山治水，故又称“黑龙潭”。潭的四周林木苍翠，桃树满坡。春天，桃花盛开，鲜艳美丽，映着一潭碧水。伫立潭边，使人不由想起晋陶渊明在《桃花源记》 中，武陵人初入桃花林的感觉，故称桃花潭。<br><br>千尺崖：（猫儿沟）深入千尺崖底部，只见两面如刀削斧砍的悬崖，怪石嶙峋。沟的最狭处不过数米，堪称“一线天”。放眼千尺崖，沟壑纵横，如蛛网，草木森森，既神秘又幽深，适旅游者探险。<br><br>劈山救父峰：五座相连的山峰屹立在千尺崖中段，站在悬崖上部遥望五座山峰，人们惊奇地发现，在每座山峰的腰部都有一个大大的“缺口”。相传，那是地球神在进行造山运动时，不小心把一个叫宏的天神压在山卜，地球神只好请来天神之子“宇”。“宇”用巨斧把山劈开，救出父亲。故事听起来虽然觉得离奇，但那逼真的大缺口却给人以遐想。<br><br>风动石：（飞来石）由揽月峰下麻柳槽，穿过茂密的柳杉林，箭竹林，便进入崎岖的山路，两旁奇峰林立，山鸣谷应。步上弯弯曲曲的石阶，但见一巨石立于崖顶之上，高10 米有余，底部几乎只有半平方米宽度跟基岩相接，而且，接面是断裂的，不知何年何月自何处飞来。山风吹过，巨石仿佛摇摇欲坠，故取名“风动石”，亦称“飞来石”<br><br>扇子崖：位于千尺崖中上段北侧，峭壁悬崖，像用一垛巨石竖起的“城墙”, 高百丈，如敞开的巨扇，条条“扇骨”武陵山国家森林公园分明，故而得名。扇子崖山势险峻，景色秀丽，有“集华山之险，容黄山之秀”之誉，具有较高观尝价值。游人站在千尺崖上部，放眼眺望，薄雾自沟中升腾，吃动，扇子崖连着沟壑，时隐时现，置身如腾云驾雾，又似隧入万丈深渊，奇幻至极。<br><br>小石林：位于西山中上部，这里石芽、石笋、石峰集中分布，高不过数米，排列如林，形似骆驼，似狮子，似指天宝剑，似神笔插地拟狮象对峙，千奇百怪，称“小石林”。这些石林的特点还在于岩顶上都长有常春藤，有的开花，有的结果，岩石仿佛戴着镶有宝石的绿帽，绿拥着石，石戴着绿，别有一番情趣，具有较高的旅游开发价值。 　　在大木林场场部的柳杉林中亦有一片石笋。柳杉有力的根系，盘结在石缝之中。柳杉高20 余米，直径40 多厘米，高大挺拔，显得生机勃勃，是一个稀有的景点。<br><br>揽月峰：既是公园最高峰，也是涪陵区海拔最高点。不仅山体高耸壮美，而且，当游人沿着山道蜿蜒而上，随着山势变化，步移景异，可观望眼前群山，“横看成岭侧成峰，远近高低各不同”的迥异景象。到得顶峰，宛若在泰山之巅，“会当凌绝项，一览众山小”。浮云掠过，脚下薄雾轻纱，如置身九天之上。<br><br>龙王洞：洞口敞开在西山上部，是一个天然的石灰岩溶洞。据地质资料推算，洞形成于1.8 亿年以前。初步探查，洞体坡位高，岩石稳定，洞深十数米，东西长数百米，最宽处可容纳200--300 人。原先是地下暗河，洞因水转，水因洞迥。暗河消失后，洞内石灰岩水滴，经万年凝聚、连接成为石钟乳，石笋、石柱、石慢，石花、石龟，景观奇特，计有大小景点10 余处，可堪称武陵的“地下艺术长廊，具有较高的旅游开发价值。<br><br>通天洞：洞口位于大木林场场部坡脚下方，洞大幽深。步入洞口，但见奇石林立．石柱、石笋、犬牙交错。入得洞武陵山国家森林公园去，时而攀高，时而钻穴。洞长500 米，洞最高处约有十多米。洞内有暗河、小溪，流水潺潺；洞中有洞，峰上有峰，奇妙无比，真是鬼斧神工，天造地设。通大洞奇妙处还在洞口。因洞口地处低凹，夏秋雨季，常有雨雾笼罩，汇集的雨水从洞顶洒落如银色珠帘。冬日，沟谷中潜水从草莽中渗出，流经洞顶，遇低温，凝成晶莹的冰凌，垂挂于洞口，斜阳之中，光如玉，白如银。<br><br>碑记：这次外业调查考察，在牛皮坝（林场场部）西面的山神庙及农地边发现两块较大的石碑。一块高1.2 米，宽0.6 米，厚0.1 米，字迹模糊，难以辨认记述年代和内容；另外一块高1.3 米，宽0.7 米，厚0.08 米，字迹依稀可辨，记载的是清嘉庆”年间，为重修武陵寺民间捐资的名录，其中捐资最多的为袁氏家族。经过了解，“山神庙”和“武陵寺”于60 年代文化大革命前还存在，后来“破四旧”才被毁的。由此追溯，牛皮坝的过去曾是香火旺盛的佛教圣地，蕴藏有丰富的佛教文化和巴人文化，有待进一步发掘。<br><br>古栈道：过去，山神庙栈道是来往武陵山、龙塘乡、山窝乡游客必经之道。山神庙地形险要，两边悬崖峭壁，大有“一夫当关，万夫莫入”之势。寺庙立于两道石崖之间，用石块铺垫的古栈道自寺庙底下通过，坡陡、险窄、充分体现了“蜀道难，难于上青天”之雄奇。<br><br>“浙江林”纪念塔：为保护长江、黄河流域自然生态环境，党中央提出了天然林保护工程和大力营造生态公益林实施计划。浙江人民为响应号召，向重庆市涪陵区主动捐资造林。造林地便选在大木林场场部对面山坡。经两年营造，而今林木长势良好。为纪念浙江人民积极举动，在植树地方建造了一座纪念塔。塔高9.9 米，刻字17309 个，碑记：滔滔长江截流成库，巍巍高峡乃现平溯，世纪之文，三峡工程缚龙治水利国利民，四海内外华夏儿女为之鼓舞。然经年积月，长江上游生态恶化终酿苦果，以致洪灾扰民，世人莫不为之忧。大力植树造林，防止水土流失，是为确保三峡库区和整个长江流城的长治久安和可持续发展的主要前提条件。办元一九九八年三月，浙江日报社响应江泽民总书记号召，倡议全省人民捐资植树，管造三吹“浙江武陵山国家森林公园林’：为绿化长江三峡尽一份绵薄之力。倡议既出，浙江大地应者如云。东至象山港，南至括苍山，西起开化，北达长兴，地不分东西南北，人不分男女老少，援手者众。此项社会公益活动厉时一年，浙江儿女捐资奉现，植树二万株，三峡“浙江林”始成。长江乃中华民族母亲河，三峡工程利及千秋万代，植树造林，护我中华毋亲河，此乃华复儿女应尽职责。今三峡“浙江林”初植，立碑于此，以晓谕世人。是为记。人们到武陵山森林公园旅游，有幸读一下这篇碑记，将会激起对长江母亲河爱护的无限情怀。<br><br>揽月峰了望台：揽月峰（原名‘大山堡’，以下改称‘揽月峰’）海拔1980 米，山顶稍窄，建于此处的多功能了望台，是一个具有现代气息的仿古三层塔楼。除了了望监视山林火灾外，游人可登塔观光，远眺乌江水色，田园风光，武陵山全貌；近观青松（华山松）绿柳（柳杉），漫漫山花，或者下到电视转播台的休息厅，欣赏音乐电视，喝茶，茗品小憩，十分惬意。<br><br><b>公园住宿：</b>武陵山舒适的星级宾馆、浪漫情侣帐篷、实惠的经济客房。 　　<br>云顶避暑山庄——拥有三人间、标准间、套房等各种房间共55间。室内设备全部按三星级酒店标准　配置。同时，还提供品茶、会议、棋牌娱乐等多种服务。温馨舒适，价格适中。 　　<br>雀巢宾馆——建造在大树上的帐篷宾馆。住在森林中，明月当空，虫鸟伴唱。尽情呼吸新鲜空气，享受大自然的恩宠。&nbsp;<br></div><h3>门票：</h3><div class=\"jj-sum\">成人门票：40元/人	<br>周五-周日需要购买门票，周一-周四免费开放。</div><h3>开放时间：</h3><p><a name=\"menpiao\"></a><span></span><a name=\"kaifang\"></a><span></span></p><div class=\"jj-sum\">全天开放</div>',NULL,'2019-04-08 02:53:20',0,1),(46,'碧潭幽谷','http://travel-1252092877.file.myqcloud.com/1554663378765.jpg','<p pingfang=\"\" background-color:=\"\"><img src=\"http://pavo.elongstatic.com/trip600/000eL8VE.jpg\" title=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\" alt=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\"></p><p pingfang=\"\" background-color:=\"\">碧潭幽谷是金佛山西坡景区通往索道下站中间，从景区大门口坐景交车过去只有几分钟就到峡谷口子，据说，夏天的山谷里有漫山遍野的萤火虫。这几天还有个风铃节，进入栈道口，峡谷的清风吹来，风铃丁零当啷作响，像风在歌唱。</p><p pingfang=\"\" background-color:=\"\"><img src=\"http://pavo.elongstatic.com/trip600/000eL8P5.jpg\" title=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\" alt=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\"></p><p pingfang=\"\" background-color:=\"\">一条栈道沿着溪流蜿蜒而行，向森林茂密的深处，溪水集潭的时候，碧绿幽幽，倒映着青山，像一面自拍的镜子。</p><p pingfang=\"\" background-color:=\"\"><img src=\"http://pavo.elongstatic.com/trip600/000eL8Hy.jpg\" title=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\" alt=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\"></p><p pingfang=\"\" background-color:=\"\">一路上潭池密布，飞泉流瀑众多，溪流如青丝罗带，轻盈地在峰峦幽谷间回环跌落，股股山泉从林中隙间蹦出，纤曲婉转，水韵悠悠；两岸树木葱茏，青藤摇曳，沿路而行，满目青翠，如进入一幽邃的绿港，青岩、绿树与清溪、碧涧相映，构成了一幅天然的山水画，瞬间回归了自然。</p><p pingfang=\"\" background-color:=\"\"><img src=\"http://pavo.elongstatic.com/trip600/000eL8zw.jpg\" title=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\" alt=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\"></p><p pingfang=\"\" background-color:=\"\">怡心亭是一个中途休息的驿站，当我们准备再次起身的时候，突然发现一群猴子挡去了我们的去路，它们正在树梢上飞舞跳跃着打架。胡老师说，慢着，大概是猴子在争夺王位，等它们打完架我们再过去。</p><p pingfang=\"\" background-color:=\"\" text-align:=\"\"><img src=\"http://pavo.elongstatic.com/trip600/000eL8rx.jpg\" title=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\" alt=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\"></p><p pingfang=\"\" background-color:=\"\">这只脸和屁股最红的应该是猴王了，据说猴子群居，每一群猴子都有一个猴王，猴王是年轻的公猴通过与原来的猴王争斗取胜，统领全家的，落败的猴王将独自离开猴群，直到老死消失。动物界一直这样优胜劣汰，看似残忍，却延续了物种的生命力。</p><p pingfang=\"\" background-color:=\"\"><img src=\"http://pavo.elongstatic.com/trip600/000eL8jT.jpg\" title=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\" alt=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\"></p><p pingfang=\"\" background-color:=\"\">当猴子发现我们后，突然安静了下来，其中一只猴子跳到栈道的栏杆上，其余的布局在盖过栈道的树枝上，或蹲或挂，围观着我们。</p><p pingfang=\"\" background-color:=\"\" text-align:=\"\"><img src=\"http://pavo.elongstatic.com/trip600/000eL8eM.jpg\" title=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\" alt=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\"></p><p pingfang=\"\" background-color:=\"\">几个队友尝试着冲过栈道，被一群猴子一阵怒吼，赶了回来。看来此山是它们开，要从这里过，留下买路钱。</p><p pingfang=\"\" background-color:=\"\"><img src=\"http://pavo.elongstatic.com/trip600/000eL875.jpg\" title=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\" alt=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\"></p><p pingfang=\"\" background-color:=\"\">我们继续在怡心亭等待，闫老师和她的闺女走得慢，将到怡心亭的时候被猴王发现，它就守在一堆草木后面，像是要打伏击，于是我们迅速将这个信息传递了给她们，让她们先缓一缓通过。</p><p pingfang=\"\" background-color:=\"\" text-align:=\"\"><img src=\"http://pavo.elongstatic.com/trip600/000eL80d.jpg\" title=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\" alt=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\"></p><p pingfang=\"\" background-color:=\"\">其实猴子是害怕人类对它们有伤害，它们在等待了一段时间发现我们是好人后，逐渐收山回到高处去了，于是我们继续往峡谷深处前行。</p><p pingfang=\"\" background-color:=\"\" text-align:=\"\"><img src=\"http://pavo.elongstatic.com/trip600/000eL7Ss.jpg\" title=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\" alt=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\"></p><p pingfang=\"\" background-color:=\"\">这时峡谷越来越窄，山势越加险峻，植被越来越茂，炙热的阳光或许一天只有一个小时洒进谷底，穿谷的山风透心清凉。特殊的自然环境让这里盛夏没有酷暑之感，胡老师说，在最热的季节，这里的平均气温不会超过20°C。</p><p pingfang=\"\" background-color:=\"\"><img src=\"http://pavo.elongstatic.com/trip600/000eL7JW.jpg\" title=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\" alt=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\"></p><p pingfang=\"\" background-color:=\"\">从溪流的左岸跨过小拱桥到右岸，午后的阳光斜进山壁，“小象”、“尤”和“胖丁”美美地走过，形成碧潭幽谷最美的风景线。</p><p pingfang=\"\" background-color:=\"\"><img src=\"http://pavo.elongstatic.com/trip600/000eL7AM.jpg\" title=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\" alt=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\"></p><p pingfang=\"\" background-color:=\"\">碧潭幽谷</p><p pingfang=\"\" background-color:=\"\" text-align:=\"\"><img src=\"http://pavo.elongstatic.com/trip600/000eL7qC.jpg\" title=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\" alt=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\"></p><p pingfang=\"\" background-color:=\"\">碧潭幽谷分为幽谷听泉、石崖意禅、归去来园、绝壁览胜、地质奇观五个体验段，全长有3.5公里，但我分不清哪一段叫什么了，只觉得越往深处，四周越加原始神秘，奇树仙草、灵崖异石、飞瀑流泉，清幽而静谧。这里的负氧离子含量一定很高，是洗肺、康体、森林SPA的绝佳生态地，观云、戏水、赏石、听泉，一路走来，心旷神怡。</p><p pingfang=\"\" background-color:=\"\"><img src=\"http://pavo.elongstatic.com/trip600/000eL7fZ.jpg\" title=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\" alt=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\"></p><p pingfang=\"\" background-color:=\"\">青岩、绿树与清溪、碧涧相映，构成了一幅天然的山水画，是我们回归自然的理想去处呵。</p><p pingfang=\"\" background-color:=\"\"><img src=\"http://pavo.elongstatic.com/trip600/000eL76h.jpg\" title=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\" alt=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\"></p><p pingfang=\"\" background-color:=\"\">天色向晚的时候，又从右岸回到左岸，应该属于“绝壁览胜”段了吧，胡老师选定了一个夜晚观赏萤火虫的最佳位置，他说，只要等到天黑，这里将出现漫山遍野的迷人“灯光”。</p><p pingfang=\"\" background-color:=\"\"><img src=\"http://pavo.elongstatic.com/trip600/000eL6XM.jpg\" title=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\" alt=\"#夏天的味道#碧潭幽谷寻觅萤火虫，沿途遭猴群收买路钱\"></p><p pingfang=\"\" background-color:=\"\">看着时间还早，我又要下山去天星小镇拍摄滨水泡沫节，便先行离开了。这里只能用队友发来的这个迷人夜晚，安慰一下浪漫的情怀。</p>',NULL,'2019-04-08 02:56:34',0,1);
/*!40000 ALTER TABLE `travel_recommend` ENABLE KEYS */;
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
INSERT INTO `travel_user` VALUES (1,NULL,NULL,NULL,NULL,NULL,'',NULL),(4,'hopes',2,'',NULL,'','http://travel-1252092877.file.myqcloud.com/1554643965021.jpg',NULL),(6,'小武',1,'',NULL,'',NULL,NULL),(9,'玖歌',1,'',NULL,'','http://travel-1252092877.file.myqcloud.com/1556088290568.jpg',NULL),(10,'helloworld',1,'',NULL,'','http://travel-1252092877.file.myqcloud.com/1556182758674.jpg',NULL),(1000,NULL,NULL,NULL,NULL,NULL,'',NULL),(21323,NULL,NULL,NULL,NULL,NULL,'',NULL),(234243,NULL,NULL,NULL,NULL,NULL,'',NULL),(213231111,NULL,NULL,NULL,NULL,NULL,'',NULL);
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

-- Dump completed on 2019-04-27 18:16:39
