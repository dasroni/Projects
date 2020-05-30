/*******************
NAME : RONI DAS
PROJECT 3, ESE 333
4/16/2020
*******************/


#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct core{
struct process* p; // pointer to the process currently running on this core
int proc_time; // cumulative time this process has been running on the core.
int busy;      // either 0 or 1, 0 if there is no process, 1 if there is process running on the core
};

// virtual computer struct
struct computer
{
	struct core cores[4]; //this computer has 4 cores
	long time;   // computer time in millisecond
};

// struct to store process information
struct process
{
char * process_ID; 
int arrival_time;   // when this process arrives (e.g., being created) 
int service_time;  // the amount of time this process needs to run on a core to finish
int io; // boolean io vlaue (C does not have bool value (c89/90), so use int to demonstrate boolean).
};

// one link in a queue that the scheduler uses to chain process structs, 
// this is a standard struct to create linked list which is generally leveraged to implement queue
struct node 
{
	struct process* p; // the process pointed by this link
	struct node *next; // the pointer to the next link
};


//head for the processes queue
struct node* head;
//tail for the processes queue
struct node* tail;
//head for the arrival queue
struct node* pending_head;
//tail for the arrival queue
struct node* pending_tail;
int proc_num; //number of processes in the queue, waiting to be scheduled and not running
int quantum; // the amount of time in milliseconds a process can run on a core before being swapped out
//struct for computer
struct computer computer;



//This method creates a Arrival Queue linkedList, called in read_file();
//Note pending_tail is not used because it's one time read, list doesnt grow dynamically. 
void createPendingList(char* name, int service_time, int arrival_time){

		struct process* newProcess = malloc(sizeof(struct process)); 
				
			newProcess->process_ID=malloc(sizeof(50));
			strcpy(newProcess->process_ID,name);
			newProcess->arrival_time = arrival_time;
			newProcess->service_time = service_time;
			
			
		struct node* cursor = pending_head;
		if(pending_head == NULL){
			
			pending_head = malloc(sizeof(struct node));
			pending_head->p = newProcess;
			pending_head->next = NULL;

		}
		else{
				while(cursor->next != NULL){	cursor = cursor->next;	}
				cursor->next = malloc(sizeof(struct node));
				cursor->next->p = newProcess;
				cursor->next->next = NULL;
		}


}


//This method print content of linked list. 1 = ProcessQ , 0 = ArrivalQ. 
void printList(struct node* list, int listType){

	struct node* cursor = list;

	
		printf("Process ID | Service Time | Arrival Time\n");
		while(cursor != NULL){	

		printf("%-3s%17d%12d\n", cursor->p->process_ID,cursor->p->service_time, cursor->p->arrival_time);
		
		cursor = cursor->next;	}

		if( head == NULL && tail ==NULL && listType == 1){

		 printf("---> No Ready Process Yet!\n");
		}

}


//This method removes a link from either list, and free allocated memory for object,1 = ProcessQ , 0 = ArrivalQ. 
void removeNodeFromList(struct node* list, int listType){
	
	struct node* remv = list; //removes head from given list. 
	if(listType == 0 )  //pendingQueue / ArrivalQ
	{

		pending_head = list->next;
		free(remv);
		
	}
	else  //ProcessQ
	{	if(tail == list){
			head = tail = NULL;

		}else
		{
		head = list->next;

		} 
		free(remv);

	}


}

//Reads content of a file creates a ArrivalQ list. 
void read_file()
{
	int i,i2;
	FILE* file = fopen("input.txt", "r"); 
    char line[90];
    char name[100];
    char service_time[3];
    char arrival_time[3];


    fgets(line, sizeof(line), file);
    while (fgets(line, sizeof(line), file)) {
        //printf("%s", line); 
        i=0;
        while(line[i]!=' '&&i<90){name[i]=line[i];i++;}
        if(i>90)break;
        name[i]=0;
        i2=++i;
        while(line[i]!=' '&&i<90){service_time[i-i2]=line[i];i++;}
        if(i>90)break;
        service_time[i]=0;
        i2=++i;
        while(line[i]!=' '&&i<90){arrival_time[i-i2]=line[i];i++;}
        if(i>90)break;
        arrival_time[i]=0;
        printf("name: %s, service_time: %d, arrival_time: %d\n",name,atoi(service_time), atoi(arrival_time));

        /* add your code here, you are to create the upcoming processes queue here.
           essentially create a node for each process and chain them in a queue.
           note this queue is *not* the process queue used for round robin scheduling
        */

	createPendingList(name,atoi(service_time), atoi(arrival_time)); //Creats ArrivalQ list
	
	
        
        
        
    }
 
    printf("\n\t***Arrival Queue List***\n");	
    printList(pending_head,0);
    printf("\t************************\n");
    fclose(file);
    return;
    
}

