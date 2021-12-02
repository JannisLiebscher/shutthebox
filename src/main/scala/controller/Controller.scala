package controller
import util.*
import model.Game
case class Controller(var game: Game) extends Observable:
  val undoManager = new UndoManager[Game]
  override def toString(): String = game.toString()

  def doAndPublish(doThis: => Game) =
    game = doThis
    notifyObservers
  def doAndPublish(doThis: Int => Game, num: Int) =
    game = doThis(num)
    notifyObservers

  def wuerfeln: Game =
    if (game.count() <= 6) game = game.wuerfeln(1)
    else game = game.wuerfeln(2)
    return game

  def shut(num: Int): Game =
    game = undoManager.doStep(game, ShutCommand(num))
    return game

  def endMove: Game =
    game = game.endMove()
    return game

  def undo: Game = undoManager.undoStep(game)
  def redo: Game = undoManager.redoStep(game)
