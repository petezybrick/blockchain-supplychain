Create the Presto image

create MySQL for demo


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
	
	

 
 
hive --service hiveserver2 &

echo 'myuser:mypass' | chpasswd

