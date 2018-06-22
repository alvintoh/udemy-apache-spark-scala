# 49. Structured Streaming

## STREAMING WITH DATASETS
Structured Streaming in Spark 2.0

### Structured Streaming
* Spark 2.0.0 introduced "structured streaming" as an experimental feature.
* Uses DataSets as its primary API (much of Spark is going this way)
* Imagine a DataSet that just keeps getting appended to forever, and you query it whenever you want.
```
	val inputDF = spark.readStream.json("s3://logs")
	inputDF.groupBy($"action", window($"time", "1 hour")).count().writeStream.format("jdbc").start("jdbc:mysql//...")
```