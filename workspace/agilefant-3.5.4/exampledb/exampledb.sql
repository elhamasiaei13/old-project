-- MySQL dump 10.13  Distrib 5.5.31, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: example20131105
-- ------------------------------------------------------
-- Server version	5.5.31-0ubuntu0.12.04.1

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
-- Table structure for table `Holiday`
--

DROP TABLE IF EXISTS `Holiday`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Holiday` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `endDate` datetime DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA44DC038C1610AD2` (`user_id`),
  CONSTRAINT `FKA44DC038C1610AD2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Holiday`
--

LOCK TABLES `Holiday` WRITE;
/*!40000 ALTER TABLE `Holiday` DISABLE KEYS */;
/*!40000 ALTER TABLE `Holiday` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `HolidayAnomaly`
--

DROP TABLE IF EXISTS `HolidayAnomaly`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HolidayAnomaly` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK267567ABC1610AD2` (`user_id`),
  CONSTRAINT `FK267567ABC1610AD2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HolidayAnomaly`
--

LOCK TABLES `HolidayAnomaly` WRITE;
/*!40000 ALTER TABLE `HolidayAnomaly` DISABLE KEYS */;
/*!40000 ALTER TABLE `HolidayAnomaly` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `agilefant_revisions`
--

DROP TABLE IF EXISTS `agilefant_revisions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `agilefant_revisions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `userId` int(11) NOT NULL,
  `userName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agilefant_revisions`
--

LOCK TABLES `agilefant_revisions` WRITE;
/*!40000 ALTER TABLE `agilefant_revisions` DISABLE KEYS */;
INSERT INTO `agilefant_revisions` VALUES (12,1383652852028,3,'Administrator');
/*!40000 ALTER TABLE `agilefant_revisions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assignment`
--

DROP TABLE IF EXISTS `assignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assignment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `availability` int(11) NOT NULL,
  `delta_personal_load` bigint(20) DEFAULT NULL,
  `backlog_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3D2B86CDF8762ABE` (`backlog_id`),
  KEY `FK3D2B86CDE537EC82` (`backlog_id`),
  KEY `FK3D2B86CDC1610AD2` (`user_id`),
  KEY `FK3D2B86CDF63400A2` (`backlog_id`),
  CONSTRAINT `FK3D2B86CDF63400A2` FOREIGN KEY (`backlog_id`) REFERENCES `backlogs` (`id`),
  CONSTRAINT `FK3D2B86CDC1610AD2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK3D2B86CDE537EC82` FOREIGN KEY (`backlog_id`) REFERENCES `backlogs` (`id`),
  CONSTRAINT `FK3D2B86CDF8762ABE` FOREIGN KEY (`backlog_id`) REFERENCES `backlogs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assignment`
--

LOCK TABLES `assignment` WRITE;
/*!40000 ALTER TABLE `assignment` DISABLE KEYS */;
INSERT INTO `assignment` VALUES (1,100,0,6,5),(2,100,0,7,5);
/*!40000 ALTER TABLE `assignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assignment_AUD`
--

DROP TABLE IF EXISTS `assignment_AUD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assignment_AUD` (
  `id` int(11) NOT NULL,
  `REV` int(11) NOT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  `availability` int(11) DEFAULT NULL,
  `delta_personal_load` bigint(20) DEFAULT NULL,
  `backlog_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`REV`),
  KEY `FK1B6D861E20258526` (`REV`),
  CONSTRAINT `FK1B6D861E20258526` FOREIGN KEY (`REV`) REFERENCES `agilefant_revisions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assignment_AUD`
--

LOCK TABLES `assignment_AUD` WRITE;
/*!40000 ALTER TABLE `assignment_AUD` DISABLE KEYS */;
INSERT INTO `assignment_AUD` VALUES (1,12,0,100,0,6,5),(2,12,0,100,0,7,5);
/*!40000 ALTER TABLE `assignment_AUD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `backlogs`
--

DROP TABLE IF EXISTS `backlogs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `backlogs` (
  `backlogtype` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` longtext,
  `name` varchar(255) DEFAULT NULL,
  `backlogSize` bigint(20) DEFAULT NULL,
  `baselineLoad` bigint(20) DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `readonlyToken` varchar(255) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `readonlyToken` (`readonlyToken`),
  KEY `FK7E75FF16B8A85DD5` (`parent_id`),
  CONSTRAINT `FK7E75FF16B8A85DD5` FOREIGN KEY (`parent_id`) REFERENCES `backlogs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `backlogs`
--

LOCK TABLES `backlogs` WRITE;
/*!40000 ALTER TABLE `backlogs` DISABLE KEYS */;
INSERT INTO `backlogs` VALUES ('Product',5,'&nbsp;<br><b>Welcome to your Agilefant account!</b><br><br>This is a <b>Product. </b>Products<b>&nbsp;</b>are developed in <b>projects</b>, <br>which in turn can further be split into <b>iterations <br></b>(in Scrum, iterations are called \"sprints\").&nbsp;<br><br><div>On the <b>story tree</b>&nbsp;tab below you can see how the steps <br>for developing the Example product have been refined into <br>iteration-sized\n chunks. On the <b>Project planning</b>&nbsp;tab, <br>you can drag n\' drop the leaf stories (that is, stories that have <br>no children) into projects and iterations. And on the <b>projects <br></b>tab, you can create stories, set their status, assign people to <br>them and modify the projects\' start and end dates.<br><div><br><b>To get instant help, you chat with us (look left and down for the chat box)!&nbsp;</b><br>And, if you want to get rid of all example data, just click Actions -&gt; Delete <br>for the Example Product of the backlogs.<br></div><div><br></div><div><u>For more info on Agilefant, see:</u><br></div><div>+ The <a href=\"http://agilefant.org/support/user-guide/\">User Guide</a></div><div>+ The <a href=\"http://agilefant.org/support/faq/\">FAQ</a> - details regarding specific features<br><br><u>For in-depth info on the principles behind Agilefant and their origin, see:</u><br>+ <a href=\"http://www.soberit.hut.fi/%7Ejvahanii/\">Dr. Agilefant\'s</a> Ph.D.: \"<a href=\"http://lib.tkk.fi/Diss/2012/isbn9789526045061/isbn9789526045061.pdf\">Towards Agile Product and Portfolio Management</a>\"<br></div></div>','Example product',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('Project',6,'&nbsp;<br>This is an example <b>project</b>.&nbsp;Think of&nbsp;Projects as corresponding <br>to a major release or an otherwise significant undertaking from a <br>business point of view. If you\'re familiar with Dean Leffingwell\'s <br><a href=\"http://scaledagileframework.com/\">scaled agile framework</a>, the project level in Agilefant corresponds to <br>\"<a href=\"http://www.scaledagileframework.com/program-level/\">program</a>\".<br><br>The <b>Story tree</b>&nbsp;tab works just like on the \nProduct level, with the <br>exception that it only displays those \nstories (and their parent stories) <br>that have been scheduled to be developed \nin this project.<br><br>On the&nbsp;<b>Leaf stories&nbsp;</b>tab, you can prioritize the leaf stories against <br>each other using a force-ranked list. You can also move them <br>into and out of <b>iterations</b>.<br><br>On the <b>iterations</b> tab you can see a single iteration. If you have<br>many teams working in your project, you\'ll want to create<br>an iteration for each of them.<div><br><div><div><b>Planned size</b> and <b>baseline load</b> are <br>explained in the <a href=\"http://agilefant.org/support/faq/\">FAQ</a>. Look at the <a href=\"http://agilefant.org/support/user-guide/\">user guide</a> to <br>see how the <b>project burn-up</b> works.</div></div></div>','Example project',30000,120,'2014-02-05 13:37:47',NULL,'2013-11-05 13:37:47',1,0,5),('Iteration',7,'&nbsp;<br>This is an iteration. Here you can see the <b>Stories</b> planned <br>for this iteration as a prioritised list.<br><br>Only the first story (\"left rear wheel\") has <br>been split into <b>tasks</b>&nbsp;(click on the plus to open see them) <br>which have been estimated in man-hours.<br><br>Go ahead and open the second story, <br>and give the tasks effort estimates! <br>You can see how the burndown reacts.<br><br>You can also have tasks that do not belong to any story.<br><br>(<b>Planned size</b> and <b>baseline load</b> are explained in the <a href=\"http://agilefant.org/support/faq/\">FAQ</a>.)','Example iteration',0,NULL,'2013-12-03 13:37:47',NULL,'2013-11-05 13:37:47',NULL,NULL,6);
/*!40000 ALTER TABLE `backlogs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `backlogs_AUD`
--

DROP TABLE IF EXISTS `backlogs_AUD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `backlogs_AUD` (
  `backlogtype` varchar(31) NOT NULL,
  `id` int(11) NOT NULL,
  `REV` int(11) NOT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  `description` longtext,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `backlogSize` bigint(20) DEFAULT NULL,
  `baselineLoad` bigint(20) DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`REV`),
  KEY `FK46C0B1E720258526` (`REV`),
  CONSTRAINT `FK46C0B1E720258526` FOREIGN KEY (`REV`) REFERENCES `agilefant_revisions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `backlogs_AUD`
--

LOCK TABLES `backlogs_AUD` WRITE;
/*!40000 ALTER TABLE `backlogs_AUD` DISABLE KEYS */;
INSERT INTO `backlogs_AUD` VALUES ('Product',5,12,0,'&nbsp;<br><b>Welcome to your Agilefant account!</b><br><br>This is a <b>Product. </b>Products<b>&nbsp;</b>are developed in <b>projects</b>, <br>which in turn can further be split into <b>iterations <br></b>(in Scrum, iterations are called \"sprints\").&nbsp;<br><br><div>On the <b>story tree</b>&nbsp;tab below you can see how the steps <br>for developing the Example product have been refined into <br>iteration-sized\n chunks. On the <b>Project planning</b>&nbsp;tab, <br>you can drag n\' drop the leaf stories (that is, stories that have <br>no children) into projects and iterations. And on the <b>projects <br></b>tab, you can create stories, set their status, assign people to <br>them and modify the projects\' start and end dates.<br><div><br><b>To get instant help, you chat with us (look left and down for the chat box)!&nbsp;</b><br>And, if you want to get rid of all example data, just click Actions -&gt; Delete <br>for the Example Product of the backlogs.<br></div><div><br></div><div><u>For more info on Agilefant, see:</u><br></div><div>+ The <a href=\"http://agilefant.org/support/user-guide/\">User Guide</a></div><div>+ The <a href=\"http://agilefant.org/support/faq/\">FAQ</a> - details regarding specific features<br><br><u>For in-depth info on the principles behind Agilefant and their origin, see:</u><br>+ <a href=\"http://www.soberit.hut.fi/%7Ejvahanii/\">Dr. Agilefant\'s</a> Ph.D.: \"<a href=\"http://lib.tkk.fi/Diss/2012/isbn9789526045061/isbn9789526045061.pdf\">Towards Agile Product and Portfolio Management</a>\"<br></div></div>','Example product',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('Project',6,12,0,'&nbsp;<br>This is an example <b>project</b>.&nbsp;Think of&nbsp;Projects as corresponding <br>to a major release or an otherwise significant undertaking from a <br>business point of view. If you\'re familiar with Dean Leffingwell\'s <br><a href=\"http://scaledagileframework.com/\">scaled agile framework</a>, the project level in Agilefant corresponds to <br>\"<a href=\"http://www.scaledagileframework.com/program-level/\">program</a>\".<br><br>The <b>Story tree</b>&nbsp;tab works just like on the \nProduct level, with the <br>exception that it only displays those \nstories (and their parent stories) <br>that have been scheduled to be developed \nin this project.<br><br>On the&nbsp;<b>Leaf stories&nbsp;</b>tab, you can prioritize the leaf stories against <br>each other using a force-ranked list. You can also move them <br>into and out of <b>iterations</b>.<br><br>On the <b>iterations</b> tab you can see a single iteration. If you have<br>many teams working in your project, you\'ll want to create<br>an iteration for each of them.<div><br><div><div><b>Planned size</b> and <b>baseline load</b> are <br>explained in the <a href=\"http://agilefant.org/support/faq/\">FAQ</a>. Look at the <a href=\"http://agilefant.org/support/user-guide/\">user guide</a> to <br>see how the <b>project burn-up</b> works.</div></div></div>','Example project',5,30000,120,'2014-02-05 13:37:47',1,'2013-11-05 13:37:47',0),('Iteration',7,12,0,'&nbsp;<br>This is an iteration. Here you can see the <b>Stories</b> planned <br>for this iteration as a prioritised list.<br><br>Only the first story (\"left rear wheel\") has <br>been split into <b>tasks</b>&nbsp;(click on the plus to open see them) <br>which have been estimated in man-hours.<br><br>Go ahead and open the second story, <br>and give the tasks effort estimates! <br>You can see how the burndown reacts.<br><br>You can also have tasks that do not belong to any story.<br><br>(<b>Planned size</b> and <b>baseline load</b> are explained in the <a href=\"http://agilefant.org/support/faq/\">FAQ</a>.)','Example iteration',6,0,NULL,'2013-12-03 13:37:47',NULL,'2013-11-05 13:37:47',NULL);
/*!40000 ALTER TABLE `backlogs_AUD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history_backlogs`
--

DROP TABLE IF EXISTS `history_backlogs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `history_backlogs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `branchMax` bigint(20) NOT NULL,
  `doneSum` bigint(20) NOT NULL,
  `estimateSum` bigint(20) NOT NULL,
  `rootSum` bigint(20) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `backlog_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA6B13261F8762ABE` (`backlog_id`),
  CONSTRAINT `FKA6B13261F8762ABE` FOREIGN KEY (`backlog_id`) REFERENCES `backlogs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history_backlogs`
--

LOCK TABLES `history_backlogs` WRITE;
/*!40000 ALTER TABLE `history_backlogs` DISABLE KEYS */;
INSERT INTO `history_backlogs` VALUES (2,48,0,48,48,'2013-09-05 13:45:23',6),(3,55,0,55,55,'2013-10-11 14:38:23',6);
/*!40000 ALTER TABLE `history_backlogs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history_iterations`
--

DROP TABLE IF EXISTS `history_iterations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `history_iterations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deltaOriginalEstimate` bigint(20) NOT NULL,
  `effortLeftSum` bigint(20) NOT NULL,
  `originalEstimateSum` bigint(20) NOT NULL,
  `timestamp` date NOT NULL,
  `iteration_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `iteration_id` (`iteration_id`,`timestamp`),
  KEY `FK9DD479C14157D2A2` (`iteration_id`),
  CONSTRAINT `FK9DD479C14157D2A2` FOREIGN KEY (`iteration_id`) REFERENCES `backlogs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history_iterations`
--

LOCK TABLES `history_iterations` WRITE;
/*!40000 ALTER TABLE `history_iterations` DISABLE KEYS */;
INSERT INTO `history_iterations` VALUES (2,1320,1140,1320,'2013-09-05',7);
/*!40000 ALTER TABLE `history_iterations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hourentries`
--

DROP TABLE IF EXISTS `hourentries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hourentries` (
  `DTYPE` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `description` longtext,
  `minutesSpent` bigint(20) NOT NULL,
  `user_id` int(11) NOT NULL,
  `backlog_id` int(11) DEFAULT NULL,
  `story_id` int(11) DEFAULT NULL,
  `task_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKF6FE62CE0E4BFA2` (`story_id`),
  KEY `FKF6FE62CF63400A2` (`backlog_id`),
  KEY `FKF6FE62CC1610AD2` (`user_id`),
  KEY `FKF6FE62C6E84F892` (`task_id`),
  CONSTRAINT `FKF6FE62C6E84F892` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`),
  CONSTRAINT `FKF6FE62CC1610AD2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKF6FE62CE0E4BFA2` FOREIGN KEY (`story_id`) REFERENCES `stories` (`id`),
  CONSTRAINT `FKF6FE62CF63400A2` FOREIGN KEY (`backlog_id`) REFERENCES `backlogs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hourentries`
--

LOCK TABLES `hourentries` WRITE;
/*!40000 ALTER TABLE `hourentries` DISABLE KEYS */;
INSERT INTO `hourentries` VALUES ('TaskHourEntry',1,'2013-09-05 14:12:00','',120,5,NULL,NULL,11);
/*!40000 ALTER TABLE `hourentries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `labels`
--

DROP TABLE IF EXISTS `labels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `labels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `displayName` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `creator_id` int(11) DEFAULT NULL,
  `story_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `label_name` (`name`),
  KEY `FKBDD05FFFE0E4BFA2` (`story_id`),
  KEY `FKBDD05FFF1C5D0ED1` (`creator_id`),
  CONSTRAINT `FKBDD05FFF1C5D0ED1` FOREIGN KEY (`creator_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKBDD05FFFE0E4BFA2` FOREIGN KEY (`story_id`) REFERENCES `stories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `labels`
--

LOCK TABLES `labels` WRITE;
/*!40000 ALTER TABLE `labels` DISABLE KEYS */;
/*!40000 ALTER TABLE `labels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logins`
--

DROP TABLE IF EXISTS `logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logins` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4B213410C1610AD4` (`user_id`),
  CONSTRAINT `FK4B213410C1610AD4` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logins`
--

LOCK TABLES `logins` WRITE;
/*!40000 ALTER TABLE `logins` DISABLE KEYS */;
/*!40000 ALTER TABLE `logins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schema_version`
--

DROP TABLE IF EXISTS `schema_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schema_version` (
  `version_rank` int(11) NOT NULL,
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) NOT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(30) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  KEY `schema_version_vr_idx` (`version_rank`),
  KEY `schema_version_ir_idx` (`installed_rank`),
  KEY `schema_version_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schema_version`
--

LOCK TABLES `schema_version` WRITE;
/*!40000 ALTER TABLE `schema_version` DISABLE KEYS */;
INSERT INTO `schema_version` VALUES (1,1,'0','Initial version','SQL','V0__Initial_version.sql',-1085754636,'agilefant','2013-11-05 11:50:41',791,1),(2,2,'1','updated version','SQL','V1__updated_version.sql',-733178282,'agilefant','2013-11-05 11:50:41',64,1);
/*!40000 ALTER TABLE `schema_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settings`
--

DROP TABLE IF EXISTS `settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings`
--

LOCK TABLES `settings` WRITE;
/*!40000 ALTER TABLE `settings` DISABLE KEYS */;
INSERT INTO `settings` VALUES (13,'Agilefant database version','AgilefantDatabaseVersion','304'),(14,NULL,'branchMetricsType','both'),(15,NULL,'CriticalLow','100'),(16,NULL,'DailyWork','true'),(17,NULL,'DevDashboard','true'),(18,NULL,'DevPortfolio','true'),(19,NULL,'HourReporting','true'),(20,NULL,'labelsInStoryList','true'),(21,NULL,'OptimalHigh','85'),(22,NULL,'OptimalLow','70'),(23,NULL,'RangeHigh','120'),(24,NULL,'RangeLow','0'),(25,NULL,'StoryTreeFieldOrder','state,storyPoints,labels,name,backlog,breadcrumb'),(26,NULL,'weekendsInBurndown','true');
/*!40000 ALTER TABLE `settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stories`
--

DROP TABLE IF EXISTS `stories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` longtext,
  `name` varchar(255) NOT NULL,
  `state` int(11) NOT NULL,
  `storyPoints` int(11) DEFAULT NULL,
  `storyValue` int(11) DEFAULT NULL,
  `treeRank` int(11) NOT NULL DEFAULT '0',
  `backlog_id` int(11) DEFAULT NULL,
  `iteration_id` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8FB060534157D2A2` (`iteration_id`),
  KEY `FK8FB06053F63400A2` (`backlog_id`),
  KEY `FK8FB06053F5E897CD` (`parent_id`),
  CONSTRAINT `FK8FB06053F5E897CD` FOREIGN KEY (`parent_id`) REFERENCES `stories` (`id`),
  CONSTRAINT `FK8FB060534157D2A2` FOREIGN KEY (`iteration_id`) REFERENCES `backlogs` (`id`),
  CONSTRAINT `FK8FB06053F63400A2` FOREIGN KEY (`backlog_id`) REFERENCES `backlogs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stories`
--

LOCK TABLES `stories` WRITE;
/*!40000 ALTER TABLE `stories` DISABLE KEYS */;
INSERT INTO `stories` VALUES (3,NULL,'The first zero-point energy car on the market',1,NULL,NULL,1,5,NULL,NULL),(4,NULL,'Seats',1,5,NULL,5,5,NULL,3),(5,NULL,'Bodywork',0,10,NULL,4,6,NULL,3),(6,NULL,'Wheels',1,NULL,NULL,2,5,NULL,3),(7,NULL,'Right rear wheel',0,2,NULL,3,6,NULL,6),(8,NULL,'Left front wheel',0,2,NULL,0,6,7,6),(9,NULL,'Right front wheel',0,2,NULL,2,6,NULL,6),(10,NULL,'Engine',0,NULL,NULL,1,5,NULL,3),(11,NULL,'Electrical',0,10,NULL,1,6,NULL,10),(12,NULL,'Combustion',0,5,NULL,2,6,NULL,10),(13,NULL,'Fuel containers',1,NULL,NULL,3,5,NULL,3),(14,NULL,'Accumulator',0,4,NULL,2,6,NULL,13),(15,NULL,'Gasoline tank',1,3,NULL,1,6,NULL,13),(16,NULL,'Paint that glows in the dark',0,10,NULL,0,5,NULL,3),(17,NULL,'Zero-point energy warp generator',0,20,NULL,0,5,NULL,10),(18,NULL,'Left rear wheel',1,2,NULL,1,6,7,6),(19,NULL,'Antimatter capsule',0,15,NULL,0,6,NULL,13);
/*!40000 ALTER TABLE `stories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stories_AUD`
--

DROP TABLE IF EXISTS `stories_AUD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stories_AUD` (
  `id` int(11) NOT NULL,
  `REV` int(11) NOT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  `description` longtext,
  `name` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `storyPoints` int(11) DEFAULT NULL,
  `storyValue` int(11) DEFAULT NULL,
  `treeRank` int(11) DEFAULT '0',
  `backlog_id` int(11) DEFAULT NULL,
  `iteration_id` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`REV`),
  KEY `FK853E2CA420258526` (`REV`),
  CONSTRAINT `FK853E2CA420258526` FOREIGN KEY (`REV`) REFERENCES `agilefant_revisions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stories_AUD`
--

LOCK TABLES `stories_AUD` WRITE;
/*!40000 ALTER TABLE `stories_AUD` DISABLE KEYS */;
INSERT INTO `stories_AUD` VALUES (3,12,0,NULL,'The first zero-point energy car on the market',1,NULL,NULL,1,5,NULL,NULL),(4,12,0,NULL,'Seats',1,5,NULL,5,5,NULL,3),(5,12,0,NULL,'Bodywork',0,10,NULL,4,6,NULL,3),(6,12,0,NULL,'Wheels',1,NULL,NULL,2,5,NULL,3),(7,12,0,NULL,'Right rear wheel',0,2,NULL,3,6,NULL,6),(8,12,0,NULL,'Left front wheel',0,2,NULL,0,6,7,6),(9,12,0,NULL,'Right front wheel',0,2,NULL,2,6,NULL,6),(10,12,0,NULL,'Engine',0,NULL,NULL,1,5,NULL,3),(11,12,0,NULL,'Electrical',0,10,NULL,1,6,NULL,10),(12,12,0,NULL,'Combustion',0,5,NULL,2,6,NULL,10),(13,12,0,NULL,'Fuel containers',1,NULL,NULL,3,5,NULL,3),(14,12,0,NULL,'Accumulator',0,4,NULL,2,6,NULL,13),(15,12,0,NULL,'Gasoline tank',1,3,NULL,1,6,NULL,13),(16,12,0,NULL,'Paint that glows in the dark',0,10,NULL,0,5,NULL,3),(17,12,0,NULL,'Zero-point energy warp generator',0,20,NULL,0,5,NULL,10),(18,12,0,NULL,'Left rear wheel',1,2,NULL,1,6,7,6),(19,12,0,NULL,'Antimatter capsule',0,15,NULL,0,6,NULL,13);
/*!40000 ALTER TABLE `stories_AUD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `story_access`
--

DROP TABLE IF EXISTS `story_access`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `story_access` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `story_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK44C5ABEEE0E4BFA2` (`story_id`),
  KEY `FK44C5ABEEC1610AD2` (`user_id`),
  CONSTRAINT `FK44C5ABEEC1610AD2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK44C5ABEEE0E4BFA2` FOREIGN KEY (`story_id`) REFERENCES `stories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `story_access`
--

LOCK TABLES `story_access` WRITE;
/*!40000 ALTER TABLE `story_access` DISABLE KEYS */;
INSERT INTO `story_access` VALUES (2,'2013-09-04 22:26:37',3,5),(3,'2013-09-04 22:27:33',3,5),(4,'2013-09-04 22:27:50',3,5),(5,'2013-09-04 22:27:58',3,5),(6,'2013-09-04 22:28:09',3,5),(7,'2013-09-04 22:28:22',3,5),(8,'2013-09-04 22:28:38',3,5),(9,'2013-09-04 22:28:46',3,5),(10,'2013-09-04 22:28:51',3,5),(11,'2013-09-04 22:29:05',4,5),(12,'2013-09-04 22:29:11',4,5),(13,'2013-09-04 22:29:20',4,5),(14,'2013-09-04 22:34:44',3,5),(15,'2013-09-04 22:34:51',6,5),(16,'2013-09-04 22:34:57',6,5),(17,'2013-09-04 22:35:08',7,5),(18,'2013-09-04 22:35:13',7,5),(19,'2013-09-04 22:35:20',9,5),(20,'2013-09-04 22:36:05',8,5),(21,'2013-09-04 22:36:16',9,5),(22,'2013-09-04 22:36:31',7,5),(23,'2013-09-04 22:36:47',5,5),(24,'2013-09-04 22:36:58',4,5),(25,'2013-09-04 22:37:02',4,5),(26,'2013-09-04 22:39:34',3,5),(27,'2013-09-05 11:16:24',3,5),(28,'2013-09-05 11:16:30',10,5),(29,'2013-09-05 11:16:35',10,5),(30,'2013-09-05 11:16:42',11,5),(31,'2013-09-05 11:16:50',10,5),(32,'2013-09-05 11:16:56',12,5),(33,'2013-09-05 11:17:02',3,5),(34,'2013-09-05 11:17:09',13,5),(35,'2013-09-05 11:17:24',13,5),(36,'2013-09-05 11:17:36',14,5),(37,'2013-09-05 11:17:52',13,5),(38,'2013-09-05 11:24:14',3,5),(39,'2013-09-05 11:24:43',3,5),(40,'2013-09-05 11:57:27',8,5),(41,'2013-09-05 11:57:50',8,5),(42,'2013-09-05 12:05:23',10,5),(43,'2013-09-05 12:05:43',3,5),(44,'2013-09-05 13:08:07',8,5),(45,'2013-09-05 13:10:37',18,5),(46,'2013-09-05 13:16:15',8,5),(47,'2013-09-05 13:18:50',8,5),(48,'2013-09-05 13:23:04',16,5),(49,'2013-09-05 13:23:12',10,5),(50,'2013-09-05 13:23:57',17,5),(51,'2013-09-05 13:24:04',11,5),(52,'2013-09-05 13:24:09',12,5),(53,'2013-09-05 13:24:17',8,5),(54,'2013-09-05 13:24:21',18,5),(55,'2013-09-05 13:24:29',9,5),(56,'2013-09-05 13:24:34',7,5),(57,'2013-09-05 13:24:46',15,5),(58,'2013-09-05 13:24:54',14,5),(59,'2013-09-05 13:25:00',5,5),(60,'2013-09-05 13:25:04',4,5),(61,'2013-09-05 13:28:07',13,5),(62,'2013-09-05 13:28:13',13,5),(63,'2013-09-05 13:28:27',19,5),(64,'2013-09-05 13:28:32',19,5),(65,'2013-09-05 13:45:35',4,5),(66,'2013-09-05 13:47:08',4,5),(67,'2013-09-05 14:07:07',4,5),(68,'2013-09-05 14:11:53',15,5),(69,'2013-09-05 14:11:58',8,5),(70,'2013-09-05 14:12:00',18,5),(71,'2013-09-05 14:12:02',18,5),(72,'2013-10-11 14:36:25',3,5),(73,'2013-10-11 14:38:04',3,5),(74,'2013-10-11 14:38:15',19,5);
/*!40000 ALTER TABLE `story_access` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `story_user`
--

DROP TABLE IF EXISTS `story_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `story_user` (
  `Story_id` int(11) NOT NULL,
  `User_id` int(11) NOT NULL,
  PRIMARY KEY (`Story_id`,`User_id`),
  KEY `FK5914FCD5E0E4BFA2` (`Story_id`),
  KEY `FK5914FCD5C1610AD2` (`User_id`),
  CONSTRAINT `FK5914FCD5C1610AD2` FOREIGN KEY (`User_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK5914FCD5E0E4BFA2` FOREIGN KEY (`Story_id`) REFERENCES `stories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `story_user`
--

LOCK TABLES `story_user` WRITE;
/*!40000 ALTER TABLE `story_user` DISABLE KEYS */;
INSERT INTO `story_user` VALUES (4,5),(8,5),(15,5),(18,5);
/*!40000 ALTER TABLE `story_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `story_user_AUD`
--

DROP TABLE IF EXISTS `story_user_AUD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `story_user_AUD` (
  `REV` int(11) NOT NULL,
  `Story_id` int(11) NOT NULL,
  `User_id` int(11) NOT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`REV`,`Story_id`,`User_id`),
  KEY `FK9A1CB82620258526` (`REV`),
  CONSTRAINT `FK9A1CB82620258526` FOREIGN KEY (`REV`) REFERENCES `agilefant_revisions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `story_user_AUD`
--

LOCK TABLES `story_user_AUD` WRITE;
/*!40000 ALTER TABLE `story_user_AUD` DISABLE KEYS */;
INSERT INTO `story_user_AUD` VALUES (12,4,5,0),(12,8,5,0),(12,15,5,0),(12,18,5,0);
/*!40000 ALTER TABLE `story_user_AUD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storyrank`
--

DROP TABLE IF EXISTS `storyrank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `storyrank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rank` int(11) NOT NULL,
  `backlog_id` int(11) NOT NULL,
  `story_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `backlog_id` (`backlog_id`,`story_id`),
  KEY `FK6600C2A1E0E4BFA2` (`story_id`),
  KEY `FK6600C2A1F63400A2` (`backlog_id`),
  CONSTRAINT `FK6600C2A1F63400A2` FOREIGN KEY (`backlog_id`) REFERENCES `backlogs` (`id`),
  CONSTRAINT `FK6600C2A1E0E4BFA2` FOREIGN KEY (`story_id`) REFERENCES `stories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storyrank`
--

LOCK TABLES `storyrank` WRITE;
/*!40000 ALTER TABLE `storyrank` DISABLE KEYS */;
INSERT INTO `storyrank` VALUES (3,2,6,15),(4,1,7,8),(5,1,6,8),(6,4,6,9),(7,3,6,7),(8,8,6,5),(9,7,6,14),(10,6,6,12),(11,5,6,11),(12,0,6,18),(13,0,7,18),(14,9,6,19);
/*!40000 ALTER TABLE `storyrank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storyrank_AUD`
--

DROP TABLE IF EXISTS `storyrank_AUD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `storyrank_AUD` (
  `id` int(11) NOT NULL,
  `REV` int(11) NOT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  `backlog_id` int(11) DEFAULT NULL,
  `story_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`REV`),
  KEY `FK1CD8B7F220258526` (`REV`),
  CONSTRAINT `FK1CD8B7F220258526` FOREIGN KEY (`REV`) REFERENCES `agilefant_revisions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storyrank_AUD`
--

LOCK TABLES `storyrank_AUD` WRITE;
/*!40000 ALTER TABLE `storyrank_AUD` DISABLE KEYS */;
INSERT INTO `storyrank_AUD` VALUES (3,12,0,2,6,15),(4,12,0,1,7,8),(5,12,0,1,6,8),(6,12,0,4,6,9),(7,12,0,3,6,7),(8,12,0,8,6,5),(9,12,0,7,6,14),(10,12,0,6,6,12),(11,12,0,5,6,11),(12,12,0,0,6,18),(13,12,0,0,7,18),(14,12,0,9,6,19);
/*!40000 ALTER TABLE `storyrank_AUD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_user`
--

DROP TABLE IF EXISTS `task_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_user` (
  `tasks_id` int(11) NOT NULL,
  `responsibles_id` int(11) NOT NULL,
  PRIMARY KEY (`tasks_id`,`responsibles_id`),
  KEY `FKAC91A45B1C109E9` (`tasks_id`),
  KEY `FKAC91A4527F4B120` (`responsibles_id`),
  CONSTRAINT `FKAC91A4527F4B120` FOREIGN KEY (`responsibles_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKAC91A45B1C109E9` FOREIGN KEY (`tasks_id`) REFERENCES `tasks` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_user`
--

LOCK TABLES `task_user` WRITE;
/*!40000 ALTER TABLE `task_user` DISABLE KEYS */;
INSERT INTO `task_user` VALUES (2,5),(3,5),(4,5),(5,5),(6,5),(7,5),(8,5),(9,5),(10,5),(11,5),(12,5),(13,5),(14,5),(15,5);
/*!40000 ALTER TABLE `task_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_user_AUD`
--

DROP TABLE IF EXISTS `task_user_AUD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_user_AUD` (
  `REV` int(11) NOT NULL,
  `tasks_id` int(11) NOT NULL,
  `responsibles_id` int(11) NOT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`REV`,`tasks_id`,`responsibles_id`),
  KEY `FKF0A49D9620258526` (`REV`),
  CONSTRAINT `FKF0A49D9620258526` FOREIGN KEY (`REV`) REFERENCES `agilefant_revisions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_user_AUD`
--

LOCK TABLES `task_user_AUD` WRITE;
/*!40000 ALTER TABLE `task_user_AUD` DISABLE KEYS */;
INSERT INTO `task_user_AUD` VALUES (12,2,5,0),(12,3,5,0),(12,4,5,0),(12,5,5,0),(12,6,5,0),(12,7,5,0),(12,8,5,0),(12,9,5,0),(12,10,5,0),(12,11,5,0),(12,12,5,0),(12,13,5,0),(12,14,5,0),(12,15,5,0);
/*!40000 ALTER TABLE `task_user_AUD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasks`
--

DROP TABLE IF EXISTS `tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tasks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` longtext,
  `effortleft` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `originalestimate` bigint(20) DEFAULT NULL,
  `rank` int(11) NOT NULL DEFAULT '0',
  `state` int(11) NOT NULL,
  `iteration_id` int(11) DEFAULT NULL,
  `story_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6907B8E4157D2A2` (`iteration_id`),
  KEY `FK6907B8EE0E4BFA2` (`story_id`),
  CONSTRAINT `FK6907B8EE0E4BFA2` FOREIGN KEY (`story_id`) REFERENCES `stories` (`id`),
  CONSTRAINT `FK6907B8E4157D2A2` FOREIGN KEY (`iteration_id`) REFERENCES `backlogs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasks`
--

LOCK TABLES `tasks` WRITE;
/*!40000 ALTER TABLE `tasks` DISABLE KEYS */;
INSERT INTO `tasks` VALUES (2,NULL,NULL,'Vulcanize the tire',NULL,1,0,NULL,8),(3,NULL,NULL,'Attach the tire to the rim',NULL,2,0,NULL,8),(4,NULL,NULL,'Fill up the tire with air',NULL,3,0,NULL,8),(5,NULL,NULL,'Attach the tire to the left front axis',NULL,4,0,NULL,8),(6,NULL,NULL,'Check that it spins properly',NULL,5,0,NULL,8),(7,NULL,120,'Attach the tire to the rim',120,2,0,NULL,18),(8,NULL,180,'Fill up the tire with air',180,3,0,NULL,18),(9,NULL,240,'Attach the tire to the left rear axis',240,4,0,NULL,18),(10,NULL,180,'Check that it spins properly',180,5,0,NULL,18),(11,NULL,120,'Vulcanize the tire',300,1,0,NULL,18),(12,NULL,300,'[RECURRING] Have a coffee break every now and then',300,1,0,7,NULL),(13,NULL,900,'Build the frames of all seats',600,1,0,NULL,4),(14,NULL,NULL,'Put on the black leather canvases',NULL,2,0,NULL,4),(15,NULL,NULL,'Spray seats with \'new car smell\' lotion',NULL,3,0,NULL,4);
/*!40000 ALTER TABLE `tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasks_AUD`
--

DROP TABLE IF EXISTS `tasks_AUD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tasks_AUD` (
  `id` int(11) NOT NULL,
  `REV` int(11) NOT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  `description` longtext,
  `effortleft` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `originalestimate` bigint(20) DEFAULT NULL,
  `rank` int(11) DEFAULT '0',
  `state` int(11) DEFAULT NULL,
  `iteration_id` int(11) DEFAULT NULL,
  `story_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`REV`),
  KEY `FKBD8325F20258526` (`REV`),
  CONSTRAINT `FKBD8325F20258526` FOREIGN KEY (`REV`) REFERENCES `agilefant_revisions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasks_AUD`
--

LOCK TABLES `tasks_AUD` WRITE;
/*!40000 ALTER TABLE `tasks_AUD` DISABLE KEYS */;
INSERT INTO `tasks_AUD` VALUES (2,12,0,NULL,NULL,'Vulcanize the tire',NULL,1,0,NULL,8),(3,12,0,NULL,NULL,'Attach the tire to the rim',NULL,2,0,NULL,8),(4,12,0,NULL,NULL,'Fill up the tire with air',NULL,3,0,NULL,8),(5,12,0,NULL,NULL,'Attach the tire to the left front axis',NULL,4,0,NULL,8),(6,12,0,NULL,NULL,'Check that it spins properly',NULL,5,0,NULL,8),(7,12,0,NULL,120,'Attach the tire to the rim',120,2,0,NULL,18),(8,12,0,NULL,180,'Fill up the tire with air',180,3,0,NULL,18),(9,12,0,NULL,240,'Attach the tire to the left rear axis',240,4,0,NULL,18),(10,12,0,NULL,180,'Check that it spins properly',180,5,0,NULL,18),(11,12,0,NULL,120,'Vulcanize the tire',300,1,0,NULL,18),(12,12,0,NULL,300,'[RECURRING] Have a coffee break every now and then',300,1,0,7,NULL),(13,12,0,NULL,900,'Build the frames of all seats',600,1,0,NULL,4),(14,12,0,NULL,NULL,'Put on the black leather canvases',NULL,2,0,NULL,4),(15,12,0,NULL,NULL,'Spray seats with \'new car smell\' lotion',NULL,3,0,NULL,4);
/*!40000 ALTER TABLE `tasks_AUD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_iteration`
--

DROP TABLE IF EXISTS `team_iteration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team_iteration` (
  `Team_id` int(11) NOT NULL,
  `Iteration_id` int(11) NOT NULL,
  PRIMARY KEY (`Team_id`,`Iteration_id`),
  KEY `FKF2269B7B4157D2A2` (`Iteration_id`),
  KEY `FKF2269B7B745BA992` (`Team_id`),
  CONSTRAINT `FKF2269B7B745BA992` FOREIGN KEY (`Team_id`) REFERENCES `teams` (`id`),
  CONSTRAINT `FKF2269B7B4157D2A2` FOREIGN KEY (`Iteration_id`) REFERENCES `backlogs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_iteration`
--

LOCK TABLES `team_iteration` WRITE;
/*!40000 ALTER TABLE `team_iteration` DISABLE KEYS */;
/*!40000 ALTER TABLE `team_iteration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_iteration_AUD`
--

DROP TABLE IF EXISTS `team_iteration_AUD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team_iteration_AUD` (
  `REV` int(11) NOT NULL,
  `Team_id` int(11) NOT NULL,
  `Iteration_id` int(11) NOT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`REV`,`Team_id`,`Iteration_id`),
  KEY `FKFE5293CC20258526` (`REV`),
  CONSTRAINT `FKFE5293CC20258526` FOREIGN KEY (`REV`) REFERENCES `agilefant_revisions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_iteration_AUD`
--

LOCK TABLES `team_iteration_AUD` WRITE;
/*!40000 ALTER TABLE `team_iteration_AUD` DISABLE KEYS */;
/*!40000 ALTER TABLE `team_iteration_AUD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_product`
--

DROP TABLE IF EXISTS `team_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team_product` (
  `Team_id` int(11) NOT NULL,
  `Product_id` int(11) NOT NULL,
  PRIMARY KEY (`Team_id`,`Product_id`),
  KEY `FK65CE090D745BA992` (`Team_id`),
  KEY `FK65CE090DA7FE2362` (`Product_id`),
  CONSTRAINT `FK65CE090DA7FE2362` FOREIGN KEY (`Product_id`) REFERENCES `backlogs` (`id`),
  CONSTRAINT `FK65CE090D745BA992` FOREIGN KEY (`Team_id`) REFERENCES `teams` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_product`
--

LOCK TABLES `team_product` WRITE;
/*!40000 ALTER TABLE `team_product` DISABLE KEYS */;
INSERT INTO `team_product` VALUES (2,5);
/*!40000 ALTER TABLE `team_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_product_AUD`
--

DROP TABLE IF EXISTS `team_product_AUD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team_product_AUD` (
  `REV` int(11) NOT NULL,
  `Team_id` int(11) NOT NULL,
  `Product_id` int(11) NOT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`REV`,`Team_id`,`Product_id`),
  KEY `FK4E84E85E20258526` (`REV`),
  CONSTRAINT `FK4E84E85E20258526` FOREIGN KEY (`REV`) REFERENCES `agilefant_revisions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_product_AUD`
--

LOCK TABLES `team_product_AUD` WRITE;
/*!40000 ALTER TABLE `team_product_AUD` DISABLE KEYS */;
INSERT INTO `team_product_AUD` VALUES (12,2,5,0);
/*!40000 ALTER TABLE `team_product_AUD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_user`
--

DROP TABLE IF EXISTS `team_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team_user` (
  `User_id` int(11) NOT NULL,
  `Team_id` int(11) NOT NULL,
  KEY `FKF587546DC1610AD2` (`User_id`),
  KEY `FKF587546D745BA992` (`Team_id`),
  CONSTRAINT `FKF587546D745BA992` FOREIGN KEY (`Team_id`) REFERENCES `teams` (`id`),
  CONSTRAINT `FKF587546DC1610AD2` FOREIGN KEY (`User_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_user`
--

LOCK TABLES `team_user` WRITE;
/*!40000 ALTER TABLE `team_user` DISABLE KEYS */;
INSERT INTO `team_user` VALUES (5,2);
/*!40000 ALTER TABLE `team_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_user_AUD`
--

DROP TABLE IF EXISTS `team_user_AUD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team_user_AUD` (
  `REV` int(11) NOT NULL,
  `Team_id` int(11) NOT NULL,
  `User_id` int(11) NOT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`REV`,`Team_id`,`User_id`),
  KEY `FK7FE983BE20258526` (`REV`),
  CONSTRAINT `FK7FE983BE20258526` FOREIGN KEY (`REV`) REFERENCES `agilefant_revisions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_user_AUD`
--

LOCK TABLES `team_user_AUD` WRITE;
/*!40000 ALTER TABLE `team_user_AUD` DISABLE KEYS */;
INSERT INTO `team_user_AUD` VALUES (12,2,5,0);
/*!40000 ALTER TABLE `team_user_AUD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teams`
--

DROP TABLE IF EXISTS `teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teams` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` longtext,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teams`
--

LOCK TABLES `teams` WRITE;
/*!40000 ALTER TABLE `teams` DISABLE KEYS */;
INSERT INTO `teams` VALUES (2,NULL,'Admin team');
/*!40000 ALTER TABLE `teams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teams_AUD`
--

DROP TABLE IF EXISTS `teams_AUD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teams_AUD` (
  `id` int(11) NOT NULL,
  `REV` int(11) NOT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  `description` longtext,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`REV`),
  KEY `FKF6966C8720258526` (`REV`),
  CONSTRAINT `FKF6966C8720258526` FOREIGN KEY (`REV`) REFERENCES `agilefant_revisions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teams_AUD`
--

LOCK TABLES `teams_AUD` WRITE;
/*!40000 ALTER TABLE `teams_AUD` DISABLE KEYS */;
INSERT INTO `teams_AUD` VALUES (2,12,0,NULL,'Admin team');
/*!40000 ALTER TABLE `teams_AUD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin` bit(1) DEFAULT b'1',
  `autoassignToStories` bit(1) DEFAULT b'0',
  `autoassignToTasks` bit(1) DEFAULT b'1',
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `fullName` varchar(255) DEFAULT NULL,
  `initials` varchar(255) DEFAULT NULL,
  `loginName` varchar(255) DEFAULT NULL,
  `markStoryStarted` int(11) DEFAULT '1',
  `password` varchar(255) DEFAULT NULL,
  `recentItemsNumberOfWeeks` int(11) NOT NULL,
  `weekEffort` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `loginName` (`loginName`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (4,'\0','\0','',NULL,'','readonly','readonly','readonly',1,'5ebe2294ecd0e0f08eab7690d2a6ee69',0,NULL),(5,'','\0','',NULL,'','Administrator','Admin','admin',1,'5ebe2294ecd0e0f08eab7690d2a6ee69',16,2400);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_AUD`
--

DROP TABLE IF EXISTS `users_AUD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_AUD` (
  `id` int(11) NOT NULL,
  `REV` int(11) NOT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `fullName` varchar(255) DEFAULT NULL,
  `initials` varchar(255) DEFAULT NULL,
  `loginName` varchar(255) DEFAULT NULL,
  `weekEffort` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`,`REV`),
  KEY `FK154C77D920258526` (`REV`),
  CONSTRAINT `FK154C77D920258526` FOREIGN KEY (`REV`) REFERENCES `agilefant_revisions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_AUD`
--

--
-- Table structure for table `whatsnextentry`
--

DROP TABLE IF EXISTS `whatsnextentry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `whatsnextentry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rank` int(11) NOT NULL DEFAULT '0',
  `task_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `task_id` (`task_id`,`user_id`),
  KEY `FK4B213410C1610AD2` (`user_id`),
  KEY `FK4B2134106E84F892` (`task_id`),
  CONSTRAINT `FK4B2134106E84F892` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`),
  CONSTRAINT `FK4B213410C1610AD2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `whatsnextentry`
--

LOCK TABLES `whatsnextentry` WRITE;
/*!40000 ALTER TABLE `whatsnextentry` DISABLE KEYS */;
INSERT INTO `whatsnextentry` VALUES (1,3,13,5),(2,4,12,5);
/*!40000 ALTER TABLE `whatsnextentry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `whatsnextstoryentry`
--

DROP TABLE IF EXISTS `whatsnextstoryentry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `whatsnextstoryentry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rank` int(11) NOT NULL DEFAULT '0',
  `story_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `story_id` (`story_id`,`user_id`),
  KEY `FK4B213410C1610AD3` (`user_id`),
  KEY `FK4B2134106E84F893` (`story_id`),
  CONSTRAINT `FK4B2134106E84F893` FOREIGN KEY (`story_id`) REFERENCES `stories` (`id`),
  CONSTRAINT `FK4B213410C1610AD3` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `whatsnextstoryentry`
--

LOCK TABLES `whatsnextstoryentry` WRITE;
/*!40000 ALTER TABLE `whatsnextstoryentry` DISABLE KEYS */;
INSERT INTO `whatsnextstoryentry` VALUES (3,1,4,5),(4,2,15,5),(5,3,8,5),(6,4,18,5);
/*!40000 ALTER TABLE `whatsnextstoryentry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `widgetcollections`
--

DROP TABLE IF EXISTS `widgetcollections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `widgetcollections` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK26C78D1C1610AD2` (`user_id`),
  CONSTRAINT `FK26C78D1C1610AD2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `widgetcollections`
--

LOCK TABLES `widgetcollections` WRITE;
/*!40000 ALTER TABLE `widgetcollections` DISABLE KEYS */;
INSERT INTO `widgetcollections` VALUES (1,'Example custom dashboard',NULL);
/*!40000 ALTER TABLE `widgetcollections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `widgets`
--

DROP TABLE IF EXISTS `widgets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `widgets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `listNumber` int(11) DEFAULT NULL,
  `objectId` int(11) DEFAULT NULL,
  `position` int(11) DEFAULT NULL,
  `type` varchar(255) NOT NULL,
  `widgetCollection_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4FE3EEAF8BACA792` (`widgetCollection_id`),
  CONSTRAINT `FK4FE3EEAF8BACA792` FOREIGN KEY (`widgetCollection_id`) REFERENCES `widgetcollections` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `widgets`
--

LOCK TABLES `widgets` WRITE;
/*!40000 ALTER TABLE `widgets` DISABLE KEYS */;
INSERT INTO `widgets` VALUES (1,1,8,0,'story',1),(2,0,6,1,'projectMetrics',1),(3,0,7,0,'iterationMetrics',1);
/*!40000 ALTER TABLE `widgets` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-11-05 14:11:04
