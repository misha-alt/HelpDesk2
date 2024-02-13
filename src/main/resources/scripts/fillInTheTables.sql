/*set database collation 'UTF8_GENERAL_CI';*/
SET CHARSET UTF8;
/* менеджер*/
INSERT INTO USER(id, first_name, last_name, login, email)
VALUES(1, 'Миша', 'Yatskevich', 'Pan', '1somemail');

INSERT INTO PASSWORDS(id, password, enabled, user_id)
VALUES(1, '100', true, 1);

INSERT INTO USERROLES(id, role_name)
VALUES(1, 'ROLE_MANAGER');

INSERT INTO USER_ROLES(user_id, userrole_id)
VALUES(1, 1);



/*инженер*/
INSERT INTO USER(id, first_name, last_name, login, email)
VALUES(2, 'Alfred', 'Nooble', 'Bam', '4somemail');

INSERT INTO PASSWORDS(id, password, enabled, user_id)
VALUES(2, '200', true, 2);

INSERT INTO USERROLES(id, role_name)
VALUES(2, 'ROLE_ENGINEER');

INSERT INTO USER_ROLES(user_id, userrole_id)
VALUES(2, 2);

/*пользователь*/
INSERT INTO USER(id, first_name, last_name, login, email)
VALUES(3, 'Billy', 'Kid', 'Cowboy', '2somemail');

INSERT INTO PASSWORDS(id, password, enabled, user_id)
VALUES(3, '300', true, 3);

INSERT INTO USERROLES(id, role_name)
VALUES(3, 'ROLE_USER');

INSERT INTO USER_ROLES(user_id, userrole_id)
VALUES(3, 3);




