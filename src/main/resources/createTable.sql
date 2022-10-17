
create table USERA
(
id integer not null,
first_name varchar(255) not null,
last_name varchar(255) not null,
role_id enum_id,
password varchar(255) not null,
primary key (id)
);

create table FEEDBACK
(
id integer not null,
rate integer not null,
date_rate date,
text varchar(255) not null,
primary key (id)
);

create table USERS_COMMENT
(
id integer not null,
text varchar(255) not null,
comment_date DATE,
primary key (id)
);


create sequence hibernate_sequence start with 1 increment by 1;


alter table FEEDBACK add constraint for_FB foreign key (id) references USERA;
alter table USERS_COMMENT add constraint for_UC foreign key (id) references USERA;
create type enum_id as enum ('Employee','Manager','Engineer');



