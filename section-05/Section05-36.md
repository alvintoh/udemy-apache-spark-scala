# 36. Partitioning

## PARTITIONING RDD'S

### Let's Look At The Code
* If you look at MoviesSimilarities1M.scala script in your Spark Eclipse IDE, there were a few things that we have changed.
* There was one line of code that talks about partitioning on a fairly large cluster
```
	// Now key by (movie1, movie2) pairs.
	val moviePairs = uniqueJoinedRatings.map(makePairs).partitionBy(new HashPartitioner(100))
```
	
### Optimizing For Running On A Cluster: Partitioning
* Spark isn't totally magic - you need to think about how your data is partitioned
* Running our movie similarity script as-is might not work at all.
 * That self-join is expensive, and Spark won't distribute it on its own.

* Use .partitionBy() on an RDD before running a large operation that benefits from partitioning
 * Join(). cogroup(), groupWith(), join(), leftOuterJoin(), rightOuterJoin(), groupByKey(), combineByKey(), and lookup()
 * Those operations will preserve your partitioning in their result too.

### Choosing A Partition Size
* Too few partitions won't take full advantage of your cluster
* Too many results in too much overhead froms shuffling data
* At least as many partitions as you have cores, or executors that fit within your available memory
* partitionBy(100) is usually a reasonsable place to start for large operations.
```
	// Filter out duplicate pairs
	val uniqueJoinedRatings = joinedRatings.filter(filterDuplicates)

	// Now key by (movie1, movie2) pairs.
	val moviePairs = uniqueJoinedRatings.map(makePairs).partitionBy(new HashPartitioner(100))

	// We now have (movie1, movie2) => (rating1, rating2)
	// Now collect all ratings for each movie pair and compute similarity
	val moviePairRatings = moviePairs.groupByKey()
```