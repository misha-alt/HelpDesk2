/*set database collation 'UTF8_GENERAL_CI';*/
SET CHARSET UTF8;
INSERT INTO USER(id, first_name, last_name, login, email)
VALUES(1, 'Misha', 'Yatskevich', 'Pan', '1somemail');



INSERT INTO PASSWORDS(id, password, enabled, user_id)
VALUES(1, '100', true, 1);


INSERT INTO USERROLES(id, role_name)
VALUES(1, 'ROLE_MANAGER');



INSERT INTO USER_ROLES(user_id, userrole_id)
VALUES(1, 1);

