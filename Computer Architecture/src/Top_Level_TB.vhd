-------------------------------------------------------------------------------
--
-- Title       : IF_ID_TB
-- Design      : SIMD
-- Author      : Asif
-- Company     : Stony Brook University
--
-------------------------------------------------------------------------------
--
-- File        : C:\Users\Asif\OneDrive\SBU\Sixth Semester\ESE345\Project\VHDL\Pipelined_SIMD_Multimedia_Unit\src\RegisterFile_TB.vhd
-- Generated   : Sat Nov 23 06:47:06 2019
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
--{entity {RegisterFile_TB} architecture {RegisterFile_TB}}

library ieee; 
use ieee.std_logic_1164.all; 
use ieee.std_logic_unsigned.all;
use ieee.numeric_std.ALL;	
use STD.textio.all;
use ieee.std_logic_textio.all; 


 

entity Top_Level is	
		
end Top_Level;

--}} End of automatically maintained section

architecture behavioral_TB of Top_Level is	


--------Functions to write one TEXT file--------------------

	function chr(sl: std_logic) return character is
		variable c: character;
		begin
		case sl is
		when 'U' => c:= 'U';
		when 'X' => c:= 'X';
		when '0' => c:= '0';
		when '1' => c:= '1';
		when 'Z' => c:= 'Z';
		when 'W' => c:= 'W';
		when 'L' => c:= 'L';
		when 'H' => c:= 'H';
		when '-' => c:= '-';
		end case;
	return c;
	end chr;


	function str(slv: std_logic_vector) return string is
		variable result : string (1 to slv'length);
		variable r : integer;
		begin
			r := 1;
			for i in slv'range loop
			result(r) := chr(slv(i));
			r := r + 1;
			end loop;
		return result;
	end str;

 ------------------------------------------------------------
	
	
	
--input signals	 to System
signal clk, rset_bar: std_logic;   


--For Instruction Buffer---	 
signal      data_in : std_logic_vector(24 downto 0);	
signal 		data_in_ext : std_logic_vector(18 downto 0);




		--For RegisterFile
	
signal		read_1 :  std_logic_vector(4 downto 0);		 --targetted registers
signal		read_2 :  std_logic_vector(4 downto 0);		 --to read from and write to
signal		read_3 : std_logic_vector(4 downto 0);
		


---- Control Unit-------
signal	instruction : std_logic_vector(24 downto 0);



--- ALU signals-----
   
signal		data_in_rs1 :  std_logic_vector(127 downto 0);
signal		data_in_rs2 :  std_logic_vector(127 downto 0);
signal		data_in_rs3 : std_logic_vector(127 downto 0);
signal		ALU_control : std_logic_vector(4 downto 0);
		




-----ID/EX Buffer-----
		---For Buffer---
signal		data_of_rs1 : std_logic_vector(127 downto 0);	   
signal		data_of_rs2 :  std_logic_vector(127 downto 0);	
signal		data_of_rs3 : std_logic_vector(127 downto 0);
signal		control_for_ALU: std_logic_vector(4 downto 0);
signal		wb_control_rd_input : std_logic_vector(5 downto 0);	   



signal			index : std_logic_vector(127 downto 0);
signal			signExtend : std_logic_vector(127 downto 0);	   
		
signal			mux_control_1 : std_logic;
signal			mux_control_2 : std_logic;	 
signal			mux_control_Z : std_logic;


signal		index_out : std_logic_vector(127 downto 0);
signal		signExtend_out : std_logic_vector(127 downto 0); 
		
signal		mux_control_1_out : std_logic; 
signal		mux_control_2_out : std_logic; 
signal		mux_control_Z_out : std_logic; 
		
		


-----------EX/WB Buffer-------------
		
signal		ALU_computation : std_logic_vector(127 downto 0);
signal		WB_input : std_logic_vector(5 downto 0);

		
		----WriteBackData
signal		data_WB_out: std_logic_vector(127 downto 0);
signal		WB_register_rd: std_logic_vector(4 downto 0);
		
		--writeBackControl---
signal		register_write_inter :std_logic; 



-----------IF Register-------------
			
signal		counter : std_logic_vector(5 downto 0);		   




---SIGNALS FOR FORWARDS UNITE----
				

signal 		data_for_ALU_1 : 	std_logic_vector(127 downto 0);	
signal 		data_for_ALU_2 : 	std_logic_vector(127 downto 0);
signal 		data_for_ALU_3 : 	std_logic_vector(127 downto 0);	 

signal 		mux_out_A :  std_logic_vector(127 downto 0);	
signal 		mux_out_B :  std_logic_vector(127 downto 0);  
signal 		mux_out_Z :  std_logic_vector(127 downto 0);

signal 		rs2 : std_logic_vector(127 downto 0); 
signal		rs2_out: std_logic_vector(127 downto 0); 

	
	

constant period : time :=100 ns;
 
begin		 
	
		
	U_IFRegister : entity IF_Reg
		port map (clk=> clk , rset_bar => rset_bar, counter => counter);
	
	
	U_Forward: entity DATA_FW
		port map(rset_bar => rset_bar, data_of_rs1=> data_of_rs1,data_of_rs2 => data_of_rs2,data_of_rs3 => data_of_rs3,
		register_num_1 => read_1, register_num_2 => read_2,register_num_3 => read_3, address_WB_of_rd => WB_input,  ALU_calculation_for_FW => ALU_computation,
		fw_for_registers1 => data_in_rs1, fw_for_registers2 => data_in_rs2, fw_for_registers3 => data_in_rs3 
		
		);
		
	
	U_INSTRICTION_BUFFER: entity instructionBuffer
		port map( rset_bar => rset_bar,  counter_in => counter, instructionOut => data_in);
	
	U_IFID: entity IF_ID
		port map(clk => clk, rset_bar => rset_bar, data_in => data_in , read_rs1 =>read_1,
		read_rs2 =>read_2,read_rs3 =>read_3, signExtend => data_in_ext, control_in => instruction);		----- Change Read_rd , should come for WB stage???
			
	U_RegisterFile : entity RegisterFile
		port map(rset_bar => rset_bar, read_1 => read_1, read_2 => read_2, read_3 => read_3,register_write => register_write_inter, data_in => data_WB_out,
				 reg_num => WB_register_rd, out_1 => data_of_rs1, out_2=> data_of_rs2, out_3 => data_of_rs3);
	
				 			 
	U_Control : entity Control
	port map (rset_bar => rset_bar, instruction => instruction, control_out => control_for_ALU, control_out_WB => wb_control_rd_input,
				mux_select_1 => mux_control_1,mux_select_2 =>mux_control_2, mux_select_Z => mux_control_z );
	
	
	U_SignExtend_Index: entity signIndex 															   
	port map(rset_bar => rset_bar, data_in_ext => data_in_ext, index => index, signExtendOutput => signExtend, rs2 => rs2 );
	
	
	 U_ID_EX_BUFFER: entity ID_EX
	   port map(clk => clk, rset_bar => rset_bar, data_of_rs1 => data_in_rs1,data_of_rs2 => data_in_rs2,data_of_rs3 =>data_in_rs3, control_for_ALU => control_for_ALU,
				wb_control_rd_input => wb_control_rd_input, wb_control_rd => WB_input,  
				data_of_rs1_ALU => data_for_ALU_1, data_of_rs2_ALU => data_for_ALU_2, data_of_rs3_ALU =>data_for_ALU_3, control_for_ALU_out => ALU_control,
				index => index, signExtend => signExtend, mux_control_1 => mux_control_1, mux_control_2 => mux_control_2, index_out => index_out,
				signExtend_out => signExtend_out, mux_control_1_out => mux_control_1_out, mux_control_2_out => mux_control_2_out, rs2_c => rs2, rs2_out => rs2_out,
				mux_control_Z => mux_control_z, mux_control_Z_out => mux_control_Z_out 
				
				);	
				
	U_MUX_A : entity mux_2to1
		port map(rset_bar => rset_bar, data_rs => data_for_ALU_2, data_buff => mux_out_Z, cs => mux_control_1_out , mux_out => mux_out_A);	
		
	U_MUX_B : entity mux_2to1
		port map(rset_bar => rset_bar, data_rs => data_for_ALU_3, data_buff => signExtend_out, cs => mux_control_2_out , mux_out => mux_out_B);	  
		
		
		
	U_MUX_Z : entity mux_2to1
		port map(rset_bar => rset_bar, data_rs => index_out, data_buff => rs2_out, cs => mux_control_Z_out , mux_out => mux_out_Z);	
		
	
	U_Multimedia_ALU : entity ALU
		port map(rset_bar => rset_bar, data_in_rs1 => data_for_ALU_1, data_in_rs2 => mux_out_A, data_in_rs3 => mux_out_B,
		ALU_control => ALU_control, ALU_out => ALU_computation); 
		
		
		U_EX_WB_buffer: entity EX_WB 
		  port map (clk=> clk, rset_bar => rset_bar, ALU_computation => ALU_computation, WB_input => WB_input, data_WB_out => data_WB_out, WB_register_rd => WB_register_rd,
		  			  register_write => register_write_inter);

	process	 
	
	

	
	
			---For File Input SETUP----
	File outfile: text;
	--constant filename_write : string :="Results.txt";			
	variable f_status: 	FILE_OPEN_STATUS;
	Variable L : Line;	
	Variable L_A: Line;
	Variable L_B: Line;
	
	variable j : integer :=0;
	variable b: std_logic_vector(24 downto 0);	 
	
	variable msg : string(442 downto 1);
	
	variable instructuionW: string(25 downto 1);
	variable register_A: string(5 downto 1);
	variable register_B: string(5 downto 1);
	variable register_C: string(5 downto 1);
	
	variable content_A: string(127 downto 1); 
	variable content_B: string(127 downto 1);
	variable content_C: string(127 downto 1);
	variable ALU_CalculationW: string(127 downto 1);  
	variable Final_data_Out: string(127 downto 1);
	variable destination_R : string(5 downto 1);
	
	
	

	
	-----------------------------	  
    variable max : integer := 2**(32-1)-1;
	variable min : std_logic_vector(23 downto 0):=(others=>'1');   
	
	

	
	
	begin	  
		
	  	
		
		---FILE Write---
		file_open(f_status,outfile,"Results.txt",write_mode);
		write(L,string'("::::Instructions::::       -<rs3>- -<r2>- -<rs1>-   ::Content::  -<r3>- -<r2>- -<r1>-  ::Control::   ::ALU_Calculation::   :::FINAL_DATA:::    -<rd>- "));
		writeLine(outfile,L);
		write(L,string'("====================   =============================   ===========  ====================  ===========   ===================   ================    ====== "));
		writeLine(outfile,L); 
		write(L,string'("                                                                                                                                                         "));
		writeLine(outfile,L);	 		
		write(L,string'("                                                                                                                                                         "));
		writeLine(outfile,L);	
			  
				
	

	-----------------------------
		
		
	    rset_bar <= '0', '1' after 90ns;	
		clk <='0';	
		
		
		for i in 0 to 512 loop
			
			wait for period/2; 
			
			if (i mod 2 = 0) then
			msg := str(data_in) & " "& str(read_3) & " " &  str(read_2) & " "& str(read_1) & " " & str(data_of_rs1) & " "& str( control_for_ALU) & " " & str( ALU_computation) & " "& str(data_WB_out) & " " & str(WB_register_rd);
			write(L,msg);	
			writeLine(outfile,L);
			end if;	
		
			clk <= not clk;	  
		end loop;
	
		file_close(outfile);
	
	wait;
	end process;
 

	   
	   
end behavioral_TB;
