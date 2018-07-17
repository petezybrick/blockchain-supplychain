Initialize Cassandra
	docker-compose up
	docker exec -it bcsc-cassandra1 /bin/bash
	cd /usr/apache-cassandra-3.9/
	./bin/cqlsh bcsc-cassandra1
	CREATE KEYSPACE bcsc WITH replication = {'class':'SimpleStrategy','replication_factor':3};
	Run InitLoadConfig.java
	use bcsc;
	select * from bcsc_config;
	

Bring up MySQL the first time
	docker-compose up
Exec into the container
	docker exec -it bcsc-mysql /bin/bash
bring up mysql command line
	mysql -p
		Note: password is Password*8
	source /tmp/blockchain-shared/mysql/init.sql
	source /tmp/blockchain-shared/mysql/supplier-create-tables.sql
exit MySQL
	exit;
exit container
	exit

Create Tomcat9 Image
review dockerfile
open a terminal window, cd to tomcat folder
docker build -t tomcat:9.0.6 .


Zeppelin
The Zeppelin .7 image I created has MySQL connector jar already installed, if not you will need to copy it to 
/usr/share/zeppelin-${ZEPPELIN_VERSION}/interpreter/jdbc, in this case /usr/share/zeppelin-0.7.0/interpreter/jdbc

MySQL
Configure MySQL configuration
Note: if 'docker-compose rm' is run, then these steps must be repeated 
Connect to Zeppelin: http://localhost:8082
Add MySQL interpreter
	In the top right corner, click on the down arrow next to "anonymous" and select Interpreter
	Entries:
		Interpreter Name: iote2e-mysql
		Interpreter Group: jdbc
		default.driver: com.mysql.jdbc.Driver
		defaul.url: jdbc:mysql://bcsc-mysql:3306/db_supplier
		default.user: supplier
		default.password: Password*8
	Press Save
Create a Notebook: 
	Click on Notebook dropdown, select Create new note
	Note Name: bcsc
	Default interpreter: bcsc-mysql
	Enter: SHOW TABLES
	Press: the "READY" arrow on the right
	Verify success, if not then research the error
	Enter queries against other tables, do a SHOW TABLES to list the available tables as these will change over time.


Presto
Copy the Presto JDBC driver into /usr/share/zeppelin-0.7.0/interpreter/jdbc.  Here are the steps
1. copy presto-jdbc-0.194.jar into docker/blockchain-shared
2. exec into Zeppelin: docker exec -it bcsc-zeppelin /bin/bash
3. cp /tmp/blockchain-shared/presto-hive/presto-jdbc-0.194.jar /usr/share/zeppelin-0.7.0/interpreter/jdbc/
4. exit from bcsc-zeppelin
5. stop/start the bcsc-zeppelin container
6. configure Zeppelin JDBC connection for Presto
Add Presto interpreter
	In the top right corner, click on the down arrow next to "anonymous" and select Interpreter
	Entries:
		Interpreter Name: bcsc-presto
		Interpreter Group: jdbc
		default.driver: com.facebook.presto.jdbc.PrestoDriver
		defaul.url: jdbc:presto://bcsc-presto-coordinator:8080/cat_bcsc_mysql/db_supplier
		default.user: test
		default.password: 
	Press Save

Note: Had to downgrade Presto JDBC on Zeppelin from 0.194 to 0.179 due to properties issue that started at 0.180
	https://issues.apache.org/jira/browse/ZEPPELIN-2891 
	
