-- ============================================================
-- SEEDING DE MÓDULO RENDIMIENTO — Railway Production
-- Adaptado a IDs reales del DB (proyectos 1,2 | asignacion 1)
-- Seguro: INSERT IGNORE evita duplicados por UNIQUE KEY
-- ============================================================
USE indecsa;

-- ──────────────────────────────────────────────────────────────
-- 10. Cuadrillas (2 extra en proyecto 1, 1 extra en proyecto 2)
-- ──────────────────────────────────────────────────────────────
INSERT IGNORE INTO Cuadrilla (id_proyecto, nombre_cuadrilla, frente_trabajo, estatus_cuadrilla)
VALUES
    (1, 'Cuadrilla A',     'Frente Norte - Estructura',           'ACTIVO'),
    (1, 'Cuadrilla B',     'Frente Sur - Cimentación',            'ACTIVO'),
    (2, 'Cuadrilla Única', 'Interior - Pintura y Mobiliario',     'ACTIVO');

-- ──────────────────────────────────────────────────────────────
-- 11. Estándares de rendimiento
-- ──────────────────────────────────────────────────────────────
INSERT IGNORE INTO Estandar_Rendimiento (nombre_actividad, unidad_medida, rendimiento_esperado, descripcion)
VALUES
    ('Colado de losa',            'm3',      0.8500, 'Metros cúbicos de concreto colados por hora-hombre'),
    ('Aplanado de muros',         'm2',      2.5000, 'Metros cuadrados de aplanado por hora-hombre'),
    ('Colocación de tabique',     'piezas', 35.0000, 'Piezas de tabique colocadas por hora-hombre'),
    ('Pintura de muros',          'm2',      4.0000, 'Metros cuadrados pintados por hora-hombre'),
    ('Instalación de mobiliario', 'piezas',  0.5000, 'Piezas de mobiliario instaladas por hora-hombre');

-- ──────────────────────────────────────────────────────────────
-- 12. Registro de horas — usando id_asignacion_tp=1 (único existente)
--     Cuadrilla A = la primera cuadrilla de proyecto 1
-- ──────────────────────────────────────────────────────────────
SET @cuad_a = (SELECT id_cuadrilla FROM Cuadrilla WHERE nombre_cuadrilla = 'Cuadrilla A'     LIMIT 1);
SET @cuad_b = (SELECT id_cuadrilla FROM Cuadrilla WHERE nombre_cuadrilla = 'Cuadrilla B'     LIMIT 1);
SET @cuad_u = (SELECT id_cuadrilla FROM Cuadrilla WHERE nombre_cuadrilla = 'Cuadrilla Única' LIMIT 1);
SET @emp1   = 1;  -- Carlos Admin (ADMIN)
SET @emp2   = 2;  -- Laura RH (CAPITAL_HUMANO)

INSERT IGNORE INTO Registro_Horas
    (id_asignacion_tp, id_cuadrilla, fecha_registro, horas_trabajadas, tipo_periodo, id_empleado_registro)
VALUES
    (1, @cuad_a, '2026-04-01', 8.00, 'DIARIO',   @emp1),
    (1, @cuad_a, '2026-04-02', 8.00, 'DIARIO',   @emp1),
    (1, @cuad_a, '2026-04-03', 7.50, 'DIARIO',   @emp1),
    (1, @cuad_a, '2026-04-07', 8.00, 'DIARIO',   @emp2),
    (1, @cuad_b, '2026-04-08', 8.00, 'DIARIO',   @emp2);

-- ──────────────────────────────────────────────────────────────
-- 13. Avance de partidas ejecutadas
--     Estandar "Colado de losa" y "Pintura de muros"
-- ──────────────────────────────────────────────────────────────
SET @est_losa   = (SELECT id_estandar FROM Estandar_Rendimiento WHERE nombre_actividad = 'Colado de losa'   LIMIT 1);
SET @est_pint   = (SELECT id_estandar FROM Estandar_Rendimiento WHERE nombre_actividad = 'Pintura de muros' LIMIT 1);
SET @est_azul   = (SELECT id_estandar FROM Estandar_Rendimiento WHERE nombre_actividad = 'Colocación de Azulejo' LIMIT 1);

INSERT INTO Avance_Partida
    (id_proyecto, id_cuadrilla, id_estandar, nombre_partida, fecha_registro,
     cantidad_ejecutada, unidad_medida, cantidad_programada, id_empleado_registro)
VALUES
    (1, @cuad_a, @est_losa, 'Colado losa nivel 1',     '2026-04-03', 12.50, 'm3',  15.00, @emp1),
    (1, @cuad_b, @est_losa, 'Colado losa nivel 2',     '2026-04-08', 10.00, 'm3',  15.00, @emp1),
    (2, @cuad_u, @est_pint, 'Pintura interior sala A', '2026-04-05', 45.00, 'm2',  50.00, @emp2),
    (2, @cuad_u, @est_azul, 'Piso baño secundario',    '2026-04-06',  8.00, 'm2',  12.00, @emp2);

-- ──────────────────────────────────────────────────────────────
-- Verificación final
-- ──────────────────────────────────────────────────────────────
SELECT 'Cuadrilla'            tbl, COUNT(*) total FROM Cuadrilla
UNION ALL
SELECT 'Estandar_Rendimiento',      COUNT(*) FROM Estandar_Rendimiento
UNION ALL
SELECT 'Registro_Horas',            COUNT(*) FROM Registro_Horas
UNION ALL
SELECT 'Avance_Partida',            COUNT(*) FROM Avance_Partida;
