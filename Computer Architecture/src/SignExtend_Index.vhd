										-------------------------------------------------------------------------------
--
-- Title       : Signed Extend and Index Component
-- Design      : SIMD
-- Author      : 
-- Company     : 
--
-------------------------------------------------------------------------------
--
-- File        : C:\Users\colle\OneDrive\SBU\Sixth Semester\ESE345\Project\VHDL\Pipelined_SIMD_Multimedia_Unit\src\RegisterFile.vhd
-- Generated   : Sat Nov 23 03:16:03 2019
-- From        : interface description file
-- By          : Itf2Vhdl ver. 1.22
--
-------------------------------------------------------------------------------
--
-- Description : 
--
-------------------------------------------------------------------------------

--{{ Section below this comment is automatically maintained
--   and may be overwritten
--{entity {RegisterFile} architecture {behavioral}}

library ieee; 
use ieee.std_logic_1164.all; 
use ieee.std_logic_unsigned.all;
use ieee.numeric_std.ALL;	 



entity signIndex is		

	port(	   	
		rset_bar : in std_logic;		                 --system Reset	   
		data_in_ext :  in std_logic_vector(18 downto 0);
		
		index : out  std_logic_vector(127 downto 0);	
		rs2 : out std_logic_vector(127 downto 0);
		signExtendOutput: out std_logic_vector(127 downto 0)
	);
end signIndex;



architecture behavioral of signIndex is 


begin 
	
	process(rset_bar, data_in_ext)	
	
	
	begin
		if(rset_bar ='0') then
			index <= (others =>'0');
			signExtendOutput <= (others =>'0');  
			rs2 <= (others =>'0');  
			
		else

			index(2 downto 0) <= data_in_ext(18 downto 16);
			signExtendOutput(20 downto 5) <= data_in_ext(15 downto 0); 	
			rs2(4 downto 0) <= data_in_ext(9 downto 5);
			end if;
	  end process;	
	  

		 
	
	
end behavioral;

