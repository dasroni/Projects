

/**
 * This Public Java Class represents a Router
 * In this class a network Router is modeled 
 * This class implements Queue system to process data
 * Queue is implemented using an array of Packet 
 * No Java API was used for Queue
 * 
 *  @author Roni Das
 *  Student ID: 108378223
 *  Date : 10/23/2018
 */


public class Router {
	Packet[] queue;
	
	int bufferSize;
	
	/**
	 * This is a overloaded constructor of Router
	 * @param buffSize
	 * buffSize : Takes in buffer size of the router
	 */
	public Router(int buffSize) {
		bufferSize = buffSize;
		queue = new Packet[bufferSize];
	}
	/**
	 * This is a default constructor of Router Class
	 * initialize instance variable for a "Dispatch" Router 
	 */
	public Router() {
		bufferSize = 3;
		queue = new Packet[bufferSize];
	}
	
	/**
	 * This method adds a Packet to end of the array
	 * @param p
	 * P: Packet Object
	 * @throws Exception
	 * Exception : Array is already full. 
	 */
	public void enqueue(Packet p) throws Exception {
		
		if(queue[bufferSize-1] != null) {
			
			throw new Exception("Network is congested. Packet "+p.getId()+" is dropped");
			
		}
		else
			for(int i = 0 ; i < bufferSize;i++) {
				if(queue[i] == null) {
				   queue[i] = p; 
				   break;
			}
		}
		
	}
	
	/**
	 * This method removes a Packet from front of the Array/Line
	 * @return
	 * Packet object front of the line
	 * @throws Exception
	 * Exception : if Array is empty (Empty Queue)
	 */
	public Packet dequeue() throws Exception {
		if(queue[0]!= null) {
			return poll();
		}
		else
			throw new Exception("Empty Queue!");
	}
	
	public Packet poll() {
		
		Packet temp = queue[0];
		
		for(int i = 0 ; i < bufferSize-1;i++) {
			queue[i] = queue[i+1]; 
			
		}
		queue[bufferSize-1] = null;
		
		return temp;
		
	}	
	
	
	/**
	 * This is a getter method for instance variable queue. 
	 * @return
	 * pointer to an Array of Packet Objects
	 */
	public Packet[] getQueue() {
		return queue;
	}
	/**
	 * This is a getter method of instance variable bufferSize
	 * @return
	 * bufferSize: size of each router 
	 */
	public int getBufferSize() {
		return bufferSize;
	}
	/**
	 * This is a setter method of instance variable queue
	 * @param queue
	 * queus: an array of packet objects
	 */
	public void setQueue(Packet[] queue) {
		this.queue = queue;
	}
	/**
	 * This is a setter method of instance variable bufferSize
	 * @param bufferSize
	 * bufferSize: size of each router
	 */
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	/**
	 * This method performs "poll" from Queue
	 * @return
	 * a pointer to Packet Object from front of the line
	 */
	public Packet peek() {
		return queue[0];
	}
	/**
	 * This method determines the size of Queue
	 * @return
	 * size : represent how many elements are currently in array
	 */
	public int size() {
		
		int currentSize = 0;
		for(int i =0 ; i <queue.length;i++) {
			if(queue[i] != null)
				currentSize++;
		}
		
		return currentSize;
	}
	/**
	 * This method determines if a Router is Empty or not
	 * @return
	 * boolean 'true' if queue is empty 'false' otherwise
	 */
	public boolean isEmpty() {
		boolean empty = true;
		
		for(int i = 0 ; i <bufferSize;i++) {
			if(queue[i]!=null)
				empty = false;
			
		}
		
		
		return empty;
	}
	/**
	 * This a String representation of Router Object
	 * @return
	 * String:String representation of Router Object
	 */
	public String toString() {
		
		
		String rotuerBufferElements = "";
		
		for(int i= 0 ; i < bufferSize; i++) {
			if(queue[i]!=null) {
				if(i==0) {
					rotuerBufferElements += queue[i];
				}
			else
			rotuerBufferElements += ","+queue[i];}
		}
	
		
		return "{"+rotuerBufferElements+"}";
	}
	
	/**
	 * This methood determines if a Packet from Intermediate Router is ready
	 * for destination router. If it is ready, packets queue up in another Router before destination
	 * This ensure "fairness" of packets routing 
	 * @param intRouters
	 * intRouters: Intermediate routers 
	 * @param tempQue
	 * tempQue : temporary router to hold ready packets
	 * @throws Exception
	 * Exception: from 'enqueue' method, if array is already full
	 */
	public static void packetReady(Router[] intRouters,Router tempQue) throws Exception  {
		
		
		for(int i = 0; i < intRouters.length;i++) {
			
			if(intRouters[i].getQueue()[0]!=null) {
		        	if(intRouters[i].getQueue()[0].getTimeToDest() == 0) {
						tempQue.enqueue(intRouters[i].getQueue()[0]);
				}
			}	   
		
		}		

		
	}
	/**
	 * This method process packets from temp Router to final destination router
	 * Also matches Packets form temp Router with Intermediate router to 
	 * determine which elements needs to be removed from interRouters 
	 * @param intRouters
	 * intRouters: Intermediate routers
	 * @param tempQue
	 * tempQue : temporary router to hold ready packets
	 * @param time
	 * Time : Time the data is being processed 
	 * @return
	 * Service Time: service time of Each Packet
	 * @throws Exception
	 * Exception : from 'deQueue' method, if Queue is empty
	 */
	
	public static int processToDes(Router[] intRouters,Router tempQue,int time) throws Exception {
		
		int index = 0;
		int serviceTime =0;
		for(int i = 0; i < intRouters.length;i++) {
			
			if(intRouters[i].getQueue()[0]!=null && tempQue.getQueue()[0]!= null) {
				if(tempQue.getQueue()[0].getId() == intRouters[i].getQueue()[0].getId()) {
						         index = i;
				}			   
			}
		}
		if(tempQue.getQueue()[0]!= null) {
		serviceTime = (time)-intRouters[index].getQueue()[0].getTimeArrive();
		tempQue.dequeue();
		System.out.println("Packet "+intRouters[index].getQueue()[0].getId()+" has successfully reached its destination: +"+serviceTime);
		intRouters[index].dequeue();
		Simulator.totalPacketsArrived++;
		}
		
		return serviceTime;
	}
	
	/**
	 * This method determines which Intermediate Router Packets are sent to from a dispatch Router
	 * @param intRouters
	 * intRouters: Intermediate routers
	 * @return
	 * Index of the Intermediate router
	 * @throws Exception
	 * Exception : if the Buffer Size is already Full
	 */
	public static int sendPacketTo(Router[] intRouters) throws Exception {
		int currentMin = intRouters[0].size();
		int index = 0;
		
		for(int i = 0; i < intRouters.length;i++) {
			
			if(intRouters[i].size() < currentMin) {
						index = i;
						currentMin = intRouters[i].size();
			}
				   
		
		}
		
			return index;
	}

}
