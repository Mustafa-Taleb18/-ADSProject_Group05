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
class ALUANDTest extends AnyFlatSpec with ChiselScalatestTester {
  "ALU_AND_Tester" should "test AND operation" in {
    test(new ALU).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.clock.setTimeout(0)

      dut.io.operation.poke(ALUOp.AND)

      // Test 1 : AND with overlapping bits
      dut.io.operandA.poke("hFF00FF00".U)
      dut.io.operandB.poke("h0F0F0F0F".U)
      dut.io.aluResult.expect("h0F000F00".U)

      // Test 2 : AND with zero (masking)
      dut.io.operandA.poke("h12345678".U)
      dut.io.operandB.poke(0.U)
      dut.io.aluResult.expect(0.U)

      // Test 3 : AND with all ones (identity)
      dut.io.operandA.poke("h89ABCDEF".U)
      dut.io.operandB.poke("hFFFFFFFF".U)
      dut.io.aluResult.expect("h89ABCDEF".U)
    }
  }
}

class ALUORTest extends AnyFlatSpec with ChiselScalatestTester {
  "ALU_OR_Tester" should "test OR operation" in {
    test(new ALU).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.clock.setTimeout(0)

      dut.io.operation.poke(ALUOp.OR)

      // Test 1 : OR with overlapping bits
      dut.io.operandA.poke("hF0F0F0F0".U)
      dut.io.operandB.poke("h0F0F0F0F".U)
      dut.io.aluResult.expect("hFFFFFFFF".U)

      // Test 2 : OR with zero 
      dut.io.operandA.poke("h12345678".U)
      dut.io.operandB.poke(0.U)
      dut.io.aluResult.expect("h12345678".U)

      // Test 3 : OR with all ones
      dut.io.operandA.poke("h00000000".U)
      dut.io.operandB.poke("hFFFFFFFF".U)
      dut.io.aluResult.expect("hFFFFFFFF".U)
    }
  }
}
class ALUXORTest extends AnyFlatSpec with ChiselScalatestTester {
  "ALU_XOR_Tester" should "test XOR operation" in {
    test(new ALU).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.clock.setTimeout(0)

      dut.io.operation.poke(ALUOp.XOR)

      // Test 1 : XOR different bits
      dut.io.operandA.poke("hAAAA5555".U)
      dut.io.operandB.poke("h5555AAAA".U)
      dut.io.aluResult.expect("hFFFFFFFF".U)

      // Test 2 : XOR with zero 
      dut.io.operandA.poke("h12345678".U)
      dut.io.operandB.poke(0.U)
      dut.io.aluResult.expect("h12345678".U)

      // Test 3 : XOR with itself
      dut.io.operandA.poke("h12345678".U)
      dut.io.operandB.poke("h12345678".U)
      dut.io.aluResult.expect(0.U)
    }
  }
}
class ALUSLL_SRL_SRATest extends AnyFlatSpec with ChiselScalatestTester {
  "ALU_SLL_SRL_SRA_Tester" should "test SLL_SRL_SRA operation" in {
    test(new ALU).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.clock.setTimeout(0)


      //  SLL TESTS 
      // Test 1 : 1 << 0 = 1
      dut.io.operandA.poke(1.U)
      dut.io.operandB.poke(0.U)
      dut.io.operation.poke(ALUOp.SLL)
      dut.io.aluResult.expect(1.U)

      // Test 2 :1 << 31 = 0x80000000
      dut.io.operandA.poke(1.U)
      dut.io.operandB.poke(31.U)
      dut.io.operation.poke(ALUOp.SLL)
      dut.io.aluResult.expect(BigInt("80000000", 16).U)

      // Test 3 : Shift with operandB > 31 (masked to 0)
      dut.io.operandA.poke(1.U)
      dut.io.operandB.poke(32.U) // 32 -> 0 after masking
      dut.io.operation.poke(ALUOp.SLL)
      dut.io.aluResult.expect(1.U)

      //  SRL TESTS 
      // Test 1 : 0x80000000 >> 31 = 1
      dut.io.operandA.poke(BigInt("80000000", 16).U)
      dut.io.operandB.poke(31.U)
      dut.io.operation.poke(ALUOp.SRL)
      dut.io.aluResult.expect(1.U)

      // Test 2 : Shift with operandB > 31 (masked to 0)
      dut.io.operandA.poke(BigInt("80000000", 16).U)
      dut.io.operandB.poke(32.U) // masked to 0
      dut.io.operation.poke(ALUOp.SRL)
      dut.io.aluResult.expect(BigInt("80000000", 16).U)

      //  SRA TESTS 
      // Test 1 : Arithmetic shift: -8 >> 2 = -2
      dut.io.operandA.poke(BigInt("FFFFFFF8", 16).U) // -8 as UInt
      dut.io.operandB.poke(2.U)
      dut.io.operation.poke(ALUOp.SRA)
      dut.io.aluResult.expect(BigInt("FFFFFFFE", 16).U) // -2

      // Test 2 :Arithmetic shift: -2147483648 >> 31 = -1
      dut.io.operandA.poke(BigInt("80000000", 16).U) // -2^31
      dut.io.operandB.poke(31.U)
      dut.io.operation.poke(ALUOp.SRA)
      dut.io.aluResult.expect(BigInt("FFFFFFFF", 16).U)

      // Test 3 :Shift by 0 (no change)
      dut.io.operandB.poke(0.U)
      dut.io.aluResult.expect(BigInt("80000000", 16).U)

      // Test 4 :Shift by >31 (masked to 0)
      dut.io.operandB.poke(32.U)
      dut.io.aluResult.expect(BigInt("80000000", 16).U)
    }
  }
}