//this function call simulates one millisecond of time on the computer (not modified)
void run_one_step() 
{
	int i;
	computer.time++;
	printf("Processing all 4 cores, current Computer time=%lu \n",computer.time);
	for(i=0;i<4;i++)
	{
		if(computer.cores[i].busy)
		{	
			computer.cores[i].p->service_time--; // deduct the remaining service time of the running process by one millisecond
			computer.cores[i].proc_time++; // increment the running time for the process by one millisecond in current quantum
			printf("Core[%d]: %s, service_time= %d,been on core for: %d \n",i,computer.cores[i].p->process_ID,computer.cores[i].p->service_time,computer.cores[i].proc_time);
			
			
			// you need to swap out or terminate a process if it uses up the current quantum, 
			// or finishes all its service time. The code for this should be located in the main()
			// function, not here.
			// Also if your code is done right, the following warning messages should never print.
			 
			
			if(computer.cores[i].proc_time>quantum)
				printf("WARNING: Process on Core[%d] should not run longer than quantum\n",i);
			if(computer.cores[i].p->service_time<0)
				printf("WARNING: Process on core[%d] stayed longer than its service time.\n",i);
		}
	}
}

//Function not modified
void run_one_step_p3() 
{
	int rndm,i;
	computer.time++;
	printf("Processing all 4 cores, current Computer time=%lu \n",computer.time);
	for(i=0;i<4;i++)
	{
		if(computer.cores[i].busy)
		{	
		    if(computer.cores[i].p->io==1)
				printf("WARNING: Process on core[%d] has io trigerred, please remove from core, reset io signal and place it back in queue\n",i);
			if(computer.cores[i].p->io==0)
			{
				computer.cores[i].p->service_time--;
				// with 10% probability, generate an io event
				rndm=rand()%10+1;
				if(rndm==10)computer.cores[i].p->io=1;	
			}
			computer.cores[i].proc_time++;
			printf("Core[%d]: process %s, service_time= %d,been on core for: %d \n",i,computer.cores[i].p->process_ID,computer.cores[i].p->service_time,computer.cores[i].proc_time);
			
			
			// you need to swap out or terminate a process if it uses up the current quantum, has an i/o event; 
			// or finishes all its service time. The code for this should be located in the main()
			// function, not here.
			// Also if your code is done right, the following warning messages should never print.
			
			if(computer.cores[i].proc_time>quantum)
				printf("WARNING: Process on Core[%d] should not run longer than quantum\n",i);
			if(computer.cores[i].p->service_time<0)
				printf("WARNING: Process on core[%d] stayed longer than its service time.\n",i);
		}
	}
}


//NOTE: you must free struct node after taking a link off the round robin queue, and scheduling the respective 
// process to run on the core. Make sure you free the struct node to avoid memory leak.
void sched_proc(struct process*p,int core_id)
{
	if(computer.cores[core_id].busy==0)
	{
		printf("Process[%s] with service_time %d has been added to core %d\n",p->process_ID,p->service_time,core_id);
		computer.cores[core_id].busy=1;
		computer.cores[core_id].p=p;
		computer.cores[core_id].proc_time=0;
		proc_num --; // xx added accounting of #proc
	}
	else printf("ERROR: must call remove_proc to remove current process before adding another to the core.\n");
}

// This handles removing a process from a core, and either discarding the process if its service_time is <=0 
// or adding it to the back of the round robin queue

void remove_proc(int core_id)
{
	printf("Process[%s] at core %d has been removed from core with remaining service_time=%d\n",
	computer.cores[core_id].p->process_ID,core_id,computer.cores[core_id].p->service_time);
	
	// if the process has finished all its service time, terminate and clean up
	if(computer.cores[core_id].p->service_time<=0)
	{
		computer.cores[core_id].busy=0;
		// free up allocated memory for process ID and struct upon termination of a process
		free(computer.cores[core_id].p->process_ID);
		free(computer.cores[core_id].p);
		computer.cores[core_id].proc_time=0;
	}
	// the process needs to run for more time, put it back into the queue for future scheduling
	else
	{
		computer.cores[core_id].proc_time=0;
		// reinsert back to the queue
		if(tail==NULL)
		{
			// in case queue is empty, i.e. all nodes struct were freed and there are no processes in the queue, this will become the first one
			tail=head=malloc(sizeof(struct node)); 
			head->p=computer.cores[core_id].p;
			head->next=NULL; 
			proc_num++;
			computer.cores[core_id].busy=0;
		}
		else
		{ 
			
			tail->next = malloc(sizeof(struct node));
			tail=tail->next;
			tail->p=computer.cores[core_id].p;
			tail->next=NULL;
			proc_num++;
			computer.cores[core_id].busy=0;
			
			
		}
		
	}

}

