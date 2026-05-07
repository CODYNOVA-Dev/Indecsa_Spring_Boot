-- ============================================================
-- SCRIPT DE CARGA MASIVA · INDECSA v4.0 (CORREGIDO Y ENLAZADO)
-- ============================================================
USE indecsa;
SET FOREIGN_KEY_CHECKS = 0; -- Desactivar para carga limpia

-- 1. LIMPIEZA
TRUNCATE TABLE Avance_Partida;
TRUNCATE TABLE Registro_Horas;
TRUNCATE TABLE Estandar_Rendimiento;
TRUNCATE TABLE Cuadrilla;
TRUNCATE TABLE Asignacion_Trabajador_Proyecto;
TRUNCATE TABLE Asignacion_Proyecto_Contratista;
TRUNCATE TABLE Trabajador;
TRUNCATE TABLE registros_migratorios;
TRUNCATE TABLE Proyecto;
TRUNCATE TABLE Contratista;
TRUNCATE TABLE Ubicacion_Proyecto;
TRUNCATE TABLE Empleado;
TRUNCATE TABLE Rol;

-- 2. ROLES Y EMPLEADOS
INSERT INTO Rol (id_rol, nombre_rol, descripcion_rol) VALUES
(1, 'ADMIN', 'Administrador total del sistema'),
(2, 'CAPITAL_HUMANO', 'Gestión de personal y asignaciones');

INSERT INTO Empleado (id_empleado, nombre_empleado, curp, correo_empleado, contrasena, id_rol) VALUES
(1, 'Juan Pérez', 'PERJ800101HDFRRN01', 'juan.perez@indecsa.com', 'hash123', 1),
(2, 'Ana García', 'GARA900202MDFRRN02', 'ana.garcia@indecsa.com', 'hash456', 2);

-- 3. UBICACIONES
INSERT INTO Ubicacion_Proyecto (id_ubicacion, calle, num_ext, num_int, colonia, cod_post, mun_alc, estado) VALUES 
(1, 'Paseo de la Reforma', '505', 'Piso 12', 'Cuauhtémoc', 06500, 'Cuauhtémoc', 'CDMX'),
(2, 'Vía Dorada', '100', NULL, 'Zona Plateada', 42084, 'Pachuca', 'Hidalgo'),
(3, 'Av. Juárez', '15', 'Local A', 'Centro Historico', 06000, 'Cuauhtémoc', 'CDMX'),
(4, 'Blvd. Hermanos Serdán', '200', 'B-10', 'Real del Monte', 72000, 'Puebla', 'Puebla');

-- 4. CONTRATISTAS
INSERT INTO Contratista (id_contratista, nombre_contratista, curp, rfc_contratista, telefono_contratista, correo_contratista, descripcion_contratista, estado_contratista, ubicacion_contratista) VALUES
(1, 'Constructora Alpha S.A.', 'CALP700101HDFXYZ01', 'CALP700101ABC', '5512345678', 'contacto@alpha.com', 'Estructuras metálicas', 'ACTIVO', 'CDMX'),
(2, 'Instalaciones Bravo', 'IBRA850505HDFXYZ02', 'IBRA850505DEF', '7719876543', 'ventas@bravo.mx', 'Mobiliario y acabados', 'ACTIVO', 'Hidalgo');

-- 5. REGISTROS MIGRATORIOS (20 FOLIADOS)
INSERT INTO registros_migratorios (id_migratorio, folio_documento, categoria, fecha_emision, dias_vigencia, fecha_vencimiento, permiso_trabajo, activo) VALUES 
(1, 'MIG-2026-001', 'actividades_remuneradas', '2025-05-01', 365, '2026-05-01', 1, 1),
(2, 'MIG-2026-002', 'actividades_remuneradas', '2025-06-15', 365, '2026-06-15', 1, 1),
(3, 'MIG-2026-003', 'actividades_remuneradas', '2025-07-20', 365, '2026-07-20', 1, 1),
(4, 'MIG-2026-004', 'negocios', '2025-08-10', 180, '2026-02-10', 1, 1),
(5, 'MIG-2026-005', 'actividades_remuneradas', '2025-01-10', 730, '2027-01-10', 1, 1),
(6, 'MIG-2026-006', 'actividades_remuneradas', '2025-03-25', 365, '2026-03-25', 1, 1),
(7, 'MIG-2026-007', 'razones_humanitarias', '2025-11-01', 365, '2026-11-01', 1, 1),
(8, 'MIG-2026-008', 'actividades_remuneradas', '2025-12-12', 365, '2026-12-12', 1, 1),
(9, 'MIG-2026-009', 'actividades_remuneradas', '2025-09-05', 365, '2026-09-05', 1, 1),
(10, 'MIG-2026-010', 'negocios', '2026-01-20', 180, '2026-07-20', 1, 1),
(11, 'MIG-2026-011', 'actividades_remuneradas', '2025-02-14', 365, '2026-02-14', 1, 1),
(12, 'MIG-2026-012', 'actividades_remuneradas', '2025-04-30', 365, '2026-04-30', 1, 1),
(13, 'MIG-2026-013', 'actividades_remuneradas', '2025-08-22', 730, '2027-08-22', 1, 1),
(14, 'MIG-2026-014', 'actividades_remuneradas', '2025-10-10', 365, '2026-10-10', 1, 1),
(15, 'MIG-2026-015', 'razones_humanitarias', '2025-05-05', 365, '2026-05-05', 1, 1),
(16, 'MIG-2026-016', 'actividades_remuneradas', '2025-06-01', 365, '2026-06-01', 1, 1),
(17, 'MIG-2026-017', 'actividades_remuneradas', '2025-07-07', 365, '2026-07-07', 1, 1),
(18, 'MIG-2026-018', 'negocios', '2025-09-15', 180, '2026-03-15', 1, 1),
(19, 'MIG-2026-019', 'actividades_remuneradas', '2025-11-20', 365, '2026-11-20', 1, 1),
(20, 'MIG-2026-020', 'actividades_remuneradas', '2025-12-31', 365, '2026-12-31', 1, 1);

