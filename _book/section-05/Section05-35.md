# 35. Creating Similar Movies from One Million Ratings on EMR

## SETTING UP AND RUNNING FROM YOUR AMAZON EMR CLUSTER

### Setting Up A Amazon EMR Cluster
* Download [MovieLens 1M Dataset](https://grouplens.org/datasets/movielens/) which contains 1 million ratings, and you can copy the file into Amazon S3 service
* You can create a new Bucket and copy the your files into that Bucket
* The Bucket on Amazon S3 should contain MovieSimilarities1M.jar(For my setup the JAR name is: MovieSimilarities1M-assembly-1.0.jar), and that is the self contained Spark driver script bubbled up into a JAR file.
* The Bucket should also contain ml-1m folder which contains the data set files for one million rating
* Open ml-1m folder and it should contain the following files of README, movies.dat, ratings.dat and users.dat
* Go to [Amazon AWS](aws-amazon.com), and create one account if you don't have one. This account is going to use real money, so don't do yourself unless you're comfortable with that.
* Once you get into the console, select EMR under Analytics
* Click on create Cluster
* Name the Cluster name as
```
	MovieSims1MTest
```

* Ensure that Logging is checked, as you retrieve those logs in the event of a failure in the S3 folder
* Select Spark Spark 1.6.2 Hadoop 2.7.2 YARN with Ganglia 3.7.2
* Stick with the default configuration unless you want to adjust the different instances and capacity which comes to different costs
* You can also choose to create an EC2 Key Pair which allows you to have some way of actually connecting to your nodes once you create them
* Just follow the instruction to Create an Amazon EC2 Key Pair and PEM file for your Windows or Mac/Linux setup
* Once you create that EC2 Key pair, save that PPK file somewhere safe, because there's no way to get it back after you've gotta than initial download.
* Once everything is set up properly, just click on Create Cluster
* It will take about 10 -15 minutes for Amazon to actually go out there and find some available hardware and get it all spun up and configured and booted up for you
* After your cluster is set up, it is ready and waiting
* To do that, you need to first connect to it
* Under the master public DNS, it gives me the externally available address of the master node that I'm gonna run my script from and if I click on this Ssh link it will tell you exactly to Connect to the Master Node Using SSH
* So for Windows, you can use something called like PuTTY or a terminal program like what I am using.
* There is a download link there for you to download PuTTY and instructions for you to follow
* So I am going to copy the address under Host Name Field
* Open up PuTTY, and paste that address under Host Name (or IP address)
* Next click on SSH, and Auth to specify your Private key for authentication
* Click on browse and direct it to your EC2 private key .ppk file that you want to use.
* Now just hit open, and we should be able to log into our master node.
* Depending on your security settings, you might actually get a timeout at this point
* So if you are trying to figure out why you can't connect no matter what you try with your firewall or whatever you still can't get through
* Odds are there is a block on the server side so if you do run into that
* A quick tip, you can click on the security group here for the master node in the console
* Once inside, click inbound and make sure you have an ssh port open
* So in this case, I had to actually manually add a ssh TCP port 22 to the IP address that I'm connecting from.
* So if you are having trouble that's probably what you need to do.
* Once you are finished, you can go back to the EMR console

### Running Commands From The PuTTY Instance Connected To EMR
* Once you are connected to that Putty Instance with EMR, input the following commands to run the JAR script
```
	ls

	pwd
	/home/hadoop

	aws s3 cp s3://sundog-spark/MovieSimilarities1M.jar ./
```
* This is copying the JAR file from S3 to /home/hadoop

```
	ls

	aws s3 cp s3://sundog-spark/ml-1m/movies.dat ./

	ls
```
* This is copying the movies.dat file from S3 to /home/hadoop

* All I need to do now is to type in the spark-submit of the name of jar file and do the execution command
* Stars Movie movieID is 260 in the 1 million ratings dataset
```
	spark-submit MovieSimilarities1M.jar 260
```
* The spark job should now execute on your EMR cluster
* The output should now show the top similar movies for a Star Wars Episode Four A New Hope based on one million real movie ratings
* The results look pretty similar
* Go back to EMR console and press Terminate to Terminate cluster
* It is important to do that, as you are still going to be billed for the time on that cluster, even though you may not be running any Spark Jobs