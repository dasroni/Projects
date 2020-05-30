//-----------------------------------------------------------------------------
//
// Title       : RD_SIPO
// Design      : RD_Bonus
// Author      : Roni Das
// Company     : Stony Brook University
//
//-----------------------------------------------------------------------------
//
// File        : C:\Users\RONIDAS\Desktop\HDL\Bonus\RD_Bonus\src\RD_SIPO.v
// Generated   : Mon Apr 30 02:45:39 2018
// From        : interface description file
// By          : Itf2Vhdl ver. 1.22
//
//-----------------------------------------------------------------------------
//
// Description : 
//
//-----------------------------------------------------------------------------
`timescale 1 ns / 1 ps

//{{ Section below this comment is automatically maintained
//   and may be overwritten
//{module {RD_SIPO}}

module RD_SIPO  ( CLK ,SerIn ,Q ,CLRbar );


  

output [9:0] Q ;
wire [9:0] Q ;

input CLK ;
wire CLK ;
input SerIn ;
wire SerIn ;
input CLRbar ;
wire CLRbar ;

//}} End of automatically maintained section
reg [9:0] Qtmp; 
assign Q = Qtmp ; 
always @ (posedge CLK or posedge CLRbar) 
		if (CLRbar)  Qtmp <= 10'b0 ; 
		else Qtmp <= {Qtmp [8:0] , SerIn} ; 		 
			
endmodule
