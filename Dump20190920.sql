CREATE DATABASE  IF NOT EXISTS `DB_Bicocco` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `DB_Bicocco`;
-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: 212.237.20.175    Database: DB_Bicocco
-- ------------------------------------------------------
-- Server version	5.7.27-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `DataType`
--

DROP TABLE IF EXISTS `DataType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `DataType` (
  `DataName` varchar(255) NOT NULL,
  PRIMARY KEY (`DataName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Tipi di dato possibili';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Services`
--

DROP TABLE IF EXISTS `Services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Services` (
  `ServiceId` int(11) NOT NULL,
  `URI` varchar(255) NOT NULL,
  `Port` int(11) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `GroupId` int(11) NOT NULL,
  `Description` varchar(500) DEFAULT NULL,
  `GetMapping` varchar(255) DEFAULT NULL,
  `PutMapping` varchar(255) DEFAULT NULL,
  `IsSensor` tinyint(1) NOT NULL,
  PRIMARY KEY (`ServiceId`),
  UNIQUE KEY `ServiceId_UNIQUE` (`ServiceId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ServicesValues`
--

DROP TABLE IF EXISTS `ServicesValues`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ServicesValues` (
  `Values` varchar(255) NOT NULL,
  `DataType` varchar(255) NOT NULL,
  `Timestamp` datetime NOT NULL,
  `SensorOrigin` int(11) NOT NULL,
  PRIMARY KEY (`Values`,`DataType`,`Timestamp`,`SensorOrigin`),
  KEY `Sensor_origin_idx` (`SensorOrigin`),
  KEY `DataTypeReference_idx` (`DataType`),
  CONSTRAINT `DataTypeReference` FOREIGN KEY (`DataType`) REFERENCES `DataType` (`DataName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `OriginServiceReference` FOREIGN KEY (`SensorOrigin`) REFERENCES `Services` (`ServiceId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Ci andranno i valori di ogni servizio poi collegati alla tabella dei servizi';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ServicesWanted`
--

DROP TABLE IF EXISTS `ServicesWanted`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ServicesWanted` (
  `Wanted` varchar(255) NOT NULL,
  `DataType` varchar(255) NOT NULL,
  `Timestamp` datetime NOT NULL,
  `SensorOrigin` int(11) NOT NULL,
  PRIMARY KEY (`DataType`,`Timestamp`,`SensorOrigin`,`Wanted`),
  KEY `SensorOrigin_idx` (`SensorOrigin`),
  CONSTRAINT `DataType_reference` FOREIGN KEY (`DataType`) REFERENCES `DataType` (`DataName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `SensorOrigin_reference` FOREIGN KEY (`SensorOrigin`) REFERENCES `Services` (`ServiceId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-20 18:44:27
