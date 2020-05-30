
/**
 * This Public Java Class represents a tree of DirectoryNodes
 * Contains functions to create and manipulate Directory Tree.
 *  @author Roni Das
 *  Student ID: 108378223
 *  Date : 11/06/2018
 */


public class DirectoryTree {
	private DirectoryNode root;
	private DirectoryNode cursor;
	
	/**
	 * Default constructor initializes a root of tree 
	 */
	public DirectoryTree() {
		
		root = new DirectoryNode();
		root.setName("root");
		cursor = root;
		
	}
	/**
	 * This method reset the cursor back to root of the tree.
	 */
	public void resetCursor() {
		cursor = root;
		
	}
	
	/**
	 * This method changes cursor to next directory in line (children).
	 * @param name
	 * name: name of child directory
	 * @throws PointerException
	 * PointerException: an exception where Directory can not be found.
	 * @throws NotADirectoryException
	 * NotADirectoryException: an exception where a Node is not a Directory(Folder).
	 */
	public void changeDirectory(String name)throws PointerException,NotADirectoryException {
		int count = 0;
		
		for(int i=0;i<cursor.getChildren().length;i++) {
			if(cursor.getChildren()[i]!=null && cursor.getChildren()[i].getName().equals(name)) {
				if(cursor.getChildren()[i].isFile())
					throw new NotADirectoryException();
				else {
					cursor = cursor.getChildren()[i];
					break;
				}
			}
			count++;
		}
		if(count == 10)
			throw new PointerException();
		
	}
	
	/**
	 * This method determines the current working directory.
	 * @return
	 * String: path to working directory.
	 */
	public String presentWorkingDirectory() {
		
		DirectoryNode tempCursor = cursor;
		
		String workingDirectory ="";
		String tempString = "";
		

		while(tempCursor!=null) {
			
			tempString += tempCursor.getName()+" ";
			tempCursor = tempCursor.getParent();
		}
		
		String[] tokens = tempString.split(" ");
		
		for(int i=tokens.length-1;i>=0;i--) {
			
			if(i==0)
				workingDirectory += tokens[i];
			else 
				workingDirectory += tokens[i]+"/";
		}
	
		return workingDirectory;
	}
	
	/**
	 * This method lists the items under a certain directory.
	 * @return
	 * String representation of items in a directory.
	 */
	public String listDirectory() {
		
		String listFile ="";
		int count =0;
		
		for(int i=0;i<cursor.getChildren().length;i++) {
			if(cursor.getChildren()[i]!=null)
				listFile += cursor.getChildren()[i].getName()+"  ";
			else
				count++;
		}
		
		if(count == 10)
			return "'"+cursor.getName()+"'"+" Directory is Empty";
		else
			return listFile;
	}
	
	
	/**
	 * This method Prints the Directory Tree starting form cursor.
	 */
	public void printDirectoryTree() {
		
		preorderTraversal(cursor,0);
		
	}
	
	/**
	 * This helper method traverse through the Tree and prints its contents in preorder.
	 * @param cursor
	 * cursor: current working directory node.
	 * @param count
	 * count: integer for tabbing in recursion.
	 */
	public void preorderTraversal(DirectoryNode cursor,int count) {
		
		String spaces = "";
		for(int i=0; i<count;i++) {
			
			spaces +=" ";
		}
		
		if(cursor == null)
			return;
		
		if(!(cursor.isFile()))
			System.out.println(spaces+"|- "+cursor.getName());
		else
			System.out.println(spaces+"- "+cursor.getName());
		
		for(int i = 0 ; i<cursor.getChildren().length;i++) {
			preorderTraversal(cursor.getChildren()[i],count+3);
		}
		
		
	}

	/**
	 * This method makes a new Directory.
	 * @param name
	 * name : name of the directory.
	 * @throws FullDirectoryException
	 * FullDirectoryException: an exception where directory is already full.
	 * @throws IllegalArgumentException
	 * an exception where user input wrong command
	 * @throws NotADirectoryException
	 * NotADirectoryException: an exception where a Node is not a Directory(Folder).
	 */
	public void makeDirectory(String name) throws FullDirectoryException,IllegalArgumentException, NotADirectoryException {
		
		int count =0;
		if(name.contains(" ")||name.contains("/"))
			throw new IllegalArgumentException();
		else {
				
			for(int i=0;i<cursor.getChildren().length;i++) {
				if(cursor.getChildren()[i] == null) {
					cursor.addChild(new DirectoryNode());
					cursor.getChildren()[i].setName(name);
					cursor.getChildren()[i].setParent(cursor);
					cursor.getChildren()[i].getParent().setName(cursor.getName());
					break;
				}
				else
					count++;
				
			}
		}
		if(count == 10)
			throw new FullDirectoryException();
		
		
	}	
	
