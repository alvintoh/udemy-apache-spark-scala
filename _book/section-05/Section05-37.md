# 37. Best Practices for Running on a Cluster

## RUNNING ON A CLUSTER

### Specifying Memory Per Executor
* Just use an empty, default SparkConf in your drive - this way, we'll use the defaults EMR sets up instead, as well as any command-line options you pass into spark-submit from your master node
* If executors start failing, you may need to adjust the memory each executor has. For example:
```
	spark-submit --executor-memory 1g MovieSimilarities1M.py 260
	(from the master node of our cluster.)
```

### Specifying A Cluster Manager
* Can use -master yarn to run on a YARN cluster
* EMR sets this up by default

### Running On A Cluster
* Get your scripts & data someplace where EMR can access them easily
 * AWS's S3 is a good choice - just use s3n://URL's when specifying file paths, and make sure your file permissions make them accessible

* Spin up an EMR cluster for Spark using the AWS console
 * Billing starts now!!

* Get the external DNS name for the master node, and log into it using the "hadoop" user account and your private key file
* Copy your driver program's JAR and any files it needs
 * ie, using aws s3 cp s3://bucket-name/filename ./

* Run spark-submit and watch the output!
* REMEMMBER TO TERMINATE YOUR CLUSTER WHEN YOU'RE DONE