Sudoku Solver
======================

Sudoku solver reads the board provided in input text file and solve it using backtracking algorithm.

Requirements
	.Java 8
	.Maven

How do I run it?

You can run the application using

mvn spring-boot:run -Dspring-boot.run.arguments=<<inpufilename>>

Or you can build the JAR file with

mvn clean install

and run the JAR 

java -jar sudokusolver-0.0.1-SNAPSHOT.jar <<inputfilename>>

Sample input file is in /resources/InputSudoku.txt where 0 represents empty cells.