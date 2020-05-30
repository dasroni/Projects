#include <linux/netlink.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <unistd.h>
#include <pthread.h>

#define NETLINK_USER 31

#define MAX_PAYLOAD 1024 /* maximum payload size*/
struct sockaddr_nl src_addr, dest_addr;
struct nlmsghdr *nlh = NULL;
struct iovec iov;
int sock_fd;
struct msghdr msg;


void processUserInput(char *line, int length){

 
 
  	while(1){
          fputs("INPUT  -> ",stdout);    
          fgets(line, length, stdin);            
	  fputs("\n", stdout);


	 printf("Sending message to kernel\n");
	strcpy(NLMSG_DATA(nlh), line);


   	 iov.iov_base = (void *)nlh;
   	 iov.iov_len = nlh->nlmsg_len;
   	 msg.msg_name = (void *)&dest_addr;
   	 msg.msg_namelen = sizeof(dest_addr);
   	 msg.msg_iov = &iov;
   	 msg.msg_iovlen = 1;
    	 sendmsg(sock_fd, &msg, 0);

	}
 
        //  if (strcmp(argv[0], "exit") == 0)  /* is it an "exit"?     */
        //       exit(0);            /*   exit if it is                */

         
 



}

int main()
{
    sock_fd = socket(PF_NETLINK, SOCK_RAW, NETLINK_USER);
    if (sock_fd < 0)
        return -1;

    memset(&src_addr, 0, sizeof(src_addr));
    src_addr.nl_family = AF_NETLINK;
    src_addr.nl_pid = getpid(); /* self pid */

    bind(sock_fd, (struct sockaddr *)&src_addr, sizeof(src_addr));

    memset(&dest_addr, 0, sizeof(dest_addr));
    dest_addr.nl_family = AF_NETLINK;
    dest_addr.nl_pid = 0; /* For Linux Kernel */
    dest_addr.nl_groups = 0; /* unicast */

    nlh = (struct nlmsghdr *)malloc(NLMSG_SPACE(MAX_PAYLOAD));
    memset(nlh, 0, NLMSG_SPACE(MAX_PAYLOAD));
    nlh->nlmsg_len = NLMSG_SPACE(MAX_PAYLOAD);
    nlh->nlmsg_pid = getpid();
    nlh->nlmsg_flags = 0;

	
	char userInput[9];

	int choice;
	int allowed; 
	
	
	
	
	

	fputs("Enter a Topic for Publisher :=>  ",stdout);     
          	fgets(userInput,8, stdin);            
	 
	for(int i=sizeof(userInput) ; i > 0;i--){
		userInput[i] = userInput[i-1];
	}		
		userInput[0] = '1';


		strcpy(NLMSG_DATA(nlh), userInput);
	printf("USER INPUT: %s\n", userInput);

    iov.iov_base = (void *)nlh;
    iov.iov_len = nlh->nlmsg_len;
    msg.msg_name = (void *)&dest_addr;
    msg.msg_namelen = sizeof(dest_addr);
    msg.msg_iov = &iov;
    msg.msg_iovlen = 1;

    printf("Sending message to kernel\n");
    sendmsg(sock_fd, &msg, 0);

    printf("Waiting for message from kernel\n");

    /* Read message from kernel */
    recvmsg(sock_fd, &msg, 0);
    printf("Received message payload: %s\n", NLMSG_DATA(nlh));

	
	
	if(strcmp(NLMSG_DATA(nlh),"ACK") == 0){

	printf("Acknowleged Creating threads\n");

  	 while(1){
	char publish[MAX_PAYLOAD];


	
	    
	 fputs("INPUT  -> ",stdout);    
          fgets(publish, MAX_PAYLOAD-1, stdin);            
	  fputs("\n", stdout);

	for(int i=sizeof(publish) ; i > 0;i--){
		publish[i] = publish[i-1];
	}		
		
	     publish[0] ='1';


       free(nlh);
       nlh = (struct nlmsghdr *)malloc(NLMSG_SPACE(MAX_PAYLOAD));
       memset(nlh, 0, NLMSG_SPACE(MAX_PAYLOAD));
       nlh->nlmsg_len = NLMSG_SPACE(MAX_PAYLOAD);
       nlh->nlmsg_pid = getpid();
       nlh->nlmsg_flags = 0;
	
	 strcpy(NLMSG_DATA(nlh), publish);

  
      iov.iov_base = (void *)nlh;
      iov.iov_len = nlh->nlmsg_len;
      msg.msg_name = (void *)&dest_addr;
      msg.msg_namelen = sizeof(dest_addr);
      msg.msg_iov = &iov;
      msg.msg_iovlen = 1;


    	 sendmsg(sock_fd, &msg, 0);

   	 }
	

	}else{
		printf("This Process is not allowed to Publish or Recieve Messages form Kernel\n");

	}
	

	





	 
		


	
	

	

  
    close(sock_fd);

}
