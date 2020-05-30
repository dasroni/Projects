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


library ieee; 
use ieee.std_logic_1164.all; 
use ieee.std_logic_unsigned.all;
use ieee.numeric_std.ALL;


entity RegisterFile is		
	port(
		rset_bar : in std_logic;
	
		read_1 : in std_logic_vector(4 downto 0);		 --targetted registers
		read_2 : in std_logic_vector(4 downto 0);		 --to read from and write to
		read_3 : in std_logic_vector(4 downto 0);
		
		register_write : in std_logic;					 --control for write
		data_in : in std_logic_vector(127 downto 0);	 --data to be written
		reg_num : in std_logic_vector(4 downto 0);	 	 --the register number	
		
		
		out_1 : out  std_logic_vector(127 downto 0);
		out_2 : out  std_logic_vector(127 downto 0);
		out_3 : out  std_logic_vector(127 downto 0)  
		
		
	
	);
end RegisterFile;



architecture behavioral of RegisterFile is 

type registers is array (31 downto 0) of std_logic_vector(127 downto 0);   --register 

signal reg_array : registers;												-- singal is needed to access the array	 

begin 
	process(rset_bar, register_write,read_1,read_2,read_3,reg_num,data_in )
	begin
		if (rset_bar = '0') then
			
			for i in 0 to 31 loop							  
				reg_array(i) <= 	std_logic_vector(to_unsigned(i*2,128));
			end loop;
			out_1 <= (others => '0');	
			out_2 <= (others => '0');
			out_3 <= (others => '0');
		elsif( register_write = '1' ) then	
			
			reg_array(to_integer(unsigned(reg_num))) <= data_in;
			
-----
			
		if(	 ( reg_num = read_1 ) and ( reg_num = read_2 ) and( reg_num = read_3 )) then
		
			out_1 <= data_in;  
			out_2 <= data_in;  
			out_3 <= data_in;	
			
		elsif( ( reg_num = read_1 ) and ( reg_num = read_2 ))	then
			
			out_1 <= data_in;  
			out_2 <= data_in;  
			out_3 <= reg_array(to_integer(unsigned(read_3)));
		
		elsif(( reg_num = read_1 ) and( reg_num = read_3 ))	then
		
			out_1 <= data_in;  
			out_2 <= reg_array(to_integer(unsigned(read_2)));  
			out_3 <= data_in;	
			
		elsif(( reg_num = read_2 ) and( reg_num = read_3 ))	then
				
			out_1 <= reg_array(to_integer(unsigned(read_1))); 
			out_2 <= data_in;  
			out_3 <= data_in;
			
		
		elsif(( reg_num = read_1 ))	then
		
			out_1 <= data_in;  
			out_2 <= reg_array(to_integer(unsigned(read_2)));	
			out_3 <= reg_array(to_integer(unsigned(read_3)));			
			
			
		elsif(( reg_num = read_2 ))	then
			
			out_1 <= reg_array(to_integer(unsigned(read_1)));
			out_2 <= data_in;  
			out_3 <= reg_array(to_integer(unsigned(read_3)));
		
		elsif(( reg_num = read_3 ))	then
		
			out_1 <= reg_array(to_integer(unsigned(read_1)));	
			out_2 <= reg_array(to_integer(unsigned(read_2)));  
			out_3 <= data_in;	
		else
			
			out_1 <= reg_array(to_integer(unsigned(read_1)));	
			out_2 <= reg_array(to_integer(unsigned(read_2)));	
			out_3 <= reg_array(to_integer(unsigned(read_3)));	
		   end if;				
				
-----

		else	
			out_1 <= reg_array(to_integer(unsigned(read_1)));	
			out_2 <= reg_array(to_integer(unsigned(read_2)));	
			out_3 <= reg_array(to_integer(unsigned(read_3)));			
			
			end if;
	end process;
	


		
end behavioral;

