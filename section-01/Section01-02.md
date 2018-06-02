# 2. Introduction, and Getting Set Up

## GETTING SET UP (Install Java, Spark, Scala, Eclipse)

### Install A Java Development Kit(JDK)
* From [oracle.com](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* Accept the default configurations

### Install Spark (pre-built)
* From [spark.apache.org](https://spark.apache.org/downloads.html)
* Choose a Spark release later than or equal to 2.0.0
* Choose a package type: Pre-built for Hadoop 2.7 and later
* Download the Spark tgz file (Unix compression format on Windows)
* So you might have to download a 3rd party tgz programme on Windows to uncompress tgz file
* [3rd party tgz programme](www.rarlab.com/download.htm)
* Open the tgz spark tgz file and extract to this folder, copy all the files inside to a new folder spark in (C:) drive
* Change the configuration file for spark, go to spark/conf and change log4j.properties.template to log4j.properties
* Open log4j.properties with whatever word editor you have (I uses Sublime 3)
* Change the following settings
```
 # Set everything to be logged to the console
 log4j.rootCategory=ERROR, console
```
* Install [winutils.exe](http://media.sundog-soft.com/SparkScala/winutils.exe) and HADOOP_HOME
* Create a new folder call winutils, and create a new folder called bin in (C:) drive
* Copy winutils.exe into C:/winutils/bin

### Set Up SPARK_HOME JAVA_HOME And PATH environment Variables
* Setting up the Windows environment, right click on the windows icon on the left hand corner and go into Control Panel
* Click on Systems and Security, then onto System and then Advanced system settings. Click on Environment Variables
* Click on New under User Variables for User
* Input in the following details for New User Variable
```
	Variable name: SPARK_HOME
	Variable value: C:\spark

	Variable name: JAVA_HOME
	Variable value: C:\Program Files\Java\jdk1.8.0_172

	Variable name: HADOOP_HOME
	Variable value: C:\winutils
```
* Now click Edit on the Path under User Variables and for User and click on New for the following environment variables
```
	%SPARK_HOME%\bin

	%JAVA_HOME%\bin
```
* Press okay for all of the settings

### Install Scala IDE (bundled with Eclipse)
* From [scala-ide.org](http://scala-ide.org/)
* Extract and copy the eclipse folder to a new folder eclipse in (C:/)
* Create a Desktop shortcut to eclipse.exe in C:/eclipse/eclipse.exe
* Open up a Windows Command Prompt in Adminstrator
```
	# To see your spark directory 
	cd C:\spark
	dir

	# spark-shell.cmd is inside the bin folder in spark directory
	cd bin

	# To write a new simple Scala Spark Application
	spark-shell

	# To see if Spark works with RDD
	val rdd=sc.textFile("../README.md")

	# Counting the number of lines in that file
	rdd.count()
```
* To exit just hit Ctrl-D

### Detailed, Written Steps At [SunDog Website](http://sundog-education.com/spark-scala)

