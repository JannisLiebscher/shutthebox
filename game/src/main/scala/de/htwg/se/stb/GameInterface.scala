package de.htwg.se.stb.model
import scala.util.{Try, Success, Failure}
import de.htwg.se.stb.diceComponent.DiceInterface
import de.htwg.se.stb.boardComponent.BoardInterface
import de.htwg.se.stb.playerComponent.PlayerInterface

trait GameInterface {
  def _getDice: DiceInterface
  def _getBoard: BoardInterface
  def _getPlayers: PlayerInterface
  def count(): Int
  def getDice: String
  def getSum: Int
  def getBoard: String
  def isShut(stone: Int): Boolean
  def getScore(player: Int): Int
  def getPlayers: String
  def getWinner: Option[String]
  def getTurn: Int
  def wuerfeln(num: Int): Try[Game]
  def shut(stone: Int): Try[Game]
  def resShut(stone: Int): Try[Game]
  def endMove: Try[Game]
  def toString(): String
}
