# 44. [Activity] Linear Regression with MLLib

## LINEAR REGRESSION WITH MLLIB

### What Is Linear Regression?
* Fit a line to a data seet of observations
* Use this line to predict unobserved values
* I don;t know why call it "regression." It's really misleading. You can use it to predict points in the future, the past, whatever. In fact time usally has nothign to do with it.

### How Does Linear Regression Work?
* Usually using "least squares"
* Minimizes the squared-error between each point and the line
* Remember the slope-intercept equation of a line? y=mx+b
* The slope is the correlation between the two variables times the standard deviation in Y, all divided by the standard deviation in X.
 * Neat how standard deviation how some real mathematical meaning, eh?

* The intercept is the mean of Y minus the slope times the mean of X

### There's More Than One Way To Do It.
* Spark Streaming uses a more complex technique called Stochastic Gradient Descent (SGD)
* Friendlier to multi-dimensional data as it looks for contour in higher dimensions

### Linear Regression With MLLLIB
* Set up your model like this:

```
	val algorithm = new LinearRegressionWithSGD()

	algorithm.optimizer
		.setNumIterations(100)
		.setStepSize(1.0)
		.setUpdater(new quareedL2Updater())
		.setRegParam(0.01)
```
* You train the model and make predictions using LabeledPoint objects, In this case the "label" is the value you're trying to predict - usually your Y axis - and the "feature" is your X axis or other axes.
 * So you train it with a bunch of known x, y points
 * And predict new Y values for given X's using thhe line the model created.

### GOTCHAS WIT SGD LINEAR REGRESSION
* SGD doesn't handle "feature scaling" well. It assumes your data is similiar to a normal distribution.
* So you need to scale your data down and back up again when you're done
* It also assumes your y-intercept is 0, unless you call algorithm.setIntercept(true)

### Let's Try It Out
* We'll fabricate some data for average page speed and revenue generated from session data on an online store
* Can we predict revenue based on page speed using a linear model?

### Activity
* Import LinearRegression.scala from sourcefolder into SparkScalaCourse in Spark-Eclipse IDE
* Open LinearRegression.scala and look at the code

### Looking At The Code
```
	import org.apache.spark.mllib.linalg.Vectors
	import org.apache.spark.mllib.regression.LabeledPoint
	import org.apache.spark.mllib.regression.LinearRegressionWithSGD
	import org.apache.spark.mllib.optimization.SquaredL2Updater
```
* Don't forget that you know we are importing a bunch of specific MLLIB classes that the script uses including vectors, labeledpoint, linear regression with SGD and squared L2 updater that we all use inside.
* So we set up our context as usual load up our training data
* So usually with supervised learning techniques like this, we are training an algoritm given a bunch of known and then applying that resulting model to unknown data.
* So to evaluate the model you are given a set of training data where you already know the answer, and you know the labels for each data point.
* So in this case, we are getting paid speeds as an x axis and we are trying to predict the amount spent as the y axis so amount spend is our label
* In this case that's what we are trying to predict and to evaluate the model we are going to throw some more data at it, where we actually know the result and we can compare the known result to what the model predicted.
* Ok so that's what's called a training test
* Normally you train and test the algorithm usiing different slices of the data.
* So you wouldn't normally want to actually evaluate a model using the same data that you trained it on
* But in this example we're gonna kind of pretend we never heard about that and actually use the same data for training and testing just for simplicity.
* In the real world, you wouldn't actually know the results so you wouldn't be able to actually evaluate the correctness of the results.

```
	// Convert input data to LabeledPoints for MLLib
	val trainingData = trainingLines.map(LabeledPoint.parse).cache()
	val testData = testingLines.map(LabeledPoint.parse)
```
* So to get this into MLLIB, we need to do is call a LabeledPoint.parse on the training data and test data and that gives me back a RDD of labeled points based on that input file.
* I'm caching the training data that bacause the algorithm may use it multiple times

```
	// Now we will create our linear regression model

	val algorithm = new LinearRegressionWithSGD()
	algorithm.optimizer
		.setNumIterations(100)
		.setStepSize(1.0)
		.setUpdater(new SquaredL2Updater())
		.setRegParam(0.01)

	val model = algorithm.run(trainingData)

	// Predict values for our test feature data using our linear regression model
	val predictions = model.predict(testData.map(_.features))

	// Zip in the "real" values so we can compare them
	val predictionAndLabel = predictions.zip(testData.map(_.label))

	// Print out the predicted and actual values for each point
	for (prediction <- predictionAndLabel) {
		println(prediction)
	}
```
* This is creating a linear regression algorithm with our set parameters
* We will then create a linear regression model with out training data
* The test data will be used to predict values using our linear regression model
* The real values will then be added, to compare the predicted and actual values for each point
* Now, run the scala code and observe the output
* As you can see the predicted results on the first value is quite close to the actual results on the right

### Exercise
* Instead of just printing out this results, go and actually compute the total squared error amongst the entire data
* So keep track of the difference between the predicted value and the actual value, and take the square of the value and add them all together and then divided by how many data points you had
* That will give you whats called the R-squared metric of just how good this model is and predicting new values.
* That will give you a number with which you can evaluate how good this model is.
* Once you have that working well, you can do is try different different values here for the optimizer and see what effect do they have for the results here.
* I have included LinearRegressionRSquared.scala which contains my implementation to derive R-Squared for the LinearRegression model