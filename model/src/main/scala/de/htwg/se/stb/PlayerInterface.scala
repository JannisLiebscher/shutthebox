package de.htwg.se.stb.model
trait PlayerInterface {

  def addScore(amount: Int): Players
  def getScore(player: Int): Int
  def toString: String
  def getWinner: Option[String]
  def getTurn: Int
}
