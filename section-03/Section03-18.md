# 18. [Activity] Improving the Word Count Script with Regular Expressions

## IMPROVING WORD COUNT
Normalizing data, sorting the results

### Text Normalization
* Problem: word variants with different capitalization, punctuation, etc.
* There are fancy natural language processing toolkits like NLTK
* But we'll keep it simple, and use a regular expression.

### Activity
* Looking at WordCount.scala, we need to account for , in the word which is contentuated with the word
* To improve upon WordCount.scala to account for , , we can look at WordCountBetter.scala from the resource folder
* Import WordCountBetter.scala and open up in Eclipse-Scala IDE

```
	// Split using a regular expression that extracts words
    val words = input.flatMap(x => x.split("\\W+"))
```
* The following code split the lines using REGEX for one word or more of them, and eliminate the additional , found

```
	// Normalize everything to lowercase
	val lowercaseWords = words.map(x => x.toLowerCase())
```
* This is done to let all the words be in lowercase to avoid duplicates between capitalized words
* Run it to see the difference in output
* Now the output looks much cleaner than WordCount.scala
* But now if the results were sorted, it would look much better
* We would explore this in the next lecture