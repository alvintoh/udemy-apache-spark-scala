# 42. [Activity] Using DataSets instead of RDD's

### Activity
* So Datasets are kind of taking over Spark and you should be using Datasets instead of RDD it make sense
* This is because it is a faster implementation over RDD
* Import PopularMoviesDatasets.scala from sourcefolder into SparkScalaCourse in Spark-Eclipse IDE
* Open PopularMoviesDatasets.scala and look at the code

### Looking At The Code
* The first part of the script is actually unchanged from the PopularMovies.scala example
* We define the structure of the data as Movie class which contains the movieID: Int

```
	// Read in each rating line and extract the movie ID; construct an RDD of Movie objects.
	val lines = spark.sparkContext.textFile("../ml-100k/u.data").map(x => Movie(x.split("\t")(1).toInt))
```
* We are parsing into a RDD of Movie object before converted it to a Dataset of Movie objects
* Now we do not have to pass it through hoops of mapping key and values to get the result that we want

```
	// Some SQL-style magic to sort all movies by popularity in one line!
	val topMovieIDs = moviesDS.groupBy("movieID").count().orderBy(desc("count")).cache()
```
* We are sorting the movies with the most ratings by using this one line in descending order

```
	// Grab the top 10
	val top10 = topMovieIDs.take(10)
    
    // Load up the movie ID -> name map
    val names = loadMovieNames()
    
    // Print the results
    println
    for (result <- top10) {
    	// result is just a Row at this point; we need to cast it back.
    	// Each row has movieID, count as above.
    	println (names(result(0).asInstanceOf[Int]) + ": " + result(1))
    }
```
* We are printing each result by casting as an Instance of Int for movieIDs into names to get the movie name
* The movie name and the number of ratings will be printed next
* Now run this code and see the output