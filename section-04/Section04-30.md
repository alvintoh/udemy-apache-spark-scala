# 30. [Activity] Running the Similiar Movies Script using Spark's Cluster Manager

### Activity
* Import MoviesSimilarities.scala from resource folder into Eclipse-Scala IDE in SparkScala folder
* Open and take a look at MoviesSimilarities.scala

### Looking At The Code
* Looking at the main method where it runs
```
	println("\nLoading movie names...")
    val nameDict = loadMovieNames()

  	/** Load up a Map of movie IDs to movie names. */
  	def loadMovieNames() : Map[Int, String] = {
    
    	// Handle character encoding issues:
    	implicit val codec = Codec("UTF-8")
    	codec.onMalformedInput(CodingErrorAction.REPLACE)
  		codec.onUnmappableCharacter(CodingErrorAction.REPLACE)

  		// Create a Map of Ints to Strings, and populate it from u.item.
    	var movieNames:Map[Int, String] = Map()

    	val lines = Source.fromFile("../ml-100k/u.item").getLines()
    	for (line <- lines) {
       		var fields = line.split('|')
       		if (fields.length > 1) {
       			movieNames += (fields(0).toInt -> fields(1))
       		}
     	}
     	return movieNames
     }
```
* The method loadMovieNames() gets maps the data from u.item into a map of key MovieIDs, and value of MovieNames

```
	val data = sc.textFile("../ml-100k/u.data")

	// Map ratings to key / value pairs: user ID => movie ID, rating
	val ratings = data.map(l => l.split("\t")).map(l => (l(0).toInt, (l(1).toInt, l(2).toDouble)))

	// Emit every movie rated together by the same user.
	// Self-join to find every combination.
	val joinedRatings = ratings.join(ratings)   

	// At this point our RDD consists of userID => ((movieID, rating), (movieID, rating))

	// Filter out duplicate pairs
	val uniqueJoinedRatings = joinedRatings.filter(filterDuplicates)
```
* Retrieves the data from u.data and split it into a map with user ID being the key and value being a tuple of movie ID and rating
* Self join to combine all the movie ratings by the same user into a RDD

```
	def filterDuplicates(userRatings:UserRatingPair):Boolean = {
		val movieRating1 = userRatings._2._1
		val movieRating2 = userRatings._2._2
    
    	val movie1 = movieRating1._1
    	val movie2 = movieRating2._1
    
    	return movie1 < movie2
  	}
```
* filterDuplicates function compares against 2 userRatingPairs and takes only 1 unique userRatingPair with the lower rating (True condition for movie1 < movie2)

```
	// Now key by (movie1, movie2) pairs.
	val moviePairs = uniqueJoinedRatings.map(makePairs)

	// We now have (movie1, movie2) => (rating1, rating2)
	// Now collect all ratings for each movie pair and compute similarity
	val moviePairRatings = moviePairs.groupByKey()

	// We now have (movie1, movie2) = > (rating1, rating2), (rating1, rating2) ...
	// Can now compute similarities.
	val moviePairSimilarities = moviePairRatings.mapValues(computeCosineSimilarity).cache()
```
* Now we have a unique key of (movie1, movie2) with value of rating 1, 2 by the makePairs function
* groupByKey functiokn will group together all the different ratings associated for a given movie pair

```
	type RatingPair = (Double, Double)
	type RatingPairs = Iterable[RatingPair]

	def computeCosineSimilarity(ratingPairs:RatingPairs): (Double, Int) = {
		var numPairs:Int = 0
		var sum_xx:Double = 0.0
		var sum_yy:Double = 0.0
		var sum_xy:Double = 0.0

		for (pair <- ratingPairs) {
			val ratingX = pair._1
			val ratingY = pair._2

			sum_xx += ratingX * ratingX
			sum_yy += ratingY * ratingY
			sum_xy += ratingX * ratingY
			numPairs += 1
		}

		val numerator:Double = sum_xy
		val denominator = sqrt(sum_xx) * sqrt(sum_yy)

		var score:Double = 0.0
		if (denominator != 0) {
			score = numerator / denominator
		}

		return (score, numPairs)
	}
```
* computeCosineSimlarity function will compute a similarity score for each of the given movie pair
* This is just one way of measuring how similiar are the ratings for a movie pair similiar to one another

