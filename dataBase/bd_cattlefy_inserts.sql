-- ============================================
-- DATOS DE PRUEBA ROBUSTOS Y COMPLETOS
-- Sistema de Gestión Ganadera
-- Con registros distribuidos en el tiempo (últimos 12 meses)
-- ============================================

-- ============================================
-- 1. ROLES
-- ============================================
INSERT INTO tb_rol (descripcion) VALUES
('Administrador'),
('Productor'),
('Veterinario');

-- ============================================
-- 2. USUARIOS (2 usuarios - relación 1:1 con granjas)
-- ============================================
INSERT INTO tb_usuarios (nombres, ape_pat, ape_mat, documento, email, contra, telefono, firebase_uid, imagen_url, rol_id, activo) VALUES
('Carlos', 'Mendoza', 'Torres', '45678901', 'carlos.mendoza@email.com', '$2a$10$hashed_password_1', '987654321', 'firebase_uid_001', 'https://example.com/img/carlos.jpg', 2, TRUE),
('Maria', 'Gonzalez', 'Ruiz', '45678902', 'maria.gonzalez@email.com', '$2a$10$hashed_password_2', '987654322', 'firebase_uid_002', 'https://example.com/img/maria.jpg', 2, TRUE);

-- ============================================
-- 3. GRANJAS (2 granjas - una por usuario)
-- ============================================
INSERT INTO tb_granjas (usuario_id, nombre, direccion, latitud, longitud, imagen_url) VALUES
(1, 'Granja San Jose', 'Km 25 Carretera Central, Huarochiri', -11.9876543, -76.8765432, 'https://example.com/granja1.jpg'),
(2, 'Granja El Paraiso', 'Av. Los Pinos 456, Canete', -12.1234567, -76.5432109, 'https://example.com/granja2.jpg');

-- ============================================
-- 4. ESPECIES (Exactamente 5 especies)
-- ============================================
INSERT INTO tb_especies (nombre) VALUES
('Vacuno'),
('Porcino'),
('Caprino'),
('Pavino'),
('Avicola');

-- ============================================
-- 5. CATEGORÍAS DE MANEJO
-- Relacionadas con etapas de vida y tipo de lote
-- Tipos de lote: Engorde, Reproduccion, Descarte, Enfermeria
-- ============================================
INSERT INTO tb_categorias_manejo (especie_id, nombre, tipo_lote, dieta_recomendada) VALUES
-- VACUNO
(1, 'Terneros Lactantes', 'Enfermeria', 'Leche maternizada + concentrado inicio'),
(1, 'Terneros Destetados', 'Engorde', 'Concentrado 18% proteina + forraje'),
(1, 'Novillos Engorde', 'Engorde', 'Forraje + concentrado 16% proteina'),
(1, 'Vacas Lecheras', 'Reproduccion', 'Forraje verde + concentrado lechero'),
(1, 'Toros Reproductores', 'Reproduccion', 'Forraje + concentrado reproductor'),
(1, 'Ganado Descarte', 'Descarte', 'Forraje basico'),

-- PORCINO
(2, 'Lechones Lactantes', 'Enfermeria', 'Leche materna + pre-iniciador'),
(2, 'Lechones Destetados', 'Engorde', 'Alimento iniciador 20% proteina'),
(2, 'Cerdos Engorde', 'Engorde', 'Balanceado 16% proteina'),
(2, 'Cerdas Reproductoras', 'Reproduccion', 'Alimento gestacion/lactancia'),
(2, 'Verracos', 'Reproduccion', 'Alimento reproductor'),
(2, 'Cerdos Descarte', 'Descarte', 'Balanceado basico'),

-- CAPRINO
(3, 'Cabritos Lactantes', 'Enfermeria', 'Leche materna + forraje tierno'),
(3, 'Cabritos Destetados', 'Engorde', 'Forraje + concentrado 16%'),
(3, 'Caprinos Engorde', 'Engorde', 'Forraje mixto + concentrado'),
(3, 'Cabras Lecheras', 'Reproduccion', 'Forraje + sales minerales'),
(3, 'Machos Reproductores', 'Reproduccion', 'Forraje + suplemento'),
(3, 'Caprinos Descarte', 'Descarte', 'Forraje basico'),

-- PAVINO
(4, 'Pavitos BB', 'Enfermeria', 'Alimento iniciador 28% proteina'),
(4, 'Pavos Crecimiento', 'Engorde', 'Alimento crecimiento 22% proteina'),
(4, 'Pavos Engorde', 'Engorde', 'Alimento finalizador 18% proteina'),
(4, 'Pavas Reproductoras', 'Reproduccion', 'Alimento reproductor'),
(4, 'Pavos Reproductores', 'Reproduccion', 'Alimento reproductor macho'),
(4, 'Pavos Descarte', 'Descarte', 'Alimento basico'),

-- AVICOLA
(5, 'Pollitos BB', 'Enfermeria', 'Alimento iniciador 21% proteina'),
(5, 'Pollos Crecimiento', 'Engorde', 'Alimento crecimiento 19% proteina'),
(5, 'Pollos Engorde', 'Engorde', 'Alimento finalizador 18% proteina'),
(5, 'Gallinas Ponedoras', 'Reproduccion', 'Alimento postura 17% proteina'),
(5, 'Gallos Reproductores', 'Reproduccion', 'Alimento reproductor'),
(5, 'Aves Descarte', 'Descarte', 'Alimento basico');

-- ============================================
-- 6. LOTES (16 lotes distribuidos)
-- Formato QR: QR_LOTE_XXX
-- ============================================
INSERT INTO tb_lotes (lote_qr, granja_id, nombre, especie_id, categoria_id, fecha_creacion, estado, capacidad_max) VALUES
-- Granja San Jose (1) - 8 lotes
('QR_LOTE_001', 1, 'Lote Vacuno Engorde A1', 1, 3, '2024-01-15 08:00:00', 'Activo', 40),
('QR_LOTE_002', 1, 'Lote Vacas Lecheras B1', 1, 4, '2024-02-01 09:00:00', 'Activo', 25),
('QR_LOTE_003', 1, 'Lote Terneros T1', 1, 1, '2024-05-20 08:00:00', 'Activo', 20),
('QR_LOTE_004', 1, 'Lote Porcino Engorde P1', 2, 9, '2024-03-10 07:30:00', 'Activo', 80),
('QR_LOTE_005', 1, 'Lote Lechones L1', 2, 7, '2024-06-01 08:00:00', 'Activo', 50),
('QR_LOTE_006', 1, 'Lote Caprino Engorde C1', 3, 15, '2024-02-20 09:00:00', 'Activo', 50),
('QR_LOTE_007', 1, 'Lote Cabras Lecheras CL1', 3, 16, '2024-01-10 09:00:00', 'Activo', 30),
('QR_LOTE_008', 1, 'Lote Pavos Engorde PV1', 4, 21, '2024-04-15 10:00:00', 'Activo', 200),

-- Granja El Paraiso (2) - 8 lotes
('QR_LOTE_009', 2, 'Lote Pavitos BB1', 4, 19, '2024-06-10 08:00:00', 'Activo', 150),
('QR_LOTE_010', 2, 'Lote Pavas Reproductoras PR1', 4, 22, '2024-02-15 09:00:00', 'Activo', 40),
('QR_LOTE_011', 2, 'Lote Pollos Engorde PE1', 5, 27, '2024-05-01 06:00:00', 'Cerrado', 400),
('QR_LOTE_012', 2, 'Lote Ponedoras PON1', 5, 28, '2024-03-15 07:00:00', 'Activo', 250),
('QR_LOTE_013', 2, 'Lote Pollos Engorde PE2', 5, 27, '2024-06-15 06:00:00', 'Activo', 400),
('QR_LOTE_014', 2, 'Lote Vacuno Engorde V2', 1, 3, '2024-03-01 08:00:00', 'Activo', 35),
('QR_LOTE_015', 2, 'Lote Cerdas Reproductoras CR1', 2, 10, '2024-02-25 08:00:00', 'Activo', 20),
('QR_LOTE_016', 2, 'Lote Vacuno Descarte VD1', 1, 6, '2024-05-15 10:00:00', 'Cerrado', 12);

-- ============================================
-- 7. ANIMALES (180+ animales)
-- Formato QR: QR_XXX_YYY (XXX = primeras 3 letras de especie)
-- ============================================
INSERT INTO tb_animales (animal_qr, especie_id, lote_id, madre_id, origen, sexo, fecha_ingreso, fecha_nacimiento, peso, precio_compra, estado, foto_url) VALUES
-- Lote 1: Vacuno Engorde A1 (15 animales)
('QR_VAC_001', 1, 1, NULL, 'Compra', 'M', '2024-01-15', '2023-07-01', 280.50, 1500.00, 'Vivo', NULL),
('QR_VAC_002', 1, 1, NULL, 'Compra', 'M', '2024-01-15', '2023-07-05', 275.00, 1480.00, 'Vivo', NULL),
('QR_VAC_003', 1, 1, NULL, 'Compra', 'M', '2024-01-15', '2023-07-10', 285.00, 1520.00, 'Vivo', NULL),
('QR_VAC_004', 1, 1, NULL, 'Compra', 'M', '2024-01-15', '2023-07-15', 290.00, 1550.00, 'Vendido', NULL),
('QR_VAC_005', 1, 1, NULL, 'Compra', 'M', '2024-01-15', '2023-07-20', 278.00, 1490.00, 'Vivo', NULL),
('QR_VAC_006', 1, 1, NULL, 'Compra', 'M', '2024-01-15', '2023-07-25', 282.00, 1510.00, 'Vivo', NULL),
('QR_VAC_007', 1, 1, NULL, 'Compra', 'M', '2024-01-15', '2023-08-01', 288.00, 1540.00, 'Vivo', NULL),
('QR_VAC_008', 1, 1, NULL, 'Compra', 'M', '2024-01-15', '2023-08-05', 276.00, 1485.00, 'Vivo', NULL),
('QR_VAC_009', 1, 1, NULL, 'Compra', 'M', '2024-01-15', '2023-08-10', 283.00, 1515.00, 'Vivo', NULL),
('QR_VAC_010', 1, 1, NULL, 'Compra', 'M', '2024-01-15', '2023-08-15', 279.00, 1495.00, 'Vivo', NULL),
('QR_VAC_011', 1, 1, NULL, 'Compra', 'M', '2024-01-15', '2023-08-20', 287.00, 1535.00, 'Vivo', NULL),
('QR_VAC_012', 1, 1, NULL, 'Compra', 'M', '2024-01-15', '2023-08-25', 281.00, 1505.00, 'Vivo', NULL),
('QR_VAC_013', 1, 1, NULL, 'Compra', 'M', '2024-01-15', '2023-09-01', 286.00, 1530.00, 'Vivo', NULL),
('QR_VAC_014', 1, 1, NULL, 'Compra', 'M', '2024-01-15', '2023-09-05', 280.00, 1500.00, 'Vivo', NULL),
('QR_VAC_015', 1, 1, NULL, 'Compra', 'M', '2024-01-15', '2023-09-10', 284.00, 1520.00, 'Vivo', NULL),

