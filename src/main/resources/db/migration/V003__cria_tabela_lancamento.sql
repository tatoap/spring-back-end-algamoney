create table lancamento (
	id bigint(20) primary key auto_increment,
	descricao varchar(50) not null,
	data_vencimento date not null,
	data_pagamento date,
	valor decimal(10,2) not null,
	observacao varchar(100),
	tipo varchar(20) not null,
	categoria_id bigint(20) not null,
	pessoa_id bigint(20) not null
) engine=InnoDB default charset=utf8;

alter table lancamento add constraint fk_lancamento_categoria
foreign key (categoria_id) references categoria (id);

alter table lancamento add constraint fk_lancamento_pessoa
foreign key (pessoa_id) references pessoa (id);