-- ============================================================
--  INDECSA · Data Seeding v4.2
--  Compatible con: indecsa_v3_0.sql (v4.0 de la BD)
--  Fecha: 2026-03-17
-- ============================================================
-- NOTA: indecsa_v3_0.sql ya hace INSERT en Rol al final (sección 6).
--       Este script NO repite ese INSERT para evitar Duplicate entry.
--       Ejecutar SIEMPRE después de indecsa_v3_0.sql.
-- ============================================================
USE indecsa;


-- ===================================
-- 2. EMPLEADO
-- ===================================
-- correo: 'admin'  → contraseña texto claro: 1234
-- correo: 'cap'    → contraseña texto claro: 1234
-- Hash generado con: new BCryptPasswordEncoder().encode("1234")
INSERT INTO Empleado (nombre_empleado, correo_empleado, contrasena, id_rol) VALUES
    ('Admin Principal',
    'admin',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
    (SELECT id_rol FROM Rol WHERE nombre_rol = 'ADMIN')),

    ('Laura Ramirez',
    'cap',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
    (SELECT id_rol FROM Rol WHERE nombre_rol = 'CAPITAL_HUMANO'));


-- ===================================
-- 3. CONTRATISTA
-- ===================================
INSERT INTO Contratista (
    nombre_contratista, rfc_contratista, telefono_contratista,
    correo_contratista, descripcion_contratista, experiencia,
    calificacion_contratista, estado_contratista, ubicacion_contratista
) VALUES
-- CDMX
('Constructora Noreste CDMX S.A. de C.V.',      'CNO850312HDF',  '5511223344', 'contacto@noreste-cdmx.com',      'Empresa especializada en obra civil y edificación de uso mixto en zona metropolitana',          '20 años en proyectos residenciales, comerciales e industriales en CDMX y área metropolitana',  5, 'ACTIVO',     'CDMX'),
('Ingeniería Estructural Vázquez S.C.',          'IEV910605HDF',  '5522334455', 'proyectos@ievazquez.com',        'Despacho de ingeniería enfocado en estructuras de concreto y acero para edificios de gran altura','15 años en diseño y supervisión de estructuras para proyectos de más de 10 niveles',           4, 'SUSPENDIDO', 'CDMX'),
('Instalaciones Integrales del Centro S.A.',     'IIC780923HDF',  '5533445566', 'info@instalacionescentro.com',   'Contratista MEP con amplia experiencia en instalaciones eléctricas, hidrosanitarias y gas',      '18 años ejecutando instalaciones en proyectos hospitalarios, hoteleros y corporativos',          4, 'ACTIVO',     'CDMX'),
('Remodelaciones y Acabados RGA S. de R.L.',     'RGA001118HDF',  '5544556677', 'rga@acabadosrga.com',            'Empresa de remodelación y acabados de alta calidad para espacios comerciales y corporativos',    '12 años en proyectos de remodelación de oficinas, centros comerciales y espacios institucionales',3, 'INACTIVO',   'CDMX'),
('Grupo Constructor Metropolitano S.A.',         'GCM920714HDF',  '5555667788', 'info@gcmetropolitano.com',       'Contratista general con capacidad para obra mayor en sector público y privado',                  '22 años como contratista general en licitaciones públicas y proyectos privados de gran escala',   5, 'ACTIVO',     'CDMX'),
('Pavimentaciones y Vialidades PV S.A.',         'PVV030509HDF',  '5566778899', 'contacto@pavimentacionespv.com', 'Especialistas en infraestructura vial, pavimentación y obras de urbanización',                   '10 años en construcción de vialidades, puentes peatonales y obras de urbanización en CDMX',      3, 'INACTIVO',   'CDMX'),
('Servicios de Ingeniería SEICO S.A. de C.V.',   'SEI110830HDF',  '5577889900', 'seico@ingenieria.com',           'Empresa de servicios técnicos especializados en supervisión y control de proyectos',             '8 años en gestión de proyectos, supervisión de obra y control de calidad para sector privado',    3, 'ACTIVO',     'CDMX'),
('Construye Fácil del Valle S. de R.L.',         'CFV160202HDF',  '5588990011', 'hola@construyefacil.com',        'Empresa mediana enfocada en obra residencial y pequeñas remodelaciones',                        '6 años en vivienda unifamiliar, fraccionamientos pequeños y remodelaciones residenciales',         2, 'SUSPENDIDO', 'CDMX'),
('Prefabricados Industriales PREIN S.A.',         'PRI881205HDF',  '5599001122', 'ventas@prein.com.mx',            'Fabricante e instalador de estructuras prefabricadas de concreto y acero para uso industrial',   '16 años en fabricación de paneles, trabes y columnas prefabricadas para naves industriales',      4, 'ACTIVO',     'CDMX'),
('Cimentaciones Profundas CP S.A. de C.V.',      'CPP950317HDF',  '5500112233', 'cp@cimentaciones.mx',            'Empresa especializada en cimentaciones profundas, pilotes y obras de contención en suelos blandos','17 años en cimentaciones especiales para zonas lacustres y suelos de alta compresibilidad',      5, 'INACTIVO',   'CDMX'),

