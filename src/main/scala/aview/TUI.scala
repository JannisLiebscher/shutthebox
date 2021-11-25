package aview

import scala.io.StdIn.readLine
import controller.Controller
import util.Observer

class Tui(controller: Controller) extends Observer:

  controller.add(this)
  println(controller.toString())
  getInputAndPrintLoop(" ")

  override def update = println(controller.toString)

  def getInputAndPrintLoop(in: String): Unit =
    val input = in
    input match
      case "w" =>
        controller.wuerfeln()
      case "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" =>
        controller.shut(input.toInt)
      case "q" =>
      case default =>
        print("Unbekannte Eingabe!")
