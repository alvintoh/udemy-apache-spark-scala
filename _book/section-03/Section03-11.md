# Ratings Histogram Walkthrough

## UNDERSTANDING THE RATINGS COUNTER CODE
By Frank Kane

### Activity
* For this Activity, we will be using RatingsCounter.scala from SparkScalaCourse package
* Select Run Configuration to run RatingsCounter.scala
* Make sure RatingCounter is selected in the Name section
* The script is actually going through 1 hundred thousand movie ratings and counting the distribution for each of the different scores

### Import What We Need
```
	package com.sundogsoftware.spark

	import org.apache.spark._
	import org.apache.spark.SparkContext._
	import org.apache.log4j._
```

### Set Up Our Context
```
	val sc = new SparkContext("local[*]", "RatingsCounter")
	// local[*], the [*] means that you are actually using all the cpu to process all the cores to do all the distributed processing
```

### Load The Data
```
	val lines = sc.textFile("../ml-100k/u.data")
```

### Extract (Map) The Data We Care About
```
	val ratings = lines.map(x => x.toString().split("\t")(2))
	// We are splitting the lines by tab delimited and extracting the 3rd value
```

### Perform An Action: Count By Value
```
	val ratings = ratings.countByValue()
```

### Sort and Display The Results
```
	val sortedResults = results.toSeq.sortBy(_._1)
	// This is sorting by the first field in ratings
```

### It's Just That Easy. 