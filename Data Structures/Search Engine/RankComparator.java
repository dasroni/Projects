


/**
 * This Public Java Class represents Rank Comparator
 *  @author Roni Das
 *  Student ID: 108378223
 *  Date : 12/04/2018
 */


import java.util.Comparator;


/**
 * this method compares two int Rank values. 
 */
public class RankComparator implements Comparator<WebPage>  {

	public int compare(WebPage arg0, WebPage arg1) {
		WebPage p1 = (WebPage) arg0;
		WebPage p2 = (WebPage) arg1;
		
		if(p1.getRank() == p2.getRank())
			return 0;
		else if(p1.getRank() > p2.getRank())
			return -1;
		else 
			return 1;
		
	
	}

}
