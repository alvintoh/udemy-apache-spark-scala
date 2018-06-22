# 50. GraphX, Pregel, and Breadth-First-Search with Pregel.

## Analyzing and transversing graphs
Using Spark GraphX

### GRAPHX
* Not that kind of graph...
* Graphs like oursocial network of superheroes - graphs in the computer science / network sense.
* However, it's only useful for specific things.
 * It can measure things like "connectedness", degree distribution, average path length, triangle counts - high level measures of a graph
 * It can count triangles in the graph, and apply the PageRank algorithm to it.
 * It can also join graphs together and transform graphs quickly
 * For things like our "degrees of seperation" example, you won't find built-in support. But it does support the Pregel API for transversing a graph...

* Introduces VertexRDD and EdgeRDD, and the Edge data type
* Otherwise, GraphX code looks like any other Spark code for the most part

### Creating Vertex RDD's
```
	// Function to extract hero ID -> hero name tuples (or None in case of failure)
	def parseNames(line: String) : Option[(VertexId, String)] = {
		var fields = line.split('\'")
		if (fields.length > 1){
			val heroID: Long = fields(0).trim().toLong
			if (heroID < 6487) { // ID's above 6486 aren't real characters
				return Some(fields(0).trim().toLong, fields(1))
			}
		}

		return None // flatmap will just discard None results, and extract data from Some results.
	}
```

### Creating Edge RDD's
```
	/** Transform an input line from marvel-graph.txt into a List of Edges */
	def makeEdges(line: String) : List[Edge[Int]] = {
		import scala.collection.mutable.ListBuffer
		var edges = new ListBuffer[Edge[Int]]()
		val fields = line.split(" ")
		val origin = fields(0)
		for (x <- 1 to (fields.length - 1)) {
			// Our attribute field is unused, but in other graphs could
			// be used to deep track of physical distances etc.
			edges += Edge(origin.toLong, fields(x).toLong, 0)
		}

		return edges.toList
	}
```

### Creating A Graph
```
	// Build up our vertices
	val names = sc.textFile("../marvel-names.txt")
	val verts = names.flatMap(parseNames)

	// Build up our edges
	val lines = sc.textFile("../marvel-graph.txt")
	val edges = lines.flatMap(makeEdges)    

	// Build up our graph, and cache it as we're going to do a bunch of stuff with it.
	val default = "Nobody"
	val graph = Graph(verts, edges, default).cache()
```

### Doing Stuff
* Top 10 most-connected heroes:
```
	graph.degrees.join(verts).sortBy(_._2._1, ascending=false).take(10).foreach(println)
```

### Breadth-First Search With Pregel
* Before, we actually implemented BFS search in Spark the hard and manual way.
* Now with Pregel, that gives us an general algorithm for actually doing iterations through a graph in a general manner and we can actually implement breadth first search using the Pregel algorithm, in a little bit of a more simple way than we did it before

### How Pregel Works
* Vertices send messages to other vertices (their neighbours)
* The graph is processed in iterations called supersteps
* Each superstep does the following:
 * Messages from the previous iteration are received by each vertex
 * Each vertex runs a program to transform itself
 * Each vertex sends messages to other vertices

### BFS: Initialization
```
	val initialGraph = graph.mapVertices(id, _) => if (id == root) 0.0 else Double.PositiveInfinity)
```

### Sending Messages
```
	triplet => { 
		if (triplet.srcAttr != Double.PositiveInfinity) { 
			Iterator((triplet.dstId, triplet.srcAttr+1)) 
		} else { 
		Iterator.empty 
		} 
	},
```

### Preserving The Minimum Distance At Each Step
* Pregel's vertex program will preserve the minimum distance between the one it receives and what it has:
 * (id, attr, msg) => math.min(attr, msg)

* Its reduce operation preserves the minimum distance if multiple messages are received for the same vertex:
 * (a,b) => math.min(a,b)

### Putting It All Together
```
	// Initialize each node with a distance of infinity, unless it's our starting point
	val initialGraph = graph.mapVertices((id, _) => if (id == root) 0.0 else Double.PositiveInfinity)

	// Now the Pregel magic
	val bfs = initialGraph.pregel(Double.PositiveInfinity, 10)( 
		// Our "vertex program" preserves the shortest distance
		// between an inbound message and its current value.
		// It receives the vertex ID we are operating on,
		// the attribute already stored with the vertex, and
		// the inbound message from this iteration.
        (id, attr, msg) => math.min(attr, msg), 
        
		// Our "send message" function propagates out to all neighbors
		// with the distance incremented by one.
		triplet => { 
			if (triplet.srcAttr != Double.PositiveInfinity) { 
				Iterator((triplet.dstId, triplet.srcAttr+1)) 
			} else { 
			Iterator.empty 
			} 
		}, 

		// The "reduce" operation preserves the minimum
		// of messages received by a vertex if multiple
		// messages are received by one vertex
		(a,b) => math.min(a,b) )
	}
```

### Let's Do It