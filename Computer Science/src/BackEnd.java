package minesweeper;

import java.util.ArrayList;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/*This Class contains all logic behind the game
 * A 2-D int Array is created to hold solutions
 * An Arraylist contains coordinates of visited cells: helps with recursion calls.
 * Solution to a given game can be found on console output by removing commented section below. 
 * */


public class BackEnd {
	
	private static int row_backend ;
	private static int col_backend;
	private static int bombs;
	
	public static ArrayList<Integer> track_up = new ArrayList<>();	
	public static int[][] board;
	public static int count = 0;
	
	
	//Create a board with user specified row and col
	public static void initializeBoard(int row,int col,int bb) {
			row_backend = row;
			col_backend = col;
			board = new int[row][col];
			bombs = bb;
	}
	
	//Checks the first chosen cell and Plays the game. 
	public static void Control(int firstM,int firstN) {
		
			if(Cell.track.size() == 1) {
				board[firstM][firstN] = 67;
				System.out.println();
				
				initializeBombs(firstN,firstM,board);
				populateNumbers(board);
				recursiveDisplay(board,MineSweeper.grid,firstM,firstN,track_up); }
			
			
			System.out.println();
			playthegame(board,firstM,firstN); //Plays the entire game after first click
					
			// Win Scene function 
			if((row_backend*col_backend) - Cell.cellCount == bombs) {
				
				for(int i= 0 ; i <row_backend;i++) {
					for(int j=0; j<col_backend;j++) {
						if(board[i][j] == 88) {
								MineSweeper.grid.add(new Cell(new Text("X"),i,j), j, i);						
						}
						else if(board[i][j] == 67)
							MineSweeper.grid.add(new Cell(new Text("0"),i,j), j, i);
						else
							MineSweeper.grid.add(new Cell(new Text(board[i][j]+""),i,j), j, i);
					}
				}
				
					MineSweeper.youWinScene();
				}
			

		
	}
	
