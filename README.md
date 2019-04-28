# spring-boot-jta-atomikos-sample
spring-boot-jta-atomikos-sample

## Description
This is a sample project using spring boot, jta, and atomikos to show how the Distributed Transaction work.

This project has two mysql dbs, db:atomikos_one keep the capital info, and db:atomikos_two keep the redpacket info.
And the main testcase will transfer capital amount from capital account "1" to capital account "2", and transfer redpacket amount from redpacket account "2" to redpacket account "1" at the same time in one transaction.

## Database

```
docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:tag
```
You have to start the following docker mysql command in the root dirctory

```
docker run -d -p 13306:3306 --name xa-mysql -v ${PWD}/docs:/docker-entrypoint-initdb.d/ -e MYSQL_ROOT_PASSWORD=supersecret mysql:5.5
```


```

docker exec -it my-mysql bash
mysql -u root -proot




CREATE DATABASE exampleDatabase;
CREATE USER 'example'@'localhost' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON databaseName.* TO 'example'@'%';
FLUSH PRIVILEGES;
QUIT



```

Database access:
jdbc:mysql://localhost:13306
jdbc:mysql://localhost:13306/atomikos_one
jdbc:mysql://localhost:13306/atomikos_two

## How to run?
1. git clone https://github.com/YihuaWanglv/spring-boot-jta-atomikos-sample.git
2. To import db script (docs folder) from the source folder run: ```docker run -d -p 13306:3306 --name xa-mysql -v ${PWD}/docs:/docker-entrypoint-initdb.d/ -e MYSQL_ROOT_PASSWORD=supersecret mysql:5.5```
3. import project into ide and run App.java or build project and run the jar
4. visit utl:http://localhost:9080/save to see saveTest

## Reference
- http://fabiomaffioletti.me/blog/2014/04/15/distributed-transactions-multiple-databases-spring-boot-spring-data-jpa-atomikos/
- https://github.com/fabiomaffioletti/mul-at