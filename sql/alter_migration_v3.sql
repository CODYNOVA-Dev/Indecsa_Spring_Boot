-- ============================================================
--  INDECSA · Migración incremental v3.0 → Railway Ready
--  Idempotente: seguro de ejecutar más de una vez.
--  NO borra datos existentes.
-- ============================================================
USE indecsa;
SET SQL_SAFE_UPDATES = 0;

-- ─────────────────────────────────────────────────────────────
-- 1. LIMPIEZA PREVIA DE ENUMs INCOMPATIBLES
--    Hibernate usa el nombre exacto del enum Java (con guiones bajos).
--    Si hay filas con los valores viejos, el MODIFY falla.
-- ─────────────────────────────────────────────────────────────
UPDATE Proyecto SET tipo_proyecto = 'Venta_mobiliaria'        WHERE tipo_proyecto = 'Venta mobiliaria';
UPDATE Proyecto SET tipo_proyecto = 'Instalacion_de_mobiliario' WHERE tipo_proyecto = 'Instalacion de mobiliario';
UPDATE Proyecto SET estatus_proyecto = 'PAUSADO'              WHERE estatus_proyecto = 'PENDIENTE';

-- ─────────────────────────────────────────────────────────────
-- 2. PROYECTO
-- ─────────────────────────────────────────────────────────────
-- 2a. MODIFY sólo (MySQL no permite mezclar MODIFY + ADD IF NOT EXISTS)
ALTER TABLE Proyecto
    MODIFY COLUMN tipo_proyecto
        ENUM('Construccion','Remodelacion','Venta_mobiliaria','Instalacion_de_mobiliario'),
    MODIFY COLUMN estatus_proyecto
        ENUM('PLANEACION','EN_CURSO','PAUSADO','FINALIZADO','CANCELADO') NOT NULL DEFAULT 'PLANEACION';

-- 2b. ADD columnas nuevas
ALTER TABLE Proyecto
    ADD COLUMN IF NOT EXISTS oferta_trabajo      VARCHAR(200) NULL,
    ADD COLUMN IF NOT EXISTS cliente             VARCHAR(200) NULL,
    ADD COLUMN IF NOT EXISTS municipio_proyecto  VARCHAR(100) NULL,
    ADD COLUMN IF NOT EXISTS estado_proyecto_geo ENUM('CDMX','Hidalgo','Puebla') NULL;

-- ─────────────────────────────────────────────────────────────
-- 3. CONTRATISTA
-- ─────────────────────────────────────────────────────────────
SET @col_old = (SELECT COUNT(*) FROM information_schema.COLUMNS
                WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'Contratista' AND COLUMN_NAME = 'ubicacion_base');
