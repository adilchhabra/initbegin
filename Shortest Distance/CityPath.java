import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;

class CityPath  {
    /* Finds the shortest round-trip route that visits all the cities. */
    /* ADIL CHHABRA*/

    public static void main(String[] args) {

	/* 
	 *  The filename containing a distance matrix is entered on the command line when the program is run.
	 *  For example, if we want to read in the distance matrix in a file called bays29.txt, the
	 *  program should be run with:
	 *
	 *               java CityPath bays6.txt
	 *
	 *  The 'filename' variable will get assigned the String value the User enters on the command line.
	*/
	String filename;
	if (args.length < 1) {
	    System.out.println("Error: no filename indicated.");
	    System.out.println("Usage: 'java CityPath <filename>'");
	    System.exit(1);
	}
	filename = args[0];

	Scanner infile = openFile(filename); // open the file for reading
	int n = getNumberOfLines(infile); // dimension of the distance matrix (total num of cities)
	infile = openFile(filename); // reopen the file
	int[][] distance = readInMatrix(infile,n); // distance matrix
	infile.close(); // make the file ready to be used again
	System.out.println("");
	System.out.println(filename + " has a matrix with " + n + " cities.");
	printMatrix(distance);

	// TO DO
	// Find shortest route recursively
	int numVisited = 0; //initial number of cities visited
	int [] path = new int [n]; //create small array of size same as 'n' number of cities
	int filler = -1; //arbitrary initial value
	for(int i = 0; i<n; i++) { //to fill path array with initial values
	    path[i]=filler;	   
	}

	int [] shortestPath = shortestCityPath(distance, path, numVisited, n); //to store shortest path in a new array
	System.out.println("");
	System.out.println("Total distance = " + pathDistance(distance, shortestPath));
	System. out.println("Shortest path is: ");
	printPath(shortestPath); //prints path			  

	// TO DO
	// Print the shortest route and the total distance on that route.
	
    } // main


    /* This method recursively finds the shortest round-trip route through all the cities 
     * represented in the distance matrix, d.  
       Parameters:
       int[][] d -- the distance matrix
       int[] path -- the partial path we've taken so far
       int numVisited -- the total number of cities we've visited so far in path
       int n -- the total number of cities we need to visit
       Returns the shortest path as an array.
     */
    private static int[] shortestCityPath(int[][] d, int[] path, int numVisited, int n) {

	if(hasRepeats(path)) //if number is repeated in the array, null is sent back during recursion and the for loop continues
	    return null;

	if(numVisited == n){ //base case
	    return path;
	}
    	int shortestSoFar = Integer.MAX_VALUE; //initial value set as maximum possible so that later value is certainly smaller
	int [] bestPath = new int [n]; //array which stores the shortest path upto a point

	for(int i = 0; i<n; i++) { //to fill in and repeatedly test values
	   		
	    if(!sameAgain(path, i)) { //to check if number to be filled in has been tested before
		path[numVisited]=i; //fill in value
		int [] copy = new int [n]; //creation of copy array to retain values of path array despite recursion
		    System.arraycopy(path,0,copy,0,n);
		    int [] shortestPath = shortestCityPath(d, copy, numVisited+1, n); //recursive statement, numVisited increases as a new building is visited every time through
		    
		    if(shortestPath != null) { //if path survives recursive sequence, that is, has no repeats
			int newDistance = pathDistance(d, shortestPath); //to get total distance through travelling as per path array
			if(newDistance<shortestSoFar) { //check if total distance is smaller than the smallest distance till that particular point in recursion
			    shortestSoFar=newDistance; //set shortest distance of the current path as overall shortest distance for future tests
			    bestPath = shortestPath; //array corresponding to shortest array till then is stored
		    }
		  }
	     }
	}
    
	return bestPath;

    } // shortestCityPath

    //tests if value to be filled has been tested before
    private static boolean sameAgain(int [] a, int j) {
	for(int i = 0; i<a.length; i++) {
	    if(a[i]==j) {
		return true;
	    }
	}
	    return false;	
    }

    //tests if path array has a repeat
     private static boolean hasRepeats(int [] a) {
	 for(int i = 0; i<a.length; i++) {
	     if(a[i]!= -1) //to ignore cases in which values in the path array = -1
		 break; 
	     if(a[i]==-1)
		 return false;
	 }
	 for(int i =0; i<a.length-1; i++) {
	     if(a[i+1]!=-1 && a[i] == a[i+1])
		 return true;
	 }
	 return false;
     }
    // This method computes the total distance traveled on a roundtrip
    // route starting at path[0], traveling to path[1], path[2], ...,
    // path[path.length-1], and ending back at path[0].
    private static int pathDistance(int[][] d, int[] path) {
	int totalDistance = 0;
	for(int i=0; i<path.length-1; i++) { //adds distance between consecutive buildings
	    totalDistance += d[path[i]][path[i+1]];
	}
	totalDistance += d[path[path.length-1]][path[0]]; //to create round distance, that is, go back from last building to first
	return totalDistance;
    } // pathDistance

    // This method prints the cities in a path
    private static void printPath(int[] path) {
	for (int city : path)
	    System.out.print(city + "\t");
	System.out.println();
    } // printPath


    // This method returns the number of lines in the file the Scanner infile
    // represents
    private static int getNumberOfLines(Scanner infile) {
	int lineCount = 0;
	while (infile.hasNextLine()) {
	    infile.nextLine();
	    lineCount++;
	}
	infile.close();
	return lineCount;
    } // getNumberOfLines

    // Prints a matrix to the screen, 1 row per line, columns separated by tabs
    private static void printMatrix(int[][] a) {
	System.out.println("");
	for(int row=0; row<a.length; row++) {
	    for(int col= 0; col<a.length; col++) {
		System.out.print(a[row][col] + "\t");
	    }
	    System.out.println("");
	}
    } // printMatrix


    // This method reads in the distance matrix of integers in the file indicated by the
    // Scanner parameter.
    public static int[][] readInMatrix(Scanner in, int n) {
	int [][] a = new int [n][n];
	for (int row=0; row<n; row++) {
	    for(int col=0; col<n; col++) {
		a[row][col] = in.nextInt();
	    }
	}
	return a;
    } // readInMatrix
   

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
