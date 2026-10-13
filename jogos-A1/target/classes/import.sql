-- Plataformas
INSERT INTO plataforma (id, nome, fabricante) VALUES (1, 'PC', 'Microsoft/Outros');
INSERT INTO plataforma (id, nome, fabricante) VALUES (2, 'PlayStation 5', 'Sony');
INSERT INTO plataforma (id, nome, fabricante) VALUES (3, 'Xbox Series X/S', 'Microsoft');
INSERT INTO plataforma (id, nome, fabricante) VALUES (4, 'Nintendo Switch', 'Nintendo');

-- Desenvolvedoras (Associação com Jogo)
INSERT INTO desenvolvedora (id, nome, paisorigem, anofundacao, website) 
VALUES (1, 'CD Projekt Red', 'Polônia', 1994, 'https://www.cdprojektred.com');

INSERT INTO desenvolvedora (id, nome, paisorigem, anofundacao, website) 
VALUES (2, 'Santa Monica Studio', 'Estados Unidos', 1999, 'https://sms.playstation.com');

INSERT INTO desenvolvedora (id, nome, paisorigem, anofundacao, website) 
VALUES (3, 'Playground Games', 'Reino Unido', 2010, 'https://www.playground-games.com');

INSERT INTO desenvolvedora (id, nome, paisorigem, anofundacao, website) 
VALUES (4, 'Nintendo EPD', 'Japão', 2015, 'https://www.nintendo.com');

INSERT INTO desenvolvedora (id, nome, paisorigem, anofundacao, website) 
VALUES (5, 'Capcom', 'Japão', 1979, 'https://www.capcom.com');

-- Requisitos de Sistema (Composição 1:1 com Jogo)
INSERT INTO requisitosistema (id, so, processador, memoriaram, placadevideo, armazenamento)
VALUES (1, 'Windows 10/11 64-bit', 'Intel Core i5-4460 3.2 GHz / AMD FX-8350', '8 GB', 'NVIDIA GTX 770 / AMD Radeon R9 290', '50 GB');

INSERT INTO requisitosistema (id, so, processador, memoriaram, placadevideo, armazenamento)
VALUES (2, 'PlayStation OS / Win 10', 'AMD Ryzen Zen 2 / Intel Core i5-6600k', '16 GB', 'AMD RDNA 2 / NVIDIA RTX 2060', '90 GB');

INSERT INTO requisitosistema (id, so, processador, memoriaram, placadevideo, armazenamento)
VALUES (3, 'Windows 10 64-bit', 'Intel i5-4460 / AMD Ryzen 3 1200', '8 GB', 'NVidia GTX 970 / AMD Radeon RX 470', '110 GB');

INSERT INTO requisitosistema (id, so, processador, memoriaram, placadevideo, armazenamento)
VALUES (4, 'Nintendo Switch OS', 'Nvidia Custom Tegra', '4 GB', 'Nvidia Tegra GPU', '18.2 GB');

INSERT INTO requisitosistema (id, so, processador, memoriaram, placadevideo, armazenamento)
VALUES (5, 'Windows 10/11 64-bit', 'Intel Core i7-8700 / AMD Ryzen 5 3600', '16 GB', 'NVIDIA GeForce GTX 1070 / AMD RX 5700', '65 GB');

-- Jogos
INSERT INTO jogo (id, titulo, descricao, preco, genero, id_desenvolvedora, id_requisito_sistema) 
VALUES (1, 'The Witcher 3: Wild Hunt', 'RPG de mundo aberto premiado com o bruxo Geralt de Rivia', 129.90, 1, 1, 1);

INSERT INTO jogo (id, titulo, descricao, preco, genero, id_desenvolvedora, id_requisito_sistema) 
VALUES (2, 'God of War Ragnarok', 'Aventura épica de Kratos e Atreus na mitologia nórdica', 299.90, 2, 2, 2);

INSERT INTO jogo (id, titulo, descricao, preco, genero, id_desenvolvedora, id_requisito_sistema) 
VALUES (3, 'Forza Horizon 5', 'Corrida em festival de mundo aberto pelas paisagens vibrantes do México', 249.00, 4, 3, 3);

