# 5. [Exercise] Scala Basics, Part 2

### Activity
* When you look at the file LearningScala1.sc
* The syntax for Scala allows you to set printf which is formatting
```
	// printf style:

	println(f"Pi is about $piSinglePrecision%.3f")
	//> Pi is about 3.142

	println(f"Zero padding on the left: $numberOne%05d")
	//> Zero padding on the left: 00001
```

* Scala allows you to subsitite variables with s prefix
```
	// Substiting in variables

	println(s"I can use s prefix to ue variable like $numberOne $truth $letterA")
	//> I can use the s prefix to use variables like 1 true a
```

* Scala allows you to substitute expressions with curly brackets
```
	// Substituting expressions (with curly brackets):

	println(s"The s prefix isn't limited to variables; I can include any expression. Like ${1+2}")
	//> The s prefix isn't limited to variables; I can include any expression. Like 3
```

* Scala can also use regular expression
* Dealing with booleans
* You can use >, <, and == to compare across val
* Scala you can use == to compare across two strings to see if both are the same string "Picard" == "Picard"

### Exercise
```
	// Write some code that takes the value of pi, doubles it, and then prints it within a string with
	// three decimal places of precision to the right.
	// Just write your code below here; any time you save the file it will automatically display the results!

	val newPi = pi * 2
	println(f"This is the new Pi: $newPi%.3f")
```