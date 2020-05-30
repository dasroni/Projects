#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>

const int max = 10000000; //number of message to be sent
pthread_mutex_t lock;
int length = 0; //number of entries in the linked list
pthread_t tid[2];

struct node* head = NULL;
struct node* tail = NULL;

struct node
{
	struct node* next;
	int data;
};




void linkedListAppend(int num){


	
		
		
	if(head == NULL && tail == NULL){
		//printf("\nFirst Element : %d\n",num);
		head = tail = malloc(sizeof(struct node));
		head->data = num;
		tail->next = NULL;
		length++;

	}else{
		//printf("\nProducing Data: %d",num);
		tail->next = malloc(sizeof(struct node));
		tail = tail->next;
		tail->data = num;
		tail->next = NULL;
		
		length++;
		//printf("\nLength from producer : %d\n", length);
		

	}	




}


int consumeData(){
	
	int result = -1;
	if(head != NULL){
		
		
		
		//printf("\nConsuming Head Data : %d", head->data);
		struct node* temp = head;
		
		if(head == tail){
			free(temp);
			head = tail = NULL;
			


		}
		else{
		head = head->next;
		free(temp);
		}
	
	       
		result = 0;
		length--;
		// printf("\nLength from consumer : %d", length);
	
	}
	
	return result;
}

void *consumer(void *vargp)
{	
	
	
	int count = 0;
	while(count < max)
	{
		
		pthread_mutex_lock(&lock);
		
		if(consumeData() == 0){count++;}
		
		pthread_mutex_unlock(&lock);

	//consume messages (data from 0 to max-1, throw error if data out of order), invoke free on the head
	//
	if(head != NULL){
		if(head->data != count) {printf("\nERROR! data %d should be %d!\n", head->data, count);}}


	





	}
	
	
	return NULL;
}

void *producer(void *vargp)
{	

	


	int count = 0;
	while(count < max)
	{	
		//produce messages (data from 0 to max-1), malloc new tails

		pthread_mutex_lock(&lock);
		linkedListAppend(count);
		count++;	
		pthread_mutex_unlock(&lock);

		
		
	
	}
	

	return NULL;
}

int main()
{
	pthread_mutex_init(&lock, NULL);
	pthread_create(&tid[0], NULL, &producer, NULL);
	pthread_create(&tid[1], NULL, &consumer, NULL);
	pthread_join(tid[1], NULL);
	pthread_join(tid[0], NULL);
	
	

	
	if(head != NULL) {printf("ERROR! List not empty\n");}
	printf("\nSuccess\n");
	exit(0);
}

/*
Useful commands:
pthread_mutex_init(&lock, NULL)
pthread_create(&tid[0], NULL, &producer, NULL);
pthread_join(&tid[1], NULL);
pthread_mutex_lock(&lock);
pthread_mutex_unlock(&lock);
*/
