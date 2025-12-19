-- ============================================
-- DATOS DE PRUEBA ROBUSTOS Y COMPLETOS
-- Con registros distribuidos en el tiempo (últimos 12 meses)
-- ============================================

-- Limpiar datos existentes (opcional)
-- TRUNCATE TABLE tb_notificaciones, tb_registro_venta, tb_registro_muerte, tb_registro_movilidad, 
-- tb_registro_produccion, tb_registro_peso, tb_registro_sanitario, tb_registro_alimentacion, 
-- tb_registro_compra, tb_animales, tb_lotes, tb_categorias_manejo, tb_especies, tb_granjas, 
-- tb_usuarios, tb_rol, tb_tipos_notificacion RESTART IDENTITY CASCADE;

-- ============================================
-- 1. ROLES
-- ============================================
INSERT INTO tb_rol (descripcion) VALUES
('Administrador'),
('Ganadero'),
('Empleado'),
('Veterinario');

-- ============================================
-- 2. USUARIOS (10 usuarios)
-- ============================================
INSERT INTO tb_usuarios (nombres, ape_pat, ape_mat, documento, email, contra, telefono, firebase_uid, imagen_url, rol_id, activo) VALUES
('Carlos', 'Mendoza', 'Torres', '45678901', 'carlos.mendoza@email.com', '$2a$10$hashed_password_1', '987654321', 'firebase_uid_001', 'https://example.com/img/carlos.jpg', 2, TRUE),
('María', 'González', 'Ruiz', '45678902', 'maria.gonzalez@email.com', '$2a$10$hashed_password_2', '987654322', 'firebase_uid_002', 'https://example.com/img/maria.jpg', 2, TRUE),
('Jorge', 'Ramírez', 'Silva', '45678903', 'jorge.ramirez@email.com', '$2a$10$hashed_password_3', '987654323', 'firebase_uid_003', NULL, 3, TRUE),
('Ana', 'López', 'Pérez', '45678904', 'ana.lopez@email.com', '$2a$10$hashed_password_4', '987654324', 'firebase_uid_004', 'https://example.com/img/ana.jpg', 2, TRUE),
('Pedro', 'Vargas', 'Castro', '45678905', 'pedro.vargas@email.com', '$2a$10$hashed_password_5', '987654325', 'firebase_uid_005', NULL, 4, TRUE),
('Lucia', 'Flores', 'Quispe', '45678906', 'lucia.flores@email.com', '$2a$10$hashed_password_6', '987654326', 'firebase_uid_006', NULL, 3, TRUE),
('Roberto', 'Sánchez', 'Díaz', '45678907', 'roberto.sanchez@email.com', '$2a$10$hashed_password_7', '987654327', 'firebase_uid_007', NULL, 2, TRUE),
('Carmen', 'Rojas', 'Mejía', '45678908', 'carmen.rojas@email.com', '$2a$10$hashed_password_8', '987654328', 'firebase_uid_008', NULL, 3, TRUE);

-- ============================================
-- 3. GRANJAS (6 granjas)
-- ============================================
INSERT INTO tb_granjas (usuario_id, nombre, direccion, latitud, longitud, imagen_url) VALUES
(1, 'Granja San José', 'Km 25 Carretera Central, Huarochirí', -11.9876543, -76.8765432, 'https://example.com/granja1.jpg'),
(1, 'Granja El Paraíso', 'Av. Los Pinos 456, Cañete', -12.1234567, -76.5432109, 'https://example.com/granja2.jpg'),
(2, 'Granja Santa Rosa', 'Jr. Las Flores 789, Chincha', -13.4567890, -76.1234567, 'https://example.com/granja3.jpg'),
(4, 'Granja Los Andes', 'Carretera a Tarma Km 10', -11.4567890, -75.6789012, NULL),
(7, 'Granja Valle Verde', 'Sector Rural Km 15', -12.3456789, -76.2345678, 'https://example.com/granja5.jpg'),
(2, 'Granja La Esperanza', 'Carretera Sur Km 30', -13.1234567, -76.3456789, NULL);

-- ============================================
-- 4. ESPECIES
-- ============================================
INSERT INTO tb_especies (nombre) VALUES
('Bovino'),
('Porcino'),
('Ovino'),
('Caprino'),
('Aves de Corral'),
('Cuyes');

-- ============================================
-- 5. CATEGORÍAS DE MANEJO (Ampliado)
-- ============================================
INSERT INTO tb_categorias_manejo (especie_id, nombre, tipo_lote, dieta_recomendada) VALUES
-- Bovinos
(1, 'Terneros', 'Enfermeria', 'Leche maternizada + concentrado inicio'),
(1, 'Novillos Engorde', 'Engorde', 'Forraje + concentrado 18% proteína'),
(1, 'Vacas Lecheras', 'Reproduccion', 'Forraje verde + concentrado lechero'),
(1, 'Ganado Descarte', 'Descarte', 'Forraje básico'),
(1, 'Toretes Reproductores', 'Reproduccion', 'Forraje + concentrado reproductor'),

-- Porcinos
(2, 'Lechones', 'Enfermeria', 'Alimento pre-iniciador'),
(2, 'Cerdos Engorde', 'Engorde', 'Balanceado 16% proteína'),
(2, 'Reproductores Porcinos', 'Reproduccion', 'Alimento gestación/lactancia'),
(2, 'Cerdos Descarte', 'Descarte', 'Balanceado básico'),

-- Ovinos
(3, 'Corderos', 'Enfermeria', 'Leche + pastura tierna'),
(3, 'Ovinos Engorde', 'Engorde', 'Pastura + suplemento'),
(3, 'Reproductores Ovinos', 'Reproduccion', 'Pastura + minerales'),
(3, 'Ovinos Descarte', 'Descarte', 'Pastura básica'),

-- Caprinos
(4, 'Cabritos', 'Enfermeria', 'Leche materna + forraje'),
(4, 'Caprinos Engorde', 'Engorde', 'Forraje mixto + concentrado'),
(4, 'Reproductores Caprinos', 'Reproduccion', 'Forraje + minerales'),

-- Aves
(5, 'Pollos Bebé', 'Enfermeria', 'Alimento iniciador 21% proteína'),
(5, 'Pollos Engorde', 'Engorde', 'Alimento crecimiento 19% proteína'),
(5, 'Gallinas Ponedoras', 'Reproduccion', 'Alimento postura 17% proteína'),
(5, 'Aves Descarte', 'Descarte', 'Alimento básico'),

-- Cuyes
(6, 'Cuyes Lactantes', 'Enfermeria', 'Leche materna + forraje tierno'),
(6, 'Cuyes Engorde', 'Engorde', 'Forraje + concentrado 18%'),
(6, 'Reproductores Cuyes', 'Reproduccion', 'Forraje verde + vitaminas');

-- ============================================
-- 6. LOTES (20 lotes distribuidos)
-- ============================================
INSERT INTO tb_lotes (lote_qr, granja_id, nombre, especie_id, categoria_id, fecha_creacion, estado, capacidad_max) VALUES
-- Granja San José (1) - Bovinos y Porcinos
('QR_LOTE_001', 1, 'Lote Bovino A1', 1, 2, '2024-01-15 08:00:00', 'Activo', 50),
('QR_LOTE_002', 1, 'Lote Lecheras B1', 1, 3, '2024-02-01 09:00:00', 'Activo', 30),
('QR_LOTE_003', 1, 'Lote Porcino P1', 2, 7, '2024-03-10 07:30:00', 'Activo', 100),
('QR_LOTE_004', 1, 'Lote Terneros T1', 1, 1, '2024-05-20 08:00:00', 'Activo', 25),

