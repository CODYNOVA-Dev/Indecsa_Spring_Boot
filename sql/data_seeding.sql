-- ============================================================
-- SEEDING DE DATOS PARA INDECSA v3.0
-- 400 trabajadores · 10 proyectos · 20 contratistas
-- ============================================================
USE indecsa;

-- ─────────────────────────────────────────────────────────────
-- 1. ROLES
-- ─────────────────────────────────────────────────────────────
INSERT INTO Rol (nombre_rol, descripcion_rol) VALUES
('ADMIN',          'Administrador total del sistema y base de datos'),
('CAPITAL_HUMANO', 'Gestión de personal, contratistas y asignaciones');

-- ─────────────────────────────────────────────────────────────
-- 2. EMPLEADOS (4)
-- ─────────────────────────────────────────────────────────────
INSERT INTO Empleado (nombre_empleado, curp, correo_empleado, contrasena, id_rol) VALUES
('Juan Pérez Ruiz',    'PERJ800101HDFRRN01', 'JUAN.PEREZ@INDECSA.COM',   'hash_admin_001', 1),
('Ana García López',   'GARA900202MDFRRN02', 'ana.garcia@indecsa.com',   'hash_rh_002',   2),
('Carlos Mendez Vega', 'MEVC850303HDFRRN03', 'carlos.mendez@indecsa.com','hash_rh_003',   2),
('Rosa Ortiz Moreno',  'ORMR780404MDFRRN04', 'rosa.ortiz@indecsa.com',   'hash_rh_004',   2);

-- ─────────────────────────────────────────────────────────────
-- 3. REGISTROS MIGRATORIOS (5)
-- ─────────────────────────────────────────────────────────────
INSERT INTO registros_migratorios (folio_documento, categoria, fecha_emision, dias_vigencia, fecha_vencimiento, permiso_trabajo, activo) VALUES
('FM3-2025-00001', 'actividades_remuneradas', '2025-01-15', 365, '2026-01-15', 1, 1),
('FM3-2025-00002', 'actividades_remuneradas', '2025-03-10', 730, '2027-03-10', 1, 1),
('FM2-2025-00003', 'negocios',                '2025-06-01', 180, '2025-12-01', 0, 1),
('FM3-2024-00004', 'actividades_remuneradas', '2024-09-20', 365, '2025-09-20', 1, 1),
('FM2-2025-00005', 'transito',                '2025-11-01',  90, '2026-01-29', 0, 1);

-- ─────────────────────────────────────────────────────────────
-- 4. UBICACIONES (10, una por proyecto)
-- ─────────────────────────────────────────────────────────────
INSERT INTO Ubicacion_Proyecto (calle, num_ext, num_int, colonia, cod_post, mun_alc, estado) VALUES
('Paseo de la Reforma',    '450', NULL,     'Cuauhtémoc',           06500, 'Cuauhtémoc',    'CDMX'),
('Av. Juárez',             '100', NULL,     'Centro',               42000, 'Pachuca',       'Hidalgo'),
('Blvd. del Niño Poblano', '1000','A',      'Rincón de la Arborada',72197, 'Puebla',        'Puebla'),
('Insurgentes Sur',        '1500', NULL,    'Del Valle',            03100, 'Benito Juárez', 'CDMX'),
('Av. Tizayuca',           '200', NULL,     'Industrial',           43800, 'Tizayuca',      'Hidalgo'),
('Av. Chapultepec',        '300', 'Piso 5', 'Juárez',               06600, 'Cuauhtémoc',    'CDMX'),
('Carretera Federal 150',  'KM5', NULL,     'Parque Industrial',    72730, 'Cuautlancingo', 'Puebla'),
('Av. Juárez',             '200', NULL,     'Centro Histórico',     42001, 'Pachuca',       'Hidalgo'),
('Paseo de la Reforma',    '200', NULL,     'Tabacalera',           06030, 'Cuauhtémoc',    'CDMX'),
('Av. 14 Sur',             '4321',NULL,     'Rancho Colorado',      72050, 'Puebla',        'Puebla');

