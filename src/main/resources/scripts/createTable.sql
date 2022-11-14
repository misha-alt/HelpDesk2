
create table FEEDBACK
(
id integer, not null,
rate integer, not null,
date_rate DATE,
text varchar(255), not null,
user_id integer not null
primary key (id)
);

create table USERS_COMMENT
(
id integer not null,
text varchar(255),
comment_date DATE,
user_id integer,
primary key (id)
);

create table USER
(
id integer not null,
first_name varchar(255) not null,
last_name varchar(255) not null,
login varchar(255) not null,
password varchar(255) not null,
authority varchar(255) not null,
primary key (id)
);



create sequence hibernate_sequence start with 1 increment by 1;


alter table FEEDBACK add constraint for_FB foreign key (user_id) references USER;
alter table USERS_COMMENT add constraint for_UC foreign key (user_id) references USER;