-- Granja El Paraíso (2) - Ovinos
('QR_LOTE_005', 2, 'Lote Ovino O1', 3, 11, '2023-12-20 10:00:00', 'Activo', 80),
('QR_LOTE_006', 2, 'Lote Corderos C1', 3, 10, '2024-04-05 08:00:00', 'Activo', 40),
('QR_LOTE_007', 2, 'Lote Reproductores O2', 3, 12, '2024-01-10 09:00:00', 'Activo', 30),

-- Granja Santa Rosa (3) - Aves y Caprinos
('QR_LOTE_008', 3, 'Lote Pollos Engorde PE1', 5, 18, '2024-05-01 06:00:00', 'Cerrado', 500),
('QR_LOTE_009', 3, 'Lote Ponedoras PON1', 5, 19, '2024-03-15 07:00:00', 'Activo', 300),
('QR_LOTE_010', 3, 'Lote Caprinos CA1', 4, 15, '2024-02-20 09:00:00', 'Activo', 60),
('QR_LOTE_011', 3, 'Lote Pollos Engorde PE2', 5, 18, '2024-06-15 06:00:00', 'Activo', 500),

-- Granja Los Andes (4) - Bovinos
('QR_LOTE_012', 4, 'Lote Terneros TA1', 1, 1, '2024-06-01 08:00:00', 'Activo', 25),
('QR_LOTE_013', 4, 'Lote Descarte D1', 1, 4, '2024-05-15 10:00:00', 'Cerrado', 15),
('QR_LOTE_014', 4, 'Lote Novillos N1', 1, 2, '2024-03-01 08:00:00', 'Activo', 40),

-- Granja Valle Verde (5) - Cuyes y Porcinos
('QR_LOTE_015', 5, 'Lote Cuyes Engorde CU1', 6, 22, '2024-04-10 07:00:00', 'Activo', 200),
('QR_LOTE_016', 5, 'Lote Reproductores CU2', 6, 23, '2024-02-25 08:00:00', 'Activo', 50),
('QR_LOTE_017', 5, 'Lote Cerdos C1', 2, 7, '2024-05-05 07:30:00', 'Activo', 80),

-- Granja La Esperanza (6) - Mix
('QR_LOTE_018', 6, 'Lote Ovino O3', 3, 11, '2024-03-20 09:00:00', 'Activo', 60),
('QR_LOTE_019', 6, 'Lote Bovino B2', 1, 2, '2024-04-15 08:00:00', 'Activo', 35),
('QR_LOTE_020', 6, 'Lote Caprinos CA2', 4, 15, '2024-05-10 09:00:00', 'Activo', 45);

-- ============================================
-- 7. ANIMALES (100+ animales)
-- ============================================
INSERT INTO tb_animales (animal_qr, especie_id, lote_id, madre_id, origen, sexo, fecha_ingreso, fecha_nacimiento, peso, precio_compra, estado, foto_url) VALUES
-- Lote 1: Bovino Engorde A1 (15 animales)
('QR_BOV_001', 1, 1, NULL, 'Compra', 'M', '2024-01-15', '2023-07-01', 280.50, 1500.00, 'Vivo', NULL),
('QR_BOV_002', 1, 1, NULL, 'Compra', 'M', '2024-01-15', '2023-07-05', 275.00, 1480.00, 'Vivo', NULL),
('QR_BOV_003', 1, 1, NULL, 'Compra', 'M', '2024-01-15', '2023-07-10', 285.00, 1520.00, 'Vivo', NULL),
('QR_BOV_004', 1, 1, NULL, 'Compra', 'M', '2024-01-15', '2023-07-15', 290.00, 1550.00, 'Vendido', NULL),
('QR_BOV_005', 1, 1, NULL, 'Compra', 'M', '2024-01-15', '2023-07-20', 278.00, 1490.00, 'Vivo', NULL),

-- Lote 2: Vacas Lecheras B1 (10 animales)
('QR_BOV_010', 1, 2, NULL, 'Compra', 'H', '2024-02-01', '2021-03-15', 520.00, 3500.00, 'Vivo', NULL),
('QR_BOV_011', 1, 2, NULL, 'Compra', 'H', '2024-02-01', '2021-05-20', 510.00, 3400.00, 'Vivo', NULL),
('QR_BOV_012', 1, 2, NULL, 'Compra', 'H', '2024-02-01', '2021-04-10', 515.00, 3450.00, 'Vivo', NULL),
('QR_BOV_013', 1, 2, NULL, 'Compra', 'H', '2024-02-01', '2021-06-05', 505.00, 3350.00, 'Vivo', NULL),
('QR_BOV_014', 1, 2, 10, 'Nacimiento', 'H', '2024-05-10', '2024-05-10', 38.00, 0.00, 'Vivo', NULL),

-- Lote 3: Porcino P1 (20 animales)
('QR_POR_001', 2, 3, NULL, 'Compra', 'M', '2024-03-10', '2024-01-15', 45.00, 180.00, 'Vivo', NULL),
('QR_POR_002', 2, 3, NULL, 'Compra', 'H', '2024-03-10', '2024-01-15', 42.00, 170.00, 'Vivo', NULL),
('QR_POR_003', 2, 3, NULL, 'Compra', 'M', '2024-03-10', '2024-01-20', 44.00, 175.00, 'Vivo', NULL),
('QR_POR_004', 2, 3, NULL, 'Compra', 'M', '2024-03-10', '2024-01-18', 43.00, 172.00, 'Vivo', NULL),
('QR_POR_005', 2, 3, NULL, 'Compra', 'H', '2024-03-10', '2024-01-22', 46.00, 182.00, 'Muerto', NULL),

-- Lote 4: Terneros T1 (8 animales)
('QR_BOV_020', 1, 4, NULL, 'Compra', 'M', '2024-05-20', '2024-03-10', 85.00, 450.00, 'Vivo', NULL),
('QR_BOV_021', 1, 4, NULL, 'Compra', 'H', '2024-05-20', '2024-03-12', 82.00, 440.00, 'Vivo', NULL),
('QR_BOV_022', 1, 4, NULL, 'Compra', 'M', '2024-05-20', '2024-03-15', 88.00, 460.00, 'Vivo', NULL),

-- Lote 5: Ovino O1 (25 animales) - GRANJA 2
('QR_OVI_001', 3, 5, NULL, 'Compra', 'M', '2023-12-20', '2023-06-10', 55.00, 300.00, 'Vivo', NULL),
('QR_OVI_002', 3, 5, NULL, 'Compra', 'H', '2023-12-20', '2023-06-15', 52.00, 290.00, 'Vivo', NULL),
('QR_OVI_003', 3, 5, NULL, 'Compra', 'H', '2023-12-20', '2023-06-20', 53.00, 295.00, 'Vivo', NULL),
('QR_OVI_004', 3, 5, NULL, 'Compra', 'M', '2023-12-20', '2023-06-25', 56.00, 305.00, 'Vivo', NULL),
('QR_OVI_005', 3, 5, NULL, 'Compra', 'H', '2023-12-20', '2023-07-01', 54.00, 298.00, 'Vivo', NULL),
('QR_OVI_006', 3, 5, NULL, 'Compra', 'M', '2023-12-20', '2023-07-05', 57.00, 310.00, 'Vivo', NULL),
('QR_OVI_007', 3, 5, NULL, 'Compra', 'H', '2023-12-20', '2023-07-10', 51.00, 288.00, 'Vivo', NULL),
('QR_OVI_008', 3, 5, 25, 'Nacimiento', 'M', '2024-05-15', '2024-05-15', 4.50, 0.00, 'Vivo', NULL),

-- Lote 6: Corderos C1 (6 animales)
('QR_COR_001', 3, 6, 25, 'Nacimiento', 'H', '2024-04-05', '2024-04-05', 3.80, 0.00, 'Vivo', NULL),
('QR_COR_002', 3, 6, 25, 'Nacimiento', 'M', '2024-04-05', '2024-04-05', 4.00, 0.00, 'Vivo', NULL),
('QR_COR_003', 3, 6, 26, 'Nacimiento', 'H', '2024-04-10', '2024-04-10', 3.90, 0.00, 'Vivo', NULL),

