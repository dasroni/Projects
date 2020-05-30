


/**
 * This Public Java Class defines the complexity of a line of Code.
 * Notations used are type O(1), O(n) and O(log n)
 * to describe insturctions complexity  
 * 
 *  @author Roni Das
 *  Student ID: 108378223
 *  Date : 10/10/2018
 */

public class Complexity {
	
	/**
	 * private data field for Complexity object. 
	 */
	private int nPower;
	private int logPower;

	/**
	 * Public default constructor of Complexity
	 * initializes this object to O(1).
	 */
	public Complexity() {
		nPower = 0;
		logPower = 0 ;
	}
	/**
	 * Public default constructor of Complexity
	 * initializes this object to given O(n) & O(log n).
	 */
	public Complexity(int nP, int lP) {
		nPower = nP;
		logPower= lP;
		
	}
	
	/**
	 * This is a getter method for field member nPower
	 * @return
	 * nPower : power of O(n)
	 */
	public int getnPower() {
		return nPower;
	}
	
	
	/**
	 * This is a setter method for field member nPower
	 * @param nPower
	 * nPower: sets power of O(n)
	 */

	public void setnPower(int nPower) {
		this.nPower = nPower;
	}

	/**
	 * This is a getter method for field member logPower
	 * @return
	 * logPower : power of O(Log n)
	 */
	public int getLogPower() {
		return logPower;
	}

	/**
	 * This is a setter method for field member logPower
	 * @param logPower
	 * logPower: sets power of O(log n)
	 */
	public void setLogPower(int logPower) {
		this.logPower = logPower;
	}
	
	/**
	 * This method compares the complexity of two Complexity Objects
	 * based on the power of their field members
	 * @param object
	 * Takes a Complexity Object
	 * @return
	 * An integer indicating which is larger. 
	 */
	public int compareTo(Complexity object) {
		  
			if(nPower > object.getnPower())
				return 1;
			else if(nPower == object.getnPower() && logPower == object.getLogPower())
				 return 0;
			else if(logPower > object.getLogPower())
				 return 1;
			else 
				return -1;	
		
	}

	/**
	 * This method returns a String representation of Complexity Object. 
	 */
	public String toString() {
		
		String one = (nPower>1) ? "^"+nPower:"";
		String two = (logPower>1) ? "^"+logPower:"";
		
		if(nPower==0 && logPower==0)
			return "O(1)";
		else if(nPower==0)
			return "O(log(n)"+two+")";
		else if(logPower==0)
			return "O(n"+one+")";
		else 
			return "O( n"+one+" * "+"log(n)"+two+" )";
		
	}

}
