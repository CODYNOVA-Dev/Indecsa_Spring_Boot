-- ============================================================
-- SEEDING DE DATOS PARA INDECSA v4.0
-- ============================================================
USE indecsa;

-- 1. Roles (Valores exactos del ENUM)
INSERT INTO Rol (nombre_rol, descripcion_rol) VALUES
    ('ADMIN', 'Administrador total del sistema y base de datos'),
    ('CAPITAL_HUMANO', 'Gestión de personal, contratistas y asignaciones');

-- 2. Empleados (Para probar el SP y Triggers de correo)
INSERT INTO Empleado (nombre_empleado, curp, correo_empleado, contrasena, id_rol) VALUES
    ('Juan Pérez', 'PERJ800101HDFRRN01', 'JUAN.PEREZ@INDECSA.COM', 'hash_seguro_123', 1),
    ('Ana García', 'GARA900202MDFRRN02', 'ana.garcia@indecsa.com', 'hash_seguro_456', 2);

-- 3. Ubicaciones (Requeridas por Proyectos)
INSERT INTO Ubicacion_Proyecto (calle, num_ext, num_int, colonia, cod_post, mun_alc, estado) VALUES
    ('Av. Reforma', '500', 'Piso 10', 'Cuauhtémoc', 06500, 'Cuauhtémoc', 'CDMX'),
    ('Calle Minería', '12', NULL, 'Centro', 42000, 'Pachuca', 'Hidalgo'),
    ('Blvd. Hermanos Serdán', '150', 'A-4', 'Real del Monte', 72000, 'Puebla', 'Puebla');

-- 4. Contratistas (Activos para evitar el Trigger T4)
INSERT INTO Contratista (nombre_contratista, curp, rfc_contratista, telefono_contratista, correo_contratista, descripcion_contratista, experiencia, calificacion_contratista, estado_contratista, ubicacion_contratista) VALUES
    ('Constructora Alpha S.A.', 'CALP700101HDFXYZ01', 'CALP700101ABC', '5512345678', 'contacto@alpha.com', 'Expertos en estructuras metálicas', '15 años en el sector civil', 5, 'ACTIVO', 'CDMX'),
    ('Instalaciones Bravo', 'IBRA850505HDFXYZ02', 'IBRA850505DEF', '7719876543', 'ventas@bravo.mx', 'Especialistas en mobiliario de oficina', '8 años', 4, 'ACTIVO', 'Hidalgo');

-- 5. Registros Migratorios (Para trabajadores extranjeros)
INSERT INTO registros_migratorios (folio_documento, categoria, fecha_emision, dias_vigencia, fecha_vencimiento, permiso_trabajo, activo) VALUES
    ('FM3-987654321', 'actividades_remuneradas', '2025-01-01', 365, '2026-01-01', 1, 1),
    ('FM2-123456789', 'negocios', '2025-06-15', 180, '2025-12-15', 1, 1);

-- 6. Proyectos (Respetando el Trigger T5 de fechas)
INSERT INTO Proyecto (nombre_proyecto, tipo_proyecto, oferta_trabajo, cliente, id_ubicacion, municipio_proyecto, estado_proyecto_geo, fecha_estimada_inicio, fecha_estimada_fin, calificacion_proyecto, estatus_proyecto, descripcion_proyecto) VALUES
    ('Torre Reforma IV', 'Construccion', 'Albañilería y Estructura', 'Gobierno CDMX', 1, 'Cuauhtémoc', 'CDMX', '2026-04-01', '2027-04-01', 5, 'PLANEACION', 'Construcción de edificio corporativo'),
    ('Remodelación Oficinas Hidalgo', 'Remodelacion', 'Pintura y Mobiliario', 'Banco Azteca', 2, 'Pachuca', 'Hidalgo', '2026-03-20', '2026-06-20', 4, 'EN_CURSO', 'Modernización de sucursal centro');

-- 7. Trabajadores (Activos para evitar Trigger T3)
INSERT INTO Trabajador (nombre_trabajador, curp, rfc, nss_trabajador, nacionalidad, id_migratorio, calle, num_ext, colonia, cod_post, mun_alc, estado, puesto, desc_puesto, especialidad_trabajador, escolaridad, experiencia, telefono_trabajador, correo_trabajador, contratacion, jornada, estado_trabajador, fecha_ingreso, calidad_vida) VALUES
    ('Carlos Slim Helú', 'SLIC500101HDFRRR01', 'SLIC500101ABC', '12345678901', 'Mexicana', NULL, 'Polanco', '100', 'Miguel Hidalgo', 11560, 'Miguel Hidalgo', 'CDMX', 'Residente de Obra', 'Supervisión técnica', 'Ingeniería Civil', 'Licenciatura', '10 años', '5500112233', 'carlos@obra.com', 'Planta', 'Completa', 'ACTIVO', '2026-01-15', 'CDMX'),
    ('Jean Pierre', 'PIEJ900101MDFRRR02', 'PIEJ900101XYZ', '98765432109', 'Haitiana', 1, 'Insurgentes', '45', 'Roma Norte', 06700, 'Cuauhtémoc', 'CDMX', 'Ayudante General', 'Carga y descarga', 'Carga pesada', 'Secundaria', '2 años', '5599887766', 'jean@correo.com', 'Temporal', 'Diurna', 'ACTIVO', '2026-02-01', 'CDMX');

-- 8. Asignación Proyecto-Contratista (Respetando Trigger T6)
INSERT INTO Asignacion_Proyecto_Contratista (id_proyecto, id_contratista, numero_contrato, fecha_inicio, fecha_fin_estimada, personal_asignado, puestos_requeridos, estatus_contrato) VALUES
    (1, 1, 'CONT-2026-001', '2026-04-01', '2027-04-01', 50, 'Soldadores, Albañiles, Fierreros', 'VIGENTE'),
    (2, 2, 'CONT-2026-002', '2026-03-20', '2026-06-20', 10, 'Instaladores, Carpinteros', 'VIGENTE');

-- 9. Asignación Trabajador-Proyecto (Respetando Triggers T1, T2 y T3)
INSERT INTO Asignacion_Trabajador_Proyecto (id_trabajador, id_proyecto, id_asignacion_pc, puesto_en_proyecto, fecha_inicio, fecha_fin_estimada, estatus_asignacion) VALUES
    (1, 1, 1, 'Supervisor A', '2026-04-01', '2027-04-01', 'ACTIVO'),
    (2, 2, 2, 'Instalador Jr', '2026-03-21', '2026-06-20', 'ACTIVO');