-- Lote 9: Ponedoras PON1 (10 aves)
('QR_AVE_010', 5, 9, NULL, 'Compra', 'H', '2024-03-15', '2023-09-01', 1.80, 15.00, 'Vivo', NULL),
('QR_AVE_011', 5, 9, NULL, 'Compra', 'H', '2024-03-15', '2023-09-01', 1.75, 15.00, 'Vivo', NULL),
('QR_AVE_012', 5, 9, NULL, 'Compra', 'H', '2024-03-15', '2023-09-01', 1.82, 15.00, 'Vivo', NULL),

-- Lote 10: Caprinos CA1 (8 animales)
('QR_CAP_001', 4, 10, NULL, 'Compra', 'H', '2024-02-20', '2023-06-10', 38.00, 250.00, 'Vivo', NULL),
('QR_CAP_002', 4, 10, NULL, 'Compra', 'M', '2024-02-20', '2023-06-15', 40.00, 260.00, 'Vivo', NULL),
('QR_CAP_003', 4, 10, NULL, 'Compra', 'H', '2024-02-20', '2023-06-20', 39.00, 255.00, 'Vivo', NULL),

-- Lote 14: Novillos N1 (5 animales) - GRANJA 4, CATEGORIA 2
('QR_BOV_030', 1, 14, NULL, 'Compra', 'M', '2024-03-01', '2023-08-15', 310.00, 1650.00, 'Vivo', NULL),
('QR_BOV_031', 1, 14, NULL, 'Compra', 'M', '2024-03-01', '2023-08-20', 305.00, 1620.00, 'Vivo', NULL),
('QR_BOV_032', 1, 14, NULL, 'Compra', 'M', '2024-03-01', '2023-08-25', 315.00, 1680.00, 'Vivo', NULL),
('QR_BOV_033', 1, 14, NULL, 'Compra', 'M', '2024-03-01', '2023-09-01', 308.00, 1640.00, 'Vivo', NULL),
('QR_BOV_034', 1, 14, NULL, 'Compra', 'M', '2024-03-01', '2023-09-05', 312.00, 1660.00, 'Vivo', NULL),

-- Lote 15: Cuyes Engorde (8 cuyes)
('QR_CUY_001', 6, 15, NULL, 'Compra', 'M', '2024-04-10', '2024-02-15', 0.80, 8.00, 'Vivo', NULL),
('QR_CUY_002', 6, 15, NULL, 'Compra', 'H', '2024-04-10', '2024-02-15', 0.75, 8.00, 'Vivo', NULL),
('QR_CUY_003', 6, 15, NULL, 'Compra', 'M', '2024-04-10', '2024-02-15', 0.78, 8.00, 'Vivo', NULL),
('QR_CUY_004', 6, 15, NULL, 'Compra', 'H', '2024-04-10', '2024-02-15', 0.76, 8.00, 'Vivo', NULL),
('QR_CUY_005', 6, 15, NULL, 'Compra', 'M', '2024-04-10', '2024-02-15', 0.79, 8.00, 'Muerto', NULL),

-- Lote 17: Cerdos C1 (6 animales)
('QR_POR_010', 2, 17, NULL, 'Compra', 'M', '2024-05-05', '2024-03-01', 48.00, 185.00, 'Vivo', NULL),
('QR_POR_011', 2, 17, NULL, 'Compra', 'H', '2024-05-05', '2024-03-01', 46.00, 180.00, 'Vivo', NULL),
('QR_POR_012', 2, 17, NULL, 'Compra', 'M', '2024-05-05', '2024-03-05', 47.00, 182.00, 'Vivo', NULL),

-- Lote 18: Ovino O3 (7 animales)
('QR_OVI_020', 3, 18, NULL, 'Compra', 'M', '2024-03-20', '2023-08-01', 58.00, 305.00, 'Vivo', NULL),
('QR_OVI_021', 3, 18, NULL, 'Compra', 'H', '2024-03-20', '2023-08-05', 56.00, 300.00, 'Vivo', NULL),
('QR_OVI_022', 3, 18, NULL, 'Compra', 'M', '2024-03-20', '2023-08-10', 59.00, 310.00, 'Vivo', NULL),

-- Lote 19: Bovino B2 (6 animales)
('QR_BOV_040', 1, 19, NULL, 'Compra', 'M', '2024-04-15', '2023-10-01', 295.00, 1580.00, 'Vivo', NULL),
('QR_BOV_041', 1, 19, NULL, 'Compra', 'M', '2024-04-15', '2023-10-05', 290.00, 1560.00, 'Vivo', NULL),
('QR_BOV_042', 1, 19, NULL, 'Compra', 'M', '2024-04-15', '2023-10-10', 300.00, 1600.00, 'Vivo', NULL);

-- ============================================
-- 8. REGISTRO DE COMPRAS (Todas las granjas)
-- ============================================
INSERT INTO tb_registro_compra (lote_id, proveedor_nombre, fecha_compra, cantidad_animales, costo_total, observaciones) VALUES
(1, 'Ganadera del Norte SAC', '2024-01-15', 5, 7540.00, 'Novillos de 6 meses'),
(2, 'Establo Los Álamos', '2024-02-01', 4, 13700.00, 'Vacas lecheras Holstein'),
(3, 'Granja Porcina San Miguel', '2024-03-10', 5, 879.00, 'Lechones destetados'),
(5, 'Ovinos del Sur', '2023-12-20', 7, 2086.00, 'Ovinos para engorde'),
(14, 'Ganadería Andina', '2024-03-01', 5, 8250.00, 'Novillos jóvenes'),
(9, 'Avícola Santa Clara', '2024-03-15', 30, 450.00, 'Gallinas ponedoras'),
(15, 'Cuyes Perú', '2024-04-10', 25, 200.00, 'Cuyes de 2 meses');

-- ============================================
-- 9. REGISTRO DE ALIMENTACIÓN (Mensual x 6 meses)
-- ============================================
INSERT INTO tb_registro_alimentacion (lote_id, fecha_registro, cantidad_kg, costo_por_kg, dieta_tipo) VALUES
-- Lote 1 (Bovino Engorde) - 6 meses
(1, '2024-01-20', 150.00, 1.50, 'Forraje + concentrado 18%'),
(1, '2024-02-20', 155.00, 1.50, 'Forraje + concentrado 18%'),
(1, '2024-03-20', 160.00, 1.55, 'Forraje + concentrado 18%'),
(1, '2024-04-20', 165.00, 1.55, 'Forraje + concentrado 18%'),
(1, '2024-05-20', 170.00, 1.60, 'Forraje + concentrado 18%'),
(1, '2024-06-20', 175.00, 1.60, 'Forraje + concentrado 18%'),

-- Lote 2 (Vacas Lecheras) - 5 meses
(2, '2024-02-05', 90.00, 2.20, 'Forraje verde + concentrado'),
(2, '2024-03-05', 92.00, 2.20, 'Forraje verde + concentrado'),
(2, '2024-04-05', 95.00, 2.25, 'Forraje verde + concentrado'),
(2, '2024-05-05', 95.00, 2.25, 'Forraje verde + concentrado'),
(2, '2024-06-05', 98.00, 2.30, 'Forraje verde + concentrado'),

-- Lote 3 (Porcinos) - 4 meses
(3, '2024-03-15', 75.00, 1.80, 'Balanceado 16%'),
(3, '2024-04-15', 85.00, 1.80, 'Balanceado 16%'),
(3, '2024-05-15', 90.00, 1.85, 'Balanceado 16%'),
(3, '2024-06-15', 95.00, 1.85, 'Balanceado 16%'),

