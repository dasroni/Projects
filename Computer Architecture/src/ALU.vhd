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

entity ALU is	
	port(
					  		     				   
		rset_bar : in std_logic;		                 --system Reset	   
		data_in_rs1 : in std_logic_vector(127 downto 0);
		data_in_rs2 : in std_logic_vector(127 downto 0);
		data_in_rs3 : in std_logic_vector(127 downto 0);	
		ALU_control : in std_logic_vector(4 downto 0);		 
		
		
		ALU_out : out  std_logic_vector(127 downto 0)
	);
end ALU;



architecture behavioral of ALU is 

type registers is array (64 downto 0) of std_logic_vector(24 downto 0);   --register 

signal reg_array : registers;			-- singal is needed to access the array	

signal ALU_calculation : std_logic_vector(127 downto 0); 

signal add_sub : std_logic;

signal max_min : std_logic;

signal mul_mode	: std_logic;		

signal one_zero : std_logic;
signal rot_shift : std_logic_vector(1 downto 0);

 

function r4Format(rs3 : std_logic_vector ; rs2: std_logic_vector; rs1 : std_logic_vector; length: integer; mode: std_logic_vector) return std_logic_vector is   
														 	
	variable max : integer := (2**(length-1))-1;								-- 32-bits-field max comparison
	variable min : integer := (2**(length-1))*(-1); 							-- 32-bits-field min comparison
	
	-- variable  max_long : integer :=	 9223372036854775807	; 
	-- varoabe   min_long : integer := -9223372036854775808
	
	variable result : integer ;	  												-- result for Integer type
	variable offset : integer := length/2 - 1;	   						  		-- offset to calculate arrya length
	
	variable max_std :  std_logic_vector(63 downto 0):= x"7FFFFFFFFFFFFFFF";	-- 64-bits-field max comparison
	variable min_std :  std_logic_vector(63 downto 0):= x"8000000000000000";	-- 64-bits-field min comparison
	variable result_std:std_logic_vector(length-1 downto 0);	 				-- result for Long type
	
	begin 	
		
		
		if(mode ="00") then 
			result := to_integer(signed(rs3(rs3'right+offset downto rs3'right)) * signed(rs2(rs2'right+offset downto rs2'right))) + to_integer(signed(rs1)); 	--ADD low	 
			result_std :=  std_logic_vector( signed (rs3(rs3'right+offset downto rs3'right)) * signed( rs2(rs2'right+offset downto rs2'right)) + signed( rs1));
			
		elsif(mode ="01") then
			result := to_integer(signed(rs3(rs3'left downto rs3'left-offset)) * signed(rs2(rs2'left downto rs2'left-offset))) + to_integer(signed(rs1)); 		   	  --ADD HIGH   
			result_std :=   std_logic_vector(signed(rs1) + signed(rs3(rs3'left downto rs3'left-offset)) * signed(rs2(rs2'left downto rs2'left-offset)));
		elsif(mode ="10") then
			result :=  to_integer(signed(rs1)) - to_integer(signed(rs3(rs3'right+offset downto rs3'right)) * signed(rs2(rs2'right+offset downto rs2'right))); 	   --SUB low  
			result_std :=  std_logic_vector(signed(rs1) - signed(rs3(rs3'right+offset downto rs3'right)) * signed(rs2(rs2'right+offset downto rs2'right))); 
		else 
			result := to_integer(signed(rs1)) - to_integer(signed(rs3(rs3'left downto rs3'left-offset)) * signed(rs2(rs2'left downto rs2'left-offset)));  				--SUB high
			result_std := std_logic_vector(signed(rs1) - signed(rs3(rs3'left downto rs3'left-offset)) * signed(rs2(rs2'left downto rs2'left-offset))); 
		 end if;
		 	 
		
		if( length = 32 and result <= min ) then
			return std_logic_vector(to_signed(min,32));
		elsif(length = 32 and result >= max)	then
			return  std_logic_vector(to_signed(max,32));
		elsif(length = 64 and signed(result_std) <= signed(min_std)) then
			return min_std;
		elsif(length = 64 and signed(result_std) >= signed(max_std)) then
			return max_std;
		else
			return result_std;
		end if;
		

	
	end r4Format;

