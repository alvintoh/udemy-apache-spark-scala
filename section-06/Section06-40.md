# 40. [Activity] Using SparkSQL

### Activity
* Import SparkSQL.scala from sourcefolder into SparkScalaCourse in Spark-Eclipse IDE
* Open SparkSQL.scala and look at the code


### Looking At The Code
```
	import org.apache.spark.sql._
```
* So we have imported the spark.sql package here

```
	// Use new SparkSession interface in Spark 2.0
	val spark = SparkSession
	 .builder
	 .appName("SparkSQL")
	 .master("local[*]")
	 .config("spark.sql.warehouse.dir", "file:///C:/temp") // Necessary to work around a Windows bug in Spark 2.0.0; omit if you're not on Windows.
	 .getOrCreate()

	val lines = spark.sparkContext.textFile("../fakefriends.csv")
	val people = lines.map(mapper)
```
* First thing we are doing is actually creating a SparkSession object instead of using SparkContext
* By Using SparkSession object, we actually do sql commands on and actually deal with datasets instead of RDD's
* By inputting .config("spark.sql.warehouse.dir", "file:///C:/"), we are actually creating a work around in Windows Environment in Spark 2.0.0
* So if you are not Windows leave that line off, but if you are on Windows be able to run without it.
* Go make sure you do have a c:/temp folder on your hard drive firt if you don't go ahead and create that right now please go ahead.
* getOrCreate() actually create our sparks session or get an existing one if you're recovering from a failure
* However if your dataset is in an actual json file. For example, we could load that up directly and actually create a dataset out of it rightaway.
* For example, we could say spark.read.json on a given json file name. And that will give us back an actual dataset as opposed to just an RDD.
* But since our data is unstructured we at first impart structure upon it before we can do datasets stuff
* We have mapped our fakefriends.csv to a Person class object
* So by calling this mapper that create a person objects based on the comma delimited values that we extract, we end up this structured data that we can then create a dateset out of that.

```
	// Infer the schema, and register the DataSet as a table.
	import spark.implicits._
	val schemaPeople = people.toDS
```
* So we need to do this step of importing spark.implicits_ in order to be able to convert a structured RDD into a dataset.
* But if you're in a situation where you're calling to DS() and it just doesn't compile and you know it should. You're probably forgetting this line of import spark.implicits._
* schemaPeople actually ends up being a data set of person objects.
* The beauty of this is that, we can actually treat it just like a sql database
* Spark has special optimization logic for things like this where it can actually do a better job at optimizing your task on a cluster.

```
	schemaPeople.printSchema()

	schemaPeople.createOrReplaceTempView("people")

	// SQL can be run over DataFrames that have been registered as a table
	val teenagers = spark.sql("SELECT * FROM people WHERE age >= 13 AND age <= 19")

	val results = teenagers.collect()

	results.foreach(println)

	spark.stop()
```
* So now we basically have a little sql database sitting in memory inside of Spark distributed potentially on a cluster
* And we would are running some sql queries on that schema that you created on people
* Remember to call stop on that session object.
* Its opening and stopping a database connection just like any other language
* Now run SparkSQL to see the output