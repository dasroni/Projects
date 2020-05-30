										-------------------------------------------------------------------------------
--
-- Title       : InstrictionDECODE/InstrutionEXecution
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



entity ID_EX is		

	port(
		clk : in std_logic;					  		     -- system Clock
		rset_bar : in std_logic;		                 --system Reset	   
	  
		
		---For Buffer---
		data_of_rs1 : in std_logic_vector(127 downto 0);	   
		data_of_rs2 : in std_logic_vector(127 downto 0);	
		data_of_rs3 : in std_logic_vector(127 downto 0);
		control_for_ALU: in std_logic_vector(4 downto 0); 
		wb_control_rd_input : in std_logic_vector(5 downto 0); 
		
		
		index : in std_logic_vector(127 downto 0);
		signExtend : in std_logic_vector(127 downto 0);	   
		
		mux_control_1 : in std_logic;
		mux_control_2 : in std_logic;
		mux_control_Z : in std_logic;
		
		rs2_c : in std_logic_vector(127 downto 0);
		
		
		data_of_rs1_ALU : out std_logic_vector(127 downto 0);	   
		data_of_rs2_ALU : out std_logic_vector(127 downto 0);	
		data_of_rs3_ALU : out std_logic_vector(127 downto 0); 
		
		--for control Unit for ALU-------
		control_for_ALU_out: out std_logic_vector(4 downto 0);   
		
			
		index_out : out std_logic_vector(127 downto 0);
		signExtend_out : out std_logic_vector(127 downto 0); 
		
		mux_control_1_out : out std_logic; 
		mux_control_2_out : out std_logic; 
		mux_control_Z_out : out std_logic;
		
		rs2_out : out std_logic_vector(127 downto 0);
		
		--for writeback control+ rd---
		wb_control_rd : out std_logic_vector(5 downto 0)
		
	);
end ID_EX;



architecture behavioral of ID_EX is 

	type registers is array (3 downto 0) of std_logic_vector(127 downto 0);   --register  


signal reg_array : registers;			-- singal is needed to access the array	


begin 
	
	process(clk,rset_bar)	
	
	
	
	variable webRegisters : std_logic_vector(5 downto 0); 	
	variable indexVari : std_logic_vector(127 downto 0);
	variable signExtendVari: std_logic_vector(127 downto 0);  
	
	variable mux_1_v : std_logic;	   
	variable mux_2_v : std_logic; 
	variable mux_Z_v : std_logic;
	variable rs2V : std_logic_vector(127 downto 0);
	
	begin
		if(rset_bar ='0') then
			data_of_rs1_ALU <= (others =>'0');
			data_of_rs2_ALU <= (others =>'0');
			data_of_rs3_ALU <= (others =>'0');
			control_for_ALU_out <= (others =>'0'); 
			wb_control_rd <= (others =>'0');  
			
			mux_control_1_out <= '0'; 
			mux_control_2_out <= '0';
			mux_control_Z_out <= '0';
			
			webRegisters := (others =>'0'); 
			index_out <= (others =>'0');
		    signExtend_out <= (others =>'0'); 
			
			
			signExtendVari := (others =>'0');
			indexVari := (others =>'0');
			
			rs2_out <= (others =>'0');	
			rs2V := (others =>'0');	 
			
				
		 	mux_1_v :='0';	   
			mux_2_v :='0';  
			mux_Z_v := '0';	   
			
			

		  	for i in 0 to 3 loop
				   reg_array(i) <=	 (others =>'0');
			end loop;
			

				 

			
		elsif (rising_edge(clk)) then
			

		
			reg_array(0) <= data_of_rs1;
			reg_array(1) <= data_of_rs2;
			reg_array(2) <= data_of_rs3;
			reg_array(3)(4 downto 0) <= control_for_ALU;	  
			webRegisters := wb_control_rd_input; 
			
			indexVari :=  index;
			signExtendVari := signExtend;  
			
			mux_1_v := mux_control_1;	   
			mux_2_v := mux_control_2;	  
			
			rs2V := rs2_c; 
			mux_Z_v := mux_control_Z;
			
		
		end if;	  
		
		
			data_of_rs1_ALU <= reg_array(0);
			data_of_rs2_ALU <= reg_array(1);
			data_of_rs3_ALU <= reg_array(2);
			control_for_ALU_out <= reg_array(3)(4 downto 0);	  
			wb_control_rd <= webRegisters;	 
			
			index_out <= indexVari;
		    signExtend_out <= signExtendVari; 
				 
			mux_control_1_out <= mux_1_v; 
			mux_control_2_out <= mux_2_v;  
			mux_control_Z_out <= mux_Z_v;
			rs2_out <= rs2V;
			
			
		
	  end process;	
	  

		 
	
	
end behavioral;

