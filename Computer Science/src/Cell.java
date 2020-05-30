package minesweeper;


import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/* Purpose of this Class is to construct individual cell with row and column 
 * Class communicates with other classes via its static fields
 * Cells are stylised and Clickable which returns its row and col # 
 * */


public class Cell extends StackPane {
		private int row = 0;
		private int column = 0;
		private static Text value = new Text("12"); 
		
		//Static Variables: transfer data to other class
	    public static ArrayList<Integer> track =new ArrayList<>();        //Add coordinates of each click to Array    
	    public static ArrayList<Integer> track_Bombs =new ArrayList<>();  //Adds only visited Cell to Array
	    public static int cellCount=0;    
		
	
		
		public Cell(Text s,int i,int j) {            //Constructor for reveling a cell. internally creates a identical cell. 
			super(new Rectangle(30,30),s);
			
			s.setFill(Color.web("#f5fffa"));
			this.setOnMouseClicked(e -> handleMouseClick()); //Each cell is Clickable
			row = i;
			column = j;
			if(!track_Bombs.contains(row*100+column)) {
				track_Bombs.add(row*100+column);
				cellCount++;} // if a cell is visited once, updated count
			
			
		}
		
		public Cell(int i, int j) {					//Constructor to create a default cells with Unique row,and Column from caller. 
			super(value,new Rectangle(30,30));
			
			setPadding(new Insets(1,1,1,1));
			setStyle("-fx-border-color: black");
			this.setPrefSize(40, 40);
			this.setOnMouseClicked(e -> handleMouseClick()); //Each cell is Clickable
			value = new Text("40");
			value.setFill(Color.RED);
			row = i;
			column = j;
		
		}

	public  void handleMouseClick() {

			if(!track.contains(row*100+column)) {    //Tracks all the clicked cells
				  track.add(row*100+column);}
			
			BackEnd.Control(row,column); // pass conrtol to Backend Class with unique row & col
			
		}

	//Getters (not used);
	public void setValue(int i) {
			value = new Text(""+i);			
	}	
	public int getValue() {
		int x = Integer.parseInt(value.getText());
		
		return x;}
	
	public int getRow() {return row;}
	public int getColumn() {return column;}


		
}
