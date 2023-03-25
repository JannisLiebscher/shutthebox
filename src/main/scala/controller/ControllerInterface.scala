package controller
import util.*
import model.GameInterface

trait ControllerInterface extends Observable {
  override def toString(): String
  def save: Unit
  def load: GameInterface
  def getSum: Int
  def getDice: String
  def getBoard: String
  def getScore(PlayerNum: Int): Int
  def getPlayers: String
  def getWinner: Option[String]
  def isShut(stone: Int): Boolean
  def doAndPublish(doThis: => GameInterface): Unit
  def doAndPublish(doThis: Int => GameInterface, num: Int): Unit
  def wuerfeln: GameInterface
  def shut(num: Int): GameInterface
  def endMove: GameInterface
  def undo: GameInterface
  def redo: GameInterface
}
