download the tgz
cd to this folder
docker build -t spark:2.3.0 .

cd ~/development/server/spark-2.3.0-bin-hadoop2.7/

./bin/spark-submit \
  --class org.apache.spark.examples.SparkPi \
  --deploy-mode cluster \
  --master spark://localhost:6066 \
  --executor-memory 8G \
  --executor-cores 2 \
  --total-executor-cores 6 \
  /usr/spark-2.3.0/examples/jars/spark-examples_2.11-2.3.0.jar \
  1000
  
Console: http://localhost:8080/

Shell
cd ~/development/server/spark-2.3.0-bin-hadoop2.7
./bin/spark-shell --master spark://localhost:7077
 

./bin/spark-submit \
  --class com.petezybrick.bcsc.test.spark.example.JavaSparkSQLExample \
  --deploy-mode cluster \
  --master spark://localhost:6066 \
  --executor-memory 8G \
  --executor-cores 2 \
  --total-executor-cores 6 \
  /tmp/blockchain-shared/jars/bcsc-test-1.0.0.jar \
  "hdfs://bcsc-hive-namenode:9000/user/bcsc/examples-data"
  
 ./bin/spark-submit \
  --class com.petezybrick.bcsc.test.spark.example.JavaSparkSQLReadOrc \
  --deploy-mode cluster \
  --master spark://localhost:6066 \
  --executor-memory 2G \
  --executor-cores 1 \
  --total-executor-cores 4 \
  /tmp/blockchain-shared/jars/bcsc-test-1.0.0.jar \
  "thrift://bcsc-hive-namenode:9083"
  
 Kept running out of swap space, so increased as per 
 	https://askubuntu.com/questions/178712/how-to-increase-swap-space
 
  
  

