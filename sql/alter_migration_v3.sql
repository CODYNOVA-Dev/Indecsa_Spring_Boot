-- ============================================================
--  INDECSA · Migración incremental v3.0 → Railway Ready
--  Idempotente: seguro de ejecutar más de una vez.
--  NO borra datos existentes.
--  Compatible con MySQL 8.0 estándar (sin extensiones MariaDB).
-- ============================================================
USE indecsa;
SET SQL_SAFE_UPDATES = 0;

-- ─────────────────────────────────────────────────────────────
-- HELPER: procedimiento para ADD COLUMN idempotente en MySQL.
-- ADD COLUMN IF NOT EXISTS no existe en MySQL estándar (es MariaDB).
-- ─────────────────────────────────────────────────────────────
DROP PROCEDURE IF EXISTS _add_col;
DELIMITER //
CREATE PROCEDURE _add_col(IN tbl VARCHAR(64), IN col VARCHAR(64), IN def TEXT)
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.COLUMNS
        WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = tbl AND COLUMN_NAME = col
    ) THEN
        SET @_ddl = CONCAT('ALTER TABLE `', tbl, '` ADD COLUMN `', col, '` ', def);
        PREPARE _s FROM @_ddl; EXECUTE _s; DEALLOCATE PREPARE _s;
    END IF;
END //
DELIMITER ;

-- ─────────────────────────────────────────────────────────────
-- 1. PROYECTO — ENUM MODIFY primero, UPDATE después
-- ─────────────────────────────────────────────────────────────
-- 1a. Ampliar ENUM para incluir valores viejos Y nuevos
ALTER TABLE Proyecto
    MODIFY COLUMN tipo_proyecto
        ENUM('Construccion','Remodelacion',
             'Venta mobiliaria','Venta_mobiliaria',
             'Instalacion de mobiliario','Instalacion_de_mobiliario'),
    MODIFY COLUMN estatus_proyecto
        ENUM('PLANEACION','EN_CURSO','PENDIENTE','PAUSADO','FINALIZADO','CANCELADO')
        NOT NULL DEFAULT 'PLANEACION';

-- 1b. Migrar valores viejos a nuevos
UPDATE Proyecto SET tipo_proyecto    = 'Venta_mobiliaria'         WHERE tipo_proyecto    = 'Venta mobiliaria';
UPDATE Proyecto SET tipo_proyecto    = 'Instalacion_de_mobiliario' WHERE tipo_proyecto    = 'Instalacion de mobiliario';
UPDATE Proyecto SET estatus_proyecto = 'PAUSADO'                   WHERE estatus_proyecto = 'PENDIENTE';

-- 1c. MODIFY final — solo valores alineados con el enum Java
ALTER TABLE Proyecto
    MODIFY COLUMN tipo_proyecto
        ENUM('Construccion','Remodelacion','Venta_mobiliaria','Instalacion_de_mobiliario'),
    MODIFY COLUMN estatus_proyecto
        ENUM('PLANEACION','EN_CURSO','PAUSADO','FINALIZADO','CANCELADO')
        NOT NULL DEFAULT 'PLANEACION';

-- 1d. ADD columnas nuevas (idempotente)
CALL _add_col('Proyecto', 'oferta_trabajo',      'VARCHAR(200) NULL');
CALL _add_col('Proyecto', 'cliente',             'VARCHAR(200) NULL');
CALL _add_col('Proyecto', 'municipio_proyecto',  'VARCHAR(100) NULL');
CALL _add_col('Proyecto', 'estado_proyecto_geo', "ENUM('CDMX','Hidalgo','Puebla') NULL");

-- ─────────────────────────────────────────────────────────────
-- 2. CONTRATISTA
-- ─────────────────────────────────────────────────────────────
-- 2a. Renombrar columna vieja si existe
SET @_c = (SELECT COUNT(*) FROM information_schema.COLUMNS
           WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'Contratista' AND COLUMN_NAME = 'ubicacion_base');
SET @_sql = IF(@_c > 0,
    'ALTER TABLE Contratista RENAME COLUMN ubicacion_base TO ubicacion_contratista',
    'SELECT 1');
PREPARE _s FROM @_sql; EXECUTE _s; DEALLOCATE PREPARE _s;

-- 2b. MODIFY
ALTER TABLE Contratista
    MODIFY COLUMN rfc_contratista VARCHAR(13) NOT NULL;

