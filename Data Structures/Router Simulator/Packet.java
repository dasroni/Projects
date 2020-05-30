

/**
 * This Public Java Class represents a packet
 * consists of Size of packet, time arrived at dispatcher etc 
 * Each packet has an unique id
 * 
 *  @author Roni Das
 *  Student ID: 108378223
 *  Date : 10/23/2018
 */

public class Packet {

	private static int packetCount = 0;
	private int id;
	private int packetSize;
	private int timeArrive;
	private int timeToDest;
	
	/**
	 * This is a overloaded constructor of Packet
	 * @param packSize
	 * PackSize: Size of Packet in Bytes
	 * @param arrival
	 * Arrival: Time this packet was created
	 */
	Packet(int packSize,int arrival){
		this.id = ++packetCount;
		this.packetSize = packSize;
		this.timeArrive = arrival;
		this.timeToDest = packetSize/100;
	}
	/**
	 * This method decrement time to Destination
	 */
	public  void decTimeToDest() {
		 timeToDest--;
	}
	/**
	 * This static method is a getter for instance variable packetCount
	 * @return
	 * PacketCount: number of packets so far created
	 */
	public static int getPacketCount() {
		return packetCount;
	}

	/**
	 * This is a getter method for instance variable ID
	 * @return
	 * ID: unique ID for each packet 
	 */
	public int getId() {
		return id;
	}

	/**
	 * This is a getter method for instance variable Packet Size
	 * @return
	 * PacketSize: size of each Packet in Bytes
	 */
	public int getPacketSize() {
		return packetSize;
	}

	/**
	 * This is a getter method for instance variable Time Arrive
	 * @return
	 * TimeArrive: time this packet was created
	 */
	public int getTimeArrive() {
		return timeArrive;
	}

	/**
	 * This is a getter method for instance variable timeToDest
	 * @return
	 * timeToDest : how long will this packet take to get to destination router
	 */
	public int getTimeToDest() {
		return timeToDest;
	}

	/**
	 * This is a setter method for instance variable packetCount
	 * @param packetCount
	 * PacketCount:  number of packets so far created
	 */
	public static void setPacketCount(int packetCount) {
		Packet.packetCount = packetCount;
	}

	/**
	 * This is a setter method for instance variable ID
	 * @param id
	 * ID: unique ID for each packet
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * This is a setter method for instance variable packetSize
	 * @param packetSize
	 * PacketSize: size of each Packet in Bytes
	 */
	public void setPacketSize(int packetSize) {
		this.packetSize = packetSize;
	}

	/**
	 * This is a setter method for instance variable timeArrive
	 * @param timeArrive
	 * TimeArrive: time this packet was created
	 */
	public void setTimeArrive(int timeArrive) {
		this.timeArrive = timeArrive;
	}

	/**
	 * This is a setter method for instance variable timeToDest
	 * @param timeToDest
	 * timeToDest : how long will this packet take to get to destination router
	 */
	public void setTimeToDest(int timeToDest) {
		this.timeToDest = timeToDest;
	}


	/**
	 * This a String representation of Packet Object 
	 * @return
	 * String: String representation of Packet Object
	 */	
	public String toString() {
			
		return "["+id+", "+timeArrive+", "+timeToDest+"]";
	}
	
}