-- ─────────────────────────────────────────────────────────────
-- 5. PROYECTOS (10)
-- FIX: tipo_proyecto usa guion bajo (Venta_mobiliaria, Instalacion_de_mobiliario)
-- FIX: estatus usa PAUSADO en lugar de PENDIENTE
-- ─────────────────────────────────────────────────────────────
INSERT INTO Proyecto (nombre_proyecto, tipo_proyecto, oferta_trabajo, cliente, id_ubicacion, municipio_proyecto, estado_proyecto_geo, fecha_estimada_inicio, fecha_estimada_fin, calificacion_proyecto, estatus_proyecto, descripcion_proyecto) VALUES
('Torre Corporativa Reforma',      'Construccion',          'Albañilería y Estructura', 'Grupo Inmobiliario TRC',  1, 'Cuauhtémoc',    'CDMX',    '2026-03-01', '2027-06-30', 5, 'EN_CURSO',   'Edificio corporativo 25 pisos zona financiera'),
('Remodelación Palacio Hidalgo',   'Remodelacion',          'Pintura y Mobiliario',     'Gobierno de Hidalgo',    2, 'Pachuca',       'Hidalgo', '2026-04-15', '2026-10-15', 4, 'EN_CURSO',   'Modernización de inmueble histórico del estado'),
('Plaza Comercial Puebla Centro',  'Construccion',          'Acabados y Estructura',    'Desarrollos del Sur SA', 3, 'Puebla',        'Puebla',  '2026-05-01', '2027-12-31', NULL,'EN_CURSO',  'Centro comercial 3 niveles zona sur'),
('Oficinas Insurgentes Sur',       'Remodelacion',          'Instalaciones y Pintura',  'Banco del Trabajo',      4, 'Benito Juárez', 'CDMX',    '2026-01-10', '2026-07-10', 4, 'FINALIZADO', 'Remodelación integral de 8 pisos de oficinas'),
('Centro Logístico Tizayuca',      'Construccion',          'Obra Civil',               'LogiMex SA de CV',       5, 'Tizayuca',      'Hidalgo', '2026-06-01', '2027-09-30', NULL,'PLANEACION','Bodega y centro de distribución 12,000 m2'),
('Residencial Las Torres CDMX',    'Construccion',          'Estructura y Acabados',    'Inmobiliaria Azteca SA', 6, 'Cuauhtémoc',    'CDMX',    '2026-02-20', '2027-08-20', 3, 'EN_CURSO',   'Conjunto habitacional 4 torres 120 depto'),
('Bodega Industrial Puebla Norte', 'Construccion',          'Acero y Concreto',         'Industrial PU SA',       7, 'Cuautlancingo', 'Puebla',  '2026-05-15', '2027-03-15', NULL,'EN_CURSO',  'Nave industrial 8,500 m2 con andén de carga'),
('Remodelación Museo Pachuca',     'Remodelacion',          'Restauración y Pintura',   'INAH Hidalgo',           8, 'Pachuca',       'Hidalgo', '2025-11-01', '2026-05-01', 5, 'FINALIZADO', 'Restauración y modernización de salas'),
('Instalación Mobiliario BBVA',    'Instalacion_de_mobiliario','Amueblado Corporativo', 'BBVA México SA',         9, 'Cuauhtémoc',    'CDMX',    '2026-03-10', '2026-06-10', 4, 'EN_CURSO',   'Amueblado completo de sucursal corporativa'),
('Ampliación Hospital Puebla',     'Construccion',          'Obra Civil y Sanitaria',   'Secretaría de Salud PU',10, 'Puebla',        'Puebla',  '2026-07-01', '2027-12-31', NULL,'PLANEACION','Nueva ala de urgencias y consultorios');

