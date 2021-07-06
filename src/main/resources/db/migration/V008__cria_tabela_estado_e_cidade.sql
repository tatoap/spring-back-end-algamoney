create table estado (
	id bigint(20) primary key auto_increment,
	nome varchar(50) not null
) engine=innoDB default charset=utf8;

create table cidade (
	id bigint(20) primary key auto_increment,
	nome varchar(50) not null,
	id_estado bigint(20) not null
) engine=innoDB default charset=utf8;

alter table cidade add constraint fk_cidade_estado
foreign key (id_estado) references estado (id);

alter table pessoa drop column endereco_cidade;
alter table pessoa drop column endereco_estado;
alter table pessoa add column endereco_id_cidade bigint(20);
alter table pessoa add constraint fk_endereco_cidade foreign key (endereco_id_cidade) references cidade (id);