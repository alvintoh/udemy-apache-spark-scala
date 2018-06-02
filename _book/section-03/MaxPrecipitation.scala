package com.sundogsoftware.spark

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._
import scala.math.max


/** Find the max precipitation by dates */
object MaxPrecipitation {
  
  def parseLine(line:String)= {
    val fields = line.split(",")
    val dates = fields(1)
    val entryType = fields(2)
    val precipitation = fields(3).toFloat
    (dates, entryType, precipitation)
  }
    /** Our main function where the action happens */
  def main(args: Array[String]) {
   
    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)
    
    // Create a SparkContext using every core of the local machine
    val sc = new SparkContext("local[*]", "MaxPrecipitation")
    
    // Read each line of input data
    val lines = sc.textFile("../1800.csv")
    
    // Convert to (dates, entryType, precipitation) tuples
    val parsedLines = lines.map(parseLine)
    
    // Filter out all but PRCP entries
    val maxPrcps = parsedLines.filter(x => x._2 == "PRCP")
    
    // Convert to (dates, precipitation)
    val datesPrecipitation = maxPrcps.map(x => (x._1, x._3))
    
    // Max by date retaining the maximum temperature found in each unique key
    val maxPrcpByDate = datesPrecipitation.reduceByKey( (x,y) => max(x, y))
    
    // Sort by maximum precipitation and collect, format, and print the results
    val results = maxPrcpByDate.sortBy(_._2, false).collect()
    
    val maxPrcpDate = results.head
    val date = maxPrcpDate._1
    val prcp = maxPrcpDate._2
    println(s"The date is $date and the maximum precipitation: $prcp") 
      
  }
}