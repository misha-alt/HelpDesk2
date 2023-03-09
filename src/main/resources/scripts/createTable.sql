
create table FEEDBACK
(
id integer, not null,
rate integer, not null,
date_rate DATE,
text varchar(255), not null,
user_id integer not null,
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

create table TICKED
(
id integer not null,
assignee varchar(255),
approver varchar(255),
loginOfcreater varchar(255),
rollOfCreater varchar(255),
name varchar(255),
description varchar(255),
state varchar(255),
urgency varchar(255),
desireddate varchar(255),
primary key (id)
);

create table USER_TICKED
(
user_id integer not null,
ticked_id integer not null
);

create table USER
(
id integer not null auto_increment,
first_name varchar(255) not null,
last_name varchar(255) not null,
login varchar(255) not null,
password varchar(255) not null,
authority varchar(255) not null,
email varchar(255) not null,
primary key (id)
);



create sequence hibernate_sequence start with 1 increment by 1;

alter table USER auto_increment=6;
alter table FEEDBACK add constraint for_FB foreign key (user_id) references USER;
alter table USERS_COMMENT add constraint for_UC foreign key (user_id) references USER;
alter table TICKED add constraint for_Tick_approv foreign key (approver_id) references USER;
alter table USER_TICKED add constraint FKnrd8tgg7q foreign key (user_id) references USER;
alter table USER_TICKED add constraint FKtepb8xswf foreign key (ticked_id) references TICKED;





