-- ============================================================
--  INDECSA · Base de Datos v3.0 — alineada con Spring Boot
-- ============================================================

DROP DATABASE IF EXISTS indecsa;
CREATE DATABASE IF NOT EXISTS indecsa
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
USE indecsa;

-- ─────────────────────────────────────────────────────────────
-- 1. CATÁLOGOS BASE
-- ─────────────────────────────────────────────────────────────
CREATE TABLE Rol (
    id_rol          INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol      ENUM('ADMIN','CAPITAL_HUMANO') NOT NULL UNIQUE,
    descripcion_rol VARCHAR(255) DEFAULT 'Sin descripcion'
);

CREATE TABLE Empleado (
    id_empleado     INT AUTO_INCREMENT PRIMARY KEY,
    nombre_empleado VARCHAR(100) NOT NULL,
    curp            VARCHAR(18)  NOT NULL,
    correo_empleado VARCHAR(100) NOT NULL UNIQUE,
    contrasena      VARCHAR(255) NOT NULL,
    id_rol          INT NOT NULL,
    INDEX (correo_empleado),
    FOREIGN KEY (id_rol) REFERENCES Rol(id_rol) ON UPDATE CASCADE ON DELETE RESTRICT
);

-- ─────────────────────────────────────────────────────────────
-- 2. INFRAESTRUCTURA Y PROYECTOS
-- ─────────────────────────────────────────────────────────────
CREATE TABLE Ubicacion_Proyecto (
    id_ubicacion INT AUTO_INCREMENT PRIMARY KEY,
    calle        VARCHAR(100) NOT NULL,
    num_ext      VARCHAR(10)  NOT NULL,
    num_int      VARCHAR(10)  NULL,
    colonia      VARCHAR(100) NOT NULL,
    cod_post     INT          NOT NULL,
    mun_alc      VARCHAR(100) NOT NULL,
    estado       ENUM('CDMX','Hidalgo','Puebla') NOT NULL
);

CREATE TABLE Proyecto (
    id_proyecto           INT AUTO_INCREMENT PRIMARY KEY,
    nombre_proyecto       VARCHAR(150) NOT NULL,
    -- FIX: enum values usan guion bajo para coincidir con el nombre del enum Java (EnumType.STRING)
    tipo_proyecto         ENUM('Construccion','Remodelacion','Venta_mobiliaria','Instalacion_de_mobiliario'),
    oferta_trabajo        VARCHAR(200) NULL,
    cliente               VARCHAR(200) NULL,
    id_ubicacion          INT NOT NULL,
    municipio_proyecto    VARCHAR(100) NULL,
    estado_proyecto_geo   ENUM('CDMX','Hidalgo','Puebla') NULL,
    fecha_estimada_inicio DATE,
    fecha_estimada_fin    DATE,
    calificacion_proyecto TINYINT CHECK (calificacion_proyecto BETWEEN 1 AND 5),
    -- FIX: PAUSADO en lugar de PENDIENTE (alineado con EstatusProyecto del Spring Boot)
    estatus_proyecto      ENUM('PLANEACION','EN_CURSO','PAUSADO','FINALIZADO','CANCELADO') NOT NULL DEFAULT 'PLANEACION',
    descripcion_proyecto  VARCHAR(500) DEFAULT 'Sin descripcion',
    CONSTRAINT chk_fechas_proyecto CHECK (
        fecha_estimada_fin IS NULL OR
        fecha_estimada_inicio IS NULL OR
        fecha_estimada_fin >= fecha_estimada_inicio
    ),
    FOREIGN KEY (id_ubicacion) REFERENCES Ubicacion_Proyecto(id_ubicacion) ON UPDATE CASCADE ON DELETE RESTRICT
);