--AHS/SFHS
function saturate 
(
	half_word1 : std_logic_vector(15 downto 0);
	half_word2 : std_logic_vector(15 downto 0);
	add_sub : std_logic) return std_logic_vector is
	variable output : std_logic_vector(15 downto 0); 
	variable added : std_logic_vector(15 downto 0);
	variable subbed : std_logic_vector(15 downto 0);
	variable upper_bound: integer := 32767;
	variable lower_bound: integer := -32768;
	begin 
		added := std_logic_vector(signed(half_word1) + signed(half_word2));
		subbed := half_word2 - half_word1;
		if(added < lower_bound) then
			added := std_logic_vector(to_signed(lower_bound, 16));			
		elsif(added > upper_bound) then
			added := std_logic_vector(to_signed(upper_bound, 16));
		end if;
		
		if (subbed < lower_bound) then
			subbed := std_logic_vector(to_signed(lower_bound, 16));
		elsif (subbed > upper_bound) then
			subbed := std_logic_vector(to_signed(upper_bound, 16));
		end if;	
		
		
		if add_sub = '1' then
			return added;
		else return subbed;
		end if;	
end function;

--function to count leading zeros
--CLZ
function CountZero
(
	word : std_logic_vector(31 downto 0)) return std_logic_vector is
	variable counter_out : std_logic_vector(31 downto 0);
	variable count : integer := 0;
	begin 
		for i in 31 downto 0 loop
				if word(i) = '0' then
					count := count + 1;
				else exit;	
				end if;
		end loop; 
		counter_out := 	std_logic_vector(to_unsigned(count, 32));		
		return counter_out;	
end function;

--POPCNTH
function CountOne
(
	word : std_logic_vector(15 downto 0)) return std_logic_vector is
	variable counter_out : std_logic_vector(15 downto 0);
	variable count : integer := 0;
	begin 
		
		for i in 15 downto 0 loop
			if word(i) = '1' then 
				count := count + 1;	
			end if;
		end loop;
		counter_out := 	std_logic_vector(to_unsigned(count, 16));		
		return counter_out;	
end function;


--MAX 
function maxSignedWord
(
	word1 : std_logic_vector(31 downto 0); 
	word2 : std_logic_vector(31 downto 0)) return std_logic_vector is
	variable max : std_logic_vector(31 downto 0);
	
	begin
		if(signed(word1) < signed(word2)) then
			max := std_logic_vector( signed(word2)); 	
		elsif(signed(word1) > signed(word2)) then
			max := std_logic_vector( signed(word1));	
		else max := std_logic_vector( signed(word1));	
		end if;	
		return max;
end function; 

--min signed word
function minSignedWord
(
	word1 : std_logic_vector(31 downto 0); 
	word2 : std_logic_vector(31 downto 0)) return std_logic_vector is
	variable min : std_logic_vector(31 downto 0);
	
	begin
		if(signed(word1) < signed(word2)) then
			min := std_logic_vector( signed(word1)); 	
		elsif(signed(word1) > signed(word2)) then
			min := std_logic_vector( signed(word2));	
		else min := std_logic_vector( signed(word1));	
		end if;	
		return min;
end function;


--MSGN 
function multiplySign
(
	word1 : std_logic_vector(31 downto 0); word2 : std_logic_vector(31 downto 0)) return std_logic_vector is 
	variable mul_out : std_logic_vector(31 downto 0);
	variable upper : integer := 2**31 - 1;
	variable lower : integer := -2**31;

begin	 
		if (to_integer(signed(word2)) < 0) then
			mul_out := std_logic_vector(to_signed(  to_integer(signed(word1)) * (-1), 32));	  
		else mul_out := word1;	
		end if;
		
		if mul_out < lower then
			mul_out := std_logic_vector(to_signed(lower, 32));
		elsif mul_out > upper then
			mul_out := std_logic_vector(to_signed(upper, 32));
		end if;	
		
	return mul_out;
end function;

--MPYU
function multiplyUnsigned
(
	word1 : std_logic_vector(31 downto 0); word2 : std_logic_vector(31 downto 0)
	) return std_logic_vector is 
	variable mul_out : std_logic_vector(31 downto 0);

	begin												 
		mul_out := std_logic_vector(word1(15 downto 0) * word2(15 downto 0)); 
	return mul_out;
end function;


--function to rotate bits
--ROT/ROTW
function rotate
(
	reg1 : std_logic_vector(127 downto 0);
	reg2 : std_logic_vector(127 downto 0)) return std_logic_vector is 
	variable  result : std_logic_vector(127 downto 0);
begin
		result := reg1;
		for i in 0 to to_integer(unsigned(reg2(6 downto 0)))-1 loop
			result := result(0) & result(127 downto 1);
		end loop;
		return result;
