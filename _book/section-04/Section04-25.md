# 25. [Activity] Find the Most Popular Superhero in a Social Graph

## MOST POPULAR SUPERHERO

### Superhero Social Networks
* So if a superhero appears with another superhero, these 2 superheros might be friends, so we can represent that relationship on a graph

### Input Data Format
* Marvel-graph.txt contains the superhero id and their association to different superheros on a line
* Marvel-graph.txt:
```
	4397 2237 1767 472 4997 5931 6235 1478 1369 806 3994 6232
	3519 4704 2460 763 1602 5306 5358 6121 6160 2459 3173 4963 6166
	3518 5409
```
* A hero may span multiple lines

* Marvel-names.txt contains the superhero id, their superhero name and real life name delimited by a space
* Marvel-names.txt:
```
	5300 "SPENCERTRACY"
	5301 "SPERZEL, ANTON"
	5302 "SPETSBURO, GEN.YURI"
	5303 "SPHINX"
	5304 "SPHINX II"
	5305 "SPHINX III"
	5306 "SPIDER-MAN/PETER PAR"
	5307 "SPIDER-MAN III/MARTH"
	5308 "SPIDER-MAN CLONE/BEN"
	5309 "SPIDER-WOMAN/JESSICA"
```

### Most Popular Superhero: Strategy
* Map input data to (heroID, number of co-occurences) per line
* Add up co-occurence by heroID using reduceByKey()
* Flip (map) RDD to (number, heroID) so we can...
* Use max() on the RDD to find the hero with the most co-occurences
* Look up the name of the winner and display the result

### Off To The Code...
* Import MostPopularSuperhero.scala from resource folder into Eclipse-Scala IDE in SparkScala folder
* Import Marvel-graph.txt and Marvel-names.txt from the resource folder into SparkScala folder to be used as the source file for MostPopularSuperhero.scala
* The delimiter used for parseNames function is '\"'' is a quotation mark
* countCoOccurences function is used to count the total number of other superhero occurences mapped to a key of the superhero id
* Run the file and you should see the superhero with the most friends

### And The Winner Is...
* CAPTAIN AMERICA is the most popular superhero with 1933 co-appearances.

### Exercise
* Now you can fiddle around with this code and find out what is the top ten most popular superhero and the top ten least popular superhero
* I have included my code topTenSuperHeroes.scala inside this folder to find out the top ten superheroes with the most and least friends