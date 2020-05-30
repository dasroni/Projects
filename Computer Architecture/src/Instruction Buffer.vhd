										-------------------------------------------------------------------------------
--
-- Title       : RegisterFile
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


entity instructionBuffer is		

	port(
					  		  
		rset_bar : in std_logic;		                 --system Reset	   
	
		counter_in : in std_logic_vector(5 downto 0);
		instructionOut : out  std_logic_vector(24 downto 0)
	);
end instructionBuffer;



architecture behavioral of instructionBuffer is 

type registers is array (64 downto 0) of std_logic_vector(24 downto 0);   --register 

signal reg_array : registers;			-- singal is needed to access the array	


begin 
	
	process(rset_bar,counter_in)	 	
	
	---For File Input SETUP----
	File f: text;
	constant filename : string :="instructionsOutput.txt";
	Variable L : Line;
	variable i : integer :=0;
	variable b: std_logic_vector(24 downto 0);	   
	-----------------------------
	
	
	begin
		if(rset_bar ='0') then
			instructionOut <= (others =>'0'); 			
			
			--File read----
			
		file_open(f,filename,read_mode);
		while((i<=63) and (not endfile(f))) loop
			readline(f,l);
			read(l,b);	
			reg_array(i) <= b;
			i := i+1;
		end loop;
			
		
		
		while(i<=63) loop
			reg_array(i) <= "1100000000000000000000000"; 
			i := i+1;
		end loop;
		file_close(f);		
			
			----------------										  
		end if;	 
		
		instructionOut <= reg_array(to_integer(unsigned(counter_in)));	
		
	  end process;	
	  

		 
	
	
end behavioral;

