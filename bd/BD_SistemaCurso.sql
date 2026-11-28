DROP DATABASE IF EXISTS BD_SistemaCurso;

CREATE DATABASE BD_SistemaCurso
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE BD_SistemaCurso;

-- =====================================================================
-- TABLAS
-- =====================================================================

CREATE TABLE TB_Especialidad (
  idEspecialidad  INT          NOT NULL AUTO_INCREMENT,
  nombre          VARCHAR(30)  NOT NULL,
  PRIMARY KEY (idEspecialidad),
  UNIQUE KEY uk_especialidad_nombre (nombre)
);

CREATE TABLE TB_Docente (
  idDocente       INT           NOT NULL AUTO_INCREMENT,
  nombreCompleto  VARCHAR(60)   NOT NULL,
  correo          VARCHAR(80)   NOT NULL,
  idEspecialidad  INT           NOT NULL,
  horasSemanales  INT           NOT NULL,
  tarifaHora      DECIMAL(10,2) NOT NULL,
  fechaIngreso    DATE          NOT NULL,
  PRIMARY KEY (idDocente),
  UNIQUE KEY uk_docente_correo (correo),
  CONSTRAINT fk_docente_especialidad FOREIGN KEY (idEspecialidad)
      REFERENCES TB_Especialidad (idEspecialidad),
  CONSTRAINT ck_docente_horas  CHECK (horasSemanales BETWEEN 4 AND 40),
  CONSTRAINT ck_docente_tarifa CHECK (tarifaHora BETWEEN 25.00 AND 200.00)
);

CREATE TABLE TB_Curso (
  idCurso    INT          NOT NULL AUTO_INCREMENT,
  codigo     VARCHAR(10)  NOT NULL,
  nombre     VARCHAR(100) NOT NULL,
  nivel      VARCHAR(20)  NOT NULL,
  idDocente  INT          NOT NULL,
  PRIMARY KEY (idCurso),
  UNIQUE KEY uk_curso_codigo (codigo),
  CONSTRAINT fk_curso_docente FOREIGN KEY (idDocente)
      REFERENCES TB_Docente (idDocente),
  CONSTRAINT ck_curso_nivel CHECK (nivel IN ('basico', 'intermedio', 'avanzado'))
);

CREATE TABLE TB_Rol (
  idRol        INT          NOT NULL AUTO_INCREMENT,
  nombre       VARCHAR(30)  NOT NULL,
  descripcion  VARCHAR(100) NULL,
  PRIMARY KEY (idRol),
  UNIQUE KEY uk_rol_nombre (nombre)
);

CREATE TABLE TB_Usuario (
  idUsuario      INT          NOT NULL AUTO_INCREMENT,
  usuario        VARCHAR(30)  NOT NULL,
  clave          CHAR(64)     NOT NULL,
  nombres        VARCHAR(50)  NOT NULL,
  apellidos      VARCHAR(50)  NOT NULL,
  correo         VARCHAR(80)  NOT NULL,
  idRol          INT          NOT NULL,
  idDocente      INT          NULL,
  estado         TINYINT(1)   NOT NULL DEFAULT 1,
  fechaRegistro  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  ultimoAcceso   DATETIME     NULL,
  PRIMARY KEY (idUsuario),
  UNIQUE KEY uk_usuario_usuario (usuario),
  UNIQUE KEY uk_usuario_correo (correo),
  CONSTRAINT fk_usuario_rol FOREIGN KEY (idRol)
      REFERENCES TB_Rol (idRol),
  CONSTRAINT fk_usuario_docente FOREIGN KEY (idDocente)
      REFERENCES TB_Docente (idDocente)
);

-- =====================================================================
-- DATOS
-- =====================================================================

INSERT INTO TB_Especialidad (idEspecialidad, nombre) VALUES
  (1, 'Programación'),
  (2, 'Base de Datos'),
  (3, 'Redes'),
  (4, 'Diseño Web'),
  (5, 'Ciberseguridad'),
  (6, 'Inteligencia Artificial'),
  (7, 'Cloud Computing'),
  (8, 'Gestión de Proyectos');

