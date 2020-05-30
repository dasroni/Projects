


/**
 * This Public Java Class represents a all Exceptions class needed
 * for BashTerminal 
 * 
 *  @author Roni Das
 *  Student ID: 108378223
 *  Date : 11/06/2018
 */
public class FullDirectoryException extends Exception{
	
	FullDirectoryException(){
		super("Present Directory is Full.");
	}
}

/**
 * This Java Class represents an exception where a Node is not a Directory(Folder). 
 * @author Roni Das
 *
 */
class NotADirectoryException extends Exception{
	
	NotADirectoryException(){
		super("Can not change directory into a file.");
	}
}
/**
 * This Java Class represents an exception where user input wrong command. 
 * @author Roni Das
 *
 */
class IllegalArgumentException extends Exception{
	
	IllegalArgumentException(){
		super("Arguments is not a valid Command or Format.Please try again.");
	}
}
/**
 * This Java Class represents an exception where Directory can not be found 
 * or cursor is out of scope of the directory. 
 * @author Roni Das
 *
 */
class PointerException extends Exception{
	
	PointerException(){
		
		super("Non-existantDirectory or Pointer is out of scope.");
	}
	
}
/**
 * This Java Class represents an exception a Path to destination from root is not found. 
 * @author Roni Das
 *
 */
class NotAValidPathException extends Exception{
	
	NotAValidPathException(){
		super("This path to directory is not Valid.");
	}
	
}