### Description:
   This project, *Direct Digital Synthesis of Sine, Triangle, and Square Waves* is final assigment from ESE 382, composed of
   last 3 labs. Described using VHDL, this project is synthesized using a FPGA. The purpose of the assigment
   is to generate periodic angalog Sine, Triangle and Square waves from digital input. A look up table stores amplitude of first 90 degree 
   of each waveform, using advantage of symmetry, other parts of the wave is then calculated. Output frequency of analog waveform can also 
   adjusted using a frequncy selector, 8-bit DIP switches in Hardware. Final result is Sine, Triangle, Square analog signal of certain frequency.         

### Resources:
   **src** folder contains all modules described using VHDL, along with their testbenches. Top level Test Bench 
    can be used to simulate the system in software. However, when synthesized the system will produced selected waveforms in Hardware.
    
**Lab_09.pdf** , **Lab_10.pdf**, **Lab_11.pdf** containes detailed descriptions of the project. 

**Design and Waveforms** , shows final result in software, along with schematic of Top level VHDL system.  


