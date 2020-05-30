
/****
NAME : RONI DAS
PROJECT 2, ESE 333
3/31/2020
	
NOTE: please allow spaces before and after special characters(<,>,|)
Program can pipe upto 3 commands, cmd1 | cmd2 | cmd3.
Only legal commands are allowed, some special characters, i.e >>,? not supported.  
Note -> background processs,and directory change commands are not pipe-able. 
***/



#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdbool.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <fcntl.h>



//Node structure
struct node
{
	struct node* next;
	char * command;
	bool isCommand;
};



//Prints stored arguments in linked list in specified way. 
//Returns a int number equals to total # of commands ( used to setup piping).
int printLinkedList(struct node* list){

	
	
	struct node* cursor;
		cursor = list;
	int numbOfCommands=0;
	
	/****************** // Uncomment this to display each link
	struct node* eachLink;
		eachLink = list;
	while(eachLink !=NULL){
		

		fputs(eachLink->command, stdout);	
		fputs("\n", stdout);	

			
	eachLink = eachLink->next;
	}
	fputs("\n", stdout);	
	/*******************/

	

	//Prints commands only
	fputs("Commands : ",stdout);

	while(cursor !=NULL){
		
		if(cursor->isCommand){
				fputs(cursor->command, stdout);	
				fputs("  ", stdout);	
				numbOfCommands++;

			}
			
	cursor = cursor->next;
	}
	
	
	
	//Prints command /w arguments lists.
	while(list !=NULL) {

		if(list->isCommand){
			fputs("\n", stdout);
			fputs(list->command, stdout);
			fputs(" : ",stdout);			
			}
		else{	
			
				fputs(list->command, stdout);
				fputs("  ", stdout);
			}
		
		list = list->next;		
		
	}


	fputs("\n\n",stdout);
	
	return numbOfCommands;

}

//delete unused list. 
void deleteList(struct node* list){
	
	struct node* releaseMem;
	while(list != NULL){
		
	 releaseMem = list;	
	 list = list->next;
	 free(releaseMem);	

	}
	
}


//Creates the linkedList to hold arguments. 
//Returns refernce to Head Node.
struct node* createLinkedList(char  **argv){

	bool flag = true; // sets up linkedList
	bool pipeChar = false; // more that one commands per input line?
		

 	struct node* HeadNode = malloc(sizeof(struct node));
	HeadNode->command = *argv++;
	HeadNode->isCommand = true; // first arg is always a command.
 
	

	struct node* traverseNode = malloc(sizeof(struct node));;


	while(**argv!= '\0'){  // traverse until end. 
	
		if(flag){

		        traverseNode->command = *argv;
			HeadNode->next = traverseNode; //next link set-up
			flag = false;
			traverseNode->isCommand = false; 

		}	
		else
			{			
			traverseNode->next = malloc(sizeof(struct node));
			traverseNode->next->command = *argv; //building lists

			if(pipeChar) // pipe char '|' detected in previous token
                 		traverseNode->next->isCommand = true;
			else
		                traverseNode->next->isCommand = false; 


			traverseNode = traverseNode->next;

			if(**argv == '|') // check if following token is command?
				pipeChar = true;
			else
				pipeChar = false;			

			}	
	

	*argv++;
	}

	return HeadNode;


}



//Given parse function, no changed made. 
void  parse(char *line, char **argv)
{	

     while (*line != '\0') {       /* if not the end of line ....... */ 
          while (*line == ' ' || *line == '\t' || *line == '\n'){
               *line++ = '\0';     /* replace white spaces with 0    */
		}
          *argv++ = line;          /* save the argument position     */
          while (*line != '\0' && *line != ' ' && 
                 *line != '\t' && *line != '\n') 
               line++;             /* skip the argument until ...    */
     }
     *argv = '\0';                 /* mark the end of argument list  */
 
}





// Checks for special characters in parsed tokens. 
//Returns the special character or default 'z';
char containsSpecialCharacter(char **args){
	
	char character = 'z';
	char **cursor = args;

	while(*cursor != NULL){
		if (strcmp(cursor[0], "cd") == 0){
			character = 'c';   // encoded char represents "CD" commands, used in other function
			break;   	
		 }	
		if(**cursor == '<' || **cursor == '>' || **cursor == '|' || **cursor == '&' ){
			character = **cursor;
			break;
		}	
			
	**cursor++;
	}
	

	return character;
	
}


