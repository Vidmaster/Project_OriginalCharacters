create if not exists database oc;

use oc;

create table users (
  id int not null auto_increment,
  username varchar(60) not null,
  email varchar(100) not null,
  description varchar (4000),
  facebookId int,
  password varchar(60),
  enabled boolean,
  unique (username),
  primary key (id)
);

create table authorities (
	username varchar(60) not null, 
	authority varchar(50) not null, 
	foreign key (username) references user (username), 
	unique index authorities_idx_1 (username, authority)
);

create table story (
  	id int not null auto_increment,
	owner int not null references user (id),
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

create table contribution (
	id int not null auto_increment,
	owner int not null references users (id),
	story int not null references story (id),
	order int not null, 
	title varchar(1000),
	body text,
	status varchar(20)
);

create table invitation (
	id int not null auto_increment,
	sender int not null references users (id),
	recipient int not null references users (id),
	story int not null references story (id),
	status varchar(20),
	type varchar(20)
);

create table comment (
	id int not null auto_increment,
	story int not null references story (id);
	commenter int not null references users (id)
	timestamp datetime,
	comment text
);

create table CharacterToContribution (
	id int not null auto_increment,
	character int not null references characters (id),
	contribution int not null references contribution (id),
);

create table CharacterToStory (
	id int not null auto_increment,
	character int not null references characters (id),
	story int not null references story (id)
);

create table UserToStory (
	id int not null auto_increment, 
	contributor int not null references users (id),
	story int not null references story (id)
);