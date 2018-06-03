# 20. [Exercise] Find the Total Amount Spent by Customer

## LEARNING ACTIVITY
Count up total amount ordered by customer

### Add Up Amount Spent By Customer
* So we need to create a simple script to see how much each customer spent on total based on a csv data

### Strategy
* Split each comma-delimited line into fields
* Map each line to key/ value pairs of customer ID and dollar amount
* Use reduceByKey to add up amount by customer ID
* collect() the results and print them

### Useful Snippets
* Review previous examples
* Split comma-delimited fields
```
	val fields = line.split(",")
```

* Treat field 0 as an integer, and field 2 as a floating point number:
```
	(fields(0).toInt, fields(2).toFloat)
```

### Good Luck

### Exercise
* Import the file customer-orders.csv as the data for your script from the resource, into the SparkScala folder
* To create new script, just right click and select create new Scala object
* Name the Scala object PurchaseByCustomer