end function;

function rotateWord
(
	reg2 : std_logic_vector(127 downto 0)) return std_logic_vector is 
	variable  result : std_logic_vector(127 downto 0);
begin
		
		result := reg2;
		for i in 0 to to_integer(unsigned(reg2(4 downto 0)))-1 loop
			result(31 downto 0) := result(0) & result(31 downto 1);
		end loop;
		for i in 0 to to_integer(unsigned(reg2(35 downto 31)))-1 loop
			result(63 downto 32) := result(32) & result(63 downto 33);
		end loop;
		for i in 0 to to_integer(unsigned(reg2(68 downto 64)))-1 loop
			result(95 downto 64) := result(64) & result(95 downto 65);
		end loop;
		for i in 0 to to_integer(unsigned(reg2(99 downto 95)))-1 loop
			result(127 downto 96) := result(96) & result(127 downto 97);
		end loop;
		return result;
end function; 

function shiftleft
(
	reg1 : std_logic_vector(127 downto 0);
	reg2 : std_logic_vector(127 downto 0)) return std_logic_vector is 
	variable  result : std_logic_vector(127 downto 0);
begin

		result := reg1;
		for i in 0 to to_integer(unsigned(reg2(3 downto 0)))-1 loop
			result(15 downto 0) := result(14 downto 0) & '0';
		end loop;
		for i in 0 to to_integer(unsigned(reg2(19 downto 16)))-1 loop
			result(31 downto 16) := result(30 downto 16) & '0';
		end loop;
		for i in 0 to to_integer(unsigned(reg2(47 downto 44)))-1 loop
			result(47 downto 32) := result(46 downto 32) & '0';
		end loop;
		for i in 0 to to_integer(unsigned(reg2(63 downto 60)))-1 loop
			result(63 downto 48) := result(62 downto 48) & '0';
		end loop;
		for i in 0 to to_integer(unsigned(reg2(79 downto 76)))-1 loop
			result(79 downto 64) := result(79 downto 65) & '0';
		end loop;
		for i in 0 to to_integer(unsigned(reg2(95 downto 92)))-1 loop
			result(95 downto 80) := result(94 downto 80) & '0';
		end loop;
		for i in 0 to to_integer(unsigned(reg2(111 downto 108)))-1 loop
			result(111 downto 96) := result(110 downto 96) & '0';
		end loop;
		for i in 0 to to_integer(unsigned(reg2(127 downto 124)))-1 loop
			result(127 downto 112) := result(126 downto 112) & '0';
		end loop; 
		return result;
end function;


