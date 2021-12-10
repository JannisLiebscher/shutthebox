package controller
import util.*
import model.Game
case class Controller(var game: Game) extends Observable:
  val undoManager = new UndoManager[Game]
  override def toString(): String = game.toString()
  def getSum: Int = game.sum
  def getDice: String = game.getDice
  def getBoard: String = game.getBoard
  def isShut(stone: Int): Boolean = game.board.isShut(stone)
  def doAndPublish(doThis: => Game) =
    game = doThis
    notifyObservers

  def doAndPublish(doThis: Int => Game, num: Int) =
    game = doThis(num)
    notifyObservers

  def wuerfeln: Game =
    if (game.count() <= 6) undoManager.doStep(game, WuerfelnCommand(1))
    else undoManager.doStep(game, WuerfelnCommand(2))

  def shut(num: Int): Game = undoManager.doStep(game, ShutCommand(num))
  def endMove: Game = undoManager.doStep(game, EndMoveCommand())
  def undo: Game = undoManager.undoStep(game)
  def redo: Game = undoManager.redoStep(game)
