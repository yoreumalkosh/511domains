-- MySQL dump 10.13  Distrib 8.0.23, for Linux (x86_64)
--
-- Host: localhost    Database: haa
-- ------------------------------------------------------
-- Server version	8.0.23-0ubuntu0.20.10.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients` (
  `clientId` bigint NOT NULL AUTO_INCREMENT,
  `fullName` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `gender` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password1` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `organization` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `role` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `city` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `country` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `isValidated` bit(1) NOT NULL,
  `isActive1` bit(1) NOT NULL,
  `isActive2` bit(1) NOT NULL,
  `isActive3` bit(1) NOT NULL,
  `dateTimeCreated` datetime NOT NULL,
  `dateTimeModified` datetime NOT NULL,
  `password2` varchar(100) NOT NULL,
  `password3` varchar(100) NOT NULL,
  PRIMARY KEY (`clientId`),
  UNIQUE KEY `clients_UN` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dkims`
--

DROP TABLE IF EXISTS `dkims`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dkims` (
  `dkimId` bigint NOT NULL AUTO_INCREMENT,
  `domainId` bigint NOT NULL,
  `selector` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`dkimId`),
  UNIQUE KEY `dkims_UN` (`domainId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `domains`
--

DROP TABLE IF EXISTS `domains`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `domains` (
  `domainId` bigint NOT NULL AUTO_INCREMENT,
  `clientId` bigint NOT NULL,
  `domainName` varchar(255) NOT NULL,
  `isActive1` bit(1) NOT NULL,
  `isActive2` bit(1) NOT NULL,
  `isActive3` bigint NOT NULL,
  `dateTimeCreated` datetime NOT NULL,
  `dateTimeModified` datetime NOT NULL,
  `isValidated` bit(1) NOT NULL,
  PRIMARY KEY (`domainId`),
  UNIQUE KEY `domains_UN` (`domainName`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `emailChangeValidation`
--

DROP TABLE IF EXISTS `emailChangeValidation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emailChangeValidation` (
  `emailChangeValidationId` bigint NOT NULL AUTO_INCREMENT,
  `emailChangeValidationCode` varchar(100) NOT NULL,
  `isUsed` bit(1) NOT NULL,
  `dateTimeCreated` datetime NOT NULL,
  `clientId` bigint NOT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`emailChangeValidationId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `emailValidation`
--

DROP TABLE IF EXISTS `emailValidation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emailValidation` (
  `emailValidationId` bigint NOT NULL AUTO_INCREMENT,
  `validationCode` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `dateTimeCreated` datetime NOT NULL,
  `used` bit(1) NOT NULL,
  `clientId` bigint NOT NULL,
  PRIMARY KEY (`emailValidationId`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mails`
--

DROP TABLE IF EXISTS `mails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mails` (
  `mailId` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `domainId` bigint NOT NULL,
  `clientId` bigint NOT NULL,
  `isEnabled` bit(1) NOT NULL,
  `dateTimeCreated` datetime NOT NULL,
  `isActive1` bit(1) NOT NULL,
  `isActive2` bit(1) NOT NULL,
  `isActive3` bit(1) NOT NULL,
  PRIMARY KEY (`mailId`),
  UNIQUE KEY `mails_UN` (`domainId`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages` (
  `messageId` bigint NOT NULL AUTO_INCREMENT,
  `messageText` varchar(4096) NOT NULL,
  `dateTimeCreated` datetime NOT NULL,
  `subject` varchar(2048) NOT NULL,
  `clientId` bigint NOT NULL,
  PRIMARY KEY (`messageId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `passwordReset`
--

DROP TABLE IF EXISTS `passwordReset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `passwordReset` (
  `passwordResetId` bigint NOT NULL AUTO_INCREMENT,
  `passwordResetCode` varchar(100) NOT NULL,
  `isUsed` bit(1) NOT NULL,
  `clientId` bigint NOT NULL,
  `dateTimeCreated` datetime NOT NULL,
  PRIMARY KEY (`passwordResetId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sessions` (
  `sessionId` bigint NOT NULL AUTO_INCREMENT,
  `sessionCode` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `dateTimeCreated` datetime NOT NULL,
  `dateTimeExpired` datetime NOT NULL,
  `isActive` bit(1) NOT NULL,
  `isInfinite` bit(1) NOT NULL,
  `dateTimeModified` datetime NOT NULL,
  `clientId` bigint NOT NULL,
  PRIMARY KEY (`sessionId`),
  UNIQUE KEY `sessions_UN` (`sessionCode`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'haa'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-21 21:53:29