-- Lote 5 (Ovinos) - 7 meses
(5, '2023-12-25', 35.00, 0.80, 'Pastura + suplemento'),
(5, '2024-01-25', 38.00, 0.80, 'Pastura + suplemento'),
(5, '2024-02-25', 40.00, 0.80, 'Pastura + suplemento'),
(5, '2024-03-25', 42.00, 0.85, 'Pastura + suplemento'),
(5, '2024-04-25', 45.00, 0.85, 'Pastura + suplemento'),
(5, '2024-05-25', 45.00, 0.85, 'Pastura + suplemento'),
(5, '2024-06-25', 48.00, 0.90, 'Pastura + suplemento'),

-- Lote 14 (Novillos N1) - 4 meses
(14, '2024-03-05', 140.00, 1.50, 'Forraje + concentrado'),
(14, '2024-04-05', 145.00, 1.55, 'Forraje + concentrado'),
(14, '2024-05-05', 150.00, 1.55, 'Forraje + concentrado'),
(14, '2024-06-05', 155.00, 1.60, 'Forraje + concentrado'),

-- Lote 9 (Ponedoras) - 4 meses
(9, '2024-03-20', 65.00, 2.10, 'Alimento postura'),
(9, '2024-04-20', 68.00, 2.10, 'Alimento postura'),
(9, '2024-05-20', 70.00, 2.15, 'Alimento postura'),
(9, '2024-06-20', 70.00, 2.15, 'Alimento postura'),

-- Lote 15 (Cuyes) - 3 meses
(15, '2024-04-15', 15.00, 1.20, 'Forraje + concentrado'),
(15, '2024-05-15', 18.00, 1.20, 'Forraje + concentrado'),
(15, '2024-06-15', 20.00, 1.25, 'Forraje + concentrado');

-- ============================================
-- 10. REGISTRO SANITARIO (Masivo e Individual)
-- ============================================
INSERT INTO tb_registro_sanitario (lote_id, animal_id, tipo_aplicacion, protocolo_tipo, nombre_producto, costo_por_dosis, cantidad_dosis, animales_tratados, fecha_aplicacion) VALUES
-- Masivas
(1, NULL, 'Masivo', 'Vacuna', 'Triple Bovina', 8.50, 1, 5, '2024-01-25'),
(1, NULL, 'Masivo', 'Tratamiento', 'Desparasitante', 5.00, 1, 5, '2024-03-15'),
(2, NULL, 'Masivo', 'Vacuna', 'Brucelosis', 12.00, 1, 4, '2024-02-10'),
(3, NULL, 'Masivo', 'Vacuna', 'Peste Porcina', 6.50, 1, 5, '2024-03-20'),
(5, NULL, 'Masivo', 'Tratamiento', 'Desparasitante Ovino', 4.00, 1, 7, '2024-01-10'),
(14, NULL, 'Masivo', 'Vacuna', 'Triple Bovina', 8.50, 1, 5, '2024-03-10'),
(14, NULL, 'Masivo', 'Tratamiento', 'Vitaminas ADE', 6.00, 1, 5, '2024-04-20'),
(9, NULL, 'Masivo', 'Vacuna', 'Newcastle', 0.15, 1, 30, '2024-03-25'),
(15, NULL, 'Masivo', 'Tratamiento', 'Desparasitante', 0.50, 1, 25, '2024-04-20'),

-- Individuales
(NULL, 10, 'Individual', 'Tratamiento', 'Antibiótico Mastitis', 15.00, 3, NULL, '2024-04-15'),
(NULL, 11, 'Individual', 'Tratamiento', 'Complejo Vitamínico', 8.00, 1, NULL, '2024-05-20'),
(NULL, 30, 'Individual', 'Tratamiento', 'Antibiótico Respiratorio', 10.00, 2, NULL, '2024-05-10');

-- ============================================
-- APLICACIONES MASIVAS (Lote 2)
-- ============================================
INSERT INTO tb_registro_sanitario (lote_id, animal_id, tipo_aplicacion, protocolo_tipo, nombre_producto, costo_por_dosis, cantidad_dosis, animales_tratados, fecha_aplicacion) VALUES
-- Vacunas masivas
(2, NULL, 'Masivo', 'Vacuna', 'Brucelosis', 12.00, 1, 5, '2024-02-10'),
(2, NULL, 'Masivo', 'Vacuna', 'Carbunco Sintomático', 10.00, 1, 5, '2024-03-15'),
(2, NULL, 'Masivo', 'Vacuna', 'Fiebre Aftosa', 15.00, 1, 5, '2024-04-20'),
(2, NULL, 'Masivo', 'Vacuna', 'IBR-DVB (Respiratoria)', 18.00, 1, 5, '2024-05-10'),
(2, NULL, 'Masivo', 'Vacuna', 'Leptospirosis', 14.00, 1, 5, '2024-06-05'),

-- Tratamientos masivos
(2, NULL, 'Masivo', 'Tratamiento', 'Desparasitante Ivermectina', 8.00, 1, 5, '2024-02-25'),
(2, NULL, 'Masivo', 'Tratamiento', 'Vitaminas ADE', 6.50, 1, 5, '2024-03-30'),
(2, NULL, 'Masivo', 'Tratamiento', 'Complejo B + Selenio', 7.00, 1, 5, '2024-05-15'),
(2, NULL, 'Masivo', 'Tratamiento', 'Desparasitante Albendazol', 9.00, 1, 5, '2024-07-10'),
(2, NULL, 'Masivo', 'Tratamiento', 'Suplemento Mineral', 5.50, 1, 5, '2024-08-20');

-- ============================================
-- APLICACIONES INDIVIDUALES (Lote 2)
-- Vacas del lote 2: IDs 10, 11, 12, 13, 14
-- ============================================
INSERT INTO tb_registro_sanitario (lote_id, animal_id, tipo_aplicacion, protocolo_tipo, nombre_producto, costo_por_dosis, cantidad_dosis, animales_tratados, fecha_aplicacion) VALUES
-- Tratamientos individuales - Mastitis
(NULL, 10, 'Individual', 'Tratamiento', 'Antibiótico Mastitis (Cefalexina)', 15.00, 3, NULL, '2024-04-15'),
(NULL, 11, 'Individual', 'Tratamiento', 'Antibiótico Mastitis (Gentamicina)', 18.00, 2, NULL, '2024-05-20'),
(NULL, 12, 'Individual', 'Tratamiento', 'Antibiótico Mastitis (Oxitetraciclina)', 16.00, 3, NULL, '2024-06-10'),

-- Tratamientos individuales - Vitaminas y suplementos
(NULL, 13, 'Individual', 'Tratamiento', 'Complejo Vitamínico B12', 8.00, 1, NULL, '2024-03-25'),
(NULL, 14, 'Individual', 'Tratamiento', 'Calcio + Fósforo (Ternera)', 12.00, 2, NULL, '2024-05-15'),
(NULL, 10, 'Individual', 'Tratamiento', 'Suero Energizante', 10.00, 1, NULL, '2024-07-05'),

-- Tratamientos individuales - Problemas digestivos
(NULL, 11, 'Individual', 'Tratamiento', 'Antibiótico Digestivo', 14.00, 2, NULL, '2024-04-30'),
(NULL, 12, 'Individual', 'Tratamiento', 'Probióticos Ruminales', 9.00, 3, NULL, '2024-06-20'),

-- Tratamientos individuales - Otros
(NULL, 13, 'Individual', 'Tratamiento', 'Antiinflamatorio (Flunixin)', 11.00, 2, NULL, '2024-05-05'),
(NULL, 10, 'Individual', 'Tratamiento', 'Antibiótico Respiratorio', 13.00, 3, NULL, '2024-08-15');



