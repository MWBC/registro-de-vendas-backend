create table services(

	id uuid not null, 
	title varchar(255) not null, 
	description text not null, 
	base_price numeric(9, 2) not null, 
	user_id uuid not null, 
	deleted boolean default false, 
	created_at timestamp, 
	updated_at timestamp, 
	
	primary key (id), 
	constraint fk_user foreign key (user_id) references users(id)
);