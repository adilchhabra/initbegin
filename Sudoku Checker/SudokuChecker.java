import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;

class SudokuChecker {
    /* Class to check the correctness of a solution 
     * to a Sudoku puzzle. 
     *
     * Adil Chhabra, 11/2015
     */

    
    public static void main(String[] args) {

	/* 
	 *  The filename containing Sudoku solutions is entered on the command line when the program is run.
	 *  For example, if we want to read in the Sudoku solutions in a file called sudoku_1.txt, the
	 *  program should be run with:
	 *
	 *               java SudokuChecker sudoku_1.txt
	 *
	 *  The 'filename' variable will get assigned the String value the User enters on the command line.
	*/
	String filename;
	if (args.length < 1) {
	    System.out.println("Error: no filename indicated.");
	    System.out.println("Usage: 'java SudokuChecker <filename>'");
	    System.exit(1);
	}
	filename = args[0];

	// Create a Scanner object from the file whose name is stored in the 'filename'
	// variable
	Scanner infile = openFile(filename);
	    
	// Read in each of the 2-D arrays (grids) of integers from the file, one at a time.
	// For each 2-D array, call checkRows(), checkCols(), and checkAllSubGrids()
	// to determine whether the array violates any of the rules describing a valid
	// Sudoku solution.
	// Print each grid followed by a message indicating that the grid is valid or a
	// message or multiple messages indicating that the grid is not valid because of
	// a row, column, and/or subgrid.

	//  ---------- TO DO -----------------
	while(infile.hasNextLine()) {   //to run the program till the text file ends

	int [][] sudoku = readInMatrix(infile); //to store 9x9 array of ints from text file to individual sudokus

	printMatrix(sudoku); //to print each 9x9 array

	if(checkAllSubGrids(sudoku) && checkRows(sudoku) && checkCols(sudoku)) { //to check for valid sudoku solution
	    System.out.println("The Grid is valid.");
	}

	//if any individual test for valid sudoku solution fails, subsequent message is printed

	if(!checkAllSubGrids(sudoku))
	    System.out.println("Grid is invalid because of a subgrid.");
	if(!checkRows(sudoku))
	    System.out.println("Grid is invalid because of a row.");
	if(!checkCols(sudoku))
	    System.out.println("Grid is invalid because of a column.");

	} //while loop

	// make the file ready to be read again
	infile.close();
	
    } // main

    // Prints a matrix to the screen, 1 row per line, columns separated by tabs
    private static void printMatrix(int[][] a) {
	//matrix passed in is the one returned from readInMatrix
	System.out.println("");
	for(int row=0; row<9; row++) {
	    for(int col=0; col<9; col++) {
		System.out.print(a[row][col] + "\t"); //to leave gap of tab between ever column
		    }
	    System.out.println(""); //to have all rows on different lines
	}
		
    } // printMatrix


    // This method reads in the next 9x9 matrix of integers in the file indicated by the
    // Scanner parameter.
    public static int[][] readInMatrix(Scanner in) {
	int [][] a = new int [9][9]; //initialization of array to be returned
	
	for(int row=0; row<9; row++) {
	    for(int col=0; col<9; col++) {
		a[row][col]=in.nextInt(); //obtaining next available token from text file
	    }
	}
	return a;
    } // readInMatrix
   

    // This method returns true if each of the 9 3x3 subgrids contains all the integers 1-9, false
    // otherwise.  It checks each subgrid individually by calling the method checkSubGrid();
    public static boolean checkAllSubGrids(int[][] a) {
	for (int row = 0; row < a.length; row += 3) {
	    for (int col = 0; col < a[0].length; col += 3) {
		if (!checkSubGrid(a,col,col+2,row,row+2))
		    return false;
	    }
	}
	return true;
    } // checkAllSubGrids

    // This method returns true if the 3x3 subgrid of a spanning between rows top and bottom and
    // columns left and right contains all the integers 1-9, false otherwise.  The parameter a is a 9x9
    // matrix.
    public static boolean checkSubGrid(int[][] a, int left, int right, int top, int bottom) {

	boolean doBreak=false; //breaking out of nested loop
	for(int c =1; c<10; c++) { //to compare with other elements in matrix
	    for(int i=top; i<=bottom; i++) { //iterates over rows
		if(doBreak) {
		    doBreak = false;
		    break;
		}
		for(int j=left; j<=right; j++) { //iterates over columns
		    if(a[i][j] == c) { //if comparison proves a repeat, break
			doBreak = true; 
			break;
		    }
		    if(j == right && i == bottom && a[i][j] != c)  //if comparison proves a repeat, false is returned
			return false;
		}
	    }
	}
    
	return true;
    } // checkSubGrid
    
    // This method returns true if every row contains all the integers 1-9, false otherwise
    public static boolean checkRows(int[][] a) {

	boolean test = true;  //boolean to break from nested for loop
	for(int row=0; row<9; row++) {
	    //create count array in which element of sodoku corresponding to indices of the array increase the sum of the elements in the count array
	    int [] count = new int[10]; //initializing and clearing out count array for every subsequent row
	    for(int col=0; col<9; col++) {
		for(int i=0; i<10; i++) {
		    if(a[row][col]==i) 
			count[i] = count[i] + 1; //to add corresponding indice count
		    if(count[i]>1){ //if same number appears more than once, boolean turns false
		    test = false;
		    }
		}	     
	    }	
	}
    if(test)
	return true;
    else 
	return false;
    } // checkRows


    // This method returns true if every column contains all the integers 1-9, false otherwise
    public static boolean checkCols(int[][] a) { 
	//same as above method but column given precedence over row
	boolean test = true;
	for(int col=0; col<9; col++) {
	    int [] count = new int[10];
	    for(int row=0; row<9; row++) {
		for(int i=0; i<10; i++) {
		    if(a[row][col]==i) 
			count[i] = count[i] + 1;
		    if(count[i]>1){
		    test = false;
		    }
		}	      
	    }	
	}
    if(test)
	return true;
    else 
	return false;

    } // checkCols

    // This method returns a Scanner object that can be used to read the contents
    // from the file indicated by the filename parameter.  If the file cannot be found,
    // the method catches the FileNotFoundException, prints an error message, and exits.
    public static Scanner openFile(String filename) {
	try {
	    Scanner infile = new Scanner(new FileReader(filename));
	    return infile;
	}
	catch(FileNotFoundException e) { //if file not found in directory
	    System.out.println("Error: Cannot find file: " + filename);
	    System.exit(1);
	    return null;
	}
    } // openFile

} // class
