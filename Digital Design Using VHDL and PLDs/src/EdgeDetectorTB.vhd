-------------------------------------------------------------------------------
--
-- Title       : EdgeDetector TestBench
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
-- Description : The design description below is a non-self checking TestBench for 
--				entity Edge Detector. Stimulus for scaler objects
--				are created using projected waveforms concepts. 
--				For system clock is generated in an infinite 
--				"for " loop. The period of this signal is = 1 us. 
--
-- stimulus  : 	rst_bar, clk, sig, pos; 	TYPE: STD_LOGIC;
-- Observed  : sig_edge;					TYPE: STD_LOGIC;  

--
-- Lab10: Task1			Bench:02		Section: 02
-------------------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;   

		entity edge_det_TB is 
		end edge_det_TB;
		
		
	architecture nonSelfChecking of edge_det_TB is	
	
	--input signals
	signal  clk : std_logic;
	signal sig, pos, rst_bar : std_logic; 
	
	-- observed signal
	signal sig_edge : std_logic;
	
	constant period : time:= 1 us;
	
	begin
		
		U1: entity edge_det port map (clk => clk, sig => sig, pos => pos, rst_bar => rst_bar, sig_edge => sig_edge);
		
		process
		begin  
			
			rst_bar <= '0', '1' after 800 ns;
			sig <= '0','1' after 2300 ns, '0' after  2800 ns, '1' after 5200ns, '0' after 6300ns;
			pos <= '1','0' after 4800ns ;
					
			
			clk <= '0';
			wait for period/2;
			
			loop 
				clk <= not clk;
				wait for period/2;
			end loop;

		end process;
	
	
	
	end nonSelfChecking;





































