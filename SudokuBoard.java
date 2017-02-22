/*******************************************
 * Author : Mathew Puryear
 * Class : cs480 
 * Date : Spring 2017 
 * Filename : SudokuBoard.java
 * Purpose : This class provides the 2d array of ints used to drive a game of sudoku
 *
 ****************************************/


import java.math.*;
import java.util.*;
import java.lang.*;
import java.io.*;

class SudokuBoard{

    public static final int DEFAULT_BOARD_SIZE = 9;

    int[][] board;


    
    public SudokuBoard(){
	board = new int[DEFAULT_BOARD_SIZE][DEFAULT_BOARD_SIZE];
	
	for(int i = 0; i < board.length; i++) {
	    for(int j = 0; j < board[0].length; j++) {
		board[i][j] = 0;
	    }
	}
    }

    public SudokuBoard(String s) {
	/* 
	 * s is the name of the file that holds a board.
	 */
	try{
	    board = readFromFile(s);
	}catch(IOException e) {}
    }

    public SudokuBoard(SudokuBoard sb) {
	// Copy Constructor
	this.board = new int[sb.board.length][sb.board[0].length];
	for(int i = 0; i < board.length; i++) {
	    for(int j = 0; j < board[0].length; j++){
		this.board[i][j] = sb.board[i][j];
	    }
	}
    }
    
    public int[][] getBoard() { return board; }
    public void printBoard() {
	for(int i = 0; i < board.length; i++) {
	    for(int j = 0; j < board[0].length; j++) {
		System.out.print(board[i][j] + " ");
	    }
	    System.out.println();
	}
    }
    
    private int[][] readFromFile(String fileName) throws IOException {
	FileInputStream in = null;
	int[][] temp;
	try{
	    in = new FileInputStream(fileName);
	    Scanner sc = new Scanner(in);
	    temp = new int[DEFAULT_BOARD_SIZE][DEFAULT_BOARD_SIZE];
	    int row, col;
	    row = col = 0;
	    while(sc.hasNextInt()) {
		temp[row][col] = sc.nextInt();
		
		col++;
		if(col == 9)
		    row++;
		col %= 9;
	    }
	}
	finally {
	    if( in != null )
		in.close();
	}

	return temp;
    }
    
    public static void main(String[] args) throws IOException{
	
	SudokuBoard sb = new SudokuBoard("test.input");
	sb.printBoard();
	
	
    }
}