-- Lote 2: Vacas Lecheras B1 (10 animales)
('QR_VAC_016', 1, 2, NULL, 'Compra', 'H', '2024-02-01', '2021-03-15', 520.00, 3500.00, 'Vivo', NULL),
('QR_VAC_017', 1, 2, NULL, 'Compra', 'H', '2024-02-01', '2021-05-20', 510.00, 3400.00, 'Vivo', NULL),
('QR_VAC_018', 1, 2, NULL, 'Compra', 'H', '2024-02-01', '2021-04-10', 515.00, 3450.00, 'Vivo', NULL),
('QR_VAC_019', 1, 2, NULL, 'Compra', 'H', '2024-02-01', '2021-06-05', 505.00, 3350.00, 'Vivo', NULL),
('QR_VAC_020', 1, 2, NULL, 'Compra', 'H', '2024-02-01', '2021-07-15', 518.00, 3480.00, 'Vivo', NULL),
('QR_VAC_021', 1, 2, NULL, 'Compra', 'H', '2024-02-01', '2021-08-20', 512.00, 3420.00, 'Vivo', NULL),
('QR_VAC_022', 1, 2, NULL, 'Compra', 'H', '2024-02-01', '2021-09-10', 508.00, 3380.00, 'Vivo', NULL),
('QR_VAC_023', 1, 2, NULL, 'Compra', 'H', '2024-02-01', '2021-10-15', 522.00, 3520.00, 'Vivo', NULL),
('QR_VAC_024', 1, 2, 16, 'Nacimiento', 'H', '2024-05-10', '2024-05-10', 38.00, 0.00, 'Vivo', NULL),
('QR_VAC_025', 1, 2, 17, 'Nacimiento', 'M', '2024-06-15', '2024-06-15', 40.00, 0.00, 'Vivo', NULL),

-- Lote 3: Terneros T1 (8 animales)
('QR_VAC_026', 1, 3, NULL, 'Compra', 'M', '2024-05-20', '2024-03-10', 85.00, 450.00, 'Vivo', NULL),
('QR_VAC_027', 1, 3, NULL, 'Compra', 'H', '2024-05-20', '2024-03-12', 82.00, 440.00, 'Vivo', NULL),
('QR_VAC_028', 1, 3, NULL, 'Compra', 'M', '2024-05-20', '2024-03-15', 88.00, 460.00, 'Vivo', NULL),
('QR_VAC_029', 1, 3, NULL, 'Compra', 'H', '2024-05-20', '2024-03-18', 84.00, 445.00, 'Vivo', NULL),
('QR_VAC_030', 1, 3, NULL, 'Compra', 'M', '2024-05-20', '2024-03-20', 86.00, 455.00, 'Vivo', NULL),
('QR_VAC_031', 1, 3, NULL, 'Compra', 'M', '2024-05-20', '2024-03-22', 87.00, 458.00, 'Vivo', NULL),
('QR_VAC_032', 1, 3, NULL, 'Compra', 'H', '2024-05-20', '2024-03-25', 83.00, 442.00, 'Vivo', NULL),
('QR_VAC_033', 1, 3, NULL, 'Compra', 'M', '2024-05-20', '2024-03-28', 86.50, 456.00, 'Vivo', NULL),

-- Lote 4: Porcino Engorde P1 (20 animales)
('QR_POR_001', 2, 4, NULL, 'Compra', 'M', '2024-03-10', '2024-01-15', 45.00, 180.00, 'Vivo', NULL),
('QR_POR_002', 2, 4, NULL, 'Compra', 'H', '2024-03-10', '2024-01-15', 42.00, 170.00, 'Vivo', NULL),
('QR_POR_003', 2, 4, NULL, 'Compra', 'M', '2024-03-10', '2024-01-20', 44.00, 175.00, 'Vivo', NULL),
('QR_POR_004', 2, 4, NULL, 'Compra', 'M', '2024-03-10', '2024-01-18', 43.00, 172.00, 'Vivo', NULL),
('QR_POR_005', 2, 4, NULL, 'Compra', 'H', '2024-03-10', '2024-01-22', 46.00, 182.00, 'Muerto', NULL),
('QR_POR_006', 2, 4, NULL, 'Compra', 'M', '2024-03-10', '2024-01-25', 44.50, 176.00, 'Vivo', NULL),
('QR_POR_007', 2, 4, NULL, 'Compra', 'H', '2024-03-10', '2024-01-28', 43.50, 173.00, 'Vivo', NULL),
('QR_POR_008', 2, 4, NULL, 'Compra', 'M', '2024-03-10', '2024-02-01', 45.50, 181.00, 'Vivo', NULL),
('QR_POR_009', 2, 4, NULL, 'Compra', 'M', '2024-03-10', '2024-02-03', 44.00, 175.00, 'Vivo', NULL),
('QR_POR_010', 2, 4, NULL, 'Compra', 'H', '2024-03-10', '2024-02-05', 42.50, 171.00, 'Vivo', NULL),
('QR_POR_011', 2, 4, NULL, 'Compra', 'M', '2024-03-10', '2024-02-08', 46.50, 183.00, 'Vivo', NULL),
('QR_POR_012', 2, 4, NULL, 'Compra', 'H', '2024-03-10', '2024-02-10', 43.00, 172.00, 'Vivo', NULL),
('QR_POR_013', 2, 4, NULL, 'Compra', 'M', '2024-03-10', '2024-02-12', 45.00, 180.00, 'Vivo', NULL),
('QR_POR_014', 2, 4, NULL, 'Compra', 'M', '2024-03-10', '2024-02-15', 44.50, 176.00, 'Vivo', NULL),
('QR_POR_015', 2, 4, NULL, 'Compra', 'H', '2024-03-10', '2024-02-18', 42.00, 170.00, 'Vivo', NULL),
('QR_POR_016', 2, 4, NULL, 'Compra', 'M', '2024-03-10', '2024-02-20', 45.50, 181.00, 'Vivo', NULL),
('QR_POR_017', 2, 4, NULL, 'Compra', 'H', '2024-03-10', '2024-02-22', 43.50, 173.00, 'Vivo', NULL),
('QR_POR_018', 2, 4, NULL, 'Compra', 'M', '2024-03-10', '2024-02-25', 44.00, 175.00, 'Vivo', NULL),
('QR_POR_019', 2, 4, NULL, 'Compra', 'M', '2024-03-10', '2024-02-28', 46.00, 182.00, 'Vivo', NULL),
('QR_POR_020', 2, 4, NULL, 'Compra', 'H', '2024-03-10', '2024-03-01', 42.50, 171.00, 'Vivo', NULL),

-- Lote 5: Lechones L1 (12 animales)
('QR_POR_021', 2, 5, NULL, 'Compra', 'M', '2024-06-01', '2024-04-20', 28.00, 120.00, 'Vivo', NULL),
('QR_POR_022', 2, 5, NULL, 'Compra', 'H', '2024-06-01', '2024-04-20', 26.00, 115.00, 'Vivo', NULL),
('QR_POR_023', 2, 5, NULL, 'Compra', 'M', '2024-06-01', '2024-04-22', 27.50, 118.00, 'Vivo', NULL),
('QR_POR_024', 2, 5, NULL, 'Compra', 'H', '2024-06-01', '2024-04-22', 26.50, 116.00, 'Vivo', NULL),
('QR_POR_025', 2, 5, NULL, 'Compra', 'M', '2024-06-01', '2024-04-25', 28.50, 122.00, 'Vivo', NULL),
('QR_POR_026', 2, 5, NULL, 'Compra', 'M', '2024-06-01', '2024-04-25', 27.00, 117.00, 'Vivo', NULL),
('QR_POR_027', 2, 5, NULL, 'Compra', 'H', '2024-06-01', '2024-04-28', 26.00, 115.00, 'Vivo', NULL),
('QR_POR_028', 2, 5, NULL, 'Compra', 'M', '2024-06-01', '2024-04-28', 28.00, 120.00, 'Vivo', NULL),
('QR_POR_029', 2, 5, NULL, 'Compra', 'H', '2024-06-01', '2024-05-01', 27.50, 118.00, 'Vivo', NULL),
('QR_POR_030', 2, 5, NULL, 'Compra', 'M', '2024-06-01', '2024-05-01', 28.50, 122.00, 'Vivo', NULL),
('QR_POR_031', 2, 5, NULL, 'Compra', 'H', '2024-06-01', '2024-05-03', 26.50, 116.00, 'Vivo', NULL),
('QR_POR_032', 2, 5, NULL, 'Compra', 'M', '2024-06-01', '2024-05-03', 27.50, 118.00, 'Vivo', NULL),

-- Lote 6: Caprino Engorde C1 (15 animales)
('QR_CAP_001', 3, 6, NULL, 'Compra', 'H', '2024-02-20', '2023-06-10', 38.00, 250.00, 'Vivo', NULL),
('QR_CAP_002', 3, 6, NULL, 'Compra', 'M', '2024-02-20', '2023-06-15', 40.00, 260.00, 'Vivo', NULL),
('QR_CAP_003', 3, 6, NULL, 'Compra', 'H', '2024-02-20', '2023-06-20', 39.00, 255.00, 'Vivo', NULL),
('QR_CAP_004', 3, 6, NULL, 'Compra', 'M', '2024-02-20', '2023-06-25', 41.00, 265.00, 'Vivo', NULL),
('QR_CAP_005', 3, 6, NULL, 'Compra', 'H', '2024-02-20', '2023-07-01', 38.50, 252.00, 'Vivo', NULL),
('QR_CAP_006', 3, 6, NULL, 'Compra', 'M', '2024-02-20', '2023-07-05', 40.50, 262.00, 'Vivo', NULL),
('QR_CAP_007', 3, 6, NULL, 'Compra', 'H', '2024-02-20', '2023-07-10', 39.50, 257.00, 'Vivo', NULL),
('QR_CAP_008', 3, 6, NULL, 'Compra', 'M', '2024-02-20', '2023-07-15', 41.50, 267.00, 'Vivo', NULL),
('QR_CAP_009', 3, 6, NULL, 'Compra', 'H', '2024-02-20', '2023-07-20', 38.00, 250.00, 'Vivo', NULL),
('QR_CAP_010', 3, 6, NULL, 'Compra', 'M', '2024-02-20', '2023-07-25', 40.00, 260.00, 'Vivo', NULL),
('QR_CAP_011', 3, 6, NULL, 'Compra', 'H', '2024-02-20', '2023-08-01', 39.00, 255.00, 'Vivo', NULL),
('QR_CAP_012', 3, 6, NULL, 'Compra', 'M', '2024-02-20', '2023-08-05', 41.00, 265.00, 'Vivo', NULL),
('QR_CAP_013', 3, 6, NULL, 'Compra', 'H', '2024-02-20', '2023-08-10', 38.50, 252.00, 'Vivo', NULL),
('QR_CAP_014', 3, 6, NULL, 'Compra', 'M', '2024-02-20', '2023-08-15', 40.50, 262.00, 'Vivo', NULL),
('QR_CAP_015', 3, 6, NULL, 'Compra', 'H', '2024-02-20', '2023-08-20', 39.00, 255.00, 'Vivo', NULL),

