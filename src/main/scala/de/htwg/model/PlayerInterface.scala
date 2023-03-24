package model

trait PlayerInterface {

  def addScore(amount: Int): Players
  def getScore(player: Int): Int
  def toString(): String
  def getTurn: Int
}
