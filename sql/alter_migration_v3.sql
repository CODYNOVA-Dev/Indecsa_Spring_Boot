-- ============================================================
--  INDECSA · Migración incremental → v3.0
--  Aplica en la BD de Railway SIN borrar datos existentes.
--  Es idempotente: usa IF NOT EXISTS / IF EXISTS.
--  Ejecutar en orden, NO omitir secciones.
-- ============================================================
USE indecsa;

-- ─────────────────────────────────────────────────────────────
-- 1. Proyecto
--    · ENUM tipo_proyecto: espacios → guiones bajos (JPA usa el nombre exacto del enum Java)
--    · ENUM estatus_proyecto: PENDIENTE → PAUSADO
--    · Columnas nuevas: oferta_trabajo, cliente, municipio_proyecto, estado_proyecto_geo
-- ─────────────────────────────────────────────────────────────
ALTER TABLE Proyecto
    MODIFY COLUMN tipo_proyecto
        ENUM('Construccion','Remodelacion','Venta_mobiliaria','Instalacion_de_mobiliario'),
    MODIFY COLUMN estatus_proyecto
        ENUM('PLANEACION','EN_CURSO','PAUSADO','FINALIZADO','CANCELADO') NOT NULL DEFAULT 'PLANEACION',
    ADD COLUMN IF NOT EXISTS oferta_trabajo     VARCHAR(200) NULL,
    ADD COLUMN IF NOT EXISTS cliente            VARCHAR(200) NULL,
    ADD COLUMN IF NOT EXISTS municipio_proyecto VARCHAR(100) NULL,
    ADD COLUMN IF NOT EXISTS estado_proyecto_geo
        ENUM('CDMX','Hidalgo','Puebla') NULL;

-- ─────────────────────────────────────────────────────────────
-- 2. Contratista
--    · RENAME: ubicacion_base → ubicacion_contratista
--    · Columnas nuevas: curp, descripcion_contratista, experiencia, calificacion_contratista
-- ─────────────────────────────────────────────────────────────

-- Renombrar solo si la columna vieja existe y la nueva no
SET @col_old = (SELECT COUNT(*) FROM information_schema.COLUMNS
                WHERE TABLE_SCHEMA = DATABASE()
                  AND TABLE_NAME   = 'Contratista'
                  AND COLUMN_NAME  = 'ubicacion_base');
SET @col_new = (SELECT COUNT(*) FROM information_schema.COLUMNS
                WHERE TABLE_SCHEMA = DATABASE()
                  AND TABLE_NAME   = 'Contratista'
                  AND COLUMN_NAME  = 'ubicacion_contratista');
SET @sql = IF(@col_old > 0 AND @col_new = 0,
    'ALTER TABLE Contratista RENAME COLUMN ubicacion_base TO ubicacion_contratista',
    'SELECT 1 -- ubicacion_contratista ya existe o ubicacion_base no existe');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

ALTER TABLE Contratista
    ADD COLUMN IF NOT EXISTS curp                     VARCHAR(18)  NOT NULL DEFAULT 'SINREGISTRO000000A',
    ADD COLUMN IF NOT EXISTS descripcion_contratista  VARCHAR(255) NOT NULL DEFAULT 'Sin descripcion',
    ADD COLUMN IF NOT EXISTS experiencia              VARCHAR(200) NULL,
    ADD COLUMN IF NOT EXISTS calificacion_contratista TINYINT      NULL;

-- ─────────────────────────────────────────────────────────────
-- 3. registros_migratorios
--    · Columna nueva: activo
-- ─────────────────────────────────────────────────────────────
ALTER TABLE registros_migratorios
    ADD COLUMN IF NOT EXISTS activo TINYINT(1) NOT NULL DEFAULT 1;

-- ─────────────────────────────────────────────────────────────
-- 4. Trabajador
--    · Reemplaza direccion_completa por 7 columnas separadas
--    · Nuevos campos laborales y complementarios
-- ─────────────────────────────────────────────────────────────

-- 4a. Agregar columnas de domicilio (nullable para no romper filas existentes)
ALTER TABLE Trabajador
    ADD COLUMN IF NOT EXISTS calle    VARCHAR(100) NULL,
    ADD COLUMN IF NOT EXISTS num_ext  VARCHAR(10)  NULL,
    ADD COLUMN IF NOT EXISTS num_int  VARCHAR(10)  NULL,
    ADD COLUMN IF NOT EXISTS colonia  VARCHAR(100) NULL,
    ADD COLUMN IF NOT EXISTS cod_post INT          NULL,
    ADD COLUMN IF NOT EXISTS mun_alc  VARCHAR(100) NULL,
    ADD COLUMN IF NOT EXISTS estado   VARCHAR(100) NULL;

