/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE IF NOT EXISTS `ad_locum` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ad_locum`;

DROP TABLE IF EXISTS `recommended_job`;
CREATE TABLE IF NOT EXISTS `recommended_job` (
  `id` bigint DEFAULT NULL,
  `user_id` double DEFAULT NULL,
  `open_job_id` double DEFAULT NULL,
  `similarity_score` double DEFAULT NULL,
  KEY `ix_recommended_job_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

REPLACE INTO `recommended_job` (`id`, `user_id`, `open_job_id`, `similarity_score`) VALUES
	(0, 1, 1, 0.75),
	(1, 1, 2, 0.75),
	(2, 1, 12, 0.75),
	(3, 1, 1, 1),
	(4, 1, 2, 0.75),
	(5, 1, 12, 1),
	(10, 2, 12, 0.75),
	(9, 2, 6, 0.75),
	(8, 2, 1, 0.75),
	(7, 2, 6, 1),
	(6, 2, 6, 0.75),
	(11, 7, 23, 1),
	(12, 7, 24, 0.75),
	(13, 7, 25, 0.75),
	(14, 7, 26, 1),
	(15, 7, 27, 0.75),
	(16, 7, 28, 1);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
