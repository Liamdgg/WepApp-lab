-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: product_management
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_code` varchar(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `quantity` int DEFAULT '0',
  `category` varchar(255) DEFAULT NULL,
  `description` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_code` (`product_code`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'P001','Laptop Dell XPS 13',1299.99,10,'Electronics','High-performance laptop','2025-12-02 13:08:02'),(2,'P002','iPhone 15 Pro',999.99,25,'Electronics','Latest iPhone model','2025-12-02 13:08:02'),(3,'P003','Office Chair',199.99,50,'Furniture','Ergonomic office chair','2025-12-02 13:08:02'),(4,'T001','Laptop Dell XPS 14',1.00,1,'Other','','2025-12-03 08:23:26'),(5,'T002','Laptop Dell XPS 15',1.00,1,'Other','','2025-12-03 08:23:37'),(6,'T003','Laptop Dell XPS 16',1.00,1,'Other','','2025-12-03 08:23:47'),(8,'T004','Laptop Dell XPS 16',1.00,1,'Other','','2025-12-03 08:24:03'),(9,'T005','Laptop Dell XPS 16',1.00,1,'Other','','2025-12-03 08:24:12'),(10,'T006','Laptop Dell XPS 16',1.00,1,'Other','','2025-12-03 08:24:18'),(11,'T007','Laptop Dell XPS 16',1.00,1,'Other','','2025-12-03 08:24:24'),(12,'T008','Laptop Dell XPS 16',1.00,1,'Other','','2025-12-03 08:27:25'),(13,'T009','Laptop Dell XPS 16',1.00,1,'Other','','2025-12-03 08:33:05'),(14,'T0010','Laptop Dell XPS 16',1.00,1,'Other','','2025-12-03 09:56:48'),(15,'T0011','Laptop Dell XPS 16',1.00,1,'Other','','2025-12-03 09:56:59');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-04 22:20:20