-- 4b. Migrar datos de direccion_completa a calle (si aún existe la columna vieja)
SET @has_dir = (SELECT COUNT(*) FROM information_schema.COLUMNS
                WHERE TABLE_SCHEMA = DATABASE()
                  AND TABLE_NAME   = 'Trabajador'
                  AND COLUMN_NAME  = 'direccion_completa');
SET @sql = IF(@has_dir > 0,
    'UPDATE Trabajador SET calle = COALESCE(calle, direccion_completa),
                           num_ext  = COALESCE(num_ext,  ''S/N''),
                           colonia  = COALESCE(colonia,  ''Sin colonia''),
                           cod_post = COALESCE(cod_post, 00000),
                           mun_alc  = COALESCE(mun_alc,  ''Sin municipio''),
                           estado   = COALESCE(estado,   ''Sin estado'')
     WHERE calle IS NULL',
    'SELECT 1 -- direccion_completa no existe, nada que migrar');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 4c. Ahora sí poner NOT NULL con DEFAULT seguro para filas que queden NULL
UPDATE Trabajador SET calle   = 'Sin calle'      WHERE calle   IS NULL;
UPDATE Trabajador SET num_ext = 'S/N'            WHERE num_ext  IS NULL;
UPDATE Trabajador SET colonia = 'Sin colonia'    WHERE colonia  IS NULL;
UPDATE Trabajador SET cod_post = 0               WHERE cod_post IS NULL;
UPDATE Trabajador SET mun_alc = 'Sin municipio'  WHERE mun_alc  IS NULL;
UPDATE Trabajador SET estado  = 'Sin estado'     WHERE estado   IS NULL;

ALTER TABLE Trabajador
    MODIFY COLUMN calle    VARCHAR(100) NOT NULL,
    MODIFY COLUMN num_ext  VARCHAR(10)  NOT NULL,
    MODIFY COLUMN colonia  VARCHAR(100) NOT NULL,
    MODIFY COLUMN cod_post INT          NOT NULL,
    MODIFY COLUMN mun_alc  VARCHAR(100) NOT NULL,
    MODIFY COLUMN estado   VARCHAR(100) NOT NULL;

-- 4d. Nuevas columnas laborales (nullable donde el tipo lo permite)
ALTER TABLE Trabajador
    ADD COLUMN IF NOT EXISTS desc_puesto             VARCHAR(500) NOT NULL DEFAULT 'Sin descripcion',
    ADD COLUMN IF NOT EXISTS especialidad_trabajador VARCHAR(100) NOT NULL DEFAULT 'General',
    ADD COLUMN IF NOT EXISTS escolaridad             VARCHAR(100) NOT NULL DEFAULT 'No especificada',
    ADD COLUMN IF NOT EXISTS experiencia             VARCHAR(200) NULL,
    ADD COLUMN IF NOT EXISTS telefono_trabajador     VARCHAR(15)  NOT NULL DEFAULT '0000000000',
    ADD COLUMN IF NOT EXISTS contratacion            VARCHAR(200) NOT NULL DEFAULT 'Planta',
    ADD COLUMN IF NOT EXISTS jornada                 VARCHAR(200) NOT NULL DEFAULT 'Completa',
    ADD COLUMN IF NOT EXISTS descripcion_trabajador  TEXT         NULL,
    ADD COLUMN IF NOT EXISTS evaluacion_trabajador   TINYINT      NULL,
    ADD COLUMN IF NOT EXISTS calidad_vida            ENUM('CDMX','Hidalgo','Puebla') NOT NULL DEFAULT 'CDMX',
    ADD COLUMN IF NOT EXISTS ant_penal               VARCHAR(500) NULL,
    ADD COLUMN IF NOT EXISTS deudor_alim             VARCHAR(500) NULL,
    ADD COLUMN IF NOT EXISTS folio_lic_cond          VARCHAR(20)  NULL,
    ADD COLUMN IF NOT EXISTS estado_civil            VARCHAR(50)  NULL,
    ADD COLUMN IF NOT EXISTS idiomas                 VARCHAR(200) NULL,
    ADD COLUMN IF NOT EXISTS lengua_indigena         VARCHAR(100) NULL,
    ADD COLUMN IF NOT EXISTS sexo                    VARCHAR(50)  NULL;

-- 4e. correo_trabajador: cambiar de NULL a NOT NULL (rellenar vacíos primero)
UPDATE Trabajador
SET correo_trabajador = CONCAT('trabajador.', id_trabajador, '@indecsa.mx')
WHERE correo_trabajador IS NULL;

