/*set database collation 'UTF8_GENERAL_CI';*/
SET CHARSET UTF8;
/*CREATE ALIAS IF NOT EXISTS BCryptHash FOR "org.mindrot.jbcrypt.BCrypt.hashpw";
CREATE ALIAS IF NOT EXISTS BCryptCheck FOR "org.mindrot.jbcrypt.BCrypt.checkpw";*/



/* менеджер*/
INSERT INTO USER(id, first_name, last_name, login, email, password)
VALUES(1, 'Миша', 'Yatskevich', 'Pan', '1somemail', '100');



INSERT INTO USERROLES(id, role_name)
VALUES(1, 'ROLE_MANAGER');

INSERT INTO USER_ROLES(user_id, userrole_id)
VALUES(1, 1);



/*инженер*/
INSERT INTO USER(id, first_name, last_name, login, email, password)
VALUES(2, 'Alfred', 'Nooble', 'Bam', '4somemail', '400');



INSERT INTO USERROLES(id, role_name)
VALUES(2, 'ROLE_ENGINEER');

INSERT INTO USER_ROLES(user_id, userrole_id)
VALUES(2, 2);

/*пользователь*/
INSERT INTO USER(id, first_name, last_name, login, email, password)
VALUES(3, 'Billy', 'Kid', 'Cowboy', '2somemail', '200');


INSERT INTO USERROLES(id, role_name)
VALUES(3, 'ROLE_USER');

INSERT INTO USER_ROLES(user_id, userrole_id)
VALUES(3, 3);




