package com.sundogsoftware.spark

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._

/** Find the top 10 superheroes with the most co-appearances. */
object TopTenSuperheroes {
  
  // Function to extract the hero ID and number of connections from each line
  def countCoOccurences(line: String) = {
    var elements = line.split("\\s+")
    ( elements(0).toInt, elements.length - 1 )
  }
  
  // Function to extract hero ID -> hero name tuples (or None in case of failure)
  def parseNames(line: String) : Option[(Int, String)] = {
    var fields = line.split('\"')
    if (fields.length > 1) {
      return Some(fields(0).trim().toInt, fields(1))
    } else {
      return None // flatmap will just discard None results, and extract data from Some results.
    }
  }
 
  /** Our main function where the action happens */
  def main(args: Array[String]) {
   
    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)
    
     // Create a SparkContext using every core of the local machine
    val sc = new SparkContext("local[*]", "MostPopularSuperhero")   
    
    // Build up a hero ID -> name RDD
    val names = sc.textFile("../marvel-names.txt")
    val namesRdd = names.flatMap(parseNames)
    
    // Load up the superhero co-appearance data
    val lines = sc.textFile("../marvel-graph.txt")
    
    // Convert to (heroID, number of connections) RDD
    val pairings = lines.map(countCoOccurences)
    
    // Combine entries that span more than one line
    val totalFriendsByCharacter = pairings.reduceByKey( (x,y) => x + y )
    
    // Sort totalFriendByCharacter by top ten most friends
    // Remove false inside sortBy function if you want find the top ten least friends
    val topTenMostFriends = totalFriendsByCharacter.sortBy(_._2, false)
    
    // Collect, format, and print the results
    val results = topTenMostFriends.collect()
    
    // For loop and print the name of the top ten superheroes with the most friends
    for (i <- 0 to 10) {
      val superHeroName = namesRdd.lookup(results(i)._1)(0)
      println(s"$superHeroName is the superhero with ${results(i)._2} co-appearances.")   
    }
  }
  
}