	/**
	 * This method makes a new file in tree.
	 * @param name
	 * name : name of the file.
	 * @throws FullDirectoryException
	 * FullDirectoryException: an exception where directory is already full.
	 * @throws IllegalArgumentException
	 * an exception where user input wrong command
	 */
	public void makeFile(String name)  throws FullDirectoryException,IllegalArgumentException {
		
		
		int count =0;
		if(name.contains(" ")||name.contains("/"))
			throw new IllegalArgumentException();
		else {
				
			for(int i=0;i<cursor.getChildren().length;i++) {
				if(cursor.getChildren()[i] == null) {
					cursor.setChild(i, new DirectoryNode());
					cursor.getChildren()[i].setName(name);
					cursor.getChildren()[i].setFile(true);
					cursor.getChildren()[i].setParent(cursor);
					cursor.getChildren()[i].getParent().setName(cursor.getName());
					break;
				}
				else
					count++;
				
			}
		}
		if(count == 10)
			throw new FullDirectoryException();
		
		
	}

	 /**
	  * this method Finds a node in Tree.
	  * @param name
	  * name: name of the node.
	  * @throws PointerException
	  * an exception where node can not be found
	  */
	public void findNode(String name) throws PointerException {
		DirectoryNode track = preTraversal(root,name);
		
		if(track!=null) {
			if(track.getName().equals(name)) {
					
			String workingDirectory ="";
			String tempString = "";
			

			while(track!=null) {
				
				tempString += track.getName()+" ";
				track = track.getParent();
			}
			
			String[] tokens = tempString.split(" ");
			
			for(int i=tokens.length-1;i>=0;i--) {
				
				if(i==0)
					workingDirectory += tokens[i];
				else 
					workingDirectory += tokens[i]+"/";
			}
		System.out.println(workingDirectory);
		}
		else throw new PointerException();
		}
	}
	/**
	 * This method trevers in tree to return the found node.
	 * @param cursor
	 * cursor: start at root.
	 * @param name
	 * name: name of the node.
	 * @return
	 * DirectoryNode
	 */
	public DirectoryNode preTraversal(DirectoryNode cursor,String name) {
		
	if(cursor!=null) {
		if(cursor.getName().equals(name))
			return cursor;
		else {
			for(int i=0; i < cursor.getChildren().length;i++) {
				DirectoryNode result = preTraversal(cursor.getChildren()[i],name);
				if(result != null) {
					return result;
				}
			}
			
		}
	}
		return null;
	}
	/**
	 * This method return cursor to its parents.
	 */
	public void MoveUpToParent() {
		
		if(cursor!=root)
			cursor = cursor.getParent();
		else
			System.out.println("Error: Already at Root Node.");
	}
	
	/**
	 * This method set cursor to a specific path.
	 * @param path
	 * path: path defined by user
	 * @throws NotAValidPathException
	 * an exception where given path is not valid
	 * @throws NotADirectoryException
	 * NotADirectoryException: an exception where a Node is not a Directory(Folder).
	 */
	public void moveCursor(String path) throws NotAValidPathException, NotADirectoryException {
		
		if(isValidPath(path)) {
			path.trim();
			String[] tokens = path.split("/");
			DirectoryNode temp = preTraversal(root,tokens[tokens.length-1]);
			
			if(temp!=null) {
				if(temp.isFile())
					throw new NotADirectoryException();
				else
					cursor = temp;
				
			}		
			
		}
		else 
			throw new NotAValidPathException();
	}
	
	/**
	 * This helper method determines if a path is valid or not.
	 * @param path
	 *  path: path defined by user
	 * @return
	 * boolean;
	 */
	public boolean isValidPath(String path) {
		
		boolean directoryInPath = false;
		path.trim();
		String[] tokens = path.split("/");
		
		int count =0;
		for(int i=0; i<tokens.length;i++) {
			DirectoryNode temp = preTraversal(root,tokens[i]);
			if(temp!=null)
				count++;
		}
		if(count == tokens.length)
			directoryInPath = true;
		
		
		return directoryInPath;
	}
	
	/**
	 * This method moves a file from soruce to destination.
	 * @param src
	 * src: source
	 * @param dst
	 * dst :destinatin.
	 * @throws FullDirectoryException
	 * FullDirectoryException: an exception where directory is already full.
	 */
	public void moveFileOrFolder(String src, String dst) throws FullDirectoryException {
		
		src.trim();
		dst.trim();
		
		String[] sourcePath = src.split("/");
		String[] desPath = dst.split("/");
		
		if(isValidPath(src) && isValidPath(dst)) {
			
			DirectoryNode source = preTraversal(root,sourcePath[sourcePath.length-1]);
			DirectoryNode destination = preTraversal(root,desPath[desPath.length-1]);
			
			if(source !=null && destination!=null) {
				
				int count =0;
					for(int i=0;i<destination.getChildren().length;i++) {
						if(destination.getChildren()[i] == null) {
							destination.setChild(i, source);
							destination.getChildren()[i].setParent(destination);
							destination.getChildren()[i].getParent().setName(destination.getName());
							
							break;
						}
						else
							count++;
						
					}
					
					source = null;
				
				if(count == 10)
					throw new FullDirectoryException();
			}
			
		}
		
		
	}
	
	
	
}