	// Plays the game
	public static void playthegame(int[][] board,int rowNum,int colNum) {
		
		if(count>0) {
			
			System.out.println();
			if(board[rowNum][colNum] == 88) {
				
				for(int i= 0 ; i <row_backend;i++) {
					for(int j=0; j<col_backend;j++) {
						if(board[i][j] == 88) {
								MineSweeper.grid.add(new Cell(new Text("X"),i,j), j, i);						
						}
						else if(board[i][j] == 67)
							MineSweeper.grid.add(new Cell(new Text("0"),i,j), j, i);
						else
							MineSweeper.grid.add(new Cell(new Text(board[i][j]+""),i,j), j, i);
					}
				}
				MineSweeper.youLoseScene();
			}
			
			else if(board[rowNum][colNum] != 0 && board[rowNum][colNum] != 67) {
				MineSweeper.grid.add(new Cell(new Text(board[rowNum][colNum]+""),rowNum,colNum),colNum,rowNum);
		
			}
			else if(board[rowNum][colNum] == 0) {
				 recursiveDisplay(board,MineSweeper.grid,rowNum,colNum,track_up);

			}
		}
		
		count++;
	}

	
	// Recursively checks all the cells for solutions
	private static void recursiveDisplay(int[][] board, GridPane grid, int rowNum,int colNum,ArrayList<Integer> track) {
		
		
		if(colNum < 0 || rowNum < 0 ) {    //Array out of bound: base Case 1
			return;}
		
		if(colNum>col_backend-1 || rowNum > row_backend-1) { //Array out of bound: base Case 2
			return;}
		
		if(board[rowNum][colNum]!=0 && board[rowNum][colNum]!=67){      //Found a number adjacent to bomb : : base Case 3
			
			grid.add(new Cell(new Text((board[rowNum][colNum]+"")),rowNum,colNum),colNum,rowNum);
			
			return;}
		
		if(!track.contains(rowNum*100+colNum)) { // ArrayList keeps track of visited coordinates 
			track.add(rowNum*100+colNum);
			if(board[rowNum][colNum] == 67) {
				grid.add(new Cell(new Text("0"),rowNum,colNum),colNum,rowNum);
			}
			else {
			grid.add(new Cell(new Text((board[rowNum][colNum]+"")),rowNum,colNum),colNum,rowNum);}
			recursiveDisplay(board,grid,rowNum-1,colNum,track); // Up
			recursiveDisplay(board,grid,rowNum,colNum+1,track); //Right	
			recursiveDisplay(board,grid,rowNum+1,colNum,track); //Down
			recursiveDisplay(board,grid,rowNum,colNum-1,track); //Left
			recursiveDisplay(board,grid,rowNum+1,colNum+1,track); //Down Right
			recursiveDisplay(board,grid,rowNum+1,colNum-1,track); //Down Left
			recursiveDisplay(board,grid,rowNum-1,colNum-1,track); // UP left
			recursiveDisplay(board,grid,rowNum-1,colNum+1, track);  //Up Right
		}
		else {
			return;}
	}

	
	// Randomly initialize Bombs to board 
	public static void initializeBombs(int firstN, int firstM,int[][] board) {
		int numbOfBombs = 0;
		while(numbOfBombs < bombs) {
			int row = (int) (Math.random()*row_backend) ;
			int coloum = (int) (Math.random()*col_backend);		
			while(  !(row ==firstM  && coloum == firstN) &&
					!(row ==firstM  && coloum == firstN+1) &&
					!(row ==firstM  && coloum == firstN-1) &&
					!(row ==firstM-1 && coloum == firstN) &&
					!(row ==firstM-1 && coloum == firstN+1) &&
					!(row ==firstM-1 && coloum == firstN-1) &&
					!(row ==firstM+1 && coloum == firstN) &&
					!(row ==firstM+1 && coloum == firstN+1) &&
					!(row ==firstM+1 && coloum == firstN-1)) {
				
				if(board[row][coloum] == 88)
					break;
				else {
				board[row][coloum] = 88 ;
				numbOfBombs++;				
				break;}
				
			}
			
		}
		
	}	
	
	
	//Based on the placement of the board Updated the neighboring cells with +1; 
	public static void populateNumbers(int[][]board) {
		
		for(int i = 0 ; i <board.length;i++) {
			for(int j = 0 ; j<board[0].length;j++) {
				if(board[i][j] == 88)
					updateNeighbors(board,i,j);
			}
		}
		
		//Remove Comment to See Solution on Console. 
		
/*		for(int i = 0 ; i <board.length ;i++) {
			for(int j = 0 ; j <board[0].length ;j++) {
				System.out.print((board[i][j]==88)?"X\t":board[i][j]+"\t");
			}
			System.out.println();
		}*/
	}

//Updates neighboring cells. 
private static void updateNeighbors(int[][] board, int i, int j) {
		
		if(i == 0 && j == 0) {
			if(board[i][j+1] != 88) //walk right
				board[i][j+1]++;
			
			if(!(board[i+1][j+1] == 88))//walk Down MjD
				board[i+1][j+1]++;
			
			if(!(board[i+1][j] == 88))//walk down
				board[i+1][j]++;				
		}
		else if(i == row_backend-1 && j == 0) {
			 if(board[i][j+1] != 88) //walk right
					board[i][j+1]++;
			 if(!(board[i-1][j] == 88))//walk up
					board[i-1][j]++;
			 if(!(board[i-1][j+1] == 88))//walk up MnD
					board[i-1][j+1]++;	
		 }
		else if(i == 0 && j == col_backend-1) {
			 if(!(board[i][j-1] == 88))//walk left
					board[i][j-1]++;
			 if(!(board[i+1][j] == 88))//walk down
					board[i+1][j]++;
			 if(!(board[i+1][j-1] == 88))//walk Down MnD
					board[i+1][j-1]++;
		 }
		 
		else if(i == row_backend-1 && j == col_backend-1)	{
			 if(!(board[i-1][j] == 88))//walk up
					board[i-1][j]++;
			 
			 if(!(board[i][j-1] == 88))//walk left
					board[i][j-1]++;
			 
			 if(!(board[i-1][j-1] == 88))//walk Up MjD
					board[i-1][j-1]++;		 
		 }
		else if( i == 0) {
			 if(board[i][j+1] != 88) //walk right
					board[i][j+1]++;
			 
			 if(!(board[i][j-1] == 88))//walk left
					board[i][j-1]++;
			 
			 if(!(board[i+1][j] == 88))//walk down
					board[i+1][j]++;
			 
			 if(!(board[i+1][j+1] == 88))//walk Down MjD
					board[i+1][j+1]++;
			 
			 if(!(board[i+1][j-1] == 88))//walk Down MnD
					board[i+1][j-1]++;
				
			}
		else if ( i == row_backend-1) {
			 if(board[i][j+1] != 88) //walk right
					board[i][j+1]++;
			 
			 if(!(board[i][j-1] == 88))//walk left
					board[i][j-1]++;
			 
			 if(!(board[i-1][j] == 88))//walk up
					board[i-1][j]++;
				
			 if(!(board[i-1][j-1] == 88))//walk Up MjD
					board[i-1][j-1]++;
			 
			if(!(board[i-1][j+1] == 88))//walk up MnD
					board[i-1][j+1]++;
				
			}
		else if(j == 0) {
			 if(board[i][j+1] != 88) //walk right
					board[i][j+1]++;
			 if(!(board[i-1][j] == 88))//walk up
					board[i-1][j]++;	
			 if(!(board[i-1][j+1] == 88))//walk up MnD
					board[i-1][j+1]++;
			 if(!(board[i+1][j] == 88))//walk down
					board[i+1][j]++;
			 if(!(board[i+1][j+1] == 88))//walk Down MjD
					board[i+1][j+1]++;	 
		 }
		else if(j == col_backend-1 ) {
			 
			 if(!(board[i][j-1] == 88))//walk left
					board[i][j-1]++;
			 
			 if(!(board[i-1][j] == 88))//walk up
					board[i-1][j]++;	
			 
			 if(!(board[i+1][j] == 88))//walk down
					board[i+1][j]++;
			 
			 if(!(board[i-1][j-1] == 88))//walk Up MjD
					board[i-1][j-1]++;	
			 
			 if(!(board[i+1][j-1] == 88))//walk Down MnD
					board[i+1][j-1]++;
		 }		 
		 
		else {
			if(board[i][j+1] != 88) //walk right
				board[i][j+1]++;
			
			if(!(board[i][j-1] == 88))//walk left
				board[i][j-1]++;
			
			if(!(board[i+1][j] == 88))//walk down
				board[i+1][j]++;
			
			if(!(board[i-1][j] == 88))//walk up
				board[i-1][j]++;
			
			if(!(board[i+1][j+1] == 88))//walk Down MjD
				board[i+1][j+1]++;
			
			if(!(board[i-1][j-1] == 88))//walk Up MjD
				board[i-1][j-1]++;		
			
			if(!(board[i-1][j+1] == 88))//walk up MnD
				board[i-1][j+1]++;		
			
			if(!(board[i+1][j-1] == 88))//walk Down MnD
				board[i+1][j-1]++;
		}	
			
	}
}
