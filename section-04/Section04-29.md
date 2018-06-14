# 29. Item-Based Collaborative Filtering in Spark, cache(), and persist()

## ITEM-BASED COLLABORATIVE FILTERJNG
Finding similiar movies using Spark and the MovieLens data set

Introducing caching RDD's

### Similiar Movies
* Its an algorithm used for finding movie recommendations or any kind of movie recommendations for a user
* Basically the idea is that we try to find relationships between movies based on customer behavior and user behavior
* So if we see 2 movies that the users tend to rate together similiarly, then we must say there might be some sort of connection betweeen 2 movies
* Using that technique, we might be able be able to built a featue that includes a list of recommended movies which might suit the user

### Item-Based Collaborative Filtering
* Find every pair of movies that were watched by the same person
* Measure he similiarity of their ratings across all users who watched both
* Sort by movie, then by similiarity strength
* (This is one way to do it!)
* Given pair of movies that have a similiar movie preferences for a user, therefore for another user 3 who watches one of the movie A associated with the pair of movies, we can use the technique to recommend the other movie which is most popularly associated with movie A
 * User 1 -> Movie A, B
 * User 2 -> Movie A, B
 * User 3 -> Movie A (Based on the algorithm, it would recommend Movie B)

### Making It A Spark Problem
* Map input ratings to (userID, (movieID, rating))
* Find every movie pair rated by the same user
 * This can be done with a "self-join" operation
 * At this point we have (userID, ((movieID, rating1), (movieID, rating2)))
* Filter out duplicate pairs
* Make the movie pairs the key
 * map to ((movieID1, movieID2), (rating1, rating2))
* groupByKey() to get every rating pair found for every movie pair
* Compute similarity between ratings for each movie in the pair
* Sort, save, and display results

### Caching RDD'S
* In this example, we'll query the final RDD of movie similiarities a couple of times
* Any time you will perform more than one action on an RDD, you must cache it!
 * Otherwise, Spark might evaluate the entire RDD all over again!
* Use .cache() or .persist() to do this.
 * What's the difference?
 * Persist() optionally lets you cache it to disk instead of just memory, just in case a node fails or something.

### Off To The Code