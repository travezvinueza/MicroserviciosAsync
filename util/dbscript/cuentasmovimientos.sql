-- CUENTAS
CREATE TABLE IF NOT EXISTS accounts (
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(255) UNIQUE,
    account_type varchar(20) NULL,
    date timestamp,
    initial_balance DOUBLE PRECISION,
    state BOOLEAN,
    id_client VARCHAR(255)
);

-- MOVIMIENTOS
CREATE TABLE IF NOT EXISTS motions (
    id BIGSERIAL PRIMARY KEY,
    date timestamp,
    transaction_type varchar(20) NULL,
    valor DOUBLE PRECISION,
    saldo DOUBLE PRECISION,
    account_id BIGINT,
    id_client VARCHAR(255),
    FOREIGN KEY (account_id) REFERENCES accounts(id)
);

-- Insertar datos en la tabla accounts
INSERT INTO accounts (account_number, account_type, date, initial_balance, state, id_client) VALUES
('222222', 'CORRIENTE', '2024-10-22 15:06:01.816', 1000.00, true, '1'),
('333333', 'AHORROS', '2024-10-22 15:06:01.816', 2000.00, true, '2'),
('444444', 'CORRIENTE', '2024-10-22 15:06:01.816', 3000.00, true, '3'),
('555555', 'AHORROS', '2024-10-22 15:06:01.816', 4000.00, true, '4');

-- Insertar datos en la tabla motions
INSERT INTO motions (date, transaction_type, valor, saldo, account_id, id_client) VALUES
('2024-10-22 20:06:01.816', 'DEPOSITO', 500.00, 1500.00, 1, 1),
('2024-10-22 20:06:01.816', 'RETIRO', 200.00, 1800.00, 2, 2),
('2024-10-22 20:06:01.816', 'DEPOSITO', 1000.00, 4000.00, 3, 3),
('2024-10-22 20:06:01.816', 'DEPOSITO', 500.00, 4500.00, 4, 4);
