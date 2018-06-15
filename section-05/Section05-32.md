# 32. [Activity] Using spark-submit to run Spark driver scripts

## RUNNING SPARK ON A CLUSTER
Using EMR, tuning performance on a cluster.

### Running With Spark-Submit
* Make sure there are no paths to your local filesystem used in your script! That's what HDFS, S3, etc. are for.
* Package up your Scala project into a JAR file (using Export in the IDE)
* You can now use spark-submit to execute your driver script outside of the IDE
```
	spark-submit -class <class object that contains your main function>
		--jars <paths to any dependencies>
		--files <files you want placed alongside your application>
		<your JAR file> 
```

### Let's Try It Out
```
	spark-submit -class com.sundogsoft.spark.PopularMovies PopularMovies.jar
```

### Activity
* To export your package as a JAR
* Right click on your project com.sundogsoftware.spark and click export
* Select a JAR file and press next and save the JAR file as File Name: PopularMovies.jar and press finish
* Next open your cmd and run as Administrator and cd to SparkScalaCourse folder
```
	cd C:/SparkScala/SparkScalaCourse
```

* This is the spark-submit command to execute that PopularMovies class
```
	spark-submit --class com.sundogsoftware.spark.PopularMovies PopularMovies.jar
```
* From there you should be able to see the output on your cmd