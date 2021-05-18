-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 18-05-2021 a las 20:29:10
-- Versión del servidor: 8.0.20
-- Versión de PHP: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `db`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE `persona` (
  `id` bigint NOT NULL,
  `apellido` varchar(255) DEFAULT NULL,
  `fechaNacimiento` datetime(6) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `numeroDocumento` varchar(255) DEFAULT NULL,
  `tipoDocumento` varchar(255) DEFAULT NULL,
  `sexo` varchar(255) DEFAULT NULL,
  `numeroAfiliado` varchar(255) DEFAULT NULL,
  `matricula` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `persona`
--

INSERT INTO `persona` (`id`, `apellido`, `fechaNacimiento`, `nombre`, `numeroDocumento`, `tipoDocumento`, `sexo`, `numeroAfiliado`, `matricula`) VALUES
(1, 'Jimenez', '1997-04-18 00:00:00.000000', 'Enzo', '40258515', 'DNI', 'Masculino', '1234', '987654321'),
(2, 'Gomez', '1980-05-05 00:00:00.000000', 'Medico', '34877212', 'DNI', 'Masculino', '1111', '876543219'),
(3, 'Saraza', '1990-05-12 00:00:00.000000', 'Paciente', '23498723', 'DNI', 'Femenino', '2222', NULL),
(4, 'PacienteApellido', '1980-05-05 00:00:00.000000', 'PacienteNombre', '40258888', 'DNI', 'Masculino', '5555', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ubicacion`
--

CREATE TABLE `ubicacion` (
  `id` bigint NOT NULL,
  `lat_actual` float NOT NULL,
  `long_actual` float NOT NULL,
  `usuario_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `ubicacion`
--

INSERT INTO `ubicacion` (`id`, `lat_actual`, `long_actual`, `usuario_id`) VALUES
(1, -34.6527, -58.6178, 1),
(2, -34.6699, -58.5622, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` bigint NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `rol` varchar(255) DEFAULT NULL,
  `persona_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `email`, `password`, `rol`, `persona_id`) VALUES
(1, 'enzo-jimenez@hotmail.com', '$2a$10$ayVqBpgb6zazFbgtKXI6hesKmwT/y.F7CTDC6qWT8UyRE5BgX7PZW', 'Medico', 1),
(2, 'paciente@paciente.com', '$2a$10$0JAvMxPGYu/GTz8U0rzFdunk5DpkQz3Tg/D.cJ6zFj7LuFJSqb882', 'Paciente', 3),
(3, 'medico@medico.com', '$2a$10$KQ4tWRl9hCeCAwObDfLwdu3yTpD3586CCvK0NOk7aH41OeJuR4.SC', 'Medico', 2),
(4, 'paciente2@paciente.com', '$2a$10$NxkSjDemweL70j6D8spWDOvHslXCv5t5Eo7xBAGhlCEA.h8RV8hAG', 'Paciente', 4);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `ubicacion`
--
ALTER TABLE `ubicacion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8yjql6sdrvc6530ffhbh4edk` (`usuario_id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpwmov35tuwavb70fabk8iusg` (`persona_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `persona`
--
ALTER TABLE `persona`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `ubicacion`
--
ALTER TABLE `ubicacion`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `ubicacion`
--
ALTER TABLE `ubicacion`
  ADD CONSTRAINT `FK8yjql6sdrvc6530ffhbh4edk` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `FKpwmov35tuwavb70fabk8iusg` FOREIGN KEY (`persona_id`) REFERENCES `persona` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
