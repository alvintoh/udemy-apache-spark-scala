# 43. Introducing MLLIB

## INTRODUCING MLLIB
Movie recommendations using Spark's machine learning library

### MLLIB Capabilities
* Feature extraction
 * Term Frequency /Inverse Document Frequency useful for search

* Basic statistics
 * Chi-squared test, Pearson or Spearman correlation, min, max, mean, variance

* Linear regression, logistic regression
* Support Vector Machines
* Naives Bayes Classifier
* Decision trees
* K-Means clustering
* Principal component analysis, singular value decomposition
* Recommendations using Alternating Least Squares

### Special MLLIB Data Types
* Vector (dense or sparse)
* LabeledPoint
* Rating

### For More Depth
* I really like "Advanced Analytics with Spark" from O'Reilly

### Let's Make Some Movie Recommendations
```
	val data = sc.textFile("../ml-100k/u.data")

	val ratings = data.map( x => x.split('/t').map( x => Rating(x(0).toInt, x(1).toInt, x(2).toDouble)).cache()

	val rank = 8
	val numIterations = 20
	val model = ALS.train(ratings, rank, numIterations)

	val recommendations = model.recommendProducts(userID, 10)
```
* IT'S JUST THAT EASY ... Let's run it.