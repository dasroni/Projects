
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class WebGraph {

	public static final int MAX_PAGES = 40;
	private WebPage[] pages = new WebPage[MAX_PAGES];
	public WebPage[] getPages() {
		return pages;
	}
	private int[][] edges = new int[MAX_PAGES][MAX_PAGES];
	/**
	 * Default constructor
	 */
	public WebGraph() {}
	
	/**
	 * This method Builds webgraph from two input txt file
	 * @param pagesFiles
	 * page file
	 * @param linksFile
	 * link file
	 * @return
	 * webgraph
	 * @throws IllegalArgumentException
	 * IllegalArgumentException
	 */
	public static WebGraph buildFormFiles(String pagesFiles, String linksFile) throws IllegalArgumentException {
		
		WebGraph web = new WebGraph();
		
		try {
		    
		     FileInputStream	file_1 = new FileInputStream(pagesFiles);
			 InputStreamReader inStream = new InputStreamReader(file_1);
			 BufferedReader readerPages = new BufferedReader(inStream);
			 
		     FileInputStream	file_2 = new FileInputStream(linksFile);
			 InputStreamReader inStream_2 = new InputStreamReader(file_2);
			 BufferedReader readerLinks = new BufferedReader(inStream_2);
			 
			 
			 int count = 0;
			 while(true) {
				 
				 String data = readerPages.readLine();	
				 if(data == null)
					 	break;	
				 
				 
				 if(!(isCorrectFormat(data,0))) {
					 web = null;
					 throw new IllegalArgumentException(); 
				 }
					 
				 else {
					 
					
					 String[] tokens = data.split(" ");
					 String[] keywords = new String[tokens.length-1];
					
					 for(int i= 0; i <keywords.length;i++) {
						 keywords[i] = tokens[i+1];
					 }
					 
					 WebPage temp = new WebPage(tokens[0],keywords);
					 web.setWebPages(count++, temp);
					 
					
					 				 
				 }
					 		 
					 		
			 }
			 
			 while(true) {
				 
				 String data = readerLinks.readLine();	 
				 if(data == null)
					 	break;	
				 
				 if(!(isCorrectFormat(data,1))) {
					 web = null;
					 throw new IllegalArgumentException(); 
				 }				 
				 else {
					 
					 String[] tokens = data.split(" ");
					 
					 if(containsWebsite(web,tokens[0]) && containsWebsite(web,tokens[1])) {
						int row = position(web,tokens[0]);
						int col = position(web,tokens[1]);
						
						web.edges[row][col] = 1;
						
						 
					 }
					 				 
				 }	 
				
			 }
			 
			 
			 for(int i = 0; i <web.pages.length;i++) {
				 			 
				 for(int j = 0; j <web.pages.length;j++) {
					 	if(web.edges[i][j] == 1)
					 		  continue;
					 	else
					 		web.edges[i][j] = 0;					 
					 	
				 }	 
				 
			 }
			 
			 web.updatePageRanks();
			 System.out.println("Success!");
			 
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File Can not be found in System.");
		} catch (IOException e1) {
			throw new IllegalArgumentException("IOException: File can not be read.");
		} catch (IllegalArgumentException e2) {
			throw new IllegalArgumentException("File's content is not in correct Format!");
			
		}

		
		
		return web;
		
	}

	/**
	 * This method finds position of an webpage
	 * @param web
	 * @param url
	 * @return
	 */
	private static int position(WebGraph web, String url) {
		
		int position = 0;
		for(int i = 0; i < web.pages.length;i++) {
			if(web.pages[i].getUrl().equals(url)) {
				position = i;
				break;
			}
			
		}
		
		return position;
	}
	
	/**
	 * This method finds position of an webpage
	 * @param url
	 * @return
	 */
	private  int position(String url) {
		
		int position = 0;
		for(int i = 0; i < pages.length;i++) {
			if(pages[i] != null && pages[i].getUrl().equals(url)) {
				position = i;
				break;
			}
			
		}
		
		return position;
	}
	
	/**
	 * This method determines if an URL is Valid or not
	 * @param web
	 * @param url
	 * @return
	 */
	private static boolean containsWebsite(WebGraph web, String url) {
		boolean isWebsiteAvailable = false;
		
		for(int i = 0; i < web.pages.length;i++) {
			if(web.pages[i].getUrl().equals(url)) {
				isWebsiteAvailable = true;
				break;
			}
			
		}
		
		
		return isWebsiteAvailable;
		
	}
	/**
	 * This method determines if an URL is Valid or not
	 * @param url
	 * @return
	 */
	private boolean containsWebsite(String url) {
		boolean isWebsiteAvailable = false;
		
		for(int i = 0; i < pages.length;i++) {
			if(pages[i] != null && pages[i].getUrl().equals(url)) {
				isWebsiteAvailable = true;
				break;
			}
			
		}
		
		
		return isWebsiteAvailable;
		
	}
	/**
	 * This method determines if format in txt if correct or not
	 * @param data
	 * @param code
	 * @return
	 */
	private static boolean isCorrectFormat(String data, int code) {
		
		boolean format = true;
		String[] tokens;
		if(code == 0) {
			tokens = data.split(" ");
			
			if(isLink(tokens[0])) {		
				for(int i=1; i<tokens.length;i++) {
					if(tokens[i].contains(" ") || tokens[i].contains(".")) {				
						format = false;
						break;
					}				
				}
		    }
			else
				format = false;
			
			
		}else {
			
			tokens = data.split(" ");
			
			if(tokens.length != 2 || !(isLink(tokens[0])) || !(isLink(tokens[1])) ) {
					format = false;
			}	
		}
		
		
		return format;
	}
	
	
	/**
	 * This method determines if is Link or not
	 * @param link
	 * @return
	 */
	private static boolean isLink(String link) {
		boolean isLink = true;
		
		int count = 0;
		for(int i=0;i<link.length();i++) {
			
			if(!(Character.isLetterOrDigit(link.charAt(i))) || link.charAt(i) == '.') {		
				count++;
			}		
		}
		
		if(count!=1)
			isLink = false;
		
		
		return isLink;
	}
	
	/**
	 * This method set Webpages at certain index
	 * @param position
	 * @param web
	 */
	public void setWebPages(int position, WebPage web) {
		
		pages[position] = web;
		
	}
	/**
	 * This method add pages to array of webpages
	 * @param url
	 * @param keywords
	 * @throws IllegalArgumentException
	 */
	public void addPages(String url, String[] keywords) throws IllegalArgumentException {
		
			if(url!=null && keywords!=null) {
				
				boolean isWebsiteAvailable = false;
				int count = 0;
				
				for(int i = 0; i < pages.length;i++) {				
					
					if(pages[i] != null && pages[i].getUrl().equals(url)) {
						isWebsiteAvailable = true;
						break;
					}
					
					if(pages[i] != null)
							count++;
					
					
				}
				
	
				
				if(isWebsiteAvailable)
					throw new IllegalArgumentException("URL is not unique, already exits in the graph");
				else {
					
					if(isLink(url)) {
						WebPage temp = new WebPage(url,keywords);
						pages[count] = temp;
						updatePageRanks();
						
						System.out.println("\n"+url+" sucessfully added to the WebGraph!");
					}
					else
						System.out.println("Enter a Valid URL: Throw Exception");
								
				}
					
				
				
			}
			else
				throw new IllegalArgumentException("Arguments are NULL");
		
		
	}
	/**
	 * This method add Link between websites
	 * @param source
	 * @param destination
	 * @throws IllegalArgumentException
	 */
	public void addLink(String source, String destination) throws IllegalArgumentException{
		
		if(source!=null && destination!=null) {
			if(isLink(source) && isLink(destination)) {
				
				if(containsWebsite(source) && containsWebsite(destination)) {
					int row = position(source);
					int col = position(destination);
					
					edges[row][col] = 1;
					
					System.out.println("\nLink sucessfully added from "+source+" to "+destination);
					updatePageRanks();
					
				}
				else
					throw new IllegalArgumentException("URLs can not be located in Graph.");		
				
			}
			else
				throw new IllegalArgumentException("Please enter a Valid URL");
					
		}
		else {
			
			throw new IllegalArgumentException("Links contains NULL value reference.");
		}
		
	}
	
	/**
	 * This method removes a Webpage
	 * @param url
	 */
	public void removePage(String url) {
		
		if(url!=null && containsWebsite(url)) {
			
			int row = position(url);
			int count = 0;
			for(int i=0; i<pages.length-1;i++) {
						
				if( i >= row)
					pages[i] = pages[i+1];	
				
				count++;
				
			}
			
			pages[count-1] = pages[count];
		
			
			for(int i=0; i<edges.length-1;i++) {
				
				if(i >= row) {
				for(int j=0;j<edges.length-1;j++) {
						
					edges[i][j] = edges[i+1][j];
	
					
					}
				}
			}
			
			
			for(int i=0; i<edges.length-1;i++) {
				
				if(i >= row) {
				for(int j=0;j<edges.length-1;j++) {	
					edges[j][i] = edges[j][i+1];
					
					}
				}
			}
			
			
			
			System.out.println(url+" Link sucessfully removed from the graph");

			
			
		}
		
		updatePageRanks();
		
	}
	/**
	 * This method removes link between WebPages
	 * @param source
	 * @param destination
	 */
	public void removeLink(String source, String destination) {
		
		if(source!=null && destination!=null) {
			
			if(containsWebsite(source) && containsWebsite(destination)) {
				int row = position(source);
				int col = position(destination);
				
				edges[row][col] = 0;
				System.out.println("\nLink sucessfully removed");
						
			}
			
			
		}
		updatePageRanks();
		
	}
	/**
	 * This methods Upates Ranks of WebPAges
	 */
	public void updatePageRanks() {
		

		
		
		int[] colSum = new int[edges.length];
		for(int i=0;i <edges.length;i++) {
			for(int j= 0; j<edges.length;j++) {
				
				colSum[j] += edges[i][j];
						
			}		
			
		}
		
		for(int i = 0; i < pages.length;i++) {
			
			
			if(pages[i]!=null) {
				pages[i].setRank(colSum[i]);
				pages[i].setIndex(i);}
				
		}
		
		
		
		
	}
	/**
	 * This method formats String for printing 
	 */
	public void printTable() {
		
		
		System.out.printf("%-12s%-30s%-15s%-25s%s","Index","URL","PageRank","Links","Keywords");
		System.out.println();
		
		for(int i=0;i<140;i++) {
			System.out.print("-");
		}
		
		System.out.println();
	
		String[] links = new String[pages.length];
		for(int i=0; i<edges.length;i++) {
			
			if(pages[i]!=null) {
				links[i] = "";
				
			for(int j=0;j<edges.length;j++) {
					if(edges[i][j] == 1)
						links[i] += j+", ";
				
					}
			
				if(links[i].length()!=0)
					links[i] = links[i].substring(0, links[i].length()-2);
			}
		}
		
		 for(int i = 0; i <pages.length;i++) {
			 
			 if(pages[i]!=null) {
				 pages[i].setReplace(links[i]);
				 String data = pages[i].toString();
				 formatData(data.replace("***", links[i]));
				//System.out.println(formatData(data.replace("***", links[i])));
			 }
			 	 		 
		 }
		
		
	}
	
	/**
	 * This is a getter method
	 * @return
	 */
	public int[][] getEdges() {
		return edges;
	}
	
	
	/**
	 * This method formats String 
	 * @param replace
	 * String
	 * @return
	 * String in format
	 */
	private String formatData(String replace) {
		
		int index = replace.lastIndexOf("|");
		
		int count = 1;
		for(int i= index-1 ; i >0;i--) {
			
			if(replace.charAt(i) !=' ')
				break;
			else
			 count++;
			
		}
		
		String format= replace.substring(0, index-count+1);
		
		String one = String.format("%-76s   %s", format, replace.substring(index));
				 
		return one;
	}
	
	/**
	 * THis methiod finds keywords
	 * @param keywords
	 * keywords
	 */
	public void searchKeyWords(String keywords) {
		
		WebPage[] rankedPages = new WebPage[MAX_PAGES ];
		WebPage[] ranked = new WebPage[MAX_PAGES ];
		
		for(int k =0 ; k<rankedPages.length;k++) {
			
			if(pages[k]!=null) {
				
				rankedPages[k] = pages[k];
			}
			
		}
		
		
		
		int len= pages.length;
		for(int i = 0 ; i < len-1;i++) {
			for(int j=1; j< len-i;j++) {
				if(rankedPages[j]!= null && rankedPages[j-1].getRank()<rankedPages[j].getRank() ) {
					
					WebPage temp = rankedPages[j-1];
					rankedPages[j-1] = rankedPages[j];
					rankedPages[j] = temp;
									
				}
						
			}
					
		}
		
		
		
		int f =0;
		for(int k =0 ; k<rankedPages.length;k++) {
			
			if(rankedPages[k]!=null) {
				
				String[] str = rankedPages[k].getKeywords();
				
				for(int i =0 ; i < str.length;i++) {
					if(str[i].equalsIgnoreCase(keywords+", ") || str[i].equalsIgnoreCase(keywords)) {
						ranked[f++] = rankedPages[k];
						
					}
					
				}
				
			}
			
		}
		
		if(ranked[0] == null) {
			System.out.println("\n Search results returned empty!");
			
		}
		
		else {
		System.out.println();
		System.out.println("Rank\tPageRank\tURL");
		System.out.print("-----------------------------------------------------");
		
		for(int i =0 ; i <ranked.length;i++) {
			
			if(ranked[i]!=null) {
				
				System.out.print("\n  "+(i+1)+"   |    "+ranked[i].getRank()+"    |     "+ranked[i].getUrl());
			}
			
			}
		}
		
		
		
	}
	
}