-- ─────────────────────────────────────────────────────────────
-- 3. GESTIÓN DE PERSONAL Y CONTRATISTAS
-- ─────────────────────────────────────────────────────────────
CREATE TABLE Contratista (
    id_contratista           INT AUTO_INCREMENT PRIMARY KEY,
    nombre_contratista       VARCHAR(100) NOT NULL,
    curp                     VARCHAR(18)  NOT NULL,
    rfc_contratista          VARCHAR(13)  NOT NULL UNIQUE,
    telefono_contratista     VARCHAR(15)  NOT NULL,
    correo_contratista       VARCHAR(100) NOT NULL UNIQUE,
    descripcion_contratista  VARCHAR(255) NOT NULL DEFAULT 'Sin descripcion',
    experiencia              VARCHAR(200) NULL,
    calificacion_contratista TINYINT      NULL CHECK (calificacion_contratista BETWEEN 1 AND 5),
    estado_contratista       ENUM('ACTIVO','INACTIVO','SUSPENDIDO') NOT NULL DEFAULT 'ACTIVO',
    -- FIX: ubicacion_contratista (nombre que usa la entidad JPA)
    ubicacion_contratista    ENUM('CDMX','Hidalgo','Puebla') NOT NULL
);

CREATE TABLE registros_migratorios (
    id_migratorio     INT AUTO_INCREMENT PRIMARY KEY,
    folio_documento   VARCHAR(50)  NOT NULL UNIQUE,
    categoria         ENUM('turismo','negocios','razones_humanitarias','transito','actividades_remuneradas') NOT NULL,
    fecha_emision     DATE NOT NULL,
    dias_vigencia     INT  CHECK (dias_vigencia > 0 AND dias_vigencia <= 1460),
    fecha_vencimiento DATE NOT NULL,
    permiso_trabajo   TINYINT(1) NOT NULL DEFAULT 0,
    -- FIX: columna requerida por RegistroMigratorio.java
    activo            TINYINT(1) NOT NULL DEFAULT 1
);

CREATE TABLE Trabajador (
    id_trabajador           INT AUTO_INCREMENT PRIMARY KEY,
    -- Datos personales
    nombre_trabajador       VARCHAR(100) NOT NULL,
    curp                    VARCHAR(18)  NOT NULL UNIQUE,
    rfc                     VARCHAR(13)  NOT NULL UNIQUE,
    nss_trabajador          VARCHAR(11)  NULL,
    nacionalidad            VARCHAR(100) NOT NULL DEFAULT 'Mexicana',
    id_migratorio           INT NULL,
    -- Domicilio (campos separados, mapeados individualmente en la entidad JPA)
    calle                   VARCHAR(100) NOT NULL,
    num_ext                 VARCHAR(10)  NOT NULL,
    num_int                 VARCHAR(10)  NULL,
    colonia                 VARCHAR(100) NOT NULL,
    cod_post                INT          NOT NULL,
    mun_alc                 VARCHAR(100) NOT NULL,
    estado                  VARCHAR(100) NOT NULL,
    -- Información laboral
    puesto                  VARCHAR(100) NOT NULL,
    desc_puesto             VARCHAR(500) NOT NULL,
    especialidad_trabajador VARCHAR(100) NOT NULL,
    escolaridad             VARCHAR(100) NOT NULL,
    experiencia             VARCHAR(200) NULL,
    telefono_trabajador     VARCHAR(15)  NOT NULL,
    correo_trabajador       VARCHAR(100) NOT NULL UNIQUE,
    contratacion            VARCHAR(200) NOT NULL,
    jornada                 VARCHAR(200) NOT NULL,
    estado_trabajador       ENUM('DESCANSO','VACACIONES','INCAPACIDAD','ACTIVO','INACTIVO','BAJA','BOLETINADO') NOT NULL DEFAULT 'ACTIVO',
    -- Datos complementarios
    descripcion_trabajador  TEXT         NULL,
    evaluacion_trabajador   TINYINT      NULL CHECK (evaluacion_trabajador BETWEEN 1 AND 5),
    fecha_ingreso           DATE         NOT NULL,
    calidad_vida            ENUM('CDMX','Hidalgo','Puebla') NOT NULL,
    ant_penal               VARCHAR(500) NULL,
    deudor_alim             VARCHAR(500) NULL,
    folio_lic_cond          VARCHAR(20)  NULL,
    estado_civil            VARCHAR(50)  NULL,
    idiomas                 VARCHAR(200) NULL,
    lengua_indigena         VARCHAR(100) NULL,
    sexo                    VARCHAR(50)  NULL,
    FOREIGN KEY (id_migratorio) REFERENCES registros_migratorios(id_migratorio) ON UPDATE CASCADE ON DELETE SET NULL
);

