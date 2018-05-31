# 10. The Resilient Distributed Dataset

## INTRODUCING RDD'S
By Frank Kane

### RDD
* Resilient
* Distributed
* Dataset

### The Sparkcontext
* Created by your driver program
* Is reponsible for making RDD's resilient and distributed
* Creates RDD's
* The Spark shell creates a "sc" object for you

### Creating RDD's
* val nums = parallelize(List(1, 2, 3, 4))
* sc.textFile("file:///c:/users/frank/gobs-a-text.txt")
 * or s3n://, hdfs://


* hiveCtx = HiveContext(sc)
 * rows = hiveCtx.sql("SELECT name, age FROM users")


* Can also create from:
 * JDBC
 * Cassandra
 * HBase
 * Elasticsearch
 * JSON, CSV, sequence files, object files, various compressed formats

### Transforming RDD's
* map
* flatmap
* filter
* distinct
* sample
* union, intersection,subtract, cartesian

### Map Example
* val rdd = sc.parallelize(List(1, 2, 3, 4))
* val squares = rdd.map(x => x * x)
* This yields 1, 4, 9, 16

### Functional Programming
Many RDD methods accept a function as a parameter
```
	rdd.map(x -> x * x)
```

Is the same thing as
```
	def squareIt(x: Int): Int = {
		return x * x
	}

	rdd.map(squareIt)
```
There, you now understand functional programming

### RDD Actions
* collect
* count
* countByValue
* take
* top
* reduce
* ... and more ...

### Lazy Evaluation
* Nothing actually happens in your driver program until an action is called!