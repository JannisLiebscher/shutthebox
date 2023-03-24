package model

trait GameInterface {
  def count(): Int
  def getDice: String
  def getSum: Int
  def getBoard: String
  def isShut(stone: Int): Boolean
  def getScore(player: Int): Int
  def getPlayers: String
  def getTurn: Int
  def wuerfeln(num: Int): Game
  def shut(stone: Int): Game
  def resShut(stone: Int): Game
  def endMove: Game
  def toString(): String
}
