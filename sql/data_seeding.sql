-- ============================================================
--  INDECSA · Data Seeding v4.0
--  Compatible con: indecsa_v3_0.sql (v4.0 de la BD)
--  Fecha: 2026-03-15
-- ============================================================
-- NOTAS:
--  · SUPERVISOR eliminado del ENUM de Rol (no existe en la BD v4.0).
--  · contrasenia_contratista y contrasenia_trabajador eliminados
--    (esos campos no existen en la BD v4.0).
--  · nombre_contratista y ubicacion_contratista agregados (NOT NULL en BD).
--  · ubicacion_trabajador agregado (NOT NULL en BD).
--  · estado_proyecto_geo ahora es ENUM('CDMX','Hidalgo','Puebla'):
--    los proyectos se reasignaron a ubicaciones válidas del ENUM.
--  · El orden respeta las dependencias de FK:
--      Rol → Empleado
--      Contratista → Asignacion_Proyecto_Contratista
--      Proyecto    → Asignacion_Proyecto_Contratista
--      Asignacion_Proyecto_Contratista → Asignacion_Trabajador_Proyecto
--      Trabajador  → Asignacion_Trabajador_Proyecto
-- ============================================================
USE indecsa;


-- ===================================
-- 1. ROL
-- ===================================
-- El ENUM de Rol solo acepta: 'ADMIN', 'CAPITAL_HUMANO'
-- Se eliminó SUPERVISOR: no existe en la BD v4.0
INSERT INTO Rol (nombre_rol, descripcion_rol) VALUES
                                                  ('ADMIN',
                                                   'Administrador del sistema con acceso total'),
                                                  ('CAPITAL_HUMANO',
                                                   'Gestion de asignaciones de personal');


-- ===================================
-- 2. EMPLEADO
-- ===================================
-- Se eliminó el empleado con rol SUPERVISOR (ya no existe en el ENUM)
INSERT INTO Empleado (nombre_empleado, correo_empleado, contrasena, id_rol) VALUES
                                                                                ('Admin Principal',
                                                                                 'admin',
                                                                                 '1234',
                                                                                 (SELECT id_rol FROM Rol WHERE nombre_rol = 'ADMIN')),

                                                                                ('Laura Ramirez',
                                                                                 'cap',
                                                                                 '1234',
                                                                                 (SELECT id_rol FROM Rol WHERE nombre_rol = 'CAPITAL_HUMANO'));


-- ===================================
-- 3. CONTRATISTA
-- ===================================
-- Cambios:
--   + nombre_contratista agregado (NOT NULL en BD)
--   + ubicacion_contratista agregado (ENUM NOT NULL en BD: 'CDMX','Hidalgo','Puebla')
--   - contrasenia_contratista eliminado (no existe en la BD)
INSERT INTO Contratista
(nombre_contratista, rfc_contratista, telefono_contratista, correo_contratista,
 descripcion_contratista, experiencia,
 calificacion_contratista, estado_contratista, ubicacion_contratista)
VALUES
    ('Constructora Norte SA',
     'CONSA800101AA1', '5551234567', 'contacto@constructoranorte.com',
     'Empresa especializada en obra civil e infraestructura urbana',
     '15 años en construccion de carreteras y puentes',
     5, 'ACTIVO', 'Hidalgo'),

    ('Edificaciones Sur SC',
     'EDIFE900215BB2', '5559876543', 'info@edificacionessur.com',
     'Contratista de edificaciones residenciales y comerciales',
     '10 años en proyectos habitacionales',
     4, 'ACTIVO', 'CDMX'),

    ('Instalaciones Oscar SRL',
     'INSOS751130CC3', '5554445566', 'ventas@instalacionesoscar.com',
     'Especialista en instalaciones electricas e hidraulicas',
     '8 años en instalaciones industriales',
     3, 'INACTIVO', 'Puebla');


