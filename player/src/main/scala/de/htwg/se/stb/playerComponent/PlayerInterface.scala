package de.htwg.se.stb.playerComponent
trait PlayerInterface {

  def addScore(amount: Int): Players
  def getScore(player: Int): Int
  def toString: String
  def getWinner: Option[String]
  def getTurn: Int
}
