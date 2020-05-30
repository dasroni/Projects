-------------------------------------------------------------------------------
--
-- Title       : PhaseAccumulator FSM
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
-- Description : The design description below is for  FSM for phase accumulator.
--				 Depending on the input max and min, it generates an output which
--				 ditatates the count direction for the phase accumulator. It also
--				 keeps track about whether it is currently on the positive half
--				 half of the cycle or the negative half of the cycle.
--				 
-- Input:  clk, reset_bar, max, min;		TYPE: STD_LOGIC;   
--
-- Output: up, pos;							TYPE: STD_LOGIC;
-- Signal: present_state, next_state;		TYPE: STATE; 
--
-- Lab10: Task 4			Bench:02		Section: 02
-------------------------------------------------------------------------------


 library ieee;
use ieee.std_logic_1164.all;	
use ieee.numeric_std.all;

entity phase_accumulator_fsm is
	port(
		clk : in std_logic; -- system clock
		reset_bar : in std_logic; -- asynchronous reset
		max : in std_logic; -- max count
		min : in std_logic; -- min count
		up : out std_logic; -- count direction
		pos : out std_logic -- positive half of sine cycle
		);
end phase_accumulator_fsm;


architecture phaseFSM of phase_accumulator_fsm is	
type state is(state_1st, state_2nd, state_3rd, state_4th,state_5th);
signal present_state, next_state: state;

begin 
	state_reg: process(clk, reset_bar)
	begin
		if reset_bar = '0' then
		present_state <= state_1st;
	elsif rising_edge(clk) then
		present_state <= next_state;
		end if;
	end process;

	
	outputs: process(present_state) 
	variable quadrant: unsigned (1 downto 0);
	begin
		case present_state is
			when state_1st => 	
			
			when state_2nd => 
			up <= '1'; 
			pos <= '1';
			
			when state_3rd =>
			up <= '0';
			pos <= '1';
			
			when state_4th =>
			up <= '1';
			pos <= '0';
			
			when state_5th =>
			up <= '0';
			pos <= '0';
		end case;
	end process;
	
	
	nxt_state: process(present_state, min, max)
	begin
		case present_state is
			
			when state_1st =>
			if max = '0' and min = '1' then
				next_state <= state_2nd;  
			else
				next_state <= state_1st;
			end if;	
			
			when state_2nd =>
			if max = '1' and min ='0' then
				next_state <= state_3rd;
			else
				next_state <= state_2nd;
			end if;
			
			when state_3rd =>
			if max = '0' and min = '1'then
				next_state <= state_4th;
			else 
				next_state <= state_3rd;
			end if;
			
			when state_4th =>
			if max = '1' and min ='0' then
				next_state <= state_5th;
			else
				next_state <= state_4th;  
				
			end if;
				
			when state_5th =>
			if max = '0' and min ='1' then
				next_state <= state_2nd;
			else
				next_state <= state_5th; 			
			end if;
			
		end case;
	end process;
	
end phaseFSM;
