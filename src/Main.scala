package template

import Chisel._

class MainIO extends Bundle {
  val led1 = UInt(OUTPUT, 1)
  val led2 = UInt(OUTPUT, 1)
  val led3 = UInt(OUTPUT, 1)
  val led4 = UInt(OUTPUT, 1)
  val led5 = UInt(OUTPUT, 1)

  val pmod1 = UInt(INPUT, 1)
  val pmod2 = UInt(INPUT, 1)
  val pmod3 = UInt(INPUT, 1)
  val pmod4 = UInt(INPUT, 1)
}

class Main extends Module {
  val io = new MainIO()

  val divider1 = Module(new Divider(1 + (1<<22)))
  val divider2 = Module(new Divider(100 + (1<<22)))
  val divider3 = Module(new Divider(1000 + (1<<22)))
  val divider4 = Module(new Divider(10000 + (1<<22)))
  val divider5 = Module(new Divider(100000 + (1<<22)))

  io.led1 := divider1.io.clk_out
  io.led2 := divider2.io.clk_out
  io.led3 := divider3.io.clk_out
  io.led4 := divider4.io.clk_out
  io.led5 := divider5.io.clk_out
}



class MainTests(c: Main) extends Tester(c) {
  step(1)
}


object Main {
  def main(args: Array[String]): Unit = {
    val tutArgs = args.slice(1, args.length)
    chiselMainTest(tutArgs, () => Module(new Main())) {
      c => new MainTests(c)
    }
  }
}