SET @col_corr = (SELECT IS_NULLABLE FROM information_schema.COLUMNS
                 WHERE TABLE_SCHEMA = DATABASE()
                   AND TABLE_NAME   = 'Trabajador'
                   AND COLUMN_NAME  = 'correo_trabajador');
SET @sql = IF(@col_corr = 'YES',
    'ALTER TABLE Trabajador MODIFY COLUMN correo_trabajador VARCHAR(100) NOT NULL',
    'SELECT 1 -- ya es NOT NULL');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- ─────────────────────────────────────────────────────────────
-- 5. Asignacion_Proyecto_Contratista
--    · Restaurar ACTIVO en el ENUM estatus_contrato
--    · Columnas nuevas: numero_contrato, fecha_fin_estimada,
--      personal_asignado, puestos_requeridos, observaciones
-- ─────────────────────────────────────────────────────────────
ALTER TABLE Asignacion_Proyecto_Contratista
    MODIFY COLUMN estatus_contrato
        ENUM('ACTIVO','VIGENTE','SUSPENDIDO','FINALIZADO','CANCELADO') NOT NULL DEFAULT 'VIGENTE',
    ADD COLUMN IF NOT EXISTS numero_contrato    VARCHAR(50)  NULL,
    ADD COLUMN IF NOT EXISTS fecha_fin_estimada DATE         NULL,
    ADD COLUMN IF NOT EXISTS personal_asignado  INT          NOT NULL DEFAULT 1,
    ADD COLUMN IF NOT EXISTS puestos_requeridos VARCHAR(500) NULL,
    ADD COLUMN IF NOT EXISTS observaciones      VARCHAR(500) NULL;

-- ─────────────────────────────────────────────────────────────
-- 6. Asignacion_Trabajador_Proyecto
--    · Columnas nuevas: puesto_en_proyecto, fecha_inicio,
--      fecha_fin_estimada, observaciones
-- ─────────────────────────────────────────────────────────────
ALTER TABLE Asignacion_Trabajador_Proyecto
    ADD COLUMN IF NOT EXISTS puesto_en_proyecto VARCHAR(100) NULL,
    ADD COLUMN IF NOT EXISTS fecha_inicio       DATE         NULL,
    ADD COLUMN IF NOT EXISTS fecha_fin_estimada DATE         NULL,
    ADD COLUMN IF NOT EXISTS observaciones      VARCHAR(500) NULL;

-- ─────────────────────────────────────────────────────────────
-- 7. Estandar_Rendimiento
--    · Columnas nuevas: descripcion, jornada_base_horas
-- ─────────────────────────────────────────────────────────────
ALTER TABLE Estandar_Rendimiento
    ADD COLUMN IF NOT EXISTS descripcion       VARCHAR(255) NULL,
    ADD COLUMN IF NOT EXISTS jornada_base_horas DECIMAL(5,2) NOT NULL DEFAULT 8.00;

-- ─────────────────────────────────────────────────────────────
-- 8. Tablas nuevas (IF NOT EXISTS = seguro si ya existen)
-- ─────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS Cuadrilla (
    id_cuadrilla      INT AUTO_INCREMENT PRIMARY KEY,
    id_proyecto       INT NOT NULL,
    nombre_cuadrilla  VARCHAR(100) NOT NULL,
    frente_trabajo    VARCHAR(200) NULL,
    estatus_cuadrilla ENUM('ACTIVO','INACTIVO') NOT NULL DEFAULT 'ACTIVO',
    observaciones     VARCHAR(500) NULL,
    UNIQUE KEY uq_cuadrilla_proyecto (nombre_cuadrilla, id_proyecto),
    FOREIGN KEY (id_proyecto) REFERENCES Proyecto(id_proyecto) ON UPDATE CASCADE ON DELETE CASCADE
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
-- 9. Verificación final
--    Muestra las tablas y un conteo por tabla para confirmar.
-- ─────────────────────────────────────────────────────────────
SELECT 'Migracion completada' AS estado;

SELECT
    t.TABLE_NAME                  AS tabla,
    t.TABLE_ROWS                  AS filas_aprox,
    COUNT(c.COLUMN_NAME)          AS num_columnas
FROM information_schema.TABLES  t
JOIN information_schema.COLUMNS c
    ON t.TABLE_SCHEMA = c.TABLE_SCHEMA AND t.TABLE_NAME = c.TABLE_NAME
WHERE t.TABLE_SCHEMA = DATABASE()
  AND t.TABLE_TYPE   = 'BASE TABLE'
GROUP BY t.TABLE_NAME, t.TABLE_ROWS
ORDER BY t.TABLE_NAME;
