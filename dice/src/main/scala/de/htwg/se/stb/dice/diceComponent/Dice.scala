package de.htwg.se.stb.diceComponent
import scala.util.Random
import com.typesafe.config.ConfigFactory

case class TwoDice(w1: Int, w2: Int) extends DiceInterface {
  val config = ConfigFactory.load()
  def this() = this(new Random().nextInt(6) + 1, new Random().nextInt(6) + 1)
  def getSum(): Int = w1 + w2
  def wuerfeln(amount: Int): DiceInterface = Dice.wuerfeln(amount)
  override def toString() = config.getString("num.two") + " und " + w2.toString
  
}
case class OneDice(w1: Int, w2: Int) extends DiceInterface {
  def this() = this(new Random().nextInt(6) + 1, 0)
  def getSum(): Int = w1
  def wuerfeln(amount: Int): DiceInterface = Dice.wuerfeln(amount)
  override def toString() = w1.toString
}

case class MockDice(w1: Int, w2: Int) extends DiceInterface {
  def this() = this(1, 1)
  def getSum(): Int = w1 + w2
  def wuerfeln(amount: Int): DiceInterface = Dice("mock")
  override def toString() = w1.toString + " und " + w2.toString
}

object Dice {
  def apply(kind: String) = kind match {
    case "1" | "one"     => new OneDice()
    case "2" | "two"     => new TwoDice()
    case "mock" | "Mock" => new MockDice()
  }
  def wuerfeln = (x: Int) => Dice(x.toString())
}
