

/**
 * This Public Java Class is a wrapper class of Complexity objects
 * This Class defines Complexity of a block of Codes.  
 * 
 *  @author Roni Das
 *  Student ID: 108378223
 *  Date : 10/10/2018
 */

public class CodeBlock {
	
	/**
	 * private data field for CodeBlock object. 
	 */
	private String name;
	private Complexity blockComplexity;
	private Complexity highestSubComplexity;
	private String loopVariable;
	
	/**
	 * static data field for CodeBlock object
	 */
	public static String[] BLOCK_TYPES = {"def","for","while","if","elif","else"};
	public static int DEF = 0;
	public static int FOR = 1;
	public static int WHILE = 2;
	public static int IF= 3;
	public static int ELIF = 4;
	public static int ELSE = 5;
	
	/**
	 * Public default constructor of CodeBlock
	 * doesn't initialize any field members.
	 */
	public CodeBlock() {}
	
	/**
	 * Public overloaded constructor of CodeBlock
	 * initialize any field members:
	 * -blockComplexity.
	 * -highestSubComplexity.
	 */
	public CodeBlock(Complexity block,Complexity highSub) {
		
		this.blockComplexity = block;
		this.highestSubComplexity = highSub;
		
	}
	
	/**
	 * This is a getter method for field member name.
	 * @return
	 * Name : represents order in stack. 
	 */
	public String getName() {
		return name;
	}
	/**
	 * This is a setter method for field member name.
	 * @param name
	 * name: nested order in string value
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * This is a getter method for field member BlockComplexity.
	 * @return
	 * BlockComplexity: a Complexity object. 
	 */
	public Complexity getBlockComplexity() {
		return blockComplexity;
	}
	/**
	 * This is a setter method for field member BlockComplexity.
	 * @param blockComplexity
	 * BlockComplexity : a Complexity Object
	 */
	public void setBlockComplexity(Complexity blockComplexity) {
		this.blockComplexity = blockComplexity;
	}
	/**
	 *  This is a getter method for field member HighestSubComplexity.
	 * @return
	 * HighestSubComplexity: returns a Complexity Object
	 */
	public Complexity getHighestSubComplexity() {
		return highestSubComplexity;
	}
	/**
	 * This is a setter method for field member HighestSubComplexity.
	 * @param highestSubComplexity
	 * highestSubComplexity: sets with a Complexity Object
	 */
	public void setHighestSubComplexity(Complexity highestSubComplexity) {
		this.highestSubComplexity = highestSubComplexity;
	}
	/**
	 * This is a getter method for field member LoopVariable.
	 * @return
	 * LoopVariable: returns loopVarible for while loops
	 */
	public String getLoopVariable() {
		return loopVariable;
	}
	/**
	 * This is a setter method for field member LoopVariable.
	 * @param loopVariable
	 * loopVariable: sets while loop variable 
	 */
	public void setLoopVariable(String loopVariable) {
		this.loopVariable = loopVariable;
	}

}
