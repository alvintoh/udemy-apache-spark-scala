# 12. Spark Internals

## SPARK INTERNALS
What actually happenend?

### An Execution Plan Is Created From Your RDD'S
* This start from textFile() -> map() -> countByValue()

### The Job Is Broken Into Stages Based On When Data Needs To Be Organized
* Stage 1 -> textFile(), map()
* Stage 2 -> countByValue()

### Each Stage Is Broken Into Tasks (Which May Be Distributed Across A Cluster)

### Finally The Tasks Are Scheduled Across Your Cluster And Executed