-- PERSONAS
CREATE TABLE personas (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255),
    genero VARCHAR(50),
    fecha DATE,
    edad INT,
    identificacion VARCHAR(50) UNIQUE,
    direccion VARCHAR(255),
    telefono VARCHAR(50)
);

-- CLIENTES
CREATE TABLE clientes (
    id SERIAL,
    contrasena VARCHAR(255),
    estado BOOLEAN,
    PRIMARY KEY (id),
    CONSTRAINT fk_client_person FOREIGN KEY (id) REFERENCES personas(id)
);

-- Insertar datos en la tabla personas
INSERT INTO personas (nombre, genero, fecha, edad, identificacion, direccion, telefono) VALUES
('Sebastián', 'Masculino', '2024-05-16', 24, '1724022437', 'Vicentina', '0979317536'),
('Jose Lema', 'Masculino', '2024-05-15', 28, '1756384920', 'La Carolina', '0987654321'),
('Maria Montalvo', 'Femenino', '2024-05-16', 35, '1702947583', 'Amazonas y NNUU ', '0998765432'),
('Juan Osorio', 'Femenino', '2024-05-16', 32, '1719483726', '13 junio y Equinoccial', '0965432187');

-- Insertar datos en la tabla clientes
INSERT INTO clientes (id, contrasena, estado) VALUES
((SELECT id FROM personas WHERE identificacion = '1724022437'), '123456', true),
((SELECT id FROM personas WHERE identificacion = '1756384920'), 'abcdef', true),
((SELECT id FROM personas WHERE identificacion = '1702947583'), 'password', true),
((SELECT id FROM personas WHERE identificacion = '1719483726'), 'mypassword', true);
