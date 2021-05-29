set foreign_key_checks = 0;

delete from categoria;
delete from pessoa;
delete from lancamento;
delete from usuario_permissao;
delete from usuario;
delete from permissao;

set foreign_key_checks = 1;

alter table categoria auto_increment = 1;
alter table pessoa auto_increment = 1;
alter table lancamento auto_increment = 1;
alter table usuario auto_increment = 1;
alter table permissao auto_increment = 1;

insert into usuario (nome, email, senha) values ('Administrador', 'admin@algamoney.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
insert into usuario (nome, email, senha) values ('Renato Ramos', 'renato.ramos@algamoney.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
insert into usuario (nome, email, senha) values ('Patricia', 'patricia@algamoney.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');

insert into permissao (descricao) values ('ROLE_CADASTRAR_CATEGORIA');
insert into permissao (descricao) values ('ROLE_PESQUISAR_CATEGORIA');
insert into permissao (descricao) values ('ROLE_CADASTRAR_PESSOA');
insert into permissao (descricao) values ('ROLE_REMOVER_PESSOA');
insert into permissao (descricao) values ('ROLE_PESQUISAR_PESSOA');
insert into permissao (descricao) values ('ROLE_CADASTRAR_LANCAMENTO');
insert into permissao (descricao) values ('ROLE_REMOVER_LANCAMENTO');
insert into permissao (descricao) values ('ROLE_PESQUISAR_LANCAMENTO');

insert into usuario_permissao (usuario_id, permissao_id) values (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8);

insert into usuario_permissao (usuario_id, permissao_id) values (2, 2), (2, 5), (2, 8);

insert into usuario_permissao (usuario_id, permissao_id) values (3, 2), (3, 5), (3, 8);

insert into categoria (nome) values ('Lazer');
insert into categoria (nome) values ('Alimentação');
insert into categoria (nome) values ('Supermercado');
insert into categoria (nome) values ('Farmácia');
insert into categoria (nome) values ('Outros');

insert into pessoa (nome, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_cidade, endereco_estado, ativo) values ('Renato Ramos', 'Rua Maria Espíndola', '121', null, 'Jardim Santa Lidia', '07.142-223', 'Guarulhos', 'SP', 1);
insert into pessoa (nome, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_cidade, endereco_estado, ativo) values ('Patricia Avelar', 'Rua Maria Espíndola', '121', null, 'Jardim Santa Lidia', '07.142-223', 'Guarulhos', 'SP', 1);
insert into pessoa (nome, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_cidade, endereco_estado, ativo) values ('Eliana Rodrigues', 'Rua das Palmeiras', '50', null, 'Gopoúva', '07.140-050', 'Guarulhos', 'SP', 0);
insert into pessoa (nome, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_cidade, endereco_estado, ativo) values ('Roseli Ramos', 'Rua das Palmeiras', '50', null, 'Gopoúva', '07.140-050', 'Guarulhos', 'SP', 1);
insert into pessoa (nome, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_cidade, endereco_estado, ativo) values ('Maria', 'Rua Turmalina', '260', null, 'Tapajós', '03.100-050', 'Teófilo Otoni', 'MG', 1);
insert into pessoa (nome, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_cidade, endereco_estado, ativo) values ('Pedro Barbosa', 'Av Brasil', '100', null, 'Tubalina', '77.400-12', 'Porto Alegre', 'RS', true);
insert into pessoa (nome, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_cidade, endereco_estado, ativo) values ('Henrique Medeiros', 'Rua do Sapo', '1120', 'Apto 201', 'Centro', '12.400-12', 'Rio de Janeiro', 'RJ', true);
insert into pessoa (nome, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_cidade, endereco_estado, ativo) values ('Carlos Santana', 'Rua da Manga', '433', null, 'Centro', '31.400-12', 'Belo Horizonte', 'MG', true);
insert into pessoa (nome, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_cidade, endereco_estado, ativo) values ('Leonardo Oliveira', 'Rua do Músico', '566', null, 'Segismundo Pereira', '38.400-00', 'Uberlândia', 'MG', true);
insert into pessoa (nome, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cep, endereco_cidade, endereco_estado, ativo) values ('Isabela Martins', 'Rua da Terra', '1233', 'Apto 10', 'Vigilato', '99.400-12', 'Manaus', 'AM', true);

insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Salário mensal', '2017-06-10', null, 6500.00, 'Distribuição de lucros', 'RECEITA', 1, 1);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Bahamas', '2017-02-10', '2017-02-10', 100.32, null, 'DESPESA', 2, 2);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Top Club', '2017-06-10', null, 120, null, 'RECEITA', 3, 3);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('CEMIG', '2017-02-10', '2017-02-10', 110.44, 'Geração', 'RECEITA', 3, 4);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('DMAE', '2017-06-10', null, 200.30, null, 'DESPESA', 3, 5);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Extra', '2017-03-10', '2017-03-10', 1010.32, null, 'RECEITA', 4, 6);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Bahamas', '2017-06-10', null, 500, null, 'RECEITA', 1, 7);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Top Club', '2017-03-10', '2017-03-10', 400.32, null, 'DESPESA', 4, 8);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Despachante', '2017-06-10', null, 123.64, 'Multas', 'DESPESA', 3, 9);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Pneus', '2017-04-10', '2017-04-10', 665.33, null, 'RECEITA', 5, 10);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Café', '2017-06-10', null, 8.32, null, 'DESPESA', 1, 5);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Eletrônicos', '2017-04-10', '2017-04-10', 2100.32, null, 'DESPESA', 5, 4);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Instrumentos', '2017-06-10', null, 1040.32, null, 'DESPESA', 4, 3);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Café', '2017-04-10', '2017-04-10', 4.32, null, 'DESPESA', 4, 2);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Lanche', '2017-06-10', null, 10.20, null, 'DESPESA', 4, 1); 