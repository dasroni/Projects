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



entity IF_ID is		

	port(
		clk : in std_logic;					  		     -- system Clock
		rset_bar : in std_logic;		                 --system Reset	   
	    data_in : in std_logic_vector(24 downto 0);
		
		---For registerFile---
		read_rs1 : out std_logic_vector(4 downto 0);	   
		read_rs2 : out std_logic_vector(4 downto 0);	
		read_rs3 : out std_logic_vector(4 downto 0);

		
		---for Signed extend + indexLoading
		signExtend: out std_logic_vector(18 downto 0);	   

		
		--for control Unit-------
		control_in : out std_logic_vector(24 downto 0)
		
	);
end IF_ID;



architecture behavioral of IF_ID is 



begin 
	
	process(clk,rset_bar)
	
    variable reg_array : std_logic_vector(24 downto 0);
	
	
	begin
		if(rset_bar ='0') then
			read_rs1 <= (others =>'0');
			read_rs2 <= (others =>'0');
			read_rs3 <= (others =>'0');
			signExtend <= (others =>'0'); 
			reg_array := (others =>'0'); 
			control_in <= (others => '0');
			
		elsif (rising_edge(clk)) then
			
			
			reg_array := data_in;			 
		
			
		end if;	 
		
			read_rs1 <= reg_array(9 downto 5);
			read_rs2 <= reg_array(14 downto 10);
			read_rs3 <= reg_array(19 downto 15);
			signExtend <= reg_array(23 downto 5);
			control_in <= reg_array;
	  end process;	
	  

		 
	
	
end behavioral;

