# 27. Superhero Degrees of Seperation: Accumuators, and Implementing BFS in Spark

## FRAMING BFS AS A SPARK PROBLEM

### Implementing BFS In Spark
* Represent each line as a node with connections, a color a distance.
* For example:
```
	5983 1165 3836 4361 1282
```

* becomes
```
	(5983, (1165, 3836, 4361, 1282), 9999, WHITE)
```
* Our initial condition is that a node is infinitely distant (9999) and white

### Map Function To Convert Marvel-Graph.txt To BFS Nodes
```
	def convertToBFS(line: String): BFSNode = {
		val fields = line.split("\\s+")
		val heroID = fields(0).toInt

		var connections: ArrayBuffer(Int) = ArrayBuffer()
		for (connections <- 1 to (fields.length - 1)){
			connections += fields(connection).toInt
		}

		var color: String = "WHITE"
		var distance: Int = 9999

		if (heroID == startCharacterID){
			color = "GRAY"
			distance = 0
		}

	}
```

### Iteratively Process The RDD
* Just like each step of our BFS example...
* Go through, looking for gray nodes to expand
* Color nodes we're done with black
* Update the distances as we go

### A BFS Iteration As A Map And Reduce Job
* The mapper:
 * Creates new nodes for each connection of gray nodes, with a distance incremented by one, color gray, and no connections
 * Colors the gray node we just processed black
 * Copies the node itself into the results.

* The reducer:
 * Combines together all nodes for the same hero ID
 * Preserves the shortest distance, and the darkest color found.
 * Preserves the list of connections from the original node.

### How Do We Know When We'Re Done?
* An accmulator allows many executors to increment a shared variable
* For example:
```
	var hitCOunter: LongAccumulator("Hit Counter")
```

* sets up a shared accumulator named "Hit Counter" with an initial value of 0.
* For each iteration, if the character we're interested in is hit, we increment the hitCounter accumulator
* After each iteration, we check if hitCounter is greater than one- if so, we're done.

### Off To The Code