CREATE TABLE tb_rol(
	rol_id SERIAL PRIMARY KEY,
	descripcion VARCHAR(20)
);

CREATE TABLE tb_usuarios (
    usuario_id SERIAL PRIMARY KEY,
    nombres VARCHAR(30) NOT NULL,
    ape_pat VARCHAR(30) NOT NULL,
    ape_mat VARCHAR(30) NOT NULL,
    documento VARCHAR(10) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,
    contra VARCHAR(150) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    firebase_uid VARCHAR(128),
	imagen_url TEXT null,
    rol_id INT REFERENCES tb_rol(rol_id),
    fecha_registro TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    fcm_token VARCHAR(500) NULL,
    fcm_token_fecha TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tb_granjas (
    granja_id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL REFERENCES tb_usuarios(usuario_id) ON DELETE CASCADE,
    nombre VARCHAR(150) NOT NULL,
    direccion TEXT,
    latitud DECIMAL(10, 8),
    longitud DECIMAL(11, 8),
	imagen_url TEXT null
);

CREATE TABLE tb_especies (
    especie_id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE tb_categorias_manejo (
    categoria_id SERIAL PRIMARY KEY,
    especie_id INT REFERENCES tb_especies(especie_id),
    nombre VARCHAR(100) NOT NULL,
    tipo_lote VARCHAR(50) NOT NULL CHECK (tipo_lote IN ('Engorde', 'Reproduccion', 'Enfermeria', 'Descarte')),
    dieta_recomendada VARCHAR(200),
    UNIQUE (especie_id, nombre)
);

CREATE TABLE tb_lotes (
    lote_id SERIAL PRIMARY KEY,
	lote_qr VARCHAR(200) NOT NULL,
    granja_id INT NOT NULL REFERENCES tb_granjas(granja_id) ON DELETE CASCADE,
    nombre VARCHAR(100) NOT NULL,
    especie_id INT NOT NULL REFERENCES tb_especies(especie_id),
    categoria_id INT REFERENCES tb_categorias_manejo(categoria_id),
    fecha_creacion TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) NOT NULL DEFAULT 'Activo' CHECK (estado IN ('Activo', 'Inactivo')),
    capacidad_max INT
);

CREATE TABLE tb_animales (
    animal_id SERIAL PRIMARY KEY,
    animal_qr VARCHAR(50) NOT NULL UNIQUE,
    especie_id INT NOT NULL REFERENCES tb_especies(especie_id),
    lote_id INT NOT NULL REFERENCES tb_lotes(lote_id),
    madre_id INT REFERENCES tb_animales(animal_id),
    origen VARCHAR(20) NOT NULL CHECK (origen IN ('Compra', 'Nacimiento')),
	sexo CHAR(1) NOT NULL CHECK (sexo IN ('F', 'M')),
    fecha_ingreso TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	fecha_nacimiento TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    peso DECIMAL(10, 2),
    precio_compra DECIMAL(10, 2),
    estado VARCHAR(20) NOT NULL DEFAULT 'Vivo' CHECK (estado IN ('Vivo', 'Vendido', 'Muerto')),
    foto_url TEXT null
);

-- COMPRAS (Ingreso de animales)
CREATE TABLE tb_registro_compra (
    compra_id SERIAL PRIMARY KEY,
    lote_id INT NOT NULL REFERENCES tb_lotes(lote_id) ON DELETE CASCADE,
    proveedor_nombre VARCHAR(150) NOT NULL,
    fecha_compra TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    cantidad_animales INT NOT NULL,
    costo_total DECIMAL(10, 2) NOT NULL,
    observaciones TEXT
);

-- ALIMENTACIÓN (Registro de consumo por lote)
CREATE TABLE tb_registro_alimentacion (
    alimentacion_id SERIAL PRIMARY KEY,
    lote_id INT NOT NULL REFERENCES tb_lotes(lote_id) ON DELETE CASCADE,
    fecha_registro TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    cantidad_kg DECIMAL(10, 2) NOT NULL,
    costo_por_kg DECIMAL(10, 4) NOT NULL,
    dieta_tipo VARCHAR(100)
);

-- SANIDAD (Consolidado: Individual y Lote)
CREATE TABLE tb_registro_sanitario (
    sanitario_id SERIAL PRIMARY KEY,
    lote_id INT REFERENCES tb_lotes(lote_id) ON DELETE CASCADE,
    animal_id INT REFERENCES tb_animales(animal_id) ON DELETE CASCADE,
    tipo_aplicacion VARCHAR(20) NOT NULL CHECK (tipo_aplicacion IN ('Individual', 'Masivo')),
    protocolo_tipo VARCHAR(50) NOT NULL CHECK (protocolo_tipo IN ('Vacuna', 'Tratamiento')),
    nombre_producto VARCHAR(100) NOT NULL,
    costo_por_dosis DECIMAL(10, 4) NOT NULL,
    cantidad_dosis DECIMAL(10, 2) DEFAULT 1,
    animales_tratados INT,
    fecha_aplicacion TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT chk_aplicacion_valida CHECK (
        (tipo_aplicacion = 'Individual' AND animal_id IS NOT NULL AND animales_tratados IS NULL) OR
        (tipo_aplicacion = 'Masivo' AND lote_id IS NOT NULL AND animales_tratados IS NOT NULL)
    )
);

-- PESAJE (Control de peso individual)
CREATE TABLE tb_registro_peso (
    peso_id SERIAL PRIMARY KEY,
    animal_id INT NOT NULL REFERENCES tb_animales(animal_id) ON DELETE CASCADE,
    fecha_pesaje TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    peso_kg DECIMAL(10, 2) NOT NULL,
    ganancia_kg DECIMAL(10, 2)
);

-- PRODUCCIÓN (Leche/Huevos por lote)
CREATE TABLE tb_registro_produccion (
    produccion_id SERIAL PRIMARY KEY,
    lote_id INT NOT NULL REFERENCES tb_lotes(lote_id) ON DELETE CASCADE,
    fecha_registro TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    tipo_produccion VARCHAR(20) NOT NULL CHECK (tipo_produccion IN ('Leche', 'Huevos')),
    cantidad DECIMAL(10, 2) NOT NULL
);

-- MOVILIDAD (Traslados entre lotes - INDIVIDUAL)
CREATE TABLE tb_registro_movilidad (
    movilidad_id SERIAL PRIMARY KEY,
    animal_id INT NOT NULL REFERENCES tb_animales(animal_id) ON DELETE CASCADE,
    lote_origen_id INT NOT NULL REFERENCES tb_lotes(lote_id),
    lote_destino_id INT NOT NULL REFERENCES tb_lotes(lote_id),
    fecha_movimiento TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    motivo VARCHAR(100)
);

CREATE TABLE tb_registro_muerte (
    muerte_id SERIAL PRIMARY KEY,
    animal_id INT NOT NULL UNIQUE REFERENCES tb_animales(animal_id) ON DELETE CASCADE,
    lote_id INT REFERENCES tb_lotes(lote_id),
    fecha_muerte TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    causa_muerte TEXT NOT NULL,
    peso_al_morir DECIMAL(10, 2)
);

CREATE TABLE tb_registro_venta (
    venta_id SERIAL PRIMARY KEY,
    lote_id INT REFERENCES tb_lotes(lote_id),
	tipo_alcance_venta VARCHAR(10) CHECK (tipo_alcance_venta IN ('Total', 'Parcial')) NOT NULL;
    tipo_venta VARCHAR(30) NOT NULL CHECK (tipo_venta IN ('Engorde', 'Reproduccion', 'Descarte')),
    peso_total_kg DECIMAL(10, 2),
    precio_por_kg DECIMAL(10, 2),
    precio_total DECIMAL(10, 2) NOT NULL,
    roi_estimado DECIMAL(5, 2),
    cliente_nombre VARCHAR(100),
    fecha_venta TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    animales_vendidos_ids INT[]
);

CREATE TABLE tb_tipos_notificacion (
    tipo_notificacion_id SERIAL PRIMARY KEY,
    codigo VARCHAR(30) NOT NULL UNIQUE,
    descripcion VARCHAR(100) NOT NULL
);

CREATE TABLE tb_notificaciones (
    notificacion_id SERIAL PRIMARY KEY,
    granja_id INT NOT NULL
        REFERENCES tb_granjas(granja_id)
        ON DELETE CASCADE,
    usuario_id INT NOT NULL
        REFERENCES tb_usuarios(usuario_id)
        ON DELETE CASCADE,
    tipo_notificacion_id INT NOT NULL
        REFERENCES tb_tipos_notificacion(tipo_notificacion_id),
    titulo VARCHAR(100) NOT NULL,
    mensaje TEXT NOT NULL,
    lote_id INT NULL
        REFERENCES tb_lotes(lote_id),
    animal_id INT NULL
        REFERENCES tb_animales(animal_id),
    venta_id INT NULL
        REFERENCES tb_registro_venta(venta_id),
    fecha_creacion TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    leida BOOLEAN DEFAULT FALSE,
    descartada BOOLEAN DEFAULT FALSE
);

