//-----------------------------------------------------------------------------
//
// Title       : RD_8bitUp
// Design      : RD_Prelab12
// Author      : Roni Das
// Company     : Stony Brook University
//
//-----------------------------------------------------------------------------
//
// File        : C:\Users\RONIDAS\Desktop\HDL\Prelab12\RD_Prelab12\src\RD_8bitUp.v
// Generated   : Sun Apr 29 01:46:08 2018
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
//{module {RD_8bitUp}}

//}} End of automatically maintained section   



module RD_8bitUp (Clr,En, CLK, Q);
input Clr;	   
wire Clr;
input En;
wire En;
input CLK;
wire CLK;

output [7:0]  Q;   
wire [7:0] Q;
reg [7:0] Qtmp;   

assign Q = Qtmp;
always @(posedge CLK or posedge Clr)
	
	begin
	if (Clr)
		Qtmp <= 8'b0;
		
	else if (En)
		Qtmp <= Qtmp + 1'b1;
		
			 
	end

endmodule