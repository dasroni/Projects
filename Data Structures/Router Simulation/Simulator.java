

import java.util.Scanner;
/**
 * This Public Java Class is driver Class for Router and Packet Class
 * Using Queue definitions a simple Router simulation of how 
 * packets get processed is carried out in this program
 * 
 *  @author Roni Das
 *  Student ID: 108378223
 *  Date : 10/23/2018
 */

public class Simulator {
	

	private static Router dispatcher = new Router();
	private static Router[] routers;
	private static Router tempQue;
	private static int totalServiceTime;
	public static int totalPacketsArrived;
	private static int packetsDropped = 0;
	private static double arrivalProb;
	private static int numIntRouters;
	private static int maxBufferSize;
	private static int minPacketSize;
	private static int maxPacketSize;
	private static int bandwidth;
	private static int duration;
	public static final int MAX_PACKETS = 3;
	
	/**
	 * The Main method where program starts execution 
	 * @param args
	 * Command line argument
	 */
	public static void main(String[] args) {	
		
		
		Scanner input = new Scanner(System.in);
		char choice = 'y';
		
	do {
		
		try {
		double averageTime =0;
		initializeInstanceVariable();
		System.out.println("\nStarting simulator...");
		System.out.print("Enter the number of Intermediate routers: ");
		numIntRouters = input.nextInt();
		routers = new Router[numIntRouters];
		System.out.print("Enter the arrival probability of a packet: ");
		arrivalProb = input.nextDouble();
		System.out.print("Enter the maximum buffer size of a router: ");
		maxBufferSize = input.nextInt();
		tempQue = new Router(maxBufferSize);
		initializeIntRouterBufferSize(maxBufferSize);
		System.out.print("Enter the minimum size of a packet: ");
		minPacketSize = input.nextInt();
		System.out.print("Enter the maximum size of a packet: ");
		maxPacketSize = input.nextInt();
		System.out.print("Enter the bandwidth size: ");
		bandwidth = input.nextInt();
		System.out.print("Enter the simulation duration: ");
		duration = input.nextInt();
	

		averageTime = simulate(duration);
		
		System.out.println("\nSimulation ending...");
		System.out.println("Totla service Time: "+totalServiceTime);
		System.out.println("Total Packets served: "+totalPacketsArrived );
		System.out.printf("Average time per packet: %.2f",averageTime);
		System.out.println("\nTotal packet dropped: "+packetsDropped);
		
		System.out.print("Do you want to try another simulation? (y/n) :");
		input.nextLine();
		choice = input.nextLine().charAt(0);
				}catch(Exception e) {
					System.out.println(e);
			}
		
		}while (choice =='y' || choice =='Y');
		
		System.out.println("\nProgam terminating successfully...");
	

	}
	/**
	 * This method initializes global variables for a new simulation period
	 */
	private static void initializeInstanceVariable() {
		 dispatcher = new Router();
		 totalServiceTime = 0;
		 totalPacketsArrived= 0;
		 packetsDropped = 0;
		
	}

	/**
	 * This is a helper method to initialize the Intermediate Routers
	 * @param maxBufferSize2
	 * maxBufferSize2: user inputed buffer size of routers
	 */
	private static void initializeIntRouterBufferSize(int maxBufferSize2) {
		for(int i= 0 ; i < numIntRouters;i++ ) {
			routers[i] = new Router(maxBufferSize);
		}
		
	}
	/**
	 * This method is a simple Packet routing simulator in Network
	 * @param simulationPeriod
	 * simulationPeriod : user inputed simulation time period
	 * @return
	 * Average service time. 
	 */
	public static double simulate(int simulationPeriod) {
		
		int time = 0;
		while(time < simulationPeriod) {
			
			System.out.println("\nTime: "+(time+1));
			
			for(int i = 0; i < MAX_PACKETS;i++) {
				 if(Math.random() < arrivalProb) {
					Packet toDispatchRouter = new Packet(randInt(minPacketSize,maxPacketSize),time);
					try {
						dispatcher.enqueue(toDispatchRouter);
					} catch (Exception e) {
						
						System.out.println(e);
					}
					System.out.println("Packet "+toDispatchRouter.getId()+" arrives at dispatcher with size "+toDispatchRouter.getPacketSize());
				 }
				
			}
			if(dispatcher.isEmpty()) {
				System.out.println("No Packets arrived");
			}
			
		 	
			while(!(dispatcher.isEmpty())){
				
				 try {
				int index = Router.sendPacketTo(routers);
				
				updateRouterContent(index,(dispatcher.getQueue())[0]);
				System.out.println("Packet "+(dispatcher.getQueue())[0].getId()+" sent to Router "+(index+1));
				dispatcher.dequeue();
				
				}
					catch(Exception e1) {
							System.out.println(e1);
							packetsDropped++;
							try {
								dispatcher.dequeue();
							} catch (Exception e5) {
								System.out.println(e5);
							}
						}
			
			}
	

			for(int k=0;k<bandwidth;k++) {
			 try {
				Router.packetReady(routers, tempQue);
				totalServiceTime+=Router.processToDes(routers, tempQue,time);
			 }
			 catch(Exception e2) {
				 System.out.println(e2);
			 }
			}
			
			for(int j = 0; j<numIntRouters;j++ ) {
				System.out.print("R"+(j+1)+":  "+routers[j].toString()+"\n");
			}
			
			for(int l = 0; l<numIntRouters;l++ ) {
				
				if(routers[l].getQueue()[0]!=null) {
				routers[l].getQueue()[0].decTimeToDest();}
			}
			
		try {
			while(!(dispatcher.isEmpty())) {
				dispatcher.dequeue();
			}}
		catch(Exception e) {
			System.out.println(e);
		}
			

			
			time++;
			
		}
		
		
		return ((double)totalServiceTime/totalPacketsArrived);
		
	}
	/**
	 * This is a helper method to update contents of Intermediate routers from dispatcher
	 * @param index
	 * Index of Intermediate Router
	 * @param p
	 * Packet P
	 * @throws Exception
	 * Exception : from "enqueue" method, if overflow occurs
	 */
	private static void updateRouterContent(int index, Packet p) throws Exception {
		if(routers[index].getQueue()[maxBufferSize-1]!=null)
			throw new Exception("Network is congested. Packet "+p.getId()+" is dropped");
		else {
		for(int i= 0 ; i<maxBufferSize;i++) {
			if((routers[index].getQueue())[i] == null) {
				routers[index].enqueue(p);
				break;
			}
				
			
		}
	  }
	}
	
	/**
	 * This is a helper method to determines the size of Packets
	 * @param minVal
	 * minVal : user inputed minimum packet Size
	 * @param maxVal
	 * maxVal : user inputed maximum packet Size
	 * @return
	 * a random int value between minVal and maxVal
	 */

	private static int randInt(int minVal, int maxVal) {	
		return minVal+(int)(Math.random()*(maxVal-minVal));		
		
	}
	

}