-- Hidalgo
('Constructora Hidalguense del Norte S.A.',      'CHN830614HGO',  '7711223344', 'info@chidalguense.com',          'Empresa local con amplia trayectoria en obra pública y privada en el estado de Hidalgo',         '25 años ejecutando proyectos de infraestructura, vivienda e industria en Hidalgo y Querétaro',    5, 'ACTIVO',     'Hidalgo'),
('Ingeniería Civil Pachuca S.C.',                'ICP770928HGO',  '7722334455', 'contacto@icpachuca.com',         'Despacho de ingeniería civil con especialidad en hidráulica y saneamiento',                      '20 años en proyectos de agua potable, drenaje y plantas de tratamiento en zona centro',            4, 'INACTIVO',   'Hidalgo'),
('Electrificación Industrial EI S.A.',           'EII001025HGO',  '7733445566', 'ei@electrificacion.mx',          'Contratista eléctrico industrial con experiencia en subestaciones y líneas de media tensión',     '14 años en electrificación de parques industriales, subestaciones y líneas de distribución',       4, 'ACTIVO',     'Hidalgo'),
('Carreteras y Puentes CP Hidalgo S.A.',         'CPH910303HGO',  '7744556677', 'cpuentes@hidalgo.com',           'Empresa especializada en infraestructura carretera, puentes y obras de arte',                    '18 años en construcción de carreteras federales, estatales y puentes en zona centro-norte',        5, 'SUSPENDIDO', 'Hidalgo'),
('Desarrollos Industriales Sahagún S.A.',        'DIS040817HGO',  '7755667788', 'info@dissahagun.com',            'Constructora enfocada en naves industriales y parques logísticos en corredor industrial',         '12 años en construcción de naves, bodegas y parques industriales en Hidalgo y Tlaxcala',           4, 'ACTIVO',     'Hidalgo'),
('Restauración y Patrimonio RP S.C.',            'RPP120614HGO',  '7766778899', 'rp@restauracion.mx',             'Despacho especializado en restauración de edificios históricos y zonas arqueológicas',            '9 años en restauración de inmuebles catalogados y trabajos con supervisión del INAH',              3, 'ACTIVO',     'Hidalgo'),
('Obra Pública Hidalgo OPH S.A. de C.V.',        'OPH860520HGO',  '7777889900', 'oph@obrapublica.mx',             'Contratista de obra pública con amplia experiencia en licitaciones de gobierno estatal y municipal','22 años participando en licitaciones públicas de salud, educación e infraestructura en Hidalgo',  5, 'INACTIVO',   'Hidalgo'),
('Geotecnia y Pavimentos GP S.A.',               'GPP930711HGO',  '7788990011', 'gp@geotecnia.mx',                'Empresa especializada en estudios geotécnicos, pavimentos y obras de terracería',                 '15 años en pavimentación, terracerías y estudios de suelo para proyectos carreteros',              3, 'ACTIVO',     'Hidalgo'),
('Instalaciones Hidalgo IH S. de R.L.',          'IHI050216HGO',  '7799001122', 'ih@instalacioneshgo.com',        'Empresa de instalaciones eléctricas, hidrosanitarias y especiales para sector residencial',       '7 años en instalaciones para fraccionamientos, edificios de vivienda y equipamientos',             2, 'SUSPENDIDO', 'Hidalgo'),
('Supervisión Técnica Integral STI S.C.',         'STI180930HGO',  '7700112233', 'sti@supervision.mx',             'Despacho de supervisión e interventoría técnica para proyectos de cualquier magnitud',             '11 años en supervisión de obra para organismos de gobierno y desarrolladores privados',             4, 'ACTIVO',     'Hidalgo'),

