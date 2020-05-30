//-----------------------------------------------------------------------------
//
// Title       : RD_DFF
// Design      : RD_Prelab12
// Author      : Roni Das
// Company     : Stony Brook University
//
//-----------------------------------------------------------------------------
//
// File        : C:\Users\RONIDAS\Desktop\HDL\Prelab12\RD_Prelab12\src\RD_DFF.v
// Generated   : Sun Apr 29 03:15:19 2018
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
//{module {RD_DFF}}
module RD_Dff ( D ,Q ,CLRbar ,CLK );
//}} End of automatically maintained section	   

output Q ;
wire Q ;
input D ;
wire D ;
input CLRbar ;
wire CLRbar ;
input CLK ;
wire CLK ;	   


//}} End of automatically maintained section
reg  Qtmp ;
assign Q= Qtmp ;
always @ (posedge CLK or posedge CLRbar)
	begin
	  if (CLRbar) Qtmp <= 1'b0; 
	  else Qtmp <= D; 	  
	end



// -- Enter your statements here -- //

endmodule