-- ─────────────────────────────────────────────────────────────
-- 6. CONTRATISTAS (20)
-- FIX: agregados curp, descripcion_contratista, experiencia, calificacion_contratista
-- FIX: ubicacion_contratista en lugar de ubicacion_base
-- ─────────────────────────────────────────────────────────────
INSERT INTO Contratista (nombre_contratista, curp, rfc_contratista, telefono_contratista, correo_contratista, descripcion_contratista, experiencia, calificacion_contratista, estado_contratista, ubicacion_contratista) VALUES
('Constructora Alfa SA',           'CTRT700101HDFTRN01', 'CALF700101AB1', '5512340001', 'contacto@construalfa.mx',  'Expertos en estructuras de concreto y acero',    '15 años en obra civil',          5, 'ACTIVO', 'CDMX'),
('Ingeniería Bravo SC',            'CTRT700201HDFTRN02', 'BRAV760606AB2', '7712340002', 'info@ingbravo.mx',         'Especialistas en instalaciones MEP',             '8 años en proyectos corporativos',4, 'ACTIVO', 'Hidalgo'),
('Edificaciones Delta SA',         'CTRT700301HDFTRN03', 'EDEL790909AB3', '2212340003', 'delta@edif.mx',            'Construcción de plazas comerciales',             '12 años en sector comercial',    4, 'ACTIVO', 'Puebla'),
('Acabados Premier SA',            'CTRT700401HDFTRN04', 'PREM810212AB4', '5512340004', 'premier@acabados.mx',      'Pintura, yeso y acabados finos',                 '10 años en acabados de lujo',    5, 'ACTIVO', 'CDMX'),
('Estructuras del Norte SC',       'CTRT700501HDFTRN05', 'ENOR720414AB5', '7712340005', 'norte@estructuras.mx',     'Diseño y montaje de estructuras metálicas',      '18 años en estructuras',         5, 'ACTIVO', 'Hidalgo'),
('Plomería Industrial SA',         'CTRT700601HDFTRN06', 'PISA830707AB6', '2212340006', 'pisa@plomeria.mx',         'Redes hidráulicas y sanitarias industriales',    '9 años en plomería industrial',  3, 'ACTIVO', 'Puebla'),
('Electra Servicios SA',           'CTRT700701HDFTRN07', 'ELEC740808AB7', '5512340007', 'electra@servicios.mx',     'Instalaciones eléctricas de alta y baja tensión','20 años en instalaciones',       5, 'ACTIVO', 'CDMX'),
('Pinturas y Acabados MX SA',      'CTRT700801HDFTRN08', 'PAMY920303AB8', '7712340008', 'pam@pinturas.mx',          'Pintura industrial, comercial y residencial',    '6 años en sector pinturas',      3, 'ACTIVO', 'Hidalgo'),
('Carpintería Fina SA',            'CTRT700901HDFTRN09', 'CFSA861010AB9', '2212340009', 'cfsa@carpinteria.mx',      'Muebles y herrería arquitectónica a medida',     '14 años en carpintería fina',    4, 'ACTIVO', 'Puebla'),
('Montajes Estructurales SC',      'CTRT701001HDFTRN10', 'MEST910111AC1', '5512340010', 'mest@montajes.mx',         'Montaje de estructuras y cubiertas metálicas',   '11 años en montajes',            4, 'ACTIVO', 'CDMX'),
('Herrerías Unidas SA',            'CTRT701101HDFTRN11', 'HEUN680808AC2', '7712340011', 'heun@herreria.mx',         'Herrería estructural y decorativa',              '22 años en el sector',           5, 'ACTIVO', 'Hidalgo'),
('Impermeabilizaciones Plus SA',   'CTRT701201HDFTRN12', 'IMPL840404AC3', '2212340012', 'impl@impermeab.mx',        'Sistemas de impermeabilización y techos verdes', '7 años en impermeabilización',   3, 'ACTIVO', 'Puebla'),
('Techos y Cubiertas SA',          'CTRT710101HDFTRN13', 'TCSA780616AC4', '5512340013', 'tcsa@techos.mx',           'Cubiertas metálicas y de PVC para naves',        '16 años en sistemas de techo',   4, 'ACTIVO', 'CDMX'),
('Soluciones Técnicas MX SC',      'CTRT710201HDFTRN14', 'STMX820929AC5', '7712340014', 'stmx@solucion.mx',         'Consultoría técnica y supervisión de obra',      '13 años en supervisión',         4, 'ACTIVO', 'Hidalgo'),
('Urbanización Integral SA',       'CTRT710301HDFTRN15', 'URIN730303AC6', '2212340015', 'urin@urbaniza.mx',         'Obra pública, urbanización y pavimentación',     '19 años en obra pública',        5, 'ACTIVO', 'Puebla'),
('Cerrajerías del Sur SA',         'CTRT710401HDFTRN16', 'CDSU950605AC7', '5512340016', 'cds@cerrajeria.mx',         'Sistemas de seguridad y cerrajería industrial',  '5 años en el sector',            3, 'ACTIVO', 'CDMX'),
('Refacciones Industriales SC',    'CTRT710501HDFTRN17', 'REIN770202AC8', '7712340017', 'rein@refacc.mx',            'Mantenimiento y refaccionamiento de maquinaria', '17 años en mantenimiento',       4, 'ACTIVO', 'Hidalgo'),
('Diseño y Construcción PU SA',    'CTRT710601HDFTRN18', 'DCPU880112AC9', '2212340018', 'dcpu@diseno.mx',            'Diseño arquitectónico y construcción residencial','10 años en diseño y obra',      4, 'ACTIVO', 'Puebla'),
('Grupo Constructor Azteca SA',    'CTRT710701HDFTRN19', 'GCAZ760808AD1', '5512340019', 'gcaz@azteca.mx',            'Constructor general de proyectos de gran escala','25 años de trayectoria',        5, 'ACTIVO', 'CDMX'),
('Servicios Integrales HI SC',     'CTRT710801HDFTRN20', 'SIHI850515AD2', '7712340020', 'sihi@servicios.mx',         'Servicios de limpieza, acabados y mantenimiento','8 años en servicios integrales', 3, 'ACTIVO', 'Hidalgo');

