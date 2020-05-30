										-------------------------------------------------------------------------------
--
-- Title       : InstrictionFetch/InstrutionDecoder
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



entity IF_Reg is		

	port(
		clk : in std_logic;					  		     -- system Clock
		rset_bar : in std_logic;		                 --system Reset	   

		counter : out std_logic_vector( 5 downto 0)
		
	);
end IF_Reg;



architecture behavioral of IF_Reg is 


begin 
	
	process(clk,rset_bar)	
	
	
	variable count : integer range 0 to 2**6-1; 
	
	begin
		if(rset_bar ='0') then
			count := 0;
			counter <= (others =>'0');
			
		elsif (rising_edge(clk)) then
			if( not (count = 63) ) then			
			   count := count +1;	
			  
			end if;	  		
			
		end if;	  
		
		
		counter <= std_logic_vector(to_unsigned(count,6));
	  end process;	
	  

		 
	
	
end behavioral;

