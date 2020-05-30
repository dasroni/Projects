													  										 -------------------------------------------------------------------------------
--
-- Title       : PhaseAccumulator
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
-- Description : The desgin description below is for a phase accumulator system. 
--				 It has a asynchronous reset. A single bit input dictates whether
--				 the data input should be added or subtracted from the current 
--				 count. The count can not exceed the max value and it can not
--				 go below the min value. If count reaches max value, then the 
--				 output max is set. If the count reaches the lowest value(0),
--				 then the output min is set. The least significant m bits are
--				 outputtted out of the total a bits.
--
-- Generic: a, m;					TYPE: INTEGER;
-- Input:   clk, reset_bar, up;		TYPE: STD_LOGIC;
-- Input:	d;						TYPE: STD_LOGIC_VECTOR;
-- Output:  max, min;				TYPE: STD_LOGIC;
-- Output:  q;						TYPE: STD_LOGIC_VECTOR;
--
-- Lab10: Task_3			Bench:02		Section: 02
-------------------------------------------------------------------------------

 library ieee;
use ieee.std_logic_1164.all;	
use ieee.numeric_std.all;

entity phase_accumulator is
	generic (
		a : positive := 14;-- width of phase accumulator
		m : positive := 7 -- width of phase accum output
		);
	port(
		clk : in std_logic; -- system clock
		reset_bar : in std_logic; -- asynchronous reset
		up : in std_logic; -- count direction control, 1 => up, 0 => dn
		d : in std_logic_vector(a -1 downto 0); -- count delta
		max : out std_logic; -- count has reached max value
		min : out std_logic; -- count has reached min value
		q : out std_logic_vector(m - 1 downto 0) -- phase acc. output
		);
end phase_accumulator;



architecture phase of phase_accumulator is
signal temp: std_logic_vector (a-1 downto 0);
begin
	process(clk, reset_bar, up)
	variable count: integer range 0 to 2**a-1;  --2^13 = 16384
	begin

		
		if reset_bar = '0' then
			count := 0;
			min <='1';
			max <= '0';
		elsif rising_edge(clk) then
			
			if up = '1' then
				if (count + to_integer(unsigned(d)) <= 2**a-1) then
					count := count + to_integer(unsigned(d)); 
			 else
			   count := (2**a-1) - to_integer(unsigned(d));  	
					--count := (2**a-1);  
					

				end if;	   
				
				
				if(count + to_integer(unsigned(d)) >= 2**a-1) then
					max <= '1';	
					min <='0';
				end if;
				
			else   
				
				if (count - to_integer(unsigned(d)) >= 0) then
					count := count - to_integer(unsigned(d));
			  else
				count := to_integer(unsigned(d));  
				    --count := 0;

				end if;
				
				if (count - to_integer(unsigned(d)) <= 0) then
					min <= '1';
					max <='0';
				end if;
			end if;
			
		end if;	
		
--			if count = 2**a-1 then  
--				max <= '1';
--				min <= '0'; --new change made 
--				
--			elsif count = 0 then
--				min <= '1';
--				max <= '0'; --new change made	
--				
--		else 
--			max <= '0';
--			min <= '0';
--
--			end if;	
		
		
		temp <= std_logic_vector(to_unsigned(count, a));
		q <= temp(a-1 downto m);  -- least or most sig bit?
	end process;
end phase;
