# 38. Troubleshooting, and Managing Dependencies

## TROUBLESHOOTING SPARK

### Troubleshooting Cluster Jobs Part I
* It is a dark art.
* Your master will run a console on port 4040
 * But in EMR, it's next to impossible to actually connect to it from outside
 * If you have your own cluster running on your network, life's a little easier in that respect
 * Let's take a look on our local machine though

### Activity
* I would run the MovieSimilarities script on my desktop that processes the 100k ratings locally
* In this case, I'm running on my local host, the IP address of your own machine is always 127.0.0.1
* So to run the Spark script in the SparkScalaCourse, I will type in this command
```
	spark-submit --class com.sundogsoftware.spark.MovieSimilarities MovieSims.jar 50
```
* To access the SparkUI, the address is http://127.0.0.1:4040 when the spark-submit command is running
* You can open all the other tabs like Stages, Storage, Environment and Executors to see the status and progress of the Spark Job
* You can also see how Spark actually broke up the job in individual stages and you can visualize those stages independently
* Remember stages represent points at which SPARK needs to shuffle data.
* So the more stages you have, the more data is being shuffled around so it is least efficient
* Your job is running so there could be opportunities to explicitly partition things to avoid shuffling and reduce the number of stages and by studying what's going on here that can be a useful way of figuring it out.
* The environment tab gives you some general troubleshooting information about the Spark Job and the environment itself
* Also useful things the path for Java. So again if you're having trouble with dependencies and trying to figure out why certain libraries loading that might tell you why and more general information about the configuration of job on your machine
* We have the executors tab here that's actually telling how many executors are actually running and just running on my local desktop.
* It may be surprising that you only have 1 executor, but Spark decided to allocate you 1 because Spark decided that you didn't actually need more than one executor to complete this job.
* But if you were on a cluster, and you saw only 1 executor. That would be a sign of trouble
* That would probably mean that things are configured right on your cluster.
* Maybe you left something in the configuration on the script itself to run locally or restrict the number
* So thats the SparkUI in a nutshell and the Job is already completed. So let's kick it off again.

### Troubleshooting Cluster Jobs Part II
* Logs
 * In standalone mode, they're in the web UI
 * In YARN though, the logs are distributed. You need to collect them after the fact using yarn logs -applicationID `<app ID>`

* While your driver script runs, it will log errors like executors failing to issue heartbeats
 * This generally means you are asking too much of each executor.
 * You may need more of them -ie, more machines in your cluster
 * Each executor may need more memory
 * Or use partitionBy() to demand less work from individual executors by using smaller partitions.

### Managing Dependencies
* Remember your executors aren't necessarily on the same box as your driver script
* Use broadcast variables to share data outside of RDD's
* Need some Java or Scala package that's not pre-loaded on EMR?
 * Bundle them into your JAR with sbt assembly
 * Or use -jars with spark-submit to add individual libraries that are on the master
 * Try to avoid suing obscure packages you don't need in the first place. Time is money on your cluster, and you're better off not fiddling with it.