// a demo running 4 processes until they're finished. The scheduling is done explicitly, not using
// a scheduling algorithm. This is just to demonstrate how processes will be scheduled. In main() 
// you need to write a generic scheduling algorithm for arbitrary number of processes.
void demo()
{
	int i;
	struct process *p0,*p1,*p2,*p3;
	p0=malloc(sizeof(struct process));
	p1=malloc(sizeof(struct process));
	p2=malloc(sizeof(struct process));
	p3=malloc(sizeof(struct process));
	
	p0->process_ID=malloc(sizeof(50));//you can assume process ID will never exceed 50 characters
	p1->process_ID=malloc(sizeof(50));
	p2->process_ID=malloc(sizeof(50));
	p3->process_ID=malloc(sizeof(50));

	strcpy(p0->process_ID,"first");
	strcpy(p1->process_ID,"Second");
	strcpy(p2->process_ID,"Third");
	strcpy(p3->process_ID,"Fourth");

	//assign arrival time
	p0->arrival_time=0;
	p1->arrival_time=0;
	p2->arrival_time=0;
	p3->arrival_time=0;

	//assign service time
	p0->service_time=16;
	p1->service_time=17;
	p2->service_time=19;
	p3->service_time=21;

	// we will skip queue construction here because it's just 4 processes.
	// you must use the round robin queue for the scheduling algorithm for generic cases where many processes
	// exist and may need more than one quantum to finish
	
	
	// xx 4 processes are waiting to be scheduled. No queue is built in demo for simplicity.
	// in your generic algorithm, you should create actual queues, and proc_num should be the number of processes whose
	// arrival time has come, and are waiting in the round robin queue to be scheduled.
	proc_num=4; 
	
	
	//schedule process to each core
	sched_proc(p0,0);
	sched_proc(p1,1);
	sched_proc(p2,2);
	sched_proc(p3,3);

	for(i=0;i<16;i++)run_one_step();
	remove_proc(0);
	run_one_step();
	remove_proc(1);
	run_one_step();
	run_one_step();
	remove_proc(2);
	run_one_step();
	remove_proc(3);
	sched_proc(head->p,0);
	
	//NOTE: you must free struct node after scheduling the process. The demo code is not doing it here
	// for simplification, but you have to do it in your code or you will have memory leakage
	
	//head==tail since it was the only one added now to remove it we just make pointer pointing to NULL
	head=NULL;
	tail=NULL;
	run_one_step();
	remove_proc(0);
	printf("DONE\n");
}

//This method creates a process Queue linked list. 
void ProcessQueue(struct node* processNode){

		       struct process* newProcess = malloc(sizeof(struct process));
				
			newProcess->process_ID=malloc(sizeof(50));
			strcpy(newProcess->process_ID,processNode->p->process_ID);
			newProcess->arrival_time = processNode->p->arrival_time;
			newProcess->service_time = processNode->p->service_time;
			

		if(tail == NULL){ //First element on list
			
			head = tail = malloc(sizeof(struct node));
			head->p = newProcess;
			head->next = NULL;

		}
		else{	//add it to end of the list
			tail->next = malloc(sizeof(struct node));
			tail=tail->next;
			tail->p = newProcess;
			tail->next = NULL;			

		}



}

//Method creates a Process Queue based on arrival time from Arrival Q list. 
//note: allocated memory are freed indiviually when a process copied from ArrivalQ -> ProcessQ 
void RoundRobinList(){

	int currentTime = computer.time; 
		
	struct node* cursor = pending_head;
	
       if( pending_head != NULL){ // is ArrivalQ empty?
		while( (cursor != NULL) && (cursor->p->arrival_time == currentTime)){ // when there are more than 1 process arrives at the same computer time
			ProcessQueue(cursor);
			cursor = cursor->next;
			removeNodeFromList(pending_head,0); // removes the found node, free memory, & deep copied to ProcessQ ; 0 = arrivalQ list, 1= ProcessQ list.
		 
		}

	}		

}


