### Description:
   
  * **Kernel pub/sub System**
        <p>*Kernel pub/sub System* is a **4** parts project describing common publishing and subscribing model and 
        solves consumer/producer problem. **Part 1**, consists of thread based producer-consumer program with mutex implementation.
        **Part 2**, consists of process communication via kernel module. *Netlink API* is used in successful connection of 
        user-space process to kernel module. In this part, **2** userspace process can be *Subscribers* while **1** process
        is *Publisher*. **Part 3**, of the project's allows any number of user-space process to be either *Subscribers* or *Publishers*.
        **Bonus Part**, allows *Publisher* process produce under a **Topic** while *Subscriber* process only gets messages when 
        they are subscribed to that **Topic** only. For kernel data structure, *rhashtable* and *kernel linkedlist* is used. <p>
 * **Linux Shell**
        <p>*Linux Shell* is a custom shell program that supporst subsets of linux commands. Program reads user input from
        terminal and process it for various system calls. Using system calls such as *fork* , *execvp* , *waitpid* ,  *waitpid* and many more program 
    execute user command. Program also supports 3 levels of *piped commands*. Overall, custom shell program can be used in processing 
    almost all the linux commands.   <p>
 * **Round-Robin Scheduling**
        <p>*Round-Robin Scheduling* is a C program simulating process scheduling using *Round-Robin Algorithm*.
    As per specification, the program contains a *Virtual Computer construct* with *4 cores* and given quantum. Arrival Process are 
read in from a user text file and scheduling is performed. Program also considers scheduling process with I/O Interrupts.  <p>
 

### Resources:
   This directory contains **3** separate projects from Real-Time Operating Systems Class, aka, ESE 333. Under each of the
four project's folder contains all the *Source codes* for respective project. 

In directory, **Kernel pub/sub System** contains detailed description of the project in pdf file, along with *Source Codes* for all parts written in language C.


In directory, **Linux Shell** contains detailed description of the project, along with *Source Codes* written in language C.

In directory, **Round-Robin Scheduling** contains detailed description of the project, along with *Source Codes* written in language C.
Directory also contains **input.txt** file describes incoming process to the system (input to program).