-- ─────────────────────────────────────────────────────────────
-- 7. ASIGNACIONES PROYECTO-CONTRATISTA (20 = 2 por proyecto)
-- FIX: agregados numero_contrato, fecha_fin_estimada, personal_asignado
-- ─────────────────────────────────────────────────────────────
INSERT INTO Asignacion_Proyecto_Contratista (id_proyecto, id_contratista, numero_contrato, fecha_inicio, fecha_fin_estimada, personal_asignado, puestos_requeridos, estatus_contrato) VALUES
(1,  1,  'CONT-2026-001', '2026-03-01', '2027-06-30', 50, 'Albañiles, Fierreros, Soldadores',       'VIGENTE'),
(1,  2,  'CONT-2026-002', '2026-03-01', '2027-06-30', 15, 'Electricistas, Plomeros',                'VIGENTE'),
(2,  3,  'CONT-2026-003', '2026-04-15', '2026-10-15', 20, 'Carpinteros, Pintores',                  'VIGENTE'),
(2,  4,  'CONT-2026-004', '2026-04-15', '2026-10-15', 10, 'Instaladores de mobiliario',             'VIGENTE'),
(3,  5,  'CONT-2026-005', '2026-05-01', '2027-12-31', 60, 'Estructura y cimentación',               'VIGENTE'),
(3,  6,  'CONT-2026-006', '2026-05-01', '2027-12-31', 20, 'Plomeros, Sanitarios',                   'VIGENTE'),
(4,  7,  'CONT-2026-007', '2026-01-10', '2026-07-10', 18, 'Electricistas, Instaladores',            'FINALIZADO'),
(4,  8,  'CONT-2026-008', '2026-01-10', '2026-07-10', 12, 'Pintores, Carpinteros',                  'FINALIZADO'),
(5,  9,  'CONT-2026-009', '2026-06-01', '2027-09-30', 45, 'Obra civil general',                     'VIGENTE'),
(5,  10, 'CONT-2026-010', '2026-06-01', '2027-09-30', 20, 'Montajistas, Fierreros',                 'VIGENTE'),
(6,  11, 'CONT-2026-011', '2026-02-20', '2027-08-20', 55, 'Albañiles, Carpinteros, Pintores',       'VIGENTE'),
(6,  12, 'CONT-2026-012', '2026-02-20', '2027-08-20', 15, 'Impermeabilizadores, Técnicos',          'VIGENTE'),
(7,  13, 'CONT-2026-013', '2026-05-15', '2027-03-15', 40, 'Montajistas de estructura metálica',     'VIGENTE'),
(7,  14, 'CONT-2026-014', '2026-05-15', '2027-03-15', 20, 'Soldadores, Herreros',                   'VIGENTE'),
(8,  15, 'CONT-2026-015', '2025-11-01', '2026-05-01', 25, 'Restauradores, Pintores de arte',        'FINALIZADO'),
(8,  16, 'CONT-2026-016', '2025-11-01', '2026-05-01', 10, 'Técnicos en iluminación',                'FINALIZADO'),
(9,  17, 'CONT-2026-017', '2026-03-10', '2026-06-10', 12, 'Instaladores de mobiliario BBVA',        'VIGENTE'),
(9,  18, 'CONT-2026-018', '2026-03-10', '2026-06-10',  8, 'Cerrajeros, Técnicos de seguridad',      'VIGENTE'),
(10, 19, 'CONT-2026-019', '2026-07-01', '2027-12-31', 70, 'Obra civil, hospitalaria y sanitaria',   'VIGENTE'),
(10, 20, 'CONT-2026-020', '2026-07-01', '2027-12-31', 20, 'Plomeros sanitarios, Instaladores MEP',  'VIGENTE');

-- ─────────────────────────────────────────────────────────────
-- 8. TRABAJADORES (400 vía stored procedure)
--
--    CURP: TRAB + YY + MM + 01 + H/M + DFTRN01  (18 chars, único)
--      · 1-200  → H (masculino), índice 0-199 → fechas año 70-86, mes 01-12
--      · 201-400 → M (femenino), mismo ciclo de fechas
--    RFC:  TRABWRKR + ZZZZ + A  (13 chars, único por secuencia)
--    NSS:  secuencial de 11 dígitos
-- ─────────────────────────────────────────────────────────────
DELIMITER //