-- ─────────────────────────────────────────────────────────────
-- 4. CUADRILLAS
-- ─────────────────────────────────────────────────────────────
CREATE TABLE Cuadrilla (
    id_cuadrilla      INT AUTO_INCREMENT PRIMARY KEY,
    id_proyecto       INT NOT NULL,
    nombre_cuadrilla  VARCHAR(100) NOT NULL,
    frente_trabajo    VARCHAR(200) NULL,
    estatus_cuadrilla ENUM('ACTIVO','INACTIVO') NOT NULL DEFAULT 'ACTIVO',
    observaciones     VARCHAR(500) NULL,
    UNIQUE KEY uq_cuadrilla_proyecto (nombre_cuadrilla, id_proyecto),
    FOREIGN KEY (id_proyecto) REFERENCES Proyecto(id_proyecto) ON UPDATE CASCADE ON DELETE CASCADE
);

-- ─────────────────────────────────────────────────────────────
-- 5. ASIGNACIONES
-- ─────────────────────────────────────────────────────────────
CREATE TABLE Asignacion_Proyecto_Contratista (
    id_asignacion_pc   INT AUTO_INCREMENT PRIMARY KEY,
    id_proyecto        INT NOT NULL,
    id_contratista     INT NOT NULL,
    numero_contrato    VARCHAR(50)  NULL,
    fecha_inicio       DATE         NULL,
    fecha_fin_estimada DATE         NULL,
    personal_asignado  INT          NOT NULL DEFAULT 1,
    puestos_requeridos VARCHAR(500) NULL,
    -- FIX: restaurado ACTIVO al ENUM (Spring Boot lo usa en EstatusContrato)
    estatus_contrato   ENUM('ACTIVO','VIGENTE','SUSPENDIDO','FINALIZADO','CANCELADO') NOT NULL DEFAULT 'VIGENTE',
    observaciones      VARCHAR(500) NULL,
    UNIQUE KEY uq_proyecto_contratista (id_proyecto, id_contratista),
    FOREIGN KEY (id_proyecto)    REFERENCES Proyecto(id_proyecto)       ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (id_contratista) REFERENCES Contratista(id_contratista) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Asignacion_Trabajador_Proyecto (
    id_asignacion_tp   INT AUTO_INCREMENT PRIMARY KEY,
    id_trabajador      INT NOT NULL,
    id_proyecto        INT NOT NULL,
    id_asignacion_pc   INT NOT NULL,
    puesto_en_proyecto VARCHAR(100) NULL,
    fecha_inicio       DATE         NULL,
    fecha_fin_estimada DATE         NULL,
    estatus_asignacion ENUM('ACTIVO','SUSPENDIDO','INCAPACIDAD','CANCELADO','VACACIONES','FINALIZADO') NOT NULL DEFAULT 'ACTIVO',
    observaciones      VARCHAR(500) NULL,
    UNIQUE KEY uq_trabajador_proyecto (id_trabajador, id_proyecto),
    FOREIGN KEY (id_trabajador)    REFERENCES Trabajador(id_trabajador)                          ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (id_proyecto)      REFERENCES Proyecto(id_proyecto)                              ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (id_asignacion_pc) REFERENCES Asignacion_Proyecto_Contratista(id_asignacion_pc) ON UPDATE CASCADE ON DELETE CASCADE
);

-- ─────────────────────────────────────────────────────────────
-- 6. MÓDULO DE RENDIMIENTO
-- ─────────────────────────────────────────────────────────────
CREATE TABLE Estandar_Rendimiento (
    id_estandar          INT AUTO_INCREMENT PRIMARY KEY,
    nombre_actividad     VARCHAR(100) NOT NULL,
    unidad_medida        ENUM('m2','m3','ml','piezas','porcentaje') NOT NULL,
    rendimiento_esperado DECIMAL(10,4) NOT NULL CHECK (rendimiento_esperado > 0),
    descripcion          VARCHAR(255) NULL,
    -- FIX: jornada_base_horas requerido por el trigger de eficiencia y por la entidad Java
    jornada_base_horas   DECIMAL(5,2) NOT NULL DEFAULT 8.00 CHECK (jornada_base_horas > 0)
);

CREATE TABLE Registro_Horas (
    id_registro          INT AUTO_INCREMENT PRIMARY KEY,
    id_asignacion_tp     INT NOT NULL,
    id_cuadrilla         INT NULL,
    fecha_registro       DATE NOT NULL,
    horas_trabajadas     DECIMAL(5,2) NOT NULL CHECK (horas_trabajadas > 0 AND horas_trabajadas <= 24),
    tipo_periodo         ENUM('DIARIO','SEMANAL') NOT NULL DEFAULT 'DIARIO',
    observaciones        VARCHAR(500) NULL,
    id_empleado_registro INT NOT NULL,
    UNIQUE KEY uq_registro_fecha (id_asignacion_tp, fecha_registro),
    FOREIGN KEY (id_asignacion_tp)     REFERENCES Asignacion_Trabajador_Proyecto(id_asignacion_tp) ON DELETE CASCADE,
    FOREIGN KEY (id_cuadrilla)         REFERENCES Cuadrilla(id_cuadrilla)                          ON DELETE SET NULL,
    FOREIGN KEY (id_empleado_registro) REFERENCES Empleado(id_empleado)
);

CREATE TABLE Avance_Partida (
    id_avance            INT AUTO_INCREMENT PRIMARY KEY,
    id_proyecto          INT NOT NULL,
    id_cuadrilla         INT NULL,
    id_estandar          INT NULL,
    nombre_partida       VARCHAR(200) NOT NULL,
    fecha_registro       DATE         NOT NULL,
    cantidad_ejecutada   DECIMAL(12,4) NOT NULL,
    unidad_medida        ENUM('m2','m3','ml','piezas','porcentaje') NOT NULL,
    cantidad_programada  DECIMAL(12,4) NULL,
    observaciones        VARCHAR(500) NULL,
    id_empleado_registro INT NOT NULL,
    FOREIGN KEY (id_proyecto)          REFERENCES Proyecto(id_proyecto)              ON DELETE CASCADE,
    FOREIGN KEY (id_cuadrilla)         REFERENCES Cuadrilla(id_cuadrilla)             ON DELETE SET NULL,
    FOREIGN KEY (id_estandar)          REFERENCES Estandar_Rendimiento(id_estandar)   ON DELETE SET NULL,
    FOREIGN KEY (id_empleado_registro) REFERENCES Empleado(id_empleado)
);

-- ─────────────────────────────────────────────────────────────
-- 7. TRIGGERS
-- ─────────────────────────────────────────────────────────────
DELIMITER //

-- Calcular fecha_vencimiento automáticamente si no se proporciona
CREATE TRIGGER trg_fecha_migratoria_ai BEFORE INSERT ON registros_migratorios
FOR EACH ROW BEGIN
    IF NEW.fecha_vencimiento IS NULL OR NEW.fecha_vencimiento = '0000-00-00' THEN
        SET NEW.fecha_vencimiento = DATE_ADD(NEW.fecha_emision, INTERVAL NEW.dias_vigencia DAY);
    END IF;
END //

-- Validar que id_asignacion_pc corresponda al id_proyecto indicado
CREATE TRIGGER trg_validar_coherencia_asignacion BEFORE INSERT ON Asignacion_Trabajador_Proyecto
FOR EACH ROW BEGIN
    DECLARE v_id_proy_contrato INT;
    SELECT id_proyecto INTO v_id_proy_contrato
    FROM Asignacion_Proyecto_Contratista
    WHERE id_asignacion_pc = NEW.id_asignacion_pc;
    IF v_id_proy_contrato <> NEW.id_proyecto THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: El contrato seleccionado no pertenece a este proyecto.';
    END IF;
END //

-- Normalización de correo de Empleado (INSERT y UPDATE)
CREATE TRIGGER trg_lower_email_emp BEFORE INSERT ON Empleado
FOR EACH ROW SET NEW.correo_empleado = LOWER(TRIM(NEW.correo_empleado)); //

CREATE TRIGGER trg_lower_email_emp_upd BEFORE UPDATE ON Empleado
FOR EACH ROW SET NEW.correo_empleado = LOWER(TRIM(NEW.correo_empleado)); //

-- Normalización de correo de Trabajador (INSERT y UPDATE)
CREATE TRIGGER trg_lower_email_tra BEFORE INSERT ON Trabajador
FOR EACH ROW SET NEW.correo_trabajador = LOWER(TRIM(NEW.correo_trabajador)); //

CREATE TRIGGER trg_lower_email_tra_upd BEFORE UPDATE ON Trabajador
FOR EACH ROW SET NEW.correo_trabajador = LOWER(TRIM(NEW.correo_trabajador)); //

DELIMITER ;
