

/**
 * This Public Java Class represents a DirectoryNode
 * Each Node has 10 Children and a parent. 
 * 
 *  @author Roni Das
 *  Student ID: 108378223
 *  Date : 11/06/2018
 */

public class DirectoryNode {
	private String name;
	private DirectoryNode[] children = new DirectoryNode[10];
	private DirectoryNode parent;
	boolean isFile;
	
	/**
	 * Default Constructor of Directory Node.
	 */
	public DirectoryNode() {}	
	
	/**
	 * This getter method returns a pointer to 10 directory children
	 * @return
	 * Children: represents array of Directory Nodes
	 */
	public DirectoryNode[] getChildren() {
		return children;
	}
	/**
	 * This setter method sets instance variable Children
	 * @param children
	 * Children: represents array of Directory Nodes
	 */
	public void setChildren(DirectoryNode[] children) {
		this.children = children;
	}
	
	/**
	 * This setter method sets individuals nodes of array Children
	 * @param i
	 * i = index of array
	 * @param child
	 * Child = a Directory Node. 
	 */
	public void setChild(int i,DirectoryNode child) {
		children[i] = child;
	}
	

	
	
	
	/**
	 * This method adds a Child to array of Directory node from left-to-right
	 * @param node
	 * node : Directory Node.
	 * @throws NotADirectoryException
	 * NotADirectoryException : Exception where Node passed is not a Folder(directory)
	 * @throws FullDirectoryException
	 * FullDirectoryException : Exception where the Directory is already Full. no new node can't be added. 
	 */
	public void addChild(DirectoryNode node) throws NotADirectoryException,FullDirectoryException {
		int count =0;
		
		if(!(node.isFile)) {
		
			for(int i = 0; i<children.length;i++) {
				
				if(children[i]==null) {
					children[i] = node;
					break;
				}
				else
				count++;		
			}
			if(count == 10)
				throw new FullDirectoryException();
		}
		else
			throw new NotADirectoryException();
		
	}
	
	/**
	 * This is getter method for instance variable Name;
	 * @return
	 * Name : name of this Node.
	 */
	public String getName() {
		return name;
	}

	/**
	 * This is setter method for instance variable Name;
	 * @return
	 * Name : name of this Node.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * This is getter method for instance variable File;
	 * @return
	 * File : is this Node a file or Folder?
	 */
	public boolean isFile() {
		return isFile;
	}
	
	/**
	 * This is setter method for instance variable File;
	 * @return
	 * File : is this Node a file or Folder?
	 */
	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}
	/**
	 * This is getter method for instance variable parent of type DirectoryNode;
	 * @return
	 * parent = a Directory Node.
	 */
	public DirectoryNode getParent() {
		return parent;
	}
	/**
	 * This is setter method for instance variable parent of type DirectoryNode;
	 * @return
	 * parent = a Directory Node.
	 */
	public void setParent(DirectoryNode parent) {
		this.parent = parent;
	}

	
	
}


