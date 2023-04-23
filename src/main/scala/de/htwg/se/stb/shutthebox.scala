package de.htwg.se.stb

import com.google.inject.Guice
import de.htwg.se.stb.controller.ControllerInterface
import de.htwg.se.stb.module.ShutTheBoxModuleXML
import scala.io.StdIn.readLine
import de.htwg.se.stb.dice.rest.DiceService

object shutthebox {
  @main def run: Unit =
    val injector = Guice.createInjector(new ShutTheBoxModuleXML)
    val c = injector.getInstance(classOf[ControllerInterface])
    val tui = new aview.Tui(c)
    val gui = new aview.Gui(c)
    DiceService.main

    while (true) {
      val input: String = readLine()
      if (input == "q" | input == "quit")
        gui.dispose()
        System.exit(0)
      tui.getInputAndPrintLoop(input)
    }
}
