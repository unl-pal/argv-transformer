CREATE TABLE Credentials (username VARCHAR(20), passwd VARCHAR(32), logincount INTEGER);
INSERT INTO Credentials VALUES ('troosevelt', 'jabberwock', 0); 
INSERT INTO Credentials VALUES ('tjefferson', 'mockturtle', 0); 
CREATE TABLE Groups (username VARCHAR(20), groupname VARCHAR(20));
INSERT INTO Groups VALUES ('troosevelt', 'registereduser');
INSERT INTO Groups VALUES ('troosevelt', 'invitedguest');
INSERT INTO Groups VALUES ('tjefferson', 'invitedguest');