-- Lote 7: Cabras Lecheras CL1 (10 animales)
('QR_CAP_016', 3, 7, NULL, 'Compra', 'H', '2024-01-10', '2022-03-10', 45.00, 350.00, 'Vivo', NULL),
('QR_CAP_017', 3, 7, NULL, 'Compra', 'H', '2024-01-10', '2022-04-15', 43.00, 340.00, 'Vivo', NULL),
('QR_CAP_018', 3, 7, NULL, 'Compra', 'H', '2024-01-10', '2022-05-20', 44.00, 345.00, 'Vivo', NULL),
('QR_CAP_019', 3, 7, NULL, 'Compra', 'H', '2024-01-10', '2022-06-10', 42.00, 335.00, 'Vivo', NULL),
('QR_CAP_020', 3, 7, NULL, 'Compra', 'H', '2024-01-10', '2022-07-15', 45.50, 352.00, 'Vivo', NULL),
('QR_CAP_021', 3, 7, NULL, 'Compra', 'H', '2024-01-10', '2022-08-20', 43.50, 342.00, 'Vivo', NULL),
('QR_CAP_022', 3, 7, NULL, 'Compra', 'H', '2024-01-10', '2022-09-10', 44.50, 346.00, 'Vivo', NULL),
('QR_CAP_023', 3, 7, NULL, 'Compra', 'H', '2024-01-10', '2022-10-15', 46.00, 355.00, 'Vivo', NULL),
('QR_CAP_024', 3, 7, 16, 'Nacimiento', 'H', '2024-05-15', '2024-05-15', 3.50, 0.00, 'Vivo', NULL),
('QR_CAP_025', 3, 7, 17, 'Nacimiento', 'M', '2024-06-10', '2024-06-10', 3.80, 0.00, 'Vivo', NULL),

-- Lote 8: Pavos Engorde PV1 (25 animales)
('QR_PAV_001', 4, 8, NULL, 'Compra', 'M', '2024-04-15', '2024-01-20', 8.50, 45.00, 'Vivo', NULL),
('QR_PAV_002', 4, 8, NULL, 'Compra', 'H', '2024-04-15', '2024-01-20', 7.80, 42.00, 'Vivo', NULL),
('QR_PAV_003', 4, 8, NULL, 'Compra', 'M', '2024-04-15', '2024-01-22', 8.20, 44.00, 'Vivo', NULL),
('QR_PAV_004', 4, 8, NULL, 'Compra', 'M', '2024-04-15', '2024-01-22', 8.60, 46.00, 'Vivo', NULL),
('QR_PAV_005', 4, 8, NULL, 'Compra', 'H', '2024-04-15', '2024-01-25', 7.90, 43.00, 'Vivo', NULL),
('QR_PAV_006', 4, 8, NULL, 'Compra', 'M', '2024-04-15', '2024-01-25', 8.40, 45.00, 'Vivo', NULL),
('QR_PAV_007', 4, 8, NULL, 'Compra', 'H', '2024-04-15', '2024-01-28', 8.00, 43.00, 'Vivo', NULL),
('QR_PAV_008', 4, 8, NULL, 'Compra', 'M', '2024-04-15', '2024-01-28', 8.70, 46.00, 'Vivo', NULL),
('QR_PAV_009', 4, 8, NULL, 'Compra', 'M', '2024-04-15', '2024-02-01', 8.30, 44.00, 'Vivo', NULL),
('QR_PAV_010', 4, 8, NULL, 'Compra', 'H', '2024-04-15', '2024-02-01', 7.70, 42.00, 'Vivo', NULL),
('QR_PAV_011', 4, 8, NULL, 'Compra', 'M', '2024-04-15', '2024-02-03', 8.50, 45.00, 'Vivo', NULL),
('QR_PAV_012', 4, 8, NULL, 'Compra', 'H', '2024-04-15', '2024-02-03', 8.10, 43.00, 'Vivo', NULL),
('QR_PAV_013', 4, 8, NULL, 'Compra', 'M', '2024-04-15', '2024-02-05', 8.60, 46.00, 'Vivo', NULL),
('QR_PAV_014', 4, 8, NULL, 'Compra', 'M', '2024-04-15', '2024-02-05', 8.40, 45.00, 'Vivo', NULL),
('QR_PAV_015', 4, 8, NULL, 'Compra', 'H', '2024-04-15', '2024-02-08', 7.90, 42.00, 'Vivo', NULL),
('QR_PAV_016', 4, 8, NULL, 'Compra', 'M', '2024-04-15', '2024-02-08', 8.20, 44.00, 'Vivo', NULL),
('QR_PAV_017', 4, 8, NULL, 'Compra', 'H', '2024-04-15', '2024-02-10', 8.00, 43.00, 'Vivo', NULL),
('QR_PAV_018', 4, 8, NULL, 'Compra', 'M', '2024-04-15', '2024-02-10', 8.50, 45.00, 'Vivo', NULL),
('QR_PAV_019', 4, 8, NULL, 'Compra', 'M', '2024-04-15', '2024-02-12', 8.70, 46.00, 'Vivo', NULL),
('QR_PAV_020', 4, 8, NULL, 'Compra', 'H', '2024-04-15', '2024-02-12', 7.80, 42.00, 'Vivo', NULL),
('QR_PAV_021', 4, 8, NULL, 'Compra', 'M', '2024-04-15', '2024-02-15', 8.30, 44.00, 'Vivo', NULL),
('QR_PAV_022', 4, 8, NULL, 'Compra', 'H', '2024-04-15', '2024-02-15', 8.10, 43.00, 'Vivo', NULL),
('QR_PAV_023', 4, 8, NULL, 'Compra', 'M', '2024-04-15', '2024-02-18', 8.60, 46.00, 'Vivo', NULL),
('QR_PAV_024', 4, 8, NULL, 'Compra', 'M', '2024-04-15', '2024-02-18', 8.40, 45.00, 'Vivo', NULL),
('QR_PAV_025', 4, 8, NULL, 'Compra', 'H', '2024-04-15', '2024-02-20', 7.90, 42.00, 'Vivo', NULL),

-- Lote 9: Pavitos BB1 (20 animales)
('QR_PAV_026', 4, 9, NULL, 'Compra', 'M', '2024-06-10', '2024-05-15', 1.20, 15.00, 'Vivo', NULL),
('QR_PAV_027', 4, 9, NULL, 'Compra', 'H', '2024-06-10', '2024-05-15', 1.10, 14.00, 'Vivo', NULL),
('QR_PAV_028', 4, 9, NULL, 'Compra', 'M', '2024-06-10', '2024-05-15', 1.25, 15.00, 'Vivo', NULL),
('QR_PAV_029', 4, 9, NULL, 'Compra', 'H', '2024-06-10', '2024-05-15', 1.15, 14.00, 'Vivo', NULL),
('QR_PAV_030', 4, 9, NULL, 'Compra', 'M', '2024-06-10', '2024-05-18', 1.22, 15.00, 'Vivo', NULL),
('QR_PAV_031', 4, 9, NULL, 'Compra', 'H', '2024-06-10', '2024-05-18', 1.12, 14.00, 'Vivo', NULL),
('QR_PAV_032', 4, 9, NULL, 'Compra', 'M', '2024-06-10', '2024-05-20', 1.28, 16.00, 'Vivo', NULL),
('QR_PAV_033', 4, 9, NULL, 'Compra', 'H', '2024-06-10', '2024-05-20', 1.18, 14.00, 'Vivo', NULL),
('QR_PAV_034', 4, 9, NULL, 'Compra', 'M', '2024-06-10', '2024-05-22', 1.24, 15.00, 'Vivo', NULL),
('QR_PAV_035', 4, 9, NULL, 'Compra', 'H', '2024-06-10', '2024-05-22', 1.14, 14.00, 'Vivo', NULL),
('QR_PAV_036', 4, 9, NULL, 'Compra', 'M', '2024-06-10', '2024-05-25', 1.26, 15.00, 'Vivo', NULL),
('QR_PAV_037', 4, 9, NULL, 'Compra', 'H', '2024-06-10', '2024-05-25', 1.16, 14.00, 'Vivo', NULL),
('QR_PAV_038', 4, 9, NULL, 'Compra', 'M', '2024-06-10', '2024-05-28', 1.30, 16.00, 'Vivo', NULL),
('QR_PAV_039', 4, 9, NULL, 'Compra', 'H', '2024-06-10', '2024-05-28', 1.20, 15.00, 'Vivo', NULL),
('QR_PAV_040', 4, 9, NULL, 'Compra', 'M', '2024-06-10', '2024-05-30', 1.27, 15.00, 'Vivo', NULL),
('QR_PAV_041', 4, 9, NULL, 'Compra', 'H', '2024-06-10', '2024-05-30', 1.17, 14.00, 'Vivo', NULL),
('QR_PAV_042', 4, 9, NULL, 'Compra', 'M', '2024-06-10', '2024-06-01', 1.25, 15.00, 'Vivo', NULL),
('QR_PAV_043', 4, 9, NULL, 'Compra', 'H', '2024-06-10', '2024-06-01', 1.15, 14.00, 'Vivo', NULL),
('QR_PAV_044', 4, 9, NULL, 'Compra', 'M', '2024-06-10', '2024-06-03', 1.29, 16.00, 'Vivo', NULL),
('QR_PAV_045', 4, 9, NULL, 'Compra', 'H', '2024-06-10', '2024-06-03', 1.19, 14.00, 'Vivo', NULL),