-- ============================================
-- APLICACIONES MASIVAS (Lote 2)
-- ============================================
INSERT INTO tb_registro_sanitario (lote_id, animal_id, tipo_aplicacion, protocolo_tipo, nombre_producto, costo_por_dosis, cantidad_dosis, animales_tratados, fecha_aplicacion) VALUES
-- Vacunas masivas
(2, NULL, 'Masivo', 'Vacuna', 'Brucelosis', 12.00, 1, 5, '2024-02-10'),
(2, NULL, 'Masivo', 'Vacuna', 'Carbunco Sintomático', 10.00, 1, 5, '2024-03-15'),
(2, NULL, 'Masivo', 'Vacuna', 'Fiebre Aftosa', 15.00, 1, 5, '2024-04-20'),
(2, NULL, 'Masivo', 'Vacuna', 'IBR-DVB (Respiratoria)', 18.00, 1, 5, '2024-05-10'),
(2, NULL, 'Masivo', 'Vacuna', 'Leptospirosis', 14.00, 1, 5, '2024-06-05'),

-- Tratamientos masivos
(2, NULL, 'Masivo', 'Tratamiento', 'Desparasitante Ivermectina', 8.00, 1, 5, '2024-02-25'),
(2, NULL, 'Masivo', 'Tratamiento', 'Vitaminas ADE', 6.50, 1, 5, '2024-03-30'),
(2, NULL, 'Masivo', 'Tratamiento', 'Complejo B + Selenio', 7.00, 1, 5, '2024-05-15'),
(2, NULL, 'Masivo', 'Tratamiento', 'Desparasitante Albendazol', 9.00, 1, 5, '2024-07-10'),
(2, NULL, 'Masivo', 'Tratamiento', 'Suplemento Mineral', 5.50, 1, 5, '2024-08-20');

-- ============================================
-- APLICACIONES INDIVIDUALES (Lote 2)
-- Vacas del lote 2: IDs 10, 11, 12, 13, 14
-- ============================================
INSERT INTO tb_registro_sanitario (lote_id, animal_id, tipo_aplicacion, protocolo_tipo, nombre_producto, costo_por_dosis, cantidad_dosis, animales_tratados, fecha_aplicacion) VALUES
-- Tratamientos individuales - Mastitis
(NULL, 10, 'Individual', 'Tratamiento', 'Antibiótico Mastitis (Cefalexina)', 15.00, 3, NULL, '2024-04-15'),
(NULL, 11, 'Individual', 'Tratamiento', 'Antibiótico Mastitis (Gentamicina)', 18.00, 2, NULL, '2024-05-20'),
(NULL, 12, 'Individual', 'Tratamiento', 'Antibiótico Mastitis (Oxitetraciclina)', 16.00, 3, NULL, '2024-06-10'),

-- Tratamientos individuales - Vitaminas y suplementos
(NULL, 13, 'Individual', 'Tratamiento', 'Complejo Vitamínico B12', 8.00, 1, NULL, '2024-03-25'),
(NULL, 14, 'Individual', 'Tratamiento', 'Calcio + Fósforo (Ternera)', 12.00, 2, NULL, '2024-05-15'),
(NULL, 10, 'Individual', 'Tratamiento', 'Suero Energizante', 10.00, 1, NULL, '2024-07-05'),

-- Tratamientos individuales - Problemas digestivos
(NULL, 11, 'Individual', 'Tratamiento', 'Antibiótico Digestivo', 14.00, 2, NULL, '2024-04-30'),
(NULL, 12, 'Individual', 'Tratamiento', 'Probióticos Ruminales', 9.00, 3, NULL, '2024-06-20'),

-- Tratamientos individuales - Otros
(NULL, 13, 'Individual', 'Tratamiento', 'Antiinflamatorio (Flunixin)', 11.00, 2, NULL, '2024-05-05'),
(NULL, 10, 'Individual', 'Tratamiento', 'Antibiótico Respiratorio', 13.00, 3, NULL, '2024-08-15');
 
INSERT INTO tb_registro_sanitario (lote_id, animal_id, tipo_aplicacion, protocolo_tipo, nombre_producto, costo_por_dosis, cantidad_dosis, animales_tratados, fecha_aplicacion) VALUES
-- Tratamientos individuales - Otros
(2, 13, 'Individual', 'Tratamiento', 'Antiinflamatorio (Flunixin)', 11.00, 2, NULL, '2024-11-05'),
(2, 10, 'Individual', 'Tratamiento', 'Antibiótico Respiratorio', 13.00, 3, NULL, '2024-10-15');

-- ============================================
-- 11. REGISTRO DE PESO (Múltiples pesajes)
-- ============================================
INSERT INTO tb_registro_peso (animal_id, fecha_pesaje, peso_kg, ganancia_kg) VALUES
-- Bovino 001 (Lote 1) - 6 pesajes
(1, '2024-01-15', 280.50, NULL),
(1, '2024-02-15', 315.00, 34.50),
(1, '2024-03-15', 350.00, 35.00),
(1, '2024-04-15', 385.00, 35.00),
(1, '2024-05-15', 420.00, 35.00),
(1, '2024-06-15', 455.00, 35.00),

-- Bovino 002 (Lote 1) - 6 pesajes
(2, '2024-01-15', 275.00, NULL),
(2, '2024-02-15', 308.00, 33.00),
(2, '2024-03-15', 342.00, 34.00),
(2, '2024-04-15', 376.00, 34.00),
(2, '2024-05-15', 410.00, 34.00),
(2, '2024-06-15', 445.00, 35.00),

-- Bovino 003 (Lote 1) - 5 pesajes
(3, '2024-01-15', 285.00, NULL),
(3, '2024-02-15', 320.00, 35.00),
(3, '2024-03-15', 356.00, 36.00),
(3, '2024-04-15', 392.00, 36.00),
(3, '2024-05-15', 428.00, 36.00),

-- Vacas Lecheras (Lote 2) - 4 pesajes cada una
(10, '2024-02-01', 520.00, NULL),
(10, '2024-03-15', 525.00, 5.00),
(10, '2024-05-01', 530.00, 5.00),
(10, '2024-06-15', 535.00, 5.00),

(11, '2024-02-01', 510.00, NULL),
(11, '2024-03-15', 515.00, 5.00),
(11, '2024-05-01', 518.00, 3.00),
(11, '2024-06-15', 522.00, 4.00),

-- Porcinos (Lote 3) - 4 pesajes
(15, '2024-03-10', 45.00, NULL),
(15, '2024-04-10', 68.00, 23.00),
(15, '2024-05-10', 92.00, 24.00),
(15, '2024-06-10', 115.00, 23.00),

(16, '2024-03-10', 42.00, NULL),
(16, '2024-04-10', 65.00, 23.00),
(16, '2024-05-10', 88.00, 23.00),
(16, '2024-06-10', 110.00, 22.00),

-- Ovinos (Lote 5) - 7 pesajes
(21, '2023-12-20', 55.00, NULL),
(21, '2024-01-20', 58.00, 3.00),
(21, '2024-02-20', 61.00, 3.00),
(21, '2024-03-20', 64.00, 3.00),
(21, '2024-04-20', 67.00, 3.00),
(21, '2024-05-20', 70.00, 3.00),
(21, '2024-06-20', 73.00, 3.00),

(22, '2023-12-20', 52.00, NULL),
(22, '2024-01-20', 55.00, 3.00),
(22, '2024-02-20', 58.00, 3.00),
(22, '2024-03-20', 61.00, 3.00),
(22, '2024-04-20', 64.00, 3.00),
(22, '2024-05-20', 67.00, 3.00),
(22, '2024-06-20', 70.00, 3.00),

