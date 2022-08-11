-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 11, 2022 at 06:00 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cli_atm`
--

CREATE DATABASE IF NOT EXISTS `cli_atm`;

USE `cli_atm`;

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `id` int(11) NOT NULL,
  `tgl_transaksi` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `id_user` int(11) DEFAULT NULL,
  `nama_transaksi` varchar(25) NOT NULL,
  `tujuan` varchar(20) NOT NULL,
  `nama_penerima` varchar(50) NOT NULL,
  `nominal` int(11) NOT NULL,
  `status` varchar(7) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id`, `tgl_transaksi`, `id_user`, `nama_transaksi`, `tujuan`, `nama_penerima`, `nominal`, `status`) VALUES
(1, '2022-07-12 15:18:47', 1, 'Transfer Saldo', '7910910544825211', 'Jenny Dwi', 1500, 'sukses'),
(2, '2022-07-15 13:50:35', 1, 'Pembayaran Shopee', 'SHPE12345678', 'andikarisky313', 2500, 'sukses'),
(7, '2022-08-06 01:51:29', 1, 'Top Up Lazada Credit', 'LZC196910557', 'andikaa', 3000, 'sukses'),
(9, '2022-08-11 03:20:17', 7, 'Pembelian Pulsa', '08812345678', 'Smartfren', 25000, 'sukses'),
(10, '2022-08-11 03:23:00', 7, 'Pembelian Pulsa', '082123456789', 'Telkomsel', 25000, 'sukses'),
(11, '2022-08-11 03:29:46', 7, 'Tagihan Indihome', 'WFI370999001', 'tomi', 29000, 'sukses'),
(12, '2022-08-11 03:34:17', 1, 'Pembelian Pulsa', '081234567890', 'Telkomsel', 50000, 'sukses'),
(13, '2022-08-11 03:54:07', 1, 'Pembelian Pulsa', '087890123456', 'XL', 50000, 'sukses');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `nama_lengkap` varchar(50) NOT NULL,
  `no_ktp` bigint(17) NOT NULL,
  `jenis_kelamin` varchar(1) NOT NULL,
  `tgl_lahir` date NOT NULL,
  `alamat` text NOT NULL,
  `nama_ayah` varchar(50) NOT NULL,
  `nama_ibu` varchar(50) NOT NULL,
  `number_login` varchar(17) NOT NULL,
  `pin_login` int(5) NOT NULL,
  `saldo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `nama_lengkap`, `no_ktp`, `jenis_kelamin`, `tgl_lahir`, `alamat`, `nama_ayah`, `nama_ibu`, `number_login`, `pin_login`, `saldo`) VALUES
(1, 'Andika Risky Septiawan', 3313123456789101, 'L', '2004-09-10', 'Dawung, Matesih, karanganyar', 'ayahnya', 'ibunya', '9170438674437292', 2345, 292000),
(7, 'Jenny Dwi Nugraini', 1234567890098765, 'P', '2004-01-15', 'Kayangan', 'ayahnya2', 'ibunya2', '7910910544825211', 2345, 23500);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