-- Lote 10: Pavas Reproductoras PR1 (12 animales)
('QR_PAV_046', 4, 10, NULL, 'Compra', 'H', '2024-02-15', '2022-08-10', 9.50, 80.00, 'Vivo', NULL),
('QR_PAV_047', 4, 10, NULL, 'Compra', 'H', '2024-02-15', '2022-09-15', 9.20, 78.00, 'Vivo', NULL),
('QR_PAV_048', 4, 10, NULL, 'Compra', 'H', '2024-02-15', '2022-10-20', 9.80, 82.00, 'Vivo', NULL),
('QR_PAV_049', 4, 10, NULL, 'Compra', 'H', '2024-02-15', '2022-11-10', 9.40, 79.00, 'Vivo', NULL),
('QR_PAV_050', 4, 10, NULL, 'Compra', 'H', '2024-02-15', '2022-12-15', 9.60, 80.00, 'Vivo', NULL),
('QR_PAV_051', 4, 10, NULL, 'Compra', 'H', '2024-02-15', '2023-01-20', 9.30, 78.00, 'Vivo', NULL),
('QR_PAV_052', 4, 10, NULL, 'Compra', 'H', '2024-02-15', '2023-02-10', 9.70, 81.00, 'Vivo', NULL),
('QR_PAV_053', 4, 10, NULL, 'Compra', 'H', '2024-02-15', '2023-03-15', 9.50, 80.00, 'Vivo', NULL),
('QR_PAV_054', 4, 10, NULL, 'Compra', 'H', '2024-02-15', '2023-04-20', 9.20, 78.00, 'Vivo', NULL),
('QR_PAV_055', 4, 10, NULL, 'Compra', 'H', '2024-02-15', '2023-05-10', 9.80, 82.00, 'Vivo', NULL),
('QR_PAV_056', 4, 10, 46, 'Nacimiento', 'H', '2024-06-01', '2024-06-01', 0.90, 0.00, 'Vivo', NULL),
('QR_PAV_057', 4, 10, 47, 'Nacimiento', 'M', '2024-06-05', '2024-06-05', 0.95, 0.00, 'Vivo', NULL),

-- Lote 11: Pollos Engorde PE1 - CERRADO (30 animales - todos vendidos)
('QR_AVI_001', 5, 11, NULL, 'Compra', 'M', '2024-05-01', '2024-03-15', 2.80, 8.00, 'Vendido', NULL),
('QR_AVI_002', 5, 11, NULL, 'Compra', 'H', '2024-05-01', '2024-03-15', 2.60, 7.50, 'Vendido', NULL),
('QR_AVI_003', 5, 11, NULL, 'Compra', 'M', '2024-05-01', '2024-03-15', 2.75, 8.00, 'Vendido', NULL),
('QR_AVI_004', 5, 11, NULL, 'Compra', 'H', '2024-05-01', '2024-03-15', 2.65, 7.50, 'Vendido', NULL),
('QR_AVI_005', 5, 11, NULL, 'Compra', 'M', '2024-05-01', '2024-03-15', 2.85, 8.00, 'Vendido', NULL),
('QR_AVI_006', 5, 11, NULL, 'Compra', 'H', '2024-05-01', '2024-03-15', 2.70, 7.50, 'Vendido', NULL),
('QR_AVI_007', 5, 11, NULL, 'Compra', 'M', '2024-05-01', '2024-03-15', 2.80, 8.00, 'Vendido', NULL),
('QR_AVI_008', 5, 11, NULL, 'Compra', 'H', '2024-05-01', '2024-03-15', 2.65, 7.50, 'Vendido', NULL),
('QR_AVI_009', 5, 11, NULL, 'Compra', 'M', '2024-05-01', '2024-03-15', 2.90, 8.00, 'Vendido', NULL),
('QR_AVI_010', 5, 11, NULL, 'Compra', 'H', '2024-05-01', '2024-03-15', 2.75, 7.50, 'Vendido', NULL),
('QR_AVI_011', 5, 11, NULL, 'Compra', 'M', '2024-05-01', '2024-03-15', 2.80, 8.00, 'Vendido', NULL),
('QR_AVI_012', 5, 11, NULL, 'Compra', 'H', '2024-05-01', '2024-03-15', 2.70, 7.50, 'Vendido', NULL),
('QR_AVI_013', 5, 11, NULL, 'Compra', 'M', '2024-05-01', '2024-03-15', 2.85, 8.00, 'Vendido', NULL),
('QR_AVI_014', 5, 11, NULL, 'Compra', 'H', '2024-05-01', '2024-03-15', 2.65, 7.50, 'Vendido', NULL),
('QR_AVI_015', 5, 11, NULL, 'Compra', 'M', '2024-05-01', '2024-03-15', 2.80, 8.00, 'Vendido', NULL),
('QR_AVI_016', 5, 11, NULL, 'Compra', 'H', '2024-05-01', '2024-03-15', 2.70, 7.50, 'Vendido', NULL),
('QR_AVI_017', 5, 11, NULL, 'Compra', 'M', '2024-05-01', '2024-03-15', 2.90, 8.00, 'Vendido', NULL),
('QR_AVI_018', 5, 11, NULL, 'Compra', 'H', '2024-05-01', '2024-03-15', 2.75, 7.50, 'Vendido', NULL),
('QR_AVI_019', 5, 11, NULL, 'Compra', 'M', '2024-05-01', '2024-03-15', 2.80, 8.00, 'Vendido', NULL),
('QR_AVI_020', 5, 11, NULL, 'Compra', 'H', '2024-05-01', '2024-03-15', 2.65, 7.50, 'Vendido', NULL),
('QR_AVI_021', 5, 11, NULL, 'Compra', 'M', '2024-05-01', '2024-03-15', 2.85, 8.00, 'Vendido', NULL),
('QR_AVI_022', 5, 11, NULL, 'Compra', 'H', '2024-05-01', '2024-03-15', 2.70, 7.50, 'Vendido', NULL),
('QR_AVI_023', 5, 11, NULL, 'Compra', 'M', '2024-05-01', '2024-03-15', 2.80, 8.00, 'Vendido', NULL),
('QR_AVI_024', 5, 11, NULL, 'Compra', 'H', '2024-05-01', '2024-03-15', 2.75, 7.50, 'Vendido', NULL),
('QR_AVI_025', 5, 11, NULL, 'Compra', 'M', '2024-05-01', '2024-03-15', 2.90, 8.00, 'Vendido', NULL),
('QR_AVI_026', 5, 11, NULL, 'Compra', 'H', '2024-05-01', '2024-03-15', 2.65, 7.50, 'Vendido', NULL),
('QR_AVI_027', 5, 11, NULL, 'Compra', 'M', '2024-05-01', '2024-03-15', 2.80, 8.00, 'Vendido', NULL),
('QR_AVI_028', 5, 11, NULL, 'Compra', 'H', '2024-05-01', '2024-03-15', 2.70, 7.50, 'Vendido', NULL),
('QR_AVI_029', 5, 11, NULL, 'Compra', 'M', '2024-05-01', '2024-03-15', 2.85, 8.00, 'Vendido', NULL),
('QR_AVI_030', 5, 11, NULL, 'Compra', 'H', '2024-05-01', '2024-03-15', 2.75, 7.50, 'Vendido', NULL),

-- Lote 12: Ponedoras PON1 (20 animales)
('QR_AVI_031', 5, 12, NULL, 'Compra', 'H', '2024-03-15', '2023-09-01', 1.80, 15.00, 'Vivo', NULL),
('QR_AVI_032', 5, 12, NULL, 'Compra', 'H', '2024-03-15', '2023-09-01', 1.75, 15.00, 'Vivo', NULL),
('QR_AVI_033', 5, 12, NULL, 'Compra', 'H', '2024-03-15', '2023-09-01', 1.82, 15.00, 'Vivo', NULL),
('QR_AVI_034', 5, 12, NULL, 'Compra', 'H', '2024-03-15', '2023-09-05', 1.78, 15.00, 'Vivo', NULL),
('QR_AVI_035', 5, 12, NULL, 'Compra', 'H', '2024-03-15', '2023-09-05', 1.85, 15.00, 'Vivo', NULL),
('QR_AVI_036', 5, 12, NULL, 'Compra', 'H', '2024-03-15', '2023-09-05', 1.77, 15.00, 'Vivo', NULL),
('QR_AVI_037', 5, 12, NULL, 'Compra', 'H', '2024-03-15', '2023-09-10', 1.80, 15.00, 'Vivo', NULL),
('QR_AVI_038', 5, 12, NULL, 'Compra', 'H', '2024-03-15', '2023-09-10', 1.83, 15.00, 'Vivo', NULL),
('QR_AVI_039', 5, 12, NULL, 'Compra', 'H', '2024-03-15', '2023-09-10', 1.76, 15.00, 'Vivo', NULL),
('QR_AVI_040', 5, 12, NULL, 'Compra', 'H', '2024-03-15', '2023-09-15', 1.81, 15.00, 'Vivo', NULL),
('QR_AVI_041', 5, 12, NULL, 'Compra', 'H', '2024-03-15', '2023-09-15', 1.79, 15.00, 'Vivo', NULL),
('QR_AVI_042', 5, 12, NULL, 'Compra', 'H', '2024-03-15', '2023-09-15', 1.84, 15.00, 'Vivo', NULL),
('QR_AVI_043', 5, 12, NULL, 'Compra', 'H', '2024-03-15', '2023-09-20', 1.78, 15.00, 'Vivo', NULL),
('QR_AVI_044', 5, 12, NULL, 'Compra', 'H', '2024-03-15', '2023-09-20', 1.82, 15.00, 'Vivo', NULL),
('QR_AVI_045', 5, 12, NULL, 'Compra', 'H', '2024-03-15', '2023-09-20', 1.77, 15.00, 'Vivo', NULL),
('QR_AVI_046', 5, 12, NULL, 'Compra', 'H', '2024-03-15', '2023-09-25', 1.80, 15.00, 'Vivo', NULL),
('QR_AVI_047', 5, 12, NULL, 'Compra', 'H', '2024-03-15', '2023-09-25', 1.85, 15.00, 'Vivo', NULL),
('QR_AVI_048', 5, 12, NULL, 'Compra', 'H', '2024-03-15', '2023-09-25', 1.76, 15.00, 'Vivo', NULL),
('QR_AVI_049', 5, 12, NULL, 'Compra', 'H', '2024-03-15', '2023-09-28', 1.81, 15.00, 'Vivo', NULL),
('QR_AVI_050', 5, 12, NULL, 'Compra', 'H', '2024-03-15', '2023-09-28', 1.79, 15.00, 'Vivo', NULL),

