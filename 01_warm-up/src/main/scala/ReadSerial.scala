// ADS I Class Project
// Chisel Introduction
//
// Chair of Electronic Design Automation, RPTU in Kaiserslautern
// File created on 18/10/2022 by Tobias Jauch (@tojauch)

package readserial

import chisel3._
import chisel3.util._

/** Controller for Serial Receiver */
class Controller extends Module {
  val io = IO(new Bundle {
    val rxd    = Input(UInt(1.W))
    val cnt_s  = Input(UInt(3.W))
    val cnt_en = Output(Bool())
    val valid  = Output(Bool())
  })

  val sIdle = 1.U(1.W)      // Idle state
  val sReceive = 0.U(1.W)   // Receive state

  val state = RegInit(sIdle)
  val validReg = RegInit(false.B)  // Register for valid signal

  io.cnt_en := state === sReceive
  io.valid := validReg

  when(reset.asBool) {
    state := sIdle
    validReg := false.B
  }

  when(state === sIdle) {
    validReg := false.B
    when(io.rxd === 0.U) {
      state := sReceive
    }
  }.otherwise { // sReceive
    when(io.cnt_s === 7.U) {
      state := sIdle
      validReg := true.B  // Will be valid on the next cycle
    }
  }
}

/** 3-bit Counter for Serial Receiver */
class Counter extends Module {
  val io = IO(new Bundle {
    val cnt_en = Input(Bool())
    val cnt_s  = Output(UInt(4.W))
  })

  val cnt = RegInit(0.U(3.W))
  

  when(reset.asBool) {
    cnt := 0.U
  }.elsewhen(io.cnt_en) {
    when(cnt === 7.U) {
      cnt := 0.U
    }.otherwise {
      cnt := cnt + 1.U
    }
  }
  
  io.cnt_s := cnt
}

/** 8-bit Shift Register for Serial Receiver */
class ShiftRegister extends Module {
  val io = IO(new Bundle {
  val rxd    = Input(UInt(1.W))
 // val cnt_en = Input(Bool())
  val data   = Output(UInt(8.W))
  })

val reg = RegInit(0.U(8.W))
  reg := io.rxd | (reg << 1)
  io.data := reg

}

/** Top-level Serial Receiver */
class ReadSerialAdder extends Module {
  val io = IO(new Bundle {
    val rxd   = Input(UInt(1.W))
    val data  = Output(UInt(8.W))
    val valid = Output(Bool())
  })

  val counter     = Module(new Counter)
  val controller  = Module(new Controller)
  val shiftRegister    = Module(new ShiftRegister)

  // rxd to controller & shift register
  controller.io.rxd := io.rxd
  shiftRegister.io.rxd    := io.rxd
  //shiftRegister.io.cnt_en := controller.io.cnt_en || io.rxd === 0.U


  // cnt_s from counter → controller
  controller.io.cnt_s := counter.io.cnt_s

  // cnt_en from controller → counter + shiftRegister
  counter.io.cnt_en := controller.io.cnt_en


  // Outputs
  io.valid := controller.io.valid
  io.data  := shiftRegister.io.data
}
