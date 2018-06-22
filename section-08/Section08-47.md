# 47. Spark Streaming Overview

## SPARK STREAMING

### Spark Streaming
* Analyzes continual streams of data
 * Common example: processing log data from a website or server

* Data is aggregated and analyzed at some given interval
* Can take data fed to some port, Amazon Kinesis, HDFS, Kafka, Flume, and others
* "Checkpointing" stores state to disk periodically for fault tolerance
* A "Dstream" object breaks up the stream into distinct RDD's
* Simple example:
```
	val stream = new StreamingContext(conf, Seconds(1))
	val lines = stream.sockTextStream("localhost", 8888)
	val errors = lines.filter(_.contains("error"))
	errors.print()
```
* This listen to log data sent into port 8888, one second at a time, and prints out error lines.

* You may need to kick off the job explicitly
```
	stream.start()
	stream.awaitTermination()
```

* Remember your RDD's only contain one little chunk of incoming data.
* "Windowed operations" can combine results from multiple batches over some sliding time window
 * See window(), reduceByWindow(), reduceByKeyAndWindow()

* updateStateByKey()
 * Lets you maintain a state acros many batches as time goes by
 * For example, running counts of some event

### Let's Stream
* We'll run a Spark Streaming script that monitors live Tweets from Twitter, and keeps track of the most popular hashtags as Tweets are received!

### Need A Twitter Developer Account.
* Specifically we need to get an API key and access token
* This allows us to stream Twitter data in real time!
* Do this at [Twitter Website](http://apps.twitter.com/)

### Create A Twitter.txt File In Your WorkSpace
* On each line, specify a name and your own consumer key & access token information
* For example (substitute in your own keys & tokens!)
```
	consumerKey AXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXSQ
	consumerSecret 9EwXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	accessToken 384953089-3DUtu13XXXXXXXXXXXXXXXXXXXX
	accessTokenSecret 7XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
```

### Step One
* Get a Twitter stream and extract just the messages themselves

```
	val tweets = TwitterUtils.createStream(ssc, None)
	val statuses = tweets.map(status => status.getText())

	Vote for #BoatMcBoatFace!
	Vote for "I Like Big Boats and I Cannot Lie!"
	No! Vote for #WhatIceberg ?
	Are you crazy? #BoatMcBoatFace all the way.
	...
```

### Step Two
* Create a new Dstream that has every individual word as its own entry.
* We use flatMap() for this

```
	val tweetwords = statuses.flatMap(tweetText => tweetText.split(" "))

	Vote
	for
	#BoatyMcBoatFace
	Vote
	For
	I
	...
```

### Step Three
* Eliminate anything that's not a hashtag
* We use the filter() function for this

```
	val hashtags = tweetwords.filter(word => word.startsWith("#"))

	#BoatyMcBoatFace
	#WhatIceberg
	#BoatyMcBoatFace
	...
```

### Step Four
* Convert our RDD of hashtags to key/value pairs
* This is so we can count them up with a reduce function

```
	val hashtagKeyValues = hashtags.map(hashtag => (hashtag, 1))

	(#BoatyMcBoatFace, 1)
	(#WhatIceberg, 1)
	(#BoatyMcBoatFace, 1)
	...
```

### Step Five
* Count up the results over a sliding window
* A reduce operations adds up all of the values for a given key
 * Here, a key is a unique hashtag, and each value is 1
 * So by adding up all the "1"'s associated with each instance of a hastag, we get the count of that hashtag

* reduceByKeyAndWindow performs this reduce operations over a given window and slide interval

```
	val hashtagCounts = hashtagKeyValues.reduceByKeyAndWindow((x,y) => x + y, (x,y) => x - y, Seconds(300), Seconds(1))

	(#BoatyMcBoatFace, 2)
	(#WhatIceberg, 1)
```
* Besides combining, we also pass in a function to remove hashtag, and input the parameters of 5 minutes for window time and 1 seconds for batch time
* So basically every one second, it will update the result over the past five minutes

### Step Six
* Sort and output the results.
* The counts are in the second value of each tuple, so we sort by `._2 element`

```
	val sortedResults = hashtagCounts.transform(rdd => rdd.sortBy(x => x._2, false))
	sortedResults.print

	(#BoatyMcBoatFace, 2)
	(#WhatIceberg, 1)
```

### Let's See It In Action