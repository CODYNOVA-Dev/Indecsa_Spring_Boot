-- ============================================================
--  INDECSA · Base de Datos v4.2 (Normalizacion Total + Imagenes)
--  Fecha: 2026-05-15 (Ajuste de Caracteres)
-- ============================================================

DROP DATABASE IF EXISTS indecsa;
CREATE DATABASE IF NOT EXISTS indecsa 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;
USE indecsa;

-- ===================================
-- 1. CATALOGOS BASE
-- ===================================

CREATE TABLE Estado (
    id_estado   INT AUTO_INCREMENT PRIMARY KEY,
    nombre_est  VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO Estado (nombre_est) VALUES ('CDMX'), ('Hidalgo'), ('Puebla');

CREATE TABLE Domicilio (
    id_domicilio INT AUTO_INCREMENT PRIMARY KEY,
    calle        VARCHAR(100) NOT NULL,
    num_ext      VARCHAR(10)  NOT NULL,
    num_int      VARCHAR(10)  NULL,
    colonia      VARCHAR(100) NOT NULL,
    cod_post     INT          NOT NULL,
    mun_alc      VARCHAR(100) NOT NULL,
    id_estado    INT          NOT NULL,
    FOREIGN KEY (id_estado) REFERENCES Estado(id_estado) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE Rol (
    id_rol          INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol      ENUM('ADMIN','CAPITAL_HUMANO') NOT NULL UNIQUE,
    descripcion_rol VARCHAR(255) DEFAULT 'Sin descripcion'
);

INSERT INTO Rol (nombre_rol, descripcion_rol) VALUES 
('ADMIN', 'Acceso total al sistema'),
('CAPITAL_HUMANO', 'Gestion de personal y asignaciones');

-- ===================================
-- 2. ENTIDADES PRINCIPALES
-- ===================================

CREATE TABLE Empleado (
    id_empleado     INT AUTO_INCREMENT PRIMARY KEY,
    nombre_empleado VARCHAR(100) NOT NULL,
    curp            VARCHAR(18)  NOT NULL UNIQUE,
    correo_empleado VARCHAR(100) NOT NULL UNIQUE,
    contrasena      VARCHAR(255) NOT NULL,
    foto_perfil_url VARCHAR(255) NULL,
    id_rol          INT NOT NULL,
    FOREIGN KEY (id_rol) REFERENCES Rol(id_rol) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE Contratista (
    id_contratista           INT AUTO_INCREMENT PRIMARY KEY,
    nombre_contratista       VARCHAR(100) NOT NULL,
    curp                     VARCHAR(18)  NOT NULL,
    rfc_contratista          VARCHAR(15)  NOT NULL UNIQUE,
    telefono_contratista     VARCHAR(15)  NOT NULL,
    correo_contratista       VARCHAR(100) NOT NULL UNIQUE,
    descripcion_contratista  VARCHAR(255) NOT NULL,
    foto_perfil_url          VARCHAR(255) NULL,
    experiencia              VARCHAR(200),
    calificacion_contratista TINYINT CHECK (calificacion_contratista BETWEEN 1 AND 5),
    estado_contratista       ENUM('ACTIVO','INACTIVO','SUSPENDIDO') NOT NULL DEFAULT 'ACTIVO',
    id_estado_operacion      INT NOT NULL,
    FOREIGN KEY (id_estado_operacion) REFERENCES Estado(id_estado)
);

CREATE TABLE registros_migratorios (
    id_migratorio    INT AUTO_INCREMENT PRIMARY KEY,
    folio_documento  VARCHAR(50)  NOT NULL UNIQUE,
    categoria        ENUM('turismo','negocios','razones_humanitarias','transito','actividades_remuneradas') NOT NULL,
    fecha_emision    DATE NOT NULL,
    dias_vigencia    INT  CHECK (dias_vigencia <= 1460),
    fecha_vencimiento DATE NOT NULL,
    permiso_trabajo  TINYINT(1) NOT NULL DEFAULT 0,
    activo           TINYINT(1) NOT NULL DEFAULT 1
);

-- ===================================
-- 3. PROYECTOS Y TRABAJADORES
-- ===================================

CREATE TABLE Proyecto (
    id_proyecto           INT AUTO_INCREMENT PRIMARY KEY,
    nombre_proyecto       VARCHAR(150) NOT NULL,
    tipo_proyecto         ENUM('Construccion','Remodelacion','Venta_mobiliaria','Instalacion_de_mobiliario'),
    oferta_trabajo        VARCHAR(200),
    cliente               VARCHAR(200) NOT NULL,
    id_domicilio          INT NOT NULL,
    fecha_estimada_inicio DATE,
    fecha_estimada_fin    DATE,
    calificacion_proyecto TINYINT CHECK (calificacion_proyecto BETWEEN 1 AND 5),
    estatus_proyecto      ENUM('PLANEACION','EN_CURSO','PENDIENTE','FINALIZADO','CANCELADO') NOT NULL DEFAULT 'PLANEACION',
    descripcion_proyecto  VARCHAR(500) DEFAULT 'Sin descripcion',
    imagen_proyecto_url   VARCHAR(255) NULL,
    FOREIGN KEY (id_domicilio) REFERENCES Domicilio(id_domicilio) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE Trabajador (
    id_trabajador           INT AUTO_INCREMENT PRIMARY KEY,
    nombre_trabajador       VARCHAR(100) NOT NULL,
    curp                    VARCHAR(18)  NOT NULL UNIQUE,
    rfc                     VARCHAR(13)  NOT NULL UNIQUE,
    nss_trabajador          VARCHAR(11)  UNIQUE,
    nacionalidad            VARCHAR(100) NOT NULL,
    id_migratorio           INT NULL,
    id_domicilio            INT NOT NULL,
    foto_perfil_url         VARCHAR(255) NULL,
    puesto                  VARCHAR(100) NOT NULL,
    desc_puesto             VARCHAR(500) NOT NULL,
    especialidad_trabajador VARCHAR(100) NOT NULL,
    escolaridad             VARCHAR(100) NOT NULL,
    experiencia             VARCHAR(200),
    telefono_trabajador     VARCHAR(15)  NOT NULL,
    correo_trabajador       VARCHAR(100) NOT NULL UNIQUE,
    contratacion            VARCHAR(200) NOT NULL,
    jornada                 VARCHAR(200) NOT NULL,
    estado_trabajador       ENUM('DESCANSO','VACACIONES','INCAPACIDAD','ACTIVO','INACTIVO','BAJA','BOLETINADO') NOT NULL DEFAULT 'ACTIVO',
    evaluacion_trabajador   TINYINT      CHECK (evaluacion_trabajador BETWEEN 1 AND 5),
    fecha_ingreso           DATE         NOT NULL,
    id_estado_calidad_vida  INT NOT NULL,
    sexo                    ENUM('Masculino','Femenino','Otro') NOT NULL,
    ant_penal               VARCHAR(500),
    deudor_alim             VARCHAR(500),
    folio_lic_cond          VARCHAR(20),
    estado_civil            VARCHAR(50),
    idiomas                 VARCHAR(200),
    lengua_indigena         VARCHAR(100),
    FOREIGN KEY (id_migratorio) REFERENCES registros_migratorios(id_migratorio) ON UPDATE CASCADE ON DELETE SET NULL,
    FOREIGN KEY (id_domicilio)  REFERENCES Domicilio(id_domicilio) ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (id_estado_calidad_vida) REFERENCES Estado(id_estado)
);

-- ===================================
-- 4. ASIGNACIONES
-- ===================================

CREATE TABLE Asignacion_Proyecto_Contratista (
    id_asignacion_pc   INT AUTO_INCREMENT PRIMARY KEY,
    id_proyecto        INT          NOT NULL,
    id_contratista     INT          NOT NULL,
    numero_contrato    VARCHAR(50),
    fecha_inicio       DATE,
    fecha_fin_estimada DATE,
    personal_asignado  INT NOT NULL CHECK (personal_asignado > 0),
    estatus_contrato   ENUM('ACTIVO','VIGENTE','SUSPENDIDO','FINALIZADO','CANCELADO') NOT NULL DEFAULT 'VIGENTE',
    observaciones      VARCHAR(500),
    UNIQUE KEY uq_proyecto_contratista (id_proyecto, id_contratista),
    FOREIGN KEY (id_proyecto)    REFERENCES Proyecto(id_proyecto)       ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (id_contratista) REFERENCES Contratista(id_contratista) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Asignacion_Trabajador_Proyecto (
    id_asignacion_tp   INT AUTO_INCREMENT PRIMARY KEY,
    id_trabajador      INT NOT NULL,
    id_proyecto        INT NOT NULL,
    id_asignacion_pc   INT NOT NULL,
    puesto_en_proyecto VARCHAR(100),
    fecha_inicio       DATE,
    fecha_fin_estimada DATE,
    estatus_asignacion ENUM('ACTIVO','SUSPENDIDO','INCAPACIDAD','CANCELADO','VACACIONES','FINALIZADO') NOT NULL DEFAULT 'ACTIVO',
    UNIQUE KEY uq_trabajador_proyecto (id_trabajador, id_proyecto),
    FOREIGN KEY (id_trabajador)    REFERENCES Trabajador(id_trabajador)                         ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (id_proyecto)      REFERENCES Proyecto(id_proyecto)                             ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (id_asignacion_pc) REFERENCES Asignacion_Proyecto_Contratista(id_asignacion_pc) ON UPDATE CASCADE ON DELETE CASCADE
);

-- ===================================
-- 5. RENDIMIENTO DE OBRA
-- ===================================

CREATE TABLE Cuadrilla (
    id_cuadrilla      INT AUTO_INCREMENT PRIMARY KEY,
    id_proyecto       INT NOT NULL,
    nombre_cuadrilla  VARCHAR(100) NOT NULL,
    frente_trabajo    VARCHAR(200),
    estatus_cuadrilla ENUM('ACTIVO','INACTIVO') NOT NULL DEFAULT 'ACTIVO',
    UNIQUE KEY uq_cuadrilla_proyecto (nombre_cuadrilla, id_proyecto),
    FOREIGN KEY (id_proyecto) REFERENCES Proyecto(id_proyecto) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Estandar_Rendimiento (
    id_estandar          INT AUTO_INCREMENT PRIMARY KEY,
    nombre_actividad     VARCHAR(100) NOT NULL,
    unidad_medida        ENUM('m2','m3','ml','piezas','porcentaje') NOT NULL,
    rendimiento_esperado DECIMAL(10,4) NOT NULL
);

CREATE TABLE Registro_Horas (
    id_registro          INT AUTO_INCREMENT PRIMARY KEY,
    id_asignacion_tp     INT NOT NULL,
    id_cuadrilla         INT NULL,
    fecha_registro       DATE NOT NULL,
    horas_trabajadas     DECIMAL(5,2) NOT NULL,
    id_empleado_registro INT NOT NULL,
    CONSTRAINT chk_horas CHECK (horas_trabajadas > 0 AND horas_trabajadas <= 24),
    UNIQUE KEY uq_registro_fecha (id_asignacion_tp, fecha_registro),
    FOREIGN KEY (id_asignacion_tp) REFERENCES Asignacion_Trabajador_Proyecto(id_asignacion_tp) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (id_cuadrilla)     REFERENCES Cuadrilla(id_cuadrilla) ON UPDATE CASCADE ON DELETE SET NULL,
    FOREIGN KEY (id_empleado_registro) REFERENCES Empleado(id_empleado)
);

CREATE TABLE Avance_Partida (
    id_avance            INT AUTO_INCREMENT PRIMARY KEY,
    id_proyecto          INT NOT NULL,
    id_cuadrilla         INT NULL,
    id_estandar          INT NULL,
    nombre_partida       VARCHAR(200) NOT NULL,
    fecha_registro       DATE NOT NULL,
    cantidad_ejecutada   DECIMAL(12,4) NOT NULL,
    id_empleado_registro INT NOT NULL,
    FOREIGN KEY (id_proyecto) REFERENCES Proyecto(id_proyecto) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (id_cuadrilla) REFERENCES Cuadrilla(id_cuadrilla) ON UPDATE CASCADE ON DELETE SET NULL,
    FOREIGN KEY (id_estandar)  REFERENCES Estandar_Rendimiento(id_estandar) ON UPDATE CASCADE ON DELETE SET NULL,
    FOREIGN KEY (id_empleado_registro) REFERENCES Empleado(id_empleado)
);

-- ===================================
-- 6. ROLES Y USUARIOS BD
-- ===================================
DROP ROLE IF EXISTS 'ROL_CAPITALHUMANO','ROL_ADMIN';
CREATE ROLE IF NOT EXISTS 'ROL_ADMIN','ROL_CAPITALHUMANO';

GRANT ALL PRIVILEGES ON indecsa.* TO 'ROL_ADMIN';
GRANT SELECT ON indecsa.Proyecto TO 'ROL_CAPITALHUMANO';
GRANT SELECT ON indecsa.Trabajador TO 'ROL_CAPITALHUMANO';
GRANT SELECT, INSERT, UPDATE ON indecsa.Asignacion_Proyecto_Contratista TO 'ROL_CAPITALHUMANO';
GRANT SELECT, INSERT, UPDATE ON indecsa.Asignacion_Trabajador_Proyecto TO 'ROL_CAPITALHUMANO';

DROP USER IF EXISTS 'Admin_unico'@'%';
CREATE USER 'Admin_unico'@'%' IDENTIFIED BY 'Admin_unico@indecsa.com';
GRANT 'ROL_ADMIN' TO 'Admin_unico'@'%';
SET DEFAULT ROLE 'ROL_ADMIN' TO 'Admin_unico'@'%';

-- ===================================
-- 7. TRIGGERS Y PROCEDIMIENTOS
-- ===================================
DELIMITER //

-- T1: Validar fechas de proyecto
CREATE DEFINER = CURRENT_USER TRIGGER trg_validar_fechas_proyecto
BEFORE INSERT ON Proyecto FOR EACH ROW
BEGIN
    IF NEW.fecha_estimada_fin IS NOT NULL AND NEW.fecha_estimada_fin < NEW.fecha_estimada_inicio THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La fecha de fin no puede ser anterior al inicio.';
    END IF;
END //

-- T2: Cierre automatico de asignaciones
CREATE DEFINER = CURRENT_USER TRIGGER trg_cerrar_asignaciones_finalizacion
AFTER UPDATE ON Proyecto FOR EACH ROW
BEGIN
    IF NEW.estatus_proyecto IN ('FINALIZADO','CANCELADO') AND OLD.estatus_proyecto NOT IN ('FINALIZADO','CANCELADO') THEN
        UPDATE Asignacion_Trabajador_Proyecto 
        SET estatus_asignacion = 'FINALIZADO' 
        WHERE id_proyecto = NEW.id_proyecto AND estatus_asignacion = 'ACTIVO';
        
        UPDATE Asignacion_Proyecto_Contratista 
        SET estatus_contrato = 'FINALIZADO' 
        WHERE id_proyecto = NEW.id_proyecto AND estatus_contrato IN ('ACTIVO', 'VIGENTE');
    END IF;
END //

-- T3: Normalizacion de correos
CREATE DEFINER = CURRENT_USER TRIGGER trg_norm_correo_empleado BEFORE INSERT ON Empleado FOR EACH ROW 
BEGIN SET NEW.correo_empleado = LOWER(TRIM(NEW.correo_empleado)); END //

CREATE DEFINER = CURRENT_USER TRIGGER trg_norm_correo_trabajador BEFORE INSERT ON Trabajador FOR EACH ROW 
BEGIN SET NEW.correo_trabajador = LOWER(TRIM(NEW.correo_trabajador)); END //

-- SP: Crear usuario de BD
CREATE DEFINER = CURRENT_USER PROCEDURE sp_crear_usuario_desde_empleado(IN emp_id INT, IN temp_password VARCHAR(255))
BEGIN
    DECLARE emp_correo VARCHAR(100);
    DECLARE r_nombre VARCHAR(50);
    DECLARE u_name VARCHAR(32);
    
    SELECT e.correo_empleado, r.nombre_rol INTO emp_correo, r_nombre
    FROM Empleado e JOIN Rol r ON e.id_rol = r.id_rol WHERE e.id_empleado = emp_id;
    
    SET u_name = LEFT(SUBSTRING_INDEX(emp_correo, '@', 1), 32);
    SET @s1 = CONCAT('CREATE USER IF NOT EXISTS ''', u_name, '''@''%'' IDENTIFIED BY ''', temp_password, '''');
    PREPARE stmt1 FROM @s1; EXECUTE stmt1; DEALLOCATE PREPARE stmt1;
    
    SET @rol_target = IF(r_nombre = 'ADMIN', 'ROL_ADMIN', 'ROL_CAPITALHUMANO');
    SET @s2 = CONCAT('GRANT ', @rol_target, ' TO ''', u_name, '''@''%''');
    PREPARE stmt2 FROM @s2; EXECUTE stmt2; DEALLOCATE PREPARE stmt2;
END //

DELIMITER ;