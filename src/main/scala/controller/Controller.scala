package controller
import util.*
import scala.util.{Try, Success, Failure}
import model.GameInterface
import model.fileioComponent.*
case class Controller(
    var game: GameInterface,
    file: model.fileioComponent.FileIOInterface
) extends ControllerInterface:
  val undoManager = new UndoManager[Try[GameInterface]]
  override def toString(): String = game.toString()
  def getSum: Int = game.getSum
  def getDice: String = game.getDice
  def getBoard: String = game.getBoard
  def isShut(stone: Int): Boolean = game.isShut(stone)
  def getScore(PlayerNum: Int): Int = game.getScore(PlayerNum)
  def getPlayers: String = game.getPlayers
  def getWinner: Option[String] = game.getWinner
  def save: Unit = file.save(game)
  def load: Try[GameInterface] = Success(file.load)

  def doAndPublish(doThis: => Try[GameInterface]) =
    doThis match {
      case Success(value) => 
        game = doThis.get
        notifyObservers
      case Failure(e) => raiseError(e)
    }

  def doAndPublish(doThis: Int => Try[GameInterface], num: Int) =
    doThis(num) match {
      case Success(value) => 
        game = doThis(num).get
        notifyObservers
      case Failure(e) => raiseError(e)
    }
    
  def wuerfeln: Try[GameInterface] =
    if (game.count() <= 6) undoManager.doStep(Success(game), WuerfelnCommand(1))
    else undoManager.doStep(Success(game), WuerfelnCommand(2))

  def shut(num: Int): Try[GameInterface] =
    undoManager.doStep(Success(game), ShutCommand(num))
  def endMove: Try[GameInterface] =
    undoManager.doStep(Success(game), EndMoveCommand())
  def undo: Try[GameInterface] = undoManager.undoStep(Success(game))
  def redo: Try[GameInterface] = undoManager.redoStep(Success(game))

object Controller:
  def apply(): Controller =
    new Controller(model.Game(), new FileIOJSON)
  def apply(kind: String): Controller = kind match {
    case "mock" | "Mock"  => Controller(model.Game("mock"), new FileIOJSON)
    case "xml" | "XML"    => Controller(model.Game(), new FileIOXML)
    case "json" | "JSON"  => Controller(model.Game(), new FileIOJSON)
  }
