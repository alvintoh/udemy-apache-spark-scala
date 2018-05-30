# 7. [Exercise] Functions in Scala

### Activity
* For this activity, go ahead and import from the resource the file LearningScala3.scala
* LearningScala3.scala mainly talks about the building blocks of Functional Programming for Scala Code
* The follow defines how to define a function in Scala
* Scala do not need to explicitly declare a return statement to return the value
* Scala will return the last value found in the function scope
```
	// Functions

	// Format is def <function name>(parameter name: type...) : return type = { expression }
	// Don't forget the = before the expression!
	def squareIt(x: Int) : Int = {
		x * x
	}                                               
	//> squareIt: (x: Int)Int

	def cubeIt(x: Int): Int = {x * x * x}           
	//> cubeIt: (x: Int)Int

	println(squareIt(2))                            
	//> 4

	println(cubeIt(2))                              
	//> 8
```

* Scala can take in other functions as parameter for its current Function parameter
* Need to declare => as the other function to transform to this current method return value
```
	// Functions can take other functions as parameters

	def transformInt(x: Int, f: Int => Int) : Int = {
		f(x)
	}                                               
	//> transformInt: (x: Int, f: Int => Int)Int

	val result = transformInt(2, cubeIt)            
	//> result  : Int = 8

	println (result)                                
	//> 8
```

* You can declare Lambda functions in Scala without even explicitly giving them a name
```
	// "Lambda functions", "anonymous functions", "function literals"
	// You can declare functions inline without even giving them a name
	// This happens a lot in Spark.
	transformInt(3, x => x * x * x)                 //> res0: Int = 27

	transformInt(10, x => x / 2)                    //> res1: Int = 5

	transformInt(2, x => {val y = x * 2; y * y})    //> res2: Int = 16
```

### Exercise
```
	// EXERCISE
	// Strings have a built-in .toUpperCase method. For example, "foo".toUpperCase gives you back FOO.
	// Write a function that converts a string to upper-case, and use that function of a few test strings.
	// Then, do the same thing using a function literal instead of a separate, named function.

	def transform(str: String) : String = {str.toUpperCase}
	//> transform: (str: String)String

	transform("testing1")                           
	//> res3: String = TESTING1
	transform("testing2")                           
	//> res4: String = TESTING2
	transform("testing3")                           
	//> res5: String = TESTING3

	def transformString(str: String, functionTesting: String => String) : String = {
		functionTesting(str)
	}                                               
	//> transformString: (str: String, functionTesting: String => String)String

	transformString("testing4", transform)   
	//> res6: String = TESTING4

	transformString("testingliteral", x => x.toUpperCase)
	//> res7: String = TESTINGLITERAL
```