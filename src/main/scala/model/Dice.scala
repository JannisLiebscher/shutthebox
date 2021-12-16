package model
import scala.util.Random

case class TwoDice(w1: Int, w2: Int) extends DiceInterface {
  def this() = this(new Random().nextInt(6) + 1, new Random().nextInt(6) + 1)
  override def getSum(): Int = w1 + w2
  def wuerfeln(amount: Int): DiceInterface =
    if (amount == 2) Dice("two") else Dice("one")
  override def toString() = w1.toString + " und " + w2.toString
}
class OneDice(w1: Int, w2: Int) extends DiceInterface {
  def this() = this(new Random().nextInt(6) + 1, 0)
  override def getSum(): Int = w1
  def wuerfeln(amount: Int): DiceInterface =
    if (amount == 2) Dice("two") else Dice("one")
  override def toString() = w1.toString
}

object Dice {
  def apply(kind: String) = kind match {
    case "1" | "one" => new OneDice()
    case "2" | "two" => new TwoDice()
  }
}
