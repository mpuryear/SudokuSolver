make: 
	javac *.java
	gcc GroupSudokuSolver.c -o GroupSudokuSolver.x

clean:
	rm -f *.*~ *.x *.class
