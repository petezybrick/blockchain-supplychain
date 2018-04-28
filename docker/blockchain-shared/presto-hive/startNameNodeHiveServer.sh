#!/bin/bash
hdfs namenode &
sleep 5
hiveserver2
