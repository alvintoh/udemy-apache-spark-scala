# 28. Superhero Degrees of Seperation: Review the code, and run it!

### Activity
* Import DegreesOfSeperation.scala from resource folder into Eclipse-Scala IDE in SparkScala folder
* Open and take a look at DegreesOfSeperation.scala
* At the start, we seperate each line in marvel-graph.txt into a BFS node with heroID, default distance and color of gray

### Looking at the Code
```
	val startCharacterID = 5306 // SpiderMan
	val targetCharacterID = 14 // ADAM 3,031 (who?)
```
* This is the characters we want to find the seperation between.


```	
	// BFSData contains an array of hero ID connections, the distance, and color.
	var hitCounter:Option[Accumulator[Int]] = None

	//BFSData contains an array of hero ID connections, the distance, and color.
	type BFSData = (Array[Int]), Int, String)

	// A BFSNode has a heroID and the BFSData associated with it.
	type BFS Node = (Int, BFSData)
```
* Specifying the BFSData and BFSNode

```
	def convertToBFS(line: String): BFSNode = {

		// Split up the line into fields
		val fields = line.split("\\s+")

		// Extract this hero ID from the field field
		val heroID = fields(0).toInt

		// Extract subsequent hero ID's into the connections array
		var connections: ArrayBuffer[Int] = ArrayBuffer()
		for (connections <- i to (fields.length - 1)) {
			connections += fields(connection).toInt
		}

		// Default distance and color is 9999 and white
		var color: String = "WHITE"
		var distance: Int = 9999

		// Unless this is the character we'restarting from
		if (heroID == startCharacterID){
			color = "GRAY"
			distance = 0
		}

		return (heroID, (connections.toArray, distance, color))
	}
```
* Specifying the hero ID and the connections from the csv data
* Assigning the default color of WHITE and distance of 9999 to a new BFSNode
* Unless this is the character we are starting from, the color is changed to GRAY and the distance is changed to 0

```
	def main(args: Array[String]) {

		// Set the log level to only print errors
		Logger.getLogger("org").setLevel(Level.ERROR)

		// Create a SparkContext using every core of the local machine
		val sc = new SparkContext("Local[*]", "DegreesOfSeperation")

		// Our accumulator, used to signal when we find the target
		// character is in our BFS transversal.
		hitCounter = Some(sc.longAccumulator("Hit Counter"))
    
   	 	var iterationRdd = createStartingRdd(sc)
   	 	for (iteration <- 1 to 10) {
			println("Running BFS Iteration# " + iteration)

			// Create new vertices as needed to darken or reduce distances in the
			// reduce stage. If we encounter the node we're looking for as a GRAY
			// node, increment our accumulator to signal that we're done.
			val mapped = iterationRdd.flatMap(bfsMap)

			// Note that mapped.count() action here forces the RDD to be evaluated, and
			// that's the only reason our accumulator is actually updated.  
			println("Processing " + mapped.count() + " values.")

			if (hitCounter.isDefined) {
				val hitCount = hitCounter.get.value

				if (hitCount > 0) {
					println("Hit the target character! From " + hitCount + " different direction(s).")
					return
				}
			}

			// Reducer combines data for each character ID, preserving the darkest
			// color and shortest path.      
			iterationRdd = mapped.reduceByKey(bfsReduce)
		}
   	 }
```
* We are going to run the main method and iterate through 10 times through the graph to find out who is actually connected to one another
* It actually goes to the function bfsMap to expand BFS Node into this node and its children

```
	/** Expands a BFSNode into this node and its children */
  	def bfsMap(node:BFSNode): Array[BFSNode] = {
    
    	// Extract data from the BFSNode
    	val characterID:Int = node._1
    	val data:BFSData = node._2
    
    	val connections:Array[Int] = data._1
    	val distance:Int = data._2
    	var color:String = data._3
    
    	// This is called from flatMap, so we return an array
    	// of potentially many BFSNodes to add to our new RDD
    	var results:ArrayBuffer[BFSNode] = ArrayBuffer()
    
    	// Gray nodes are flagged for expansion, and create new
    	// gray nodes for each connection
    	if (color == "GRAY") {
      		for (connection <- connections) {
        		val newCharacterID = connection
        		val newDistance = distance + 1
        		val newColor = "GRAY"
        
        		// Have we stumbled across the character we're looking for?
        		// If so increment our accumulator so the driver script knows.
        		if (targetCharacterID == connection) {
          			if (hitCounter.isDefined) {
            			hitCounter.get.add(1)
          			}
        		}
        
        		// Create our new Gray node for this connection and add it to the results
        		val newEntry:BFSNode = (newCharacterID, (Array(), newDistance, newColor))
        		results += newEntry
    		}
      
      		// Color this node as black, indicating it has been processed already.
      		color = "BLACK"
    	}
    
    	// Add the original node back in, so its connections can get merged with 
    	// the gray nodes in the reducer.
    	val thisEntry:BFSNode = (characterID, (connections, distance, color))
    	results += thisEntry
    
    	return results.toArray
  	}
```
* Returns a results of array that contains the many possible BFS nodes that contains the results for the targetCharacterID
* hitCounter is defined as a global option, so the operation can reference it while calculating if the current node colour is gray.
* If the current node colour is gray, it means it is the character we are starting from

```
	/** Combine nodes for the same heroID, preserving the shortest length and darkest color. */
  	def bfsReduce(data1:BFSData, data2:BFSData): BFSData = {
    
    	// Extract data that we are combining
    	val edges1:Array[Int] = data1._1
    	val edges2:Array[Int] = data2._1
    	val distance1:Int = data1._2
    	val distance2:Int = data2._2
    	val color1:String = data1._3
    	val color2:String = data2._3
    
    	// Default node values
    	var distance:Int = 9999
    	var color:String = "WHITE"
    	var edges:ArrayBuffer[Int] = ArrayBuffer()
    
    	// See if one is the original node with its connections.
    	// If so preserve them.
    	if (edges1.length > 0) {
      		edges ++= edges1
    	}
    	if (edges2.length > 0) {
      		edges ++= edges2
    	}
    
    	// Preserve minimum distance
    	if (distance1 < distance) {
      		distance = distance1
    	}
    	if (distance2 < distance) {
      		distance = distance2
    	}
    
    	// Preserve darkest color
    	if (color1 == "WHITE" && (color2 == "GRAY" || color2 == "BLACK")) {
      		color = color2
    	}
    	if (color1 == "GRAY" && color2 == "BLACK") {
      		color = color2
    	}
    	if (color2 == "WHITE" && (color1 == "GRAY" || color1 == "BLACK")) {
      		color = color1
    	}
    	if (color2 == "GRAY" && color1 == "BLACK") {
      		color = color1
    	}
    	return (edges.toArray, distance, color)
  	}
```
* Maps the nodes and returns the nodes with edges, minimum distance and darkest color
* Remember for the starting character id we are starting with, the distance is set to 0 in bfsMap function
* Now go ahead and run the code and you should see the output

### The Results
```
	Running BFS Iteration# 1
	Processing 8330 values.
	Running BFS Iteration# 2
	Processing 220615 values.
	Hit the target character! From 1 different direction(s).
```
* So it means that our targetted character ADAM 3,031 is 2 degrees of seperations from Spiderman our starting character