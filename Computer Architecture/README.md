### Description:
The project is about designing a four-stage pipelined multimedia unit. The 25-bits multimedia
instruction sets are based on reduced MIPS based instruction sets.
The project is completed using VHDL . Structural and behavioral coding style is used. Behavioral
style contains multiple *“processes”*. Each of these *"processes"* are are treated as a concurrent
statement. All of the processes in an architecture are executed simultaneously. Structural style
is used at the end to combine each of the modules to create a system. Each module can be tested using their
corresponding testbenches or using TopLevel testbench. A simple Java based program is used to generate bitstream for each instruction.
The output file *instructionsOutput.txt* in **src** is then fed to multimedia unit for processing instructions.Full Project Report and description can be found in **Project_Report.pdf**.  

### Resources:
   **src** folder contains all VHDL files describing each module used in the project along with their testbenches. 
    **Project_Report.pdf** contains detailed description of the project along with pdf version of *Source Files*. **Report** also
    contains simulated data and results from prossing all formats of instructs set. 
