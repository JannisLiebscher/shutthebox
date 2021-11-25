package aview

import scala.io.StdIn.readLine
import controller.Controller
import util.Observer

class Tui(controller: Controller) extends Observer:
  controller.add(this)
  def run =
    println(controller.toString())
    getInputAndPrintLoop()

  override def update = println(controller.toString)

  def getInputAndPrintLoop(): Unit =
    val input = readLine
    input match
      case "w" =>
        controller.wuerfeln()
      case "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" =>
        controller.shut(input.toInt)
    getInputAndPrintLoop()
