create table user
(
	account_id varchar(100),
	name varchar(50),
	token char(36),
	gmt_create bigint,
	gmt_modified bigint,
	id integer auto_increment,
		primary key (id)
);
