# 24. [Activity] Use Broadcast Variables to Display Movie Names

## USING BROADCASE VARIABLES IN SPARK

### Let's Make The Results Readable.
* Display movie names, not ID's (from the u.item file)
* We could just keep a table loaded in the driver program
 * Or we could even let Spark automatically automatically forward it to each executor when needed
 * But what if the table were massive? We'd only want to transfer once to each executor, and keep it there.

### Introducing Broadcast variables
* Broadcast objects to the executors, such that they are always there whenever needed
* Just sc.broadcast() to ship off whatever you want
* Then use .value() to get the object back

### Off To The Code Again!
* Import PopularMoviesNicer.scala from resource folder into Eclipse-Scala IDE in SparkScala folder
* Open and take a look at PopularMoviesNicer.scala