INSERT INTO tb_rol (descripcion) VALUES
('Administrador'),
('Productor'),
('Veterinario');

/*INSERT INTO tb_usuarios eliminado por motivo de que se necesita el campo uuid
(nombres, ape_pat, ape_mat, documento, email, contra, telefono, imagen_url, rol_id)
VALUES
('Carlos', 'Ramirez', 'Lopez', '12345678', 'carlos.admin@example.com', 'pass123', '987654321', NULL, 1),  -- Administrador


('María', 'Torres', 'Sanchez', '87654321', 'maria.productor@example.com', 'pass456', '912345678', NULL, 2), -- Productor

('Luis', 'Fernandez', 'Gomez', '55667788', 'luis.vete@example.com', 'pass789', '999888777', NULL, 3);     -- Veterinario
*/
INSERT INTO tb_especies (nombre) VALUES
('Vacuno'),
('Porcino'),
('Caprino'),
('Pavino'),
('Avícola');

-- VACUNO
INSERT INTO tb_categorias_manejo (especie_id, nombre, tipo_lote, dieta_recomendada) VALUES
(1, 'Terneros Lactantes', 'Reproduccion', 'Leche materna + concentrado iniciador'),
(1, 'Terneros Destetados', 'Engorde', 'Concentrado + forraje verde'),
(1, 'Novillos Engorde', 'Engorde', 'Forraje + concentrado alto en energía'),
(1, 'Vacas Lecheras', 'Reproduccion', 'Forraje + concentrado proteico'),
(1, 'Toros Reproductores', 'Reproduccion', 'Forraje + suplemento mineral'),
(1, 'Animales Enfermos Vacuno', 'Enfermeria', 'Dieta especial según diagnóstico'),
(1, 'Vacas Descarte', 'Descarte', 'Forraje básico');

-- PORCINO
INSERT INTO tb_categorias_manejo (especie_id, nombre, tipo_lote, dieta_recomendada) VALUES
(2, 'Lechones Pre-Destete', 'Reproduccion', 'Leche materna + pre-iniciador'),
(2, 'Lechones Destetados', 'Engorde', 'Alimento iniciador'),
(2, 'Cerdos Crecimiento', 'Engorde', 'Alimento crecimiento 16% proteína'),
(2, 'Cerdos Finalización', 'Engorde', 'Alimento finalizador 14% proteína'),
(2, 'Cerdas Gestantes', 'Reproduccion', 'Alimento gestación'),
(2, 'Cerdas Lactantes', 'Reproduccion', 'Alimento lactancia alto en energía'),
(2, 'Verracos', 'Reproduccion', 'Alimento mantenimiento + mineral'),
(2, 'Animales Enfermos Porcino', 'Enfermeria', 'Dieta especial según diagnóstico'),
(2, 'Cerdos Descarte', 'Descarte', 'Alimento básico');

-- CAPRINO
INSERT INTO tb_categorias_manejo (especie_id, nombre, tipo_lote, dieta_recomendada) VALUES
(3, 'Cabritos Lactantes', 'Reproduccion', 'Leche materna + heno tierno'),
(3, 'Cabritos Destetados', 'Engorde', 'Concentrado + forraje'),
(3, 'Cabras Lecheras', 'Reproduccion', 'Forraje + concentrado proteico'),
(3, 'Machos Reproductores', 'Reproduccion', 'Forraje + suplemento mineral'),
(3, 'Animales Enfermos Caprino', 'Enfermeria', 'Dieta especial según diagnóstico'),
(3, 'Cabras Descarte', 'Descarte', 'Forraje básico');

-- PAVINO
INSERT INTO tb_categorias_manejo (especie_id, nombre, tipo_lote, dieta_recomendada) VALUES
(4, 'Pavitos Lactantes', 'Reproduccion', 'Alimento iniciador 28% proteína'),
(4, 'Pavos Engorde', 'Engorde', 'Alimento finalizador 20% proteína'),
(4, 'Pavas Reproductoras', 'Reproduccion', 'Alimento reproducción + calcio'),
(4, 'Pavos Reproductores', 'Reproduccion', 'Alimento reproductor'),
(4, 'Animales Enfermos Pavino', 'Enfermeria', 'Dieta especial según diagnóstico'),
(4, 'Pavos Descarte', 'Descarte', 'Alimento básico');

-- AVÍCOLA
INSERT INTO tb_categorias_manejo (especie_id, nombre, tipo_lote, dieta_recomendada) VALUES
(5, 'Pollitos BB', 'Reproduccion', 'Alimento iniciador 21% proteína'),
(5, 'Pollos Engorde', 'Engorde', 'Alimento finalizador 18% proteína'),
(5, 'Gallinas Ponedoras', 'Reproduccion', 'Alimento postura + calcio'),
(5, 'Gallos Reproductores', 'Reproduccion', 'Alimento reproductor'),
(5, 'Animales Enfermos Avícola', 'Enfermeria', 'Dieta especial según diagnóstico'),
(5, 'Aves Descarte', 'Descarte', 'Alimento básico');

INSERT INTO tb_tipos_notificacion (codigo, descripcion) VALUES
('MUERTE_ANIMAL', 'Notificación de muerte de animal'),
('ENFERMERIA_SATURADA', 'Lote de enfermería con alta ocupación'),
('MORTALIDAD_ALTA', 'Tasa de mortalidad elevada en lote'),
('ANIMAL_BAJO_PESO', 'Animal con ganancia de peso insuficiente'),
('VENTA_COMPLETADA', 'Venta registrada exitosamente'),
('NACIMIENTO', 'Nuevo nacimiento registrado'),
('PRODUCCION_BAJA', 'Producción por debajo del promedio'),
('LOTE_LISTO_VENTA', 'Lote alcanzó peso óptimo para venta');
