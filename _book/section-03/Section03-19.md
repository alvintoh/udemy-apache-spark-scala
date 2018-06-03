# 19. [Activity] Sorting the Word Count Results

## SORTING OUR WORD COUNT RESULTS
Tricks for getting the output you needed

### Sorting The Results: Step 1
* We could sort what countByValue() returns, but let's use RDD's to keep it scalable.
* First, do countByValue() the "hard way":
```
	val wordCounts = lowercaseWords.map(x => (x, 1)).reduceByKey((x, y) => x + y)
```

* Convert each word to a key/ value pair with a value of 1
* Then count them all up with reduceByKey()

### Sorting The Results: Step 2
* Flip the (word, count) pairs to (count, word)
* Then use sortByKey() to sort by count(now that count is our new key)
```
	val wordCountsSorted = wordCounts.map(x = > (x._2, x._1)).sortByKey()
```

### Off To The Code

### Activity
* Import WordCountBetterSorted.scala into SparkScala folder
* Open up the scala script to see the codes we have described
* See if you can add in a filter function to filter out the list of most used words like "the" etc.
* The scala file WordCountBetterSortedFiltered.scala contains the code for filtering out the most commonly used grammar words, for generating a more insightful analysis
* The file WordCountBetterSortedFiltered.scala is included in this folder