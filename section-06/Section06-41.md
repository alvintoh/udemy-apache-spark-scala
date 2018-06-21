# 41. [Activity] Using DataFrames and DataSets

### Activity
* You don't have to use SQL with SparkSQL necessarily
* You can also call functions directly without actually relying on the sql syntax
* That can be little bit more efficient
* Import DataFrames.scala from sourcefolder into SparkScalaCourse in Spark-Eclipse IDE
* Open DataFrames.scala and look at the code

### Looking At The Code
* You are still importing the SQL package from Saprk and alot of the codes looks the same as SparkSQL.scala

```
	println("Let's select the name column:")
	people.select("name").show()
```
* Instead of calling spark.sql select name, I am going to operate on the dataset directly by calling .select("name").show()
* This will quickly show the top 20 results for that dataset

```
	println("Filter out anyone over 21:")
	people.filter(people("age") < 21).show()
```
* We can also call people on our dataset, by calling the filter functio to filter out people who are over the age of 21
* This will show the top 20 results for that dataset

```
	println("Group by age:")
	people.groupBy("age").count().show()
```
* This will group the people by their age and count the total number for that age
* This reduces the need for SQL like query

```
	println("Make everyone 10 years older:")
	people.select(people("name"), people("age") + 10).show()
```
* We are selecting the name of people, and selecting the age column adding 10 as we go and show these results as we go.
* So now let's go ahead and run these results
* You should now see the top 20 results for each of the respective functions you specified