//This function redircts the output to a file. 
//Creats a new file, if no file exits
//If exits, replaces the file content with output. (not appended). 
void redirectOutput(char **argv){
			
		int fd1;
		char **args = argv;
		while(**argv != '\0'){

		   if(**argv == '>'){
			*argv++ = NULL;
			fd1 = open(*argv,O_WRONLY | O_CREAT | O_TRUNC, S_IRUSR | S_IWUSR, S_IRGRP, S_IROTH);
			dup2(fd1,1);
			//close(fd1); //execvp closes fd on exit
		break;
		}
			
			*argv++;
			}

			execvp(args[0],args); // replaces the child process. 

}


//This function redirect input from a file. simple read permission. 
void redirectInput(char **argv){

		int fd;
		char **args = argv;
		while(**argv != '\0'){

		   if(**argv == '<'){
			*argv++ = NULL;
			fd = open(*argv,O_RDONLY);
			dup2(fd,0);
			//close(fd); //execvp closes fd on exit
		        break;
		}
			
	*argv++;
	}

			execvp(args[0],args);

	

}



//Supports IN/OUT redirection, Background process, Directory change. # (single Command)
void normalFork(char **argv){

     pid_t  pid;
     int    status;
    bool flagBackground =  (containsSpecialCharacter(argv) =='&'); //  background process logic
    bool directoryChang =  (containsSpecialCharacter(argv) =='c'); //  "cd" command , note: not piped supported. 
		
		//Remove "\0" from end point, for EXECVP processing;
		char **cur = argv;
		while(**cur != '\0'){
			*cur++;
			}
		*cur = NULL;
    

	if(flagBackground){
			char **cursor = argv;		
			while(*cursor != NULL){	
				if(**cursor == '&') // find '&', remove from argument list. 
					*cursor = NULL;
			*cursor++;
			}
	
	}    
		


	if(directoryChang){
			char *buf;
			*argv++;
			chdir(*argv);
			printf("%s\n\n",getcwd(buf,100));
			return; // change parent's directory. no need to fork;

	}

	

     pid = fork(); // create a child process which will be destroyed via execvp. 

     if(pid < 0){
            fputs("Unable to create childprocess",stdout);
	}
	
     if(pid == 0){
		
		int ex = 1;
		char **cur = argv;
	
		switch(containsSpecialCharacter(argv)){ // checks to see if redirection/backgroud tasks needed, otherwise, simply execute.
		
		case '<': 
			redirectInput(argv);
			break;
		case '>':
			redirectOutput(argv);
			break;

		case '&':			
			execvp(argv[0],argv);
			break;
		default:// no special charactes, simply execute command. 
			execvp(argv[0],argv);
			break;
		}


		if(ex < 0){
			printf("Execution Failed\n");
			exit(1);
		}

		exit(0); //if execution failes, exit from child process. 
	}
    

 	 if(flagBackground)
	{
	  //do not wait on Child process to finish: Background process. 
	}
         else
	   waitpid(pid,&status,0); //otherwise wait until execvp completes. 



}


//This function extract Only one command w/ args from a line of piped commands. 
void extractSingleCommand(char **args, char **argv, int numb){

	for(int i=0 ; i < numb ;i ++ ) {  //run for 1st,2nd,3rd child
		
	    while(**argv !='|'){ //advance to next located piped char '|'
		    *argv++;
		   }
		*argv++;
	}


	while(**argv !='\0'){  
		
		if(**argv == '|'){
		        break;
		}
		else{
			*args = malloc(sizeof(*argv));
			 strcpy(*args++,*argv); //make a copy of command and arguments. 
		 }
	
	 *argv++;					
         }
	*args = NULL; //null terminated for execvp processing. 


}


//Supports 2 level piped commands. cmd1 | cmd2 | cmd3
//upto 3 children piped, left to right.

