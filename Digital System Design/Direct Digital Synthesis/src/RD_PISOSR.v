//-----------------------------------------------------------------------------
//
// Title       : RD_PISOSR
// Design      : RD_Prelab12
// Author      : Roni Das
// Company     : Stony Brook University
//
//-----------------------------------------------------------------------------
//
// File        : C:\Users\RONIDAS\Desktop\HDL\Prelab12\RD_Prelab12\src\RD_PISOSR.v
// Generated   : Sun Apr 29 05:05:24 2018
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
//{module {RD_PISOSR}}

module RD_PISOSR ( D ,Ser_in ,ParL_Ctrl ,Clk ,Q11 );

output Q11 ;
wire Q11 ;

input [11:0] D ;
wire [11:0] D ;
input Ser_in ;
wire Ser_in ;
input ParL_Ctrl ;
wire ParL_Ctrl ;
input Clk ;
wire Clk ;

//}} End of automatically maintained section		 

reg [11:0] Qtmp;
assign Q11 = Qtmp [11];


always @ (negedge Clk)
	begin
		if (ParL_Ctrl)
			Qtmp <=  D;
		else
			Qtmp <=  {Qtmp [10:0], Ser_in } ;
end



endmodule
