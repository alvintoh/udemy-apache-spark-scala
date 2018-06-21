# 39. Introduction to SparkSQL

## SPARK SQL
DataFrames and DataSets

### Working With Structured Data
* Extends RDD to a "DataFrame" objet
* DataFrames:
 * Contain Row objects
 * Can run SQL queries
 * Has a schema (leading to more efficient storage)
 * Read and write to JSON, Hive, parquet
 * Communicates with JDBC/ ODBC, Tableau

### DataSets
* A DataFrame is really just a DataSet of Row objects
(DataSet[Row])
* DataSets can explicitly wrap a given struct or type (DataSet[Person], DataSet[(String, Double)])
 * It knows what its column are from the get-go
* DataFrames schema is inferred at runtime; but a DataSet can be inferred at compile time
 * Faster detection of errors, and better optimization
* RDD's can be converted to DataSets with .toDS()

### DataSets Are The New Hotness
* The trend in Spark is to use RDD's less, and DataSets more
* DataSets are more efficient
 * They can be serialized very efficiently - even better than Kryo
 * Optimal execution plans can be determined at compile time
* DataSets allow for better interoperability
 * MLLib and Spark Streaming are moving toward using DataSets instead of RDD's for their primary API
* DataSets simplify development
 * You can perform most SQL operations on a dataset with one line

### In Spark 2.0.0, you create a SparkSession object instead of a SparkContext when using SparkSQL /DataSets
 * You can get a SparkContext from this session, and use it to issue SQL queries on your DataSets!
 * Stop the session when you're done.

### Other Stuff You Can Do With DataFrames
* myResultDataFrame.show()
* myResultDataFrame.select("someFieldName")
* myResultDataFrame.filter(myResultDataFrame("someFieldName") > 200)
* myResultDataFrame.groupBy(myResultDataFrame("someFieldName")).mean()
* myResultDataFrame.rdd().map(mapperFunction)

### Shell Access
* Spark SQL exposes a JDBC/ODBC server (if you built Spark with Hive support)
* Start it with sbin/start-thriftserver.sh
* Listens on port 10000 by default
* Connect using bin/beeline -u jdbc:hive2://localhost:10000
* Viola, you have a SQL shellto Spark SQL
* You can create new tables, or query existing ones that were cached using hiveCtx.cacheTable("tableName")

### User-Defined Functions (UDF'S)
```
	import org.apache.spark.sql.functions.udf

	val square = (x => x * x)
	squaredDF = df.withColumn("square", square('value'))
```

### Let's Play With Spark SQL And DataFrames
* Use our fake social network data from earlier
* Query it with SQL, and then use DataSets without SQL
* Finally we'll re-do our popular movies example with DataSets, and see how much simpler it is.