SET @sql = IF(@col_old > 0,
    'ALTER TABLE Contratista RENAME COLUMN ubicacion_base TO ubicacion_contratista',
    'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 3b. MODIFY sólo
ALTER TABLE Contratista
    MODIFY COLUMN rfc_contratista VARCHAR(13) NOT NULL;

-- 3c. ADD columnas nuevas
ALTER TABLE Contratista
    ADD COLUMN IF NOT EXISTS curp                    VARCHAR(18)  NOT NULL DEFAULT 'SINREGISTRO000000A',
    ADD COLUMN IF NOT EXISTS descripcion_contratista VARCHAR(255) NOT NULL DEFAULT 'Sin descripcion',
    ADD COLUMN IF NOT EXISTS experiencia             VARCHAR(200) NULL,
    ADD COLUMN IF NOT EXISTS calificacion_contratista TINYINT     NULL;

-- ─────────────────────────────────────────────────────────────
-- 4. REGISTROS MIGRATORIOS
-- ─────────────────────────────────────────────────────────────
ALTER TABLE registros_migratorios
    ADD COLUMN IF NOT EXISTS activo TINYINT(1) NOT NULL DEFAULT 1;

-- ─────────────────────────────────────────────────────────────
-- 5. TRABAJADOR
-- ─────────────────────────────────────────────────────────────

-- 5a. Correo (primero nullable para no romper filas existentes)
ALTER TABLE Trabajador
    ADD COLUMN IF NOT EXISTS correo_trabajador VARCHAR(100) NULL;

UPDATE Trabajador
SET correo_trabajador = CONCAT('trabajador.', id_trabajador, '@indecsa.mx')
WHERE correo_trabajador IS NULL;

-- 5b. Domicilio separado (migra desde direccion_completa si existe)
ALTER TABLE Trabajador
    ADD COLUMN IF NOT EXISTS calle    VARCHAR(100) NULL,
    ADD COLUMN IF NOT EXISTS num_ext  VARCHAR(10)  NULL,
    ADD COLUMN IF NOT EXISTS num_int  VARCHAR(10)  NULL,
    ADD COLUMN IF NOT EXISTS colonia  VARCHAR(100) NULL,
    ADD COLUMN IF NOT EXISTS cod_post INT          NULL,
    ADD COLUMN IF NOT EXISTS mun_alc  VARCHAR(100) NULL,
    ADD COLUMN IF NOT EXISTS estado   VARCHAR(100) NULL;

SET @has_dir = (SELECT COUNT(*) FROM information_schema.COLUMNS
                WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'Trabajador' AND COLUMN_NAME = 'direccion_completa');
SET @sql = IF(@has_dir > 0,
    'UPDATE Trabajador SET calle = COALESCE(calle, direccion_completa) WHERE calle IS NULL',
    'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

UPDATE Trabajador SET calle    = 'Sin calle'       WHERE calle    IS NULL;
UPDATE Trabajador SET num_ext  = 'S/N'             WHERE num_ext  IS NULL;
UPDATE Trabajador SET colonia  = 'Sin colonia'     WHERE colonia  IS NULL;
UPDATE Trabajador SET cod_post = 0                 WHERE cod_post IS NULL;
UPDATE Trabajador SET mun_alc  = 'Sin municipio'   WHERE mun_alc  IS NULL;
UPDATE Trabajador SET estado   = 'Sin estado'      WHERE estado   IS NULL;

ALTER TABLE Trabajador
    MODIFY COLUMN calle    VARCHAR(100) NOT NULL,
    MODIFY COLUMN num_ext  VARCHAR(10)  NOT NULL,
    MODIFY COLUMN colonia  VARCHAR(100) NOT NULL,
    MODIFY COLUMN cod_post INT          NOT NULL,
    MODIFY COLUMN mun_alc  VARCHAR(100) NOT NULL,
    MODIFY COLUMN estado   VARCHAR(100) NOT NULL;

-- 5c. Campos laborales requeridos por la entidad JPA
ALTER TABLE Trabajador
    ADD COLUMN IF NOT EXISTS desc_puesto              VARCHAR(500) NOT NULL DEFAULT 'Sin descripcion',
    ADD COLUMN IF NOT EXISTS especialidad_trabajador  VARCHAR(100) NOT NULL DEFAULT 'General',
    ADD COLUMN IF NOT EXISTS escolaridad              VARCHAR(100) NOT NULL DEFAULT 'No especificada',
    ADD COLUMN IF NOT EXISTS experiencia              VARCHAR(200) NULL,
    ADD COLUMN IF NOT EXISTS telefono_trabajador      VARCHAR(15)  NOT NULL DEFAULT '0000000000',
    ADD COLUMN IF NOT EXISTS contratacion             VARCHAR(200) NOT NULL DEFAULT 'Planta',
    ADD COLUMN IF NOT EXISTS jornada                  VARCHAR(200) NOT NULL DEFAULT 'Completa',
    ADD COLUMN IF NOT EXISTS calidad_vida             ENUM('CDMX','Hidalgo','Puebla') NOT NULL DEFAULT 'CDMX',
    ADD COLUMN IF NOT EXISTS descripcion_trabajador   TEXT         NULL,
    ADD COLUMN IF NOT EXISTS evaluacion_trabajador    TINYINT      NULL,
    ADD COLUMN IF NOT EXISTS ant_penal                VARCHAR(500) NULL,
    ADD COLUMN IF NOT EXISTS deudor_alim              VARCHAR(500) NULL,
    ADD COLUMN IF NOT EXISTS folio_lic_cond           VARCHAR(20)  NULL,
    ADD COLUMN IF NOT EXISTS estado_civil             VARCHAR(50)  NULL,
    ADD COLUMN IF NOT EXISTS idiomas                  VARCHAR(200) NULL,
    ADD COLUMN IF NOT EXISTS lengua_indigena          VARCHAR(100) NULL,
    ADD COLUMN IF NOT EXISTS sexo                     VARCHAR(50)  NULL;

-- ─────────────────────────────────────────────────────────────
-- 6. ASIGNACION_PROYECTO_CONTRATISTA  ← CAUSA RAÍZ DEL CRASH
--    Hibernate falla al arrancar porque busca estas columnas
--    y no las encuentra en la BD de Railway.
-- ─────────────────────────────────────────────────────────────
-- 6a. MODIFY sólo
ALTER TABLE Asignacion_Proyecto_Contratista
    MODIFY COLUMN estatus_contrato
        ENUM('ACTIVO','VIGENTE','SUSPENDIDO','FINALIZADO','CANCELADO') NOT NULL DEFAULT 'VIGENTE';

-- 6b. ADD columnas nuevas
ALTER TABLE Asignacion_Proyecto_Contratista
    ADD COLUMN IF NOT EXISTS numero_contrato    VARCHAR(50)  NULL,
    ADD COLUMN IF NOT EXISTS fecha_fin_estimada DATE         NULL,
    ADD COLUMN IF NOT EXISTS personal_asignado  INT          NOT NULL DEFAULT 1,
    ADD COLUMN IF NOT EXISTS puestos_requeridos VARCHAR(500) NULL,
    ADD COLUMN IF NOT EXISTS observaciones      VARCHAR(500) NULL;

-- ─────────────────────────────────────────────────────────────
-- 7. ASIGNACION_TRABAJADOR_PROYECTO
-- ─────────────────────────────────────────────────────────────
ALTER TABLE Asignacion_Trabajador_Proyecto
    ADD COLUMN IF NOT EXISTS puesto_en_proyecto VARCHAR(100) NULL,
    ADD COLUMN IF NOT EXISTS fecha_inicio       DATE         NULL,
    ADD COLUMN IF NOT EXISTS fecha_fin_estimada DATE         NULL,
    ADD COLUMN IF NOT EXISTS observaciones      VARCHAR(500) NULL;

-- ─────────────────────────────────────────────────────────────
-- 8. ESTANDAR_RENDIMIENTO
-- ─────────────────────────────────────────────────────────────
ALTER TABLE Estandar_Rendimiento
    ADD COLUMN IF NOT EXISTS descripcion        VARCHAR(255) NULL,
    ADD COLUMN IF NOT EXISTS jornada_base_horas DECIMAL(5,2) NOT NULL DEFAULT 8.00;

-- ─────────────────────────────────────────────────────────────
-- 9. TABLAS NUEVAS
-- ─────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS Cuadrilla (
    id_cuadrilla      INT AUTO_INCREMENT PRIMARY KEY,
    id_proyecto       INT NOT NULL,
    nombre_cuadrilla  VARCHAR(100) NOT NULL,
    frente_trabajo    VARCHAR(200) NULL,
    estatus_cuadrilla ENUM('ACTIVO','INACTIVO') NOT NULL DEFAULT 'ACTIVO',
    observaciones     VARCHAR(500) NULL,
    UNIQUE KEY uq_cuadrilla_proyecto (nombre_cuadrilla, id_proyecto),
    FOREIGN KEY (id_proyecto) REFERENCES Proyecto(id_proyecto) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Registro_Horas (
    id_registro          INT AUTO_INCREMENT PRIMARY KEY,
    id_asignacion_tp     INT NOT NULL,
    id_cuadrilla         INT NULL,
    fecha_registro       DATE NOT NULL,
    horas_trabajadas     DECIMAL(5,2) NOT NULL,
    tipo_periodo         ENUM('DIARIO','SEMANAL') NOT NULL DEFAULT 'DIARIO',
    observaciones        VARCHAR(500) NULL,
    id_empleado_registro INT NOT NULL,
    UNIQUE KEY uq_registro_fecha (id_asignacion_tp, fecha_registro),
    FOREIGN KEY (id_asignacion_tp)     REFERENCES Asignacion_Trabajador_Proyecto(id_asignacion_tp) ON DELETE CASCADE,
    FOREIGN KEY (id_cuadrilla)         REFERENCES Cuadrilla(id_cuadrilla)                          ON DELETE SET NULL,
    FOREIGN KEY (id_empleado_registro) REFERENCES Empleado(id_empleado)
);

CREATE TABLE IF NOT EXISTS Avance_Partida (
    id_avance            INT AUTO_INCREMENT PRIMARY KEY,
    id_proyecto          INT NOT NULL,
    id_cuadrilla         INT NULL,
    id_estandar          INT NULL,
    nombre_partida       VARCHAR(200)  NOT NULL,
    fecha_registro       DATE          NOT NULL,
    cantidad_ejecutada   DECIMAL(12,4) NOT NULL,
    unidad_medida        ENUM('m2','m3','ml','piezas','porcentaje') NOT NULL,
    cantidad_programada  DECIMAL(12,4) NULL,
    observaciones        VARCHAR(500)  NULL,
    id_empleado_registro INT NOT NULL,
    FOREIGN KEY (id_proyecto)          REFERENCES Proyecto(id_proyecto)             ON DELETE CASCADE,
    FOREIGN KEY (id_cuadrilla)         REFERENCES Cuadrilla(id_cuadrilla)            ON DELETE SET NULL,
    FOREIGN KEY (id_estandar)          REFERENCES Estandar_Rendimiento(id_estandar)  ON DELETE SET NULL,
    FOREIGN KEY (id_empleado_registro) REFERENCES Empleado(id_empleado)
);

-- ─────────────────────────────────────────────────────────────
-- 10. TRIGGERS DE NORMALIZACIÓN DE CORREO
-- ─────────────────────────────────────────────────────────────
DELIMITER //

DROP TRIGGER IF EXISTS trg_lower_email_emp //
CREATE TRIGGER trg_lower_email_emp BEFORE INSERT ON Empleado
FOR EACH ROW BEGIN
    SET NEW.correo_empleado = LOWER(TRIM(NEW.correo_empleado));
END //

DROP TRIGGER IF EXISTS trg_lower_email_emp_upd //
CREATE TRIGGER trg_lower_email_emp_upd BEFORE UPDATE ON Empleado
FOR EACH ROW BEGIN
    SET NEW.correo_empleado = LOWER(TRIM(NEW.correo_empleado));
END //

DROP TRIGGER IF EXISTS trg_lower_email_tra //
CREATE TRIGGER trg_lower_email_tra BEFORE INSERT ON Trabajador
FOR EACH ROW BEGIN
    IF NEW.correo_trabajador IS NOT NULL THEN
        SET NEW.correo_trabajador = LOWER(TRIM(NEW.correo_trabajador));
    END IF;
END //

DROP TRIGGER IF EXISTS trg_lower_email_tra_upd //
CREATE TRIGGER trg_lower_email_tra_upd BEFORE UPDATE ON Trabajador
FOR EACH ROW BEGIN
    IF NEW.correo_trabajador IS NOT NULL THEN
        SET NEW.correo_trabajador = LOWER(TRIM(NEW.correo_trabajador));
    END IF;
END //

DROP TRIGGER IF EXISTS trg_lower_email_con //
CREATE TRIGGER trg_lower_email_con BEFORE INSERT ON Contratista
FOR EACH ROW BEGIN
    SET NEW.correo_contratista = LOWER(TRIM(NEW.correo_contratista));
END //

DELIMITER ;

-- ─────────────────────────────────────────────────────────────
-- 11. VERIFICACIÓN FINAL
-- ─────────────────────────────────────────────────────────────
SET SQL_SAFE_UPDATES = 1;

SELECT 'Migracion v3.0 completada' AS status;

SELECT
    t.TABLE_NAME      AS tabla,
    COUNT(c.COLUMN_NAME) AS columnas
FROM information_schema.TABLES  t
JOIN information_schema.COLUMNS c
    ON t.TABLE_SCHEMA = c.TABLE_SCHEMA AND t.TABLE_NAME = c.TABLE_NAME
WHERE t.TABLE_SCHEMA = DATABASE()
  AND t.TABLE_TYPE   = 'BASE TABLE'
GROUP BY t.TABLE_NAME
ORDER BY t.TABLE_NAME;