INSERT INTO TB_Docente (idDocente, nombreCompleto, correo, idEspecialidad, horasSemanales, tarifaHora, fechaIngreso) VALUES
  (1,  'Juan Lopez',     'jlopez@cibertec.edu.pe',    5, 20, 50.00, '2021-03-01'),
  (2,  'Roberto Gomez',  'rgomez@cibertec.edu.pe',    1, 16, 42.50, '2022-08-15'),
  (3,  'Julio Alvarez',  'jalvarez@cibertec.edu.pe',  1, 24, 45.00, '2020-01-10'),
  (4,  'Jose Lopez',     'jolopez@cibertec.edu.pe',   4, 12, 38.00, '2023-04-20'),
  (5,  'Juan Diaz',      'jdiaz@cibertec.edu.pe',     5, 18, 55.00, '2022-02-01'),
  (6,  'Jorge Ventura',  'jventura@cibertec.edu.pe',  4, 20, 40.00, '2024-03-11'),
  (7,  'Diana Ulloa',    'dulloa@cibertec.edu.pe',    2, 16, 48.75, '2021-09-06'),
  (8,  'Carla Mendoza',  'cmendoza@cibertec.edu.pe',  6, 20, 60.00, '2022-05-16'),
  (9,  'Luis Paredes',   'lparedes@cibertec.edu.pe',  3, 24, 44.00, '2019-08-05'),
  (10, 'Ana Quispe',     'aquispe@cibertec.edu.pe',   2, 18, 47.50, '2023-03-13'),
  (11, 'Miguel Castro',  'mcastro@cibertec.edu.pe',   7, 16, 65.00, '2024-01-08'),
  (12, 'Rosa Huaman',    'rhuaman@cibertec.edu.pe',   8, 12, 52.00, '2020-10-19'),
  (13, 'Pedro Salazar',  'psalazar@cibertec.edu.pe',  1, 30, 46.00, '2018-04-02'),
  (14, 'Lucia Rojas',    'lrojas@cibertec.edu.pe',    4, 20, 41.50, '2025-02-17'),
  (15, 'Fernando Vega',  'fvega@cibertec.edu.pe',     5,  8, 70.00, '2025-08-04');

INSERT INTO TB_Curso (idCurso, codigo, nombre, nivel, idDocente) VALUES
  (1,  'C454',  'Seguridad de Aplicaciones',          'avanzado',   1),
  (2,  'C2626', 'Visual Basic',                       'intermedio', 2),
  (3,  'C1515', 'Java',                               'basico',     3),
  (4,  'C1212', 'Aplicaciones Móviles Android',       'basico',     4),
  (5,  'C1518', 'OWASP',                              'avanzado',   5),
  (6,  'C4554', 'Aplicaciones Móviles iOS',           'basico',     6),
  (7,  'C4559', 'Python',                             'basico',     7),
  (8,  'C2010', 'Base de Datos MySQL',                'basico',     10),
  (9,  'C2011', 'SQL Server Avanzado',                'avanzado',   7),
  (10, 'C3020', 'Redes y Comunicaciones',             'intermedio', 9),
  (11, 'C3021', 'Cisco CCNA',                         'avanzado',   9),
  (12, 'C4030', 'HTML5 y CSS3',                       'basico',     14),
  (13, 'C4031', 'JavaScript y React',                 'intermedio', 6),
  (14, 'C5040', 'Ethical Hacking',                    'avanzado',   15),
  (15, 'C6050', 'Machine Learning con Python',        'intermedio', 8),
  (16, 'C6051', 'Inteligencia Artificial Generativa', 'avanzado',   8),
  (17, 'C7060', 'AWS Cloud Practitioner',             'basico',     11),
  (18, 'C7061', 'Docker y Kubernetes',                'intermedio', 11),
  (19, 'C8070', 'Gestión Ágil con Scrum',             'basico',     12),
  (20, 'C1520', 'Lenguaje de Programación I',         'intermedio', 13),
  (21, 'C1521', 'Spring Boot',                        'avanzado',   2);

INSERT INTO TB_Rol (idRol, nombre, descripcion) VALUES
  (1, 'ADMINISTRADOR', 'Acceso total al sistema'),
  (2, 'COORDINADOR',   'Gestiona cursos y docentes'),
  (3, 'DOCENTE',       'Consulta sus cursos y sus datos');

