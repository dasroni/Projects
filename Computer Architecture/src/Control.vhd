-------------------------------------------------------------------------------
--
-- Title       : MultimediaALU
-- Design      : SIMD
-- Author      : Asif
-- Company     : Stony Brook University
--
-------------------------------------------------------------------------------
--
-- File        : C:\Users\colle\OneDrive\SBU\Sixth Semester\ESE345\Project\VHDL\Pipelined_SIMD_Multimedia_Unit\src\MultimediaALU.vhd
-- Generated   : Sat Nov 23 20:40:10 2019
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


entity Control is
	port(	   
	rset_bar : in std_logic;
	instruction : in std_logic_vector(24 downto 0);	  

	
	mux_select_1 : out std_logic;
	mux_select_2 : out std_logic;  
	mux_select_Z : out std_logic;
	
	control_out : out std_logic_vector(4 downto 0);
	control_out_WB: out std_logic_vector(5 downto 0)
	
	);
end Control;


architecture behave of Control is  
  

begin  			
	process(rset_bar,instruction) 
	begin  
		if (rset_bar ='0') then	  
					
					control_out <= (others => '0');
					control_out_WB <= (others => '0'); 
					mux_select_1 <= '0';
					mux_select_2 <= '0';
					mux_select_Z <= '0';
					
		else
			
					control_out <= (others => '0');
					control_out_WB (5)<= '1';
					control_out_WB (4 downto 0) <= instruction(4 downto 0);	  
					mux_select_1 <= '0';
					mux_select_2 <= '0'; 
					mux_select_Z <= '0';
				
				if (instruction(24 downto 23) = "00" or 
					instruction(24 downto 23) = "01")   then
					control_out <= std_logic_vector(to_unsigned(0,5)); --ldi 
					mux_select_1 <= '1';
					mux_select_2 <= '1';
					
				elsif instruction(24 downto 23) = "10" then
					case instruction(22 downto 20) is
						when "000" => control_out <=	std_logic_vector(to_unsigned(1,5)); --SIMALS
						when "001" => control_out <= std_logic_vector(to_unsigned(2,5));	--SIMAHS
						when "010" => control_out <= std_logic_vector(to_unsigned(3,5));	--SIMSLS
						when "011" => control_out <= std_logic_vector(to_unsigned(4,5));	--SIMSHS
						when "100" => control_out <= std_logic_vector(to_unsigned(5,5)); --SLIMALS
						when "101" => control_out <= std_logic_vector(to_unsigned(6,5)); --SLIMAHS
						when "110" => control_out <= std_logic_vector(to_unsigned(7,5));	--SLIMSLS
						when "111" => control_out <= std_logic_vector(to_unsigned(8,5));	--SLIMSHS
						when others => control_out <= "ZZZZZ";
					end case;
					
				elsif instruction(24 downto 23) = "11" then 
					case instruction(19 downto 15) is	
						when "00000" => control_out <= std_logic_vector(to_unsigned(9,5)); --NOP	 
									    control_out_WB (5)<= '0';
						when "00001" => control_out <= std_logic_vector(to_unsigned(10,5)); --A
						when "00010" => control_out <= std_logic_vector(to_unsigned(11,5)); --AH
						when "00011" => control_out <= std_logic_vector(to_unsigned(12,5)); --AHS
						when "00100" => control_out<= std_logic_vector(to_unsigned(13,5)); --AND
						when "00101" => control_out <= std_logic_vector(to_unsigned(14,5)); --BCW
						when "00110" => control_out <= std_logic_vector(to_unsigned(15,5)); --CLZ
						when "00111" => control_out <= std_logic_vector(to_unsigned(16,5)); --MAX
						when "01000" => control_out <= std_logic_vector(to_unsigned(17,5)); --MIN
						when "01001" => control_out <= std_logic_vector(to_unsigned(18,5)); --MSGN
						when "01010" => control_out <= std_logic_vector(to_unsigned(19,5)); --MPYU
						when "01011" => control_out <= std_logic_vector(to_unsigned(20,5)); --OR
						when "01100" => control_out <= std_logic_vector(to_unsigned(21,5)); --POPCNTN
						when "01101" => control_out <= std_logic_vector(to_unsigned(22,5)); --ROT
						when "01110" => control_out <= std_logic_vector(to_unsigned(23,5)); --ROTW
						when "01111" => control_out <= std_logic_vector(to_unsigned(24,5)); --SHLHI
										mux_select_Z<='1';
						when "10000" => control_out <= std_logic_vector(to_unsigned(25,5)); --SFH
						when "10001" => control_out <= std_logic_vector(to_unsigned(26,5)); --SFW
						when "10010" => control_out <= std_logic_vector(to_unsigned(27,5)); --SFHS
						when "10011" => control_out <= std_logic_vector(to_unsigned(28,5)); --XOR
						when others  => control_out <= "ZZZZZ";
					end case;	
				end if;	
			end if;
	end process; 

end behave;
