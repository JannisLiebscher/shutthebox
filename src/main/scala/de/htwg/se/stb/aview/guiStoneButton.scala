package de.htwg.se.stb.aview

import swing._
import de.htwg.se.stb.controller.ControllerInterface
import de.htwg.se.stb.util.Observer

class guiButtonTop(number: Int) extends Button {
  this.text = number.toString
  def shut = this.text = "#"
  def notShut = this.text = number.toString
  override def toString = this.text
}

class guiButtonBase(number: Int) extends Button {
  this.text = "#"
  def shut = this.text = number.toString
  def notShut = this.text = "#"
  override def toString = this.text
}
