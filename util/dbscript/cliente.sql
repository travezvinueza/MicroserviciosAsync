-- PERSONAS
CREATE TABLE persons (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    gender_person VARCHAR(50),
    date DATE,
    age INT,
    identification_number VARCHAR(50) UNIQUE,
    address VARCHAR(255),
    phone VARCHAR(50)
);

-- CLIENTES
CREATE TABLE clients (
    id SERIAL,
    password VARCHAR(255),
    state BOOLEAN,
    PRIMARY KEY (id),
    CONSTRAINT fk_client_person FOREIGN KEY (id) REFERENCES persons(id)
);

-- Insertar datos en la tabla personas
INSERT INTO persons (name, gender_person, date, age, identification_number, address, phone) VALUES
('Sebastián', 'MASCULINO', '2024-10-21', 24, '1724022437', 'Vicentina', '0979317536'),
('Jose Lema', 'MASCULINO', '2024-10-21', 28, '1756384920', 'La Carolina', '0987654321'),
('Maria Montalvo', 'FEMENINO', '2024-10-21', 35, '1702947583', 'Amazonas y NNUU ', '0998765432'),
('Juan Osorio', 'FEMENINO', '2024-10-21', 32, '1719483726', '13 junio y Equinoccial', '0965432187');

-- Insertar datos en la tabla clientes
INSERT INTO clients (id, password, state) VALUES
((SELECT id FROM persons WHERE identification_number = '1724022437'), '$2a$12$yBbYR9h7WCL4lGGLQBuxMeGKE1qyIKtJ.37nGEltGHozl7kiFVZT.', true),
((SELECT id FROM persons WHERE identification_number = '1756384920'), '$2a$12$iueJQu0H.odxzAydvNheQ.2a1XQNprJd6Ds4uC1ULh5LeGyeVLoju', true),
((SELECT id FROM persons WHERE identification_number = '1702947583'), '$2a$12$fItExTIb9l2wfWI3n/ze6OKcC8r5rZHIkzj0rLchnIMqT/VcIVq4a', true),
((SELECT id FROM persons WHERE identification_number = '1719483726'), '$2a$12$/Ce6eXZvPQJ/7gym3QK.kudi0YU4VCvk3jfHTgLT/aECXt/4UnaMi', true);
