										-------------------------------------------------------------------------------
--
-- Title       : InstrictionExecute/WriteBack
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



entity EX_WB is		

	port(
		clk : in std_logic;					  		     -- system Clock
		rset_bar : in std_logic;		                 --system Reset	   
	 
		
		---For registerFile---
		ALU_computation : in std_logic_vector(127 downto 0);
		WB_input : in std_logic_vector(5 downto 0);

		
		----WriteBackData
		data_WB_out: out std_logic_vector(127 downto 0);
		WB_register_rd: out std_logic_vector(4 downto 0);  
		
		
		--writeBackControl---
		register_write : out std_logic 
		
	);
end EX_WB;



architecture behavioral of EX_WB is 


begin 
	
	process(clk,rset_bar)	
	
	variable DATA_reg : std_logic_vector(127 downto 0);
	variable control_reg : std_logic;
	variable rd_reg : std_logic_vector(4 downto 0);
	
	begin
		if(rset_bar ='0') then
			data_WB_out <= (others =>'0');
			WB_register_rd <= (others =>'0');
			register_write <= '0';
 
		
				control_reg := '0';	  
				DATA_reg :=  (others =>'0');
				rd_reg := (others => '0');
				

			
			
		elsif (rising_edge(clk)) then
			

			
			control_reg := WB_input(5);	  
			DATA_reg :=  ALU_computation;
			rd_reg := WB_input(4 downto 0);
			
			
		end if;	
		  	register_write <= control_reg;
			WB_register_rd <= rd_reg;
			data_WB_out <= DATA_reg;
									 
		
	  end process;	
	  

		 
	
	
end behavioral;