-- Lote 13: Pollos Engorde PE2 (25 animales)
('QR_AVI_051', 5, 13, NULL, 'Compra', 'M', '2024-06-15', '2024-05-01', 2.20, 7.50, 'Vivo', NULL),
('QR_AVI_052', 5, 13, NULL, 'Compra', 'H', '2024-06-15', '2024-05-01', 2.10, 7.00, 'Vivo', NULL),
('QR_AVI_053', 5, 13, NULL, 'Compra', 'M', '2024-06-15', '2024-05-01', 2.25, 7.50, 'Vivo', NULL),
('QR_AVI_054', 5, 13, NULL, 'Compra', 'H', '2024-06-15', '2024-05-01', 2.15, 7.00, 'Vivo', NULL),
('QR_AVI_055', 5, 13, NULL, 'Compra', 'M', '2024-06-15', '2024-05-01', 2.30, 7.50, 'Vivo', NULL),
('QR_AVI_056', 5, 13, NULL, 'Compra', 'H', '2024-06-15', '2024-05-03', 2.12, 7.00, 'Vivo', NULL),
('QR_AVI_057', 5, 13, NULL, 'Compra', 'M', '2024-06-15', '2024-05-03', 2.22, 7.50, 'Vivo', NULL),
('QR_AVI_058', 5, 13, NULL, 'Compra', 'H', '2024-06-15', '2024-05-03', 2.18, 7.00, 'Vivo', NULL),
('QR_AVI_059', 5, 13, NULL, 'Compra', 'M', '2024-06-15', '2024-05-05', 2.28, 7.50, 'Vivo', NULL),
('QR_AVI_060', 5, 13, NULL, 'Compra', 'H', '2024-06-15', '2024-05-05', 2.14, 7.00, 'Vivo', NULL),
('QR_AVI_061', 5, 13, NULL, 'Compra', 'M', '2024-06-15', '2024-05-08', 2.24, 7.50, 'Vivo', NULL),
('QR_AVI_062', 5, 13, NULL, 'Compra', 'H', '2024-06-15', '2024-05-08', 2.16, 7.00, 'Vivo', NULL),
('QR_AVI_063', 5, 13, NULL, 'Compra', 'M', '2024-06-15', '2024-05-10', 2.26, 7.50, 'Vivo', NULL),
('QR_AVI_064', 5, 13, NULL, 'Compra', 'H', '2024-06-15', '2024-05-10', 2.11, 7.00, 'Vivo', NULL),
('QR_AVI_065', 5, 13, NULL, 'Compra', 'M', '2024-06-15', '2024-05-12', 2.29, 7.50, 'Vivo', NULL),
('QR_AVI_066', 5, 13, NULL, 'Compra', 'H', '2024-06-15', '2024-05-12', 2.13, 7.00, 'Vivo', NULL),
('QR_AVI_067', 5, 13, NULL, 'Compra', 'M', '2024-06-15', '2024-05-15', 2.21, 7.50, 'Vivo', NULL),
('QR_AVI_068', 5, 13, NULL, 'Compra', 'H', '2024-06-15', '2024-05-15', 2.17, 7.00, 'Vivo', NULL),
('QR_AVI_069', 5, 13, NULL, 'Compra', 'M', '2024-06-15', '2024-05-18', 2.27, 7.50, 'Vivo', NULL),
('QR_AVI_070', 5, 13, NULL, 'Compra', 'H', '2024-06-15', '2024-05-18', 2.15, 7.00, 'Vivo', NULL),
('QR_AVI_071', 5, 13, NULL, 'Compra', 'M', '2024-06-15', '2024-05-20', 2.23, 7.50, 'Vivo', NULL),
('QR_AVI_072', 5, 13, NULL, 'Compra', 'H', '2024-06-15', '2024-05-20', 2.19, 7.00, 'Vivo', NULL),
('QR_AVI_073', 5, 13, NULL, 'Compra', 'M', '2024-06-15', '2024-05-22', 2.25, 7.50, 'Vivo', NULL),
('QR_AVI_074', 5, 13, NULL, 'Compra', 'H', '2024-06-15', '2024-05-22', 2.12, 7.00, 'Vivo', NULL),
('QR_AVI_075', 5, 13, NULL, 'Compra', 'M', '2024-06-15', '2024-05-25', 2.28, 7.50, 'Vivo', NULL),

-- Lote 14: Vacuno Engorde V2 (12 animales)
('QR_VAC_034', 1, 14, NULL, 'Compra', 'M', '2024-03-01', '2023-08-10', 285.00, 1520.00, 'Vivo', NULL),
('QR_VAC_035', 1, 14, NULL, 'Compra', 'M', '2024-03-01', '2023-08-15', 290.00, 1550.00, 'Vivo', NULL),
('QR_VAC_036', 1, 14, NULL, 'Compra', 'M', '2024-03-01', '2023-08-20', 282.00, 1510.00, 'Vivo', NULL),
('QR_VAC_037', 1, 14, NULL, 'Compra', 'M', '2024-03-01', '2023-08-25', 288.00, 1540.00, 'Vivo', NULL),
('QR_VAC_038', 1, 14, NULL, 'Compra', 'M', '2024-03-01', '2023-09-01', 283.00, 1515.00, 'Vivo', NULL),
('QR_VAC_039', 1, 14, NULL, 'Compra', 'M', '2024-03-01', '2023-09-05', 286.00, 1530.00, 'Vivo', NULL),
('QR_VAC_040', 1, 14, NULL, 'Compra', 'M', '2024-03-01', '2023-09-10', 281.00, 1505.00, 'Vivo', NULL),
('QR_VAC_041', 1, 14, NULL, 'Compra', 'M', '2024-03-01', '2023-09-15', 289.00, 1545.00, 'Vivo', NULL),
('QR_VAC_042', 1, 14, NULL, 'Compra', 'M', '2024-03-01', '2023-09-20', 284.00, 1520.00, 'Vivo', NULL),
('QR_VAC_043', 1, 14, NULL, 'Compra', 'M', '2024-03-01', '2023-09-25', 287.00, 1535.00, 'Vivo', NULL),
('QR_VAC_044', 1, 14, NULL, 'Compra', 'M', '2024-03-01', '2023-09-28', 285.00, 1525.00, 'Vivo', NULL),
('QR_VAC_045', 1, 14, NULL, 'Compra', 'M', '2024-03-01', '2023-10-01', 290.00, 1550.00, 'Vivo', NULL),

-- Lote 15: Cerdas Reproductoras CR1 (10 animales)
('QR_POR_033', 2, 15, NULL, 'Compra', 'H', '2024-02-25', '2022-06-10', 180.00, 850.00, 'Vivo', NULL),
('QR_POR_034', 2, 15, NULL, 'Compra', 'H', '2024-02-25', '2022-07-15', 175.00, 840.00, 'Vivo', NULL),
('QR_POR_035', 2, 15, NULL, 'Compra', 'H', '2024-02-25', '2022-08-20', 178.00, 845.00, 'Vivo', NULL),
('QR_POR_036', 2, 15, NULL, 'Compra', 'H', '2024-02-25', '2022-09-10', 182.00, 855.00, 'Vivo', NULL),
('QR_POR_037', 2, 15, NULL, 'Compra', 'H', '2024-02-25', '2022-10-15', 176.00, 842.00, 'Vivo', NULL),
('QR_POR_038', 2, 15, NULL, 'Compra', 'H', '2024-02-25', '2022-11-20', 179.00, 848.00, 'Vivo', NULL),
('QR_POR_039', 2, 15, NULL, 'Compra', 'H', '2024-02-25', '2022-12-10', 181.00, 852.00, 'Vivo', NULL),
('QR_POR_040', 2, 15, NULL, 'Compra', 'H', '2024-02-25', '2023-01-15', 177.00, 844.00, 'Vivo', NULL),
('QR_POR_041', 2, 15, 33, 'Nacimiento', 'H', '2024-06-20', '2024-06-20', 1.50, 0.00, 'Vivo', NULL),
('QR_POR_042', 2, 15, 34, 'Nacimiento', 'M', '2024-06-25', '2024-06-25', 1.60, 0.00, 'Vivo', NULL),

-- Lote 16: Vacuno Descarte VD1 - CERRADO (8 animales - todos vendidos)
('QR_VAC_046', 1, 16, NULL, 'Compra', 'H', '2024-05-15', '2018-03-10', 420.00, 800.00, 'Vendido', NULL),
('QR_VAC_047', 1, 16, NULL, 'Compra', 'M', '2024-05-15', '2018-05-20', 450.00, 850.00, 'Vendido', NULL),
('QR_VAC_048', 1, 16, NULL, 'Compra', 'H', '2024-05-15', '2018-07-15', 410.00, 780.00, 'Vendido', NULL),
('QR_VAC_049', 1, 16, NULL, 'Compra', 'M', '2024-05-15', '2018-09-10', 440.00, 830.00, 'Vendido', NULL),
('QR_VAC_050', 1, 16, NULL, 'Compra', 'H', '2024-05-15', '2018-11-05', 425.00, 810.00, 'Vendido', NULL),
('QR_VAC_051', 1, 16, NULL, 'Compra', 'M', '2024-05-15', '2019-01-20', 455.00, 860.00, 'Vendido', NULL),
('QR_VAC_052', 1, 16, NULL, 'Compra', 'H', '2024-05-15', '2019-03-15', 415.00, 790.00, 'Vendido', NULL),
('QR_VAC_053', 1, 16, NULL, 'Compra', 'M', '2024-05-15', '2019-05-10', 445.00, 840.00, 'Vendido', NULL);

-- ============================================
-- 8. REGISTRO DE COMPRAS (16 registros - uno por lote)
-- ============================================
INSERT INTO tb_registro_compra (lote_id, proveedor_nombre, fecha_compra, cantidad_animales, costo_total, observaciones) VALUES
(1, 'Ganadera Los Andes SAC', '2024-01-15 08:00:00', 15, 22650.00, 'Novillos de engorde raza Holstein'),
(2, 'Ganadera Lechera del Norte', '2024-02-01 09:00:00', 8, 27040.00, 'Vacas lecheras alta produccion'),
(3, 'Criadores Unidos SAC', '2024-05-20 08:00:00', 8, 3608.00, 'Terneros destetados para recria'),
(4, 'Porcicultores Asociados', '2024-03-10 07:30:00', 20, 3520.00, 'Lechones destetados linea genetica Landrace'),
(5, 'Granja San Martin', '2024-06-01 08:00:00', 12, 1410.00, 'Lechones recien destetados'),
(6, 'Caprinocultores del Sur', '2024-02-20 09:00:00', 15, 3855.00, 'Caprinos para engorde raza criolla mejorada'),
(7, 'Caprinos Lecheros SAC', '2024-01-10 09:00:00', 8, 2744.00, 'Cabras lecheras en produccion'),
(8, 'Avicola Santa Rosa', '2024-04-15 10:00:00', 25, 1100.00, 'Pavos BB linea comercial'),
(9, 'Incubadora Nacional', '2024-06-10 08:00:00', 20, 296.00, 'Pavitos BB recien nacidos'),
(10, 'Reproductoras Premium SAC', '2024-02-15 09:00:00', 10, 800.00, 'Pavas reproductoras certificadas'),
(11, 'Pollitos BB Express', '2024-05-01 06:00:00', 30, 232.50, 'Pollos BB linea Cobb 500'),
(12, 'Ponedoras Comerciales SAC', '2024-03-15 07:00:00', 20, 300.00, 'Gallinas ponedoras de 18 semanas'),
(13, 'Pollitos BB Express', '2024-06-15 06:00:00', 25, 187.50, 'Pollos BB linea Ross 308'),
(14, 'Ganadera Central', '2024-03-01 08:00:00', 12, 18300.00, 'Novillos engorde raza Brown Swiss'),
(15, 'Reproductores Porcinos SAC', '2024-02-25 08:00:00', 8, 6760.00, 'Cerdas reproductoras F1'),
(16, 'Comercializadora Ganadera', '2024-05-15 10:00:00', 8, 6560.00, 'Ganado descarte para faena');