CREATE PROCEDURE seed_trabajadores()
BEGIN
    DECLARE i       INT DEFAULT 1;
    DECLARE v_idx   INT;
    DECLARE v_year  INT;
    DECLARE v_month INT;
    DECLARE v_sex   CHAR(1);
    DECLARE v_curp             VARCHAR(18);
    DECLARE v_rfc              VARCHAR(13);
    DECLARE v_nss              VARCHAR(11);
    DECLARE v_nombre           VARCHAR(100);
    DECLARE v_puesto           VARCHAR(100);
    DECLARE v_desc_puesto      VARCHAR(500);
    DECLARE v_especialidad     VARCHAR(100);
    DECLARE v_escolaridad      VARCHAR(100);
    DECLARE v_telefono         VARCHAR(15);
    DECLARE v_correo           VARCHAR(100);
    DECLARE v_contratacion     VARCHAR(200);
    DECLARE v_jornada          VARCHAR(200);
    DECLARE v_calle            VARCHAR(100);
    DECLARE v_colonia          VARCHAR(100);
    DECLARE v_mun_alc          VARCHAR(100);
    DECLARE v_estado_addr      VARCHAR(100);
    DECLARE v_calidad          VARCHAR(10);
    DECLARE v_ingreso          DATE;

    WHILE i <= 400 DO
        SET v_idx   = IF(i <= 200, i - 1, i - 201);
        SET v_year  = 70 + (v_idx DIV 12);
        SET v_month = (v_idx MOD 12) + 1;
        SET v_sex   = IF(i <= 200, 'H', 'M');

        SET v_curp  = CONCAT('TRAB', LPAD(v_year, 2, '0'), LPAD(v_month, 2, '0'),
                             '01', v_sex, 'DFTRN01');
        SET v_rfc   = CONCAT('TRABWRKR', LPAD(i, 4, '0'), 'A');
        SET v_nss   = LPAD(i, 11, '0');
        SET v_nombre = CONCAT('Trabajador ', LPAD(i, 3, '0'));

        SET v_puesto = ELT(((i-1) MOD 10) + 1,
            'Albañil','Electricista','Plomero','Carpintero','Pintor',
            'Soldador','Fierrero','Ayudante General','Supervisor','Oficial de Primera');

        SET v_desc_puesto = ELT(((i-1) MOD 5) + 1,
            'Oficial de primera categoría','Oficial de segunda categoría',
            'Ayudante de piso','Jefe de cuadrilla','Operador de maquinaria');

        SET v_especialidad = ELT(((i-1) MOD 10) + 1,
            'Obra civil','Instalaciones eléctricas','Plomería','Carpintería','Pintura',
            'Soldadura','Fierrería','Instalación general','Supervisión de obra','Acabados');

        SET v_escolaridad = ELT(((i-1) MOD 4) + 1,
            'Primaria','Secundaria','Preparatoria','Técnico');

        SET v_telefono     = CONCAT('55', LPAD(i, 8, '0'));
        SET v_correo       = CONCAT('trabajador.', LPAD(i, 3, '0'), '@indecsa.mx');
        SET v_contratacion = IF(i MOD 3 = 0, 'Temporal', 'Planta');
        SET v_jornada      = IF(i MOD 4 = 0, 'Parcial', 'Completa');

        SET v_calle    = CONCAT('Calle ', ELT(((i-1) MOD 5) + 1,
                         'Norte','Sur','Oriente','Poniente','Central'), ' ', ((i-1) MOD 20) + 1);
        SET v_colonia  = ELT(((i-1) MOD 5) + 1,
                         'Obrera','Industrial','Centro','Del Valle','Guerrero');
        SET v_mun_alc  = ELT(((i-1) MOD 5) + 1,
                         'Cuauhtémoc','Azcapotzalco','Pachuca','Puebla','Tizayuca');
        SET v_estado_addr = ELT(((i-1) MOD 3) + 1,
                            'Ciudad de México','Hidalgo','Puebla');
        SET v_calidad  = ELT(((i-1) MOD 3) + 1, 'CDMX','Hidalgo','Puebla');

        SET v_ingreso = DATE_ADD('2018-01-01', INTERVAL ((i * 7) MOD 2920) DAY);

        INSERT INTO Trabajador (
            nombre_trabajador, curp, rfc, nss_trabajador, nacionalidad, id_migratorio,
            calle, num_ext, colonia, cod_post, mun_alc, estado,
            puesto, desc_puesto, especialidad_trabajador, escolaridad,
            telefono_trabajador, correo_trabajador, contratacion, jornada,
            estado_trabajador, fecha_ingreso, calidad_vida
        ) VALUES (
            v_nombre, v_curp, v_rfc, v_nss, 'Mexicana', NULL,
            v_calle, LPAD(i, 3, '0'), v_colonia,
            6000 + ((i-1) MOD 100) * 100, v_mun_alc, v_estado_addr,
            v_puesto, v_desc_puesto, v_especialidad, v_escolaridad,
            v_telefono, v_correo, v_contratacion, v_jornada,
            'ACTIVO', v_ingreso, v_calidad
        );

        SET i = i + 1;
    END WHILE;
