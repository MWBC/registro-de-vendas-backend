create table users (

	id uuid not null, 
	name varchar(255) not null, 
	email varchar(100) not null, 
	password varchar(100) not null, 
	created_at timestamp, 
	updated_at timestamp, 
	
	constraint email_unique UNIQUE (email), 
	primary key (id)
);