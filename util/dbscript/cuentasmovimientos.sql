-- CUENTAS
CREATE TABLE IF NOT EXISTS cuentas (
    id BIGSERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(255) UNIQUE,
    tipo_cuenta VARCHAR(255),
    fecha timestamp,
    saldo_inicial DOUBLE PRECISION,
    estado BOOLEAN,
    id_cliente VARCHAR(255)
);

-- MOVIMIENTOS
CREATE TABLE IF NOT EXISTS movimientos (
    id BIGSERIAL PRIMARY KEY,
    fecha timestamp,
    tipo_movimiento VARCHAR(255),
    valor DOUBLE PRECISION,
    saldo DOUBLE PRECISION,
    cuenta_id BIGINT,
    FOREIGN KEY (cuenta_id) REFERENCES cuentas(id)
);

-- Insertar datos en la tabla cuentas
INSERT INTO cuentas (numero_cuenta, tipo_cuenta, fecha, saldo_inicial, estado, id_cliente) VALUES
('123456', 'Corriente', '2024-06-09 17:06:01.816', 1000.00, true, '1'),
('234567', 'Ahorros', '2024-06-09 17:06:01.816', 2000.00, true, '2'),
('345678', 'Corriente', '2024-06-09 17:06:01.816', 3000.00, true, '3'),
('456789', 'Ahorros', '2024-06-09 17:06:01.816', 4000.00, true, '4');

-- Insertar datos en la tabla movimientos
INSERT INTO movimientos (fecha, tipo_movimiento, valor, saldo, cuenta_id) VALUES
('2024-06-09 17:06:01.816', 'DEPOSITO', 500.00, 1500.00, 1),
('2024-06-09 17:06:01.816', 'RETIRO', 200.00, 1800.00, 2),
('2024-06-09 17:06:01.816', 'DEPOSITO', 1000.00, 4000.00, 3),
('2024-06-09 17:06:01.816', 'DEPOSITO', 500.00, 4500.00, 4);
