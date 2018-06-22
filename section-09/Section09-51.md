# 51. [Activity] Superhero Degrees of Seperation using GraphX

## GraphX in Action
Applying GraphX to the Superhero Social Network

### Activity
* Import GraphX.scala from sourcefolder into SparkScalaCourse in Spark-Eclipse IDE
* Open GraphX.scala and look at the code

### Looking At The Code
```
	import org.apache.spark.graphx._
```
* Importing the GraphX package into the scala code

```
	// Function to extract hero ID -> hero name tuples (or None in case of failure)
	def parseNames(line: String) : Option[(VertexId, String)] = {
		var fields = line.split('\"')
		if (fields.length > 1) {
			val heroID:Long = fields(0).trim().toLong
			if (heroID < 6487) {  // ID's above 6486 aren't real characters
	    		return Some( fields(0).trim().toLong, fields(1))
	    	}
		}
	}	 
```
* Returns a valid data associated with the vertex else it returns none
* makeEdges transform an input line and create a list of edges connected from every superheroID to the associated superheroID

* We will then print the top 10 most connected superheroes (heroes with the most number count of degrees of connectiveness)
* Our vertexId is set to 5306 which is spiderman being the root of our BFS
* Pregel will be used to traverse through the initialGraph
* Instead of using colour, white etc in our previous example, we will now be using postiveInfinity

```
	// Print out the first 100 results:
	bfs.vertices.join(verts).take(100).foreach(println)

	// Recreate our "degrees of separation" result:
	println("\n\nDegrees from SpiderMan to ADAM 3,031")  // ADAM 3031 is hero ID 14
	bfs.vertices.filter(x => x._1 == 14).collect.foreach(println)
```
* After the Pregel traversal, we will get back the vertices that contain its values with the actual degrees of seperation from spiderman, when we are done
* We will then traverse to ADAM 3031 to recreate our earlier example of BFS search
* So now run the code and see the output