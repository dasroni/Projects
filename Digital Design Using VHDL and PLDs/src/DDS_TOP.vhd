									   -------------------------------------------------------------------------------
--
-- Title       : DDS System with Frequency Selection
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
-- Description : The design description below is for top Level of 
--				DDS system code in structural style. 
-- 
-- Input:  load_frq, clk, reset_bar; 		 	TYPE: STD_LOGIC;
-- Input:  freq_val;							TYPE: STD_LOGIC_VECTOR;	
--
-- Output: dac_sine_value;						TYPE: STD_LOGIC_VECTOR;	
--		     pos_sine;						    TYPE: STD_LOGIC_VECTOR;
--
-- Lab10: Task_6			Bench:02		Section: 02
-------------------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;   	
use ieee.numeric_std.all;



		entity dds_w_freq_select is
			generic (a : positive := 14; m : positive := 7);
					port(
						ws1 : in std_logic;
						ws0 : in std_logic;
						clk : in std_logic;-- system clock
						reset_bar : in std_logic;-- asynchronous reset
						freq_val : in std_logic_vector(a - 1 downto 0);-- selects frequency	  Load Value
						load_freq : in std_logic;-- pulse to load a new frequency selection	  Load Time
						dac_value : out std_logic_vector(7 downto 0);-- output to DAC
						pos_sine : out std_logic-- positive half of sine wave cycle
						);	
	attribute loc : string;
	attribute loc of clk : signal is "J1";
	attribute loc of reset_bar : signal is "F1";
	attribute loc of freq_val : signal is 
                   "A13,F8,C12,E10,F9,E8,E7,D7,C5,E6,A10,D9,B6,B5";
	attribute loc of load_freq : signal is "C2";
	attribute loc of ws1 : signal is "C7";
	attribute loc of ws0 : signal is "D8";
	attribute loc of dac_value : signal is 
			 "C4,F7,B9,B7,A5,A4,A3,D6"; 
	attribute loc of pos_sine : signal is "B4";

		end dds_w_freq_select; 
		
		
		
		
	   architecture structural of  dds_w_freq_select is	 	
	   
	   
	   
	   
	   --output Edge Dect
	   signal sig_edge : std_logic;
	   
	   -- output f-Reg.
	   signal reg_out : std_logic_vector(a-1 downto 0); 
	   
	   
	   
	   signal max_A , min_A , up :std_logic; 
	   
	   signal addr, sine_val : std_logic_vector(m-1 downto 0); 	
	   
	   --logic Signal;
	   signal log_val_sine : std_logic_vector(m-1 downto 0); 	
	   signal log_val_table : std_logic_vector(m-1 downto 0); 

	   --output ACC_FSM
	   signal accOut1, accOut2 : std_logic;	 
	   
	   --Local Tri_Sq Output;
	   signal tri_sq : std_logic_vector(m-1 downto 0);	
	   
	   --Local Final Output:
	   signal finalOut1 : std_logic_vector(m downto 0);	
	   signal finalOut2 : std_logic_vector(m downto 0);
	   
	   
	   begin 
		   
		   
		edge_detector_FSM :	entity edge_det 
				port map(rst_bar => reset_bar, clk => clk, sig => load_freq, pos => '1', sig_edge => sig_edge );
		
		frequency_reg :	 entity frequency_reg
			
				port map (load => sig_edge , clk => clk, reset_bar=> reset_bar, d => freq_val, q => reg_out );
		
		
	  phaseaccumulator : entity  phase_accumulator  
		 	port map (clk => clk, reset_bar => reset_bar, up => accOut1, d => reg_out , max => max_A , min => min_A, q => addr );
		
		
		
	   phase_accumulator_FSM : entity phase_accumulator_fsm  
		port map (clk=> clk, reset_bar => reset_bar, max => max_A , min => min_A , up => accOut1 , pos => accOut2 );
		
		
		
	    sine_lookup_table : entity sine_table 
		port map (addr => addr, sine_val => log_val_sine); 

		
		
		Triangle_Square : entity triangle_square
			port map(ws1=>ws1, ws0=>ws0, addr=>addr , table_value => log_val_table );	
			
		adder_subtracter : entity adder_subtracter
		port map ( pos => accOut2 , sine_value => sine_val, dac_sine_val => dac_value);
	 
		
	
		sine_val <= log_val_sine when std_logic_vector'(ws1,ws0) ="01" else log_val_table;
		pos_sine <= accOut2;	
		   
	 end  structural;
		
		
		
		
		
		
		
		
		
		
		
		
		