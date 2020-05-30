
/**
 * This Public Java Class is driver Class for both Complexity and CodBlock Objects
 * Using stack defined in Java library this class reads a Python file
 * and determines its code Complexity in O(n) notation. 
 * 
 *  @author Roni Das
 *  Student ID: 108378223
 *  Date : 10/10/2018
 */

import java.io.*;
import java.util.*;
import java.util.Stack;

public class PythonTracer {
    public static final int SPACE_COUNT = 4;
	
    /**
     * This is the main Method where program execution starts 
     * This method reads a python file containing codes 
     * and call an function to evaluate it's complexity
     * @param args
     * Command line arguments
     */
	public static void main(String[] args){
		
		Scanner input = new Scanner(System.in);
		System.out.print("Enter a file name(or 'quit' to quit): ");
		

		try {
	    String file = input.nextLine();	    
		if(!file.equals("quit") && !file.equals("Quit") ) {
			Complexity block = tracefile(file);
			System.out.println("\n\nOverall Complexity of "+file+" : "+block);         
			System.out.println("\nProgram terminating successfully...");
			}
		else
			System.out.println("Program terminating successfully...");
		}
		catch(FileNotFoundException e) {
			System.out.println("FileNotFoundException: File is not located in Project File..");
		
		}
		catch(Exception e) {
			
			System.out.println("\n"+e+"\nMake sure there is no spaces (' ') enterd in an empty line in Python Code");
		}
		

	}
	/**
	 * This Method takes in a Python file containing Code
	 * and evaluates the complexity of block codes resides within it
	 * using various function calls and logic. 
	 * @param file
	 * String file containing Python Code
	 * @return
	 * Complexity object: Overall Complexity of the code
	 * @throws FileNotFoundException
	 * FileNotFoundException: when file can not be located in disk by this program.  
	 */
	public static Complexity tracefile(String file) throws FileNotFoundException {
		
		Stack<CodeBlock> stack = new Stack<CodeBlock>();
		File fileObj = new File(file);
		Scanner input = new Scanner(fileObj);
		
		int nestedOrder = 0;
		int counter = 0;
		while(input.hasNextLine()) {
			String line = input.nextLine();
			String trimLine = line.trim();
			
			
			int indents = 0;
			
	
			if(line.length()!= 0 && trimLine.charAt(0)!='#') {
				
				indents = leadingSpaces(line)/SPACE_COUNT;
				
				while(indents < stack.size()) {
					 if(indents==0) {
						
						input.close(); 
						System.out.print("\nLeaving Block: ");
						return stack.peek().getBlockComplexity();
			            
					 }
					 else {
						 System.out.print("\nLeaving Block: ");
						 CodeBlock oldTop = (CodeBlock) stack.pop();
						 Complexity oldTopComplexity = addComplexity(oldTop);
						 System.out.print(oldTop.getName());           
						if(oldTopComplexity.compareTo(((CodeBlock) stack.peek()).getHighestSubComplexity())==1) {
							
							System.out.print(",updating block "+stack.peek().getName()+":");  
							  ((CodeBlock) stack.peek()).setHighestSubComplexity(oldTopComplexity);		
						     System.out.println("\nBlock "+stack.peek().getName()+":\tblock Complexity = "+stack.peek().getBlockComplexity()
		    		  			 + "        higest Sub-complexity = "+stack.peek().getHighestSubComplexity());
							 
						
						}				
						else {
							System.out.print(" , nothing to update");
						
					      System.out.println("\nBlock "+stack.peek().getName()+":\tblock Complexity = "+stack.peek().getBlockComplexity()
	    		  			 + "        higest Sub-complexity = "+stack.peek().getHighestSubComplexity());
					      
						}
					 }
				} 
				
				 String keyWord="";
				 if(isKeyWord(trimLine))	{
					 keyWord = keyWord(trimLine);
					 
						if(indents > nestedOrder)
							counter = 1;
						else 
							counter++;
						nestedOrder = indents;
					 
					 System.out.print("\nEntering Block ");
					 if(keyWord.equals("for")) {
				       	
						    CodeBlock variable;
						    
							if(trimLine.contains(" N:")) {
								Complexity order = new Complexity(1,0);
								variable = new CodeBlock(order,new Complexity());
							}
							
							else if(trimLine.contains(" log_N:")) {
								
								Complexity order = new Complexity(0,1);
								variable = new CodeBlock(order,new Complexity());
							    }
							else variable = new CodeBlock();
						 
						 variable.setName(stack.peek().getName()+"."+counter);
						 System.out.print(variable.getName()+" '"+CodeBlock.BLOCK_TYPES[CodeBlock.FOR]+"':");
						 
						 stack.push(variable);
						 
					      System.out.println("\nBlock "+variable.getName()+":\tblock Complexity = "+variable.getBlockComplexity()
	    		  			 + "        higest Sub-complexity = "+variable.getHighestSubComplexity());
					 }
					 else if(keyWord.equals("while")) {
						 
						
						 CodeBlock whileLoop = new CodeBlock(new Complexity(),new Complexity());
						 whileLoop.setLoopVariable(loopVariable(trimLine));
						 
						 whileLoop.setName(stack.peek().getName()+"."+counter);
						 System.out.print(whileLoop.getName()+" '"+CodeBlock.BLOCK_TYPES[CodeBlock.WHILE]+"':");
						 
						 stack.push(whileLoop);
						 
					      System.out.println("\nBlock "+whileLoop.getName()+":\tblock Complexity = "+whileLoop.getBlockComplexity()
	    		  			 + "        higest Sub-complexity = "+whileLoop.getHighestSubComplexity());
						 
					 }
					 else {
						      CodeBlock rest = new CodeBlock(new Complexity(),new Complexity()); 
						      
						      
						      if(keyWord.equals("def")) {
						    	  rest.setName("1");
						    	  System.out.print(rest.getName()+" '"+CodeBlock.BLOCK_TYPES[CodeBlock.DEF]+"':");
						      }
						      else
						    	  rest.setName(stack.peek().getName()+"."+counter);
						     
						      stack.push(rest); 
						      
						     if(keyWord.equals("if"))
						    	 System.out.print(rest.getName()+" '"+CodeBlock.BLOCK_TYPES[CodeBlock.IF]+"':");
						     if(keyWord.equals("else"))
						    	 System.out.print(rest.getName()+" '"+CodeBlock.BLOCK_TYPES[CodeBlock.ELSE]+"':");
						     if(keyWord.equals("elif"))
						    	 System.out.print(rest.getName()+" '"+CodeBlock.BLOCK_TYPES[CodeBlock.ELIF]+"':");
						     
						      
						      
						      
						      System.out.println("\nBlock "+rest.getName()+":\tblock Complexity = "+rest.getBlockComplexity()
						    		  			 + "        higest Sub-complexity = "+rest.getHighestSubComplexity());
					 }
					 
				 }
				 
				
				 else if(((CodeBlock) stack.peek()).BLOCK_TYPES[stack.size()].equals("while") &&
						 trimLine.contains(((CodeBlock) stack.peek()).getLoopVariable())) {
					   
					    System.out.print("\nFound a statement, updating block "+stack.peek().getName());
					 	String loopVar = ((CodeBlock) stack.peek()).getLoopVariable();
				            
					      if(loopVar.equals(loopVar+" -= 1"))
					    	  ((CodeBlock) stack.peek()).setBlockComplexity(new Complexity(1,0));
					      else
					    	  ((CodeBlock) stack.peek()).setBlockComplexity(new Complexity(0,1));
					 				 
					     System.out.println("\nBlock "+stack.peek().getName()+":\tblock Complexity = "+stack.peek().getBlockComplexity()
					     								+ "        higest Sub-complexity = "+stack.peek().getHighestSubComplexity());
				 }
	
				
			}

			
		}
		while(stack.size()>1) {
			 
			 CodeBlock oldTop = (CodeBlock) stack.pop();
			 Complexity oldTopComplexity = addComplexity(oldTop);
			 if(oldTopComplexity.compareTo(((CodeBlock) stack.peek()).getHighestSubComplexity())==1) {
				 System.out.print(",updating block "+stack.peek().getName()+":");  
				 ((CodeBlock) stack.peek()).setHighestSubComplexity(oldTopComplexity);
			     System.out.println("\nBlock "+stack.peek().getName()+":\tblock Complexity = "+stack.peek().getBlockComplexity()
    		  			 + "        higest Sub-complexity = "+stack.peek().getHighestSubComplexity());
			 }
				 
		}

		input.close();
		System.out.print("\nLeaving Block: "+stack.peek().getName()+"....");
		return addComplexity(stack.pop());
	}
	
