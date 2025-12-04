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
    /* 
     * TODO: Define IO ports of a 4-bit ripple-carry-adder as presented in the lecture
     */
    })

  /* 
   * TODO: Instanciate the full adders and one half adderbased on the previously defined classes
   */


  /* 
   * TODO: Describe output behaviour based on the input values and the internal 
   */
}