-- ============================================
-- 9. REGISTRO DE ALIMENTACIÓN (48 registros distribuidos)
-- Último año (12 meses) - 3 registros por lote activo
-- ============================================
INSERT INTO tb_registro_alimentacion (lote_id, fecha_registro, cantidad_kg, costo_por_kg, dieta_tipo) VALUES
-- Lote 1: Vacuno Engorde A1
(1, '2024-02-15 08:00:00', 450.00, 1.80, 'Forraje + concentrado 16% proteina'),
(1, '2024-04-20 08:00:00', 480.00, 1.85, 'Forraje + concentrado 16% proteina'),
(1, '2024-06-25 08:00:00', 500.00, 1.90, 'Forraje + concentrado 16% proteina'),

-- Lote 2: Vacas Lecheras B1
(2, '2024-03-10 09:00:00', 300.00, 2.20, 'Forraje verde + concentrado lechero'),
(2, '2024-05-15 09:00:00', 320.00, 2.25, 'Forraje verde + concentrado lechero'),
(2, '2024-07-10 09:00:00', 310.00, 2.30, 'Forraje verde + concentrado lechero'),

-- Lote 3: Terneros T1
(3, '2024-06-01 08:00:00', 80.00, 3.50, 'Leche maternizada + concentrado inicio'),
(3, '2024-07-05 08:00:00', 90.00, 3.55, 'Leche maternizada + concentrado inicio'),
(3, '2024-08-10 08:00:00', 95.00, 3.60, 'Concentrado inicio + forraje'),

-- Lote 4: Porcino Engorde P1
(4, '2024-04-15 07:30:00', 200.00, 1.60, 'Balanceado 16% proteina'),
(4, '2024-05-20 07:30:00', 220.00, 1.65, 'Balanceado 16% proteina'),
(4, '2024-06-25 07:30:00', 240.00, 1.70, 'Balanceado finalizador'),

-- Lote 5: Lechones L1
(5, '2024-06-20 08:00:00', 60.00, 2.80, 'Alimento iniciador 20% proteina'),
(5, '2024-07-15 08:00:00', 70.00, 2.85, 'Alimento iniciador 20% proteina'),
(5, '2024-08-10 08:00:00', 75.00, 2.90, 'Alimento crecimiento'),

-- Lote 6: Caprino Engorde C1
(6, '2024-03-25 09:00:00', 120.00, 1.40, 'Forraje mixto + concentrado'),
(6, '2024-05-10 09:00:00', 130.00, 1.45, 'Forraje mixto + concentrado'),
(6, '2024-07-15 09:00:00', 135.00, 1.50, 'Forraje mixto + concentrado'),

-- Lote 7: Cabras Lecheras CL1
(7, '2024-02-20 09:00:00', 90.00, 1.80, 'Forraje + sales minerales'),
(7, '2024-04-15 09:00:00', 95.00, 1.85, 'Forraje + sales minerales'),
(7, '2024-06-20 09:00:00', 100.00, 1.90, 'Forraje + sales minerales'),

-- Lote 8: Pavos Engorde PV1
(8, '2024-05-10 10:00:00', 180.00, 2.10, 'Alimento crecimiento 22% proteina'),
(8, '2024-06-15 10:00:00', 200.00, 2.15, 'Alimento finalizador 18% proteina'),
(8, '2024-07-20 10:00:00', 220.00, 2.20, 'Alimento finalizador 18% proteina'),

-- Lote 9: Pavitos BB1
(9, '2024-06-25 08:00:00', 50.00, 3.20, 'Alimento iniciador 28% proteina'),
(9, '2024-07-20 08:00:00', 60.00, 3.25, 'Alimento iniciador 28% proteina'),
(9, '2024-08-15 08:00:00', 70.00, 3.30, 'Alimento crecimiento'),

-- Lote 10: Pavas Reproductoras PR1
(10, '2024-03-20 09:00:00', 80.00, 2.50, 'Alimento reproductor'),
(10, '2024-05-15 09:00:00', 85.00, 2.55, 'Alimento reproductor'),
(10, '2024-07-10 09:00:00', 90.00, 2.60, 'Alimento reproductor'),

-- Lote 11: Pollos Engorde PE1 (CERRADO)
(11, '2024-05-20 06:00:00', 150.00, 1.80, 'Alimento crecimiento 19% proteina'),
(11, '2024-06-10 06:00:00', 180.00, 1.85, 'Alimento finalizador 18% proteina'),
(11, '2024-06-28 06:00:00', 200.00, 1.90, 'Alimento finalizador 18% proteina'),

-- Lote 12: Ponedoras PON1
(12, '2024-04-10 07:00:00', 120.00, 2.00, 'Alimento postura 17% proteina'),
(12, '2024-05-25 07:00:00', 130.00, 2.05, 'Alimento postura 17% proteina'),
(12, '2024-07-15 07:00:00', 135.00, 2.10, 'Alimento postura 17% proteina'),

-- Lote 13: Pollos Engorde PE2
(13, '2024-07-01 06:00:00', 140.00, 1.80, 'Alimento crecimiento 19% proteina'),
(13, '2024-07-25 06:00:00', 160.00, 1.85, 'Alimento finalizador 18% proteina'),
(13, '2024-08-15 06:00:00', 175.00, 1.90, 'Alimento finalizador 18% proteina'),

-- Lote 14: Vacuno Engorde V2
(14, '2024-04-05 08:00:00', 420.00, 1.80, 'Forraje + concentrado 16% proteina'),
(14, '2024-06-10 08:00:00', 450.00, 1.85, 'Forraje + concentrado 16% proteina'),
(14, '2024-08-15 08:00:00', 480.00, 1.90, 'Forraje + concentrado finalizador'),

-- Lote 15: Cerdas Reproductoras CR1
(15, '2024-03-20 08:00:00', 140.00, 2.40, 'Alimento gestacion'),
(15, '2024-05-25 08:00:00', 150.00, 2.45, 'Alimento lactancia'),
(15, '2024-07-20 08:00:00', 145.00, 2.50, 'Alimento gestacion'),

-- Lote 16: Vacuno Descarte VD1 (CERRADO)
(16, '2024-05-25 10:00:00', 200.00, 1.20, 'Forraje basico'),
(16, '2024-06-15 10:00:00', 210.00, 1.25, 'Forraje basico');

-- ============================================
-- 10. REGISTRO SANITARIO (60 registros)
-- Individual y Masivo distribuidos
-- ============================================
INSERT INTO tb_registro_sanitario (lote_id, animal_id, tipo_aplicacion, protocolo_tipo, nombre_producto, costo_por_dosis, cantidad_dosis, animales_tratados, fecha_aplicacion) VALUES
-- VACUNAS MASIVAS - Lote 1: Vacuno Engorde A1
(1, NULL, 'Masivo', 'Vacuna', 'Triple Bovina (IBR-DVB-PI3)', 8.50, 1.0, 15, '2024-02-01 08:00:00'),
(1, NULL, 'Masivo', 'Vacuna', 'Clostr idiales 8 vias', 6.80, 1.0, 15, '2024-03-15 08:00:00'),
(1, NULL, 'Masivo', 'Vacuna', 'Refuerzo Triple Bovina', 8.50, 1.0, 14, '2024-05-20 08:00:00'),

-- VACUNAS MASIVAS - Lote 2: Vacas Lecheras B1
(2, NULL, 'Masivo', 'Vacuna', 'Brucelosis (RB51)', 12.00, 1.0, 10, '2024-02-15 09:00:00'),
(2, NULL, 'Masivo', 'Vacuna', 'Reproductiva Bovina', 15.50, 1.0, 10, '2024-04-10 09:00:00'),

-- TRATAMIENTOS INDIVIDUALES - Lote 1: Vacuno
(1, 5, 'Individual', 'Tratamiento', 'Ivermectina antiparasitario', 18.00, 1.0, NULL, '2024-03-10 10:00:00'),
(1, 7, 'Individual', 'Tratamiento', 'Antibiotico Oxitetraciclina LA', 25.00, 2.0, NULL, '2024-04-15 11:00:00'),
(1, 12, 'Individual', 'Tratamiento', 'Antiinflamatorio Flunixin', 20.00, 1.0, NULL, '2024-06-20 09:30:00'),

-- VACUNAS MASIVAS - Lote 4: Porcino Engorde P1
(4, NULL, 'Masivo', 'Vacuna', 'Circovirus Porcino', 5.50, 1.0, 20, '2024-03-25 07:30:00'),
(4, NULL, 'Masivo', 'Vacuna', 'Mycoplasma', 6.20, 1.0, 19, '2024-05-10 07:30:00'),

-- TRATAMIENTOS INDIVIDUALES - Lote 4: Porcino
(4, 35, 'Individual', 'Tratamiento', 'Antibiotico Enrofloxacina', 15.00, 3.0, NULL, '2024-04-20 08:00:00'),
(4, 38, 'Individual', 'Tratamiento', 'Complejo B inyectable', 12.00, 1.0, NULL, '2024-05-25 08:30:00'),

-- VACUNAS MASIVAS - Lote 5: Lechones L1
(5, NULL, 'Masivo', 'Vacuna', 'Circovirus + Mycoplasma', 7.80, 1.0, 12, '2024-06-15 08:00:00'),
(5, NULL, 'Masivo', 'Vacuna', 'Refuerzo Circovirus', 5.50, 1.0, 12, '2024-07-20 08:00:00'),

-- VACUNAS MASIVAS - Lote 6: Caprino Engorde C1
(6, NULL, 'Masivo', 'Vacuna', 'Clostr idiales Ovinos/Caprinos', 4.50, 1.0, 15, '2024-03-05 09:00:00'),
(6, NULL, 'Masivo', 'Vacuna', 'Refuerzo Clostridiales', 4.50, 1.0, 15, '2024-05-15 09:00:00'),

