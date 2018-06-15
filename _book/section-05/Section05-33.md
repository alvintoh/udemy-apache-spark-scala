# 33. [Activity] Packaging driver scripts with SBT

## PACKAGING WITH SBT

### What is SBT
* Like Maven for Scala
* Manages your library dependency tree for you
* Can package up all of your dependencies into a self-contained JAR
* If you have many dependencies (or depend on a library that in turn has lots of dependencies), it makes life a lot easier than passing a ton of -jars options
* Get it from scala-sbt.org

### Using SBT
* Set up a directory structure like this:

| Dir Structure |
|:-------------:|:-------------:|
| src 			| project		|
| main			|				|
| scala			|				|

* Your Scala source files go in the source folder
* In your project folder, create an assembly.sbt file that contains one line:
```
	addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.3")
```
* Check the latest sbt documentation as this will change over time. This works with sbt 0.13.11

### Creating An SBT Build File
* At the root (alongside the src and project directories) create a build.sbt file
* Example:
```
	name := "PopularMovies"

	version := "1.0"

	organization := "com.sundogsoftware"

	scalaVersion := "2.10.6"

	libraryDependecies ++= Seq(
	"org.apache.spark" %% "spark-core" % "1.6.1" % "provided"
	)
```

### Adding Dependencies
* Say for example you need to depend on Kafka, which isn't built into Spark. You could add
```
	"org.apache.spark" %% "spark-streaming-kafka" % "1.6.1",
```
* to your library dependencies, and sbt will automatically fetch it and everything it needs and bundle it into your JAR file.

* Make sure you use the correct Spark version number, and note that I did NOT use "provided" on the line.

### Bundling It Up
* Just run:
```
	sbt assembly
```
* ...from the root folder, and it does its magic

* You 'll find the JAR in target/scala-2.10 (or whatever Scala version you're building against.)

### Here's The Cool Thing
* This JAR is self-contained! Just use spark-submit `<jar file>` and it'll run, even without specifying a class!
* Let's try it out.

### Activity
* Go to [SBT](https://www.scala-sbt.org) and navigate to the Download Tab to download the Windows version and install it
* You can also go to [MovieLens](https://grouplens.org/datasets/movielens/) and click on the 1M Dataset and click README.TXT to understand that dataset
* You can import MovieSimilarities1M.scala from the source folder into your Spark-Eclipse IDE into your project folder SparkScalaCourse
* Open and see some of the differences of delimiters and way in parsing the data

```
	val data = sc.textFile("s3n://sundog-spark/ml-1m/ratings.dat")
```
* The ratings.dat file is instead loaded from a cloud Amazon s3 storage
* Amazon s3 is distributed cloud storage service provider, and s3 is available to every node on my Amazon Elastic MapReduce cluster
* That data is in a place that I can handle the size of it and can store redundantly
* So when we set up a Amazon Elastic MapReduce Cluster (Amazon EMR), it will come preconfigured to take the best advantage of the cluster it has
* When you navigate to the source folder SparkScala, you can navigate to the folder sbt, and there will be a build.sbt inside it
* Change the version inside build.sbt according to your setup

```
	name := "MovieSimilarities1M"

	version := "1.0"

	organization := "com.sundogsoftware"

	scalaVersion := "2.11.8"

	libraryDependencies ++= Seq(
	"org.apache.spark" %% "spark-core" % "2.3.0" % "provided"
	)
```

* If you navigate sbt/project folder, you will see a assembly.sbt inside it

```
	addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.6")
```
* This SBT version may change depending on your SBT version
* This line will add access to the SBT assembly command that I can use to actually make my final jar file

* The folder also contains a src/main/scala folder which contains MovieSimilarities1M.scala file inside it.
* So to run SBT, open cmd and run as Administrator  in SparkScala source folder sbt folder
```
	cd C:\Users\User\Desktop\Alvin Programming Files\Data Science Courses\Apache 2.0 Spark with Scala\SparkScala\sbt
```

```
	dir
```
* dir is to see files under sbt directory

```
	sbt assembly
```
* This runs the SBT assembly command to build your JAR

```
	sbt about
```
* If you are unsure of your sbt version, please type sbt about in cmd main window to check your sbt version and input the correct version into assembly.sbt
* For my setup, my sbt version is 1.1.6
* So go to this page [SBT Version reference for sbt-assembly](https://github.com/sbt/sbt-assembly) to change your assembly.sbt
* For sbt version 1.1.6, I would need to change the SbtPlugin to 0.14.6
```
	addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.6")
```

* After the operation runs, there should now be a target folder which contains a scala-2.10 folder (which I specified is my dependency) which contains MovieSimilarities1M-assembly-1.0.jar
* So that is the actual JAR file that I acutally need to distribute to my cluster.
* And that is my Spark drivers script that I can run from a real cluster.