-- 6. PROYECTOS (Valores ENUM alineados)
INSERT INTO Proyecto (id_proyecto, nombre_proyecto, tipo_proyecto, oferta_trabajo, cliente, id_ubicacion, municipio_proyecto, estado_proyecto_geo, estatus_proyecto) VALUES
(1, 'Torre Reforma IV', 'Construccion', 'Estructura', 'Corporativo Reforma S.A.', 1, 'Cuauhtémoc', 'CDMX', 'EN_CURSO'),
(2, 'Plaza Pachuca Dorada', 'Remodelacion', 'Acabados', 'Grupo Inmobiliario Plata', 2, 'Pachuca', 'Hidalgo', 'EN_CURSO'),
(3, 'Restaurante Centro', 'Instalacion de mobiliario', 'Cocina', 'Chef Global Mx', 3, 'Cuauhtémoc', 'CDMX', 'FINALIZADO'),
(4, 'Cedis Puebla', 'Construccion', 'Nave', 'Logística Express', 4, 'Puebla', 'Puebla', 'PLANEACION');

-- 7. CONTRATOS (Asignacion_Proyecto_Contratista)
INSERT INTO Asignacion_Proyecto_Contratista (id_asignacion_pc, id_proyecto, id_contratista, numero_contrato, personal_asignado, estatus_contrato) VALUES
(1, 1, 1, 'CONT-REF-001', 50, 'VIGENTE'),
(2, 2, 2, 'CONT-PAC-002', 50, 'VIGENTE');

