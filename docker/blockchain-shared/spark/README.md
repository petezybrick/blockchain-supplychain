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
  




