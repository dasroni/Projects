											   									-------------------------------------------------------------------------------
--
-- Title       : FrequencyRegister
-- Design      : DDS_with_Frequency_Selection

-- Author      : Asif Iqbal => ID: 110333685	 
--			   : Roni Das   => ID: 108378223  

-- Company     : Stony Brook University
--
-------------------------------------------------------------------------------
--
-- File        : C:\Users\Asif\OneDrive\SBU\Fifth Semester\ESE382\Labs\LAB10\lab10\src\FrequencyRegister.vhd
-- Generated   : Wed Apr 24 01:47:09 2019
-- From        : interface description file
-- By          : Itf2Vhdl ver. 1.22
--
-------------------------------------------------------------------------------
--
-- Description : The design description below is for a register. It is specific
--				 to this Lab design. It holds the input data. The data is 
--				 outputted at the rising clk edge if only the enable load is high.
--				 The system has an asynchronous reset.
-- 
-- Input:  load, clk, reset_bar; 		TYPE: STD_LOGIC;
-- Input:  d;							TYPE: STD_LOGIC_VECTOR;
-- Output: q;							TYPE: STD_LOGIC_VECTOR;
--
-- Lab10: Task_2			Bench:02		Section: 02
-------------------------------------------------------------------------------



library ieee;
use ieee.std_logic_1164.all;

entity frequency_reg is
generic (a : positive := 14);
	port(
		load : in std_logic; -- enable register to load data
		clk : in std_logic; -- system clock
		reset_bar : in std_logic; -- active low asynchronous reset
		d : in std_logic_vector(a-1 downto 0); -- data input
		q : out std_logic_vector(a-1 downto 0) -- register output
		);
end frequency_reg;

architecture freque of frequency_reg is
begin
	process(clk, load, reset_bar)
	begin
		if reset_bar = '0' then
			q <= (others => '0');
		elsif rising_edge(clk) then
			if load = '1' then
				q <= d;
			end if;		
		end if;
	end process;
end freque;
