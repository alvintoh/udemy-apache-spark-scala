# 46. [Activity] Using DataFrames with MLLib

## MLLIB WITH DATASETS

### Using DataSets With MLLib Is Actually Preferred
* But's not always practical. Not everything is a SQ problem...
* Use spark.ml instead of spark.mllib for the preferred DataSet-based API (instead of RDDs)
 * Performs better
 * Will interoperate better with Spark Streaming, Spark SQL, etc.

* Available in Spark 2.0.0+
* API's are different

### Let's look An Example
* We'll do our linear regresson example using DataSets this time.

### Activity
* Import LinearRegressionDataFrame.scala from sourcefolder into SparkScalaCourse in Spark-Eclipse IDE
* Open LinearRegressionDataFrame.scala and look at the code

### Looking At the Code
```
	import org.apache.spark.ml.regression.LinearRegression
	import org.apache.spark.sql.types._
	import org.apache.spark.ml.linalg.Vectors
```
* Now we are importing all these seperate spark ml packages that are different from the MLLib package that we use before

```
	// Use new SparkSession interface in Spark 2.0
	val spark = SparkSession
		.builder
		.appName("LinearRegressionDF")
		.master("local[*]")
		.config("spark.sql.warehouse.dir", "file:///C:/temp") // Necessary to work around a Windows bug in Spark 2.0.0; omit if you're not on Windows.
		.getOrCreate()
```
* Now instead of using a spark context, we're going to use a Spark session.
* This is basically the API that we use in Spark 2.0 for doing DataSet stuff and Spark SQL stuff.

```
	// Load up our page speed / amount spent data in the format required by MLLib
	// (which is label, vector of features)

	// In machine learning lingo, "label" is just the value you're trying to predict, and
	// "feature" is the data you are given to make a prediction with. So in this example
	// the "labels" are the first column of our data, and "features" are the second column.
	// You can have more than one "feature" which is why a vector is required.
	val inputLines = spark.sparkContext.textFile("../regression.txt")
	val data = inputLines.map(_.split(",")).map(x => (x(0).toDouble, Vectors.dense(x(1).toDouble)))
```
* We are parsing and delimiting the lines by , commas
* The _ underscore is basically a wildcard so sort of a shortcut instead of saying X => x.split(), you can just say underscore and it means the same exact thing.
* So it means that each individual input coming into your map function that's represented by the underscore character
* Remember a DataFrame is a DataSet.

```
	// Convert this RDD to a DataFrame
	import spark.implicits._
	val colNames = Seq("label", "features")
	val df = data.toDF(colNames: _*)
```
* Here, we are actually giving names to those columns
* So in a DataFrame, you want to have names associate with those columns sowe can actually do SQL querys on them and refer to them by name.
* So let's do something a little bit more fancier just to make life a little bit more interesting if you're not familiar with machine learning you get a little free lesson here.
* So one way you can evaluate the performance of a machine learningmodel is technique called Train test
and the idea is that you have set of data where you have a set of known results.
* So in this case,we know the actual order amount and page speed for a set of data.
* What we're going to do is to split the data into half randomly
* A half is used for building our model and the other half is reserved for testing that model
* Since the model had no knowledge of this other data when you are creating it, it's a good way to actually test how effective this model is at predicting data that it hasn't seen before.

```
	// Note, there are lots of cases where you can avoid going from an RDD to a DataFrame.
	// Perhaps you're importing data from a real database. Or you are using structured streaming
	// to get your data.

	// Let's split our data into training data and testing data
	val trainTest = df.randomSplit(Array(0.5, 0.5))
	val trainingDF = trainTest(0)
	val testDF = trainTest(1)

	// Now create our linear regression model
	val lir = new LinearRegression()
		.setRegParam(0.3) // regularization 
		.setElasticNetParam(0.8) // elastic net mixing
		.setMaxIter(100) // max iterations
		.setTol(1E-6) // convergence tolerance
```
* Split the data 50 50 randomly into trainingDF and testDF
* Create a linear regression model

```
	// Train the model using our training data
	val model = lir.fit(trainingDF)

	// Now see if we can predict values in our test data.
	// Generate predictions using our linear regression model for all features in our 
	// test dataframe:
	val fullPredictions = model.transform(testDF).cache()

	// This basically adds a "prediction" column to our testDF dataframe.

	// Extract the predictions and the "known" correct labels.
	val predictionAndLabel = fullPredictions.select("prediction", "label").rdd.map(x => (x.getDouble(0), x.getDouble(1)))

	// Print out the predicted and actual values for each point
	for (prediction <- predictionAndLabel) {
		println(prediction)
	}
	
	// Stop the session
    spark.stop()
```
* Train the model using trainingDF and predict the values as fullPredictions
* Extract the predictions and the "known" correct labels
* Print it out and see if it is accurate
* Stop the spark session once you have finished running spark job
* The results are pretty similiar and this tells us our model is actually pretty good