void pipedFork(char **argv, int numb){

     pid_t  pid_A, pid_B, pid_C;
     int    status_A, status_B, status_C;
     int pipefd[2];  //first pipe : child 1, child 2
     int pipe2fd[2]; // second pipe : child 2, child3(if exists).
 
     if(numb == 3){ //numb to decided weather to create second pipe or not. 
	
        if(pipe(pipefd) ==-1){
		perror("unable to establish a connection");
		exit(1);
	      }

	
     	if(pipe(pipe2fd) ==-1){
		perror("unable to establish a connection");
		exit(1);
	}


	}
	else{ // only one pipe need to process commands. 


     	if(pipe(pipefd) ==-1){
		perror("unable to establish a connection");
		exit(1);
	 }

	}


     pid_A = fork(); // first (& second) child is always needed,since there are atleast two commands per piped input line. 

     if(pid_A < 0){
            fputs("Unable to Fork",stdout);
	}
	
     if(pid_A == 0){
		
		int ex = 1;
		char *buf;
		char *args[64];
		
		if(numb == 3){
			 close(pipefd[0]); //read end
			 close(pipe2fd[0]); 
			 close(pipe2fd[1]);
		 	dup2(pipefd[1],STDOUT_FILENO); // duplicate fd 
		 }
		else{
		close(pipefd[0]);    //close read end
		dup2(pipefd[1],STDOUT_FILENO);
		}
		

             extractSingleCommand(args,argv,0); // get only one command from the input line.
	 			

	switch(containsSpecialCharacter(args)){ //check to see if redirection needed. 
		
		case '<': 
			redirectInput(args);
			break;
		case '>':
			redirectOutput(args);
			break;
		default:
			execvp(args[0],args);
			break;
		}
		
		if(ex < 0){
			printf("Execution Failed\n");
			exit(1);
		}
		exit(0);

	}



	pid_B = fork(); //second child is also needed. 

	if(pid_B < 0){
            fputs("Unable to Fork 2nd Child Process",stdout);
	}

	if(pid_B == 0){
		
		int ex = 1;
		char *buf;
	        char *argl[64];
	

		if(numb == 3){
			close(pipefd[1]);    //close write end of pipe 1
			close(pipe2fd[0]) ;  // close read end of pipe 2
			dup2(pipefd[0],STDIN_FILENO); // read from child 1
			dup2(pipe2fd[1],STDOUT_FILENO); // write to child 2
		 }
		else{

		close(pipefd[1]);    //close write end
		dup2(pipefd[0],STDIN_FILENO);
		}
		
		extractSingleCommand(argl,argv,1); // get the second command from input line
		

		switch(containsSpecialCharacter(argl)){
		
		case '<': 
			redirectInput(argl);
			break;
		case '>':
			redirectOutput(argl);
			break;
		default:
			execvp(argl[0],argl);
			break;
		}

		
		if(ex < 0){
			printf("Execution Failed\n");
			exit(1);
		       }		
		exit(0);


		}



	if(numb == 3){ // there are 3 commands, hence, two pipe needed. fork another child.

	pid_C = fork();
	if(pid_C < 0){
            fputs("Unable to Fork 3rd Child Process",stdout);
	}
		
	if(pid_C == 0){
		
		int ex = 1;
	        char *argl[64];
	
		close(pipe2fd[1]);  // close all unused pipes
		close(pipefd[0]);   
		close(pipefd[1]);

		dup2(pipe2fd[0],STDIN_FILENO); // read from child 2
		
		extractSingleCommand(argl,argv,2); // get the last command from line
		

		switch(containsSpecialCharacter(argl)){
		
		case '<': 
			redirectInput(argl);
			break;
		case '>':
			redirectOutput(argl);
			break;
		default:
			fputs("default : ",stdout);
			ex = execvp(argl[0],argl);
			break;
		}


		if(ex < 0){
			printf("Execution Failed\n");
			exit(1);
		       }		
		exit(0);


		}
	  }
		
	
       if(numb == 3 ){ // there are no communication between parent and children
		close(pipefd[0]);
		close(pipefd[1]);
		close(pipe2fd[0]);
		close(pipe2fd[1]);
		waitpid(pid_A,&status_A,0);
		waitpid(pid_B,&status_B,0);
		waitpid(pid_B,&status_C,0);
	}
	else{  // second piped never created. close the first one. 
		close(pipefd[0]);
		close(pipefd[1]);
		waitpid(pid_A,&status_A,0);
		waitpid(pid_B,&status_B,0);

	}		

}


// Transfer function, delegates logic. 
void  execute(char **argv, int numb) //write your code here
		
{
		if(numb == 0){}    // no command to execute
		else if(numb == 1){ normalFork(argv);}  // only one command, procced normally.
		else if(numb > 1) {pipedFork(argv,numb);} // more than one commands, setup neccessery pipes. 
}


void  main(void)
{
     char  line[1024];             /* the input line                 */
     char  *argv[64];              /* the command line argument      */
     char *args[64];
     int numberOfCommands;
 
     while (1) {                   /* repeat until done ....         */
          fputs("Shell -> ",stdout);     /*   display a prompt             */
          fgets(line, 1024, stdin);              /*   read in the command line     */
	  fputs("\n", stdout);
          parse(line, argv);       /*   parse the line               */
          if (strcmp(argv[0], "exit") == 0)  /* is it an "exit"?     */
               exit(0);            /*   exit if it is                */
	  else
               {		
		struct node* list;
		list  = createLinkedList(argv);           //create list
		numberOfCommands = printLinkedList(list); //Prints list, returns # of commands stored. 
	        execute(argv,numberOfCommands);           /* execute the command */	
		}
         
     }
}