-- Puebla
('Constructora Angelópolis S.A. de C.V.',        'CAP890415PUE',  '2211223344', 'info@angelopolis-const.com',     'Empresa líder en construcción de proyectos residenciales y de uso mixto en Puebla y Tlaxcala',    '20 años en torres residenciales, conjuntos habitacionales y desarrollos de uso mixto en Puebla',   5, 'SUSPENDIDO', 'Puebla'),
('Infraestructura Poblana IP S.A.',              'IPP760812PUE',  '2222334455', 'contacto@infraestructurapue.com','Contratista general con vasta experiencia en obra de infraestructura urbana y carretera',         '24 años en carreteras, viaductos, distribuidores viales y obras hidráulicas en Puebla',            5, 'ACTIVO',     'Puebla'),
('Instalaciones Especiales Puebla S.A.',         'IEP020309PUE',  '2233445566', 'iep@instalacionespue.mx',        'Empresa especializada en instalaciones MEP para proyectos industriales y hospitalarios',          '13 años en instalaciones para hospitales, plantas automotrices y centros comerciales en Puebla',    4, 'INACTIVO',   'Puebla'),
('Plantas Industriales del Sur PIS S.A.',        'PSI971104PUE',  '2244556677', 'pis@plantasindustriales.mx',     'Constructora especializada en naves industriales, plantas de proceso y ampliaciones de fábrica',   '16 años en construcción de plantas industriales para sector automotriz, alimentario y farmacéutico',4, 'ACTIVO',     'Puebla'),
('Energías Renovables Poblanas ERP S.A.',        'ERP140625PUE',  '2255667788', 'erp@energiaspoblanas.com',       'Empresa especializada en construcción de parques solares y proyectos de energía renovable',        '9 años en instalación de plantas fotovoltaicas, parques eólicos y subestaciones de interconexión',  4, 'ACTIVO',     'Puebla'),
('Restauración Patrimonial Puebla RPP S.C.',     'RPP081112PUE',  '2266778899', 'rpp@restauracionpuebla.mx',      'Despacho con amplia experiencia en restauración de inmuebles del centro histórico de Puebla',      '15 años en restauración de edificios coloniales bajo normativa del INAH e INBA en Puebla',          4, 'SUSPENDIDO', 'Puebla'),
('Desarrollos Agroindustriales DA S.A.',         'DAI190707PUE',  '2277889900', 'da@agroindustriales.mx',         'Empresa constructora enfocada en infraestructura para el sector agroalimentario e industrial',     '10 años en plantas procesadoras, cámaras frigoríficas y bodegas para el sector agroindustrial',    3, 'ACTIVO',     'Puebla'),
('Grupo Hotelero Constructor GHC S.A.',          'GHC030214PUE',  '2288990011', 'ghc@hotelero.mx',                'Constructora con experiencia en proyectos hoteleros, boutique y de hospedaje patrimonial',         '11 años en construcción y rehabilitación de hoteles en centros históricos y zonas turísticas',      3, 'INACTIVO',   'Puebla'),
('Urbanizaciones y Lotificaciones UL S.A.',      'ULP001121PUE',  '2299001122', 'ul@urbanizaciones.mx',           'Empresa especializada en urbanización de fraccionamientos, vialidades internas y redes de servicios','8 años en habilitación de fraccionamientos residenciales y de interés social en Puebla',          2, 'ACTIVO',     'Puebla'),
('Supervisión e Interventoría Puebla SIP S.C.',  'SIP150318PUE',  '2200112233', 'sip@supervision-pue.mx',         'Despacho de supervisión técnica especializado en obra pública y privada de gran escala',            '12 años en interventoría para proyectos de infraestructura, hospitales y edificación en Puebla',    5, 'SUSPENDIDO', 'Puebla');


