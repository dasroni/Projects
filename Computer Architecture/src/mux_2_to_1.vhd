														   --------------------------------------------------------------------------
--
-- Title       : D-ff
-- Design      : lab07	   
--
-- Author      : Asif Iqbal => ID: 110333685	 
--			   : Roni Das   => ID: 108378223	
--
-- Company     : Stony Brook
--
---------------------------------------------------------------------------
--
-- File        : C:\Users\Asif\OneDrive\SBU\Fifth Semester\ESE382\Labs\LAB07\LAB07\src\D-ff.vhd
-- Generated   : Tue Mar  26 14:57:59 2019
-- From        : interface description file
-- By          : Itf2Vhdl ver. 1.22
--
---------------------------------------------------------------------------
--
-- Description :The code below is for a D-Flip-Flop memory element. Using
--				Behavioral style coding dff is described. During every
--				rising clock edge qff clocks in values on data line d. 
--				
-- Input: d, clk; Type: STD_LOGIC;
-- Output: qff; Type: STD_LOGIC;

-- Lab07: Task1			Bench:02		Section: 02
----------------------------------------------------------------------------

--{{ Section below this comment is automatically maintained
--   and may be overwritten
--{entity {therm2gray} architecture {therm2grayIf}}


library IEEE;
use IEEE.std_logic_1164.all;
use ieee.numeric_std.all;


entity mux_2to1 is
	port( 
		rset_bar : in std_logic;
		data_rs : in std_logic_vector(127 downto 0); -- data input channels  
		data_buff: in std_logic_vector(127 downto 0); -- data input channels		  
		
		cs : in std_logic; -- mux select inputs
		mux_out : out std_logic_vector(127 downto 0) -- output of mux
		);
end mux_2to1;


architecture behavioral of mux_2to1 is
	begin  
	   
		process (rset_bar,cs,data_rs,data_buff)
		begin 
			if(rset_bar ='0') then 
				mux_out <= (others =>'0');
			
			else
				
			case cs is 		
				when '1'  => mux_out <= data_buff;
				when '0'  => mux_out <= data_rs;
				when others => mux_out <= data_rs;
	 		
			end case;
			end if; 
		 end process;
			
		

end behavioral;
