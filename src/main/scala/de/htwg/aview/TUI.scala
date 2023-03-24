package de.htwg.aview

import scala.io.StdIn.readLine
import controller.ControllerInterface
import util.Observer

class Tui(controller: ControllerInterface) extends Observer:

  controller.add(this)

  getInputAndPrintLoop("start")

  override def update = println(controller.toString)

  def getInputAndPrintLoop(in: String): Unit =
    val input = in
    input match
      case "start" => println("Welcome to ShutTheBox")
      case "w" =>
        controller.doAndPublish(controller.wuerfeln)
      case "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" =>
        controller.doAndPublish(controller.shut, input.toInt)
      case "next" =>
        controller.doAndPublish(controller.endMove)
      case "z" => controller.doAndPublish(controller.redo); None
      case "y" => controller.doAndPublish(controller.undo); None
      case "q" => System.exit(0)
      case default =>
        print("Unbekannte Eingabe!\n")