-- ===================================
-- 4. TRABAJADOR
-- ===================================
INSERT INTO Trabajador (
    nombre_trabajador, nss_trabajador, experiencia, telefono_trabajador,
    correo_trabajador, especialidad_trabajador, estado_trabajador,
    descripcion_trabajador, calificacion_trabajador, fecha_ingreso, ubicacion_trabajador
) VALUES
-- CDMX
('Carlos Mendoza Ríos',       '12345678901', '8 años en obra civil y estructuras de concreto',           '5512345678', 'carlos.mendoza@indecsa.com',    'Albañilería',           'VACACIONES','Especialista en cimentaciones y muros de carga',              5, '2018-03-12', 'CDMX'),
('Jorge Luis Peña Vargas',    '12345678902', '5 años en instalaciones eléctricas industriales',          '5523456789', 'jorge.pena@indecsa.com',        'Electricidad',          'ACTIVO',    'Certificado en NOM-001-SEDE para instalaciones eléctricas',   4, '2020-06-01', 'CDMX'),
('Miguel Ángel Torres Sosa',  '12345678903', '3 años en plomería residencial y comercial',               '5534567890', 'miguel.torres@indecsa.com',     'Plomería',              'BAJA',      'Manejo de sistemas de agua caliente y fría',                  3, '2022-01-15', 'CDMX'),
('Roberto Fuentes Castillo',  '12345678904', '10 años en soldadura estructural',                         '5545678901', 'roberto.fuentes@indecsa.com',   'Soldadura',             'ACTIVO',    'Soldadura MIG, TIG y arco eléctrico en estructuras metálicas',5, '2015-09-20', 'CDMX'),
('Andrés Villanueva Cruz',    '12345678905', '6 años en acabados y pintura de interiores',               '5556789012', 'andres.villanueva@indecsa.com', 'Pintura y Acabados',    'INACTIVO',  'Aplicación de pintura vinílica, esmalte y texturizados',      4, '2019-04-10', 'CDMX'),
('Fernando Guzmán Leal',      '12345678906', '4 años como operador de maquinaria pesada',                '5567890123', 'fernando.guzman@indecsa.com',   'Maquinaria Pesada',     'ACTIVO',    'Operación de excavadora, retroexcavadora y compactadora',     4, '2021-02-28', 'CDMX'),
('Héctor Morales Jiménez',    '12345678907', '7 años en carpintería para obra',                          '5578901234', 'hector.morales@indecsa.com',    'Carpintería',           'ACTIVO',    'Fabricación e instalación de cimbra y acabados en madera',    4, '2017-11-05', 'CDMX'),
('Luis Alberto Ramos Díaz',   '12345678908', '2 años en instalaciones hidráulicas',                      '5589012345', 'luis.ramos@indecsa.com',        'Plomería',              'VACACIONES','Apoyo en sistemas de drenaje y tuberías de PVC',              2, '2023-07-01', 'CDMX'),
('Ernesto Salinas Bravo',     '12345678909', '9 años en supervisión de obra',                            '5590123456', 'ernesto.salinas@indecsa.com',   'Supervisión de Obra',   'ACTIVO',    'Supervisión de frentes de trabajo y control de calidad',      5, '2016-05-18', 'CDMX'),
('Daniel Ortega Navarro',     '12345678910', '1 año como ayudante general de construcción',              '5501234567', 'daniel.ortega@indecsa.com',     'Ayudante General',      'BAJA',      'Apoyo en carga, mezcla y limpieza de obra',                   2, '2024-01-10', 'CDMX'),

-- Hidalgo
('Juan Pablo Reyes Téllez',   '23456789001', '11 años en estructuras de acero',                          '7712345678', 'juan.reyes@indecsa.com',        'Estructuras Metálicas', 'ACTIVO',    'Montaje de estructuras metálicas para naves industriales',    5, '2014-08-22', 'Hidalgo'),
('Alejandro Rojas Herrera',   '23456789002', '6 años en instalaciones sanitarias',                       '7723456789', 'alejandro.rojas@indecsa.com',   'Plomería',              'INACTIVO',  'Instalación de sistemas de drenaje pluvial y sanitario',      4, '2019-03-14', 'Hidalgo'),
('Marco Antonio Luna Pérez',  '23456789003', '4 años en electricidad residencial',                       '7734567890', 'marco.luna@indecsa.com',        'Electricidad',          'ACTIVO',    'Instalación de tableros, contactos y luminarias',             3, '2021-09-30', 'Hidalgo'),
('Óscar Ramírez Montes',      '23456789004', '8 años en impermeabilización de azoteas',                  '7745678901', 'oscar.ramirez@indecsa.com',     'Impermeabilización',    'VACACIONES','Aplicación de sistemas Impertek, poliuretano y fibra de vidrio',4,'2017-02-07','Hidalgo'),
('Raúl Espinoza Contreras',   '23456789005', '5 años en topografía y trazo de obra',                     '7756789012', 'raul.espinoza@indecsa.com',     'Topografía',            'ACTIVO',    'Manejo de nivel, teodolito y estación total',                 4, '2020-10-19', 'Hidalgo'),
('Ismael Vargas Aguirre',     '23456789006', '3 años en colocación de pisos y azulejos',                 '7767890123', 'ismael.vargas@indecsa.com',     'Acabados',              'ACTIVO',    'Colocación de porcelanato, mosaico y azulejo',                3, '2022-05-25', 'Hidalgo'),
('Gerardo Medina Sánchez',    '23456789007', '12 años en obra civil de carreteras',                      '7778901234', 'gerardo.medina@indecsa.com',    'Obra Civil',            'ACTIVO',    'Experiencia en pavimentación, terracerías y puentes',         5, '2013-01-09', 'Hidalgo'),
('Arturo Domínguez Flores',   '23456789008', '7 años en herrería y cancelería',                          '7789012345', 'arturo.dominguez@indecsa.com',  'Herrería',              'BAJA',      'Fabricación e instalación de puertas, rejas y ventanas',      4, '2018-06-30', 'Hidalgo'),
('Salvador Ángeles Olvera',   '23456789009', '2 años en pintura de exteriores',                          '7790123456', 'salvador.angeles@indecsa.com',  'Pintura y Acabados',    'ACTIVO',    'Aplicación de pinturas para fachadas y protección anticorrosiva',3,'2023-03-15','Hidalgo'),
('Benito Cervantes Juárez',   '23456789010', '15 años en cimentaciones profundas',                       '7701234567', 'benito.cervantes@indecsa.com',  'Albañilería',           'INACTIVO',  'Pilotes, pilas y losas de cimentación en suelos blandos',     5, '2010-07-04', 'Hidalgo'),

