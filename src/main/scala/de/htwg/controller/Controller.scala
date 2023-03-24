package de.htwg.controller
import util.*
import model.GameInterface
import model.fileioComponent.*
case class Controller(
    var game: GameInterface,
    var file: model.fileioComponent.FileIOInterface
) extends ControllerInterface:
  val undoManager = new UndoManager[GameInterface]
  override def toString(): String = game.toString()
  def getSum: Int = game.getSum
  def getDice: String = game.getDice
  def getBoard: String = game.getBoard
  def isShut(stone: Int): Boolean = game.isShut(stone)
  def getScore(PlayerNum: Int): Int = game.getScore(PlayerNum)
  def getPlayers: String = game.getPlayers
  def save: Unit = file.save(game)
  def load: GameInterface = file.load

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
  def apply(): Controller =
    new Controller(model.Game(), new FileIOJSON)
  def apply(kind: String): Controller = kind match {
    case "mock"          => Controller(model.Game("mock"), new FileIOJSON)
    case "xml" | "XML"   => Controller(model.Game(), new FileIOXML)
    case "json" | "JSON" => Controller(model.Game(), new FileIOJSON)
  }
