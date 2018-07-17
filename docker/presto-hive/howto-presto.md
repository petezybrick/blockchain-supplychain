
Build the hadoop hive image
cd docker/hadoop-hive
download hadoop-2.9.0.tar.gz, apache-hive-2.3.2-bin.tar.gz, mysql-connector-8.0.11.jar
docker build -t hadoop-hive:2.9.0 .

Build the Presto/Hive image
review presto-hive dockerfile
open a terminal session
cd /home/pete/development/gitrepo/blockchain-supplychain/docker/blockchain-shared/presto-hive
docker build -t hadoop-presto:2.9.0 .

Start the cluster, there will be metastore errors since the database hasn't been configured yet
docker-compose --file docker-compose-presto-hive.yml up
I copied the schema files hive-schema-2.3.0.mysql.sql and hive-txn-schema-2.3.0.mysql.sql to ./data
docker exec -it bcsc-hive-mysql /bin/bash
cd /tmp/blockchain-shared/presto-hive/data
mysql -u root -p
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'Password*8';
FLUSH PRIVILEGES;
create database metastore;
use metastore;
source hive-schema-2.3.0.mysql.sql;
Stop and restart the cluster
docker-compose --file docker-compose-presto-hive.yml up
Final log messages from coordinator and worker nodes should be:
    	======== SERVER STARTED ========	

Create path in HDFS
docker exec -it bcsc-hive-namenode /bin/bash
hdfs dfs -mkdir -p /user/bcsc

Create the tables in Hive metastore and hdfs
run GenHiveCreateTables from your IDE
	Args: "docker/bcsc-shared/mysql-init/supplier-create-tables.sql"
	EnvVars:
		CONTACT_POINT 127.0.0.1
		ENV dev_docker
		KEYSPACE_NAME bcsc
From the bcsc-hive-namenode session, beeline and verify the tables are there
beeline
use db_bcsc;
show tables;
	note; to exit beeline, use: !quit
exit from the docker session




	

 
