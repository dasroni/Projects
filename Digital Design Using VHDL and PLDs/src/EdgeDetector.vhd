												 -------------------------------------------------------------------------------
--
-- Title       : EdgeDetector
-- Design      : DDS_with_Frequency_Selection 

-- Author      : Asif Iqbal => ID: 110333685	 
--			   : Roni Das   => ID: 108378223

-- Company     : Stony Brook University
--
-------------------------------------------------------------------------------
--
-- File        : C:\Users\Asif\OneDrive\SBU\Fifth Semester\ESE382\Labs\LAB10\lab10\src\EdgeDetector.vhd
-- Generated   : Wed Apr 24 00:44:42 2019
-- From        : interface description file
-- By          : Itf2Vhdl ver. 1.22
--
-------------------------------------------------------------------------------
--
-- Description : The design description below is an edge detector with
--				 asynchronous reset and a control input which dictates
--				 detection of positive or negative edge. The desgin is
--				 an FSM with three states. The entire system is fully
--				 synchronous. Meaning in response to the system clock
--				 a state transition is allowed.	
--
-- Inputs: 	rst_bar, clk, sig, pos; 	TYPE: STD_LOGIC;
-- Outputs: sig_edge;					TYPE: STD_LOGIC;
-- State:	state_a, state_b, state_c;	TYPE: ENUMERATION;
-- Signal:	present_state;				TYPE: STATE
--
-- Lab09: Task1			Bench:02		Section: 02
-------------------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;

entity edge_det is
 	port(
 		rst_bar : in std_logic; -- asynchronous system reset
 		clk : in std_logic; -- system clock
 		sig : in std_logic; -- input signal
 		pos : in std_logic; -- '1' for positive edge, '0' for negative
 		sig_edge : out std_logic -- high for one sys. clk after edge
 );
	end edge_det;



architecture edgy of edge_det is
	type state is (state_a, state_b, state_c);
	signal present_state, next_state : state;

begin
	state_reg: process(clk, rst_bar)
	begin
		if rst_bar = '0' then
			present_state <= state_a;
		elsif rising_edge(clk) then
			present_state <= next_state;
		end if;
	end process;
		
		outputs: process(present_state)
		begin
			case present_state is
					when state_c => sig_edge <= '1';
					when state_a | state_b => sig_edge <= '0';
				end case;
		end process;
			
			nxt_state: process(present_state, sig,pos)
			begin
				if pos = '1' then
					case present_state is
						when state_a =>	
						if sig = '1' then
							next_state <= state_a;
						else next_state <= state_b;
						end if;
						
						when state_b =>
							if sig = '0' then
								next_state <= state_b;
							else next_state <= state_c;
							end if;
							
						when state_c =>
						if sig = '1' then
							next_state <= state_a;
							else next_state <= state_b;
						end if;
					end case;
					
				else 
					case present_state is
						when state_a =>
						if sig = '0' then
							next_state <= state_a;
						else next_state <= state_b;	
						end if;					   
						
						when state_b => 
						if sig = '1' then
							next_state <= state_b;
						else next_state <= state_c;
						end if;
						
						when state_c =>
						if sig = '1' then
							next_state <= state_b;
							else next_state <= state_a;
						end if;
					  end case;
				end if;
			end process;
end edgy;
