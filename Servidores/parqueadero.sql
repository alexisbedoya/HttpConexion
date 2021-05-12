-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 30-01-2021 a las 19:26:47
-- Versión del servidor: 10.4.16-MariaDB
-- Versión de PHP: 7.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `parqueadero`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `listado`
--

CREATE TABLE `listado` (
  `nombre` varchar(50) NOT NULL,
  `cedula` varchar(50) NOT NULL,
  `placa` varchar(50) NOT NULL,
  `estado` tinyint(1) NOT NULL,
  `id` int(11) NOT NULL,
  `horaEntrada` datetime NOT NULL DEFAULT current_timestamp(),
  `horaSalida` datetime DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `listado`
--

INSERT INTO `listado` (`nombre`, `cedula`, `placa`, `estado`, `id`, `horaEntrada`, `horaSalida`) VALUES
('alex', '10074020', 'XVF100', 0, 1, '2021-01-29 10:31:02', '2021-01-29 13:30:11'),
('$nombre', '$cedula', '$placa', 0, 2, '2021-01-29 11:02:49', '2021-01-30 09:24:31'),
('prueba', '123', '123mj', 0, 23, '2021-01-30 09:54:29', '2021-01-30 09:55:46'),
('prueba', '123', '123mj', 1, 24, '2021-01-30 10:09:23', NULL),
('12', '12', '12', 1, 25, '2021-01-30 10:57:23', NULL),
('fabi ', '233321', 'VTSh3223', 1, 26, '2021-01-30 11:03:54', NULL),
('123', '123', '123', 0, 27, '2021-01-30 13:14:22', '2021-01-30 13:19:58');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `listado`
--
ALTER TABLE `listado`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `listado`
--
ALTER TABLE `listado`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
