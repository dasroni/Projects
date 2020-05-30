

import java.util.Collection;

public class WebPage implements Comparable{
	private String url;
	private int index;
	private int rank;
	private String[] keywords;
	private String replace ="***";
	
	
	/**
	 * This getter method for instance variable URL
	 * @return
	 * String url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * This getter method for instance variable Index
	 * @return
	 * int Index of this Webpage
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * This getter method for instance variable Rank
	 * @return
	 * Int Rank of this Webpage among others
	 */
	public int getRank() {
		return rank;
	}
	/**
	 * This getter method for instance variable Keywords
	 * @return
	 * String[] of keywords in an WebPage
	 */
	public String[] getKeywords() {
		return keywords;
	}
	
	/**
	 * This setter method for instance variable URL
	 * @param url
	 * url of this webpage
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * This setter method for instance variable Index
	 * @param index
	 * index of this webpage
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 *  This setter method for instance variable rank
	 * @param rank
	 * rank of this webpage
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}
	/**
	 *  This setter method for instance variable keywords
	 * @param keywords
	 * keywordsof this webpage
	 */
	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}
	/**
	 * This getter method for instance variable replace
	 * @return
	 * String replace needs to add links 
	 */
	public String getReplace() {
		return replace;
	}
	
	/**
	 *  This setter method for instance variable rplace
	 * @param replace
	 * rank of this webpage
	 */
	public void setReplace(String replace) {
		this.replace = replace;
	}
	
	/**
	 * default const
	 */
	public WebPage() {}
	/**
	 * Overloaded constructor
	 * @param url_C
	 * url
	 * @param tokens
	 * keywords
	 */
	public WebPage(String url_C, String[] tokens) {
		url = url_C;
		keywords = new String[tokens.length];
		
		for(int i=0; i<keywords.length;i++) {
			keywords[i] = (i==keywords.length-1)? tokens[i]:tokens[i]+", ";		
		}
			
	}
	/**
	 * This is String represntation of this Object
	 */
	public String toString() {
		
		String one = String.format("%2s      |   %-24s   |     %-3s     |   %-22s|  ", index+"",url,rank+"",replace);
		
		for(int i=0 ; i<keywords.length;i++) {
			
			one+=keywords[i];
			
		}
		
		return one;
	}
	@Override
	/**
	 * This compare to Method compare two Webpage objects
	 */
	public int compareTo(Object arg0) {
		WebPage p1 = (WebPage) arg0;
		if(this.rank == p1.rank)
			return 0;
		else if(this.rank > p1.rank)
			return 1;
		else 
			return -1;
		
		
	}

}
