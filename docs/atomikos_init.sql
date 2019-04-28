CREATE DATABASE atomikos_one;
CREATE USER 'atomikos_one'@'%' IDENTIFIED BY 'atomikos_one';
GRANT ALL PRIVILEGES ON atomikos_one.* TO 'atomikos_one';
FLUSH PRIVILEGES;

CREATE DATABASE atomikos_two;
CREATE USER 'atomikos_two'@'%' IDENTIFIED BY 'atomikos_two';
GRANT ALL PRIVILEGES ON atomikos_two.* TO 'atomikos_two';
FLUSH PRIVILEGES;

QUIT
