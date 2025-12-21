// ADS I Class Project
// Assignment 02: Arithmetic Logic Unit and UVM Testbench
//
// Chair of Electronic Design Automation, RPTU University Kaiserslautern-Landau
// File created on 09/21/2025 by Tharindu Samarakoon (gug75kex@rptu.de)
// File updated on 10/29/2025 by Tobias Jauch (tobias.jauch@rptu.de)

package Assignment02

import chisel3._
import chisel3.util._
import chisel3.experimental.ChiselEnum

//define AluOp Enum
object AluOp extends ChiselEnum {
  val ADD = Value
}


class ALU extends Module {
  
  val io = IO(new Bundle {
    val operandA = Input(UInt(32,W))
    val operandB = Input(UInt(32,W))
    val operation = Input(AluOp())
    val result = Output(UInt(32,W))
  })

  //ToDo: implement ALU functionality according to the task specification

}