																		-------------------------------------------------------------------------------
--
-- Title       : FrequencyRegister Test Bench
-- Design      : DDS_with_Frequency_Selection

-- Author      : Asif Iqbal => ID: 110333685	 
--			   : Roni Das   => ID: 108378223  

-- Company     : Stony Brook University
--
-------------------------------------------------------------------------------
--
-- File        : C:\Users\Asif\OneDrive\SBU\Fifth Semester\ESE382\Labs\LAB10\lab10\src\FrequencyRegister.vhd
-- Generated   : Wed Apr 24 01:47:09 2019
-- From        : interface description file
-- By          : Itf2Vhdl ver. 1.22
--
-------------------------------------------------------------------------------
--
-- Description : The design description below is for a frequency
--				register non-self checking Test Bench. 
--				Concurrent signal assignments are used to create projected 
--              waveform. The system clock is produced using "for" loop 
--				and period of 1 us. 
--
-- 
-- Stimulus:  load, clk, reset_bar; 		TYPE: STD_LOGIC;
-- 			                    d;			TYPE: STD_LOGIC_VECTOR;
-- Observed:                   q;			TYPE: STD_LOGIC_VECTOR;
--
-- Lab10: Task_2			Bench:02		Section: 02
-------------------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;   	
use ieee.numeric_std.all;

		entity freq_REG_TB is 	
	
		-- Generic declarations of the tested unit
		generic(
		a : positive := 14;
		m : positive := 7 );
		
		
		end freq_REG_TB;
		
		
	architecture nonSelfChecking of freq_REG_TB is	
	
	--input signals
	signal  clk : std_logic := '0';
	signal load, reset_bar : std_logic; 
	signal d : std_logic_vector(a-1 downto 0);
	
	-- observed signal
	signal q : std_logic_vector(a-1 downto 0);	  
	
	signal temp : unsigned(a-1 downto 0);
	
	constant period : time:= 1 us;
	
	begin
		
		U1: entity frequency_reg 
			generic map (a => a)
			port map(clk => clk, load => load , reset_bar => reset_bar, d => d, q=>q);
		
			
			reset_bar <= '0', '1' after 800 ns;
			load <= '1','0' after 7000ns ;	
		
		
						
		clock: process				-- system clock		  	
		begin		
			
      		d <= (others => '0');
	
			for i in 0 to 1032 * (2 ** 7) loop
			  	temp <= to_unsigned(i,14);
				wait for period/2;
				d <= std_logic_vector(temp);
				clk <= not clk;	
				
			end loop;
			std.env.finish;
		end process;
	
	
	
	end nonSelfChecking;





































