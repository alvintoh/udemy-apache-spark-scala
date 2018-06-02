# 13. Key /Value RDD's,and the Average Friends by Age example

## KEY /VALUE RDD'S
And the "friends by age" example

### RDD'S Can Hold Key /Value Pairs
* For example: number of friends by age
* Key is age, value is number of friends
* Instead of just a list of ages or a list of # of friends, we can store (age, # friends) etc...

### Creating A Key /Value RDD
* Nothing special in Scala, really
* Just map pairs of data into the RDD using tuples. For example.
```
	totalsByAge = rdd.map(x => (x, 1))
```

* Voila, you now have a key /value RDD.
* OK to have tuples or other objects as values as well.

### Spark Can Do Special Stuff With Key /Value Data
* reduceByKey(): combine values with the same key using some function. rdd.reduceByKey((x, y) => x + y) adds them up.
* groupByKey(): Group values with the same key
* sortByKey(): Sort RDD by key values
* keys(), values() - Create an RDD of just the keys, or just the values

### You Can Do SQL-Style Joins On Two Key /Value-RDD's
* join, rightOuterJoin, leftOuterJoin, cogroup, subtractByKey
* We 'll look at an example of this later.

### Mapping Just The Values Of A Key /Value RDD?
* With Key /Value data, use mapValues() and flatMapValues() if your transformation doesn't affect the keys.
* It's more efficient

### Friends By Age Example
* Input Data: ID, name, age, number of friends
 * 0, Will, 33, 385
 * 1, Jean-Luc, 33, 2
 * 2, Hugh, 55, 22
 * 3, Deanna, 40, 465
 * 4, Quark, 68, 21

### Parsing (Mapping) The Input Data
```
	def parseLine(line: String) = {
		val fields = line.split(",")
		val age = fields(2).toInt
		val numFriends = fields(3).toInt
		(age, numFriends)
	}

	val lines = sc.textFile("../fakefriends.csv")
	val rdd = lines.map(parseLine)
```

* Output is key/value pairs of (age, numFriends):
 * 33, 385
 * 33, 2
 * 55, 221
 * 40, 465
 * ...

### Count Up Sum Of Friends And Number Of Entries Per Age
```
	val totalsByAge = rdd.mapValues(x => (x, 1)).reduceByKey((x,y) => (x._1 + y._1, x._2 + y._2))

	rdd.mapValues(x => (x, 1))

	(33, 385) => (33, (385, 1))
	(33, 2) => (33, (2, 1))
	(55, 221) => (55, (221, 1))

	reduceByKey((x, y) => (x, 1 + y, 1, 2 + x, 2))

	Adds up all values for all unique key!

	(33, (387, 2))
```

### Compute Averages
```
	val averagesByAge = totalsByAge.mapValues(x => x._1/ x._2)
	(33, (387, 2)) => (33, 193.5)
```

### Collect And Display The Results
```
	val results = averagesByAge.collect()
	results.sorted.foreach(println)
```
