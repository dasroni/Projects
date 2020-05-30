												-------------------------------------------------------------------------------
--
-- Title       : PhaseAccumulator Test Bench
-- Design      : DDS_with_Frequency_Selection 
--
-- Author      : Asif Iqbal => ID: 110333685	 
--			   : Roni Das   => ID: 108378223 
--
-- Company     : Stony Brook University
--
-------------------------------------------------------------------------------
--
-- File        : C:\Users\Asif\OneDrive\SBU\Fifth Semester\ESE382\Labs\LAB10\lab10\src\PhaseAccumulator.vhd
-- Generated   : Wed Apr 24 02:32:16 2019
-- From        : interface description file
-- By          : Itf2Vhdl ver. 1.22
--
-------------------------------------------------------------------------------
--
-- Description : The desgin description below is non-self checking Test Bench
--				 for a phase accumulator system.
--				Concurrent signal assignments are used to create projected 
--              waveforms. The system clock is produced using "for" loop 
--				and period of 1 us.
--				 
--
-- Generic: a, m;					    TYPE: INTEGER;
-- Stimulus:   clk, reset_bar, up;		TYPE: STD_LOGIC;
-- Stimulus:	d;						TYPE: STD_LOGIC_VECTOR;	
--
-- Observed:  max, min;				    TYPE: STD_LOGIC;
-- Observed:  q;						TYPE: STD_LOGIC_VECTOR;
--
-- Lab10: Task_3			Bench:02		Section: 02
-------------------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;   	
use ieee.numeric_std.all;

		entity phase_TB is 	
	
		-- Generic declarations of the tested unit
		generic(
		a : positive := 14;
		m : positive := 7 );
		
		
		end phase_TB;
		
		
	architecture nonSelfChecking of phase_TB is	
	
	--input signals
	signal  clk : std_logic := '0';
	signal up, reset_bar : std_logic; 
	signal d : std_logic_vector(a-1 downto 0);
	
	-- observed signal
	signal q : std_logic_vector(m-1 downto 0);	 
	signal max, min : std_logic;
	
	constant period : time:= 1 us;
	
	begin
		
		U1: entity phase_accumulator 
			generic map (a => a, m => m)
			port map(clk => clk, up => up , reset_bar => reset_bar, d => d, q=>q, max => max, min => min);
		
			
			reset_bar <= '0', '1' after 800 ns;
			up <= '1', '0' after 10 ms;	
			
			d <= "00000000000010";
		
		
						
		clock: process				-- system clock		  	
		begin		
			
			for i in 0 to 1032 * (2 ** 7) loop
				wait for period/2;
				clk <= not clk;	
				
			end loop;
			std.env.finish;
		end process;
	
	
	
	end nonSelfChecking;





