-- TRATAMIENTOS INDIVIDUALES - Lote 6: Caprino
(6, 61, 'Individual', 'Tratamiento', 'Desparasitante Albendazol', 8.00, 1.0, NULL, '2024-04-10 10:00:00'),
(6, 63, 'Individual', 'Tratamiento', 'Antibiotico Oxitetraciclina', 12.00, 1.0, NULL, '2024-06-15 09:30:00'),

-- VACUNAS MASIVAS - Lote 7: Cabras Lecheras CL1
(7, NULL, 'Masivo', 'Vacuna', 'Toxoide Tetanico', 3.80, 1.0, 10, '2024-01-25 09:00:00'),
(7, NULL, 'Masivo', 'Vacuna', 'Clostridiales 8 vias', 4.50, 1.0, 10, '2024-03-20 09:00:00'),

-- VACUNAS MASIVAS - Lote 8: Pavos Engorde PV1
(8, NULL, 'Masivo', 'Vacuna', 'Newcastle + Bronquitis', 2.50, 1.0, 25, '2024-04-30 10:00:00'),
(8, NULL, 'Masivo', 'Vacuna', 'Viruela Aviar', 1.80, 1.0, 25, '2024-06-05 10:00:00'),

-- TRATAMIENTOS INDIVIDUALES - Lote 8: Pavos
(8, 81, 'Individual', 'Tratamiento', 'Antibiotico Enrofloxacina', 8.00, 2.0, NULL, '2024-05-20 11:00:00'),
(8, 84, 'Individual', 'Tratamiento', 'Complejo vitaminico', 5.00, 1.0, NULL, '2024-06-25 10:30:00'),

-- VACUNAS MASIVAS - Lote 9: Pavitos BB1
(9, NULL, 'Masivo', 'Vacuna', 'Newcastle en agua', 1.20, 1.0, 20, '2024-06-25 08:00:00'),
(9, NULL, 'Masivo', 'Vacuna', 'Gumboro', 1.50, 1.0, 20, '2024-07-10 08:00:00'),

-- VACUNAS MASIVAS - Lote 11: Pollos Engorde PE1 (CERRADO)
(11, NULL, 'Masivo', 'Vacuna', 'Newcastle + Bronquitis', 1.80, 1.0, 30, '2024-05-15 06:00:00'),
(11, NULL, 'Masivo', 'Vacuna', 'Gumboro', 1.50, 1.0, 30, '2024-06-01 06:00:00'),

-- VACUNAS MASIVAS - Lote 12: Ponedoras PON1
(12, NULL, 'Masivo', 'Vacuna', 'Newcastle oleosa', 3.20, 1.0, 20, '2024-04-01 07:00:00'),
(12, NULL, 'Masivo', 'Vacuna', 'Bronquitis infecciosa', 2.80, 1.0, 20, '2024-05-15 07:00:00'),
(12, NULL, 'Masivo', 'Vacuna', 'Coriza aviar', 2.50, 1.0, 20, '2024-06-20 07:00:00'),

-- VACUNAS MASIVAS - Lote 13: Pollos Engorde PE2
(13, NULL, 'Masivo', 'Vacuna', 'Newcastle en agua', 1.80, 1.0, 25, '2024-07-01 06:00:00'),
(13, NULL, 'Masivo', 'Vacuna', 'Gumboro', 1.50, 1.0, 25, '2024-07-20 06:00:00'),

-- TRATAMIENTOS INDIVIDUALES - Lote 13: Pollos
(13, 121, 'Individual', 'Tratamiento', 'Antibiotico Amoxicilina', 6.00, 3.0, NULL, '2024-07-25 07:00:00'),

