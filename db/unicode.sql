-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 03, 2022 at 01:13 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `unicode`
--

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE `event` (
  `id` int(4) NOT NULL,
  `name` varchar(250) NOT NULL,
  `description` text NOT NULL,
  `longdescription` text NOT NULL,
  `kategori` text NOT NULL,
  `kegiatan` text NOT NULL,
  `image` varchar(250) NOT NULL,
  `lokasi` text NOT NULL,
  `tiket` text NOT NULL,
  `waktu` text NOT NULL,
  `cp` text NOT NULL,
  `link` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`id`, `name`, `description`, `longdescription`, `kategori`, `kegiatan`, `image`, `lokasi`, `tiket`, `waktu`, `cp`, `link`) VALUES
(97, 'Kemakom Mengaji', 'Tadarus', 'Tadarus Bersama Kemakom', 'Sosial', 'Online', 'event3.jpg', 'Online', 'Gratis', '13/12/2021', 'wa.me/62881023018857', 'bit.ly/GrupKemakomMengaji'),
(98, '40 Hari Kebaikan', 'Aksi berdonasi', 'Aksi berdonasi selama 40 hari. Donasi disalurkan untuk mahasiswa FPMIPA UPI yang membutuhkan bantuan.', 'Sosial', 'Online', 'event2.jpg', 'Online', 'Rp1.000,-', '24/01/2022', 'wa.me/6289639434829', 'bit.ly/40HK2021'),
(99, 'Penelitian Skripsi', 'Membutuhkan Responden', 'Hallo! Saya Rima Ridhayanti Utami Mahasiswa S1 Bimbingan Konseling Universitas Pendidikan Indonesia. Saat ini saya sedang mengerjakan tugas akhir skripsi dan membutuhkan responden mahasiswa/i Universitas Pendidikan Indonesia yang tengah menempuh semester 3 s.d. 7', 'Workshop', 'Online', 'event5.jpg', 'Online', 'Gratis', '04/01/2022', 'wa.me/62881023018857', 'forms.gle/4TRHESRAULRBBNUL6');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `event`
--
ALTER TABLE `event`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `event`
--
ALTER TABLE `event`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=104;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