-- 2c. ADD columnas nuevas
CALL _add_col('Contratista', 'curp',                     "VARCHAR(18)  NOT NULL DEFAULT 'SINREGISTRO000000A'");
CALL _add_col('Contratista', 'descripcion_contratista',  "VARCHAR(255) NOT NULL DEFAULT 'Sin descripcion'");
CALL _add_col('Contratista', 'experiencia',              'VARCHAR(200) NULL');
CALL _add_col('Contratista', 'calificacion_contratista', 'TINYINT NULL');

-- ─────────────────────────────────────────────────────────────
-- 3. REGISTROS MIGRATORIOS
-- ─────────────────────────────────────────────────────────────
CALL _add_col('registros_migratorios', 'activo', 'TINYINT(1) NOT NULL DEFAULT 1');

-- ─────────────────────────────────────────────────────────────
-- 4. TRABAJADOR
-- ─────────────────────────────────────────────────────────────

-- 4a. Correo (nullable primero para no romper filas existentes)
CALL _add_col('Trabajador', 'correo_trabajador', 'VARCHAR(100) NULL');

UPDATE Trabajador
SET correo_trabajador = CONCAT('trabajador.', id_trabajador, '@indecsa.mx')
WHERE correo_trabajador IS NULL;

-- 4b. Domicilio separado
CALL _add_col('Trabajador', 'calle',    'VARCHAR(100) NULL');
CALL _add_col('Trabajador', 'num_ext',  'VARCHAR(10)  NULL');
CALL _add_col('Trabajador', 'num_int',  'VARCHAR(10)  NULL');
CALL _add_col('Trabajador', 'colonia',  'VARCHAR(100) NULL');
CALL _add_col('Trabajador', 'cod_post', 'INT NULL');
CALL _add_col('Trabajador', 'mun_alc',  'VARCHAR(100) NULL');
CALL _add_col('Trabajador', 'estado',   'VARCHAR(100) NULL');

-- Migrar desde direccion_completa si la columna vieja existe
SET @_c = (SELECT COUNT(*) FROM information_schema.COLUMNS
           WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'Trabajador' AND COLUMN_NAME = 'direccion_completa');
SET @_sql = IF(@_c > 0,
    'UPDATE Trabajador SET calle = COALESCE(calle, direccion_completa) WHERE calle IS NULL',
    'SELECT 1');
PREPARE _s FROM @_sql; EXECUTE _s; DEALLOCATE PREPARE _s;

UPDATE Trabajador SET calle    = 'Sin calle'     WHERE calle    IS NULL;
UPDATE Trabajador SET num_ext  = 'S/N'           WHERE num_ext  IS NULL;
UPDATE Trabajador SET colonia  = 'Sin colonia'   WHERE colonia  IS NULL;
UPDATE Trabajador SET cod_post = 0               WHERE cod_post IS NULL;
UPDATE Trabajador SET mun_alc  = 'Sin municipio' WHERE mun_alc  IS NULL;
UPDATE Trabajador SET estado   = 'Sin estado'    WHERE estado   IS NULL;

ALTER TABLE Trabajador
    MODIFY COLUMN calle    VARCHAR(100) NOT NULL,
    MODIFY COLUMN num_ext  VARCHAR(10)  NOT NULL,
    MODIFY COLUMN colonia  VARCHAR(100) NOT NULL,
    MODIFY COLUMN cod_post INT          NOT NULL,
    MODIFY COLUMN mun_alc  VARCHAR(100) NOT NULL,
    MODIFY COLUMN estado   VARCHAR(100) NOT NULL;

-- 4c. Campos laborales
CALL _add_col('Trabajador', 'desc_puesto',             "VARCHAR(500) NOT NULL DEFAULT 'Sin descripcion'");
CALL _add_col('Trabajador', 'especialidad_trabajador', "VARCHAR(100) NOT NULL DEFAULT 'General'");
CALL _add_col('Trabajador', 'escolaridad',             "VARCHAR(100) NOT NULL DEFAULT 'No especificada'");
CALL _add_col('Trabajador', 'experiencia',             'VARCHAR(200) NULL');
CALL _add_col('Trabajador', 'telefono_trabajador',     "VARCHAR(15)  NOT NULL DEFAULT '0000000000'");
CALL _add_col('Trabajador', 'contratacion',            "VARCHAR(200) NOT NULL DEFAULT 'Planta'");
CALL _add_col('Trabajador', 'jornada',                 "VARCHAR(200) NOT NULL DEFAULT 'Completa'");
CALL _add_col('Trabajador', 'calidad_vida',            "ENUM('CDMX','Hidalgo','Puebla') NOT NULL DEFAULT 'CDMX'");
CALL _add_col('Trabajador', 'descripcion_trabajador',  'TEXT NULL');
CALL _add_col('Trabajador', 'evaluacion_trabajador',   'TINYINT NULL');
CALL _add_col('Trabajador', 'ant_penal',               'VARCHAR(500) NULL');
CALL _add_col('Trabajador', 'deudor_alim',             'VARCHAR(500) NULL');
CALL _add_col('Trabajador', 'folio_lic_cond',          'VARCHAR(20)  NULL');
CALL _add_col('Trabajador', 'estado_civil',            'VARCHAR(50)  NULL');
CALL _add_col('Trabajador', 'idiomas',                 'VARCHAR(200) NULL');
CALL _add_col('Trabajador', 'lengua_indigena',         'VARCHAR(100) NULL');
CALL _add_col('Trabajador', 'sexo',                    'VARCHAR(50)  NULL');

