/*******************************
 * Author : Mathew Puryear
 * Class : cs480
 * Date : Spring 2017
 * Filename : Move.java
 * Purpose: this class stores a row col and value for a valid move in sudoku. 
 ******************************/

class Move{
    public int row;
    public int col;
    public int value;
    public Move(int r, int c, int v) {this.row = r; this.col = c; this.value = v;}
    public void print() {
	System.out.println("row: " + row);
	System.out.println("col: " + col);
	System.out.println("value: " + value);
    }
}
