package template

import Chisel._

class Divider(max: Int) extends Module {
  val io = new Bundle {
    val clk_out = Bool(OUTPUT)
  }

  val width = log2Up(max) + 1
  val counter = Reg(init=UInt(0, width))
  val max_value = UInt(max, width)
  val output = Reg(init=Bool(false))

  io.clk_out := output

  when(counter === max_value) {
    output := ~output
    counter := UInt(0, width)
  }.otherwise {
    counter := counter + UInt(1, width)
  }
}
