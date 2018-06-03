package com.sundogsoftware.spark

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._

/** Count up how many of each word occurs in a book, using regular expressions and sorting the final results */
object WordCountBetterSortedFiltered {
 
  /** Our main function where the action happens */
  def main(args: Array[String]) {
   
    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)
    
     // Create a SparkContext using the local machine
    val sc = new SparkContext("local", "WordCountBetterSorted")   
    
    // Load each line of my book into an RDD
    val input = sc.textFile("../book.txt")
    
    // Split using a regular expression that extracts words
    val words = input.flatMap(x => x.split("\\W+"))
    
    // Normalize everything to lowercase
    val lowercaseWords = words.map(x => x.toLowerCase())
    
    // Count of the occurrences of each word
    val wordCounts = lowercaseWords.map(x => (x, 1)).reduceByKey( (x,y) => x + y )
    
    // Flip (word, count) tuples to (count, word) and then sort by key (the counts)
    val wordCountsSorted = wordCounts.map( x => (x._2, x._1) ).sortByKey()
    
    // Filter most commonly used words like "you" etc.
    val filteredWords = wordCountsSorted.filter(x => 
      x._2 != "you" && 
      x._2 != "to" && 
      x._2 != "your" &&
      x._2 != "the" &&
      x._2 != "a" &&
      x._2 != "of" && 
      x._2 != "and" && 
      x._2 != "that" &&
      x._2 != "it" &&
      x._2 != "in" &&
      x._2 != "is" &&
      x._2 != "for" && 
      x._2 != "on" && 
      x._2 != "are" &&
      x._2 != "if" &&
      x._2 != "s" && 
      x._2 != "i")
    
    // Print the results, flipping the (count, word) results to word: count as we go.
    for (result <- filteredWords) {
      val count = result._1
      val word = result._2
      println(s"$word: $count")
    }
    
  }
  
}

