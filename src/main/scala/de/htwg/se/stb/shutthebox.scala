package de.htwg.se.stb

import com.google.inject.Guice
import de.htwg.se.stb.controller.ControllerInterface
import de.htwg.se.stb.module._
import scala.io.StdIn.readLine
import de.htwg.se.stb.rest.*

object shutthebox {
  @main def run: Unit =
    val injector = Guice.createInjector(new ShutTheBoxModuleSQL)
    val c = injector.getInstance(classOf[ControllerInterface])
    val tui = new aview.Tui(c)
    val gui = new aview.Gui(c)
    DiceService.main
    PlayerService.main
    BoardService.main
    tui.update
    while (true) {
      val input: String = readLine()
      if (input == "q" | input == "quit")
        gui.dispose()
        DiceService.shutdown()
        PlayerService.shutdown()
        BoardService.shutdown()
        System.exit(0)
      tui.getInputAndPrintLoop(input)
    }
}
