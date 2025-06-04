-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 04, 2025 at 10:08 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `remsdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `bid` int(11) NOT NULL,
  `title` varchar(200) NOT NULL,
  `author` varchar(50) NOT NULL,
  `category` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`bid`, `title`, `author`, `category`) VALUES
(10, 'to kill a mockingbird', 'harper lee', 'Fiction'),
(11, 'clean code: a handbook on agile software craftmanship', 'robert c. martin', 'Programming'),
(12, 'the hobbit', 'j.r.r. tolkien', 'Fantasy'),
(13, 'the silent patient', 'alex michaelides', 'Thriller'),
(14, 'sapiens: a brief history of human kind', 'yuval noah harari', 'History'),
(15, 'the diary of a young girl', 'anne frank', 'Biography');

-- --------------------------------------------------------

--
-- Table structure for table `borrowed`
--

CREATE TABLE `borrowed` (
  `id` int(11) NOT NULL,
  `bookBorrowed` int(11) NOT NULL,
  `borrower` int(11) NOT NULL,
  `dateBorrowed` datetime NOT NULL DEFAULT current_timestamp(),
  `DTreturned` datetime DEFAULT NULL,
  `status` enum('borrowed','returned') NOT NULL DEFAULT 'borrowed'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `borrowed`
--

INSERT INTO `borrowed` (`id`, `bookBorrowed`, `borrower`, `dateBorrowed`, `DTreturned`, `status`) VALUES
(12, 14, 4, '2025-06-04 15:28:39', '2025-06-04 15:29:53', 'returned'),
(13, 11, 8, '2025-06-04 15:29:05', '2025-06-04 15:29:50', 'returned'),
(14, 15, 8, '2025-06-04 15:29:12', '2025-06-04 15:29:46', 'returned'),
(15, 15, 4, '2025-06-04 15:46:54', '2025-06-04 15:47:43', 'returned'),
(16, 13, 9, '2025-06-04 15:47:18', '2025-06-04 15:47:48', 'returned');

-- --------------------------------------------------------

--
-- Table structure for table `members`
--

CREATE TABLE `members` (
  `id` int(11) NOT NULL,
  `lname` varchar(50) NOT NULL,
  `fname` varchar(50) NOT NULL,
  `middleinitial` varchar(2) NOT NULL,
  `extension` varchar(5) NOT NULL,
  `address` varchar(50) NOT NULL,
  `phone` varchar(12) NOT NULL,
  `DTAdded` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `members`
--

INSERT INTO `members` (`id`, `lname`, `fname`, `middleinitial`, `extension`, `address`, `phone`, `DTAdded`) VALUES
(4, 'ventura', 'remy dianne', 'i', '', 'santa ana', '09565782150', '2025-06-03 20:56:09'),
(8, 'dutdut', 'mary grace', 'v', '', 'santa ana', '8786', '2025-06-04 15:27:51'),
(9, 'carrera', 'althea', 'a', '', 'santa ana', '09565792150', '2025-06-04 15:41:17');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`bid`);

--
-- Indexes for table `borrowed`
--
ALTER TABLE `borrowed`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bb_id` (`bookBorrowed`),
  ADD KEY `b_id` (`borrower`);

--
-- Indexes for table `members`
--
ALTER TABLE `members`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `bid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `borrowed`
--
ALTER TABLE `borrowed`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `members`
--
ALTER TABLE `members`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `borrowed`
--
ALTER TABLE `borrowed`
  ADD CONSTRAINT `b_id` FOREIGN KEY (`borrower`) REFERENCES `members` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `bb_id` FOREIGN KEY (`bookBorrowed`) REFERENCES `books` (`bid`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
