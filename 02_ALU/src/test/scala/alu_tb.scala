// ADS I Class Project
// Pipelined RISC-V Core with Hazard Detection and Resolution
//
// Chair of Electronic Design Automation, RPTU in Kaiserslautern
// File created on 10/31/2025 by Tobias Jauch (tobias.jauch@rptu.de)

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

import Assignment02._

// Test ADD operation
class ALUAddTest extends AnyFlatSpec with ChiselScalatestTester {
  "ALU_Add_Tester" should "test ADD operation" in {
    test(new ALU).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.clock.setTimeout(0)

      // Test 1: Simple test
      dut.io.operandA.poke(10.U)
      dut.io.operandB.poke(10.U)
      dut.io.operation.poke(ALUOp.ADD)
      dut.io.aluResult.expect(20.U)
      dut.clock.step(1)

      // Test 2: addition with zero
      dut.io.operandA.poke(65.U)
      dut.io.operandB.poke(0.U)
      dut.io.aluResult.expect(65.U)
      dut.clock.step(1)

      // Test 3: wraparound (overflow)
      dut.io.operandA.poke("hFFFFFFFF".U)
      dut.io.operandB.poke(1.U)
      dut.io.aluResult.expect(0.U)        // wraparound
      dut.clock.step(1)

    }
  }
}
// ---------------------------------------------------
// Add test classes for all other ALU operations
//---------------------------------------------------
class ALUSUBTest extends AnyFlatSpec with ChiselScalatestTester {
  "ALU_SUB_Tester" should "test SUB operation" in {
    test(new ALU).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.clock.setTimeout(0)

      // Test 1: Simple test
      dut.io.operandA.poke(20.U)
      dut.io.operandB.poke(10.U)
      dut.io.operation.poke(ALUOp.SUB)
      dut.io.aluResult.expect(10.U)
      dut.clock.step(1)

      // Test 2: substraction with zero
      dut.io.operandA.poke(65.U)
      dut.io.operandB.poke(0.U)
      dut.io.aluResult.expect(65.U)
      dut.clock.step(1)

      // Test 3: wraparound (overflow)
      dut.io.operation.poke(ALUOp.SUB)
      dut.io.operandA.poke(0.U)
      dut.io.operandB.poke(1.U)
      dut.io.aluResult.expect("hFFFFFFFF".U)

      // Test 4 : 20 - 32 = -12 (two's-complement wraparound)
      dut.io.operation.poke(ALUOp.SUB)
      dut.io.operandA.poke(20.U)
      dut.io.operandB.poke(32.U)
      dut.io.aluResult.expect("hFFFFFFF4".U)

    }
  }
}