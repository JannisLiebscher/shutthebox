package de.htwg.se.stb.aview

import scala.io.StdIn.readLine
import de.htwg.se.stb.controller.ControllerInterface
import de.htwg.se.stb.util.Observer

class Tui(controller: ControllerInterface) extends Observer:

  controller.add(this)

  getInputAndPrintLoop("start")

  override def update = println(controller.toString)
  override def handle(error: Throwable) = println(error.getMessage())

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
      case "save" => controller.save; None
      case "load" => controller.doAndPublish(controller.load); None
      case "delete" => controller.delete; None
      case "update" => controller.update; None
      case default =>
        print("Unbekannte Eingabe!\n")
