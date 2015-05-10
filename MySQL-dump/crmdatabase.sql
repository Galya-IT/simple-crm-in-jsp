CREATE DATABASE  IF NOT EXISTS `crm` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `crm`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: crm
-- ------------------------------------------------------
-- Server version	5.6.19-log

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
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clients` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `Location` varchar(50) DEFAULT NULL,
  `Notes` text,
  `ContractDate` date DEFAULT NULL,
  `LogoImageName` varchar(50) DEFAULT NULL,
  `IsDeleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Name_UNIQUE` (`Name`),
  UNIQUE KEY `Id_UNIQUE` (`Id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (1,'Wal-mart','Bentonville, Arkansas','Wal-Mart Stores, Inc., branded as Walmart /ËwÉlmÉrt/, is an American multinational retail corporation that runs chains of large discount department stores and warehouse stores. Headquartered in Bentonville, Arkansas, the company was founded by Sam Walton in 1962 and incorporated on October 31, 1969. It has over 11,000 stores in 27 countries, under a total 55 different names.[7] The company operates under the Walmart name in the US and Puerto Rico. It operates in Mexico as Walmart de MÃ©xico y CentroamÃ©rica, in the United Kingdom as Asda, in Japan as Seiyu, and in India as Best Price. It has wholly owned operations in Argentina, Brazil, and Canada.','2005-02-20','New_Walmart_Logo.svg.png',0),(2,'Royal Dutch Shell','London','Royal Dutch Shell plc (LSE: RDSA, RDSB), commonly known as Shell, is an AngloâDutch multinational oil and gas company headquartered in the Netherlands and incorporated in the United Kingdom.[2] Created by the merger of Royal Dutch Petroleum and UK-based Shell Transport & Trading, it is the second largest company in the world, in terms of revenue,[1] and one of the six oil and gas \"supermajors\".\r\n\r\nShell is also one of the world\'s most valuable companies.[3] As of January, 2013 the largest shareholder is Capital Research Global Investors with 9.85% ahead of BlackRock in second with 6.89%.[4] Shell topped the 2013 Fortune Global 500 list of the world\'s largest companies.[5] Royal Dutch Shell revenue was equal to 84% of the Netherlands\'s $555.8 billion GDP at the time.[6]','2001-08-20','Shell Logo.jpg',0),(3,'Exxon Mobil Corporation','Irving, Texas','Exxon Mobil Corp., or ExxonMobil, is an American multinational oil and gas corporation headquartered in Irving, Texas, United States. It is a direct descendant of John D. Rockefeller\'s Standard Oil company,[3] and was formed on November 30, 1999, by the merger of Exxon and Mobil (formerly Standard Oil of New Jersey and Standard Oil of New York). It is affiliated with Imperial Oil which operates in Canada.\r\n\r\nThe world\'s third largest company by revenue, ExxonMobil is also the second largest publicly traded company by market capitalization.[4][5] The company was ranked No. 5 globally in Forbes Global 2000 list in 2013.[6] ExxonMobil\'s reserves were 72 billion BOE (barrels of oil equivalent) at the end of 2007 and, at then (2007) rates of production, were expected to last more than 14 years.[7] With 37 oil refineries in 21 countries constituting a combined daily refining capacity of 6.3 million barrels (1,000,000 m3), ExxonMobil is the largest refiner in the world,[8][9] a title that was also associated with Standard Oil since its incorporation in 1870.[10]','2008-09-20','250px-Exxon_Mobil_Logo.svg.png',0),(4,'China National Petroleum CorporationChina Beijing','China Beijing','China National Petroleum Corporation (CNPC) (simplified Chinese: ä¸­å½ç³æ²¹å¤©ç¶æ°éå¢å¬å¸; traditional Chinese: ä¸­åç³æ²¹å¤©ç¶æ°£éåå¬å¸; pinyin: ZhÅngguÃ³ ShÃ­yÃ³u TiÄnrÃ¡nqÃ¬ JÃ­tuÃ¡n GÅngsÄ«)[2] is a Chinese state-owned oil and gas corporation and the largest integrated energy company in the People\'s Republic of China. It has its headquarters in Dongcheng District, Beijing.[3]\r\n\r\nCNPC is the parent of PetroChina, the second largest company in the world in terms of market capitalization as of June 2010.[4]','2031-10-20','283px-CNPC.svg.png',0);
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-07-23 15:45:14
