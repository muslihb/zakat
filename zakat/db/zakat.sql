-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 08, 2021 at 03:12 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `zakat`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbmaster`
--

CREATE TABLE `tbmaster` (
  `nama` varchar(100) NOT NULL,
  `nomortelepon` varchar(15) NOT NULL,
  `alamat` text NOT NULL,
  `username` varchar(40) NOT NULL,
  `password` varchar(30) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tbpenerimazakat`
--

CREATE TABLE `tbpenerimazakat` (
  `id_penerima` int(11) NOT NULL,
  `id_amil` varchar(40) DEFAULT NULL,
  `namapenerima` varchar(50) NOT NULL,
  `jenis_zakat` varchar(50) NOT NULL,
  `jumlah_zakat` varchar(50) NOT NULL,
  `status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tbpenyaluran`
--

CREATE TABLE `tbpenyaluran` (
  `id_penyaluran` int(11) NOT NULL,
  `jenis_zakat` varchar(40) NOT NULL,
  `zakat_tersedia` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tbrequestzakat`
--

CREATE TABLE `tbrequestzakat` (
  `id_request` int(11) NOT NULL,
  `id_muzakki` varchar(40) DEFAULT NULL,
  `id_amil` varchar(40) DEFAULT NULL,
  `zakat` varchar(30) NOT NULL,
  `jenis_zakat` varchar(100) NOT NULL,
  `total_zakat` varchar(100) NOT NULL,
  `tgl_pengambilan` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbmaster`
--
ALTER TABLE `tbmaster`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `tbpenerimazakat`
--
ALTER TABLE `tbpenerimazakat`
  ADD PRIMARY KEY (`id_penerima`),
  ADD KEY `id_amil` (`id_amil`);

--
-- Indexes for table `tbpenyaluran`
--
ALTER TABLE `tbpenyaluran`
  ADD PRIMARY KEY (`id_penyaluran`);

--
-- Indexes for table `tbrequestzakat`
--
ALTER TABLE `tbrequestzakat`
  ADD PRIMARY KEY (`id_request`),
  ADD KEY `id_muzakki` (`id_muzakki`),
  ADD KEY `id_amil` (`id_amil`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbpenerimazakat`
--
ALTER TABLE `tbpenerimazakat`
  MODIFY `id_penerima` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbpenyaluran`
--
ALTER TABLE `tbpenyaluran`
  MODIFY `id_penyaluran` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbrequestzakat`
--
ALTER TABLE `tbrequestzakat`
  MODIFY `id_request` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbpenerimazakat`
--
ALTER TABLE `tbpenerimazakat`
  ADD CONSTRAINT `tbpenerimazakat_ibfk_1` FOREIGN KEY (`id_amil`) REFERENCES `tbmaster` (`username`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `tbrequestzakat`
--
ALTER TABLE `tbrequestzakat`
  ADD CONSTRAINT `tbrequestzakat_ibfk_1` FOREIGN KEY (`id_muzakki`) REFERENCES `tbmaster` (`username`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `tbrequestzakat_ibfk_2` FOREIGN KEY (`id_amil`) REFERENCES `tbmaster` (`username`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
