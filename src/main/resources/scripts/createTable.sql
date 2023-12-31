set database collation 'UTF8_GENERAL_CI';
create table FEEDBACK
(
id integer not null,
rate integer,
date_rate varchar(255),
text varchar(255),
user_id integer,
ticked_id integer,
primary key (id)
);

create table USERS_COMMENT
(
id integer not null,
text varchar(255),
comment_date DATE,
ticked_id integer,
user_id integer,
loginOfCreator varchar(255),
primary key (id)
);

create table TICKED
(
id integer not null,
categor varchar(255),
assignee varchar(255),
approver varchar(255),
loginOfcreater varchar(255),
rollOfCreater varchar(255),
name varchar(255),
description varchar(255),
state varchar(255),
urgency varchar(255),
desireddate varchar(255),
create_date varchar(255),
myFile_id integer,
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

create table MYFILE
(
id integer not null,
file_name varchar (255) not null,
fileType varchar (255) not null,
ticked_id integer,
image blob,
history_id integer,
primary key (id)
);

create table TICKETHISTORY
(
id integer not null,
dateHistory varchar(255),
nameHistory varchar(255),
ticket_description varchar(255),
myFile varchar(255),
deletedFilename varchar(255),
ticked_id integer,
primary key (id)
);



create sequence hibernate_sequence start with 1 increment by 1;

alter table USER auto_increment=6;

alter table FEEDBACK add constraint for_FB foreign key (user_id) references USER;
alter table USERS_COMMENT add constraint for_UC foreign key (ticked_id) references TICKED;
alter table USERS_COMMENT add constraint for_UCC foreign key (user_id) references USER;
alter table TICKED add constraint for_Tick_approv foreign key (approver_id) references USER;
alter table USER_TICKED add constraint FKnrd8tgg7q foreign key (user_id) references USER;
alter table USER_TICKED add constraint FKtepb8xswf foreign key (ticked_id) references TICKED;
alter table MYFILE add constraint fk foreign key (ticked_id) references TICKED;
alter table TICKED add constraint FKf foreign key (myFile_id) references MYFILE;
alter table TICKETHISTORY add constraint nameKonst foreign key (ticked_id) references TICKED;
alter table MYFILE add constraint GHJ foreign key (history_id) references TICKETHISTORY;
alter table FEEDBACK add constraint anotherConst foreign key (ticked_id) references TICKED;
alter table FEEDBACK add constraint oneEnotherConst foreign key (user_id) references USER;












