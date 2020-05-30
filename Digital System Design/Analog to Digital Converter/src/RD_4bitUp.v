//-----------------------------------------------------------------------------
//
// Title       : RD_4bitUp
// Design      : RD_Bonus
// Author      : Roni Das
// Company     : Stony Brook University
//
//-----------------------------------------------------------------------------
//
// File        : C:\Users\RONIDAS\Desktop\HDL\Bonus\RD_Bonus\src\RD_4bitUp.v
// Generated   : Mon Apr 30 02:47:29 2018
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
//{module {RD_4bitUp}}

module RD_4bitUp (Clr,En, CLK, Q);
input Clr;	
input En;
input CLK;

output [3:0]  Q;
reg [3:0] Q;   


always @(posedge CLK or posedge Clr)
	
	begin
	if (Clr)
		Q <= 4'b0;
		
	else if (En)
		Q <= Q + 1'b1;
		
			 
	end

	
endmodule