-- ===================================
-- 4. TRABAJADOR
-- ===================================
-- Cambios:
--   + ubicacion_trabajador agregado (ENUM NOT NULL en BD: 'CDMX','Hidalgo','Puebla')
--   - contrasenia_trabajador eliminado (no existe en la BD)
INSERT INTO Trabajador
(nombre_trabajador, nss_trabajador, experiencia,
 telefono_trabajador, correo_trabajador,
 especialidad_trabajador, estado_trabajador,
 descripcion_trabajador, calificacion_trabajador,
 fecha_ingreso, ubicacion_trabajador)
VALUES
    ('Jorge Luis Perez',   '12345678901', '5 años en colado de losas y cimentaciones',
     '5551112233', 'jorge.perez@trabajador.com',
     'Albanil',      'ACTIVO',
     'Trabajador puntual con experiencia en obra gruesa',    5, '2021-03-01', 'Hidalgo'),

    ('Maria Elena Torres', '23456789012', '3 años en instalaciones electricas residenciales',
     '5552223344', 'maria.torres@trabajador.com',
     'Electricista',  'ACTIVO',
     'Certificada en norma NOM-001-SEDE',                    4, '2022-07-15', 'Hidalgo'),

    ('Roberto Salas',      '34567890123', '7 años en plomeria industrial',
     '5553334455', 'roberto.salas@trabajador.com',
     'Plomero',       'ACTIVO',
     'Especialista en redes hidraulicas a presion',          4, '2020-01-10', 'CDMX'),

    ('Ana Gomez',          '45678901234', '2 años en acabados y pintura',
     '5554445566', 'ana.gomez@trabajador.com',
     'Acabados',      'VACACIONES',
     'Experiencia en proyectos residenciales de lujo',       3, '2023-02-20', 'Puebla'),

    ('Luis Hernandez',     '56789012345', '1 año en topografia',
     '5555556677', 'luis.hernandez@trabajador.com',
     'Topografo',     'BAJA',
     'Dado de baja por termino de contrato',                 2, '2024-01-05', 'CDMX');


-- ===================================
-- 5. PROYECTO
-- ===================================
-- Cambios:
--   estado_proyecto_geo ahora es ENUM('CDMX','Hidalgo','Puebla') NOT NULL.
--   Los valores originales ('San Luis Potosi', 'Queretaro', 'Nuevo Leon')
--   no existen en el ENUM; se reasignaron a valores válidos:
--     · Carretera Federal 57  → Hidalgo
--     · Complejo Las Palmas   → Puebla
--     · Planta Industrial     → CDMX
INSERT INTO Proyecto
(nombre_proyecto, tipo_proyecto, lugar_proyecto,
 municipio_proyecto, estado_proyecto_geo,
 fecha_estimada_inicio, fecha_estimada_fin,
 calificacion_proyecto, estatus_proyecto, descripcion_proyecto)
VALUES
    ('Ampliacion Carretera Federal 57',
     'Infraestructura vial', 'Km 142, Carretera 57',
     'Pachuca', 'Hidalgo',
     '2026-02-01', '2026-11-30',
     NULL, 'EN_CURSO',
     'Ampliacion a cuatro carriles del tramo norte de la federal 57'),

    ('Complejo Habitacional Las Palmas',
     'Edificacion residencial', 'Fracc. Las Palmas',
     'Puebla', 'Puebla',
     '2026-04-01', '2027-03-31',
     NULL, 'PLANEACION',
     'Construccion de 120 viviendas de interes social con areas comunes'),

    ('Planta Industrial Zona Norte',
     'Obra industrial', 'Parque Industrial Norte',
     'Azcapotzalco', 'CDMX',
     '2025-06-01', '2026-01-15',
     4, 'FINALIZADO',
     'Construccion de nave industrial de 5000 m2 con instalaciones especiales');


-- ===================================
-- 6. ASIGNACION PROYECTO - CONTRATISTA
-- ===================================
-- Sin cambios en estructura; los datos ya son coherentes con la BD.
INSERT INTO Asignacion_Proyecto_Contratista
(id_proyecto, id_contratista, numero_contrato,
 fecha_inicio, fecha_fin_estimada,
 monto_contratado, estatus_contrato, observaciones)
