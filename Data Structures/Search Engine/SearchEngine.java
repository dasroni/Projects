


/**
 * This Public Java Class represents generic Search Engine
 *  @author Roni Das
 *  Student ID: 108378223
 *  Date : 12/04/2018
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SearchEngine {
	public static final String PAGES_FILE = "pages.txt";
	public static final String LINKS_FILE = "links.txt";
	private static WebGraph web ;
	
	public static void main(String[] args) {
		
		System.out.println("Loading WebGraph Data...");
		web = WebGraph.buildFormFiles(PAGES_FILE, LINKS_FILE);
			
		Scanner input = new Scanner(System.in);
		String choice = "";
		
		do {
			menu();
			System.out.print("\nPlease select an option: ");
			choice = input.nextLine();
			processUserInput(choice.trim());
			
	
		} while(!(choice.equals("Q") || choice.equalsIgnoreCase("q")));

	}
	
	/**
	 * This method process User input
	 * @param choice
	 * User Choice
	 */
	public static void processUserInput(String choice) {
		Scanner input = new Scanner(System.in);
	try {	
		
		switch(choice) {
		case "ap":
		case "Ap":
		case "aP":
		case "AP":
			System.out.print("Enter a URL: ");
			String url = input.nextLine();
			System.out.print("Enter keywords(space_separated): ");
			String key = input.nextLine();
			String[] one = key.split(" ");
			web.addPages(url, one);
			break;
		case "rp":
		case "Rp":
		case "rP":
		case "RP":
			System.out.print("Enter a URL: ");
			String rem= input.nextLine();
			web.removePage(rem);
			break;
		case "al":
		case "Al":
		case "aL":
		case "AL":
				System.out.print("Enter a source URL: ");
				String source = input.nextLine();
				System.out.print("Enter a destination URL: ");
				String dest = input.nextLine();
				web.addLink(source.trim(), dest.trim());
			
			break;
		case "rl":
		case "Rl":
		case "rL":
		case "RL":
			System.out.print("Enter a source URL: ");
			source = input.nextLine();
			System.out.print("Enter a destination URL: ");
			dest = input.nextLine();
			web.removeLink(source, dest);
			break;
		case "p":
		case "P":
			
			String menu = "\n    (I) Sort based on index (ASC)\r\n" + 
					"    (U) Sort based on URL (ASC)\r\n" + 
					"    (R) Sort based on rank (DSC";
			
			System.out.println(menu);
			System.out.print("\nPlease select an option: ");
			String userInput = input.nextLine();
			String trimmed = userInput.trim();
			
			if(trimmed.length() !=1 )
						System.out.println("Invalid User Input, Please try again.");
			else {
				System.out.println();
				web.printTable();
				ArrayList<WebPage> myList = createArraylist(web);
				switch(trimmed.charAt(0)) {
				case 'r':
				case 'R':
					Collections.sort(myList, new RankComparator());
					printList(myList);
					System.out.println();
					break;
				case 'u':
				case 'U':
					Collections.sort(myList, new URLComparator());
					printList(myList);
					System.out.println();
					
					
					break;
				case 'i':
				case 'I':
						Collections.sort(myList, new IndexComparator());
						printList(myList);
						System.out.println();
					break;
				default:
					System.out.println("Invalid User Input, Please try again.");
					break;
				
				}
				
				
				
			}
			
			break;
		case "s":
		case "S":
			System.out.print("\nSearch KeyWord: ");
			String s = input.nextLine();
			web.searchKeyWords(s);
			System.out.println();
			break;
			
		case "q":
		case "Q":			
				System.out.println("\nGoodbye.");
			break;
		default:
			System.out.println("Invalid User Input, Please try again..");
			break;	
		
		}
	}
	catch(IllegalArgumentException e) {
		System.out.println("\n"+e);
		
	}catch(Exception e1) {
		System.out.println("\n"+e1);
		
	}
		
	}
	
	


	
	
	/**
	 * This methods print Table 
	 * @param myList
	 * myList : arrayList of WebPages
	 */
	private static void printList(ArrayList<WebPage> myList) {
		
		for(int i= 0; i<myList.size();i++) {
			
			System.out.println(myList.get(i));
		}
		
	}

	/**
	 * This method creats an ArrayList of Webpages
	 * @param web
	 * web = Web Graph
	 * @return
	 * ArrayList
	 */
	private static ArrayList<WebPage> createArraylist(WebGraph web) {
		
		ArrayList<WebPage> myList = new ArrayList<WebPage>();
		
		for(int i =0; i < web.getPages().length;i++) {
			
			if(web.getPages()[i]!=null)
				myList.add(web.getPages()[i]);
			
		}
		
		return myList;
	}

	/**
	 * This method print USer menu
	 */
	public static void menu() {
		
		String menu = "Menu:\r\n" + 
				"    (AP) - Add a new page to the graph.\r\n" + 
				"    (RP) - Remove a page from the graph.\r\n" + 
				"    (AL) - Add a link between pages in the graph.\r\n" + 
				"    (RL) - Remove a link between pages in the graph.\r\n" + 
				"    (P)  - Print the graph.\r\n" + 
				"    (S)  - Search for pages with a keyword.\r\n" + 
				"    (Q)  - Quit.";
		System.out.println("\n"+menu);
		
	}

}