	/**
	 * This method add the total complexity of a given block
	 * @param block
	 * CodeBlock object: blockComplexity & highestSubComplexity
	 * @return
	 * a new Complexity Object with proper values of nPower and logPower
	 */
	public static Complexity  addComplexity(CodeBlock block) {
		
		int n = 0;
		int l = 0;
		
		Complexity blockComplex = block.getBlockComplexity();
		Complexity highest = block.getHighestSubComplexity();
		
		n = blockComplex.getnPower()+ highest.getnPower();
		l = blockComplex.getLogPower() + highest.getLogPower();
		
		return new Complexity(n,l);
		
		
	}
	/**
	 * This method determines loop variable for of while loops
	 * @param line
	 * String line represents a line from Python Codes. 
	 * @return
	 * String loopVarible : control variable for while loops
	 */
	public  static String loopVariable(String line) {
		
		String loopVariable ="";
		String[] splitLine = line.split(" ");
		
		loopVariable = splitLine[1];
		
		return loopVariable;
	}
	/**
	 * This method determines if a line start with a Python key word
	 * @param line
	 * String line represents a line from Python Codes. 
	 * @return
	 * boolean flag: true if line contains a Python key word, false otherwise
	 */
	public static boolean isKeyWord(String line) {
		boolean flag = false;
		
		String[] splitLine = line.split(" ");
			
			if( splitLine[0].equals("for") ||splitLine[0].equals("while") || 
			    splitLine[0].equals("if") ||splitLine[0].equals("else") || 
			    splitLine[0].equals("elif") ||splitLine[0].equals("def"))
				flag = true;
			
		
		
		return flag;
	}
	/**
	 * This method determines the number of leading spaces before text begins
	 * @param line
	 * String line represents a line from Python Codes. 
	 * @return
	 * Int leadingSpaces: number of leading spaces before text begins.
	 */
	public static int leadingSpaces(String line) {
		
		int leadingSpaces = 0;
		for(int i= 0 ; i <line.length();i++) {
			if(line.charAt(i)!=' ')
				break;
			leadingSpaces++;
		}
		return leadingSpaces;
	}
	/**
	 * This method determines the Python instruction in a line
	 * @param line
	 * String line represents a line from Python Codes. 
	 * @return
	 * String keyWord : which keyword is it -> for, while...etc 
	 */

	public  static String keyWord(String line) {
		String keyWord ="";
		String[] splitLine = line.split(" ");
		
		keyWord = splitLine[0];
		
		
		return keyWord;
	}

}
