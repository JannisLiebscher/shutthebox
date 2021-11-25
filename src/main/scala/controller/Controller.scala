package controller
import util.Observable
import model.Dice
import model.Board
case class Controller(board: Board, w: Dice) extends Observable:
  var out = "Hi ich bin ein Controller!"
  override def toString(): String = out

  def wuerfeln() =
    if (board.count() <= 6)
      w.wuerfeln(1)
      out = board.toString() + w.toString()
    else
      w.wuerfeln(2)
      out = board.toString() + w.toString()
    notifyObservers
  def shut(num: Int): Unit =
    board.shut(num)
    out = board.toString()
    notifyObservers
