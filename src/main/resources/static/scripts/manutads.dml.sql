INSERT INTO "user" (email, name, surname, password, profile, active) VALUES
('john.doe@example.com', 'John', 'Doe', 'BDQ9tbVQhT1svQZpX4A7xlFNWB31dKGl2j7ImeVLJSA=:vb1c/YJqOhbm4S+5jnAp5g==', 'EMPLOYEE', TRUE),
('jane.smith@example.com', 'Jane', 'Smith', 'BDQ9tbVQhT1svQZpX4A7xlFNWB31dKGl2j7ImeVLJSA=:vb1c/YJqOhbm4S+5jnAp5g==', 'EMPLOYEE', TRUE),
('alice.jones@example.com', 'Alice', 'Jones', 'BDQ9tbVQhT1svQZpX4A7xlFNWB31dKGl2j7ImeVLJSA=:vb1c/YJqOhbm4S+5jnAp5g==', 'EMPLOYEE', TRUE),
('bob.brown@example.com', 'Bob', 'Brown', 'BDQ9tbVQhT1svQZpX4A7xlFNWB31dKGl2j7ImeVLJSA=:vb1c/YJqOhbm4S+5jnAp5g==', 'EMPLOYEE', TRUE),
('charlie.davis@example.com', 'Charlie', 'Davis', 'BDQ9tbVQhT1svQZpX4A7xlFNWB31dKGl2j7ImeVLJSA=:vb1c/YJqOhbm4S+5jnAp5g==', 'EMPLOYEE', TRUE),
('michael.jordan@example.com', 'Michael', 'Jordan', 'BDQ9tbVQhT1svQZpX4A7xlFNWB31dKGl2j7ImeVLJSA=:vb1c/YJqOhbm4S+5jnAp5g==', 'CUSTOMER', TRUE),
('lebron.james@example.com', 'LeBron', 'James', 'BDQ9tbVQhT1svQZpX4A7xlFNWB31dKGl2j7ImeVLJSA=:vb1c/YJqOhbm4S+5jnAp5g==', 'CUSTOMER', TRUE),
('kobe.bryant@example.com', 'Kobe', 'Bryant', 'BDQ9tbVQhT1svQZpX4A7xlFNWB31dKGl2j7ImeVLJSA=:vb1c/YJqOhbm4S+5jnAp5g==', 'CUSTOMER', TRUE),
('stephen.curry@example.com', 'Stephen', 'Curry', 'BDQ9tbVQhT1svQZpX4A7xlFNWB31dKGl2j7ImeVLJSA=:vb1c/YJqOhbm4S+5jnAp5g==', 'CUSTOMER', TRUE),
('kevin.durant@example.com', 'Kevin', 'Durant', 'BDQ9tbVQhT1svQZpX4A7xlFNWB31dKGl2j7ImeVLJSA=:vb1c/YJqOhbm4S+5jnAp5g==', 'CUSTOMER', TRUE);

INSERT INTO employee (user_id, birth_date, active) VALUES
(1, '1990-01-01', TRUE),
(2, '1991-02-02', TRUE),
(3, '1992-03-03', TRUE),
(4, '1993-04-04', TRUE),
(5, '1994-05-05', TRUE);

INSERT INTO address (cep, uf, city, district, street, number, active) VALUES
('80020000', 'PR', 'Curitiba', 'Centro', 'Rua XV de Novembro', '123', TRUE),
('80240000', 'PR', 'Curitiba', 'Batel', 'Avenida Sete de Setembro', '456', TRUE),
('80010000', 'PR', 'Curitiba', 'Centro', 'Rua Marechal Deodoro', '789', TRUE),
('80020000', 'PR', 'Curitiba', 'Centro', 'Rua das Flores', '101', TRUE),
('81070000', 'PR', 'Curitiba', 'Portão', 'Rua João Bettega', '202', TRUE),
('83323000', 'PR', 'Pinhais', 'Centro', 'Rua Jacob Macanhan', '303', TRUE),
('83323000', 'PR', 'Pinhais', 'Centro', 'Avenida Camilo Di Lellis', '404', TRUE),
('83323000', 'PR', 'Pinhais', 'Centro', 'Rua Salgado Filho', '505', TRUE),
('83323000', 'PR', 'Pinhais', 'Alphaville', 'Rua Rio Trombetas', '606', TRUE),
('83323000', 'PR', 'Pinhais', 'Alphaville', 'Rua Rio Amazonas', '707', TRUE);

INSERT INTO customer (user_id, cpf, phone_number, address_id, active) VALUES
(6, '12345678901', '41999998888', 1, TRUE),
(7, '23456789012', '41999998888', 2, TRUE),
(8, '34567890123', '41999998888', 3, TRUE),
(9, '45678901234', '41999998888', 4, TRUE),
(10, '56789012345', '41999998888', 5, TRUE);

