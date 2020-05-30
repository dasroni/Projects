



/**
 * This Public Java Class represents Index Comparator
 *  @author Roni Das
 *  Student ID: 108378223
 *  Date : 12/04/2018
 */

import java.util.Comparator;

public class IndexComparator implements Comparator<WebPage> {

	
	/**
	 * this method compares two int Index values of WebPages. 
	 */
	public int compare(WebPage arg0, WebPage arg1) {
		WebPage p1 = (WebPage) arg0;
		WebPage p2 = (WebPage) arg1;
		
		if(p1.getIndex() == p2.getIndex())
			return 0;
		else if(p1.getIndex() > p2.getIndex())
			return 1;
		else 
			return -1;
		
	
	}

}