-- Puebla
('Ricardo Gutiérrez Blanco',  '34567890001', '6 años en instalaciones de gas LP y natural',              '2212345678', 'ricardo.gutierrez@indecsa.com', 'Gas e Instalaciones',   'ACTIVO',    'Certificado en instalaciones de gas residencial e industrial', 4, '2019-08-11', 'Puebla'),
('Víctor Hugo Zamora Ríos',   '34567890002', '9 años en estructuras de concreto armado',                 '2223456789', 'victor.zamora@indecsa.com',     'Estructuras Concreto',  'VACACIONES','Colado de losas, trabes y columnas de concreto armado',       5, '2016-04-23', 'Puebla'),
('Alfonso Núñez Pacheco',     '34567890003', '4 años en electricidad industrial',                        '2234567890', 'alfonso.nunez@indecsa.com',     'Electricidad',          'ACTIVO',    'Instalación de motores, variadores y tableros industriales',  4, '2021-11-02', 'Puebla'),
('Eduardo Carrillo Vega',     '34567890004', '3 años en refrigeración y aire acondicionado',             '2245678901', 'eduardo.carrillo@indecsa.com',  'HVAC',                  'ACTIVO',    'Instalación y mantenimiento de equipos de climatización',     3, '2022-08-17', 'Puebla'),
('Pablo Moreno Aguilar',      '34567890005', '7 años en pintura industrial y anticorrosiva',             '2256789012', 'pablo.moreno@indecsa.com',      'Pintura y Acabados',    'BAJA',      'Aplicación de recubrimientos epóxicos en plantas y naves',    4, '2018-01-29', 'Puebla'),
('Tomás Guerrero Mendoza',    '34567890006', '5 años en obra negra y levantamiento de muros',            '2267890123', 'tomas.guerrero@indecsa.com',    'Albañilería',           'ACTIVO',    'Levantamiento de muros de tabicón, block y tabique rojo',     3, '2020-07-08', 'Puebla'),
('Rubén Estrada Peña',        '34567890007', '10 años en plomería industrial',                           '2278901234', 'ruben.estrada@indecsa.com',     'Plomería',              'ACTIVO',    'Instalación de tuberías de acero, cobre y CPVC en plantas',   5, '2015-03-16', 'Puebla'),
('Gilberto Acosta Velázquez', '34567890008', '2 años en ayudantía de electricidad',                      '2289012345', 'gilberto.acosta@indecsa.com',   'Electricidad',          'INACTIVO',  'Apoyo en cableado, canalización y conexionado de circuitos',  2, '2024-02-20', 'Puebla'),
('Ignacio Bustamante Trejo',  '34567890009', '8 años en supervisión de instalaciones MEP',               '2290123456', 'ignacio.bustamante@indecsa.com','Supervisión de Obra',   'ACTIVO',    'Coordinación de subcontratos mecánicos, eléctricos y plomería',5,'2017-09-01','Puebla'),
('Aurelio Fuentes Cárdenas',  '34567890010', '6 años en andamios y trabajos en altura',                  '2201234567', 'aurelio.fuentes@indecsa.com',   'Trabajos en Altura',    'VACACIONES','Armado de andamios tubulares y operación de plataformas',     4, '2019-12-03', 'Puebla');


