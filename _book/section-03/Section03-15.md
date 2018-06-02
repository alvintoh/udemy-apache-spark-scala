# 15. Filtering RDD's,and the Minimum Temperature by Location Example

## FILTERING RDD'S
And the weather data examples.

### Filter() Removes Data From Your RDD
* Just takes a function that returns a boolean
* For example, we want to filter out entries that don't have "TMIN" in the first item of a list of data:
```
	val minTemps = parsedLines.filter(x => x._2 == "TMIN")
```

### Minimum Temperature In A Year
* This is the Input data snippet:
```
	ITE00100554, 18000101, TMAX, -75,,, F,
	ITE00100554, 18000101, TMIN, -148,,, F,
	GM000010962, 18000101, PRCP, 0,,, E,
	EZE00100082, 18000101, TMAX, -86,,, E,
	EZE00100082, 18000101, TMIN, -135,,, E,
```

### Parse (Map) The Input Data
```
	def parseLine(line: String) = {
		val fields = line.split(",")
		val stationID = fields(0)
		val entryType = fields(2)
		val temperature = fields(3).toFloat * 0.1f * (9.0f / 5.0f) + 32.0f
		// This the conversion formula for temperature
		(stationID, entryType, temperature)
	}

	val lines = sc.textFile("../1800.csv")
	val parsedLines = lines.map(parseLine)
```
* The Output is (stationID, entryType, temperature)

### Filter Out All But TMIN Entries
```
	val minTemps = parsedLines.filter(x => x._2 == "TMIN")
```

### Create (stationID, Temperature) Key /Value Pairs
```
	val stationTemps = minTemps.map(x => (x._1, x._3.toFloat))
```

### Find Minimum Temperature By StationID
```
	val minTempsByStation = stationTemps.reduceByKey((x, y) => min(x, y))
```

### Collect And Print The Results
```
	val results = minTempsByStation.collect()

	for (result <- results.sorted){
		val station = result._1
		val temp = result._2
		val formattedTemp = f"$temp%.2f F"
		println(s"$station minimum temperature: $formattedTemp")
	}
```