END //

DELIMITER ;

CALL seed_trabajadores();
DROP PROCEDURE seed_trabajadores;

-- ─────────────────────────────────────────────────────────────
-- 9. ASIGNACIONES TRABAJADOR-PROYECTO (400 vía stored procedure)
--    Distribución: 40 trabajadores por proyecto
--    FIX: agregados puesto_en_proyecto y fecha_inicio
-- ─────────────────────────────────────────────────────────────
DELIMITER //

CREATE PROCEDURE seed_asignaciones_tp()
BEGIN
    DECLARE i            INT DEFAULT 1;
    DECLARE v_proyecto   INT;
    DECLARE v_asignacion INT;
    DECLARE v_estatus    VARCHAR(20);
    DECLARE v_puesto_proy VARCHAR(100);
    DECLARE v_fecha_ini  DATE;

    WHILE i <= 400 DO
        SET v_proyecto   = ((i - 1) DIV 40) + 1;
        SET v_asignacion = (v_proyecto - 1) * 2 + 1;

        SET v_estatus = IF(v_proyecto IN (4, 8), 'FINALIZADO', 'ACTIVO');

        SET v_puesto_proy = ELT(((i-1) MOD 10) + 1,
            'Albañil','Electricista','Plomero','Carpintero','Pintor',
            'Soldador','Fierrero','Ayudante','Supervisor de cuadrilla','Oficial');

        SET v_fecha_ini = DATE_ADD('2026-01-01',
                            INTERVAL (v_proyecto - 1) * 15 DAY);

        INSERT INTO Asignacion_Trabajador_Proyecto
            (id_trabajador, id_proyecto, id_asignacion_pc,
             puesto_en_proyecto, fecha_inicio, estatus_asignacion)
        VALUES
            (i, v_proyecto, v_asignacion,
             v_puesto_proy, v_fecha_ini, v_estatus);

        SET i = i + 1;
    END WHILE;
END //

DELIMITER ;

CALL seed_asignaciones_tp();
DROP PROCEDURE seed_asignaciones_tp;

-- ─────────────────────────────────────────────────────────────
-- 10. ESTÁNDARES DE RENDIMIENTO (10)
-- FIX: agregada columna descripcion
-- ─────────────────────────────────────────────────────────────
INSERT INTO Estandar_Rendimiento (nombre_actividad, unidad_medida, rendimiento_esperado, descripcion, jornada_base_horas) VALUES
('Colado de losa',             'm3',      6.8000, 'Metros cúbicos de concreto colados por jornada de 8h',       8.00),
('Aplanado de muros',          'm2',     20.0000, 'Metros cuadrados de aplanado fino por jornada de 8h',        8.00),
('Colocación de tabique',      'piezas', 280.000, 'Piezas de tabique colocadas y pegadas por jornada de 8h',    8.00),
('Pintura de muros',           'm2',     32.0000, 'Metros cuadrados pintados (2 manos) por jornada de 8h',      8.00),
('Instalación de mobiliario',  'piezas',  4.0000, 'Piezas de mobiliario instaladas y aseguradas por jornada',   8.00),
('Colocación de azulejo',      'm2',     12.5000, 'Metros cuadrados de azulejo colocado por jornada de 8h',     8.00),
('Colocación de duela',        'm2',     10.0000, 'Metros cuadrados de duela instalada por jornada de 8h',      8.00),
('Impermeabilización',         'm2',     30.0000, 'Metros cuadrados impermeabilizados por jornada de 8h',       8.00),
('Instalación de tubería',     'ml',     20.0000, 'Metros lineales de tubería instalada por jornada de 8h',     8.00),
('Fierro de refuerzo',         'ml',     40.0000, 'Metros lineales de fierro habilitado por jornada de 8h',     8.00);

