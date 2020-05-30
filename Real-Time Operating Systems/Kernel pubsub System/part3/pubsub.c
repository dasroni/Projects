#include <linux/module.h>
#include <net/sock.h> 
#include <linux/netlink.h>
#include <linux/skbuff.h> 
#include <linux/kernel.h>
#include <linux/list.h>
#include <linux/types.h>
#define NETLINK_USER 31



static LIST_HEAD(processList);


struct node {
	int pid;
	struct list_head list;

};




struct sock *nl_sk = NULL;
int pid;
int pid_p = -1, pid_s1 = -1, pid_s2 =-1;
int flag_consumer = 0, flag_producer = 0;
int choice = -1; 


int decodePubSub(char* messg);
int isNotOnList(int pid);
void sendAckMessg(int pid);
void echoMessage(char* messg, int pid);
void testList(void);




void testList(void){
	
	/*
		struct node newProcess = {

		.pid = 20,
		.list = LIST_HEAD_INIT(newProcess.list)

		};
	
		struct node two = {

		.pid = 1233,
		.list = LIST_HEAD_INIT(two.list)

		};
		


	list_add(&newProcess.list,&processList);
	list_add(&two.list,&processList);


	traverse();
	

	*/





}

void echoMessage(char* messg, int pid){

	struct nlmsghdr *nlh;
	struct sk_buff *skb_out;
	int msg_size;
	int res = 0; 
	msg_size = strlen(messg);
	
	
	skb_out = nlmsg_new(msg_size, 0);
	if (!skb_out) {
		printk(KERN_ERR "Failed to allocate new skb\n");
	      	return;
	}



	nlh = nlmsg_put(skb_out, 0, 0, NLMSG_DONE, msg_size, 0);
	NETLINK_CB(skb_out).dst_group = 0; /* not in mcast group */
	strncpy(nlmsg_data(nlh), messg, msg_size);
	
	printk(KERN_ERR "Echoing now to process : %d \n",pid);
		res = nlmsg_unicast(nl_sk, skb_out, pid);

	if (res < 0 )
		printk(KERN_INFO "Error while sending bak to user\n");


}

void sendMessgToAllRegisterProcesses(char* messg);

void sendMessgToAllRegisterProcesses(char* messg){
	
	
	struct node *cursor = NULL;

	list_for_each_entry(cursor,& processList, list)
	{

		echoMessage(messg, cursor->pid);

	}


}
void sendAckMessg(int pid){
	struct nlmsghdr *nlh;
	struct sk_buff *skb_out;
	char *messg = "ACK";
	int msg_size;
	int res = 0; 
	msg_size = strlen(messg);
	
	
	skb_out = nlmsg_new(msg_size, 0);
	if (!skb_out) {
		printk(KERN_ERR "Failed to allocate new skb\n");
	      	return;
	}



	nlh = nlmsg_put(skb_out, 0, 0, NLMSG_DONE, msg_size, 0);
	NETLINK_CB(skb_out).dst_group = 0; /* not in mcast group */
	strncpy(nlmsg_data(nlh), messg, msg_size);
	
		res = nlmsg_unicast(nl_sk, skb_out, pid);

	if (res < 0 )
		printk(KERN_INFO "Error while sending bak to user\n");



}


int isNotOnList(int pid){

	int result = 0;
	int exist = -1;
	
	//int empty = list_empty(&processList);
	//printk(KERN_ERR "Is List Empty : %d\n", empty);

	printk(KERN_INFO "Before travering List\n");

	
	struct node *cursor ;
	list_for_each_entry(cursor,&processList,list){

		

			printk(KERN_INFO "Process ID on List : %d\n", cursor->pid);
			if(cursor->pid == pid){exist = 0;}
			
		
	

	}
	
	if(exist == 0){
		printk(KERN_INFO "Process is already registered\n ");
		result = -1;

	 }
	else{
		printk(KERN_INFO "Process is NOT registered\n ");
		printk(KERN_INFO "Registering Process\n ");
	       
     		struct node *newProcess;
			newProcess = kmalloc(sizeof(struct node),GFP_KERNEL);
			newProcess->pid = pid;
			INIT_LIST_HEAD(&newProcess->list);


			list_add_tail(&newProcess->list,&processList);
			printk(KERN_INFO "PID added to this list succesfully\n");


	}

	printk(KERN_INFO "AFTER THRAVERSING LIST\n");



return result;
}

int decodePubSub(char* messg){

	return 0;
}



static void hello_nl_recv_msg(struct sk_buff *skb)
{

    	struct nlmsghdr *nlh;
	//struct nlmsghdr *nlh_2;
	//struct sk_buff *skb_out;
	//struct sk_buff *skb_out2;
	int msg_size;
	//char *msg = "hello world";
	//int res = 0 , res2 = 0, res3 = 0;
	char *messg;

	printk(KERN_INFO "Entering: %s\n", __FUNCTION__);

    	
	//Recived Messesage 
    	nlh = (struct nlmsghdr *)skb->data;
    	printk(KERN_INFO "Netlink received msg payload:%s\n", (char *)nlmsg_data(nlh));
	messg =NLMSG_DATA(nlh);
	msg_size = strlen(messg);
	//Received Messeage

	

	pid = nlh->nlmsg_pid; /*pid of publishing process */
	printk(KERN_INFO "Process ID Entry : %d\n", pid);


	//testList();
	
	
	
	
	if( isNotOnList(pid) == 0){

			sendAckMessg(pid);

	}else{

		sendMessgToAllRegisterProcesses(messg);

	}


	

	
}

static int __init hello_init(void)
{

    printk("Entering: %s\n", __FUNCTION__);
    struct netlink_kernel_cfg cfg = {
        .input = hello_nl_recv_msg,
    };

    nl_sk = netlink_kernel_create(&init_net, NETLINK_USER, &cfg);
    if (!nl_sk) {
        printk(KERN_ALERT "Error creating socket.\n");
        return -10;
    }

    return 0;
}

static void __exit hello_exit(void)
{
    
    printk(KERN_INFO "exiting hello module\n");
    netlink_kernel_release(nl_sk);

	struct node *temp;
	struct list_head *pos , *q;


	list_for_each_safe(pos,q , &processList)
	{

		temp = list_entry(pos, struct node, list);
		
		list_del(pos);
		kfree(temp);

	}




}

module_init(hello_init); module_exit(hello_exit);EXPORT_SYMBOL(isNotOnList);
EXPORT_SYMBOL(sendAckMessg);EXPORT_SYMBOL(sendMessgToAllRegisterProcesses);EXPORT_SYMBOL(echoMessage);
EXPORT_SYMBOL(testList);

MODULE_LICENSE("GPL");