-- Novillos N1 (Lote 14 - Granja 4, Categoria 2) - 4 pesajes cada uno
(35, '2024-03-01', 310.00, NULL),
(35, '2024-04-01', 345.00, 35.00),
(35, '2024-05-01', 380.00, 35.00),
(35, '2024-06-01', 415.00, 35.00),

(36, '2024-03-01', 305.00, NULL),
(36, '2024-04-01', 340.00, 35.00),
(36, '2024-05-01', 375.00, 35.00),
(36, '2024-06-01', 410.00, 35.00),

(37, '2024-03-01', 315.00, NULL),
(37, '2024-04-01', 352.00, 37.00),
(37, '2024-05-01', 389.00, 37.00),
(37, '2024-06-01', 426.00, 37.00),

(38, '2024-03-01', 308.00, NULL),
(38, '2024-04-01', 343.00, 35.00),
(38, '2024-05-01', 378.00, 35.00),
(38, '2024-06-01', 413.00, 35.00),

(39, '2024-03-01', 312.00, NULL),
(39, '2024-04-01', 348.00, 36.00),
(39, '2024-05-01', 384.00, 36.00),
(39, '2024-06-01', 420.00, 36.00),

-- Cuyes (Lote 15) - 3 pesajes
(40, '2024-04-10', 0.80, NULL),
(40, '2024-05-10', 1.10, 0.30),
(40, '2024-06-10', 1.40, 0.30),

(41, '2024-04-10', 0.75, NULL),
(41, '2024-05-10', 1.05, 0.30),
(41, '2024-06-10', 1.35, 0.30);

-- ============================================
-- 12. REGISTRO DE PRODUCCIÓN (Leche y Huevos)
-- ============================================
INSERT INTO tb_registro_produccion (lote_id, fecha_registro, tipo_produccion, cantidad) VALUES
-- Vacas Lecheras (Lote 2) - Producción diaria por 4 meses
-- Febrero
(2, '2024-02-05', 'Leche', 45.50),
(2, '2024-02-10', 'Leche', 46.00),
(2, '2024-02-15', 'Leche', 46.50),
(2, '2024-02-20', 'Leche', 47.00),
(2, '2024-02-25', 'Leche', 47.50),
-- Marzo
(2, '2024-03-05', 'Leche', 48.00),
(2, '2024-03-10', 'Leche', 48.50),
(2, '2024-03-15', 'Leche', 49.00),
(2, '2024-03-20', 'Leche', 49.50),
(2, '2024-03-25', 'Leche', 50.00),
-- Abril
(2, '2024-04-05', 'Leche', 50.50),
(2, '2024-04-10', 'Leche', 51.00),
(2, '2024-04-15', 'Leche', 51.50),
(2, '2024-04-20', 'Leche', 52.00),
(2, '2024-04-25', 'Leche', 52.50),
-- Mayo
(2, '2024-05-05', 'Leche', 53.00),
(2, '2024-05-10', 'Leche', 53.50),
(2, '2024-05-15', 'Leche', 54.00),
(2, '2024-05-20', 'Leche', 54.50),
(2, '2024-05-25', 'Leche', 55.00),
-- Junio
(2, '2024-06-05', 'Leche', 55.50),
(2, '2024-06-10', 'Leche', 56.00),
(2, '2024-06-15', 'Leche', 56.50),
(2, '2024-06-20', 'Leche', 57.00),

-- Gallinas Ponedoras (Lote 9) - Producción diaria por 4 meses
-- Marzo
(9, '2024-03-20', 'Huevos', 240.00),
(9, '2024-03-25', 'Huevos', 245.00),
(9, '2024-03-30', 'Huevos', 250.00),
-- Abril
(9, '2024-04-05', 'Huevos', 252.00),
(9, '2024-04-10', 'Huevos', 255.00),
(9, '2024-04-15', 'Huevos', 258.00),
(9, '2024-04-20', 'Huevos', 260.00),
(9, '2024-04-25', 'Huevos', 262.00),
-- Mayo
(9, '2024-05-05', 'Huevos', 264.00),
(9, '2024-05-10', 'Huevos', 266.00),
(9, '2024-05-15', 'Huevos', 268.00),
(9, '2024-05-20', 'Huevos', 270.00),
(9, '2024-05-25', 'Huevos', 272.00),
-- Junio
(9, '2024-06-05', 'Huevos', 274.00),
(9, '2024-06-10', 'Huevos', 276.00),
(9, '2024-06-15', 'Huevos', 278.00),
(9, '2024-06-20', 'Huevos', 280.00);




-- ============================================
-- PRODUCCIÓN DE LECHE (Lote 2 - Vacas Lecheras)
-- Granja 1, desde Enero 2024 hasta Diciembre 2024
-- ============================================

INSERT INTO tb_registro_produccion (lote_id, fecha_registro, tipo_produccion, cantidad) VALUES
-- ENERO 2024 (15 registros)
(2, '2024-01-05', 'Leche', 42.50),
(2, '2024-01-08', 'Leche', 43.00),
(2, '2024-01-11', 'Leche', 43.50),
(2, '2024-01-14', 'Leche', 44.00),
(2, '2024-01-17', 'Leche', 44.50),
(2, '2024-01-20', 'Leche', 45.00),
(2, '2024-01-23', 'Leche', 45.50),
(2, '2024-01-26', 'Leche', 46.00),
(2, '2024-01-29', 'Leche', 46.50),

-- FEBRERO 2024 (YA EXISTENTES EN EL ARCHIVO ANTERIOR)
-- Aquí van los 5 que ya teníamos...

-- MARZO 2024 (continuación - más registros)
(2, '2024-03-01', 'Leche', 47.80),
(2, '2024-03-04', 'Leche', 48.20),
(2, '2024-03-07', 'Leche', 48.60),
(2, '2024-03-12', 'Leche', 49.20),
(2, '2024-03-17', 'Leche', 49.80),
(2, '2024-03-22', 'Leche', 50.20),
(2, '2024-03-27', 'Leche', 50.80),
(2, '2024-03-30', 'Leche', 51.00),

-- ABRIL 2024 (continuación - más registros)
(2, '2024-04-02', 'Leche', 51.20),
(2, '2024-04-07', 'Leche', 51.80),
(2, '2024-04-12', 'Leche', 52.20),
(2, '2024-04-17', 'Leche', 52.80),
(2, '2024-04-22', 'Leche', 53.20),
(2, '2024-04-27', 'Leche', 53.80),
(2, '2024-04-30', 'Leche', 54.00),

-- MAYO 2024 (continuación - más registros)
(2, '2024-05-02', 'Leche', 54.20),
(2, '2024-05-07', 'Leche', 54.80),
(2, '2024-05-12', 'Leche', 55.20),
(2, '2024-05-17', 'Leche', 55.80),
(2, '2024-05-22', 'Leche', 56.20),
(2, '2024-05-27', 'Leche', 56.80),
(2, '2024-05-30', 'Leche', 57.00),

-- JUNIO 2024 (continuación - más registros)
(2, '2024-06-02', 'Leche', 57.20),
(2, '2024-06-07', 'Leche', 57.80),
(2, '2024-06-12', 'Leche', 58.20),
(2, '2024-06-17', 'Leche', 58.80),
(2, '2024-06-22', 'Leche', 59.20),
(2, '2024-06-27', 'Leche', 59.80),
(2, '2024-06-30', 'Leche', 60.00),

-- JULIO 2024 (15 registros)
(2, '2024-07-03', 'Leche', 60.50),
(2, '2024-07-06', 'Leche', 61.00),
(2, '2024-07-09', 'Leche', 61.50),
(2, '2024-07-12', 'Leche', 62.00),
(2, '2024-07-15', 'Leche', 62.50),
(2, '2024-07-18', 'Leche', 63.00),
(2, '2024-07-21', 'Leche', 63.50),
(2, '2024-07-24', 'Leche', 64.00),
(2, '2024-07-27', 'Leche', 64.50),
(2, '2024-07-30', 'Leche', 65.00),

