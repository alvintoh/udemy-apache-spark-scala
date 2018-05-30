# 6. [Exercise] Flow Control in Scala

### Activity
* For this activity, go ahead and import from the resource the file LearningScala2.scala
* For Scala, we can control the flow of code
* For example, if and else control flow in 1 line using Functional Programming
* You can also use if and else condition with {} syntax
```
	// Flow control

	// If / else syntax
	if (1 > 3) println("Impossible!") else println("The world makes sense.")
	//> The world makes sense.

	if (1 > 3) {
		println("Impossible!")
  	} else {
  		println("The world makes sense.")
  	}  
```

* You can also use case match for certain cases
```
	// Matching - like switch in other languages:
  	
  	val number = 3                               
	//> number  : Int = 3

	number match {
  		case 1 => println("One")
  		case 2 => println("Two")
  		case 3 => println("Three")
  		case _ => println("Something else")
 	}
 	//> Three      
```

* This is the syntax for For Loop in Scala
```
	// For loops
	for (x <- 1 to 4) {
		val squared = x * x
		println(squared)
	}                                         
	//> 1
	//| 4                                              
	//| 9                                              
	//| 16
```

* This is the syntax for While Loop in Scala
```
	// While loops

	var x = 10                                      
	//> x  : Int = 10

	while (x >= 0) {
		println(x)
		x -= 1
	}                                             
	//> 10
	//| 9                                              
	//| 8                                              
	//| 7                                              
	//| 6                                              
	//| 5                                              
	//| 4                                              
	//| 3                                              
	//| 2                                              
	//| 1                                              
	//| 0
```

* This is the syntax for Do While Loop in Scala in 1 line using Functional Programming
```
	// Do While Loop
	x = 0
	//> 0

	do { println(x); x+=1 } while (x <= 10)
	//| 1
	//| 2
	//| 3
	//| 4
	//| 5
	//| 6
	//| 7
	//| 8
	//| 9
	//| 10
```

* In Scala, you can print the result returned by the expression, even while not assigning any val to it
```
	// Expressions

	// "Returns" the final value in a block automatically
	{val x = 10; x + 20}                           
	//> res0: Int = 30

	println({val x = 10; x + 20})            
	//> 30
```

### Exercise
```
	// EXERCISE
	// Write some code that prints out the first 10 values of the Fibonacci sequence.
	// This is the sequence where every number is the sum of the two numbers before it.
	// So, the result should be 0, 1, 1, 2, 3, 5, 8, 13, 21, 34

	var prevVal = 0                          
	//> prevVal  : Int = 0
	var prevPrevVal = 1                      
	//> prevPrevVal  : Int = 1
	var i = 0                                
	//> i  : Int = 0

	do{
		println(prevVal)
		val sum = prevPrevVal + prevVal
		prevPrevVal = prevVal
		prevVal = sum
		i += 1
	 } while (i < 10)                         
	//> 0
	//| 1
	//| 1
	//| 2
	//| 3
	//| 5
	//| 8
	//| 13
	//| 21
	//| 34
```