INSERT INTO equip_category (category_desc, active) VALUES
('Computador', TRUE),
('Periférico', TRUE),
('Impressora', TRUE),
('Dispositivo móvel', TRUE),
('Notebook', TRUE),
('Monitor', TRUE);

INSERT INTO request (id, equip_category_id, equip_desc, defect_desc, budget, repair_desc, customer_orientations, customer_id, active) VALUES
(1, 1, 'Computador Positivo Unique 1GB', 'Estava funcionando normalmente, mas não ligou.', 0.0, '', '', 1, TRUE),
(2, 2, 'Teclado mecânico Logitech', 'A tecla ''A'' não funciona.', 50.0, '', '', 1, TRUE),
(3, 5, 'Notebook Dell Inspiron', 'Desempenho abaixo do esperado.', 200.0, '', '', 1, TRUE),
(4, 3, 'Impressora HP LaserJet', 'Erro de conexão via Wi-Fi.', 10.0, '', '', 1, TRUE),
(5, 6, 'Monitor Samsung 24 polegadas', 'Manchas na tela, dificultando a visualização.', 0.0, '', '', 1, TRUE),
(6, 2, 'Mouse Razer', 'O sensor do mouse está inoperante.', 40.0, 'Foi necessária a troca do sensor.', 'Considerar a compra de um novo mouse.', 4, TRUE),
(7, 2, 'Carregador Dell 65W', 'Carregador não está funcionando.', 100.0, 'O cabo estava rompido.', 'Recomendo substituição.', 5, TRUE),
(8, 1, 'Desktop HP', 'Ruído excessivo durante o funcionamento.', 75.0, 'Foi necessário lubrificar o ventilador.', 'Realizamos a manutenção.', 3, TRUE),
(9, 1, 'Computador Lenovo', 'Sistema operacional não responde.', 0.0, '', '', 3, TRUE),
(10, 5, 'Notebook Asus', 'Aplicativos não estão abrindo.', 0.0, '', '', 3, TRUE),
(11, 5, 'Laptop Samsung', 'A tela pisca intermitentemente.', 0.0, '', '', 4, TRUE),
(12, 1, 'Desktop Sony', 'Falta de conexão com Wi-Fi.', 0.0, '', '', 4, TRUE),
(13, 2, 'Headset JBL', 'Microfone não capta som.', 0.0, '', '', 4, TRUE),
(14, 5, 'Notebook Acer', 'O notebook não liga.', 0.0, '', '', 4, TRUE),
(15, 3, 'Impressora Epson', 'Impressora não responde ao comando de impressão.', 0.0, '', '', 5, TRUE),
(16, 3, 'Impressora Canon', 'Impressões saem borradas.', 0.0, '', '', 5, TRUE),
(17, 5, 'Laptop Toshiba', 'O laptop não liga mesmo conectado.', 0.0, '', '', 1, TRUE),
(18, 4, 'Tablet Apple', 'Tela está trincada.', 0.0, '', '', 1, TRUE),
(19, 1, 'Desktop HP', 'Erro na inicialização.', 0.0, '', '', 3, TRUE),
(20, 2, 'Teclado Microsoft', 'Teclas não respondem.', 0.0, '', '', 3, TRUE),
(21, 5, 'Desktop Dell', 'Não liga após a atualização.', 0.0, '', '', 4, TRUE),
(22, 5, 'Notebook HP', 'O notebook não reconhece a bateria.', 0.0, '', '', 4, TRUE),
(23, 2, 'Webcam Logitech', 'Não reconhecida pelo sistema.', 0.0, '', '', 5, TRUE),
(24, 2, 'Caixas de som Creative', 'Sem som durante a reprodução.', 0.0, '', '', 5, TRUE),
(25, 5, 'Notebook Acer', 'O desempenho é abaixo do esperado.', 0.0, '', '', 1, TRUE),
(26, 3, 'Impressora Brother', 'Sem resposta ao comando de impressão.', 0.0, '', '', 2, TRUE),
(27, 5, 'Laptop Toshiba', 'Sem resposta ao pressionar o botão de ligar.', 0.0, '', '', 1, TRUE),
(28, 6, 'Monitor LG 27 polegadas', 'Áreas da tela não respondem.', 0.0, '', '', 1, TRUE),
(29, 4, 'Samsung A30', 'A camera traseira não foca em nada.', 0.0, '', '', 1, TRUE),
(30, 3, 'Epson TX115 Series', 'O leitor de nível de tinta está falhando, as vezes o cartucho é novo e a impressão diz que precisa recarregar', 0.0, '', '', 1, TRUE);

