 --------------------------------------------------------------------------
--
-- Title       : Simple Direct Digital Synthesis System
-- Design      : lab10	   
--
-- Author      : Asif Iqbal => ID: 110333685	 
--			   : Roni Das   => ID: 108378223	
--
-- Company     : Stony Brook
--
-------------------------------------------------------------------------------
--
-- File        : C:\Users\Asif\OneDrive\SBU\Fifth Semester\ESE382\Labs\LAB09\lab09\src\Adder_Subtracter.vhd
-- Generated   : Wed Apr 17 10:51:46 2019
-- From        : interface description file
-- By          : Itf2Vhdl ver. 1.22
--
-------------------------------------------------------------------------------
--
-- Description : The design description below is for a addder/subtractor. In 
--				 system, the input bit pos is combined with the 7 bit input
--				 sine_value to output a 8 bit value. This output is then fed
--				 to the DAC. The pos bit is now is the MSB, followed by the
--				 sine_value bits.
				 
--
--				
-- Input: pos, sine_value; 		Type: STD_LOGIC;
-- Output: dac_sine_val; 		Type: STD_LOGIC_VECTOR; 

-- Lab11: Task5			Bench:02		Section: 02
-------------------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;	
use ieee.numeric_std.all;


		entity adder_subtracter is
			port(
				pos : in std_logic;-- indicates pos. or neg. half of cycle
				sine_value : in std_logic_vector(6 downto 0);-- from sine table
				dac_sine_val : out std_logic_vector(7 downto 0)-- output to DAC
				);
		end adder_subtracter;	   
		
		architecture addOrsub of adder_subtracter is 	 
					
		begin
		   	
			process (pos, sine_value) 
			begin 
				if( pos = '1') then
					--dac_sine_val(6 downto 0) <= sine_value;
					dac_sine_val <= std_logic_vector(to_unsigned(to_integer(unsigned(sine_value)), 8) + 127);
				else
					--dac_sine_val(6 downto 0) <= sine_value;
					dac_sine_val <= std_logic_vector(127 - to_unsigned(to_integer(unsigned(sine_value)), 8));
				end if;
			
			end process;

		end addOrsub;