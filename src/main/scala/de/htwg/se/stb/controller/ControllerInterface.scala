package de.htwg.se.stb.controller
import scala.util.{Try, Success, Failure}
import de.htwg.se.stb.util.*
import de.htwg.se.stb.model.GameInterface

trait ControllerInterface extends Observable {
  override def toString(): String
  def save: Unit
  def load: Try[GameInterface]
  def delete: Unit
  def update: Unit
  def getSum: Int
  def getDice: String
  def getBoard: String
  def getScore(PlayerNum: Int): Int
  def getPlayers: String
  def getWinner: Option[String]
  def isShut(stone: Int): Boolean
  def doAndPublish(doThis: => Try[GameInterface]): Unit
  def doAndPublish(doThis: Int => Try[GameInterface], num: Int): Unit
  def wuerfeln: Try[GameInterface]
  def shut(num: Int): Try[GameInterface]
  def endMove: Try[GameInterface]
  def undo: Try[GameInterface]
  def redo: Try[GameInterface]
}