VALUES
    -- Constructora Norte → Carretera Federal (EN_CURSO)
    ((SELECT id_proyecto    FROM Proyecto    WHERE nombre_proyecto  = 'Ampliacion Carretera Federal 57'),
     (SELECT id_contratista FROM Contratista WHERE rfc_contratista  = 'CONSA800101AA1'),
     'CONT-2026-001',
     '2026-02-01', '2026-11-30',
     4500000.00, 'ACTIVO',
     'Contrato principal de obra civil para ampliacion vial'),

    -- Edificaciones Sur → Complejo Habitacional (PLANEACION)
    ((SELECT id_proyecto    FROM Proyecto    WHERE nombre_proyecto  = 'Complejo Habitacional Las Palmas'),
     (SELECT id_contratista FROM Contratista WHERE rfc_contratista  = 'EDIFE900215BB2'),
     'CONT-2026-002',
     '2026-04-01', '2027-03-31',
     9800000.00, 'ACTIVO',
     'Contrato de edificacion para el complejo residencial'),

    -- Constructora Norte → Planta Industrial (FINALIZADO)
    ((SELECT id_proyecto    FROM Proyecto    WHERE nombre_proyecto  = 'Planta Industrial Zona Norte'),
     (SELECT id_contratista FROM Contratista WHERE rfc_contratista  = 'CONSA800101AA1'),
     'CONT-2025-010',
     '2025-06-01', '2026-01-15',
     12000000.00, 'FINALIZADO',
     'Contrato concluido satisfactoriamente');


-- ===================================
-- 7. ASIGNACION TRABAJADOR - PROYECTO
-- ===================================
-- Sin cambios en estructura; los datos ya son coherentes con la BD.
-- Ana Gomez (VACACIONES) y Luis Hernandez (BAJA) se excluyen:
--   · BAJA e INACTIVO son bloqueados por trigger trg_bloquear_trabajador_inactivo.
--   · VACACIONES es técnicamente permitido pero se omite intencionalmente.
INSERT INTO Asignacion_Trabajador_Proyecto
(id_trabajador, id_proyecto, id_asignacion_pc,
 rol_en_proyecto, fecha_inicio, fecha_fin_estimada,
 estatus_asignacion, observaciones)
VALUES
    -- Jorge Perez → Carretera Federal (albanil, contrato CONT-2026-001)
    ((SELECT id_trabajador   FROM Trabajador WHERE correo_trabajador = 'jorge.perez@trabajador.com'),
     (SELECT id_proyecto     FROM Proyecto   WHERE nombre_proyecto   = 'Ampliacion Carretera Federal 57'),
     (SELECT id_asignacion_pc FROM Asignacion_Proyecto_Contratista  WHERE numero_contrato = 'CONT-2026-001'),
     'Albanil de cimentacion',
     '2026-02-10', '2026-11-30',
     'ACTIVO', 'Asignado a cuadrilla de colado zona norte'),

    -- Maria Torres → Carretera Federal (electricista, mismo contrato)
    ((SELECT id_trabajador   FROM Trabajador WHERE correo_trabajador = 'maria.torres@trabajador.com'),
     (SELECT id_proyecto     FROM Proyecto   WHERE nombre_proyecto   = 'Ampliacion Carretera Federal 57'),
     (SELECT id_asignacion_pc FROM Asignacion_Proyecto_Contratista  WHERE numero_contrato = 'CONT-2026-001'),
     'Electricista de campo',
     '2026-03-01', '2026-11-30',
     'ACTIVO', 'Responsable de alumbrado publico del tramo'),

    -- Roberto Salas → Complejo Habitacional (plomero, contrato CONT-2026-002)
    ((SELECT id_trabajador   FROM Trabajador WHERE correo_trabajador = 'roberto.salas@trabajador.com'),
     (SELECT id_proyecto     FROM Proyecto   WHERE nombre_proyecto   = 'Complejo Habitacional Las Palmas'),
     (SELECT id_asignacion_pc FROM Asignacion_Proyecto_Contratista  WHERE numero_contrato = 'CONT-2026-002'),
     'Plomero jefe de instalaciones',
     '2026-04-05', '2027-03-31',
     'ACTIVO', 'Lider de la red hidraulica del complejo');