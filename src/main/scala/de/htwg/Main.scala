package main.scala.de.htwg

import scala.io.StdIn.readLine
object shutthebox {
  @main def run: Unit =
    val injector = Guice.createInjector(new ShutTheBoxModuleXML)
    val c = injector.getInstance(classOf[ControllerInterface])
    val tui = new aview.Tui(c)
    val gui = new aview.Gui(c)

    var input: String = ""
    while (input != "q") {
      input = readLine()
      tui.getInputAndPrintLoop(input)
    }
}
