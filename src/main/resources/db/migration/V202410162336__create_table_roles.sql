create table roles (

	id serial not null, 
	title varchar(255) not null, 
	created_at timestamp, 
	updated_at timestamp, 
	primary key (id)
);