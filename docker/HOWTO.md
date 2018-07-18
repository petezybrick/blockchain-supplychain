Initialize Cassandra
	docker-compose -f docker-compose-cassandra.yml up
	docker exec -it bcsc-cassandra1 /bin/bash
	cd /usr/apache-cassandra-3.9/
	./bin/cqlsh bcsc-cassandra1
	CREATE KEYSPACE bcsc WITH replication = {'class':'SimpleStrategy','replication_factor':3};
	Run InitLoadConfig.java
	use bcsc;
	select * from bcsc_config;
	

Bring up MySQL the first time
	docker-compose -f docker-compose-mysql.yml up
Exec into the container
	docker exec -it bcsc-mysql /bin/bash
bring up mysql command line
	mysql -p
		Note: password is Password*8
	GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'Password*8';
	FLUSH PRIVILEGES;
	source /tmp/bcsc-shared/mysql-init/init.sql
	source /tmp/bcsc-shared/mysql-init/supplier-create-tables.sql
exit MySQL
	exit;
exit container
	exit

Install/Configure Presto - see presto-hive/howto-presto.md
Install/Configure Spark - see spark/howto-spark.md
Install/Configure Zeppelin - see howto-zeppelin.md
Start the Cluster
./docker-env-start.sh

Generate data - GenSimSuppliers
Run GenSimSuppliers, verify the data is loaded in HDFS 
bring up Hadoop console: http://localhost:50070
Click on Utilities, Browse the file system
verify files exist under /user/bcsc/bcsc_data
Verify files can be queried via Presto
cd /home/pete/development/server
./presto-cli --server localhost:8090 --catalog cat_bcsc_hive --schema db_bcsc
show tables
	verify results
	
Run GenSimComplaints, verify 




Zeppelin
The Zeppelin .7 image I created has MySQL connector jar already installed, if not you will need to copy it to 
/usr/share/zeppelin-${ZEPPELIN_VERSION}/interpreter/jdbc, in this case /usr/share/zeppelin-0.7.0/interpreter/jdbc

Start Zeppelin
docker-compose -f docker-compose-zeppelin.yml

MySQL
Configure MySQL configuration
Note: if 'docker-compose rm' is run, then these steps must be repeated 
Connect to Zeppelin: http://localhost:8082
Add MySQL interpreter
	In the top right corner, click on the down arrow next to "anonymous" and select Interpreter
	Entries:
		Interpreter Name: bcsc-mysql
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
Note: Presto must be up and running, see 
Copy the Presto JDBC driver into /usr/share/zeppelin-0.7.0/interpreter/jdbc.  Here are the steps
1. copy presto-jdbc-0.179.jar into docker/bcsc-shared
2. exec into Zeppelin: docker exec -it bcsc-zeppelin /bin/bash
3. cp /tmp/bcsc-shared/presto-hive/presto-jdbc-0.179.jar /usr/share/zeppelin-0.7.0/interpreter/jdbc/
4. exit from bcsc-zeppelin
5. stop/start the bcsc-zeppelin container
docker-compose -f docker-compose-zeppelin.yml stop
docker-compose -f docker-compose-zeppelin.yml start
6. configure Zeppelin JDBC connection for Presto
Add Presto interpreter
	In the top right corner, click on the down arrow next to "anonymous" and select Interpreter
	Entry: bcsc-presto-mysql:
		Interpreter Name: bcsc-presto
		Interpreter Group: jdbc
		default.driver: com.facebook.presto.jdbc.PrestoDriver
		defaul.url: jdbc:presto://bcsc-presto-coordinator:8080/cat_bcsc_mysql/db_supplier
		default.user: test
		default.password: 
	Press Save
	Entry: bcsc-presto-hive:
		Interpreter Name: bcsc-hive
		Interpreter Group: jdbc
		default.driver: com.facebook.presto.jdbc.PrestoDriver
		defaul.url: jdbc:presto://bcsc-presto-coordinator:8080/cat_bcsc_hive/db_bcsc
		default.user: test
		default.password: 
	Press Save
Note: Had to downgrade Presto JDBC on Zeppelin from 0.194 to 0.179 due to properties issue that started at 0.180
	https://issues.apache.org/jira/browse/ZEPPELIN-2891 
	
	
Populating the complaints to find the culprit
cd ~/development/server/spark-2.3.0-bin-hadoop2.7

./bin/spark-submit \
  --class com.petezybrick.bcsc.service.sparksql.CustomerComplaintService \
  --deploy-mode cluster \
  --master spark://localhost:6066 \
  --executor-memory 2G \
  --executor-cores 1 \
  --total-executor-cores 4 \
  /tmp/bcsc-shared/jars/bcsc-service-1.0.0.jar \
  "dev_docker" "bcsc-cassandra1" "bcsc"
