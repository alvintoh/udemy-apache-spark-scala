# 44. [Activity] Using MLLib to Produce Movie Recommendations

### Activity
* So the first thing, I have don is to modify the data set a little bit
* So I have gone into the u.data file for ml-100k folder, and added three entries for a fictitious user.

```
	0	50	5	881250949
	0	172	5	881250949
	0	133	1	881250949
```
* What these ratings mean basically is that I've created a new user id zero, which is going to represent me
* This particular user zero user loves Star Wars which happens to be Id 50 and loves The Empire Strikes Back which is 172, five star ratings on both, but hates the movie Gone With the Wind which is rating 1
* Import MovieRecommendationsALS.scala from sourcefolder into SparkScalaCourse in Spark-Eclipse IDE
* Open MovieRecommendationsALS.scala and look at the code

### Looking At The Code
```
	import org.apache.spark.mllib.recommendation._
```
* The MLLib package was imported to our Scala file

```
	// Build the recommendation model using Alternating Least Squares
	println("\nTraining recommendation model...")

	val rank = 8
	val numIterations = 20

	val model = ALS.train(ratings, rank, numIterations)
```
* The recommendation model was built by using ALS, and setting the rank and numIterations by our specified number

```
	val userID = args(0).toInt

	println("\nRatings for user ID " + userID + ":")

	val userRatings = ratings.filter(x => x.user == userID)

	val myRatings = userRatings.collect()

	for (rating <- myRatings) {
		println(nameDict(rating.product.toInt) + ": " + rating.rating.toString)
	}
```
* We will take the userId that we want based on the command line argument
* The ratings for all the movies that the userID have rated will be printed out

```
	println("\nTop 10 recommendations:")
    
    val recommendations = model.recommendProducts(userID, 10)
    for (recommendation <- recommendations) {
    	println( nameDict(recommendation.product.toInt) + " score " + recommendation.rating )
    }
```
* Next, the model will recommend the top ten movies for the userID based on ALS
* The only difference here is put an argument of 0 when you run the scala code
* Now, run it and see the output
* However, there's an issue. Each time you run the model, it will display different results

### But The Results Aren't Really That Great.
* Very sensitive to the paremeters chosen. Takes more work to find optimal parameters for a data set than to run the recommendations
 * Can use "train/test" to evaluate various permutations of parameters
 * But what is a "good recommendation" anyway ?

* I'm not convinced it's even working properly.
 * Puttin your faith in a black box is dodgy.
 * We'd get better results using our movie similarity results instead, to find similar moves to moves each user liked.
 * Complicated isn't always better.

* Never blindly trust results when analyzing big data
 * Small problems in algorithms become big ones
 * Very often, quality of your input data is the real issue.

### MLLIB Is Still Really Useful, Though.