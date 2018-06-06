# 26. Superhero Degrees of Seperation: Introducing Breadth-First Search

## SUPERHERO DEGREES OF SERPERATION
An iteratve Breadth-First-Search implementation in Spark, and introducing accumulators

### Degrees Of Seperation: Breadth-First Search
* So Kevin Bacon is rumoured to be the center of Hollywood and claimed to worked with almost every actor and anyone in Hollywood could be found within 6 degrees of seperation from Kevin Bacon
* Having worked in IMDB.com, that is not true so we are trying to find out how well connected are the superheroes from one another

### BFS In Action
* We need to find out the line connecting the superheroes in relation and the distance associated
* Each superheroe is represented as a node
* We can associate different colour as the state, and the colors can change depends on how we explore the relationship from the initial state to other states
* So from one starting node, we can explore the nearest nodes, and store that as 1 to represent the degree of seperation
* We would want to maintain the shortest distance path, and further explore the breadth down the explored path
* The node would change from white (unexplore) to black (explored)

### Implementing BFS In Spark
* Represent each line as a node with connections, a color, and a distance
* For example:
```
	5983 1165 3836 4361 1282
```

* becomes
```
	(5911, (1165, 3836, 4361, 1282), 9999, WHITE)
```

* Our initial condition is that a node is infinitely distance (9999) and white