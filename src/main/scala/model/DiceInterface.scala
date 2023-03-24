package model

trait DiceInterface {
  def getSum(): Int
  def wuerfeln(amount: Int): DiceInterface
  def toString(): String
}
