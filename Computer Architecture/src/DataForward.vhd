										-------------------------------------------------------------------------------
--
-- Title       : ALU
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
use STD.textio.all;
use ieee.std_logic_textio.all;


entity DATA_FW is		

	port(		   
		rset_bar : in std_logic;		                 --system Reset	   	 
		
		register_num_1: in std_logic_vector(4 downto 0); 
		register_num_2: in std_logic_vector(4 downto 0);
		register_num_3: in std_logic_vector(4 downto 0);
		
		data_of_rs1 : in std_logic_vector(127 downto 0);	
		data_of_rs2 : in std_logic_vector(127 downto 0);
		data_of_rs3 : in std_logic_vector(127 downto 0);	   
		
		address_WB_of_rd : in std_logic_vector(5 downto 0);
		
				
		ALU_calculation_for_FW : in std_logic_vector(127 downto 0);
		
	
		
		fw_for_registers1 : out  std_logic_vector(127 downto 0);  
		fw_for_registers2 : out  std_logic_vector(127 downto 0);
		fw_for_registers3 : out  std_logic_vector(127 downto 0) 
		
	);
end DATA_FW;



architecture behavioral of DATA_FW is 



begin 

	P1: process(rset_bar, register_num_1, register_num_2, register_num_3, data_of_rs1,data_of_rs2,data_of_rs3, ALU_calculation_for_FW,address_WB_of_rd)	  
	
	
	begin
				
	  if(rset_bar ='0') then
		
			fw_for_registers1 <=(others =>'0');  
			fw_for_registers2 <=(others =>'0');  
			fw_for_registers3 <=(others =>'0');     
			
			
	elsif(address_WB_of_rd(5) = '0') then	 
		
		   	fw_for_registers1 <= data_of_rs1;  
			fw_for_registers2 <= data_of_rs2;  
			fw_for_registers3 <= data_of_rs3;    
	
	else
		
		if(	 ( address_WB_of_rd(4 downto 0) = register_num_1 ) and ( address_WB_of_rd(4 downto 0)= register_num_2 ) and( address_WB_of_rd(4 downto 0)= register_num_3 )) then
			
			fw_for_registers1 <= ALU_calculation_for_FW;  
			fw_for_registers2 <= ALU_calculation_for_FW;  
			fw_for_registers3 <= ALU_calculation_for_FW;	
			
		elsif( (address_WB_of_rd(4 downto 0) = register_num_1) and (address_WB_of_rd(4 downto 0)= register_num_2))	then
			fw_for_registers1 <= ALU_calculation_for_FW;  
			fw_for_registers2 <= ALU_calculation_for_FW;  
			fw_for_registers3 <= data_of_rs3;	
		
		elsif(( address_WB_of_rd(4 downto 0) = register_num_1 )  and ( address_WB_of_rd(4 downto 0)= register_num_3 ))	then
			fw_for_registers1 <= ALU_calculation_for_FW;  
			fw_for_registers2 <= data_of_rs2;  
			fw_for_registers3 <=  ALU_calculation_for_FW;	
			
		elsif(( address_WB_of_rd(4 downto 0)= register_num_2 ) and( address_WB_of_rd(4 downto 0)= register_num_3 ))	then
			fw_for_registers1 <= data_of_rs1;  
			fw_for_registers2 <= ALU_calculation_for_FW;  
			fw_for_registers3 <= ALU_calculation_for_FW;	
		
		elsif(address_WB_of_rd(4 downto 0) = (register_num_1))	then
			fw_for_registers1 <= ALU_calculation_for_FW;  
			fw_for_registers2 <= data_of_rs2;  
			fw_for_registers3 <=  data_of_rs3;	
			
			
		elsif(address_WB_of_rd(4 downto 0) = (register_num_2))	then
			fw_for_registers1 <= data_of_rs1;  
			fw_for_registers2 <= ALU_calculation_for_FW;  
			fw_for_registers3 <= data_of_rs3;	
		
		elsif(address_WB_of_rd(4 downto 0) = (register_num_3))	then
			fw_for_registers1 <= data_of_rs1;  
			fw_for_registers2 <= data_of_rs2;  
			fw_for_registers3 <=  ALU_calculation_for_FW;	
		else
			fw_for_registers1 <= data_of_rs1;  
			fw_for_registers2 <= data_of_rs2;  
			fw_for_registers3 <= data_of_rs3;    
		   end if;
															  
			
		end if;
									  
					
	end process p1;
	
		 
	
	
end behavioral;

