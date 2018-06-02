# 3. [Activity] Create a Histogram of Real Movie Ratings with Spark!

### Download the required files for SparkScala
* Create a folder called SparkScala on your (C:/) so as to store your files of the course content
* Go to [grouplens.org](grouplens.org)
* Click on the datasets tab, and choose MovieLens 100K Dataset, ml-100kzip to download
* Extract and copy ml-100k folder with all the files inside to the SparkScala folder you created
* Download the [SparkScala.zip](http://media.sundog-soft.com/SparkScala/SparkScala.zip) from Sundog Website
* Extract the files from SparkScala.zip and remember where you store all of these data, as it contains all the source files for this course

### Importing SparkScala files into Scala Eclipse IDE
* Open and input C:/SparkScala for select a workspace for Scala IDE
* Create a new Scala project for the course
* Name it SparkScalaCourse and click finish
* Right click the project and click create, new Package
* Name the package
```
	com.sundogsoftware.spark
```
* Click the finish button
* Right click on the package and click import
* Navigate to General/File System/
* Choose where you have put the SparkScala Folder sources (For me, I put under this directory)
```
	C:\Users\User\Desktop\Alvin Programming Files\Data Science Courses\Apache 2.0 Spark with Scala\SparkScala)
```
* Check RatingsCounter.scala
* Double click on RatingsCounter.scala
* You can see all the codes under that file, and there will be alot of missing dependencies
* To resolve that, right click SparkScalaCourse project and select properties
* Go to Java build path and select add external JARs
* Navigate to (C:/spark/jars) and CTRL-a and select all the spark JARS to be added to Scala Spark IDE
* There might be errors displaying that spark JARS were under Scala version 2.11 which is different from my current Scala version of 2.13
* To fix that, right click on the project and go to properties, and go to Scala Compiler
* Check use Project Settings, and select the Fixed Version Scala built in version of your Scala IDE (For me its Scala version 2.11.11)
* After that, all Scala version related errors should disappear

### Creating and Running a Scala Spark Application
* Click on run, and go to Run Configurations
* Click on Scala Applications, and input this for main class
```
	com.sundogsoftware.spark.RatingsCounter
```
* Click run and it should work
* The console should now show the output for the count for the ratings 1 to 5