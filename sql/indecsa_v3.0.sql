-- ============================================================
--  INDECSA · Base de Datos v4.0
--  Fecha: 2026-03-15
-- ============================================================

-- ===================================
-- 1. LIMPIEZA Y CREACIÓN DE BASE
-- ===================================
DROP DATABASE IF EXISTS indecsa;
CREATE DATABASE IF NOT EXISTS indecsa
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
USE indecsa;


-- ===================================
-- 2. TABLAS
-- ===================================

CREATE TABLE Rol (
                     id_rol          INT AUTO_INCREMENT PRIMARY KEY,
                     nombre_rol      ENUM('ADMIN','CAPITAL_HUMANO') NOT NULL UNIQUE,
                     descripcion_rol VARCHAR(255) DEFAULT 'Sin descripcion'
);

CREATE TABLE Empleado (
                          id_empleado     INT AUTO_INCREMENT PRIMARY KEY,
                          nombre_empleado VARCHAR(100) NOT NULL,
                          correo_empleado VARCHAR(100) NOT NULL UNIQUE,
                          contrasena      VARCHAR(255) NOT NULL,
                          id_rol          INT NOT NULL,
                          FOREIGN KEY (id_rol) REFERENCES Rol(id_rol) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE Contratista (
                             id_contratista           INT AUTO_INCREMENT PRIMARY KEY,
                             nombre_contratista       VARCHAR(100) NOT NULL,
                             rfc_contratista          VARCHAR(15)  NOT NULL UNIQUE,
                             telefono_contratista     VARCHAR(15)  NOT NULL,
                             correo_contratista       VARCHAR(100) NOT NULL UNIQUE,
                             descripcion_contratista  VARCHAR(255) NOT NULL,
                             experiencia              VARCHAR(200),
                             calificacion_contratista TINYINT CHECK (calificacion_contratista BETWEEN 1 AND 5),
                             estado_contratista       ENUM('ACTIVO','INACTIVO','SUSPENDIDO') NOT NULL DEFAULT 'ACTIVO',
                             ubicacion_contratista    ENUM('CDMX','Hidalgo','Puebla') NOT NULL
);

CREATE TABLE Trabajador (
                            id_trabajador           INT AUTO_INCREMENT PRIMARY KEY,
                            nombre_trabajador       VARCHAR(100) NOT NULL,
                            nss_trabajador          VARCHAR(15),
                            experiencia             VARCHAR(200),
                            telefono_trabajador     VARCHAR(15)  NOT NULL,
                            correo_trabajador       VARCHAR(100) NOT NULL UNIQUE,
                            especialidad_trabajador VARCHAR(100) NOT NULL,
                            estado_trabajador       ENUM('ACTIVO','INACTIVO','VACACIONES','BAJA') NOT NULL DEFAULT 'ACTIVO',
                            descripcion_trabajador  VARCHAR(800) DEFAULT 'Sin descripcion',
                            calificacion_trabajador TINYINT CHECK (calificacion_trabajador BETWEEN 1 AND 5),
                            fecha_ingreso           DATE NOT NULL,
                            ubicacion_trabajador    ENUM('CDMX','Hidalgo','Puebla') NOT NULL
);

CREATE TABLE Proyecto (
                          id_proyecto           INT AUTO_INCREMENT PRIMARY KEY,
                          nombre_proyecto       VARCHAR(150) NOT NULL,
                          tipo_proyecto         VARCHAR(80),
                          lugar_proyecto        VARCHAR(200),
                          municipio_proyecto    VARCHAR(100),
                          estado_proyecto_geo   ENUM('CDMX','Hidalgo','Puebla') NOT NULL,
                          fecha_estimada_inicio DATE,
                          fecha_estimada_fin    DATE,
                          calificacion_proyecto TINYINT CHECK (calificacion_proyecto BETWEEN 1 AND 5),
                          estatus_proyecto      ENUM('PLANEACION','EN_CURSO','PAUSADO','FINALIZADO','CANCELADO') NOT NULL DEFAULT 'PLANEACION',
                          descripcion_proyecto  VARCHAR(500) DEFAULT 'Sin descripcion'
);

CREATE TABLE Asignacion_Proyecto_Contratista (
                                                 id_asignacion_pc   INT AUTO_INCREMENT PRIMARY KEY,
                                                 id_proyecto        INT NOT NULL,
                                                 id_contratista     INT NOT NULL,
                                                 numero_contrato    VARCHAR(50),
                                                 fecha_inicio       DATE,
                                                 fecha_fin_estimada DATE,
                                                 monto_contratado   DECIMAL(15,2),
                                                 estatus_contrato   ENUM('ACTIVO','SUSPENDIDO','FINALIZADO','RESCINDIDO') NOT NULL DEFAULT 'ACTIVO',
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
                                                rol_en_proyecto    VARCHAR(100),
                                                fecha_inicio       DATE,
                                                fecha_fin_estimada DATE,
                                                estatus_asignacion ENUM('ACTIVO','SUSPENDIDO','FINALIZADO') NOT NULL DEFAULT 'ACTIVO',
                                                observaciones      VARCHAR(500),
                                                UNIQUE KEY uq_trabajador_proyecto (id_trabajador, id_proyecto),
                                                FOREIGN KEY (id_trabajador)    REFERENCES Trabajador(id_trabajador)                         ON UPDATE CASCADE ON DELETE CASCADE,
                                                FOREIGN KEY (id_proyecto)      REFERENCES Proyecto(id_proyecto)                             ON UPDATE CASCADE ON DELETE CASCADE,
                                                FOREIGN KEY (id_asignacion_pc) REFERENCES Asignacion_Proyecto_Contratista(id_asignacion_pc) ON UPDATE CASCADE ON DELETE CASCADE
);


-- ===================================
-- 3. ROLES Y PERMISOS
-- ===================================
DROP ROLE IF EXISTS 'ROL_CAPITALHUMANO','ROL_ADMIN';
CREATE ROLE IF NOT EXISTS 'ROL_ADMIN','ROL_CAPITALHUMANO';

-- ROL_ADMIN: control total
GRANT SELECT, INSERT, UPDATE, DELETE ON indecsa.Rol                              TO 'ROL_ADMIN';
GRANT SELECT, INSERT, UPDATE, DELETE ON indecsa.Empleado                         TO 'ROL_ADMIN';
GRANT SELECT, INSERT, UPDATE, DELETE ON indecsa.Contratista                      TO 'ROL_ADMIN';
GRANT SELECT, INSERT, UPDATE, DELETE ON indecsa.Proyecto                         TO 'ROL_ADMIN';
GRANT SELECT, INSERT, UPDATE, DELETE ON indecsa.Trabajador                       TO 'ROL_ADMIN';
GRANT SELECT, INSERT, UPDATE, DELETE ON indecsa.Asignacion_Proyecto_Contratista  TO 'ROL_ADMIN';
GRANT SELECT, INSERT, UPDATE, DELETE ON indecsa.Asignacion_Trabajador_Proyecto   TO 'ROL_ADMIN';

-- ROL_CAPITALHUMANO: solo lectura en catálogos, gestión de asignaciones
GRANT SELECT                         ON indecsa.Proyecto                         TO 'ROL_CAPITALHUMANO';
GRANT SELECT                         ON indecsa.Contratista                      TO 'ROL_CAPITALHUMANO';
GRANT SELECT                         ON indecsa.Trabajador                       TO 'ROL_CAPITALHUMANO';
GRANT SELECT, INSERT, UPDATE, DELETE ON indecsa.Asignacion_Proyecto_Contratista  TO 'ROL_CAPITALHUMANO';
GRANT SELECT, INSERT, UPDATE, DELETE ON indecsa.Asignacion_Trabajador_Proyecto   TO 'ROL_CAPITALHUMANO';

-- Usuario administrador único
DROP USER IF EXISTS 'Admin_unico'@'%';
CREATE USER 'Admin_unico'@'%' IDENTIFIED BY 'Admin_unico@indecsa.com';
GRANT 'ROL_ADMIN' TO 'Admin_unico'@'%';
ALTER USER 'Admin_unico'@'%' DEFAULT ROLE 'ROL_ADMIN';


-- ===================================
-- 4. TRIGGERS
-- ===================================
DELIMITER //

-- T1: Validar que id_asignacion_pc pertenece al proyecto indicado
CREATE TRIGGER trg_validar_contratista_en_proyecto
    BEFORE INSERT ON Asignacion_Trabajador_Proyecto
    FOR EACH ROW
BEGIN
    DECLARE v_id_proyecto_apc INT;
    SELECT id_proyecto INTO v_id_proyecto_apc
    FROM Asignacion_Proyecto_Contratista
    WHERE id_asignacion_pc = NEW.id_asignacion_pc;
    IF v_id_proyecto_apc IS NULL OR v_id_proyecto_apc <> NEW.id_proyecto THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'El contrato (id_asignacion_pc) no pertenece al proyecto indicado.';
    END IF;
END //

-- T2: No asignar trabajadores a proyectos FINALIZADOS o CANCELADOS
CREATE TRIGGER trg_bloquear_asignacion_proyecto_inactivo
    BEFORE INSERT ON Asignacion_Trabajador_Proyecto
    FOR EACH ROW
BEGIN
    DECLARE v_estatus VARCHAR(20);
    SELECT estatus_proyecto INTO v_estatus
    FROM Proyecto WHERE id_proyecto = NEW.id_proyecto;
    IF v_estatus IN ('FINALIZADO','CANCELADO') THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'No se pueden asignar trabajadores a proyectos FINALIZADOS o CANCELADOS.';
    END IF;
END //

-- T3: No asignar trabajador con estado BAJA o INACTIVO
CREATE TRIGGER trg_bloquear_trabajador_inactivo
    BEFORE INSERT ON Asignacion_Trabajador_Proyecto
    FOR EACH ROW
BEGIN
    DECLARE v_estado VARCHAR(20);
    SELECT estado_trabajador INTO v_estado
    FROM Trabajador WHERE id_trabajador = NEW.id_trabajador;
    IF v_estado IN ('BAJA','INACTIVO') THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'No se puede asignar un trabajador con estado BAJA o INACTIVO.';
    END IF;
END //

-- T4: No asignar contratista SUSPENDIDO o INACTIVO
CREATE TRIGGER trg_bloquear_contratista_inactivo
    BEFORE INSERT ON Asignacion_Proyecto_Contratista
    FOR EACH ROW
BEGIN
    DECLARE v_estado VARCHAR(20);
    SELECT estado_contratista INTO v_estado
    FROM Contratista WHERE id_contratista = NEW.id_contratista;
    IF v_estado IN ('INACTIVO','SUSPENDIDO') THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'No se puede asignar un contratista INACTIVO o SUSPENDIDO a un proyecto.';
    END IF;
END //

-- T5: Validar fechas en Proyecto (INSERT)
CREATE TRIGGER trg_validar_fechas_proyecto
    BEFORE INSERT ON Proyecto
    FOR EACH ROW
BEGIN
    IF NEW.fecha_estimada_fin IS NOT NULL
        AND NEW.fecha_estimada_inicio IS NOT NULL
        AND NEW.fecha_estimada_fin < NEW.fecha_estimada_inicio THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'La fecha de fin estimada no puede ser anterior a la fecha de inicio.';
    END IF;
END //

-- T5b: Validar fechas en Proyecto (UPDATE)
CREATE TRIGGER trg_validar_fechas_proyecto_upd
    BEFORE UPDATE ON Proyecto
    FOR EACH ROW
BEGIN
    IF NEW.fecha_estimada_fin IS NOT NULL
        AND NEW.fecha_estimada_inicio IS NOT NULL
        AND NEW.fecha_estimada_fin < NEW.fecha_estimada_inicio THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'La fecha de fin estimada no puede ser anterior a la fecha de inicio.';
    END IF;
END //

-- T6: Validar fechas en contratos (INSERT)
CREATE TRIGGER trg_validar_fechas_contrato
    BEFORE INSERT ON Asignacion_Proyecto_Contratista
    FOR EACH ROW
BEGIN
    IF NEW.fecha_fin_estimada IS NOT NULL
        AND NEW.fecha_inicio IS NOT NULL
        AND NEW.fecha_fin_estimada < NEW.fecha_inicio THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'La fecha fin del contrato no puede ser anterior a la fecha de inicio.';
    END IF;
END //

-- T6b: Validar fechas en contratos (UPDATE)
CREATE TRIGGER trg_validar_fechas_contrato_upd
    BEFORE UPDATE ON Asignacion_Proyecto_Contratista
    FOR EACH ROW
BEGIN
    IF NEW.fecha_fin_estimada IS NOT NULL
        AND NEW.fecha_inicio IS NOT NULL
        AND NEW.fecha_fin_estimada < NEW.fecha_inicio THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'La fecha fin del contrato no puede ser anterior a la fecha de inicio.';
    END IF;
END //

-- T7: Al finalizar/cancelar proyecto, cerrar asignaciones activas
CREATE TRIGGER trg_cerrar_asignaciones_al_finalizar
    AFTER UPDATE ON Proyecto
    FOR EACH ROW
BEGIN
    IF NEW.estatus_proyecto IN ('FINALIZADO','CANCELADO')
        AND OLD.estatus_proyecto NOT IN ('FINALIZADO','CANCELADO') THEN
        UPDATE Asignacion_Trabajador_Proyecto
        SET    estatus_asignacion = 'FINALIZADO'
        WHERE  id_proyecto = NEW.id_proyecto
          AND  estatus_asignacion = 'ACTIVO';
        UPDATE Asignacion_Proyecto_Contratista
        SET    estatus_contrato = 'FINALIZADO'
        WHERE  id_proyecto = NEW.id_proyecto
          AND  estatus_contrato = 'ACTIVO';
    END IF;
END //

-- T8: Normalizar correos a minúsculas
CREATE TRIGGER trg_normalizar_correo_empleado
    BEFORE INSERT ON Empleado
    FOR EACH ROW
BEGIN
    SET NEW.correo_empleado = LOWER(TRIM(NEW.correo_empleado));
END //

CREATE TRIGGER trg_normalizar_correo_trabajador
    BEFORE INSERT ON Trabajador
    FOR EACH ROW
BEGIN
    SET NEW.correo_trabajador = LOWER(TRIM(NEW.correo_trabajador));
END //

CREATE TRIGGER trg_normalizar_correo_contratista
    BEFORE INSERT ON Contratista
    FOR EACH ROW
BEGIN
    SET NEW.correo_contratista = LOWER(TRIM(NEW.correo_contratista));
END //

DELIMITER ;


-- ===================================
-- 5. PROCEDIMIENTOS ALMACENADOS
-- ===================================
DELIMITER //

-- SP1: Crear usuario de BD desde un empleado
CREATE PROCEDURE sp_crear_usuario_desde_empleado(IN emp_id INT, IN temp_password VARCHAR(255))
BEGIN
    DECLARE emp_correo  VARCHAR(100);
    DECLARE nombre_rol  VARCHAR(50);
    DECLARE nombre_user VARCHAR(32);
    DECLARE rol_db      VARCHAR(50);

    SELECT e.correo_empleado, r.nombre_rol
    INTO   emp_correo, nombre_rol
    FROM   Empleado e
               JOIN   Rol r ON e.id_rol = r.id_rol
    WHERE  e.id_empleado = emp_id;

    IF emp_correo IS NULL THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Empleado no encontrado.';
    END IF;

    CASE
        WHEN nombre_rol = 'ADMIN'          THEN SET rol_db = 'ROL_ADMIN';
        WHEN nombre_rol = 'CAPITAL_HUMANO' THEN SET rol_db = 'ROL_CAPITALHUMANO';
        ELSE
            SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = 'El rol del empleado no tiene un rol de base de datos asociado.';
        END CASE;

    SET nombre_user = LEFT(SUBSTRING_INDEX(emp_correo, '@', 1), 32);

    SET @sql_create  = CONCAT('CREATE USER IF NOT EXISTS ''', nombre_user, '''@''%'' IDENTIFIED BY ''', temp_password, '''');
    SET @sql_grant   = CONCAT('GRANT ', rol_db, ' TO ''', nombre_user, '''@''%''');
    SET @sql_default = CONCAT('ALTER USER ''', nombre_user, '''@''%'' DEFAULT ROLE ', rol_db);

    PREPARE s1 FROM @sql_create;  EXECUTE s1; DEALLOCATE PREPARE s1;
    PREPARE s2 FROM @sql_grant;   EXECUTE s2; DEALLOCATE PREPARE s2;
    PREPARE s3 FROM @sql_default; EXECUTE s3; DEALLOCATE PREPARE s3;

    FLUSH PRIVILEGES;

    SELECT CONCAT('Exito: Usuario ', nombre_user, ' vinculado al rol ', rol_db) AS Status;
END //

-- SP2: Asignar o reasignar rol a un empleado
CREATE PROCEDURE sp_asignar_rol(
    IN p_id_empleado INT,
    IN p_codigo_rol  TINYINT  -- 1=ADMIN | 2=CAPITAL_HUMANO
)
sp_asignar_rol: BEGIN
    DECLARE v_id_rol_nuevo     INT;
    DECLARE v_id_rol_actual    INT;
    DECLARE v_nombre_rol_nuevo VARCHAR(50);
    DECLARE v_nombre_rol_act   VARCHAR(50);
    DECLARE v_existe_empleado  TINYINT DEFAULT 0;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
            ROLLBACK;
            RESIGNAL;
        END;

    SELECT COUNT(*) INTO v_existe_empleado
    FROM Empleado WHERE id_empleado = p_id_empleado;

    IF v_existe_empleado = 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: Empleado no encontrado.';
    END IF;

    CASE p_codigo_rol
        WHEN 1 THEN SET v_nombre_rol_nuevo = 'ADMIN';
        WHEN 2 THEN SET v_nombre_rol_nuevo = 'CAPITAL_HUMANO';
        ELSE
            SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = 'Codigo de rol no reconocido. Valores validos: 1=ADMIN, 2=CAPITAL_HUMANO.';
        END CASE;

    SELECT id_rol INTO v_id_rol_nuevo
    FROM Rol WHERE nombre_rol = v_nombre_rol_nuevo;

    SELECT e.id_rol, r.nombre_rol
    INTO   v_id_rol_actual, v_nombre_rol_act
    FROM   Empleado e
               JOIN   Rol r ON e.id_rol = r.id_rol
    WHERE  e.id_empleado = p_id_empleado;

    IF v_id_rol_actual = v_id_rol_nuevo THEN
        SELECT CONCAT('Sin cambios: El empleado ya tiene el rol ', v_nombre_rol_nuevo) AS Status;
        LEAVE sp_asignar_rol;
    END IF;

    START TRANSACTION;
    UPDATE Empleado SET id_rol = v_id_rol_nuevo WHERE id_empleado = p_id_empleado;
    COMMIT;

    SELECT CONCAT('Exito: Rol actualizado de [', v_nombre_rol_act, '] a [', v_nombre_rol_nuevo, '] para empleado id=', p_id_empleado) AS Status;

END sp_asignar_rol //

DELIMITER ;


-- ===================================
-- 6. DATOS INICIALES
-- ===================================
INSERT INTO Rol (nombre_rol, descripcion_rol) VALUES
                                                  ('ADMIN',         'Acceso total al sistema'),
                                                  ('CAPITAL_HUMANO','Gestión de asignaciones de personal');


-- ===================================
-- EJEMPLOS DE USO
-- ===================================
-- Crear usuario de BD para el empleado con id 1:
--   CALL sp_crear_usuario_desde_empleado(1, 'MiPassword123');
--
-- Asignar rol ADMIN (codigo 1) al empleado con id 5:
--   CALL sp_asignar_rol(5, 1);
--
-- Asignar rol CAPITAL_HUMANO (codigo 2) al empleado con id 3:
--   CALL sp_asignar_rol(3, 2);