package de.htwg.se.stb.diceComponent

trait DiceInterface {
  def getSum(): Int
  def wuerfeln(amount: Int): DiceInterface
  def toString(): String
}
