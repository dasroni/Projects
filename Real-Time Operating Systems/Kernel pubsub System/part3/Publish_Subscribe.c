
/*
Note: Since, process runs threads concurrnetly, there is no gurantee which thread will spin up first.
try opening multiple terminals, threads can be identified from their output to terminal.
Another way is to spam publish multiple times until thread switch happens. 
I found roughly about after 60-100 messeges, subscriber thread shows up.  

*/



#include <linux/netlink.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <unistd.h>
#include <pthread.h>
#include <fcntl.h>

#define NETLINK_USER 31

#define MAX_PAYLOAD 1024 /* maximum payload size*/
struct sockaddr_nl src_addr, dest_addr;
struct nlmsghdr *nlh = NULL;
struct iovec iov;
int sock_fd;
struct msghdr msg;


//Reciver veriables. 

struct nlmsghdr *mesgHeader = NULL;
struct iovec iov2;
struct msghdr msgReceiver;
//

pthread_t tid[2];
pthread_mutex_t lock;


void *subscriber(void *vargp)
{	
	 	
     while(1){

	


	pthread_mutex_lock(&lock);


	
     //  printf("MY Process ID from subscriber : %d\n", getpid());

	 recvmsg(sock_fd, &msg, 0);
	 printf("::: Subscriber :: Received message payload: %s\n", NLMSG_DATA(nlh));
	pthread_mutex_unlock(&lock);


    }
	
	return NULL;
}

void *publisher(void *vargp)
{	
     
   
     while(1){

	

     pthread_mutex_lock(&lock);
      char userInput[MAX_PAYLOAD];
      fputs("Publish  -> ",stdout);    
      fgets(userInput, MAX_PAYLOAD, stdin);            
      fputs("\n", stdout);

	


       free(nlh);
       nlh = (struct nlmsghdr *)malloc(NLMSG_SPACE(MAX_PAYLOAD));
       memset(nlh, 0, NLMSG_SPACE(MAX_PAYLOAD));
       nlh->nlmsg_len = NLMSG_SPACE(MAX_PAYLOAD);
       nlh->nlmsg_pid = getpid();
       nlh->nlmsg_flags = 0;

      //  printf("MY Process ID IS : %d\n", getpid());
	
	 strcpy(NLMSG_DATA(nlh), userInput);

  
      iov.iov_base = (void *)nlh;
      iov.iov_len = nlh->nlmsg_len;
      msg.msg_name = (void *)&dest_addr;
      msg.msg_namelen = sizeof(dest_addr);
      msg.msg_iov = &iov;
      msg.msg_iovlen = 1;


       sendmsg(sock_fd, &msg, 0);
       printf("Send sucessfully\n");
       pthread_mutex_unlock(&lock);

    }
	

	return NULL;
}


int main()
{	
		
	char userInput[MAX_PAYLOAD];
	int choice;
	int allowed; 
	

    sock_fd = socket(PF_NETLINK, SOCK_RAW, NETLINK_USER);
    if (sock_fd < 0)
        return -1;

    memset(&src_addr, 0, sizeof(src_addr));
    src_addr.nl_family = AF_NETLINK;
    src_addr.nl_pid = getpid(); /* self pid */
	//printf("MY Process ID IS : %d\n", getpid());

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


	
	

	       printf("Registernig Process as both Publisher and Subscriber\n");     
		strcpy(NLMSG_DATA(nlh), "1");

    iov.iov_base = (void *)nlh;
    iov.iov_len = nlh->nlmsg_len;
    msg.msg_name = (void *)&dest_addr;
    msg.msg_namelen = sizeof(dest_addr);
    msg.msg_iov = &iov;
    msg.msg_iovlen = 1;

	

	
    printf("MY Process ID IS : %d\n", getpid());

  




    printf("Sending message to kernel\n");
    sendmsg(sock_fd, &msg, 0);

    printf("Waiting for message from kernel\n");

    /* Read message from kernel */
    recvmsg(sock_fd, &msg, 0);
    printf("Received message payload: %s\n", NLMSG_DATA(nlh));


	if(strcmp(NLMSG_DATA(nlh),"ACK") == 0){

	printf("Acknowleged Creating threads\n");
	pthread_mutex_init(&lock, NULL);
	pthread_create(&tid[0], NULL, &publisher, NULL);
	pthread_create(&tid[1], NULL, &subscriber, NULL);
	pthread_join(tid[1], NULL);
	pthread_join(tid[0], NULL);


	}else{
		printf("This Process is not allowed to Publish or Recieve Messages form Kernel\n");

	}
	

	
    close(sock_fd);

}
