create if not exists database oc;

use oc;

create table users (
  id int not null auto_increment,
  username varchar(60) not null,
  email varchar(100) not null,
  description varchar (4000),
  facebookId int,
  password varchar(60),
  enabled boolean default true,
  unique (username),
  unique (email),
  primary key (id),
  index USERNAME_INDEX (username),
  index EMAIL_INDEX (email)
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
	visible boolean default true,
	inviteonly boolean default false,
	primary key (id),
	index TITLE_INDEX (title),
	index OWNER_INDEX (owner)
);

create table characters (
	id int not null auto_increment,
	owner int not null references users(id),
	name varchar(250) not null,
	appearance text,
	personality text,
	notes text,
	boolean taggable default true,
	boolean notify_on_tag default true,
	primary key (id),
	index NAME_INDEX (name),
	index OWNER_INDEX (owner),
	index TAGGABLE_INDEX (taggable)
);

create table contribution (
	id int not null auto_increment,
	owner int not null references users (id),
	story int not null references story (id),
	ordering int not null default 0, 
	title varchar(1000),
	body text,
	status varchar(20),
	primary key (id),
	index TITLE_INDEX (title),
	index OWNER_INDEX (owner),
	index STORY_INDEX (story)
);

create table invitation (
	id int not null auto_increment,
	sender int not null references users (id),
	recipient int not null references users (id),
	story int not null references story (id),
	status varchar(20),
	type varchar(20),
	primary key (id),
	index SENDER_INDEX (sender),
	index RECIPIENT_INDEX (recipient)
);

create table comment (
	id int not null auto_increment,
	story int not null references story (id),
	commenter int not null references users (id),
	timestamp datetime,
	comment text,
	primary key (id),
	index STORY_INDEX (story),
	index COMMENTER_INDEX (commenter)
);

create table CharacterToContribution (
	original_character int not null references characters (id),
	contribution int not null references contribution (id),
	primary key (original_character,contribution)
);

create table CharacterToStory (
	original_character int not null references characters (id),
	story int not null references story (id),
	primary key (original_character,story)
);

create table UserToStory (
	contributor int not null references users (id),
	story int not null references story (id),
	primary key (contributor,story)
);