-- ===================================
-- 5. PROYECTO
-- ===================================
INSERT INTO Proyecto (
    nombre_proyecto, tipo_proyecto, lugar_proyecto, municipio_proyecto,
    estado_proyecto_geo, fecha_estimada_inicio, fecha_estimada_fin,
    calificacion_proyecto, estatus_proyecto, descripcion_proyecto
) VALUES
-- CDMX
('Edificio Corporativo Torre Norte',        'Construcción',         'Av. Insurgentes Norte 1234',        'Gustavo A. Madero',    'CDMX',    '2024-01-15', '2025-06-30', 4, 'FINALIZADO',  'Torre de 12 niveles con sótano de estacionamiento y área comercial en planta baja'),
('Remodelación Centro Comercial Perisur',   'Remodelación',         'Periferico Sur 4690',               'Tlalpan',              'CDMX',    '2024-03-01', '2024-09-15', NULL,'EN_CURSO',   'Renovación de fachada, pisos y sistemas eléctricos de local comercial de 3200 m2'),
('Ampliación Planta Industrial Vallejo',    'Ampliación',           'Calz. Vallejo 890',                 'Azcapotzalco',         'CDMX',    '2024-06-10', '2025-03-20', 3, 'FINALIZADO',  'Construcción de nave industrial de 1800 m2 con oficinas administrativas anexas'),
('Conjunto Habitacional Los Álamos',        'Construcción',         'Av. Tláhuac 3300',                  'Tláhuac',              'CDMX',    '2025-01-10', '2026-08-30', NULL,'PAUSADO',    'Desarrollo de 48 viviendas de interés social en 4 bloques de 12 unidades'),
('Rehabilitación Mercado Tepito',           'Rehabilitación',       'Calle Tenochtitlan 40',             'Cuauhtémoc',           'CDMX',    '2025-03-01', '2025-11-30', NULL,'EN_CURSO',   'Reforzamiento estructural, impermeabilización y modernización de instalaciones'),
('Centro de Salud Iztapalapa Norte',        'Construcción',         'Av. Ermita Iztapalapa 820',         'Iztapalapa',           'CDMX',    '2025-05-15', '2026-04-30', 5, 'FINALIZADO',  'Clínica de primer nivel con consultorios, farmacia y área de urgencias menores'),
('Puente Vehicular Eje 8 Sur',              'Infraestructura',      'Eje 8 Sur esq. Calz. de La Viga',  'Iztacalco',            'CDMX',    '2025-07-01', '2026-05-15', NULL,'PLANEACION', 'Paso vehicular elevado de 80 metros lineales sobre canal de aguas negras'),
('Oficinas Administrativas CDMX-Centro',   'Remodelación',         'Av. Juárez 100',                    'Cuauhtémoc',           'CDMX',    '2025-09-01', '2026-02-28', NULL,'EN_CURSO',   'Remodelación de 4 pisos de oficinas gubernamentales, proyecto suspendido por presupuesto'),
('Estacionamiento Subterráneo Polanco',     'Construcción',         'Av. Presidente Masaryk 360',        'Miguel Hidalgo',       'CDMX',    '2026-02-01', '2027-01-31', 2, 'CANCELADO',   'Estacionamiento de 3 niveles subterráneos con capacidad para 420 vehículos'),
('Colegio de Educación Básica Xochimilco',  'Construcción',         'Prolongación División del Norte',   'Xochimilco',           'CDMX',    '2026-04-01', '2027-03-15', NULL,'PLANEACION', 'Plantel de educación primaria con 18 aulas, biblioteca, cancha y comedor'),

