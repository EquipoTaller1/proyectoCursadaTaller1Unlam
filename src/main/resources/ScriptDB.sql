-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-05-2021 a las 21:45:08
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
-- Estructura de tabla para la tabla `cita`
--

CREATE TABLE `cita` (
  `id` bigint NOT NULL,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `tipoCita_id` bigint DEFAULT NULL,
  `especialidad_id` bigint DEFAULT NULL,
  `medico_id` bigint DEFAULT NULL,
  `paciente_id` bigint DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `cita`
--

INSERT INTO `cita` (`id`, `fecha`, `hora`, `tipoCita_id`, `especialidad_id`, `medico_id`, `paciente_id`, `created_at`, `updated_at`) VALUES
(1, '2021-05-12', '18:00:00', 1, 1, 1, 2, '2021-05-21 21:03:26.000000', NULL),
(2, '2021-05-28', '18:00:00', 1, 2, 3, 2, '2021-05-24 20:19:27.000000', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `citahistoria`
--

CREATE TABLE `citahistoria` (
  `id` bigint NOT NULL,
  `cita_id` bigint DEFAULT NULL,
  `estado_id` bigint DEFAULT NULL,
  `observacion` varchar(255) DEFAULT NULL,
  `archivo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `citahistoria`
--

INSERT INTO `citahistoria` (`id`, `cita_id`, `estado_id`, `observacion`, `archivo`, `created_at`) VALUES
(1, 1, 1, 'Creado', NULL, '2021-05-21 21:03:26.000000'),
(2, 1, 3, 'Se deja constancia que se atendio al paciente bla bla', NULL, '2021-05-21 21:03:43.000000'),
(3, 2, 1, 'Creado', NULL, '2021-05-24 20:20:22.000000');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `especialidad`
--

CREATE TABLE `especialidad` (
  `id` bigint NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `especialidad`
--

INSERT INTO `especialidad` (`id`, `descripcion`) VALUES
(1, 'Cardiologia'),
(2, 'Clinico');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `especialidad_medico`
--

CREATE TABLE `especialidad_medico` (
  `especialidad_id` bigint NOT NULL,
  `medico_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `especialidad_medico`
--

INSERT INTO `especialidad_medico` (`especialidad_id`, `medico_id`) VALUES
(1, 1),
(2, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estado`
--

CREATE TABLE `estado` (
  `id` bigint NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `estado`
--

INSERT INTO `estado` (`id`, `descripcion`) VALUES
(1, 'Creado'),
(2, 'Cancelado'),
(3, 'Atendido');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE `persona` (
  `id` bigint NOT NULL,
  `nombre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `apellido` varchar(255) DEFAULT NULL,
  `tipoDocumento` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `numeroDocumento` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `sexo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `fechaNacimiento` date DEFAULT NULL,
  `numeroAfiliado` varchar(255) DEFAULT NULL,
  `matricula` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `persona`
--

INSERT INTO `persona` (`id`, `nombre`, `apellido`, `tipoDocumento`, `numeroDocumento`, `sexo`, `fechaNacimiento`, `numeroAfiliado`, `matricula`) VALUES
(1, 'Enzo', 'Jimenez', 'DNI', '40258515', 'Masculino', '1997-04-18', '1234', '987654321'),
(2, 'Medico', 'Gomez', 'DNI', '34877212', 'Masculino', '1980-05-05', '1111', '876543219'),
(3, 'Paciente', 'Saraza', 'DNI', '23498723', 'Femenino', '1990-05-12', '2222', NULL),
(4, 'PacienteNombre', 'PacienteApellido', 'DNI', '40258888', 'Masculino', '1980-05-05', '5555', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipocita`
--

CREATE TABLE `tipocita` (
  `id` bigint NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `tipocita`
--

INSERT INTO `tipocita` (`id`, `descripcion`) VALUES
(1, 'Programada'),
(2, 'Urgencia');

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
-- Indices de la tabla `cita`
--
ALTER TABLE `cita`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKh0xa3kjv8bt1c87f8c3wwkrv1` (`especialidad_id`),
  ADD KEY `FKh3hpw8ld8953mlx2hadyxsglg` (`medico_id`),
  ADD KEY `FKkcdsbj014ee29mjph2humj4d0` (`paciente_id`),
  ADD KEY `FKn3o2v6825xj424a4c0xv36u23` (`tipoCita_id`);

--
-- Indices de la tabla `citahistoria`
--
ALTER TABLE `citahistoria`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKj0hv3g8m3o86c7twacej45k3n` (`cita_id`),
  ADD KEY `FK2l1qtf6dlo9jf2dnlxrslqnuh` (`estado_id`);

--
-- Indices de la tabla `especialidad`
--
ALTER TABLE `especialidad`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `especialidad_medico`
--
ALTER TABLE `especialidad_medico`
  ADD PRIMARY KEY (`especialidad_id`,`medico_id`),
  ADD KEY `FKs2hptidhhor9l62s77sc56otl` (`medico_id`);

--
-- Indices de la tabla `estado`
--
ALTER TABLE `estado`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tipocita`
--
ALTER TABLE `tipocita`
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
-- AUTO_INCREMENT de la tabla `cita`
--
ALTER TABLE `cita`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `citahistoria`
--
ALTER TABLE `citahistoria`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `especialidad`
--
ALTER TABLE `especialidad`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `estado`
--
ALTER TABLE `estado`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `persona`
--
ALTER TABLE `persona`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `tipocita`
--
ALTER TABLE `tipocita`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

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
-- Filtros para la tabla `cita`
--
ALTER TABLE `cita`
  ADD CONSTRAINT `FKh0xa3kjv8bt1c87f8c3wwkrv1` FOREIGN KEY (`especialidad_id`) REFERENCES `especialidad` (`id`),
  ADD CONSTRAINT `FKh3hpw8ld8953mlx2hadyxsglg` FOREIGN KEY (`medico_id`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `FKkcdsbj014ee29mjph2humj4d0` FOREIGN KEY (`paciente_id`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `FKn3o2v6825xj424a4c0xv36u23` FOREIGN KEY (`tipoCita_id`) REFERENCES `tipocita` (`id`);

--
-- Filtros para la tabla `citahistoria`
--
ALTER TABLE `citahistoria`
  ADD CONSTRAINT `FK2l1qtf6dlo9jf2dnlxrslqnuh` FOREIGN KEY (`estado_id`) REFERENCES `estado` (`id`),
  ADD CONSTRAINT `FKj0hv3g8m3o86c7twacej45k3n` FOREIGN KEY (`cita_id`) REFERENCES `cita` (`id`);

--
-- Filtros para la tabla `especialidad_medico`
--
ALTER TABLE `especialidad_medico`
  ADD CONSTRAINT `FKocg7yj9ixqa7qxabdqqnxw9hk` FOREIGN KEY (`especialidad_id`) REFERENCES `especialidad` (`id`),
  ADD CONSTRAINT `FKs2hptidhhor9l62s77sc56otl` FOREIGN KEY (`medico_id`) REFERENCES `usuario` (`id`);

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
