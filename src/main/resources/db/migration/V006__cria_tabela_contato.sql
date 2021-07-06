create table contato (
	id bigint(20) primary key auto_increment,
	nome varchar(50) not null,
	telefone varchar(20) not null,
	email varchar(100) not null,
	pessoa_id bigint(20) not null
) engine=InnoDB default charset=utf8;

alter table contato add constraint fk_contato_pessoa
foreign key (pessoa_id) references pessoa (id);