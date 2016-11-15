create if not exists database oc;

use oc;

create table users (
  id int not null auto_increment,
  username varchar(60) not null,
  email varchar(100) not null,
  description varchar (4000),
  facebookId int,
  password varchar(60),
  unique (username),
  primary key (id)
);

create table story (
  	id int not null auto_increment,
	owner int not null references users (id),
	title varchar(1000) not null,
	description text,
	genre varchar(250),
	private boolean default false,
	visible boolean default true,
	primary key (id)
);

create table characters (
	id int not null auto_increment,
	owner int not null references users(id),
	name varchar(250) not null,
	appearance text,
	personality text,
	notes text,
	primary key (id)
);