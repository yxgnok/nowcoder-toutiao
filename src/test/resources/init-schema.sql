use toutiao;
drop table if exists user ;
create table user (
  id int(11) unsigned not null auto_increment,
  name varchar(64) not null default "",
  password varchar(128) not null default "",
  salt varchar(32) not null default "",
  head_url varchar(255) not null default "",
  primary key (id),
  unique key name (name)
) engine=InnoDB default charset=utf8;

drop table if exists news;
create table news (
  id int(11) unsigned not null auto_increment,
  title varchar(128) not null default "",
  link varchar(256) not null default "",
  image varchar(256) not null default "",
  like_count int(11) not null,
  comment_count int(11) not null,
  created_date datetime not null,
  user_id int(11) not null,
  primary key (id)
) engine=InnoDB default charset=utf8;

drop table if exists message;
create table message (
  id int(11) unsigned not null auto_increment,
  fromid int(11) not null,
  toid int(11) not null,
  content varchar(256) not null default "",
  conversation_id int(11) not null,
  created_date datetime not null,
  primary key (id)
) engine=InnoDB default charset=utf8;

drop table if exists comment;
create table comment (
  id int(11) unsigned not null auto_increment,
  content varchar(256) not null default "",
  user_id int(11) not null,
  created_date datetime not null,
  news_id int(11) not null,
  primary key(id)
) engine=InnoDB default charset=utf8;