-- AGOSTO 2024 (15 registros)
(2, '2024-08-02', 'Leche', 65.50),
(2, '2024-08-05', 'Leche', 66.00),
(2, '2024-08-08', 'Leche', 66.50),
(2, '2024-08-11', 'Leche', 67.00),
(2, '2024-08-14', 'Leche', 67.50),
(2, '2024-08-17', 'Leche', 68.00),
(2, '2024-08-20', 'Leche', 68.50),
(2, '2024-08-23', 'Leche', 69.00),
(2, '2024-08-26', 'Leche', 69.50),
(2, '2024-08-29', 'Leche', 70.00),

-- SEPTIEMBRE 2024 (15 registros)
(2, '2024-09-01', 'Leche', 70.50),
(2, '2024-09-04', 'Leche', 71.00),
(2, '2024-09-07', 'Leche', 71.50),
(2, '2024-09-10', 'Leche', 72.00),
(2, '2024-09-13', 'Leche', 72.50),
(2, '2024-09-16', 'Leche', 73.00),
(2, '2024-09-19', 'Leche', 73.50),
(2, '2024-09-22', 'Leche', 74.00),
(2, '2024-09-25', 'Leche', 74.50),
(2, '2024-09-28', 'Leche', 75.00),

-- OCTUBRE 2024 (15 registros)
(2, '2024-10-01', 'Leche', 75.50),
(2, '2024-10-04', 'Leche', 76.00),
(2, '2024-10-07', 'Leche', 76.50),
(2, '2024-10-10', 'Leche', 77.00),
(2, '2024-10-13', 'Leche', 77.50),
(2, '2024-10-16', 'Leche', 78.00),
(2, '2024-10-19', 'Leche', 78.50),
(2, '2024-10-22', 'Leche', 79.00),
(2, '2024-10-25', 'Leche', 79.50),
(2, '2024-10-28', 'Leche', 80.00),
(2, '2024-10-31', 'Leche', 80.50),

-- NOVIEMBRE 2024 (15 registros)
(2, '2024-11-03', 'Leche', 81.00),
(2, '2024-11-06', 'Leche', 81.50),
(2, '2024-11-09', 'Leche', 82.00),
(2, '2024-11-12', 'Leche', 82.50),
(2, '2024-11-15', 'Leche', 83.00),
(2, '2024-11-18', 'Leche', 83.50),
(2, '2024-11-21', 'Leche', 84.00),
(2, '2024-11-24', 'Leche', 84.50),
(2, '2024-11-27', 'Leche', 85.00),
(2, '2024-11-30', 'Leche', 85.50),

-- DICIEMBRE 2024 (15 registros)
(2, '2024-12-03', 'Leche', 86.00),
(2, '2024-12-06', 'Leche', 86.50),
(2, '2024-12-09', 'Leche', 87.00),
(2, '2024-12-12', 'Leche', 87.50),
(2, '2024-12-15', 'Leche', 88.00),
(2, '2024-12-18', 'Leche', 88.50),
(2, '2024-12-21', 'Leche', 89.00),
(2, '2024-12-24', 'Leche', 89.50);


INSERT INTO tb_registro_produccion (lote_id, fecha_registro, tipo_produccion, cantidad) VALUES
(2, '2024-01-05', 'Huevos', 210.00),
(2, '2024-01-08', 'Huevos', 215.00),
(2, '2024-01-11', 'Huevos', 220.00),
(2, '2024-01-14', 'Huevos', 222.00),
(2, '2024-01-17', 'Huevos', 225.00),
(2, '2024-01-20', 'Huevos', 228.00),
(2, '2024-01-23', 'Huevos', 230.00),
(2, '2024-01-26', 'Huevos', 232.00),
(2, '2024-01-29', 'Huevos', 235.00);


-- ============================================
-- PRODUCCIÓN DE HUEVOS (Lote 9 - Ponedoras)
-- Granja 3, desde Enero 2024 hasta Diciembre 2024
-- ============================================
INSERT INTO tb_registro_produccion (lote_id, fecha_registro, tipo_produccion, cantidad) VALUES
-- ENERO 2024 (12 registros)
(9, '2024-01-05', 'Huevos', 210.00),
(9, '2024-01-08', 'Huevos', 215.00),
(9, '2024-01-11', 'Huevos', 220.00),
(9, '2024-01-14', 'Huevos', 222.00),
(9, '2024-01-17', 'Huevos', 225.00),
(9, '2024-01-20', 'Huevos', 228.00),
(9, '2024-01-23', 'Huevos', 230.00),
(9, '2024-01-26', 'Huevos', 232.00),
(9, '2024-01-29', 'Huevos', 235.00),



-- FEBRERO 2024 (12 registros)
(9, '2024-02-01', 'Huevos', 238.00),
(9, '2024-02-04', 'Huevos', 240.00),
(9, '2024-02-07', 'Huevos', 242.00),
(9, '2024-02-10', 'Huevos', 245.00),
(9, '2024-02-13', 'Huevos', 247.00),
(9, '2024-02-16', 'Huevos', 250.00),
(9, '2024-02-19', 'Huevos', 252.00),
(9, '2024-02-22', 'Huevos', 255.00),
(9, '2024-02-25', 'Huevos', 257.00),
(9, '2024-02-28', 'Huevos', 260.00),

-- MARZO 2024 (continuación - más registros)
(9, '2024-03-02', 'Huevos', 262.00),
(9, '2024-03-05', 'Huevos', 264.00),
(9, '2024-03-08', 'Huevos', 266.00),
(9, '2024-03-11', 'Huevos', 268.00),
(9, '2024-03-14', 'Huevos', 270.00),
(9, '2024-03-17', 'Huevos', 272.00),
(9, '2024-03-22', 'Huevos', 274.00),
(9, '2024-03-27', 'Huevos', 276.00),
(9, '2024-03-31', 'Huevos', 278.00),

-- ABRIL 2024 (continuación)
(9, '2024-04-03', 'Huevos', 280.00),
(9, '2024-04-08', 'Huevos', 282.00),
(9, '2024-04-13', 'Huevos', 284.00),
(9, '2024-04-18', 'Huevos', 286.00),
(9, '2024-04-23', 'Huevos', 288.00),
(9, '2024-04-28', 'Huevos', 290.00),
(9, '2024-04-30', 'Huevos', 292.00),

-- MAYO 2024 (continuación)
(9, '2024-05-03', 'Huevos', 293.00),
(9, '2024-05-08', 'Huevos', 294.00),
(9, '2024-05-13', 'Huevos', 295.00),
(9, '2024-05-18', 'Huevos', 296.00),
(9, '2024-05-23', 'Huevos', 297.00),
(9, '2024-05-28', 'Huevos', 298.00),
(9, '2024-05-31', 'Huevos', 299.00),

-- JUNIO 2024 (continuación)
(9, '2024-06-03', 'Huevos', 300.00),
(9, '2024-06-08', 'Huevos', 301.00),
(9, '2024-06-13', 'Huevos', 302.00),
(9, '2024-06-18', 'Huevos', 303.00),
(9, '2024-06-23', 'Huevos', 304.00),
(9, '2024-06-28', 'Huevos', 305.00),
(9, '2024-06-30', 'Huevos', 305.50),

-- JULIO 2024 (12 registros)
(9, '2024-07-03', 'Huevos', 306.00),
(9, '2024-07-06', 'Huevos', 307.00),
(9, '2024-07-09', 'Huevos', 308.00),
(9, '2024-07-12', 'Huevos', 309.00),
(9, '2024-07-15', 'Huevos', 310.00),
(9, '2024-07-18', 'Huevos', 310.50),
(9, '2024-07-21', 'Huevos', 311.00),
(9, '2024-07-24', 'Huevos', 311.50),
(9, '2024-07-27', 'Huevos', 312.00),
(9, '2024-07-30', 'Huevos', 312.50),