-- ─────────────────────────────────────────────────────────────
-- 11. CUADRILLAS (20 = 2 por proyecto)
-- ─────────────────────────────────────────────────────────────
INSERT INTO Cuadrilla (id_proyecto, nombre_cuadrilla, frente_trabajo, estatus_cuadrilla) VALUES
(1,  'Cuadrilla A - Reforma',        'Frente Norte – Estructura y Cimentación', 'ACTIVO'),
(1,  'Cuadrilla B - Reforma',        'Frente Sur – Acabados y Fachada',         'ACTIVO'),
(2,  'Cuadrilla A - Hidalgo',        'Planta Baja – Pintura y Yeso',            'ACTIVO'),
(2,  'Cuadrilla B - Hidalgo',        'Pisos 1-3 – Mobiliario y Acabados',       'ACTIVO'),
(3,  'Cuadrilla A - Puebla',         'Nivel 0 – Cimentación',                   'ACTIVO'),
(3,  'Cuadrilla B - Puebla',         'Nivel 1 – Estructura',                    'ACTIVO'),
(4,  'Cuadrilla A - Insurgentes',    'Pisos 1-4 – Eléctrico',                   'INACTIVO'),
(4,  'Cuadrilla B - Insurgentes',    'Pisos 5-8 – Acabados',                    'INACTIVO'),
(5,  'Cuadrilla A - Tizayuca',       'Zona A – Obra Civil',                     'ACTIVO'),
(5,  'Cuadrilla B - Tizayuca',       'Zona B – Estructura Metálica',            'ACTIVO'),
(6,  'Cuadrilla A - Las Torres',     'Torre 1 y 2 – Estructura',                'ACTIVO'),
(6,  'Cuadrilla B - Las Torres',     'Torre 3 y 4 – Acabados',                  'ACTIVO'),
(7,  'Cuadrilla A - Bodega',         'Nave Principal – Acero',                  'ACTIVO'),
(7,  'Cuadrilla B - Bodega',         'Andén de Carga – Concreto',               'ACTIVO'),
(8,  'Cuadrilla A - Museo',          'Sala Principal – Restauración',           'INACTIVO'),
(8,  'Cuadrilla B - Museo',          'Accesos – Pintura y Iluminación',         'INACTIVO'),
(9,  'Cuadrilla A - BBVA',           'Piso 1 – Mobiliario de Escritorios',      'ACTIVO'),
(9,  'Cuadrilla B - BBVA',           'Piso 2 – Mobiliario de Salas',            'ACTIVO'),
(10, 'Cuadrilla A - Hospital',       'Ala Nueva – Obra Gris',                   'ACTIVO'),
(10, 'Cuadrilla B - Hospital',       'Ala Nueva – Instalaciones Sanitarias',    'ACTIVO');

-- ─────────────────────────────────────────────────────────────
-- 12. REGISTRO DE HORAS (200 registros vía stored procedure)
--     Un registro diario por cada uno de los primeros 200 trabajadores.
--     id_cuadrilla: primera cuadrilla del proyecto del trabajador
--       Proyecto N → cuadrilla id = (N-1)*2 + 1
-- ─────────────────────────────────────────────────────────────
DELIMITER //

CREATE PROCEDURE seed_registro_horas()
BEGIN
    DECLARE i           INT DEFAULT 1;
    DECLARE v_proyecto  INT;
    DECLARE v_cuadrilla INT;
    DECLARE v_fecha     DATE;
    DECLARE v_empleado  INT;
    DECLARE v_horas     DECIMAL(5,2);

    WHILE i <= 200 DO
        SET v_proyecto  = ((i - 1) DIV 40) + 1;
        SET v_cuadrilla = (v_proyecto - 1) * 2 + 1;
        SET v_fecha     = DATE_ADD('2026-01-10', INTERVAL ((i - 1) MOD 90) DAY);
        SET v_empleado  = ((i - 1) MOD 4) + 1;
        SET v_horas     = IF(i MOD 5 = 0, 7.50, 8.00);

        INSERT INTO Registro_Horas
            (id_asignacion_tp, id_cuadrilla, fecha_registro,
             horas_trabajadas, tipo_periodo, id_empleado_registro)
        VALUES
            (i, v_cuadrilla, v_fecha, v_horas, 'DIARIO', v_empleado);

        SET i = i + 1;
    END WHILE;
END //

DELIMITER ;

CALL seed_registro_horas();
DROP PROCEDURE seed_registro_horas;

