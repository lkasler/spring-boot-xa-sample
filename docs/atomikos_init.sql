CREATE DATABASE atomikos_one;
CREATE USER 'atomikos_one'@'localhost' IDENTIFIED BY 'atomikos_one';
GRANT ALL PRIVILEGES ON databaseName.* TO 'atomikos_one'@'localhost';
FLUSH PRIVILEGES;


CREATE DATABASE atomikos_two;
CREATE USER 'atomikos_two'@'localhost' IDENTIFIED BY 'atomikos_two';
GRANT ALL PRIVILEGES ON databaseName.* TO 'atomikos_two'@'localhost';
FLUSH PRIVILEGES;

QUIT