-- Hidalgo
('Modernización Planta Cementera Tula',     'Modernización',        'Carretera Tula-Tepeji Km 4',        'Tula de Allende',      'Hidalgo', '2023-08-01', '2024-07-31', 4, 'FINALIZADO',  'Actualización de línea de producción, subestación eléctrica y sistemas de control'),
('Carretera Alimentadora Actopan-Ixmiquilpan','Infraestructura',    'Tramo carretero Actopan norte',     'Actopan',              'Hidalgo', '2024-02-15', '2025-01-20', NULL,'EN_CURSO',   'Construcción de 12 km de carretera de dos carriles con obras de drenaje'),
('Bodega Logística Sahagún',                'Construcción',         'Parque Industrial Ciudad Sahagún',  'Tepeapulco',           'Hidalgo', '2024-09-01', '2025-08-15', 3, 'FINALIZADO',  'Nave de almacenamiento de 5000 m2 con andenes de carga y oficinas de control'),
('Hospital General Pachuca Expansion',      'Ampliación',           'Av. Juárez 1200',                   'Pachuca de Soto',      'Hidalgo', '2025-02-01', '2026-09-30', NULL,'PAUSADO',    'Nuevo edificio de 6 niveles con 80 camas, quirófanos y unidad de cuidados intensivos'),
('Planta Tratamiento Aguas Tulancingo',     'Infraestructura',      'Periférico Tulancingo Oriente',     'Tulancingo',           'Hidalgo', '2025-04-10', '2026-07-20', NULL,'EN_CURSO',   'PTAR con capacidad de 150 l/s para tratamiento de aguas residuales municipales'),
('Fraccionamiento Residencial Valle Verde', 'Construcción',         'Blvd. Felipe Ángeles 450',          'Pachuca de Soto',      'Hidalgo', '2025-06-01', '2027-05-31', NULL,'PLANEACION', 'Desarrollo residencial de 120 casas en 3 etapas con áreas verdes y club house'),
('Rehabilitación Zona Arqueológica Tula',   'Rehabilitación',       'Zona Arqueológica Tula',            'Tula de Allende',      'Hidalgo', '2025-08-01', '2026-03-31', NULL,'EN_CURSO',   'Trabajos de consolidación estructural y restauración de pirámide B, proyecto pausado'),
('Subestación Eléctrica Ixmiquilpan',       'Infraestructura',      'Carretera México-Laredo Km 102',    'Ixmiquilpan',          'Hidalgo', '2025-10-01', '2026-08-15', 5, 'FINALIZADO',  'Subestación de 115/23 kV con capacidad de 40 MVA para zona norte de Hidalgo'),
('Centro Deportivo Municipal Huejutla',     'Construcción',         'Av. Benito Juárez s/n',             'Huejutla de Reyes',    'Hidalgo', '2026-01-15', '2027-06-30', NULL,'EN_CURSO',   'Complejo deportivo con alberca olímpica, canchas techadas y gimnasio municipal'),
('Distribuidor Vial Tepeji',                'Infraestructura',      'Autopista México-Querétaro Km 58',  'Tepeji del Río',       'Hidalgo', '2026-03-01', '2027-04-30', NULL,'PLANEACION', 'Distribuidores a desnivel para descongestionamiento vial en acceso a ciudad'),

-- Puebla
('Torre Residencial Angelópolis',           'Construcción',         'Blvd. Atlixcáyotl 2901',            'San Andrés Cholula',   'Puebla',  '2023-06-01', '2025-02-28', 5, 'FINALIZADO',  'Torre de lujo de 20 niveles con 80 departamentos, amenidades y 3 sótanos de estacionamiento'),
('Planta Automotriz Expansión Huejotzingo', 'Ampliación',           'Parque Industrial Huejotzingo',     'Huejotzingo',          'Puebla',  '2024-01-10', '2025-04-30', NULL,'PAUSADO',    'Ampliación de 8000 m2 de planta de ensamble con área de pintura y bodega de partes'),
('Campus Universitario BUAP Sur',           'Construcción',         'Carretera Federal Puebla-Atlixco',  'Atlixco',              'Puebla',  '2024-07-01', '2026-06-30', NULL,'EN_CURSO',   'Nuevo campus con 10 edificios de aulas, biblioteca central, laboratorios y áreas deportivas'),
('Libramiento Autopista Tehuacán',          'Infraestructura',      'Carretera Puebla-Oaxaca Km 130',    'Tehuacán',             'Puebla',  '2025-01-20', '2026-10-31', 3, 'FINALIZADO',  'Libramiento de 18 km que descongestiona el centro de Tehuacán con 2 puentes mayores'),
('Centro Logístico San Martín Texmelucan',  'Construcción',         'Carretera Puebla-México Km 34',     'San Martín Texmelucan','Puebla',  '2025-03-15', '2026-05-20', NULL,'EN_CURSO',   'Parque logístico de 3 naves industriales de 3000 m2 cada una con área de maniobras'),
('Rehabilitación Zona Histórica Cholula',   'Rehabilitación',       'Portal Guerrero s/n',               'San Pedro Cholula',    'Puebla',  '2025-05-01', '2025-12-15', NULL,'PLANEACION', 'Restauración de portales, pavimentos y fachadas del centro histórico de Cholula'),
('Planta Fotovoltaica Tepeaca',             'Infraestructura',      'Carretera Tepeaca-Tochtepec Km 6',  'Tepeaca',              'Puebla',  '2025-07-10', '2026-09-30', 2, 'CANCELADO',   'Parque solar de 15 MW con 42,000 paneles fotovoltaicos y subestación de interconexión'),
('Hotel Boutique Centro Histórico Puebla',  'Construcción',         'Calle 5 de Mayo 200',               'Puebla de Zaragoza',   'Puebla',  '2025-09-01', '2026-11-30', NULL,'EN_CURSO',   'Hotel de 45 habitaciones en edificio patrimonial rehabilitado, pausado por permisos INAH'),
('Planta Procesadora Alimentos Izúcar',     'Construcción',         'Parque Industrial Izúcar',          'Izúcar de Matamoros',  'Puebla',  '2026-01-10', '2027-03-31', NULL,'PLANEACION', 'Planta de procesamiento de agroproductos con cámara fría, área de empaque y laboratorio'),
('Viaducto Elevado Puebla-Amozoc',          'Infraestructura',      'Blvd. Atlixcáyotl - Amozoc',        'Puebla de Zaragoza',   'Puebla',  '2026-05-01', '2028-04-30', NULL,'EN_CURSO',   'Viaducto elevado de 6 km con 4 carriles para conectar oriente de la ciudad con autopista');


