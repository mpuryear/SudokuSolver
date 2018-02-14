/************************************************
 * Author : Mathew Puryear
 * Date : Spring 2017
 * Filename : SudokuSolver.java
 * Purpose : Take a given sudokuboard and print the solution to the user, using a Depth-First search to find it.
 **********************************************/

import java.util.*;
import java.io.*;

class SudokuSolver{

    public static boolean DEBUGGING;
    public static void main(String[] args) throws FileNotFoundException {

	DEBUGGING = false;
	
	PlaySudoku initialBoard;
	if(args.length >= 1) {
	    if(!(args[0].equals("-test"))) {
		/*
		 * Param1 = <filename>
		 */
		
		initialBoard = new PlaySudoku(args[0]);
		System.out.println("\nTesting: " + args[0]);
		System.out.println("\nUnsolved Board: \n");
		initialBoard.board.printBoard();
		System.out.println("\nSolved Board: \n");
		run(initialBoard);
	    }
	    else {
		
		/*
		 *  param1 = -test
		 */

		// run all of the files in tests.input
		ArrayList<String> testBoards = new ArrayList<String>();

		File folder = new File("./boards/");
		File[] listOfFiles = folder.listFiles();

		for(int i = 0; i < listOfFiles.length; i++) {
		    testBoards.add(listOfFiles[i].getName());
		}

		// Empty our list of strings and use them to
		// run our test on every board stored.
		while(!testBoards.isEmpty()) {
		    String filename = testBoards.get(0);
		    testBoards.remove(0);
		    System.out.println("\nTesting: " + filename);
		    PlaySudoku ps = new PlaySudoku("./boards/" + filename);
		    System.out.println("\nUnsolved Board: \n");
		    ps.board.printBoard();
		    System.out.println("\nSolved Board: \n");
		    run(ps);
		}
	    }
	}
	else {
	    /*
	     * NO PARAM
	     */
	    
	    Scanner reader = new Scanner(System.in);
	    System.out.println("Input a filename of a sudoku board " +
			       "(incorrect filename will cause nullptr exception)");
	    String file = reader.nextLine();
	    initialBoard = new PlaySudoku(file);
	    run(initialBoard);
	}
	

	
    }
    
    public static void run(PlaySudoku initialBoard) {
	/**
	 *
	 * Accept a board, and print the solved version of said board. Additionally print
	 * the total time taken to find a solution.
	 *
	 **/ 
	

	// Snapshot our start time to calculate delta time
	long startTime = System.currentTimeMillis();

	// Fringe creation
	ArrayList<PlaySudoku> fringe = new ArrayList<PlaySudoku>();
	fringe.add(initialBoard);
	
	
	while(!fringe.isEmpty()) {
	    
	    // fringe.front() and pop_front()
	    PlaySudoku currentBoard = fringe.get(0);
	    fringe.remove(0);
	    
	    // Reduce our breadth by not including every possible move. Only the valid moves for
	    // our row and col with the least possible moves. If there is not a successful board of
	    // these children, then a successful board is not possible.
	    ArrayList<Move> currentMoves = currentBoard.getValidMoves().get(0);
	    
	    // We put all of our moves in a list, and then insert them
	    // into the actual fringe backwards
	    ArrayList<PlaySudoku> tempFringe = new ArrayList<PlaySudoku>();
	    
	    // Make all potiential moves on our given board and insert them
	    // into the fringe.
	    while(!currentMoves.isEmpty()) {
		PlaySudoku newBoard = new PlaySudoku(currentBoard);
		
		// front and pop_front the moves
		Move nextMove = currentMoves.get(0);
		currentMoves.remove(0);
		
		newBoard.makeMove(nextMove.row, nextMove.col, nextMove.value);
		
		// if the board is not currently solved we need to add all
		// children back to the front of the fringe.
		if(newBoard.isSolved()) {
		    newBoard.board.printBoard();
		    long endTime = System.currentTimeMillis();
		    long deltaTime = endTime - startTime;
		    System.out.println("\nTime: " +
				       deltaTime + "ms");
		    return;
		}
		
		if(!newBoard.gameOver()) {
		    // We create this queue using push_front
		    // in order to remove it all from the queue
		    // and place it on the fringe in order
		    tempFringe.add(0, newBoard);
		}
		// The board is unsolvable so we do not add it to fringe
	    }
	
	
	    // Insert all of the tempfringe into the front of the fringe
	    // in a reverse order, thus allowing us to expand the left most child node first.
	    while(!tempFringe.isEmpty()){
		PlaySudoku temp = tempFringe.get(0);
		tempFringe.remove(0);
		fringe.add(0, temp);
	    }
	}
    }
}