-- Claves: admin123, coord123, docente123, inactivo123 (cifradas con SHA2)
INSERT INTO TB_Usuario (idUsuario, usuario, clave, nombres, apellidos, correo, idRol, idDocente, estado) VALUES
  (1, 'admin',       SHA2('admin123', 256),    'Administrador', 'del Sistema', 'admin@cibertec.edu.pe',    1, NULL, 1),
  (2, 'coordinador', SHA2('coord123', 256),    'Maria',         'Torres',      'mtorres@cibertec.edu.pe',  2, NULL, 1),
  (3, 'jperez',      SHA2('coord123', 256),    'Jorge',         'Perez',       'jperez@cibertec.edu.pe',   2, NULL, 1),
  (4, 'jlopez',      SHA2('docente123', 256),  'Juan',          'Lopez',       'jlopez@cibertec.edu.pe',   3, 1,    1),
  (5, 'dulloa',      SHA2('docente123', 256),  'Diana',         'Ulloa',       'dulloa@cibertec.edu.pe',   3, 7,    1),
  (6, 'rgomez',      SHA2('docente123', 256),  'Roberto',       'Gomez',       'rgomez@cibertec.edu.pe',   3, 2,    1),
  (7, 'cmendoza',    SHA2('docente123', 256),  'Carla',         'Mendoza',     'cmendoza@cibertec.edu.pe', 3, 8,    1),
  (8, 'psalazar',    SHA2('docente123', 256),  'Pedro',         'Salazar',     'psalazar@cibertec.edu.pe', 3, 13,   1),
  (9, 'inactivo',    SHA2('inactivo123', 256), 'Usuario',       'Bloqueado',   'inactivo@cibertec.edu.pe', 3, NULL, 0);

-- =====================================================================
-- PROCEDIMIENTOS: ESPECIALIDADES
-- =====================================================================

CREATE PROCEDURE sp_listar_especialidades()
BEGIN
  SELECT idEspecialidad, nombre
  FROM TB_Especialidad
  ORDER BY nombre;
END;

-- =====================================================================
-- PROCEDIMIENTOS: DOCENTES
-- =====================================================================

CREATE PROCEDURE sp_listar_docentes()
BEGIN
  SELECT d.idDocente, d.nombreCompleto, d.correo,
         d.idEspecialidad, e.nombre AS especialidad,
         d.horasSemanales, d.tarifaHora, d.fechaIngreso
  FROM TB_Docente d
  INNER JOIN TB_Especialidad e ON d.idEspecialidad = e.idEspecialidad
  ORDER BY d.idDocente;
END;

CREATE PROCEDURE sp_obtener_docente(IN p_idDocente INT)
BEGIN
  SELECT d.idDocente, d.nombreCompleto, d.correo,
         d.idEspecialidad, e.nombre AS especialidad,
         d.horasSemanales, d.tarifaHora, d.fechaIngreso
  FROM TB_Docente d
  INNER JOIN TB_Especialidad e ON d.idEspecialidad = e.idEspecialidad
  WHERE d.idDocente = p_idDocente;
END;

CREATE PROCEDURE sp_registrar_docente(
  IN p_nombreCompleto VARCHAR(60),
  IN p_correo         VARCHAR(80),
  IN p_idEspecialidad INT,
  IN p_horasSemanales INT,
  IN p_tarifaHora     DECIMAL(10,2),
  IN p_fechaIngreso   DATE
)
BEGIN
  INSERT INTO TB_Docente (nombreCompleto, correo, idEspecialidad, horasSemanales, tarifaHora, fechaIngreso)
  VALUES (p_nombreCompleto, p_correo, p_idEspecialidad, p_horasSemanales, p_tarifaHora, p_fechaIngreso);
END;

CREATE PROCEDURE sp_actualizar_docente(
  IN p_idDocente      INT,
  IN p_nombreCompleto VARCHAR(60),
  IN p_correo         VARCHAR(80),
  IN p_idEspecialidad INT,
  IN p_horasSemanales INT,
  IN p_tarifaHora     DECIMAL(10,2),
  IN p_fechaIngreso   DATE
)
BEGIN
  UPDATE TB_Docente
  SET nombreCompleto = p_nombreCompleto,
      correo         = p_correo,
      idEspecialidad = p_idEspecialidad,
      horasSemanales = p_horasSemanales,
      tarifaHora     = p_tarifaHora,
      fechaIngreso   = p_fechaIngreso
  WHERE idDocente = p_idDocente;
END;

CREATE PROCEDURE sp_eliminar_docente(IN p_idDocente INT)
BEGIN
  DELETE FROM TB_Docente WHERE idDocente = p_idDocente;