-- ===================================
-- 6. ASIGNACION PROYECTO - CONTRATISTA
-- ===================================
INSERT INTO Asignacion_Proyecto_Contratista
(id_proyecto, id_contratista, numero_contrato,
 fecha_inicio, fecha_fin_estimada,
 monto_contratado, estatus_contrato, observaciones)
VALUES
    ((SELECT id_proyecto    FROM Proyecto    WHERE nombre_proyecto = 'Ampliacion Carretera Federal 57'),
     (SELECT id_contratista FROM Contratista WHERE rfc_contratista = 'CONSA800101AA1'),
     'CONT-2026-001', '2026-02-01', '2026-11-30',
     4500000.00, 'ACTIVO',
     'Contrato principal de obra civil para ampliacion vial'),

    ((SELECT id_proyecto    FROM Proyecto    WHERE nombre_proyecto = 'Complejo Habitacional Las Palmas'),
     (SELECT id_contratista FROM Contratista WHERE rfc_contratista = 'EDIFE900215BB2'),
     'CONT-2026-002', '2026-04-01', '2027-03-31',
     9800000.00, 'ACTIVO',
     'Contrato de edificacion para el complejo residencial'),

    ((SELECT id_proyecto    FROM Proyecto    WHERE nombre_proyecto = 'Planta Industrial Zona Norte'),
     (SELECT id_contratista FROM Contratista WHERE rfc_contratista = 'CONSA800101AA1'),
     'CONT-2025-010', '2025-06-01', '2026-01-15',
     12000000.00, 'FINALIZADO',
     'Contrato concluido satisfactoriamente');


-- ===================================
-- 7. ASIGNACION TRABAJADOR - PROYECTO
-- ===================================
INSERT INTO Asignacion_Trabajador_Proyecto
(id_trabajador, id_proyecto, id_asignacion_pc,
 rol_en_proyecto, fecha_inicio, fecha_fin_estimada,
 estatus_asignacion, observaciones)
VALUES
    ((SELECT id_trabajador    FROM Trabajador WHERE correo_trabajador = 'jorge.perez@trabajador.com'),
     (SELECT id_proyecto      FROM Proyecto   WHERE nombre_proyecto   = 'Ampliacion Carretera Federal 57'),
     (SELECT id_asignacion_pc FROM Asignacion_Proyecto_Contratista    WHERE numero_contrato = 'CONT-2026-001'),
     'Albanil de cimentacion', '2026-02-10', '2026-11-30',
     'ACTIVO', 'Asignado a cuadrilla de colado zona norte'),

    ((SELECT id_trabajador    FROM Trabajador WHERE correo_trabajador = 'maria.torres@trabajador.com'),
     (SELECT id_proyecto      FROM Proyecto   WHERE nombre_proyecto   = 'Ampliacion Carretera Federal 57'),
     (SELECT id_asignacion_pc FROM Asignacion_Proyecto_Contratista    WHERE numero_contrato = 'CONT-2026-001'),
     'Electricista de campo', '2026-03-01', '2026-11-30',
     'ACTIVO', 'Responsable de alumbrado publico del tramo'),

    ((SELECT id_trabajador    FROM Trabajador WHERE correo_trabajador = 'roberto.salas@trabajador.com'),
     (SELECT id_proyecto      FROM Proyecto   WHERE nombre_proyecto   = 'Complejo Habitacional Las Palmas'),
     (SELECT id_asignacion_pc FROM Asignacion_Proyecto_Contratista    WHERE numero_contrato = 'CONT-2026-002'),
     'Plomero jefe de instalaciones', '2026-04-05', '2027-03-31',
     'ACTIVO', 'Lider de la red hidraulica del complejo');