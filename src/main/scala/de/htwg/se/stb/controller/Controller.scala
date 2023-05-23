package de.htwg.se.stb.controller
import de.htwg.se.stb.model.*
import de.htwg.se.stb.model.fileioComponent._
import de.htwg.se.stb.util.UndoManager
import scala.util.{Failure, Success, Try}

case class Controller(
    var game: GameInterface,
    file: FileIOInterface
) extends ControllerInterface:
  val undoManager = new UndoManager[GameInterface]
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
  def delete: Unit = file.delete
  def update: Unit = file.update(game)

  def doAndPublish(doThis: => Try[GameInterface]) =
    doThis match {
      case Success(value) => 
        game = value
        notifyObservers
      case Failure(e) => raiseError(e)
    }

  def doAndPublish(doThis: Int => Try[GameInterface], num: Int) =
    doThis(num) match {
      case Success(value) => 
        game = value
        notifyObservers
      case Failure(e) => raiseError(e)
    }
    
  def wuerfeln: Try[GameInterface] =
    if (game.count() <= 6) undoManager.doStep(game, WuerfelnCommand(1))
    else undoManager.doStep(game, WuerfelnCommand(2))

  def shut(num: Int): Try[GameInterface] =
    undoManager.doStep(game, ShutCommand(num))
  def endMove: Try[GameInterface] =
    undoManager.doStep(game, EndMoveCommand())
  def undo: Try[GameInterface] = undoManager.undoStep(game)
  def redo: Try[GameInterface] = undoManager.redoStep(game)

object Controller:
  def apply(): Controller =
    new Controller(Game(), new FileIOJSON)
  def apply(kind: String): Controller = kind match {
    case "mock" | "Mock"  => Controller(Game("mock"), new FileIOJSON)
    case "xml" | "XML"    => Controller(Game(), new FileIOXML)
    case "json" | "JSON"  => Controller(Game(), new FileIOJSON)
    case "sql" | "SQL"  => Controller(Game(), new FileIOSQL)
    case "mongo" | "MONGO"  => Controller(Game(), new FileIOMongo)
  }
