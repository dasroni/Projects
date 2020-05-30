				  --------------------------------------------------------------------------
--
-- Title       : Sine Table
-- Design      : DDS_with_Frequency_Selection    
--
-- Author      : Asif Iqbal => ID: 110333685	 
--			   : Roni Das   => ID: 108378223	
--
-- Company     : Stony Brook
--
-------------------------------------------------------------------------------
--
-- File        : C:\Users\Asif\OneDrive\SBU\Fifth Semester\ESE382\Labs\LAB09\lab09\src\SineTable.vhd
-- Generated   : Wed Apr 23 10:51:46 2019
-- From        : interface description file
-- By          : Itf2Vhdl ver. 1.22
--
-------------------------------------------------------------------------------
--
-- Description : The design description below is a look up table for sine values.
--				 It contains an array with 128 elements, each representing a 
--				 sine value. The address counter outputs a 7 bit table address,
--				 which is then converted to integer to find the appropriate sine
--				 table index. This system outputs the sine value at that index. 
				 
--
--				
-- Input: addr; 			Type: STD_LOGIC_VECTOR; 
-- Output: sine_val; 		Type: STD_LOGIC;

-- Lab10: Task5			Bench:02		Section: 02
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;	
use ieee.numeric_std.all;


		entity sine_table is
		port(
			addr : in std_logic_vector(6 downto 0);-- table address
			sine_val : out std_logic_vector(6 downto 0)-- table entry value
		);
		end sine_table;	   
		
		architecture tableLookUp of sine_table is 	 
		
		type table is array (0 to 127) of std_logic_vector(6 downto 0);
				
			constant sineTable : table := (
			"0000000","0000010","0000011","0000101","0000110","0001000","0001001","0001011",
			"0001101","0001110","0010000","0010001","0010011","0010100","0010110","0010111",
			"0011001","0011011","0011100","0011110","0011111","0100001","0100010","0100100",
			"0100101","0100111","0101000","0101010","0101011","0101101","0101110","0110000",
			"0110001","0110010","0110100","0110101","0110111","0111000","0111010","0111011",
			"0111100","0111110","0111111","1000000","1000010","1000011","1000100","1000110",
			"1000111","1001000","1001010","1001011","1001100","1001101","1001111","1010000",
			"1010001","1010010","1010011","1010101","1010110","1010111","1011000","1011001",
			"1011010","1011011","1011101","1011110","1011111","1100000","1100001","1100010",
			"1100011","1100100","1100101","1100110","1100111","1100111","1101000","1101001",
			"1101010","1101011","1101100","1101101","1101101","1101110","1101111","1110000",
			"1110001","1110001","1110010","1110011","1110011","1110100","1110101","1110101",
			"1110110","1110110","1110111","1110111","1111000","1111000","1111001","1111001",
			"1111010","1111010","1111011","1111011","1111100","1111100","1111100","1111101",
			"1111101","1111101","1111101","1111110","1111110","1111110","1111110","1111110",
			"1111111","1111111","1111111","1111111","1111111","1111111","1111111","1111111" 
					
			);
					
		begin
		   	
			process (addr)
			begin 
				
			sine_val <= sineTable(to_integer(unsigned(addr)));
			
			end process;

		end tableLookUp;