INSERT INTO request_status (id, date_time, request_id, sending_employee_id, in_charge_employee_id, status, active) VALUES
(1, '2024-09-24 00:00:00', 1, null, null, 'OPEN', TRUE),
(2, '2024-09-25 00:00:00', 2, null, null, 'OPEN', TRUE),
(3, '2024-09-26 00:00:00', 2, null, 1, 'BUDGETED', TRUE),
(4, '2024-09-27 00:00:00', 3, null, null, 'OPEN', TRUE),
(5, '2024-09-28 00:00:00', 3, null, 1, 'BUDGETED', TRUE),
(6, '2024-10-12 00:00:00', 3, null, 1, 'REJECTED', TRUE),
(7, '2024-10-13 00:00:00', 4, null, null, 'OPEN', TRUE),
(8, '2024-10-13 01:00:00', 4, null, 1, 'BUDGETED', TRUE),
(9, '2024-10-14 00:00:00', 4, null, 1, 'APPROVED', TRUE),
(10, '2024-10-15 00:00:00', 5, null, null, 'OPEN', TRUE),
(11, '2024-10-16 00:00:00', 5, 2, 1, 'REDIRECTED', TRUE),
(12, '2024-10-16 01:00:00', 6, null, null, 'OPEN', TRUE),
(13, '2024-10-16 02:00:00', 6, null, 1, 'BUDGETED', TRUE),
(14, '2024-10-18 00:00:00', 6, null, 1, 'APPROVED', TRUE),
(15, '2024-10-20 00:00:00', 6, null, 1, 'FIXED', TRUE),
(16, '2024-10-20 01:00:00', 7, null, null, 'OPEN', TRUE),
(17, '2024-10-20 02:00:00', 7, null, 2, 'BUDGETED', TRUE),
(18, '2024-10-20 03:00:00', 7, null, 2, 'APPROVED', TRUE),
(19, '2024-10-20 04:00:00', 7, null, 2, 'FIXED', TRUE),
(20, '2024-10-21 00:00:00', 7, null, 2, 'PAID', TRUE),
(21, '2024-10-21 01:00:00', 8, null, null, 'OPEN', TRUE),
(22, '2024-10-21 02:00:00', 8, null, 3, 'BUDGETED', TRUE),
(23, '2024-10-22 00:00:00', 8, null, 3, 'APPROVED', TRUE),
(24, '2024-10-22 01:00:00', 8, null, 3, 'FIXED', TRUE),
(25, '2024-10-22 02:00:00', 8, null, 3, 'PAID', TRUE),
(26, '2024-10-22 03:00:00', 8, null, 3, 'FINALIZED', TRUE),
(27, '2024-10-22 03:00:01', 9, null, null, 'OPEN', TRUE),
(28, '2024-10-22 03:30:00', 10, null, null, 'OPEN', TRUE),
(29, '2024-10-23 00:00:00', 11, null, null, 'OPEN', TRUE),
(30, '2024-10-23 01:00:00', 1, null, null, 'OPEN', TRUE),
(31, '2024-10-23 02:00:00', 13, null, null, 'OPEN', TRUE),
(32, '2024-10-23 03:00:00', 14, null, null, 'OPEN', TRUE),
(33, '2024-10-24 00:00:00', 15, null, null, 'OPEN', TRUE),
(34, '2024-10-24 01:00:00', 16, null, null, 'OPEN', TRUE),
(35, '2024-10-25 00:00:00', 17, null, null, 'OPEN', TRUE),
(36, '2024-10-25 01:00:00', 18, null, null, 'OPEN', TRUE),
(37, '2024-10-26 00:00:00', 19, null, null, 'OPEN', TRUE),
(38, '2024-10-26 01:00:00', 20, null, null, 'OPEN', TRUE),
(39, '2024-10-26 02:00:00', 21, null, null, 'OPEN', TRUE),
(40, '2024-10-26 03:00:00', 22, null, null, 'OPEN', TRUE),
(41, '2024-10-26 03:30:00', 23, null, null, 'OPEN', TRUE),
(42, '2024-10-26 04:00:00', 24, null, null, 'OPEN', TRUE),
(43, '2024-10-26 04:30:00', 25, null, null, 'OPEN', TRUE),
(44, '2024-10-26 05:00:00', 26, null, null, 'OPEN', TRUE),
(45, '2024-10-26 05:30:00', 27, null, null, 'OPEN', TRUE),
(46, '2024-10-26 06:00:00', 28, null, null, 'OPEN', TRUE),
(47, '2024-10-27 00:00:00', 29, null, null, 'OPEN', TRUE),
(48, '2024-10-27 01:00:00', 30, null, null, 'OPEN', TRUE);