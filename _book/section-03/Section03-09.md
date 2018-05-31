# Introduction to Spark

## WHAT IS SPARK?
"A fast and general engine for large-scale data processing"

### It's Scalable
| Process Flow for Spark 		|
|-------------------------------|						
|								|								| Executor -Cache Tasks 		|
| Driver Program -Spark Context | Cluster Manager (Spark,YARN) 	| Executor -Cache Tasks			|
| 								|								| Executor -Cache Tasks			|

### It's Fast
* "Run programs up to 100x faster than Hadoop MapReduce in memory, or 10x faster on disk."
* DAG Engine (directed acyclic graph) optimizes workflows

### It's Hot
* Amazon
* Ebay: log analysis and aggregation
* NASA JPL: Deep Space Network
* Groupon
* TripAdviser
* Yahoo
* Many others: https://cwiki.apache.org/confluence/display/SPARK/Powered+By+Spark

### It's Not That Hard
* Code in Python, Java, or Scala
* Build around one main concept: the Resilient Distributed Dataset (RDD)

### Components Of Spark
| SPARK CORE 			|
|-----------------------|
| Spark Streaming		| Spark SQL					| MLLLib					| GraphX					|

### This Course Uses Scala
* Why Scala?
 * Spark itself is written in Scala
 * Scala's functional programming model is a good fit for distributed processing
 * Gives you fast performance (Scala compiles to Java bytecode)
 * Less code & boilerplate stuff than Java
 * Python is slow in comparison


* But...
 * You probably don't know Scala
 * So we'll have to learn the basics first.
 * It's not as hard you think!

### Fear Not
* Scala code in Spark looks a LOTlike Python code.
* Python code to square numbers in a data set:
```
	nums = sc.parallelize([1, 2, 3, 4])
	squared = nums.map[lambda x: x * x].collect()
```

* Scala code to square numbers in a data set:
```
	val nums = sc.parallelize(List(1, 2, 3, 4))
	val squared = nums.map(x -> x * x).collect()
```