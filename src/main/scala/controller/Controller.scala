package controller
import util.Observable
import model.Dice
import model.Board
case class Controller(var board: Board, w: Dice) extends Observable:
  private var out = "Hi ich bin ein Controller!"
  override def toString(): String = out

  def wuerfeln() =
    if (board.count() <= 6) out = board.toString() + w.wuerfeln(1).toString()
    else
      out = board.toString() + w.wuerfeln(2).toString()
    notifyObservers
  def shut(num: Int): Unit =
    board = board.shut(num)
    out = board.toString()
    notifyObservers