begin 

	p1: process(rset_bar,ALU_control,data_in_rs1,data_in_rs2,data_in_rs3)
	--LI format---
		variable index : integer := 0;	
		variable final_out : std_logic_vector(127 downto 0);  
		----------------------
	begin 
		if(rset_bar ='0') then
			ALU_out <= (others => '0');
		else
			
			case ALU_control is	
				
				when "00000" =>  
						index := to_integer(unsigned(data_in_rs2));
						ALU_out (index*16+15 downto index*16) <= data_in_rs3(20 downto 5); 
						final_out(index*16+15 downto index*16) :=  data_in_rs3(20 downto 5);   
			
						
			--------------
			----Format R3 : 4 words
				when "00001" =>
					  
				for i in 0 to 3 loop 
			  ALU_out(i*32+31 downto i*32) <= r4Format(data_in_rs3(i*32+31 downto i*32),data_in_rs2(i*32+31 downto i*32),data_in_rs1(i*32+31 downto i*32),32,"00" );
											
				end loop;
				
				when "00010" =>
				for i in 0 to 3 loop 
			    ALU_out(i*32+31 downto i*32) <= r4Format(data_in_rs3(i*32+31 downto i*32),data_in_rs2(i*32+31 downto i*32),data_in_rs1(i*32+31 downto i*32),32,"01" );
											
				end loop; 
				
				when "00011" =>
				for i in 0 to 3 loop 
			    ALU_out(i*32+31 downto i*32) <= r4Format(data_in_rs3(i*32+31 downto i*32),data_in_rs2(i*32+31 downto i*32),data_in_rs1(i*32+31 downto i*32),32,"10" );
											
				end loop; 
				
				when "00100" =>
				for i in 0 to 3 loop 
			    ALU_out(i*32+31 downto i*32) <= r4Format(data_in_rs3(i*32+31 downto i*32),data_in_rs2(i*32+31 downto i*32),data_in_rs1(i*32+31 downto i*32),32,"11" );									
				end loop; 
			---------------
			-----Format R3 4 words Ends
			
			
			
			--------------
			----Format R3 : 2 words 
				when "00101" =>
					 
				for i in 0 to 1 loop 
			   
				 ALU_out(i*64+63 downto i*64) <= r4Format(data_in_rs3(i*64+63 downto i*64),data_in_rs2(i*64+63 downto i*64),data_in_rs1(i*64+63 downto i*64),64,"00" );
											
				end loop;
				
				when "00110" =>
				for i in 0 to 1 loop 
			   ALU_out(i*64+63 downto i*64) <= r4Format(data_in_rs3(i*64+63 downto i*64),data_in_rs2(i*64+63 downto i*64),data_in_rs1(i*64+63 downto i*64),64,"01" );
											
				end loop; 
				
				when "00111" =>
				for i in 0 to 1 loop 
			   ALU_out(i*64+63 downto i*64) <= r4Format(data_in_rs3(i*64+63 downto i*64),data_in_rs2(i*64+63 downto i*64),data_in_rs1(i*64+63 downto i*64),64,"10" );
											
				end loop; 
				
				when "01000" =>
				for i in 0 to 1 loop 
			    ALU_out(i*64+63 downto i*64) <= r4Format(data_in_rs3(i*64+63 downto i*64),data_in_rs2(i*64+63 downto i*64),data_in_rs1(i*64+63 downto i*64),64,"11" );									
				end loop; 
			---------------
			-----Format R3 2 words Ends			
			
			
			
				
				
				when "01001" =>  --NOP  TESTED/WORKS
				ALU_out <= (others =>'0');
				
				when "01010" =>	  --A  TESTED/WORKS
				ALU_out(31 downto 0) <= data_in_rs1(31 downto 0) + data_in_rs2(31 downto 0);
				ALU_out(63 downto 32) <= data_in_rs1(63 downto 32) + data_in_rs2(63 downto 32);
				ALU_out(95 downto 64) <= data_in_rs1(95 downto 64) + data_in_rs2(95 downto 64);
				ALU_out(127 downto 96) <= data_in_rs1(127 downto 96) + data_in_rs2(127 downto 96);
				
				when "01011" =>  --AH  
				ALU_out(15 downto 0) <= data_in_rs1(15 downto 0) + data_in_rs2(15 downto 0);
				ALU_out(31 downto 16) <= data_in_rs1(31 downto 16) + data_in_rs2(31 downto 16);
				ALU_out(47 downto 32) <= data_in_rs1(47 downto 32) + data_in_rs2(47 downto 32);
				ALU_out(63 downto 48) <= data_in_rs1(63 downto 48) + data_in_rs2(63 downto 48);
				ALU_out(79 downto 64) <= data_in_rs1(79 downto 64) + data_in_rs2(79 downto 64);
				ALU_out(95 downto 80) <= data_in_rs1(95 downto 80) + data_in_rs2(95 downto 80);
				ALU_out(111 downto 96) <= data_in_rs1(111 downto 96) + data_in_rs2(111 downto 96);
				ALU_out(127 downto 112) <= data_in_rs1(127 downto 112) + data_in_rs2(127 downto 112);
				
				when "01100" => --AHS	//works
				add_sub <= '1'; --add
				ALU_out(15 downto 0) <= saturate( half_word1 => data_in_rs1(15 downto 0),
				half_word2 => data_in_rs2(15 downto 0), add_sub => add_sub);
				ALU_out(31 downto 16) <= saturate(half_word1 => data_in_rs1(31 downto 16),
				half_word2 => data_in_rs2(31 downto 16), add_sub => add_sub);
				ALU_out(47 downto 32) <= saturate(half_word1 => data_in_rs1(47 downto 32),
				half_word2 => data_in_rs2(47 downto 32), add_sub => add_sub);		
				ALU_out(63 downto 48) <= saturate(half_word1 => data_in_rs1(63 downto 48),
				half_word2 => data_in_rs2(63 downto 48), add_sub => add_sub);
				
				ALU_out(79 downto 64) <= saturate(half_word1 => data_in_rs1(79 downto 64),
				half_word2 => data_in_rs2(79 downto 64), add_sub => add_sub);
				ALU_out(95 downto 80) <= saturate(half_word1 => data_in_rs1(95 downto 80),
				half_word2 => data_in_rs2(95 downto 80), add_sub => add_sub);
				ALU_out(111 downto 96) <= saturate(half_word1 => data_in_rs1(111 downto 96),
				half_word2 => data_in_rs2(111 downto 96), add_sub => add_sub);
				ALU_out(127 downto 112) <= saturate(half_word1 => data_in_rs1(127 downto 112),
				half_word2 => data_in_rs2(127 downto 112), add_sub => add_sub);
				
				
				when "01101" =>	 --AND
				ALU_out <= (data_in_rs1 and data_in_rs2);
				
				when "01110" =>  --BCW	works
				ALU_out(31 downto 0) <= data_in_rs1(31 downto 0);
				ALU_out(63 downto 32) <= data_in_rs1(63 downto 32);
				ALU_out(95 downto 64) <= data_in_rs1(95 downto 64);
				ALU_out(127 downto 96) <= data_in_rs1(127 downto 96);
				
				when "01111" =>  --CLZ  //works
				one_zero <= '0';
				ALU_out(31 downto 0) <= CountZero(word => data_in_rs1(31 downto 0));
				ALU_out(63 downto 32) <= CountZero(word => data_in_rs1(63 downto 32));
				ALU_out(95 downto 64) <= CountZero(word => data_in_rs1(95 downto 64));
				ALU_out(127 downto 96) <= CountZero(word => data_in_rs1(127 downto 96));
				
				when "10000" =>  --MAX  //works
				ALU_out(31 downto 0) <= maxSignedWord(word1 => data_in_rs1(31 downto 0),
				word2 => data_in_rs2(31 downto 0));
				ALU_out(63 downto 32) <= maxSignedWord(word1 => data_in_rs1(63 downto 32),
				word2 => data_in_rs2(63 downto 32)); 
				ALU_out(95 downto 64) <= maxSignedWord(word1 => data_in_rs1(95 downto 64),
				word2 => data_in_rs2(95 downto 64));
				ALU_out(127 downto 96) <= maxSignedWord(word1 => data_in_rs1(127 downto 96),
				word2 => data_in_rs2(127 downto 96));
				
				
				when "10001" =>  --MIN  // works
				ALU_out(31 downto 0) <= minSignedWord(word1 => data_in_rs1(31 downto 0),
				word2 => data_in_rs2(31 downto 0));
				ALU_out(63 downto 32) <= minSignedWord(word1 => data_in_rs1(63 downto 32),
				word2 => data_in_rs2(63 downto 32)); 
				ALU_out(95 downto 64) <= minSignedWord(word1 => data_in_rs1(95 downto 64),
				word2 => data_in_rs2(95 downto 64));
				ALU_out(127 downto 96) <= minSignedWord(word1 => data_in_rs1(127 downto 96),
				word2 => data_in_rs2(127 downto 96));
				
				when "10010" =>  --MSGN
				mul_mode <= '1';
				ALU_out(31 downto 0) <= multiplySign(word1 => data_in_rs1(31 downto 0), 
				word2 => data_in_rs2(31 downto 0));
				ALU_out(63 downto 32) <= multiplySign(word1 => data_in_rs1(63 downto 32), 
				word2 => data_in_rs2(63 downto 32));
				ALU_out(95 downto 64) <= multiplySign(word1 => data_in_rs1(95 downto 64), 
				word2 => data_in_rs2(95 downto 64));
				ALU_out(127 downto 96) <= multiplySign(word1 => data_in_rs1(127 downto 96), 
				word2 => data_in_rs2(127 downto 96));
				
				when "10011" =>  --MPYU	 works
				mul_mode <= '0';
				ALU_out(31 downto 0) <= multiplyUnsigned(word1 => data_in_rs1(31 downto 0), 
				word2 => data_in_rs2(31 downto 0));
				ALU_out(63 downto 32) <= multiplyUnsigned(word1 => data_in_rs1(63 downto 32), 
				word2 => data_in_rs2(63 downto 32));
				ALU_out(95 downto 64) <= multiplyUnsigned(word1 => data_in_rs1(95 downto 64), 
				word2 => data_in_rs2(95 downto 64));
				ALU_out(127 downto 96) <= multiplyUnsigned(word1 => data_in_rs1(127 downto 96), 
				word2 => data_in_rs2(127 downto 96));
				
				
				
				when "10100" =>  --OR   works
				ALU_out <= (data_in_rs1 or data_in_rs2);
				
				
				when "10101" =>  --POPCNTH  works 

				ALU_out(15 downto 0) <= CountOne(word => data_in_rs1(15 downto 0));
				ALU_out(31 downto 16) <= CountOne(word => data_in_rs1(31 downto 16));
				ALU_out(47 downto 32) <= CountOne(word => data_in_rs1(47 downto 32));
				ALU_out(63 downto 48) <= CountOne(word => data_in_rs1(63 downto 48)); 
				ALU_out(79 downto 64) <= CountOne(word => data_in_rs1(79 downto 64));
				ALU_out(95 downto 80) <= CountOne(word => data_in_rs1(95 downto 80));
				ALU_out(111 downto 96) <= CountOne(word => data_in_rs1(111 downto 96));
				ALU_out(127 downto 112) <= CountOne(word => data_in_rs1(127 downto 112));
				
				
				when "10110" =>  --ROT  //works
				ALU_out <= rotate(reg1 => data_in_rs1, reg2 => data_in_rs2);
				
				when "10111" =>  --ROTW	 //works
				ALU_out <= rotateWord(reg2 => data_in_rs2);
				
				when "11000" =>  --SHLHI  //works
				ALU_out <= shiftLeft(reg1 => data_in_rs1, reg2 => data_in_rs2);
				
				when "11001" =>  --SFH
				ALU_out(15 downto 0) <= data_in_rs2(15 downto 0) - data_in_rs1(15 downto 0);
				ALU_out(31 downto 16) <= data_in_rs2(31 downto 16) - data_in_rs1(31 downto 16);
				ALU_out(47 downto 32) <= data_in_rs2(47 downto 32) - data_in_rs1(47 downto 32);		
				ALU_out(63 downto 48) <= data_in_rs2(63 downto 48) - data_in_rs1(63 downto 48) ;
				ALU_out(79 downto 64) <= data_in_rs2(79 downto 64) - data_in_rs1(79 downto 64);
				ALU_out(95 downto 80) <= data_in_rs2(95 downto 80) - data_in_rs1(95 downto 80);
				ALU_out(111 downto 96) <= data_in_rs2(111 downto 96) - data_in_rs1(111 downto 96);
				ALU_out(127 downto 112) <= data_in_rs2(127 downto 112) - data_in_rs1(127 downto 112);  
				--std_logic_vector(signed())
				when "11010" =>  --SFW
				ALU_out(31 downto 0) <= data_in_rs2(31 downto 0) - data_in_rs1(31 downto 0) ;
				ALU_out(63 downto 32) <= data_in_rs2(63 downto 32) - data_in_rs1(63 downto 32) ;
				ALU_out(95 downto 64) <= data_in_rs2(95 downto 64) - data_in_rs1(95 downto 64) ;
				ALU_out(127 downto 96) <= data_in_rs2(127 downto 96) - data_in_rs1(127 downto 96) ;
				
				when "11011" =>  --SFHS
				add_sub <= '0';
				ALU_out(15 downto 0) <= saturate(half_word1 => data_in_rs1(15 downto 0),
				half_word2 => data_in_rs2(15 downto 0),add_sub => add_sub);
				ALU_out(31 downto 16) <= saturate(half_word1 => data_in_rs1(31 downto 16),
				half_word2 => data_in_rs2(31 downto 16),add_sub => add_sub);
				ALU_out(47 downto 32) <= saturate(half_word1 => data_in_rs1(47 downto 32),
				half_word2 => data_in_rs2(47 downto 32),add_sub => add_sub);		
				ALU_out(63 downto 48) <= saturate(half_word1 => data_in_rs1(63 downto 48),
				half_word2 => data_in_rs2(63 downto 48),add_sub => add_sub);
				
				ALU_out(79 downto 64) <= saturate(half_word1 => data_in_rs1(79 downto 64),
				half_word2 => data_in_rs2(79 downto 64),add_sub => add_sub);
				ALU_out(95 downto 80) <= saturate(half_word1 => data_in_rs1(95 downto 80),
				half_word2 => data_in_rs2(95 downto 80),add_sub => add_sub);
				ALU_out(111 downto 96) <= saturate(half_word1 => data_in_rs1(111 downto 96),
				half_word2 => data_in_rs2(111 downto 96),add_sub => add_sub);
				ALU_out(127 downto 112) <= saturate(half_word1 => data_in_rs1(127 downto 112),
				half_word2 => data_in_rs2(127 downto 112),add_sub => add_sub);
				
				when "11100" =>  --XOR
				ALU_out <= data_in_rs1 xor data_in_rs2;
				
				
				when others => ALU_out <= (others => '0');
			 end case;	
			end if;	
	end process p1;	
end behavioral;

