// ADS I Class Project
// Chisel Introduction
//
// Chair of Electronic Design Automation, RPTU in Kaiserslautern
// File created on 18/10/2022 by Tobias Jauch (@tojauch)

package adder

import chisel3._
import chisel3.util._


/** 
  * Half Adder Class 
  * 
  * Your task is to implement a basic half adder as presented in the lecture.
  * Each signal should only be one bit wide (inputs and outputs).
  * There should be no delay between input and output signals, we want to have
  * a combinational behaviour of the component.
  */
class HalfAdder extends Module{
  
  val io = IO(new Bundle {
    val a = Input(UInt(1.W))
    val b = Input(UInt(1.W))
    val s = Output(UInt(1.W))
    val c0 = Output(UInt(1.W))
    })

    io.s := io.a + io.b     // Sum= a XOR b
    io.c0 := io.a & io.b    // carry= a AND b

}

/** 
  * Full Adder Class 
  * 
  * Your task is to implement a basic full adder. The component's behaviour should 
  * match the characteristics presented in the lecture. In addition, you are only allowed 
  * to use two half adders (use the class that you already implemented) and basic logic 
  * operators (AND, OR, ...).
  * Each signal should only be one bit wide (inputs and outputs).
  * There should be no delay between input and output signals, we want to have
  * a combinational behaviour of the component.
  */
class FullAdder extends Module{

  val io = IO(new Bundle {
    val a = Input(UInt(1.W))
    val b = Input(UInt(1.W))
    val ci = Input(UInt(1.W))
    val s = Output(UInt(1.W))
    val c0 = Output(UInt(1.W))
    })

  //Instanciate the two half adders that will be used to implement FA
  val Ha_adder1 = Module(new HalfAdder)
  val Ha_adder2 = Module(new HalfAdder)
  
  // Mapping a&b signals of FA to the first HA inputs
  Ha_adder1.io.a := io.a
  Ha_adder1.io.b := io.b
  // defined internal signals to connect the outputs of first HA
  val s1 = Ha_adder1.io.s
  val c1 = Ha_adder1.io.c0
  
  Ha_adder2.io.a := s1    // Connect the sum signal of first HA to the first input of second HA
  Ha_adder2.io.b := io.ci   // Connect the ci of FA to the second input of second HA

  // defined internal signals to connect the outputs of second HA
  val s2 = Ha_adder2.io.s
  val c2 = Ha_adder2.io.c0

  io.s := s2          // s= a XOR b XOR ci
  io.c0 := c1 | c2    // c0= (a.b) | (ci.(a XOR b))
}

/** 
  * 4-bit Adder class 
  * 
  * Your task is to implement a 4-bit ripple-carry-adder. The component's behaviour should 
  * match the characteristics presented in the lecture.  Remember: An n-bit adder can be 
  * build using one half adder and n-1 full adders.
  * The inputs and the result should all be 4-bit wide, the carry-out only needs one bit.
  * There should be no delay between input and output signals, we want to have
  * a combinational behaviour of the component.
  */
class FourBitAdder extends Module{

  val io = IO(new Bundle {
    val a = Input(UInt(4.W))
    val b = Input(UInt(4.W))
    val s = Output(UInt(4.W))
    val co = Output(Bool())
    })

    // Instanciate the full adders (n-1) and one half adderbased on the previously defined classes
    val HA_adder = Module(new(HalfAdder))
    val FA_adder1 = Module(new(FullAdder))
    val FA_adder2 = Module(new(FullAdder))
    val FA_adder3 = Module(new(FullAdder))

    // Connect a0 and b0 to the HA and connect the output to internal signals
    HA_adder.io.a := io.a(0)
    HA_adder.io.b := io.b(0)
    val s0 = HA_adder.io.s
    val c0 = HA_adder.io.c0

    // Connect a1 and b1 to the 1st FA and connect the output to internal signals
    FA_adder1.io.a := io.a(1)
    FA_adder1.io.b := io.b(1)
    FA_adder1.io.ci := c0
    val s1 = FA_adder1.io.s
    val c1 = FA_adder1.io.c0

    // Connect a2 and b2 to the 2nd FA and connect the output to internal signals
    FA_adder2.io.a := io.a(2)
    FA_adder2.io.b := io.b(2)
    FA_adder2.io.ci := c1
    val s2 = FA_adder2.io.s
    val c2 = FA_adder2.io.c0

    // Connect a3 and b3 to the 3nd FA and connect the output to internal signals
    FA_adder3.io.a := io.a(3)
    FA_adder3.io.b := io.b(3)
    FA_adder3.io.ci := c2
    val s3 = FA_adder3.io.s
    val c3 = FA_adder3.io.c0

   // Display the output s and c
    io.s := Cat(s3, s2, s1, s0)  // io.s is a UInt,cannot assign to individual bits eg. s(0)
    io.co := c3

}
