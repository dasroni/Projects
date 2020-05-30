package minesweeper;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Text;

/*This Class contains all GUIs
 * Class communicates with other classes via its static fields and functions
 * Class also contains path to a .CSS file for visual applications ( Please keep the package name Consistence for CSS theme to work )
 * Contains one Stage but multiple scenes which changes via user's actionEvent. 
 * Use can resize Scene to observe all cells. 
 * */

public class MineSweeper extends Application  {
	public static GridPane grid = new GridPane();
	public static BorderPane menu = new BorderPane();
	
	public static int rowSetting = 0;      
	public static int colSetting = 0;
	
	public static int bombs = 0;
	public static Stage window;
	public static Scene gameSetting, game;
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
		window = stage;
		displayMenu();	
		
		window.setScene(gameSetting);
		window.setTitle("Game Settings");
		window.show();	
		
	
	}
	
	//User Menu 
	public static void displayMenu() {
		TextField row = new TextField();
		TextField col = new TextField();
		TextField bb = new TextField();
			
		Button beginner = new Button("Beginner");
		beginner.setStyle("-fx-color:#ff00ff");
		
		beginner.setOnAction(e-> {
			  rowSetting = 9;
			  colSetting = 9;
			  bombs = 10;					
			  BackEnd.initializeBoard(9, 9, 10);
			  gameScene(9,9);
			  window.setScene(game);
			  window.setTitle("MineSweeper");
			  
		});	
		
		Button inter = new Button("Intermediate");
		inter.setStyle("-fx-color:#c300ff");
	
		inter.setOnAction(e-> {
			  rowSetting = 16;
			  colSetting = 16;
			  bombs = 40;
			  BackEnd.initializeBoard(16, 16,40);
			  gameScene(16,16);
			  window.setScene(game);
			  window.setTitle("MineSweeper");
			
		});
		
		Button expert = new Button("Expert");
		expert.setStyle("-fx-color:#9100ff");
		expert.setOnAction(e-> {
			  rowSetting = 16;
			  colSetting = 30;
			  bombs = 99;
			  
			  BackEnd.initializeBoard(16, 30,99);
			  gameScene(16,30);
			  window.setScene(game);
			  window.setTitle("MineSweeper");
			  
		});
		
		Button custom = new Button("Custom");
		custom.setStyle("-fx-color:#6e00ff");
		custom.setOnAction(e-> {
			try {
			  rowSetting = Integer.parseInt(row.getText());
			  colSetting = Integer.parseInt(col.getText());
			  bombs = Integer.parseInt(bb.getText());
			  BackEnd.initializeBoard(rowSetting, colSetting,bombs);
			  gameScene(rowSetting,colSetting);
			  window.setScene(game);
			  window.setTitle("MineSweeper");
			  
			}catch (Exception e1){
				window.setScene(gameSetting); //takes user back to gameSetting menu if the input is incorrect. 
			}
		});
		
		VBox bts = new VBox ();					//Holds the buttons for selection
		bts.setAlignment(Pos.BASELINE_LEFT);
		bts.setSpacing(2);
		bts.getChildren().addAll(beginner,inter,expert,custom);
		
		GridPane description = new GridPane();  //displays description of the board. 
		description.setVgap(8.5);
		description.setHgap(8);
		description.setPadding(new Insets(0,0,0,10));
		description.addRow(0,new Label("Height"),new Label("Width"),new Label("Bombs"));
		description.addRow(1,new Label("9"),new Label("9"),new Label("10"));
		description.addRow(2,new Label("16"),new Label("16"),new Label("40"));
		description.addRow(3,new Label("16"),new Label("30"),new Label("99"));
		description.addRow(4,row,col,bb);
		
		Text top = new Text("SETTINGS");
		top.getStyleClass().add("setting-font");
		
		Text bottom = new Text("For custom: fill in fields then press 'Custom' Button.");
		bottom.setFill(Color.WHITESMOKE);
		
		
		//Adjusts layout's positions and look 
		menu.setTop(top);
		menu.setAlignment(top, Pos.CENTER);	
		menu.setCenter(description);		
		menu.setLeft(bts);
		menu.setMargin(bts, new Insets(10,0,10,0));
		menu.setBottom(bottom);
		menu.setAlignment(bottom,Pos.TOP_LEFT);
		
		//Menu Scene
		gameSetting = new Scene(menu,490,200);
		gameSetting.getStylesheets().add("minesweeper/theme.css");
		
		
	}
	
	//Creates a default board of user specified # of Cells
	public static void gameScene(int row,int col) {
		
		Cell[][] cells = new Cell[row][col];	
		grid.setPadding(new Insets(5,5,5,5));
				
		for(int i= 0; i <cells.length;i++) {
			for(int j= 0; j<cells[0].length;j++) {
						cells[i][j] = new Cell(i,j); //each cell have unique row and col #
						grid.add(cells[i][j], j, i); // adds it to grid layout
			}
		}

		 game = new Scene(grid,600,600);
		 game.getStylesheets().add("minesweeper/theme.css");

		 
		 
		
	}
	
	//Displays if game is Won
	public static void youWinScene() {
		Label winLabel = new Label("You Win!");
		winLabel.setStyle("-fx-font-size :60 ");
		winLabel.setTextFill(Color.web("#f0ffff"));
		BorderPane winPane = new BorderPane();
		winPane.setCenter(grid);
		winPane.setBottom(winLabel);
		winPane.setAlignment(winLabel,Pos.CENTER);
		
		Scene winScene = new Scene(winPane,600,800);
		winScene.getStylesheets().add("minesweeper/theme.css");
		
		window.setScene(winScene);
		
	}
	
	//Displays if Game is lost. 
	public static void youLoseScene() {
		Label loseLabel = new Label("You Lose");
		loseLabel.setStyle("-fx-font-size :60 ");
		loseLabel.setTextFill(Color.web("#ff1493"));
		BorderPane losePane = new BorderPane();
		losePane.setCenter(grid);
		losePane.setBottom(loseLabel);
		losePane.setAlignment(loseLabel,Pos.CENTER);
		
		
		Scene loseScene = new Scene(losePane,600,800);
		loseScene.getStylesheets().add("minesweeper/theme.css");
		
		window.setScene(loseScene);
		
		
	}
}