-- 8. TRABAJADORES (BLOQUE 100)
-- (Debes incluir los 100 inserts de trabajadores aquí, asegurando que id_trabajador sea del 1 al 100)
INSERT INTO Trabajador (id_trabajador, nombre_trabajador, curp, rfc, nss_trabajador, nacionalidad, id_migratorio, calle, num_ext, colonia, cod_post, mun_alc, estado, puesto, desc_puesto, especialidad_trabajador, escolaridad, experiencia, telefono_trabajador, correo_trabajador, contratacion, jornada, estado_trabajador, fecha_ingreso, calidad_vida, sexo) VALUES 
-- EXTRANJEROS (1-20)
(1,'Pierre Node','CURP001EXT','RFC001EXT','NSS001','Haitiana',1,'Reforma','101','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Carga de obra','General','Secundaria','2 años','550001','p1@indecsa.mx','Temporal','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(2,'Jean Valjean','CURP002EXT','RFC002EXT','NSS002','Francesa',2,'Juarez','102','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Carga de obra','General','Secundaria','3 años','550002','p2@indecsa.mx','Temporal','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(3,'Hans Muller','CURP003EXT','RFC003EXT','NSS003','Alemana',3,'Hidalgo','103','Centro',06000,'Cuauhtémoc','CDMX','Soldador','Estructura metálica','Soldadura','Técnico','5 años','550003','p3@indecsa.mx','Temporal','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(4,'John Doe','CURP004EXT','RFC004EXT','NSS004','Estadounidense',4,'Mina','104','Guerrero',06300,'Cuauhtémoc','CDMX','Ayudante','Limpieza','General','Secundaria','1 año','550004','p4@indecsa.mx','Temporal','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(5,'Ali Khan','CURP005EXT','RFC005EXT','NSS005','Paquistaní',5,'Insurgentes','105','Roma',06700,'Cuauhtémoc','CDMX','Ayudante','Carga de obra','General','Secundaria','2 años','550005','p5@indecsa.mx','Temporal','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(6,'Luca Rossi','CURP006EXT','RFC006EXT','NSS006','Italiana',6,'Orizaba','106','Roma',06700,'Cuauhtémoc','CDMX','Cabo','Mando de obra','Obra Civil','Bachiller','8 años','550006','p6@indecsa.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(7,'Mateo Silva','CURP007EXT','RFC007EXT','NSS007','Brasileña',7,'Colima','107','Roma',06700,'Cuauhtémoc','CDMX','Ayudante','Carga de obra','General','Secundaria','2 años','550007','p7@indecsa.mx','Temporal','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(8,'Chen Wei','CURP008EXT','RFC008EXT','NSS008','China',8,'Puebla','108','Roma',06700,'Cuauhtémoc','CDMX','Ayudante','Instalación','Eléctrica','Técnico','3 años','550008','p8@indecsa.mx','Temporal','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(9,'Ivan Petrov','CURP009EXT','RFC009EXT','NSS009','Rusa',9,'Durango','109','Roma',06700,'Cuauhtémoc','CDMX','Ayudante','Carga de obra','General','Secundaria','2 años','550009','p9@indecsa.mx','Temporal','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(10,'Sven Berg','CURP010EXT','RFC010EXT','NSS010','Sueca',10,'Mazatlán','110','Condesa',06140,'Cuauhtémoc','CDMX','Soldador','Estructura metálica','Soldadura','Técnico','6 años','550010','p10@indecsa.mx','Temporal','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(11,'Lars Olaf','CURP011EXT','RFC011EXT','NSS011','Noruega',11,'Michoacán','111','Condesa',06140,'Cuauhtémoc','CDMX','Ayudante','Carga de obra','General','Secundaria','2 años','550011','p11@indecsa.mx','Temporal','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(12,'Kofi Mensah','CURP012EXT','RFC012EXT','NSS012','Ghanesa',12,'Tamaulipas','112','Condesa',06140,'Cuauhtémoc','CDMX','Ayudante','Carga de obra','General','Secundaria','2 años','550012','p12@indecsa.mx','Temporal','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(13,'Kim Min','CURP133EXT','RFC133EXT','NSS013','Coreana',13,'Amsterdam','113','Condesa',06140,'Cuauhtémoc','CDMX','Cabo','Mando de obra','Acabados','Bachiller','7 años','550013','p13@indecsa.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Femenino'),
(14,'Yuki Sato','CURP144EXT','RFC144EXT','NSS014','Japonesa',14,'Nuevo León','114','Condesa',06140,'Cuauhtémoc','CDMX','Ayudante','Instalación','Mobiliario','Secundaria','3 años','550014','p14@indecsa.mx','Temporal','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(15,'Amir Hadad','CURP155EXT','RFC155EXT','NSS015','Egipcia',15,'Baja California','115','Roma Sur',06760,'Cuauhtémoc','CDMX','Ayudante','Carga de obra','General','Secundaria','2 años','550015','p15@indecsa.mx','Temporal','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(16,'Stefan Novak','CURP166EXT','RFC166EXT','NSS016','Polaca',16,'Monterrey','116','Roma Sur',06760,'Cuauhtémoc','CDMX','Ayudante','Carga de obra','General','Secundaria','2 años','550016','p16@indecsa.mx','Temporal','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(17,'Carlos Ruiz','CURP177EXT','RFC177EXT','NSS017','Española',17,'Tlaxcala','117','Roma Sur',06760,'Cuauhtémoc','CDMX','Cabo','Mando de obra','Albañilería','Bachiller','10 años','550017','p17@indecsa.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(18,'Dimitri G.','CURP188EXT','RFC188EXT','NSS018','Griega',18,'Chiapas','118','Roma Sur',06760,'Cuauhtémoc','CDMX','Ayudante','Carga de obra','General','Secundaria','2 años','550018','p18@indecsa.mx','Temporal','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(19,'Bao Nguyen','CURP199EXT','RFC199EXT','NSS019','Vietnamita',19,'Tehuantepec','119','Roma Sur',06760,'Cuauhtémoc','CDMX','Ayudante','Carga de obra','General','Secundaria','2 años','550019','p19@indecsa.mx','Temporal','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(20,'Aarav Gupta','CURP200EXT','RFC200EXT','NSS020','India',20,'Bajio','120','Roma Sur',06760,'Cuauhtémoc','CDMX','Ayudante','Carga de obra','General','Secundaria','2 años','550020','p20@indecsa.mx','Temporal','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
-- MEXICANOS (21-100)
(21,'Aaron Lopez','CURP021MX','RFC021MX','NSS021','Mexicana',NULL,'Calle 21','21','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','3 años','550021','p21@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(22,'Abelardo Ruiz','CURP022MX','RFC022MX','NSS022','Mexicana',NULL,'Calle 22','22','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','2 años','550022','p22@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(23,'Abraham Solis','CURP023MX','RFC023MX','NSS023','Mexicana',NULL,'Calle 23','23','Centro',06000,'Cuauhtémoc','CDMX','Soldador','Obra negra','Gral','Técnico','5 años','550023','p23@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(24,'Adan Torres','CURP024MX','RFC024MX','NSS024','Mexicana',NULL,'Calle 24','24','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','1 año','550024','p24@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(25,'Adrian Lara','CURP025MX','RFC025MX','NSS025','Mexicana',NULL,'Calle 25','25','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','4 años','550025','p25@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(26,'Agustin Mora','CURP026MX','RFC026MX','NSS026','Mexicana',NULL,'Calle 26','26','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','2 años','550026','p26@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(27,'Alberto Galarza','CURP027MX','RFC027MX','NSS027','Mexicana',NULL,'Calle 27','27','Centro',06000,'Cuauhtémoc','CDMX','Cabo','Obra negra','Gral','Bachiller','9 años','550027','p27@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(28,'Aldo Diaz','CURP028MX','RFC028MX','NSS028','Mexicana',NULL,'Calle 28','28','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','2 años','550028','p28@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(29,'Alejandro Perea','CURP029MX','RFC029MX','NSS029','Mexicana',NULL,'Calle 29','29','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','3 años','550029','p29@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(30,'Alfonso Rivas','CURP030MX','RFC030MX','NSS030','Mexicana',NULL,'Calle 30','30','Centro',06000,'Cuauhtémoc','CDMX','Soldador','Obra negra','Gral','Técnico','6 años','550030','p30@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(31,'Alfredo Meza','CURP031MX','RFC031MX','NSS031','Mexicana',NULL,'Calle 31','31','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','2 años','550031','p31@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(32,'Alvaro Ortiz','CURP032MX','RFC032MX','NSS032','Mexicana',NULL,'Calle 32','32','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','2 años','550032','p32@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(33,'Amado Ruiz','CURP033MX','RFC033MX','NSS033','Mexicana',NULL,'Calle 33','33','Centro',06000,'Cuauhtémoc','CDMX','Cabo','Obra negra','Gral','Bachiller','8 años','550033','p33@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(34,'Andres Cano','CURP034MX','RFC034MX','NSS034','Mexicana',NULL,'Calle 34','34','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','4 años','550034','p34@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(35,'Angel Nava','CURP035MX','RFC035MX','NSS035','Mexicana',NULL,'Calle 35','35','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','2 años','550035','p35@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(36,'Anselmo Garcia','CURP036MX','RFC036MX','NSS036','Mexicana',NULL,'Calle 36','36','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','2 años','550036','p36@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(37,'Antonio Hernandez','CURP037MX','RFC037MX','NSS037','Mexicana',NULL,'Calle 37','37','Centro',06000,'Cuauhtémoc','CDMX','Cabo','Obra negra','Gral','Bachiller','10 años','550037','p37@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(38,'Apolo Diaz','CURP038MX','RFC038MX','NSS038','Mexicana',NULL,'Calle 38','38','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','2 años','550038','p38@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(39,'Armando Vaca','CURP039MX','RFC039MX','NSS039','Mexicana',NULL,'Calle 39','39','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','3 años','550039','p39@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(40,'Artemio Luna','CURP040MX','RFC040MX','NSS040','Mexicana',NULL,'Calle 40','40','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','2 años','550040','p40@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(41,'Arturo Peña','CURP041MX','RFC041MX','NSS041','Mexicana',NULL,'Calle 41','41','Centro',06000,'Cuauhtémoc','CDMX','Cabo','Obra negra','Gral','Bachiller','12 años','550041','p41@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(42,'Baldomero Sosa','CURP042MX','RFC042MX','NSS042','Mexicana',NULL,'Calle 42','42','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','2 años','550042','p42@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(43,'Bartolo Meza','CURP043MX','RFC043MX','NSS043','Mexicana',NULL,'Calle 43','43','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','1 año','550043','p43@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(44,'Basilio Rios','CURP044MX','RFC044MX','NSS044','Mexicana',NULL,'Calle 44','44','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','3 años','550044','p44@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(45,'Benito Juarez','CURP045MX','RFC045MX','NSS045','Mexicana',NULL,'Calle 45','45','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','2 años','550045','p45@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(46,'Bernardo Silva','CURP046MX','RFC046MX','NSS046','Mexicana',NULL,'Calle 46','46','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','2 años','550046','p46@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(47,'Blas Luna','CURP047MX','RFC047MX','NSS047','Mexicana',NULL,'Calle 47','47','Centro',06000,'Cuauhtémoc','CDMX','Cabo','Obra negra','Gral','Bachiller','8 años','550047','p47@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(48,'Bonifacio Peña','CURP048MX','RFC048MX','NSS048','Mexicana',NULL,'Calle 48','48','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','2 años','550048','p48@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(49,'Braulio Castro','CURP049MX','RFC049MX','NSS049','Mexicana',NULL,'Calle 49','49','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','3 años','550049','p49@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(50,'Bruno Diaz','CURP050MX','RFC050MX','NSS050','Mexicana',NULL,'Calle 50','50','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra negra','Gral','Secundaria','2 años','550050','p50@mx.mx','Planta','Completa','ACTIVO','2026-01-01','CDMX','Masculino'),
(51,'Calixto Rivas','CURP051MX','RFC051MX','NSS051','Mexicana',NULL,'Calle 51','51','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550051','p51@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(52,'Camilo Sosa','CURP052MX','RFC052MX','NSS052','Mexicana',NULL,'Calle 52','52','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550052','p52@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(53,'Carlos Meza','CURP053MX','RFC053MX','NSS053','Mexicana',NULL,'Calle 53','53','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','4 años','550053','p53@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(54,'Casimiro Peña','CURP054MX','RFC054MX','NSS054','Mexicana',NULL,'Calle 54','54','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','1 año','550054','p54@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(55,'Cayetano Rios','CURP055MX','RFC055MX','NSS055','Mexicana',NULL,'Calle 55','55','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','3 años','550055','p55@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(56,'Cecilio Silva','CURP056MX','RFC056MX','NSS056','Mexicana',NULL,'Calle 56','56','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550056','p56@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(57,'Celestino Luna','CURP057MX','RFC057MX','NSS057','Mexicana',NULL,'Calle 57','57','Centro',06000,'Cuauhtémoc','CDMX','Cabo','Obra','Gral','Bachiller','11 años','550057','p57@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(58,'Cesar Ruiz','CURP058MX','RFC058MX','NSS058','Mexicana',NULL,'Calle 58','58','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550058','p58@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(59,'Christian Vaca','CURP059MX','RFC059MX','NSS059','Mexicana',NULL,'Calle 59','59','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','3 años','550059','p59@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(60,'Claudio Perea','CURP060MX','RFC060MX','NSS060','Mexicana',NULL,'Calle 60','60','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550060','p60@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(61,'Clemente Rivas','CURP061MX','RFC061MX','NSS061','Mexicana',NULL,'Calle 61','61','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550061','p61@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(62,'Conrado Sosa','CURP062MX','RFC062MX','NSS062','Mexicana',NULL,'Calle 62','62','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550062','p62@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(63,'Crispin Mora','CURP063MX','RFC063MX','NSS063','Mexicana',NULL,'Calle 63','63','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','4 años','550063','p63@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(64,'Cristobal Peña','CURP064MX','RFC064MX','NSS064','Mexicana',NULL,'Calle 64','64','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','1 año','550064','p64@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(65,'Cuauhtemoc Rios','CURP065MX','RFC065MX','NSS065','Mexicana',NULL,'Calle 65','65','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','3 años','550065','p65@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(66,'Damián Silva','CURP066MX','RFC066MX','NSS066','Mexicana',NULL,'Calle 66','66','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550066','p66@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(67,'Daniel Luna','CURP067MX','RFC067MX','NSS067','Mexicana',NULL,'Calle 67','67','Centro',06000,'Cuauhtémoc','CDMX','Cabo','Obra','Gral','Bachiller','8 años','550067','p67@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(68,'Dario Peña','CURP068MX','RFC068MX','NSS068','Mexicana',NULL,'Calle 68','68','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550068','p68@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(69,'David Castro','CURP069MX','RFC069MX','NSS069','Mexicana',NULL,'Calle 69','69','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','3 años','550069','p69@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(70,'Demetrio Diaz','CURP070MX','RFC070MX','NSS070','Mexicana',NULL,'Calle 70','70','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550070','p70@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(71,'Diego Rivas','CURP071MX','RFC071MX','NSS071','Mexicana',NULL,'Calle 71','71','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550071','p71@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(72,'Dionisio Sosa','CURP072MX','RFC072MX','NSS072','Mexicana',NULL,'Calle 72','72','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550072','p72@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(73,'Domingo Meza','CURP073MX','RFC073MX','NSS073','Mexicana',NULL,'Calle 73','73','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','4 años','550073','p73@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(74,'Donato Peña','CURP074MX','RFC074MX','NSS074','Mexicana',NULL,'Calle 74','74','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','1 año','550074','p74@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(75,'Edgar Rios','CURP075MX','RFC075MX','NSS075','Mexicana',NULL,'Calle 75','75','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','3 años','550075','p75@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(76,'Edmundo Silva','CURP076MX','RFC076MX','NSS076','Mexicana',NULL,'Calle 76','76','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550076','p76@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(77,'Eduardo Luna','CURP077MX','RFC077MX','NSS077','Mexicana',NULL,'Calle 77','77','Centro',06000,'Cuauhtémoc','CDMX','Cabo','Obra','Gral','Bachiller','9 años','550077','p77@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(78,'Efrain Ruiz','CURP078MX','RFC078MX','NSS078','Mexicana',NULL,'Calle 78','78','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550078','p78@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(79,'Elias Vaca','CURP079MX','RFC079MX','NSS079','Mexicana',NULL,'Calle 79','79','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','3 años','550079','p79@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(80,'Eloy Peña','CURP080MX','RFC080MX','NSS080','Mexicana',NULL,'Calle 80','80','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550080','p80@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(81,'Emilio Rivas','CURP081MX','RFC081MX','NSS081','Mexicana',NULL,'Calle 81','81','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550081','p81@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(82,'Enrique Sosa','CURP082MX','RFC082MX','NSS082','Mexicana',NULL,'Calle 82','82','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550082','p82@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(83,'Erasmo Meza','CURP083MX','RFC083MX','NSS083','Mexicana',NULL,'Calle 83','83','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','4 años','550083','p83@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(84,'Ernesto Peña','CURP084MX','RFC084MX','NSS084','Mexicana',NULL,'Calle 84','84','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','1 año','550084','p84@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(85,'Esteban Rios','CURP085MX','RFC085MX','NSS085','Mexicana',NULL,'Calle 85','85','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','3 años','550085','p85@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(86,'Eugenio Silva','CURP086MX','RFC086MX','NSS086','Mexicana',NULL,'Calle 86','86','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550086','p86@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(87,'Eusebio Luna','CURP087MX','RFC087MX','NSS087','Mexicana',NULL,'Calle 87','87','Centro',06000,'Cuauhtémoc','CDMX','Cabo','Obra','Gral','Bachiller','8 años','550087','p87@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(88,'Eustaquio Peña','CURP088MX','RFC088MX','NSS088','Mexicana',NULL,'Calle 88','88','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550088','p88@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(89,'Evaristo Castro','CURP089MX','RFC089MX','NSS089','Mexicana',NULL,'Calle 89','89','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','3 años','550089','p89@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(90,'Ezra Diaz','CURP090MX','RFC090MX','NSS090','Mexicana',NULL,'Calle 90','90','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550090','p90@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(91,'Fabian Rivas','CURP091MX','RFC091MX','NSS091','Mexicana',NULL,'Calle 91','91','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550091','p91@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(92,'Fausto Sosa','CURP092MX','RFC092MX','NSS092','Mexicana',NULL,'Calle 92','92','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550092','p92@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(93,'Federico Meza','CURP093MX','RFC093MX','NSS093','Mexicana',NULL,'Calle 93','93','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','4 años','550093','p93@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(94,'Felipe Peña','CURP094MX','RFC094MX','NSS094','Mexicana',NULL,'Calle 94','94','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','1 año','550094','p94@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(95,'Felix Rios','CURP095MX','RFC095MX','NSS095','Mexicana',NULL,'Calle 95','95','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','3 años','550095','p95@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(96,'Fermin Silva','CURP096MX','RFC096MX','NSS096','Mexicana',NULL,'Calle 96','96','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550096','p96@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(97,'Fernando Luna','CURP097MX','RFC097MX','NSS097','Mexicana',NULL,'Calle 97','97','Centro',06000,'Cuauhtémoc','CDMX','Cabo','Obra','Gral','Bachiller','10 años','550097','p97@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(98,'Fidel Peña','CURP098MX','RFC098MX','NSS098','Mexicana',NULL,'Calle 98','98','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550098','p98@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(99,'Flavio Castro','CURP099MX','RFC099MX','NSS099','Mexicana',NULL,'Calle 99','99','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','3 años','550099','p99@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino'),
(100,'Froylan Diaz','CURP100MX','RFC100MX','NSS100','Mexicana',NULL,'Calle 100','100','Centro',06000,'Cuauhtémoc','CDMX','Ayudante','Obra','Gral','Secundaria','2 años','550100','p100@mx.mx','Planta','Diurna','ACTIVO','2026-01-01','CDMX','Masculino');
-- 9. ASIGNACIONES TP (PUENTE)
-- Bloque 1: Trabajadores 1-50 al Proyecto 1
-- (Debes incluir los 50 inserts del bloque 1...)
-- Bloque 2: Trabajadores 51-100 al Proyecto 2
-- (Debes incluir los 50 inserts del bloque 2...)
INSERT INTO Asignacion_Trabajador_Proyecto (id_asignacion_tp, id_trabajador, id_proyecto, id_asignacion_pc, puesto_en_proyecto, fecha_inicio, estatus_asignacion) VALUES 
(1, 1, 1, 1, 'Supervisor de Obra', '2026-04-01', 'ACTIVO'),
(2, 2, 1, 1, 'Ayudante General', '2026-04-01', 'ACTIVO'),
(3, 3, 1, 1, 'Soldador Especializado', '2026-04-01', 'ACTIVO'),
(4, 4, 1, 1, 'Ayudante General', '2026-04-01', 'ACTIVO'),
(5, 5, 1, 1, 'Ayudante General', '2026-04-01', 'ACTIVO'),
(6, 6, 1, 1, 'Cabo de Cuadrilla', '2026-04-01', 'ACTIVO'),
(7, 7, 1, 1, 'Ayudante General', '2026-04-01', 'ACTIVO'),
(8, 8, 1, 1, 'Ayudante General', '2026-04-01', 'ACTIVO'),
(9, 9, 1, 1, 'Ayudante General', '2026-04-01', 'ACTIVO'),
(10, 10, 1, 1, 'Soldador Especializado', '2026-04-01', 'ACTIVO'),
(11, 11, 1, 1, 'Ayudante General', '2026-04-01', 'ACTIVO'),
(12, 12, 1, 1, 'Ayudante General', '2026-04-01', 'ACTIVO'),
(13, 13, 1, 1, 'Cabo de Cuadrilla', '2026-04-01', 'ACTIVO'),
(14, 14, 1, 1, 'Ayudante General', '2026-04-01', 'ACTIVO'),
(15, 15, 1, 1, 'Ayudante General', '2026-04-01', 'ACTIVO'),
(16, 16, 1, 1, 'Ayudante General', '2026-04-01', 'ACTIVO'),
(17, 17, 1, 1, 'Cabo de Cuadrilla', '2026-04-01', 'ACTIVO'),
(18, 18, 1, 1, 'Ayudante General', '2026-04-01', 'ACTIVO'),
(19, 19, 1, 1, 'Ayudante General', '2026-04-01', 'ACTIVO'),
(20, 20, 1, 1, 'Ayudante General', '2026-04-01', 'ACTIVO'),
(21, 21, 1, 1, 'Peón', '2026-04-01', 'ACTIVO'),
(22, 22, 1, 1, 'Peón', '2026-04-01', 'ACTIVO'),
(23, 23, 1, 1, 'Soldador', '2026-04-01', 'ACTIVO'),
(24, 24, 1, 1, 'Peón', '2026-04-01', 'ACTIVO'),
(25, 25, 1, 1, 'Peón', '2026-04-01', 'ACTIVO'),
(26, 26, 1, 1, 'Peón', '2026-04-01', 'ACTIVO'),
(27, 27, 1, 1, 'Cabo', '2026-04-01', 'ACTIVO'),
(28, 28, 1, 1, 'Peón', '2026-04-01', 'ACTIVO'),
(29, 29, 1, 1, 'Peón', '2026-04-01', 'ACTIVO'),
(30, 30, 1, 1, 'Soldador', '2026-04-01', 'ACTIVO'),
(31, 31, 1, 1, 'Peón', '2026-04-01', 'ACTIVO'),
(32, 32, 1, 1, 'Peón', '2026-04-01', 'ACTIVO'),
(33, 33, 1, 1, 'Cabo', '2026-04-01', 'ACTIVO'),
(34, 34, 1, 1, 'Peón', '2026-04-01', 'ACTIVO'),
(35, 35, 1, 1, 'Peón', '2026-04-01', 'ACTIVO'),
(36, 36, 1, 1, 'Peón', '2026-04-01', 'ACTIVO'),
(37, 37, 1, 1, 'Cabo', '2026-04-01', 'ACTIVO'),
(38, 38, 1, 1, 'Peón', '2026-04-01', 'ACTIVO'),
(39, 39, 1, 1, 'Peón', '2026-04-01', 'ACTIVO'),
(40, 40, 1, 1, 'Peón', '2026-04-01', 'ACTIVO'),
(41, 41, 1, 1, 'Cabo', '2026-04-01', 'ACTIVO'),
(42, 42, 1, 1, 'Peón', '2026-04-01', 'ACTIVO'),
(43, 43, 1, 1, 'Peón', '2026-04-01', 'ACTIVO'),
(44, 44, 1, 1, 'Peón', '2026-04-01', 'ACTIVO'),
(45, 45, 1, 1, 'Peón', '2026-04-01', 'ACTIVO'),
(46, 46, 1, 1, 'Peón', '2026-04-01', 'ACTIVO'),
(47, 47, 1, 1, 'Cabo', '2026-04-01', 'ACTIVO'),
(48, 48, 1, 1, 'Peón', '2026-04-01', 'ACTIVO'),
(49, 49, 1, 1, 'Peón', '2026-04-01', 'ACTIVO'),
(50, 50, 1, 1, 'Peón', '2026-04-01', 'ACTIVO');

-- Bloque 2: Trabajadores 51-100 al Proyecto 2 (Contrato Bravo id_apc=2)
INSERT INTO Asignacion_Trabajador_Proyecto (id_asignacion_tp, id_trabajador, id_proyecto, id_asignacion_pc, puesto_en_proyecto, fecha_inicio, estatus_asignacion) VALUES 
(51, 51, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(52, 52, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(53, 53, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(54, 54, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(55, 55, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(56, 56, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(57, 57, 2, 2, 'Cabo', '2026-03-20', 'ACTIVO'),
(58, 58, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(59, 59, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(60, 60, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(61, 61, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(62, 62, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(63, 63, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(64, 64, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(65, 65, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(66, 66, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(67, 67, 2, 2, 'Cabo de Acabados', '2026-03-20', 'ACTIVO'),
(68, 68, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(69, 69, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(70, 70, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(71, 71, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(72, 72, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(73, 73, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(74, 74, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(75, 75, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(76, 76, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(77, 77, 2, 2, 'Cabo', '2026-03-20', 'ACTIVO'),
(78, 78, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(79, 79, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(80, 80, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(81, 81, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(82, 82, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(83, 83, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(84, 84, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(85, 85, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(86, 86, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(87, 87, 2, 2, 'Cabo', '2026-03-20', 'ACTIVO'),
(88, 88, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(89, 89, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(90, 90, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(91, 91, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(92, 92, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(93, 93, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(94, 94, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(95, 95, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(96, 96, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(97, 97, 2, 2, 'Cabo', '2026-03-20', 'ACTIVO'),
(98, 98, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(99, 99, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO'),
(100, 100, 2, 2, 'Ayudante', '2026-03-20', 'ACTIVO');

-- 10. CUADRILLAS
INSERT INTO Cuadrilla (id_cuadrilla, id_proyecto, nombre_cuadrilla, frente_trabajo, estatus_cuadrilla) VALUES
(1, 1, 'Cuadrilla Cimentación', 'Norte', 'ACTIVO'),
(2, 1, 'Cuadrilla Acero', 'Torre Principal', 'ACTIVO'),
(3, 2, 'Cuadrilla Acabados', 'Pisos 1-4', 'ACTIVO'),
(4, 2, 'Cuadrilla Eléctrica', 'General', 'ACTIVO'),
(5, 1, 'Cuadrilla Logística', 'Todo el sitio', 'ACTIVO');

-- 11. ESTÁNDARES
INSERT INTO Estandar_Rendimiento (id_estandar, nombre_actividad, unidad_medida, rendimiento_esperado) VALUES
(1, 'Pintura Vinílica', 'm2', 5.5),
(2, 'Instalación Piso', 'm2', 2.2),
(5, 'Armado Estructura', 'ml', 1.8),
(6, 'Muro de Block', 'm2', 1.2),
(8, 'Colado de Losa', 'm3', 0.85);

-- 12. REGISTRO DE HORAS (Los 100 registros que generamos)
-- (Debes pegar aquí el bloque de Registro_Horas del paso anterior)
INSERT INTO Registro_Horas (id_asignacion_tp, id_cuadrilla, fecha_registro, horas_trabajadas, tipo_periodo, id_empleado_registro) VALUES 
(1,1,'2026-05-01',8.0,'DIARIO',2),(2,1,'2026-05-01',8.0,'DIARIO',2),(3,2,'2026-05-01',8.0,'DIARIO',2),(4,2,'2026-05-01',8.0,'DIARIO',2),(5,5,'2026-05-01',8.0,'DIARIO',2),
(6,1,'2026-05-01',8.0,'DIARIO',2),(7,1,'2026-05-01',8.0,'DIARIO',2),(8,2,'2026-05-01',8.0,'DIARIO',2),(9,2,'2026-05-01',8.0,'DIARIO',2),(10,5,'2026-05-01',8.0,'DIARIO',2),
(11,1,'2026-05-01',8.0,'DIARIO',2),(12,1,'2026-05-01',8.0,'DIARIO',2),(13,2,'2026-05-01',8.0,'DIARIO',2),(14,2,'2026-05-01',8.0,'DIARIO',2),(15,5,'2026-05-01',8.0,'DIARIO',2),
(16,1,'2026-05-01',8.0,'DIARIO',2),(17,1,'2026-05-01',8.0,'DIARIO',2),(18,2,'2026-05-01',8.0,'DIARIO',2),(19,2,'2026-05-01',8.0,'DIARIO',2),(20,5,'2026-05-01',8.0,'DIARIO',2),
(21,1,'2026-05-01',8.0,'DIARIO',2),(22,1,'2026-05-01',8.0,'DIARIO',2),(23,2,'2026-05-01',8.0,'DIARIO',2),(24,2,'2026-05-01',8.0,'DIARIO',2),(25,5,'2026-05-01',8.0,'DIARIO',2),
(26,1,'2026-05-01',8.0,'DIARIO',2),(27,1,'2026-05-01',8.0,'DIARIO',2),(28,2,'2026-05-01',8.0,'DIARIO',2),(29,2,'2026-05-01',8.0,'DIARIO',2),(30,5,'2026-05-01',8.0,'DIARIO',2),
(31,1,'2026-05-01',8.0,'DIARIO',2),(32,1,'2026-05-01',8.0,'DIARIO',2),(33,2,'2026-05-01',8.0,'DIARIO',2),(34,2,'2026-05-01',8.0,'DIARIO',2),(35,5,'2026-05-01',8.0,'DIARIO',2),
(36,1,'2026-05-01',8.0,'DIARIO',2),(37,1,'2026-05-01',8.0,'DIARIO',2),(38,2,'2026-05-01',8.0,'DIARIO',2),(39,2,'2026-05-01',8.0,'DIARIO',2),(40,5,'2026-05-01',8.0,'DIARIO',2),
(41,1,'2026-05-01',8.0,'DIARIO',2),(42,1,'2026-05-01',8.0,'DIARIO',2),(43,2,'2026-05-01',8.0,'DIARIO',2),(44,2,'2026-05-01',8.0,'DIARIO',2),(45,5,'2026-05-01',8.0,'DIARIO',2),
(46,1,'2026-05-01',8.0,'DIARIO',2),(47,1,'2026-05-01',8.0,'DIARIO',2),(48,2,'2026-05-01',8.0,'DIARIO',2),(49,2,'2026-05-01',8.0,'DIARIO',2),(50,5,'2026-05-01',8.0,'DIARIO',2);

-- PROYECTO 2: PLAZA PACHUCA DORADA (Asignaciones 51-100)
-- Distribución balanceada en Cuadrillas 3 (Acabados) y 4 (Eléctrica)
INSERT INTO Registro_Horas (id_asignacion_tp, id_cuadrilla, fecha_registro, horas_trabajadas, tipo_periodo, id_empleado_registro) VALUES 
(51,3,'2026-05-01',8.0,'DIARIO',2),(52,4,'2026-05-01',8.0,'DIARIO',2),(53,3,'2026-05-01',8.0,'DIARIO',2),(54,4,'2026-05-01',8.0,'DIARIO',2),(55,3,'2026-05-01',8.0,'DIARIO',2),
(56,4,'2026-05-01',8.0,'DIARIO',2),(57,3,'2026-05-01',8.0,'DIARIO',2),(58,4,'2026-05-01',8.0,'DIARIO',2),(59,3,'2026-05-01',8.0,'DIARIO',2),(60,4,'2026-05-01',8.0,'DIARIO',2),
(61,3,'2026-05-01',8.0,'DIARIO',2),(62,4,'2026-05-01',8.0,'DIARIO',2),(63,3,'2026-05-01',8.0,'DIARIO',2),(64,4,'2026-05-01',8.0,'DIARIO',2),(65,3,'2026-05-01',8.0,'DIARIO',2),
(66,4,'2026-05-01',8.0,'DIARIO',2),(67,3,'2026-05-01',8.0,'DIARIO',2),(68,4,'2026-05-01',8.0,'DIARIO',2),(69,3,'2026-05-01',8.0,'DIARIO',2),(70,4,'2026-05-01',8.0,'DIARIO',2),
(71,3,'2026-05-01',8.0,'DIARIO',2),(72,4,'2026-05-01',8.0,'DIARIO',2),(73,3,'2026-05-01',8.0,'DIARIO',2),(74,4,'2026-05-01',8.0,'DIARIO',2),(75,3,'2026-05-01',8.0,'DIARIO',2),
(76,4,'2026-05-01',8.0,'DIARIO',2),(77,3,'2026-05-01',8.0,'DIARIO',2),(78,4,'2026-05-01',8.0,'DIARIO',2),(79,3,'2026-05-01',8.0,'DIARIO',2),(80,4,'2026-05-01',8.0,'DIARIO',2),
(81,3,'2026-05-01',8.0,'DIARIO',2),(82,4,'2026-05-01',8.0,'DIARIO',2),(83,3,'2026-05-01',8.0,'DIARIO',2),(84,4,'2026-05-01',8.0,'DIARIO',2),(85,3,'2026-05-01',8.0,'DIARIO',2),
(86,4,'2026-05-01',8.0,'DIARIO',2),(87,3,'2026-05-01',8.0,'DIARIO',2),(88,4,'2026-05-01',8.0,'DIARIO',2),(89,3,'2026-05-01',8.0,'DIARIO',2),(90,4,'2026-05-01',8.0,'DIARIO',2),
(91,3,'2026-05-01',8.0,'DIARIO',2),(92,4,'2026-05-01',8.0,'DIARIO',2),(93,3,'2026-05-01',8.0,'DIARIO',2),(94,4,'2026-05-01',8.0,'DIARIO',2),(95,3,'2026-05-01',8.0,'DIARIO',2),
(96,4,'2026-05-01',8.0,'DIARIO',2),(97,3,'2026-05-01',8.0,'DIARIO',2),(98,4,'2026-05-01',8.0,'DIARIO',2),(99,3,'2026-05-01',8.0,'DIARIO',2),(100,4,'2026-05-01',8.0,'DIARIO',2);

-- 13. AVANCES
INSERT INTO Avance_Partida (id_proyecto, id_cuadrilla, id_estandar, nombre_partida, fecha_registro, cantidad_ejecutada, unidad_medida, id_empleado_registro) VALUES
(1, 1, 6, 'Muro de sótano', '2026-05-01', 45.0, 'm2', 2),
(2, 3, 1, 'Pintura locales', '2026-05-01', 150.0, 'm2', 2);

SET FOREIGN_KEY_CHECKS = 1;