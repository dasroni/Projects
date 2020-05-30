

/**
 * This Public Java Class represents URL Comparator
 *  @author Roni Das
 *  Student ID: 108378223
 *  Date : 12/04/2018
 */


import java.util.Comparator;

public class URLComparator implements Comparator<WebPage> {

	/**
	 * this method compares lexicographically two string values. 
	 */
	public int compare(WebPage arg0, WebPage arg1) {
		WebPage p1 = (WebPage) arg0;
		WebPage p2 = (WebPage) arg1;
		
		return (p1.getUrl().compareTo(p2.getUrl()));
		
	
	}
}
