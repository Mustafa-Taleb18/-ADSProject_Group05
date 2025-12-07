// ADS I Class Project
// Chisel Introduction
//
// Chair of Electronic Design Automation, RPTU in Kaiserslautern
// File created on 18/10/2022 by Tobias Jauch (@tojauch)

package readserial

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class ReadSerialTester extends AnyFlatSpec with ChiselScalatestTester {

  "ReadSerial" should "work" in {
    test(new ReadSerialAdder).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>

      // ------------------------
      // Test 0x00
      // ------------------------
      // Start bit
      dut.io.rxd.poke(0.U); dut.clock.step(1)

      // Data bits MSB first: 00000000
      dut.io.rxd.poke(0.U); dut.clock.step(1)
      dut.io.rxd.poke(0.U); dut.clock.step(1)
      dut.io.rxd.poke(0.U); dut.clock.step(1)
      dut.io.rxd.poke(0.U); dut.clock.step(1)
      dut.io.rxd.poke(0.U); dut.clock.step(1)
      dut.io.rxd.poke(0.U); dut.clock.step(1)
      dut.io.rxd.poke(0.U); dut.clock.step(1)
      dut.io.rxd.poke(0.U); dut.clock.step(1)

      // Check valid pulse and data
      dut.io.valid.expect(true.B)
      dut.io.data.expect("b00000000".U)
      dut.clock.step(1)
      dut.io.valid.expect(false.B)

      // ------------------------
      // Test 0x55
      // ------------------------
      // Reset DUT
      dut.reset.poke(true.B)
      dut.clock.step(2)
      dut.reset.poke(false.B)

      // Start bit
      dut.io.rxd.poke(0.U); dut.clock.step(1)

      // Data bits MSB first: 01010101
      dut.io.rxd.poke(0.U); dut.clock.step(1)
      dut.io.rxd.poke(1.U); dut.clock.step(1)
      dut.io.rxd.poke(0.U); dut.clock.step(1)
      dut.io.rxd.poke(1.U); dut.clock.step(1)
      dut.io.rxd.poke(0.U); dut.clock.step(1)
      dut.io.rxd.poke(1.U); dut.clock.step(1)
      dut.io.rxd.poke(0.U); dut.clock.step(1)
      dut.io.rxd.poke(1.U); dut.clock.step(1)

      dut.io.valid.expect(true.B)
      dut.io.data.expect("b01010101".U)
      dut.clock.step(1)
      dut.io.valid.expect(false.B)

      // ------------------------
      // Test 0xAA
      // ------------------------
      // Start bit
      dut.io.rxd.poke(0.U); dut.clock.step(1)

      // Data bits MSB first: 10101010
      dut.io.rxd.poke(1.U); dut.clock.step(1)
      dut.io.rxd.poke(0.U); dut.clock.step(1)
      dut.io.rxd.poke(1.U); dut.clock.step(1)
      dut.io.rxd.poke(0.U); dut.clock.step(1)
      dut.io.rxd.poke(1.U); dut.clock.step(1)
      dut.io.rxd.poke(0.U); dut.clock.step(1)
      dut.io.rxd.poke(1.U); dut.clock.step(1)
      dut.io.rxd.poke(0.U); dut.clock.step(1)

      dut.io.valid.expect(true.B)
      dut.io.data.expect("b10101010".U)

      // After the last data bit has been transferred a new transmission (beginning with a start bit, 0) may immediately
      // ------------------------
      // Test 0xFF
      // ------------------------
      // Start bit
      dut.io.rxd.poke(0.U);

      // Data bits MSB first: 11111111
      dut.io.rxd.poke(1.U); dut.clock.step(1)
      dut.io.rxd.poke(1.U); dut.clock.step(1)
      dut.io.rxd.poke(1.U); dut.clock.step(1)
      dut.io.rxd.poke(1.U); dut.clock.step(1)
      dut.io.rxd.poke(1.U); dut.clock.step(1)
      dut.io.rxd.poke(1.U); dut.clock.step(1)
      dut.io.rxd.poke(1.U); dut.clock.step(1)
      dut.io.rxd.poke(1.U); dut.clock.step(1)

      dut.io.valid.expect(true.B)
      dut.io.data.expect("b11111111".U)
      dut.clock.step(1)
      dut.io.valid.expect(false.B)
    }
  }
}