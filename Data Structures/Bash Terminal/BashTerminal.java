

/**
 * This Public Java Class represents a program terminal to
 * create and manipulate directories in computer.
 *  @author Roni Das
 *  Student ID: 108378223
 *  Date : 11/06/2018
 */

import java.util.Scanner;

public class BashTerminal {
	public static DirectoryTree tree = new DirectoryTree();
	
	/**
	 * This is Main method where program start execution
	 * @param args
	 * args : command line arguments.
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String user = "[RODAS@KnightHawk]: $ ";
		
		
		System.out.println("Starting bash terminal.");
		
		while(true) {
			
			System.out.print(user);
			String command = input.nextLine();
					command.trim();
				if(command.equals("exit"))
					break;
				else if(commandIsValid(command)) {
					
					
					processCommand(command);
				} 
				else {
					try {
					throw new IllegalArgumentException();
					}catch(IllegalArgumentException e) {
						System.out.println(e);
					}	
					
				}		
			
		}
		System.out.println("Program terminating successfully");
		

	}
	/**
	 * This static method process user input command for certain operation
	 * @param command
	 * command: String value of user input command.
	 */
	public static void processCommand(String command) {
		
		String[] tokens = command.split(" ");
		
		for(int i=0;i<tokens.length;i++) {		
			tokens[i].trim();
		}
		
	try {
		switch(tokens[0]) {
		case "pwd":
				System.out.println(tree.presentWorkingDirectory());
				
				break;
		case "ls":
				if(command.equals("ls -R")) {
					System.out.println();
					tree.printDirectoryTree();
					System.out.println();}
				else if(command.equals("ls"))
						System.out.println(tree.listDirectory());
				else throw new IllegalArgumentException();
				break;
		case "cd":
				if(command.equals("cd /"))
					tree.resetCursor();
				else if(command.equals("cd .."))
						tree.MoveUpToParent();
				else if(tokens[1].contains("/"))
					tree.moveCursor(tokens[1]);
				else
					tree.changeDirectory(tokens[1]);
				break;
		case "mkdir":
				tree.makeDirectory(tokens[1]);
				break;
		case "touch":
					tree.makeFile(tokens[1]);
				break;	
		case "find":
				tree.findNode(tokens[1]);
				break;
		case "mv":
				tree.moveFileOrFolder(tokens[1],tokens[2]);
				break;
		
		}
	}catch(FullDirectoryException e) {
		System.out.println(e);
		
	}catch(NotADirectoryException e1) {
		System.out.println(e1);
		
	}catch(IllegalArgumentException e2) {
		System.out.println(e2);
	}catch(PointerException e3) {
		System.out.println(e3);
	}catch(ArrayIndexOutOfBoundsException e4) {
		System.out.println("ERROR: please use format: mv {src} {dst} ");
	}catch(NotAValidPathException e5) {
		System.out.println(e5);
	}catch(NullPointerException e7) {
		System.out.println(e7+": Node can not be located in Tree.");
	}
	catch(Exception e6) {
		System.out.println(e6);
	}
		
	}
	
	/**
	 * This static method determines if a user input command is valid.
	 * @param command
	 * command: String value of user input command.
	 * @return
	 * boolean : true if command validates, false otherwise
	 */
	public static boolean commandIsValid(String command) {
		
		boolean evaluate  = false;
		command.trim();
		String[] tokens = command.split(" ");
		
		if(!(tokens.length >2)) {
			if(tokens[0].equals("pwd")||tokens[0].equals("ls")||command.equals("ls -R") ||command.equals("cd /") 
				||tokens[0].equals("mkdir")||tokens[0].equals("touch")||tokens[0].equals("cd")
				||tokens[0].equals("find") || command.equals("cd ..")) {
				
				evaluate = true;
			}
			
		}
		if(tokens[0].equals("mv") && tokens.length == 3)
				evaluate = true;
		
		
		return evaluate;
	}

}

