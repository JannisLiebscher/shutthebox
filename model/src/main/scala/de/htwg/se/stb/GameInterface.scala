package de.htwg.se.stb.model
import scala.util.{Try, Success, Failure}

trait GameInterface {
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
