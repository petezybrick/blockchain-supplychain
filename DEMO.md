**Rebuild all**
cd /home/pete/development/gitrepo/blockchain-supplychain
./buildall.sh


**Demo Sequence**
Open terminal session
cd /home/pete/development/gitrepo/blockchain-supplychain/docker
Start all containers
./docker-env-start.sh
wait a minute or two
create n tabs


Open browser, bcsc folder
open tabs for webclient, zeppelin, spark
Lot numbers start with 20180108-1, 10 per day, every 7 days

**Data Refresh**
TODO: truncate all tables, remove all ORC files
Generate data - GenSimSuppliers
Run GenSimSuppliers, verify the data is loaded in HDFS 
bring up Hadoop console: http://localhost:50070
Click on Utilities, Browse the file system
verify files exist under /user/bcsc/bcsc_data
Verify files can be queried via Presto
Verify tables can be queried via MySQL
cd /home/pete/development/server
	
Run GenSimComplaints, verify 

Populate the complaints to find the culprit
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


Slides

Monster query

select * from lot_canine lc
inner join map_lot_canine_supplier_blockchain mlcsb on lc.lot_canine_uuid=mlcsb.lot_canine_uuid
inner join supplier_blockchain sbc on sbc.supplier_blockchain_uuid=mlcsb.supplier_blockchain_uuid
inner join supplier_block sb on sb.supplier_blockchain_uuid=sbc.supplier_blockchain_uuid
inner join supplier_block_transaction sbt on sbt.supplier_block_uuid=sb.supplier_block_uuid
inner join supplier_transaction st on st.supplier_block_transaction_uuid=sbt.supplier_block_transaction_uuid
inner join supplier s on s.supplier_uuid=st.supplier_uuid
where lc.manufacturer_lot_number='20180108-1'


Only the ingredients for a given lot


