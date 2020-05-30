						  					-------------------------------------------------------------------------------
--
-- Title       : PhaseAccumulator FSM Test Bench
-- Design      : DDS_with_Frequency_Selection

-- Author      : Asif Iqbal => ID: 110333685	 
--			   : Roni Das   => ID: 108378223

-- Company     : Stony Brook University
--
-------------------------------------------------------------------------------
--
-- File        : C:\Users\Asif\OneDrive\SBU\Fifth Semester\ESE382\Labs\LAB10\lab10\src\PhaseAccumulatorFSM.vhd
-- Generated   : Wed Apr 24 03:59:32 2019
-- From        : interface description file
-- By          : Itf2Vhdl ver. 1.22
--
-------------------------------------------------------------------------------
--
-- Description : The design description below is non-self Checking Test Bench 
--				 for  FSM for phase accumulator.   	
--				Concurrent signal assignments are used to create projected 
--              waveforms. The system clock is produced using "for" loop 
--				and period of 1 us.

--				 
-- Stimulus:  clk, reset_bar, max, min;		    TYPE: STD_LOGIC;   
--
-- Observed: up, pos;							TYPE: STD_LOGIC;
--
-- Lab10: Task 4			Bench:02		Section: 02
-------------------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;   	
use ieee.numeric_std.all;

		entity phase_accumulator_fsm_TB is 		
		end phase_accumulator_fsm_TB;
		
		
	architecture nonSelfChecking of phase_accumulator_fsm_TB is	
	
	--input signals
	signal  clk : std_logic := '0';
	signal reset_bar, max , min : std_logic; 

	
	-- observed signals
	signal up, pos : std_logic;
	
	constant period : time:= 1 us;
	
	begin
		
		U1: entity phase_accumulator_fsm
			port map(clk => clk, up => up , reset_bar => reset_bar, pos=>pos, max => max, min => min);
		
			
			reset_bar <= '0', '1' after 800 ns;
			
		  	max <= '0', '1' after 7000ns, '0' after 18000ns , '1' after 27000 ns;  
		    min <= '1', '0' after 7000ns, '1' after 18000ns , '0' after 27000 ns;
			
	
						
		clock: process				-- system clock		  	
		begin		
			
			for i in 0 to 1032 * (2 ** 7) loop
				wait for period/2;
				clk <= not clk;	
				
			end loop;
			std.env.finish;
		end process;
	
	
	
	end nonSelfChecking;





































