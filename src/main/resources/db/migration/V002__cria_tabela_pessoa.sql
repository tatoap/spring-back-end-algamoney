create table pessoa (
	id bigint(20) primary key auto_increment,
	nome varchar(255) not null,
	endereco_logradouro varchar(255),
	endereco_numero varchar(5),
	endereco_complemento varchar(100),
	endereco_bairro varchar(100),
	endereco_cep varchar(9),
	endereco_cidade varchar(50),
	endereco_estado varchar(2),
	ativo tinyint(1) not null
) engine=InnoDB default charset=utf8;