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