-- VACUNAS MASIVAS - Lote 14: Vacuno Engorde V2
(14, NULL, 'Masivo', 'Vacuna', 'Triple Bovina', 8.50, 1.0, 12, '2024-03-20 08:00:00'),
(14, NULL, 'Masivo', 'Vacuna', 'Carbunclo Sintomatico', 6.50, 1.0, 12, '2024-05-15 08:00:00'),
-- TRATAMIENTOS INDIVIDUALES - Lote 14: Vacuno
(14, 127, 'Individual', 'Tratamiento', 'Ivermectina 1%', 18.00, 1.0, NULL, '2024-04-25 09:00:00'),
(14, 129, 'Individual', 'Tratamiento', 'Oxitetraciclina LA', 25.00, 2.0, NULL, '2024-06-30 09:30:00'),
-- VACUNAS MASIVAS - Lote 15: Cerdas Reproductoras CR1
(15, NULL, 'Masivo', 'Vacuna', 'Parvovirus + Leptospira', 18.00, 1.0, 10, '2024-03-10 08:00:00'),
(15, NULL, 'Masivo', 'Vacuna', 'Circovirus adultos', 8.50, 1.0, 10, '2024-05-20 08:00:00'),
-- TRATAMIENTOS INDIVIDUALES - Lote 15: Cerdas
(15, 135, 'Individual', 'Tratamiento', 'Oxitocina post-parto', 15.00, 1.0, NULL, '2024-06-22 10:00:00'),
(15, 136, 'Individual', 'Tratamiento', 'Hierro dextrano lechones', 8.00, 1.0, NULL, '2024-06-27 09:00:00'),
-- VACUNAS MASIVAS - Lote 16: Vacuno Descarte VD1 (CERRADO)
(16, NULL, 'Masivo', 'Vacuna', 'Aftosa', 5.00, 1.0, 8, '2024-05-20 10:00:00'),
-- TRATAMIENTOS ADICIONALES distribuidos
(2, 16, 'Individual', 'Tratamiento', 'Calcio borogluconato', 22.00, 1.0, NULL, '2024-03-15 10:00:00'),
(2, 18, 'Individual', 'Tratamiento', 'Antibiotico mastitis', 35.00, 4.0, NULL, '2024-05-25 09:00:00'),
(3, 26, 'Individual', 'Tratamiento', 'Desparasitante interno', 12.00, 1.0, NULL, '2024-06-15 08:30:00'),
(3, 28, 'Individual', 'Tratamiento', 'Complejo vitaminico ADE', 15.00, 1.0, NULL, '2024-07-20 08:00:00'),
(10, 146, 'Individual', 'Tratamiento', 'Antibiotico respiratorio', 12.00, 3.0, NULL, '2024-04-20 10:00:00'),
(10, 148, 'Individual', 'Tratamiento', 'Desparasitante externo', 8.00, 1.0, NULL, '2024-06-15 09:30:00'),
(7, 66, 'Individual', 'Tratamiento', 'Suplemento mineral', 10.00, 1.0, NULL, '2024-02-25 10:00:00'),
(7, 68, 'Individual', 'Tratamiento', 'Antibiotico mastitis caprina', 18.00, 3.0, NULL, '2024-04-30 09:00:00');
-- ============================================
-- 11. REGISTRO DE PESO (80 registros)
-- Seguimiento periodico de animales seleccionados
-- ============================================
INSERT INTO tb_registro_peso (animal_id, fecha_pesaje, peso_kg, ganancia_kg) VALUES
-- Lote 1: Vacuno Engorde A1 (seguimiento de 5 animales)
(1, '2024-02-15 08:00:00', 295.00, 14.50),
(1, '2024-04-15 08:00:00', 325.00, 30.00),
(1, '2024-06-15 08:00:00', 360.00, 35.00),
(2, '2024-02-15 08:00:00', 290.00, 15.00),
(2, '2024-04-15 08:00:00', 320.00, 30.00),
(2, '2024-06-15 08:00:00', 355.00, 35.00),
(3, '2024-02-15 08:00:00', 300.00, 15.00),
(3, '2024-04-15 08:00:00', 335.00, 35.00),
(5, '2024-02-15 08:00:00', 293.00, 15.00),
(5, '2024-04-15 08:00:00', 328.00, 35.00),
-- Lote 2: Vacas Lecheras B1 (seguimiento de 4 animales)
(16, '2024-03-01 09:00:00', 525.00, 5.00),
(16, '2024-05-01 09:00:00', 530.00, 5.00),
(16, '2024-07-01 09:00:00', 528.00, -2.00),
(17, '2024-03-01 09:00:00', 515.00, 5.00),
(17, '2024-05-01 09:00:00', 520.00, 5.00),
(18, '2024-03-01 09:00:00', 520.00, 5.00),
(18, '2024-05-01 09:00:00', 525.00, 5.00),
-- Lote 3: Terneros T1 (todos los animales)
(26, '2024-06-20 08:00:00', 95.00, 10.00),
(26, '2024-07-20 08:00:00', 115.00, 20.00),
(27, '2024-06-20 08:00:00', 92.00, 10.00),
(27, '2024-07-20 08:00:00', 112.00, 20.00),
(28, '2024-06-20 08:00:00', 98.00, 10.00),
(28, '2024-07-20 08:00:00', 118.00, 20.00),
(29, '2024-06-20 08:00:00', 94.00, 10.00),
(30, '2024-06-20 08:00:00', 96.00, 10.00),
-- Lote 4: Porcino Engorde P1 (seguimiento de 6 animales)
(34, '2024-04-10 07:30:00', 52.00, 7.00),
(34, '2024-05-10 07:30:00', 68.00, 16.00),
(34, '2024-06-10 07:30:00', 88.00, 20.00),
(36, '2024-04-10 07:30:00', 51.00, 7.00),
(36, '2024-05-10 07:30:00', 66.00, 15.00),
(36, '2024-06-10 07:30:00', 85.00, 19.00),
(38, '2024-04-10 07:30:00', 53.00, 7.00),
(38, '2024-05-10 07:30:00', 69.00, 16.00),
(40, '2024-04-10 07:30:00', 50.00, 7.00),
(40, '2024-05-10 07:30:00', 65.00, 15.00),
-- Lote 5: Lechones L1 (seguimiento de 4 animales)
(54, '2024-07-01 08:00:00', 38.00, 10.00),
(54, '2024-08-01 08:00:00', 52.00, 14.00),
(55, '2024-07-01 08:00:00', 36.00, 10.00),
(55, '2024-08-01 08:00:00', 50.00, 14.00),
(56, '2024-07-01 08:00:00', 37.50, 10.00),
(57, '2024-07-01 08:00:00', 36.50, 10.00),
-- Lote 6: Caprino Engorde C1 (seguimiento de 5 animales)
(61, '2024-03-20 09:00:00', 43.00, 5.00),
(61, '2024-05-20 09:00:00', 51.00, 8.00),
(61, '2024-07-20 09:00:00', 58.00, 7.00),
(62, '2024-03-20 09:00:00', 45.00, 5.00),
(62, '2024-05-20 09:00:00', 53.00, 8.00),
(63, '2024-03-20 09:00:00', 44.00, 5.00),
(63, '2024-05-20 09:00:00', 52.00, 8.00),
(64, '2024-03-20 09:00:00', 46.00, 5.00),
-- Lote 8: Pavos Engorde PV1 (seguimiento de 5 animales)
(81, '2024-05-15 10:00:00', 11.50, 3.00),
(81, '2024-06-15 10:00:00', 15.80, 4.30),
(81, '2024-07-15 10:00:00', 19.50, 3.70),
(82, '2024-05-15 10:00:00', 10.80, 3.00),
(82, '2024-06-15 10:00:00', 14.90, 4.10),
(83, '2024-05-15 10:00:00', 11.20, 3.00),
(83, '2024-06-15 10:00:00', 15.40, 4.20),
(84, '2024-05-15 10:00:00', 11.60, 3.00),
-- Lote 11: Pollos Engorde PE1 - CERRADO (seguimiento de 5 animales)
(101, '2024-05-20 06:00:00', 3.50, 0.70),
(101, '2024-06-10 06:00:00', 5.20, 1.70),
(101, '2024-06-30 06:00:00', 6.80, 1.60),
(102, '2024-05-20 06:00:00', 3.30, 0.70),
(102, '2024-06-10 06:00:00', 5.00, 1.70),
(102, '2024-06-30 06:00:00', 6.50, 1.50),
(103, '2024-05-20 06:00:00', 3.45, 0.70),
(104, '2024-05-20 06:00:00', 3.35, 0.70),
-- Lote 13: Pollos Engorde PE2 (seguimiento de 4 animales)
(121, '2024-07-15 06:00:00', 3.20, 1.00),
(121, '2024-08-10 06:00:00', 4.80, 1.60),
(122, '2024-07-15 06:00:00', 3.10, 1.00),
(122, '2024-08-10 06:00:00', 4.60, 1.50),
(123, '2024-07-15 06:00:00', 3.25, 1.00),
(124, '2024-07-15 06:00:00', 3.15, 1.00),
-- Lote 14: Vacuno Engorde V2 (seguimiento de 4 animales)
(127, '2024-04-01 08:00:00', 300.00, 15.00),
(127, '2024-06-01 08:00:00', 340.00, 40.00),
(127, '2024-08-01 08:00:00', 380.00, 40.00),
(128, '2024-04-01 08:00:00', 305.00, 15.00),
(128, '2024-06-01 08:00:00', 345.00, 40.00),
(129, '2024-04-01 08:00:00', 297.00, 15.00),
(129, '2024-06-01 08:00:00', 337.00, 40.00);
-- ============================================
-- 12. REGISTRO DE PRODUCCIÓN (40 registros)
-- Solo para lotes de Reproduccion con produccion de leche
-- ============================================
INSERT INTO tb_registro_produccion (lote_id, fecha_registro, tipo_produccion, cantidad) VALUES
-- Lote 2: Vacas Lecheras B1 (produccion diaria)
(2, '2024-02-05 06:00:00', 'Leche', 180.50),
(2, '2024-02-20 06:00:00', 'Leche', 185.00),
(2, '2024-03-10 06:00:00', 'Leche', 190.00),
(2, '2024-03-25 06:00:00', 'Leche', 188.50),
(2, '2024-04-10 06:00:00', 'Leche', 195.00),
(2, '2024-04-25 06:00:00', 'Leche', 192.00),
(2, '2024-05-10 06:00:00', 'Leche', 198.00),
(2, '2024-05-25 06:00:00', 'Leche', 200.50),
(2, '2024-06-10 06:00:00', 'Leche', 196.00),
(2, '2024-06-25 06:00:00', 'Leche', 194.00),
(2, '2024-07-10 06:00:00', 'Leche', 197.50),
(2, '2024-07-25 06:00:00', 'Leche', 199.00),
(2, '2024-08-10 06:00:00', 'Leche', 201.50),
-- Lote 7: Cabras Lecheras CL1 (produccion diaria)
(7, '2024-01-20 06:00:00', 'Leche', 25.50),
(7, '2024-02-05 06:00:00', 'Leche', 28.00),
(7, '2024-02-20 06:00:00', 'Leche', 30.00),
(7, '2024-03-10 06:00:00', 'Leche', 32.50),
(7, '2024-03-25 06:00:00', 'Leche', 31.00),
(7, '2024-04-10 06:00:00', 'Leche', 33.00),
(7, '2024-04-25 06:00:00', 'Leche', 34.50),
(7, '2024-05-10 06:00:00', 'Leche', 35.00),
(7, '2024-05-25 06:00:00', 'Leche', 33.50),
(7, '2024-06-10 06:00:00', 'Leche', 32.00),
(7, '2024-06-25 06:00:00', 'Leche', 30.50),
(7, '2024-07-10 06:00:00', 'Leche', 31.50),
(7, '2024-07-25 06:00:00', 'Leche', 33.00),
(7, '2024-08-10 06:00:00', 'Leche', 34.00),
-- Lote 12: Ponedoras PON1 (produccion de huevos)
(12, '2024-03-20 06:00:00', 'Huevos', 340.00),
(12, '2024-04-05 06:00:00', 'Huevos', 355.00),
(12, '2024-04-20 06:00:00', 'Huevos', 360.00),
(12, '2024-05-05 06:00:00', 'Huevos', 365.00),
(12, '2024-05-20 06:00:00', 'Huevos', 370.00),
(12, '2024-06-05 06:00:00', 'Huevos', 368.00),
(12, '2024-06-20 06:00:00', 'Huevos', 372.00),
(12, '2024-07-05 06:00:00', 'Huevos', 375.00),
(12, '2024-07-20 06:00:00', 'Huevos', 378.00),
(12, '2024-08-05 06:00:00', 'Huevos', 380.00),
(12, '2024-08-20 06:00:00', 'Huevos', 376.00);
-- ============================================
-- 13. REGISTRO DE MOVILIDAD (15 registros)
-- Traslados entre lotes
-- ============================================
INSERT INTO tb_registro_movilidad (animal_id, lote_origen_id, lote_destino_id, fecha_movimiento, motivo) VALUES
-- Terneros que pasan de Lote 3 a Lote 1 (Engorde)
(26, 3, 1, '2024-08-15 08:00:00', 'Paso a etapa de engorde'),
(27, 3, 1, '2024-08-15 08:00:00', 'Paso a etapa de engorde'),
(28, 3, 1, '2024-08-15 08:00:00', 'Paso a etapa de engorde'),
-- Lechones que pasan de Lote 5 a Lote 4 (Engorde)
(54, 5, 4, '2024-08-20 08:00:00', 'Peso adecuado para engorde'),
(55, 5, 4, '2024-08-20 08:00:00', 'Peso adecuado para engorde'),
(56, 5, 4, '2024-08-20 08:00:00', 'Peso adecuado para engorde'),
-- Caprinos jovenes que pasan de enfermeria a engorde
(74, 7, 6, '2024-07-10 09:00:00', 'Destete completado'),
(75, 7, 6, '2024-07-10 09:00:00', 'Destete completado'),
-- Pavitos que pasan a engorde
(106, 9, 8, '2024-08-05 10:00:00', 'Listos para etapa final'),
(107, 9, 8, '2024-08-05 10:00:00', 'Listos para etapa final'),
(108, 9, 8, '2024-08-05 10:00:00', 'Listos para etapa final'),
-- Reproductores que pasan a descarte
(20, 2, 16, '2024-05-20 10:00:00', 'Baja produccion lechera'),
(67, 7, 16, '2024-05-22 10:00:00', 'Fin de vida productiva'),
-- Cerdas que cambian de lote
(135, 15, 4, '2024-07-15 08:00:00', 'Cerda descartada para engorde'),
(136, 15, 4, '2024-07-15 08:00:00', 'Cerda descartada para engorde');
-- ============================================
-- 14. REGISTRO DE MUERTE (8 registros)
-- ============================================
INSERT INTO tb_registro_muerte (animal_id, lote_id, fecha_muerte, causa_muerte) VALUES
(5, 4, '2024-05-15 14:30:00', 'Neumonia aguda - Tratamiento no respondio'),
(40, 4, '2024-06-20 10:15:00', 'Muerte subita - Posible problema cardiaco'),
(65, 6, '2024-06-10 16:00:00', 'Parasitosis severa - Animal debilitado'),
(85, 8, '2024-06-25 11:45:00', 'Infeccion respiratoria complicada'),
(110, 11, '2024-06-15 08:30:00', 'Ascitis - Sindrome muerte subita'),
(115, 11, '2024-06-20 09:00:00', 'Problema digestivo agudo'),
(130, 14, '2024-07-05 15:20:00', 'Timpanismo agudo no tratado a tiempo'),
(52, 10, '2024-05-25 12:00:00', 'Complicacion post-parto');
-- ============================================
-- 15. REGISTRO DE VENTA (6 registros)
-- ============================================
INSERT INTO tb_registro_venta (lote_id, tipo_alcance_venta, tipo_venta, peso_total_kg, precio_por_kg, precio_total, roi_estimado, roi_meta, cliente_nombre, fecha_venta, animales_vendidos_ids) VALUES
-- Venta Total Lote 11: Pollos Engorde PE1 (LOTE CERRADO)
(11, 'Total', 'Engorde', 195.00, 12.50, 2437.50, 28.50, 25.00, 'Distribuidora Avicola del Sur', '2024-07-05 10:00:00', ARRAY[101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130]),
-- Venta Parcial Lote 1: Vacuno Engorde (1 animal)
(1, 'Parcial', 'Engorde', 365.00, 15.80, 5767.00, 35.20, 30.00, 'Comercializadora Ganadera Norte', '2024-07-10 09:00:00', ARRAY[4]),
-- Venta Total Lote 16: Vacuno Descarte VD1 (LOTE CERRADO)
(16, 'Total', 'Descarte', 3400.00, 8.50, 28900.00, 18.50, 15.00, 'Frigor ifico Central SAC', '2024-07-20 11:00:00', ARRAY[143,144,145,146,147,148,149,150]),
-- Venta Parcial Lote 8: Pavos Engorde (5 animales)
(8, 'Parcial', 'Engorde', 98.50, 18.00, 1773.00, 32.80, 28.00, 'Restaurant El Buen Sabor', '2024-07-25 15:00:00', ARRAY[81,82,83,84,85]),
-- Venta Parcial Lote 6: Caprino Engorde (3 animales)
(6, 'Parcial', 'Engorde', 175.00, 16.50, 2887.50, 25.40, 22.00, 'Mercado Municipal Centro', '2024-08-05 10:00:00', ARRAY[61,62,63]),
-- Venta Parcial Lote 4: Porcino Engorde (4 animales)
(4, 'Parcial', 'Engorde', 340.00, 14.20, 4828.00, 30.50, 25.00, 'Distribuidora de Carnes La Granja', '2024-08-10 14:00:00', ARRAY[34,36,38,40]);