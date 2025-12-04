// ADS I Class Project
// Chisel Introduction
//
// Chair of Electronic Design Automation, RPTU in Kaiserslautern
// File created on 18/10/2022 by Tobias Jauch (@tojauch)

package adder

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec


/** 
  * Full adder tester
  * Use the truth table from the exercise sheet to test all possible input combinations and the corresponding results exhaustively
  */
class FullAdderTester extends AnyFlatSpec with ChiselScalatestTester {

  "FullAdder" should "work" in {
    test(new FullAdder).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>

        // Truth Table Implementaoin (case-1) : a=0,b=0,ci=0
        dut.io.a.poke(0.U)
        dut.io.b.poke(0.U)
        dut.io.ci.poke(0.U)
        dut.io.s.expect(0.U)
        dut.io.c0.expect(0.U)
        // Truth Table Implementaoin (case-2) : a=0,b=0,ci=1
        dut.io.a.poke(0.U)
        dut.io.b.poke(0.U)
        dut.io.ci.poke(1.U)
        dut.io.s.expect(1.U)
        dut.io.c0.expect(0.U)
        // Truth Table Implementaoin (case-3) : a=0,b=1,ci=0
        dut.io.a.poke(0.U)
        dut.io.b.poke(1.U)
        dut.io.ci.poke(0.U)
        dut.io.s.expect(1.U)
        dut.io.c0.expect(0.U)
        // Truth Table Implementaoin (case-4) : a=0,b=1,ci=1
        dut.io.a.poke(0.U)
        dut.io.b.poke(1.U)
        dut.io.ci.poke(1.U)
        dut.io.s.expect(0.U)
        dut.io.c0.expect(1.U)
        // Truth Table Implementaoin (case-5) : a=1,b=0,ci=0
        dut.io.a.poke(1.U)
        dut.io.b.poke(0.U)
        dut.io.ci.poke(0.U)
        dut.io.s.expect(1.U)
        dut.io.c0.expect(0.U)
        // Truth Table Implementaoin (case-6) : a=1,b=0,ci=1
        dut.io.a.poke(1.U)
        dut.io.b.poke(0.U)
        dut.io.ci.poke(1.U)
        dut.io.s.expect(0.U)
        dut.io.c0.expect(1.U)
        // Truth Table Implementaoin (case-7) : a=1,b=1,ci=0
        dut.io.a.poke(1.U)
        dut.io.b.poke(1.U)
        dut.io.ci.poke(0.U)
        dut.io.s.expect(0.U)
        dut.io.c0.expect(1.U)
        // Truth Table Implementaoin (case-8) : a=1,b=1,ci=1
        dut.io.a.poke(1.U)
        dut.io.b.poke(1.U)
        dut.io.ci.poke(1.U)
        dut.io.s.expect(1.U)
        dut.io.c0.expect(1.U)

        }
    } 
}

