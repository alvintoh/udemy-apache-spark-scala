# 48. [Activity] Set up a Twitter Developer Account, and Stream Tweets

## SPARK STREAMING IN ACTION

### Activity
* Now to do this first, we need to set up a developer with Twitter so that we can actually connect our Spark Streaming Application to the Twitter API.
* Go to [Twitter Apps Website](https://apps.twitter.com) and create an AppStore Twitter account and you should see a screen like this.
* Go ahead and sign in with your Twitter account
* If you don't have a Twitter Account, you can Sign up now!
* You can press Create New App and input the following details

```
	Name: SparkScalaCourse AlvinToh
	Description: Messing around with Spark Streaming
	Website: http://www.sundog-soft.com (You can put whichever website you have, I just put sundog website as default)
	CallbackUrl: (Can be left empty)
```

* Check and agree on the Developer Agreement
* Click on Create your Twitter application and your Twitter Application will be created
* After you have access the application console, now we need to click on Keys and Access Tokens.
* These are the credentials that we need in order to connect.
* So let's click on that and we want to create an access token as well as our consumer key.
* Now, you have the Consumer Key (API Key), Consumer Secret(API Secret), Access Token and an Access Token Secret.
* So leave that information up where you can get it easily
* Next access the sparkScala source folder for the course materials, you should find a twitter.txt file
* Open twitter.txt file, and copy and paste the keys that you just got into this file.
* Make sure that is one space between consumer key and the token itself, and no extra spaces at the end of anything
* You want to keep on doing this for the other credentials.
* Of course yours will be different, and don't try using the twitter.txt default keys in there because the account is going to be deleted
* After you are done, make sure you have a return at the end of the line and make sure there's no extra spaces, no extra returns or anything like that
* As this might mess up this file, and once you are happy with it, save it.
* Now copy twitter.txt and paste it into your course folder, SparkScala folder.

### Setting Up And Running The File
* Next open up Spark-Eclipse IDE and import some libraries for Scala and Spark that will let it actually talk to Twitter
* Now if you're using something before Spark 2.0, that capability is just built into Spark itself.
* But they actually removed it in Spark 2.0.0
* So if you are using Spark 2.0.0 or newer, you have to do this next step first, so go over to SparkScala
* Right click on course project in the IDE, and click on properties.
* Click on Java Build Path and press Add External JARs.
* Browse to the SparkScala source folder and import all of these 3 JARS

```
	dstream-twitter.jar
	twitter4j-core.jar
	twittet4j-stream.jar
```
* Hit Apply and Close once you are done
* Import PopularHashtags.scala from sourcefolder into SparkScalaCourse in Spark-Eclipse IDE
* Open PopularHashtags.scala and look at the code

### Looking At The Code
```
	import org.apache.spark.streaming._
	import org.apache.spark.streaming.twitter._
	import org.apache.spark.streaming.StreamingContext._
```
* Basically, we're importing all the stuff we need including a bunch of Spark Streaming classes and these Spark Streaming Twitter package
* So that this allows us to connect to Twitter and use Twitter as a Spark Streaming Receiver

```
	/** Configures Twitter service credentials using twiter.txt in the main workspace directory */
	def setupTwitter() = {
		import scala.io.Source
    
    	for (line <- Source.fromFile("../twitter.txt").getLines) {
    		val fields = line.split(" ")
    		if (fields.length == 2) {
        		System.setProperty("twitter4j.oauth." + fields(0), fields(1))
        	}
        }
    }
```
* Here is where we actually load that twitter.txt file that you just created and we parse it out one line a time, split it based on that space character
* And we just set system properties based on those settings.
* So assuming you format that file correctly, that should set up all the system properties needed to actually connect to Twitter successfully.
* So that's the first thing we do, and our main function we set up those credentials for Twitter

```
	// Create a DStream from Twitter using our streaming context
    val tweets = TwitterUtils.createStream(ssc, None)
    
    // Now extract the text of each status update into DStreams using map()
    val statuses = tweets.map(status => status.getText())
    
    // Blow out each word into a new DStream
    val tweetwords = statuses.flatMap(tweetText => tweetText.split(" "))
```
* We'll call the TwitterUtils class to actually create a receiver that listens to a stream of tweets.
* And we now have a stream called tweets that we can work with.
* So we're not dealing with individual little RDD's here but we're dealing with the Dstream as a whole which is kinnd of cool and keeping on with that we're gonna apply a flat map to that to actually bust those tweets out in individual words broken up by spaces.

```
	// Now eliminate anything that's not a hashtag
	val hashtags = tweetwords.filter(word => word.startsWith("#"))
    
    // Map each hashtag to a key/value pair of (hashtag, 1) so we can count them up by adding up the values
    val hashtagKeyValues = hashtags.map(hashtag => (hashtag, 1))
    
    // Now count them up over a 5 minute window sliding every one second
    val hashtagCounts = hashtagKeyValues.reduceByKeyAndWindow( (x,y) => x + y, (x,y) => x - y, Seconds(300), Seconds(1))
    //  You will often see this written in the following shorthand:
    //val hashtagCounts = hashtagKeyValues.reduceByKeyAndWindow( _ + _, _ -_, Seconds(300), Seconds(1))
```
* Next thing we want to do is to filter out anything that's not a hashtag
* Next we will count each individual hashtags by 1 and reduce by Key and Window in the next line of code
* Since we are moving the sliding window to the next second, we need to remove y from x, so we just say x minus y
* Because with that sliding window, sometimes we have to take stuff out as well as that stuff in.

```
	// Sort the results by the count values
	val sortedResults = hashtagCounts.transform(rdd => rdd.sortBy(x => x._2, false))

	// Print the top 10
	sortedResults.print

	// Set a checkpoint directory, and kick it all off
	// I could watch this all day!
	ssc.checkpoint("C:/checkpoint/")
	ssc.start()
	ssc.awaitTermination()
```
* Next, we will call transform on the hashtagCounts to get a Dstream which is sorted, and we will print the top 10 results
* The last step will be explicitly restarting and ending the Spark Streaming process if it fails.
* For me, I could see the results but some errors has occured which maybe related to Spark drivers being out of date.
* The top 10 results, are related to Korean which maybe surprising given you thought the results will be in English.