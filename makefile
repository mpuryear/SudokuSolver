make: 
	javac *.java
	gcc GroupSudokuSolver.c -o GroupSudokuSolver.x

clean:
	rm *.*~
	rm *.x