-- ─────────────────────────────────────────────────────────────
-- 5. ASIGNACION_PROYECTO_CONTRATISTA
-- ─────────────────────────────────────────────────────────────
ALTER TABLE Asignacion_Proyecto_Contratista
    MODIFY COLUMN estatus_contrato
        ENUM('ACTIVO','VIGENTE','SUSPENDIDO','FINALIZADO','CANCELADO')
        NOT NULL DEFAULT 'VIGENTE';

CALL _add_col('Asignacion_Proyecto_Contratista', 'numero_contrato',    'VARCHAR(50)  NULL');
CALL _add_col('Asignacion_Proyecto_Contratista', 'fecha_fin_estimada', 'DATE NULL');
CALL _add_col('Asignacion_Proyecto_Contratista', 'personal_asignado',  'INT NOT NULL DEFAULT 1');
CALL _add_col('Asignacion_Proyecto_Contratista', 'puestos_requeridos', 'VARCHAR(500) NULL');
CALL _add_col('Asignacion_Proyecto_Contratista', 'observaciones',      'VARCHAR(500) NULL');

-- ─────────────────────────────────────────────────────────────
-- 6. ASIGNACION_TRABAJADOR_PROYECTO
-- ─────────────────────────────────────────────────────────────
CALL _add_col('Asignacion_Trabajador_Proyecto', 'puesto_en_proyecto', 'VARCHAR(100) NULL');
CALL _add_col('Asignacion_Trabajador_Proyecto', 'fecha_inicio',       'DATE NULL');
CALL _add_col('Asignacion_Trabajador_Proyecto', 'fecha_fin_estimada', 'DATE NULL');
CALL _add_col('Asignacion_Trabajador_Proyecto', 'observaciones',      'VARCHAR(500) NULL');

-- ─────────────────────────────────────────────────────────────
-- 7. ESTANDAR_RENDIMIENTO
-- ─────────────────────────────────────────────────────────────
CALL _add_col('Estandar_Rendimiento', 'descripcion',        'VARCHAR(255) NULL');
CALL _add_col('Estandar_Rendimiento', 'jornada_base_horas', 'DECIMAL(5,2) NOT NULL DEFAULT 8.00');

-- ─────────────────────────────────────────────────────────────
-- 8. TABLAS NUEVAS (CREATE TABLE IF NOT EXISTS sí es MySQL estándar)
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
-- 9. TRIGGERS DE NORMALIZACIÓN DE CORREO
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

DROP TRIGGER IF EXISTS trg_lower_email_con_upd //
CREATE TRIGGER trg_lower_email_con_upd BEFORE UPDATE ON Contratista
FOR EACH ROW BEGIN
    SET NEW.correo_contratista = LOWER(TRIM(NEW.correo_contratista));
END //

DELIMITER ;

-- ─────────────────────────────────────────────────────────────
-- 10. LIMPIEZA
-- ─────────────────────────────────────────────────────────────
DROP PROCEDURE IF EXISTS _add_col;

-- ─────────────────────────────────────────────────────────────
-- 11. VERIFICACIÓN FINAL
-- ─────────────────────────────────────────────────────────────
SET SQL_SAFE_UPDATES = 1;

SELECT 'Migracion v3.0 completada' AS status;

SELECT
    t.TABLE_NAME         AS tabla,
    COUNT(c.COLUMN_NAME) AS columnas
FROM information_schema.TABLES  t
JOIN information_schema.COLUMNS c
    ON t.TABLE_SCHEMA = c.TABLE_SCHEMA AND t.TABLE_NAME = c.TABLE_NAME
WHERE t.TABLE_SCHEMA = DATABASE()
  AND t.TABLE_TYPE   = 'BASE TABLE'
GROUP BY t.TABLE_NAME
ORDER BY t.TABLE_NAME;