```
	//Save the results if desired
	//val sorted = moviePairSimilarities.sortByKey()
	//sorted.saveAsTextFile("movie-sims")

	// Extract similarities for the movie we care about that are "good".

	if (args.length > 0) {
		val scoreThreshold = 0.97
		val coOccurenceThreshold = 50.0

		val movieID:Int = args(0).toInt

		// Filter for movies with this sim that are "good" as defined by
		// our quality thresholds above     

		val filteredResults = moviePairSimilarities.filter( x =>
			{
				val pair = x._1
				val sim = x._2
				(pair._1 == movieID || pair._2 == movieID) && sim._1 > scoreThreshold && sim._2 > coOccurenceThreshold
			}
		)

		// Sort by quality score.
		val results = filteredResults.map( x => (x._2, x._1)).sortByKey(false).take(10)

		println("\nTop 10 similar movies for " + nameDict(movieID))
		for (result <- results) {
			val sim = result._1
			val pair = result._2
			// Display the similarity result that isn't the movie we're looking at
			var similarMovieID = pair._1
			if (similarMovieID == movieID) {
				similarMovieID = pair._2
			}
		println(nameDict(similarMovieID) + "\tscore: " + sim._1 + "\tstrength: " + sim._2)
		}
	}
```
* We use .cache function on moviePairSimilarities to use the RDD more than once
* We filter the movies with similarity score with a threshold we specified
* We map the results and flip the results around to take the top 10 similiar movies with the highest similarity score in descending order
* Then we use a condition to check if the similiarMovieID is the movieID we are looking for, and than display the similiarity movie score for the movie pair
* So we are finding out if for a given movie pair, do they have similar ratings given to them

### To Run The Code And Pass In An Argument
* We need to pass in the argument for the movieID we are finding, from the command line using the submit command
* Right click the package and click export
* Select Java, and JAR File
* We can use the settings as default
* We can export the JAR to our SparkScalaCourse Folder with the File name: MovieSims.jar
* Just press finish to export MovieSims.jar to the destinaton folder
* Open cmd and run as Administrator and cd to the folder containing MovieSims.jar
```
	cd C:\SparkScala\SparkScalaCourse
```

* Next pass in the submit argument for Spark to run the JAR with the movieID 50 which stands for Star Wars
```
	spark-submit --class com.sundogsoftware.spark.MovieSimilarities MovieSims.jar 50
```
* If you encounter the issue of spark-submit command not found in cmd, open control panel and navigate to environment variables. Select on system variables and add SPARK_HOME as a variable and into the path to execute spark-submit command.

* For my Windows setup, it can run the Spark Job but it encountered an exception.
* This exception is present when running simulated Hadoop environment on Windows with Spark job
```
	ERROR ShutdownHookManager: Exception while deleting Spark temp dir:
```

```
	Top 10 similar movies for Star Wars (1977)
	Empire Strikes Back, The (1980) score: 0.9895522078385338       strength: 345
	Return of the Jedi (1983)       score: 0.9857230861253026       strength: 480
	Raiders of the Lost Ark (1981)  score: 0.981760098872619        strength: 380
	20,000 Leagues Under the Sea (1954)     score: 0.9789385605497993       strength: 68
	12 Angry Men (1957)     score: 0.9776576120448436       strength: 109
	Close Shave, A (1995)   score: 0.9775948291054827       strength: 92
	African Queen, The (1951)       score: 0.9764692222674887       strength: 138
	Sting, The (1973)       score: 0.9751512937740359       strength: 204
	Wrong Trousers, The (1993)      score: 0.9748681355460885       strength: 103
	Wallace & Gromit: The Best of Aardman Animation (1996)  score: 0.9741816128302572       strength: 58
```
* You should see this as the output on your cmd for the Top 10 similiar movies rating for Stars Wars (1977)