END;

CREATE PROCEDURE sp_existe_correo_docente(
  IN p_correo    VARCHAR(80),
  IN p_idExcluir INT
)
BEGIN
  SELECT COUNT(*) AS total
  FROM TB_Docente
  WHERE correo = p_correo AND idDocente <> p_idExcluir;
END;

-- =====================================================================
-- PROCEDIMIENTOS: CURSOS
-- =====================================================================

CREATE PROCEDURE sp_listar_cursos()
BEGIN
  SELECT c.idCurso, c.codigo, c.nombre, c.nivel,
         c.idDocente, d.nombreCompleto AS docente
  FROM TB_Curso c
  INNER JOIN TB_Docente d ON c.idDocente = d.idDocente
  ORDER BY c.idCurso;
END;

CREATE PROCEDURE sp_obtener_curso(IN p_idCurso INT)
BEGIN
  SELECT c.idCurso, c.codigo, c.nombre, c.nivel,
         c.idDocente, d.nombreCompleto AS docente
  FROM TB_Curso c
  INNER JOIN TB_Docente d ON c.idDocente = d.idDocente
  WHERE c.idCurso = p_idCurso;
END;

CREATE PROCEDURE sp_registrar_curso(
  IN p_codigo    VARCHAR(10),
  IN p_nombre    VARCHAR(100),
  IN p_nivel     VARCHAR(20),
  IN p_idDocente INT
)
BEGIN
  INSERT INTO TB_Curso (codigo, nombre, nivel, idDocente)
  VALUES (p_codigo, p_nombre, p_nivel, p_idDocente);
END;

CREATE PROCEDURE sp_actualizar_curso(
  IN p_idCurso   INT,
  IN p_codigo    VARCHAR(10),
  IN p_nombre    VARCHAR(100),
  IN p_nivel     VARCHAR(20),
  IN p_idDocente INT
)
BEGIN
  UPDATE TB_Curso
  SET codigo    = p_codigo,
      nombre    = p_nombre,
      nivel     = p_nivel,
      idDocente = p_idDocente
  WHERE idCurso = p_idCurso;
END;

CREATE PROCEDURE sp_eliminar_curso(IN p_idCurso INT)
BEGIN
  DELETE FROM TB_Curso WHERE idCurso = p_idCurso;
END;

-- =====================================================================
-- PROCEDIMIENTOS: USUARIOS (LOGIN Y SESION)
-- =====================================================================

CREATE PROCEDURE sp_login(
  IN p_usuario VARCHAR(30),
  IN p_clave   VARCHAR(50)
)
BEGIN
  SELECT u.idUsuario, u.usuario, u.nombres, u.apellidos, u.correo,
         u.idRol, r.nombre AS rol, u.idDocente, u.ultimoAcceso
  FROM TB_Usuario u
  INNER JOIN TB_Rol r ON u.idRol = r.idRol
  WHERE u.usuario = p_usuario
    AND u.clave = SHA2(p_clave, 256)
    AND u.estado = 1;
END;

CREATE PROCEDURE sp_registrar_acceso(IN p_idUsuario INT)
BEGIN
  UPDATE TB_Usuario SET ultimoAcceso = NOW() WHERE idUsuario = p_idUsuario;
END;

CREATE PROCEDURE sp_obtener_usuario(IN p_idUsuario INT)
BEGIN
  SELECT u.idUsuario, u.usuario, u.nombres, u.apellidos, u.correo,
         u.idRol, r.nombre AS rol, u.idDocente, u.ultimoAcceso
  FROM TB_Usuario u
  INNER JOIN TB_Rol r ON u.idRol = r.idRol
  WHERE u.idUsuario = p_idUsuario;
END;

CREATE PROCEDURE sp_actualizar_datos_usuario(
  IN p_idUsuario INT,
  IN p_nombres   VARCHAR(50),
  IN p_apellidos VARCHAR(50),
  IN p_correo    VARCHAR(80)
)
BEGIN
  UPDATE TB_Usuario
  SET nombres   = p_nombres,
      apellidos = p_apellidos,
      correo    = p_correo
  WHERE idUsuario = p_idUsuario;
END;

-- =====================================================================
-- COMPROBACION
-- =====================================================================

CALL sp_listar_especialidades();
CALL sp_listar_docentes();
CALL sp_listar_cursos();
CALL sp_login('admin', 'admin123');