package aview

import scala.io.StdIn.readLine
import controller.Controller
import util.Observer

class TUI(controller: Controller) extends Observer:
  controller.add(this)
  def run =
    println(controller.toString())
    getInputAndPrintLoop()

  override def update = println(controller.toString)

  def getInputAndPrintLoop(): Unit =
    val input = readLine
    input match
      case "w" =>
