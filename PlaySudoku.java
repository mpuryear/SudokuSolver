/*********************************
 * Author : Mathew Puryear
 *  Date : Spring 2017
 * Filename : PlaySudoku.java
 * Purpose : The purpose of this class is not necessarily to allow the user to play
 *           but to provide the tools for an ai to play sudoku. Given a main, the
 *           required tools to write a sudoku game do exists in this file.
 *
 *********************************/



import java.math.*;
import java.util.*;
import java.lang.*;
import java.io.*;


class PlaySudoku{

    public static boolean DEBUGGING = false;
        
    SudokuBoard board;

    public PlaySudoku() {
	board = new SudokuBoard();
    }

    public PlaySudoku(String s) {
	board = new SudokuBoard(s);
    }

    public PlaySudoku(SudokuBoard sb) {
	board = new SudokuBoard(sb);
    }

    public PlaySudoku(PlaySudoku ps) {
	board = new SudokuBoard(ps.board);
    }

    public boolean equals(PlaySudoku ps) {
	// Loop over all positions and return true if they all match, else false.
	boolean b = true;
	for(int r = 0; r < board.DEFAULT_BOARD_SIZE; r++){
	    for(int c = 0; c < board.DEFAULT_BOARD_SIZE; c++) {
		b &= (this.board.getBoard()[r][c] == ps.board.getBoard()[r][c]);
	    }
	}
	return b;
    }
    
    public ArrayList<ArrayList<Move>> getValidMoves() {
	// This will return all valid moves ordered by rowxcol with
	// the least moves per rowxcol in the front 
	ArrayList<Move> moves = new ArrayList<Move>();
	ArrayList<ArrayList<Move>> collectionOfMoves = new ArrayList<ArrayList<Move>>();
	for(int r = 0; r < board.DEFAULT_BOARD_SIZE; r++) {
	    for( int c = 0; c < board.DEFAULT_BOARD_SIZE; c++) {
		moves = new ArrayList<Move>();
		for(int i = 1; i <= board.DEFAULT_BOARD_SIZE; i++) {
		    // Loop from 1 to 9 as 0 is not a valid option.
		    if(isMoveValid(r,c,i))
			moves.add(new Move(r,c,i));
		}
		if(!moves.isEmpty())
		    collectionOfMoves.add(moves);
	    }
	}

	// Sort the collection such that smallest comes first.
	Collections.sort(collectionOfMoves, new Comparator<ArrayList>() {
		public int compare(ArrayList a1, ArrayList a2) {
		    return a1.size() - a2.size();
		}
		
	    });
	return collectionOfMoves;
    }

    private boolean isMoveValid(Move m) {
	return isMoveValid(m.row, m.col, m.value);
    }
    
    private boolean isMoveValid(int row, int col, int value) {
	// Given a row, col and value, determing if this move is valid.
	
	boolean b = true;
	b &= (value > 0 && value <= 9); // Do not allow numbers other than [1,9]
	b &= (board.getBoard()[row][col] == 0); // Do not allow the user to make a move on a non 0.
	b &= isRowValid(row, value);
	b &= isColValid(col,value);
	b &= isBlockValid(row,col,value);
	return b;
    }
    
    private boolean isRowValid(int row, int value) {
	// Return true if value is a legal number to be placed in Row row.
	
	boolean b = true;
	for(int i = 0; i < board.DEFAULT_BOARD_SIZE; i++) {
	    b &= (value != board.getBoard()[row][i]);
	}
	return b;
    }

    private boolean isColValid(int col, int value) {
	// Return true if value is a legal number to be placed in Column col
	
	boolean b = true;
	for( int i = 0; i < board.DEFAULT_BOARD_SIZE; i++) {
	    b &= (value != board.getBoard()[i][col]);
	}
	return b;
    }

    private boolean isBlockValid(int row, int col, int value) {
	// Return true if the value passed into position (row,col) is a valid move for that "block"
       	
	/*
	 *  |--0,0--||--0,1--||--0,2--|
	 *0 | 0 1 2 || 3 4 5 || 6 7 8 |  
	 *1 | 0 1 2 || 3 4 5 || 6 7 8 |  
	 *2 | 0 1 2 || 3 4 5 || 6 7 8 |  
	 *  |-------||-------||-------|
	 *  |--1,0--||--1,1--||--1,2--|
	 *3 | 0 1 2 || 3 4 5 || 6 7 8 |
	 *4 | 0 1 2 || 3 4 5 || 6 7 8 |  
	 *5 | 0 1 2 || 3 4 5 || 6 7 8 |  
	 *  |-------||-------||-------|
	 *  |--2,0--||--2,1--||--2,3--|
	 *6 | 0 1 2 || 3 4 5 || 6 7 8 |  
	 *7 | 0 1 2 || 3 4 5 || 6 7 8 |  
	 *8 | 0 1 2 || 3 4 5 || 6 7 8 |  
	 *  |-------||-------||-------|
	 *
	 */

	 
	row = row / 3;
	col = col / 3;
	boolean b = true;
	for(int r = 0; r < 3; r++) {
	    for(int c = 0; c < 3; c++) {
		b &= (value != board.getBoard()[3*row + r][3*col + c]);
	    }
	}
	return b;
	
    }

    public boolean gameOver() {
	// Game is over if their are no valid moves remaining.
	return (getValidMoves().isEmpty());	
    }


    public boolean isSolved() {
	// The board is solver if there are no 0's left on the board.
	boolean b = false;
	for(int r = 0; r < board.DEFAULT_BOARD_SIZE; r++) {
	    for(int c = 0; c < board.DEFAULT_BOARD_SIZE; c++) {
		b |= (board.getBoard() [r][c] == 0);
	    }
	}
	return !b;
    }

    public void makeMove(int row, int col, int value) {
	board.getBoard()[row][col] = value;
    }

    public void makeMove(Move m) {
	board.getBoard()[m.row][m.col] = m.value;
    }
    
    public static void main(String[] args) {

	// -test <filename>
	PlaySudoku ps = new PlaySudoku(args[0]);
	Scanner reader = new Scanner(System.in);
	ArrayList<ArrayList<Move>> collectionOfMoves = new ArrayList<ArrayList<Move>>();
	ArrayList<Move> moves = new ArrayList<Move>();
	
	if(args.length > 1 && args[1].equals("-test"))
	    DEBUGGING = true;
	

	while(!ps.gameOver()) {
	    ps.board.printBoard();

	    
	    moves = ps.getValidMoves().get(0);

	    if(DEBUGGING) {
		for(int i = 0; i < moves.size(); i++) {
		    
		    System.out.println("Valid Moves:");
		    moves.get(i).print();
		}
		System.out.println("isSolved(): " + ps.isSolved());
	    }
	    int row, col, value;
	    System.out.println("Insert next row col value(space delimited)\n");
	    row = reader.nextInt();
	    col = reader.nextInt();
	    value = reader.nextInt();

 	    if(ps.isMoveValid(row,col,value))
		ps.makeMove(row,col,value);
	    if(ps.isSolved())
		System.out.println("Board solved");
	}
    }
}
