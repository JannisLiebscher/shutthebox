package controller
import util.Observable
import model.Wuerfel
import model.Board
case class Controller(board: Board, w: Wuerfel) extends Observable:
  var out = "Hi ich bin ein Controller!"
  override def toString(): String = out
  def wurefeln(anzahl: Int): (Int, Int) = w.wuerfeln(anzahl)