-- ─────────────────────────────────────────────────────────────
-- 13. AVANCES DE PARTIDA (30 registros = 3 por proyecto)
-- ─────────────────────────────────────────────────────────────
INSERT INTO Avance_Partida (id_proyecto, id_cuadrilla, id_estandar, nombre_partida, fecha_registro, cantidad_ejecutada, unidad_medida, cantidad_programada, id_empleado_registro) VALUES
-- Proyecto 1 – Torre Reforma
(1, 1, 1, 'Colado de losa nivel 1',         '2026-03-15', 48.50,  'm3',     60.00, 1),
(1, 1, 2, 'Aplanado muros lobby',            '2026-04-01', 180.00, 'm2',    200.00, 1),
(1, 2, 4, 'Pintura fachada sur',             '2026-04-20', 250.00, 'm2',    300.00, 2),
-- Proyecto 2 – Palacio Hidalgo
(2, 3, 4, 'Pintura sala principal',          '2026-04-20', 120.00, 'm2',    150.00, 2),
(2, 3, 5, 'Instalación mobiliario recepción','2026-05-01',  8.00,  'piezas', 10.00, 2),
(2, 4, 2, 'Aplanado pisos 1-3',              '2026-05-10', 400.00, 'm2',    450.00, 3),
-- Proyecto 3 – Plaza Puebla
(3, 5, 1, 'Colado de losa nivel 0',         '2026-05-10', 120.00, 'm3',    150.00, 1),
(3, 5, 3, 'Colocación tabique muro norte',  '2026-05-20', 8400.0, 'piezas',9000.0, 3),
(3, 6, 9, 'Tubería red hidráulica nivel 0', '2026-06-01', 180.00, 'ml',    220.00, 4),
-- Proyecto 4 – Insurgentes (FINALIZADO)
(4, 7, 4, 'Pintura pisos 1-4',              '2026-02-15', 640.00, 'm2',    640.00, 1),
(4, 7, 7, 'Duela madera pisos 5-8',         '2026-03-01', 520.00, 'm2',    520.00, 2),
(4, 8, 5, 'Mobiliario oficinas A-H',        '2026-03-20', 48.00,  'piezas', 48.00, 3),
-- Proyecto 5 – Tizayuca
(5, 9, 1, 'Cimentación zona A',             '2026-06-15', 85.00,  'm3',    120.00, 1),
(5, 9,10, 'Fierro de refuerzo zona A',      '2026-06-20', 800.00, 'ml',   1000.00, 2),
(5,10, 3, 'Tabique muros bodega',           '2026-07-01', 5600.0, 'piezas',7000.0, 3),
-- Proyecto 6 – Las Torres
(6,11, 1, 'Colado de losa torre 1',        '2026-03-10', 95.00,  'm3',    110.00, 4),
(6,11, 2, 'Aplanado tower 1-2',            '2026-04-05', 600.00, 'm2',    720.00, 1),
(6,12, 8, 'Impermeabilización azotea T1',  '2026-05-01', 450.00, 'm2',    500.00, 2),
-- Proyecto 7 – Bodega
(7,13, 1, 'Colado de piso nave principal', '2026-06-01', 200.00, 'm3',    250.00, 3),
(7,13,10, 'Fierro estructura nave',        '2026-06-10', 1200.0, 'ml',   1500.00, 4),
(7,14, 9, 'Tubería pluvial andén',         '2026-07-01', 240.00, 'ml',    280.00, 1),
-- Proyecto 8 – Museo (FINALIZADO)
(8,15, 4, 'Pintura sala temporal',         '2025-12-01', 280.00, 'm2',    280.00, 2),
(8,15, 6, 'Azulejo acceso principal',      '2025-12-15', 45.00,  'm2',     45.00, 3),
(8,16, 8, 'Impermeabilización cubierta',   '2026-01-10', 180.00, 'm2',    180.00, 4),
-- Proyecto 9 – BBVA
(9,17, 5, 'Mobiliario escritorios piso 1', '2026-03-15', 24.00,  'piezas', 30.00, 1),
(9,17, 5, 'Mobiliario salas de juntas',    '2026-04-01', 12.00,  'piezas', 15.00, 2),
(9,18, 6, 'Azulejo baños piso 2',          '2026-04-10', 18.00,  'm2',     20.00, 3),
-- Proyecto 10 – Hospital
(10,19, 1, 'Colado de cimentación ala',   '2026-07-15', 65.00,  'm3',    100.00, 4),
(10,19, 9, 'Red sanitaria zona urgencias','2026-08-01', 150.00, 'ml',    200.00, 1),
(10,20, 3, 'Tabique muros consultorios',  '2026-08-15', 4200.0, 'piezas',6000.0, 2);