// Method to schedual or remove a process 
//Note: this method works both for run_one_step() and run_one_step_p3(); argv : 0 means, no I/O interrupts
void RunProcess(int isIOwait){  


		//Check all four cores
		for(int i= 0; i< 4; i++){

			if( computer.cores[i].busy == 1 ){ //is it busy at the moment?

			int proRemTime = computer.cores[i].p->service_time;
                        int maxAllowedTime = computer.cores[i].proc_time;
			

			if(isIOwait == 0 ){  // logic to run_one_step()
			      if((proRemTime <= 0) || maxAllowedTime >= quantum ){  remove_proc(i);}}

			else{// logic to run_one_step_p3()
			    int ioWait = computer.cores[i].p->io;
			    if((proRemTime <= 0) || maxAllowedTime >= quantum || ioWait == 1  ){  // if either of these condition true, remove the process from core
					    computer.cores[i].p->io = 0; //set I/O interrupt back to 0 for current process. 
					    remove_proc(i);} 

			  }
	         	}
		}
	
	if(head != NULL) { // is ProcessQ empty? if not assign it to a core. 

		struct node* cursor = head;	

		for(int i= 0; i< 4; i++){ // check all 4 cores to find a spot for processes. 

			int isBusy = computer.cores[i].busy;
                     
			if( isBusy == 0 && cursor != NULL){ // if the core is idle, give some work. 


			struct process* newProcess = malloc(sizeof(struct process));
				
			newProcess->process_ID=malloc(sizeof(50));
			strcpy(newProcess->process_ID,cursor->p->process_ID);
			newProcess->arrival_time = cursor->p->arrival_time;
			newProcess->service_time = cursor->p->service_time;

				sched_proc(newProcess, i);
				cursor = cursor->next;      // more than 1 ready process can be assigned to core (if free)
				removeNodeFromList(head,1); //free memory,fix linkes ; 0 = arrivalQ list, 1= ProcessQ list.
				
			}
				

		}


	}



}

// Logic to check if all work has completed.
// Returns 99, when done ; otherwise : -1 (default) still work left. 
int allProcessHalt(){
	int isCompl = -1;

	if(pending_head == NULL && tail == NULL && head == NULL){ // All the lists are empty.

		if(computer.cores[0].busy == 0 && computer.cores[1].busy == 0 && 
		   computer.cores[2].busy == 0 && computer.cores[3].busy == 0){    // all the cores are idle state. 
				isCompl = 99; 

			}


	}

	return isCompl;

}

// Method to run simulation W/o I/O exceptions
void roundRobinWithoutIO(){

	
	while(allProcessHalt() == -1){   // ars jobs finished?

	RoundRobinList(); // create ProcessQ, when process arrives.
	printf("\n\t***ROUND ROBIN LIST***\n");	
	printList(head,1);  //print the list
	printf("\t************************\n");
	RunProcess(0); // remove or schedual if necessary , 0 = without I/O int 
	printf("\n");
	run_one_step(); // simulate. 
		

	}



}


void roundRobinWithIOwait(){

	while(allProcessHalt() == -1){  // ars jobs finished?

	RoundRobinList(); // create ProcessQ, when process arrives.
	printf("\n\t***ROUND ROBIN LIST***\n");	
	printList(head,1);
	printf("\t************************\n");
	RunProcess(1); // remove or schedual if necessary , 1 = with I/O int. 
	printf("\n");
	run_one_step_p3(); // simulate.
		

	}

}

void init()
{
	quantum=20;
	head=tail=NULL;
	pending_head = pending_tail = NULL;
	computer.time = 0; // reset computer time. 
}

int main()
{
	init();
	printf("\t*******Starting Demo*******\n");
	demo();
	printf("\t*******Demo END*******\n");
	init();
	printf("\t*******Reading Input*******\n");
	read_file(); 
	
	
	/* your code goes here for part2. In part 2, you create one node for each process, and put them on an 
	 * 'upcoming process' queue first. Then your code calls run_one_step(), for each process whose arrival time
	 * has come, take the node off the 'upcoming process' queue, and place it on round robin queue. For each
	 * process that's selected to run on a core, take the node off round robin queue.
	 * 
	 * Repeat run_one_step() until all processes finish. Please handle memory allocation/deallocation properly so there's no leak
	 */
	
	printf("\t*******Starting Simulation Without I/O*******\n");
	roundRobinWithoutIO();
	printf("\t*******END Simulation Without I/O*******\n\n");

	
	
	
	
	/* After part 2 is done, you clean up everything, e.g., freeing up all memory allocated,
	 * reset queues to empty etc.
	 * Then restart for part 3: read input file for all processes, initialize queues,
	 *  run processes using run_one_step_p3() so random i/o event can happen at each step on each core, 
	 *  until all processes finish. Remember to clean up again at the end!
	 */
	
	init();
	printf("\t*******Reading Input*******\n");
	read_file(); 
	printf("\t*******Starting Simulation With I/O*******\n");
	roundRobinWithIOwait();
	printf("\t*******END Simulation With I/O*******\n");
		
	
	
	printf("\n\t*******Done*******\n");
	return 0;
}

