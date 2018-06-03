# 17. [Activity] Counting Word Occurencences using Flatmap()

## MAP VS. FLATMAP
And the Word Count example

### Map() Transform Each Element Of An RDD Into One New Element
```
	val lines = sc.textFile("redfox.txt")
	->
	The quick red
	fox jumped
	over the lazy
	brown dogs

	val rageCaps = lines.map(x => x.toUpperCase)
	THE QUICK R RED
	FOX JUMPED
	OVER THE LAZY
	BROWN DOGS
```
Map transform each line into one new element

### FlatMap() Can Create Many New Elements From Each One
```
	val lines = sc.textFile("redfox.txt")
	->
	The quick red
	fox jumped
	over the lazy
	brown dogs

	val words = lines.flatMap(x => x.split(" "))
	->
	The
	quick
	red
	fox
	jumped
	over
	the
	lazy
	brown
	dogs
```
FlatMap can create many new elements from each line

### Code Sample: Count The Words In A Book

### Activity
* Copy the book.txt from the resource folder into the SparkScala folder
* Open up Eclipse-Scala IDE and import WordCount.scala into SparkScalaCourse project
* Open up the scala code for WordCount.scala
* The function countByValue() is used to count the occurences of each word inside book.txt
* When you run the configuration as the specified class and class name, you should see the output displaying the count for each word
* The output never account for different capitalizatios and different uni-codes which will be addressed by our scripts for the coming lectures