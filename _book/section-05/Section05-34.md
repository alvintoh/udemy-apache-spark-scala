# 34. Introducing Amazon Elastic MapReduce

## RUNNING ON A CLUSTER

### Distributed Spark
* This is the layout of a Spark Job

| Layout of Spark Job		|
|:-------------------------:|:-------------------------:|:-------------------------:|
|							| Spark Driver				|							|	
|							| Cluster Manager			|							|
| Cluster Worker/ Executors | Cluster Worker/ Executors	| Cluster Worker/ Executors	|

### Other Spark-Submit Parameters
* --master
 * yarn - for running a YARN / Hadoop cluster
 * hostname:port - for connecting to a master on a Spark standalone cluster
 * mesos://masternode:port
 * A master in your SparkConf will override this!!!

* --num-executors
 * Must set explicitly with YARN, only 2 by default

* --executor-memory
 * Make sure you don't try to use more more memory than you have

* --total-executor-cores

### Amazon Elastic MapReduce
* A quick way to create a cluster with Spark, Hadoop, and YARN pre-installed
* You pay by the hour-instance and for network and storage IO
* Let's run our one-million-ratings movie recommender on a cluster

### Let's Use Elastic MapReduce
* Very quick and easy way to rent time on a cluster of your own
* Sets up a default spark configuration for you on top of Hadoop's YARN cluster manager
 * Buzzword alert! We're using Hadoop! Well, a part of it anyhow.

* Spark also has a built-in standalone cluster manager, and scripts to set up its own EC2-based cluster.
 * But the AWS console is even easier.

* Spark on EMR isn't really expensive, but it's not cheap either.
 * Unlike MapReduce with MRJob, you'll be using m3.xlarge instances.
 * I racked up about $30 running Spark jobs over a few hours preparing this course.
 * You also have to remember to shutdown your clusters when you're done, or else...
 * So you might just want to watch, and not follow along.

* Make sure things run locally on a subset of your data first.

### Getting Set Up On EMR
* Make an Amazon Web Services account
* Create an EC2 key pair and download the .pem file
* On Windows, you'll need a terminal like PUTTY
 * For PUTTY, need to convert the .pem to a .ppk private key file

* I'll walk you through this now.