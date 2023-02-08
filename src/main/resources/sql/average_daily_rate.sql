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

CREATE TABLE IF NOT EXISTS `average_daily_rate` (
  `date` date NOT NULL,
  `average_daily_rate_weekday` double DEFAULT NULL,
  `average_daily_rate_weekend` double DEFAULT NULL,
  `weekday_14_ma` double DEFAULT NULL,
  `weekday_28_ma` double DEFAULT NULL,
  `weekend_14_ma` double DEFAULT NULL,
  `weekend_28_ma` double DEFAULT NULL,
  PRIMARY KEY (`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

REPLACE INTO `average_daily_rate` (`date`, `average_daily_rate_weekday`, `average_daily_rate_weekend`, `weekday_14_ma`, `weekday_28_ma`, `weekend_14_ma`, `weekend_28_ma`) VALUES
	('2023-01-01', NULL, 117.16666666666667, NULL, NULL, NULL, NULL),
	('2023-01-02', 126, NULL, NULL, NULL, 117.16666666666667, 117.16666666666667),
	('2023-01-03', 123.66666666666667, NULL, 126, 126, 117.16666666666667, 117.16666666666667),
	('2023-01-04', 128, NULL, 124.83333333333334, 124.83333333333334, 117.16666666666667, 117.16666666666667),
	('2023-01-05', 125.3, NULL, 125.8888888888889, 125.8888888888889, 117.16666666666667, 117.16666666666667),
	('2023-01-06', 117, NULL, 125.74166666666667, 125.74166666666667, 117.16666666666667, 117.16666666666667),
	('2023-01-07', NULL, 109, 123.99333333333334, 123.99333333333334, 117.16666666666667, 117.16666666666667),
	('2023-01-08', NULL, 129.6, 123.99333333333334, 123.99333333333334, 113.08333333333334, 113.08333333333334),
	('2023-01-09', 122.81818181818181, NULL, 123.99333333333334, 123.99333333333334, 118.58888888888889, 118.58888888888889),
	('2023-01-10', 125.66666666666667, NULL, 123.79747474747474, 123.79747474747474, 118.58888888888889, 118.58888888888889),
	('2023-01-11', 123.2, NULL, 124.06450216450216, 124.06450216450216, 118.58888888888889, 118.58888888888889),
	('2023-01-12', 124.11111111111111, NULL, 123.95643939393939, 123.95643939393939, 118.58888888888889, 118.58888888888889),
	('2023-01-13', 125.4, NULL, 123.97362514029182, 123.97362514029182, 118.58888888888889, 118.58888888888889),
	('2023-01-14', NULL, 126.45454545454545, 124.11626262626264, 124.11626262626264, 118.58888888888889, 118.58888888888889),
	('2023-01-15', NULL, 119.42857142857143, 124.11626262626264, 124.11626262626264, 120.55530303030302, 120.55530303030302),
	('2023-01-16', 116.2, NULL, 124.11626262626264, 124.11626262626264, 121.12077922077923, 120.32995670995669),
	('2023-01-17', 132, NULL, 123.13626262626262, 123.39660238751149, 121.12077922077923, 120.32995670995669),
	('2023-01-18', 122.125, NULL, 123.96959595959595, 124.1135521885522, 121.12077922077923, 120.32995670995669),
	('2023-01-19', 125.54545454545455, NULL, 123.38209595959597, 123.96058663558665, 121.12077922077923, 120.32995670995669),
	('2023-01-20', 117, NULL, 123.4066414141414, 124.0737914862915, 121.12077922077923, 120.32995670995669),
	('2023-01-21', NULL, 127.57142857142857, 123.40664141414143, 123.6022053872054, 121.12077922077923, 120.32995670995669),
	('2023-01-22', NULL, 129.35714285714286, 123.40664141414143, 123.6022053872054, 125.76363636363637, 121.53686868686867),
	('2023-01-23', 124.375, NULL, 123.40664141414143, 123.6022053872054, 125.70292207792207, 122.65405071119356),
	('2023-01-24', 133.6, NULL, 123.56232323232322, 123.65050505050506, 125.70292207792207, 122.65405071119356),
	('2023-01-25', 120.33333333333333, NULL, 124.35565656565655, 124.2357694592989, 125.70292207792207, 122.65405071119356),
	('2023-01-26', 119.33333333333333, NULL, 124.06898989898988, 124.01896745230081, 125.70292207792207, 122.65405071119356),
	('2023-01-27', 122.8, NULL, 123.59121212121211, 123.77235513024989, 125.70292207792207, 122.65405071119356),
	('2023-01-28', NULL, 120.33333333333333, 123.33121212121212, 123.7237373737374, 125.70292207792207, 122.65405071119356),
	('2023-01-29', NULL, 121.27272727272727, 123.33121212121212, 123.7237373737374, 124.17261904761905, 122.36396103896104),
	('2023-01-30', 119, NULL, 123.33121212121212, 123.7237373737374, 124.633658008658, 122.87721861471863),
	('2023-01-31', 123.33333333333333, NULL, 123.61121212121213, 123.37373737373738, 124.633658008658, 122.87721861471863),
	('2023-02-01', 121, NULL, 122.74454545454543, 123.35707070707072, 124.633658008658, 122.87721861471863),
	('2023-02-02', 132.16666666666666, NULL, 122.63204545454546, 123.00707070707071, 124.633658008658, 122.87721861471863),
	('2023-02-03', 133.11111111111111, NULL, 123.29416666666668, 123.35040404040402, 124.633658008658, 122.87721861471863),
	('2023-02-04', NULL, 130, 124.90527777777777, 124.1559595959596, 124.633658008658, 122.87721861471863),
	('2023-02-05', NULL, 126.66666666666667, 124.90527777777777, 124.1559595959596, 125.24080086580086, 125.50221861471861),
	('2023-02-06', 123, NULL, 124.90527777777777, 124.1559595959596, 124.56818181818183, 125.13555194805194),
	('2023-02-07', 125.71428571428571, NULL, 124.76777777777777, 124.1650505050505, 124.56818181818183, 125.13555194805194),
	('2023-02-08', 126.85714285714286, NULL, 123.97920634920634, 124.16743145743143, 124.56818181818183, 125.13555194805194),
	('2023-02-09', 124.5, NULL, 124.6315873015873, 124.35028860028858, 124.56818181818183, 125.13555194805194),
	('2023-02-10', 121.28571428571429, NULL, 125.14825396825395, 124.36973304473304, 124.56818181818183, 125.13555194805194),
	('2023-02-11', NULL, 127.66666666666667, 124.9968253968254, 124.16401875901875, 124.56818181818183, 125.13555194805194),
	('2023-02-12', NULL, 124.14285714285714, 124.9968253968254, 124.16401875901875, 126.40151515151516, 125.2870670995671),
	('2023-02-13', 126, NULL, 124.9968253968254, 124.16401875901875, 127.11904761904762, 125.8763528138528),
	('2023-02-14', 117.66666666666667, NULL, 125.6968253968254, 124.65401875901875, 127.11904761904762, 125.8763528138528),
	('2023-02-15', 116.5, NULL, 125.13015873015874, 123.93735209235209, 127.11904761904762, 125.8763528138528),
	('2023-02-16', 127.25, NULL, 124.68015873015875, 123.6561020923521, 127.11904761904762, 125.8763528138528),
	('2023-02-17', 122, NULL, 124.18849206349205, 123.74132936507938, 127.11904761904762, 125.8763528138528),
	('2023-02-18', NULL, 137, 123.07738095238096, 123.99132936507935, 127.11904761904762, 125.8763528138528),
	('2023-02-19', NULL, 129.2, 123.07738095238096, 123.99132936507935, 128.86904761904762, 127.05492424242424),
	('2023-02-20', 127.84615384615384, NULL, 123.07738095238096, 123.99132936507935, 129.50238095238095, 127.0352813852814),
	('2023-02-21', 125.42857142857143, NULL, 123.56199633699634, 124.16488705738705, 129.50238095238095, 127.0352813852814),
	('2023-02-22', 122, NULL, 123.5334249084249, 123.75631562881563, 129.50238095238095, 127.0352813852814),
	('2023-02-23', 125.0909090909091, NULL, 123.04771062271061, 123.83964896214896, 129.50238095238095, 127.0352813852814),
	('2023-02-24', 131.72727272727272, NULL, 123.10680153180151, 124.12752775002775, 129.50238095238095, 127.0352813852814),
	('2023-02-25', NULL, 135.33333333333334, 124.15095737595739, 124.57389138639137, 129.50238095238095, 127.0352813852814),
	('2023-02-26', NULL, 115.25, 124.15095737595739, 124.57389138639137, 131.4190476190476, 128.91028138528137),
	('2023-02-27', 122.5, NULL, 124.15095737595739, 124.57389138639137, 129.19583333333333, 128.15744047619046),
	('2023-02-28', 120.25, NULL, 123.80095737595738, 124.74889138639136, 129.19583333333333, 128.15744047619046),
	('2023-03-01', NULL, NULL, 124.0592907092907, 124.59472471972472, 129.19583333333333, 128.15744047619046);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
