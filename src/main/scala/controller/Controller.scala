package controller
import util.*
import model.GameInterface
import com.google.inject.{Guice, Inject}

case class Controller @Inject() (var game: GameInterface)
    extends ControllerInterface:
  val undoManager = new UndoManager[GameInterface]
  override def toString(): String = game.toString()
  def getSum: Int = game.getSum
  def getDice: String = game.getDice
  def getBoard: String = game.getBoard
  def isShut(stone: Int): Boolean = game.isShut(stone)
  def doAndPublish(doThis: => GameInterface) =
    game = doThis
    notifyObservers

  def doAndPublish(doThis: Int => GameInterface, num: Int) =
    game = doThis(num)
    notifyObservers

  def wuerfeln: GameInterface =
    if (game.count() <= 6) undoManager.doStep(game, WuerfelnCommand(1))
    else undoManager.doStep(game, WuerfelnCommand(2))

  def shut(num: Int): GameInterface =
    undoManager.doStep(game, ShutCommand(num))
  def endMove: GameInterface =
    undoManager.doStep(game, EndMoveCommand())
  def undo: GameInterface = undoManager.undoStep(game)
  def redo: GameInterface = undoManager.redoStep(game)

object Controller:
  def apply(): Controller = new Controller(model.Game())
  def apply(kind: String) = kind match {
    case "mock" => new Controller(model.Game("mock"))
  }