-- AGOSTO 2024 (12 registros)
(9, '2024-08-02', 'Huevos', 313.00),
(9, '2024-08-05', 'Huevos', 313.50),
(9, '2024-08-08', 'Huevos', 314.00),
(9, '2024-08-11', 'Huevos', 314.50),
(9, '2024-08-14', 'Huevos', 315.00),
(9, '2024-08-17', 'Huevos', 315.50),
(9, '2024-08-20', 'Huevos', 316.00),
(9, '2024-08-23', 'Huevos', 316.50),
(9, '2024-08-26', 'Huevos', 317.00),
(9, '2024-08-29', 'Huevos', 317.50),

-- SEPTIEMBRE 2024 (12 registros) - Ligera baja
(9, '2024-09-01', 'Huevos', 318.00),
(9, '2024-09-04', 'Huevos', 317.00),
(9, '2024-09-07', 'Huevos', 316.00),
(9, '2024-09-10', 'Huevos', 315.50),
(9, '2024-09-13', 'Huevos', 315.00),
(9, '2024-09-16', 'Huevos', 314.50),
(9, '2024-09-19', 'Huevos', 314.00),
(9, '2024-09-22', 'Huevos', 313.50),
(9, '2024-09-25', 'Huevos', 313.00),
(9, '2024-09-28', 'Huevos', 312.50),

-- OCTUBRE 2024 (12 registros) - Recuperación
(9, '2024-10-01', 'Huevos', 313.00),
(9, '2024-10-04', 'Huevos', 314.00),
(9, '2024-10-07', 'Huevos', 315.00),
(9, '2024-10-10', 'Huevos', 316.00),
(9, '2024-10-13', 'Huevos', 317.00),
(9, '2024-10-16', 'Huevos', 318.00),
(9, '2024-10-19', 'Huevos', 319.00),
(9, '2024-10-22', 'Huevos', 320.00),
(9, '2024-10-25', 'Huevos', 321.00),
(9, '2024-10-28', 'Huevos', 322.00),
(9, '2024-10-31', 'Huevos', 323.00),

-- NOVIEMBRE 2024 (12 registros)
(9, '2024-11-03', 'Huevos', 324.00),
(9, '2024-11-06', 'Huevos', 325.00),
(9, '2024-11-09', 'Huevos', 326.00),
(9, '2024-11-12', 'Huevos', 327.00),
(9, '2024-11-15', 'Huevos', 328.00),
(9, '2024-11-18', 'Huevos', 329.00),
(9, '2024-11-21', 'Huevos', 330.00),
(9, '2024-11-24', 'Huevos', 331.00),
(9, '2024-11-27', 'Huevos', 332.00),
(9, '2024-11-30', 'Huevos', 333.00),

-- DICIEMBRE 2024 (12 registros)
(9, '2024-12-03', 'Huevos', 334.00),
(9, '2024-12-06', 'Huevos', 335.00),
(9, '2024-12-09', 'Huevos', 336.00),
(9, '2024-12-12', 'Huevos', 337.00),
(9, '2024-12-15', 'Huevos', 338.00),
(9, '2024-12-18', 'Huevos', 339.00),
(9, '2024-12-21', 'Huevos', 340.00),
(9, '2024-12-24', 'Huevos', 341.00);


-- ============================================
-- 13. REGISTRO DE MOVILIDAD
-- ============================================
INSERT INTO tb_registro_movilidad (animal_id, lote_origen_id, lote_destino_id, fecha_movimiento, motivo) VALUES
(28, 5, 6, '2024-05-20', 'Oveja preñada a lote de corderos'),
(14, 2, 4, '2024-06-10', 'Ternera destetada a enfermería'),
(25, 5, 7, '2024-04-15', 'Ovino a lote de reproductores');

-- ============================================
-- 14. REGISTRO DE MUERTE
-- ============================================
INSERT INTO tb_registro_muerte (animal_id, lote_id, fecha_muerte, causa_muerte) VALUES
(19, 3, '2024-05-25', 'Complicaciones digestivas agudas'),
(44, 15, '2024-06-08', 'Causa desconocida - cuy lactante');

-- ============================================
-- 15. REGISTRO DE VENTAS
-- ============================================
INSERT INTO tb_registro_venta (lote_id, tipo_alcance_venta, tipo_venta, peso_total_kg, precio_por_kg, precio_total, roi_estimado, roi_meta, cliente_nombre, fecha_venta, animales_vendidos_ids) VALUES
(1, 'Parcial', 'Engorde', 392.00, 18.50, 7252.00, 28.50, 25.00, 'Frigorífico del Centro SAC', '2024-05-20', ARRAY[4]),
(3, 'Parcial', 'Engorde', 110.00, 12.00, 1320.00, 22.00, 20.00, 'Mercado de Carnes', '2024-06-18', ARRAY[19]),
(8, 'Total', 'Engorde', 1200.00, 8.50, 10200.00, 35.00, 30.00, 'Distribuidora Avícola', '2024-06-25', NULL),
(13, 'Total', 'Descarte', 420.00, 12.00, 5040.00, 15.50, 15.00, 'Procesadora del Sur', '2024-06-01', NULL);


-- ============================================
-- 16. TIPOS DE NOTIFICACIÓN
-- ============================================
INSERT INTO tb_tipos_notificacion (codigo, descripcion) VALUES
('VACUNA_PROXIMA', 'Vacunación próxima a vencer'),
('PESO_META', 'Animal alcanzó peso meta'),
('PRODUCCION_BAJA', 'Producción por debajo del promedio'),
('ANIMAL_ENFERMO', 'Posible animal enfermo detectado'),
('VENTA_COMPLETADA', 'Venta registrada exitosamente'),
('MORTALIDAD', 'Registro de muerte de animal'),
('CAPACIDAD_LOTE', 'Lote cerca de capacidad máxima'),
('ALIMENTACION_PENDIENTE', 'Registro de alimentación pendiente');

-- ============================================
-- 17. NOTIFICACIONES
-- ============================================
INSERT INTO tb_notificaciones (granja_id, usuario_id, tipo_notificacion_id, titulo, mensaje, lote_id, animal_id, venta_id, fecha_creacion, leida, descartada) VALUES
(1, 1, 1, 'Vacunación Pendiente', 'El lote Bovino A1 requiere refuerzo de vacuna', 1, NULL, NULL, '2024-06-10', FALSE, FALSE),
(1, 1, 2, 'Peso Objetivo', 'El animal QR_BOV_001 alcanzó 455 kg', 1, 1, NULL, '2024-06-15', TRUE, FALSE),
(1, 1, 5, 'Venta Registrada', 'Venta completada por S/ 7,252.00', 1, NULL, 1, '2024-05-20', TRUE, FALSE),
(2, 2, 3, 'Producción Baja', 'Producción de lote O1 bajo promedio', 5, NULL, NULL, '2024-06-01', FALSE, FALSE),
(4, 4, 2, 'Peso Meta Alcanzado', 'Novillos del lote N1 listos para venta', 14, NULL, NULL, '2024-06-01', TRUE, FALSE),
(3, 2, 5, 'Venta Total', 'Lote PE1 vendido completamente', 8, NULL, 3, '2024-06-25', TRUE, FALSE),
(5, 7, 7, 'Capacidad Máxima', 'Lote CU1 al 95% de capacidad', 15, NULL, NULL, '2024-06-15', FALSE, FALSE),
(1, 1, 8, 'Alimentación', 'Registrar alimentación del lote B1', 2, NULL, NULL, '2024-06-20', FALSE, FALSE);

-- ============================================
-- FIN DE INSERTS
-- ============================================

