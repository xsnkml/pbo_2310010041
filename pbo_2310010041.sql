-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 12, 2025 at 11:33 AM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pbo_2310010041`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id_admin` int NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `nama_admin` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id_admin`, `username`, `password`, `nama_admin`) VALUES
(1, 'admin', 'admin123', 'Administrator');

-- --------------------------------------------------------

--
-- Table structure for table `panen`
--

CREATE TABLE `panen` (
  `id_panen` varchar(10) NOT NULL,
  `id_periode` varchar(10) DEFAULT NULL,
  `tgl_panen` date DEFAULT NULL,
  `jumlah_panen` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `panen`
--

INSERT INTO `panen` (`id_panen`, `id_periode`, `tgl_panen`, `jumlah_panen`) VALUES
('1', '1', '2025-05-14', 1000);

-- --------------------------------------------------------

--
-- Table structure for table `periode_tanam`
--

CREATE TABLE `periode_tanam` (
  `id_periode` varchar(10) NOT NULL,
  `id_tanaman` varchar(10) DEFAULT NULL,
  `id_petani` varchar(10) DEFAULT NULL,
  `tgl_mulai_tanam` date DEFAULT NULL,
  `jumlah_tanam` double DEFAULT NULL,
  `status` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `lat` varchar(50) DEFAULT NULL,
  `longi` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `periode_tanam`
--

INSERT INTO `periode_tanam` (`id_periode`, `id_tanaman`, `id_petani`, `tgl_mulai_tanam`, `jumlah_tanam`, `status`, `lat`, `longi`) VALUES
('1', '1', '1', '2025-05-25', 2, 'Sudah Panen', '+90°', '+180°');

-- --------------------------------------------------------

--
-- Table structure for table `petani`
--

CREATE TABLE `petani` (
  `id_petani` varchar(10) NOT NULL,
  `nama_petani` varchar(50) NOT NULL,
  `jenis_kelamin` varchar(10) DEFAULT NULL,
  `alamat` varchar(50) DEFAULT NULL,
  `no_hp` varchar(13) DEFAULT NULL,
  `jumlah_lahan` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `petani`
--

INSERT INTO `petani` (`id_petani`, `nama_petani`, `jenis_kelamin`, `alamat`, `no_hp`, `jumlah_lahan`) VALUES
('1', 'Abdul', 'Pria', 'Jl.Marabahan', '08135657775', 3);

-- --------------------------------------------------------

--
-- Table structure for table `tanaman`
--

CREATE TABLE `tanaman` (
  `id_tanaman` varchar(10) NOT NULL,
  `nama_tanaman` varchar(50) NOT NULL,
  `jenis_tanaman` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `tanaman`
--

INSERT INTO `tanaman` (`id_tanaman`, `nama_tanaman`, `jenis_tanaman`) VALUES
('1', 'Bunga Bangkai', 'Bau');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id_admin`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `panen`
--
ALTER TABLE `panen`
  ADD PRIMARY KEY (`id_panen`),
  ADD KEY `id_periode` (`id_periode`);

--
-- Indexes for table `periode_tanam`
--
ALTER TABLE `periode_tanam`
  ADD PRIMARY KEY (`id_periode`),
  ADD KEY `id_tanaman` (`id_tanaman`),
  ADD KEY `id_petani` (`id_petani`);

--
-- Indexes for table `petani`
--
ALTER TABLE `petani`
  ADD PRIMARY KEY (`id_petani`);

--
-- Indexes for table `tanaman`
--
ALTER TABLE `tanaman`
  ADD PRIMARY KEY (`id_tanaman`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id_admin` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `panen`
--
ALTER TABLE `panen`
  ADD CONSTRAINT `panen_ibfk_1` FOREIGN KEY (`id_periode`) REFERENCES `periode_tanam` (`id_periode`);

--
-- Constraints for table `periode_tanam`
--
ALTER TABLE `periode_tanam`
  ADD CONSTRAINT `periode_tanam_ibfk_1` FOREIGN KEY (`id_tanaman`) REFERENCES `tanaman` (`id_tanaman`),
  ADD CONSTRAINT `periode_tanam_ibfk_2` FOREIGN KEY (`id_petani`) REFERENCES `petani` (`id_petani`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
