Create the Presto image

create MySQL for demo
docker exec -it bcsc-mysql /bin/bash
mysql -u root -p
	Note: the password is Password*8
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'Password*8';
FLUSH PRIVILEGES;


download  https://repo1.maven.org/maven2/com/facebook/presto/presto-server/0.194/presto-server-0.194.tar.gz
download jdk: server-jre-8u162-linux-x64.tar.gz
copy both to the docker/presto folder

cd to location of Presto dockerfile, i.e. cd /home/pete/development/gitrepo/learn-presto/docker/presto
docker build -t presto:0.194 .

just to check out the structure, create a container
docker run -it --rm presto:0.194

Remove danglers from iterative builds
docker rmi $(docker images -qa -f 'dangling=true')

launch the cluster
docker-compose up

Update the MySQL root to be able to connect from any address
docker exec -it presto-mysql /bin/bash
mysql -u root -p
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'Password*8';
FLUSH PRIVILEGES;

Load the Employees test database
Download zip from https://github.com/datacharmer/test_db to docker/presto/data
Note that this is now accessible from presto-mysql as /tmp/data
Unzip the file to docker/presto/data
cd /tmp/data/test_db-master

mysql -t -u root -p < employees.sql

Delete test_db-master from presto/data

Download the CLI https://prestodb.io/docs/current/installation/cli.html

Setup Hive to use MySQL as Schema Storage
1. review this link for hive-site.xml https://stackoverflow.com/questions/35449274/java-lang-runtimeexception-unable-to-instantiate-org-apache-hadoop-hive-ql-metahadoo
2. Review presto-hive/conf-hive and notice the volume mapping 
3. Review hadoop/dockerfile, note that MySQL connector is copied to /usr/local/hive/lib
	3.1 Note that I tried MySQL Connector 5 first, then staring hive metastore just hung, so used mysql-connector-java-8.0.11.jar
4. tried hive command, got: runtime exception on org.apache.hadoop.hive.ql.metadata.SessionHiveMetaStoreClient
5. as per that link above, opened MySQL session and ran this sql: /usr/local/hive/scripts/metastore/upgrade/mysql/hive-schema-2.3.0.mysql.sql
	copied the schema files hive-schema-2.3.0.mysql.sql and hive-txn-schema-2.3.0.mysql.sql to ./data
	docker exec -it presto-hive-mysql /bin/bash
	cd /tmp/data
	mysql -u root -p
	use metastore;
	source hive-schema-2.3.0.mysql.sql;
6. Manually start hive: hive --service metastore
8. Restarted cluster, exec'ed into namenode, ran queries ok: "show databases", "show tables", "select * from asdf" worked as expected


Hive setup, verify with basic table
+ this link will help http://www.informit.com/articles/article.aspx?p=2755708&seqNum=3
+ disabled file system permissions - hdfs-site.xml, dfs.permissions.enabled, just makes it easier during development
+ docker exec -it presto-hive-mysql /bin/bash
+ hdfs dfs -mkdir -p /user/pete
+ open hive
	+ create database db_people
	+ run the create table in person_create_table.sql
	+ load the p1.tsv file:  LOAD DATA LOCAL INPATH '/tmp/blockchain-shared/presto-hive/data/person/p1.tsv' OVERWRITE INTO TABLE db_people.person;
	+ run a couple of queries to verify
		select * from db_people.person;
		select * from db_people.person where birth_date>'1965-01-01';
	
./presto --server localhost:8090 --catalog hive_bcsc --schema db_bcsc
 
hive --service metastore 
hive --service hiveserver2 &

echo 'myuser:mypass' | chpasswd


+++ 2018-05-18 - new step by step instructions

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

Create the database and tables in Hive catalog, path in HDFS
docker exec -it bcsc-hive-namenode /bin/bash
hdfs dfs -mkdir -p /user/bcsc
cd /tmp/blockchain-shared/presto-hive/data
beeline -f create_bcsc_tables.sql
Open beeline and verify the tables are there
beeline
use db_bcsc;
show tables;
	note; to exit beeline, use: !quit
exit from the docker session

Run GenSimSuppliers, verify the data is loaded in HDFS 
bring up Hadoop console: http://localhost:50070
Click on Utilities, Browse the file system
verify files exist under /user/bcsc/bcsc_data
Verify files can be queried via Presto
cd /home/pete/development/server
./presto-cli --server localhost:8090 --catalog cat_bcsc --schema db_bcsc
show tables
	verify results



	

 