INSERT INTO jogo (id, titulo, descricao, preco, genero, id_desenvolvedora, id_requisito_sistema) 
VALUES (4, 'The Legend of Zelda: Tears of the Kingdom', 'Aventura fantástica nos céus e terras de Hyrule', 349.00, 3, 4, 4);

INSERT INTO jogo (id, titulo, descricao, preco, genero, id_desenvolvedora, id_requisito_sistema) 
VALUES (5, 'Resident Evil Requiem', 'Survival horror e ação tática contra ameaças biológicas', 299.90, 2, 5, 5);

-- Relacionamento Jogo <-> Plataforma (Agregação N:N)
INSERT INTO jogo_plataforma (id_jogo, id_plataforma) VALUES (1, 1);
INSERT INTO jogo_plataforma (id_jogo, id_plataforma) VALUES (1, 2);
INSERT INTO jogo_plataforma (id_jogo, id_plataforma) VALUES (1, 3);
INSERT INTO jogo_plataforma (id_jogo, id_plataforma) VALUES (1, 4);

INSERT INTO jogo_plataforma (id_jogo, id_plataforma) VALUES (2, 2);
INSERT INTO jogo_plataforma (id_jogo, id_plataforma) VALUES (2, 1);

INSERT INTO jogo_plataforma (id_jogo, id_plataforma) VALUES (3, 1);
INSERT INTO jogo_plataforma (id_jogo, id_plataforma) VALUES (3, 3);

INSERT INTO jogo_plataforma (id_jogo, id_plataforma) VALUES (4, 4);

INSERT INTO jogo_plataforma (id_jogo, id_plataforma) VALUES (5, 1);
INSERT INTO jogo_plataforma (id_jogo, id_plataforma) VALUES (5, 2);
INSERT INTO jogo_plataforma (id_jogo, id_plataforma) VALUES (5, 3);

-- Carga inicial de Pessoas (Superclasse com estratégia JOINED - Herança)
INSERT INTO pessoa (id, nome, cpf, email) VALUES (1, 'Lucas Silva', '11122233344', 'lucas.gamer@email.com');
INSERT INTO pessoa (id, nome, cpf, email) VALUES (2, 'Beatriz Santos', '22233344455', 'beatriz.rpg@email.com');
INSERT INTO pessoa (id, nome, cpf, email) VALUES (3, 'Gabriel Costa', '33344455566', 'gabriel.speed@email.com');
INSERT INTO pessoa (id, nome, cpf, email) VALUES (4, 'Marina Souza', '44455566677', 'marina.admin@jogos.com');
INSERT INTO pessoa (id, nome, cpf, email) VALUES (5, 'Carlos Eduardo', '55566677788', 'carlos.dev@jogos.com');

-- Carga de Clientes (Subclasse especializada de Pessoa)
INSERT INTO cliente (id, nickname, telefone) VALUES (1, 'DarkKnight99', '63991112233');
INSERT INTO cliente (id, nickname, telefone) VALUES (2, 'PixelQueen', '63992223344');
INSERT INTO cliente (id, nickname, telefone) VALUES (3, 'SpeedRacerX', '63993334455');

-- Carga de Funcionários (Subclasse especializada de Pessoa)
INSERT INTO funcionario (id, matricula, cargo, salario) VALUES (4, 'FUNC-001', 'Gerente da Plataforma', 8500.00);
INSERT INTO funcionario (id, matricula, cargo, salario) VALUES (5, 'FUNC-002', 'Engenheiro de Software', 7200.00);

-- Ajuste de Sequences para novos cadastros
ALTER SEQUENCE IF EXISTS plataforma_id_seq RESTART WITH 10;
ALTER SEQUENCE IF EXISTS desenvolvedora_id_seq RESTART WITH 10;
ALTER SEQUENCE IF EXISTS requisitosistema_id_seq RESTART WITH 10;
ALTER SEQUENCE IF EXISTS jogo_id_seq RESTART WITH 10;
ALTER SEQUENCE IF EXISTS pessoa_id_seq RESTART WITH 10;