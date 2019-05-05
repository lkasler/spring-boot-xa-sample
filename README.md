# spring-boot-jta-atomikos-sample
spring-boot-jta-atomikos-sample

## Description
This is a sample project using spring boot, jta, and atomikos to show how the Distributed Transaction work.

This project has two mysql dbs, db:atomikos_one keep the capital info, and db:atomikos_two keep the redpacket info.
And the main testcase will transfer capital amount from capital account "1" to capital account "2", and transfer redpacket amount from redpacket account "2" to redpacket account "1" at the same time in one transaction.


Based on https://github.com/YihuaWanglv/spring-boot-jta-atomikos-sample

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

## ActiveMQ

To run the example:

```docker run --name='xa-activemq' -d  -e 'ACTIVEMQ_STATIC_QUEUES=queue1;queue2;queue3' -p 8161:8161  -p 61616:61616 -p 61613:61613  webcenter/activemq:5.14.3 ```

Open in browser: 
```http://localhost:8161/admin/queues.jsp```
admin/admin

To stop running ActiveMQ:

```docker rm -f xa-activemq```

Configuration details site:

```https://github.com/disaster37/activemq#queue```

Example usage:

```
docker run --name='activemq' -d \
-e 'ACTIVEMQ_NAME=amqp-srv1' \
-e 'ACTIVEMQ_REMOVE_DEFAULT_ACCOUNT=true' \
-e 'ACTIVEMQ_ADMIN_LOGIN=admin' -e 'ACTIVEMQ_ADMIN_PASSWORD=your_password' \
-e 'ACTIVEMQ_WRITE_LOGIN=producer_login' -e 'ACTIVEMQ_WRITE_PASSWORD=producer_password' \
-e 'ACTIVEMQ_READ_LOGIN=consumer_login' -e 'ACTIVEMQ_READ_PASSWORD=consumer_password' \
-e 'ACTIVEMQ_JMX_LOGIN=jmx_login' -e 'ACTIVEMQ_JMX_PASSWORD=jmx_password' \
-e 'ACTIVEMQ_STATIC_TOPICS=topic1;topic2;topic3' \
-e 'ACTIVEMQ_STATIC_QUEUES=queue1;queue2;queue3' \
-e 'ACTIVEMQ_MIN_MEMORY=1024' -e  'ACTIVEMQ_MAX_MEMORY=4096' \
-e 'ACTIVEMQ_ENABLED_SCHEDULER=true' \
-v /data/activemq:/data/activemq \
-v /var/log/activemq:/var/log/activemq \
-p 8161:8161 \
-p 61616:61616 \
-p 61613:61613 \
webcenter/activemq:5.14.3
```

### ActiveMQ parameters and configuration

Avaible Configuration Parameters
Please refer the docker run command options for the --env-file flag where you can specify all required environment variables in a single file. This will save you from writing a potentially long docker run command. Alternately you can use fig.

Below is the complete list of available options that can be used to customize your activemq installation.

ACTIVEMQ_NAME: The hostname of ActiveMQ server. Default to localhost

ACTIVEMQ_LOGLEVEL: The log level. Default to INFO

ACTIVEMQ_PENDING_MESSAGE_LIMIT: It is used to prevent slow topic consumers to block producers and affect other consumers by limiting the number of messages that are retained. Default to 1000

ACTIVEMQ_STORAGE_USAGE: The maximum amount of space storage the broker will use before disabling caching and/or slowing down producers. Default to 100 gb

ACTIVEMQ_TEMP_USAGE: The maximum amount of space temp the broker will use before disabling caching and/or slowing down producers. Default to 50 gb

ACTIVEMQ_MAX_CONNECTION: It's DOS protection. It limit concurrent connections. Default to 1000

ACTIVEMQ_FRAME_SIZE: It's DOS protection. It limit the frame size. Default to 104857600 (100MB)

ACTIVEMQ_ENABLED_SCHEDULER: Permit to enabled scheduler in ActiveMQ. Default to true

ACTIVEMQ_ENABLED_AUTH: Permit to enabled the authentification in queue and topic (no anonymous access). Default to false

ACTIVEMQ_MIN_MEMORY: The init memory in MB that ActiveMQ take when start (it's like XMS). Default to 128 (128 MB)

ACTIVEMQ_MAX_MEMORY: The max memory in MB that ActiveMQ can take (it's like XMX). Default to 1024 (1024 MB)

ACTIVEMQ_REMOVE_DEFAULT_ACCOUNT: It's permit to remove all default login on ActiveMQ (Webconsole, broker and JMX). Default to false

ACTIVEMQ_ADMIN_LOGIN: The login for admin account (broker and web console). Default to admin

ACTIVEMQ_ADMIN_PASSWORD: The password for admin account. Default to admin

ACTIVEMQ_USER_LOGIN: The login to access on web console with user role (no right on broker). Default to user

ACTIVEMQ_USER_PASSWORD: The password for user account. Default to user

ACTIVEMQ_READ_LOGIN: The login to access with read only role on all queues and topics.

ACTIVEMQ_READ_PASSWORD: The password for read account.

ACTIVEMQ_WRITE_LOGIN: The login to access with write role on all queues and topics.

ACTIVEMQ_WRITE_PASSWORD: The password for write account.

ACTIVEMQ_OWNER_LOGIN: The login to access with admin role on all queues and topics.

ACTIVEMQ_OWNER_PASSWORD: The password for owner account.

ACTIVEMQ_JMX_LOGIN: The login to access with read / write role on JMX. Default to admin

ACTIVEMQ_JMX_PASSWORD: The password for JMX account. Default to activemq

ACTIVEMQ_STATIC_TOPICS: The list of topics separated by comma witch is created when ActiveMQ start.

ACTIVEMQ_STATIC_QUEUES: The list of queues separated by comma witch is created when ActiveMQ start.

Advance configuration
For advance configuration, the best way is to read ActiveMQ documentation and created your own setting file like activemq.xml. Next, you can mount it when you run this image or you can create your own image (base on this image) and include your specifics config file.

The home of ActiveMQ is in /opt/activemq, so if you want to override all the setting, you can launch docker with -v /your_path/conf:/opt/activemq/conf



## How to run?
1. git clone https://github.com/YihuaWanglv/spring-boot-jta-atomikos-sample.git
2. To run mysql and import the db scripts (docs folder) run docker from the root folder which will initialize the databases: ```docker run -d -p 13306:3306 --name xa-mysql -v ${PWD}/docs:/docker-entrypoint-initdb.d/ -e MYSQL_ROOT_PASSWORD=supersecret mysql:5.5```
To remove to container use the docker command: ```docker rm -f xa-mysql```
To run activemq:
```docker run --name='xa-activemq' -d  -e 'ACTIVEMQ_STATIC_QUEUES=queue1;queue2;queue3' -p 8161:8161  -p 61616:61616 -p 61613:61613  webcenter/activemq:5.14.3 ```
To remove activemq
```docker rm -f xa-activemq```

Or simply use docker-compose
``` docker-compose up -d```

To shut down:
```docker-compose down```

3. import project into ide and run XaApp.java or build project and run the jar
4. visit url:
http://localhost:9080/save to see saveTest

## Reference
- http://fabiomaffioletti.me/blog/2014/04/15/distributed-transactions-multiple-databases-spring-boot-spring-data-jpa-atomikos/
- https://github.com/fabiomaffioletti/mul-at