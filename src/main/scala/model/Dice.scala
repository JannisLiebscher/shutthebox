package model
import scala.util.Random

trait Dice(w1: Int, w2: Int) {
  def getSum(): Int
  def wuerfeln(amount: Int): Dice
}

case class TwoDice(w1: Int, w2: Int) extends Dice(w1: Int, w2: Int) {
  def this() = this(new Random().nextInt(6) + 1, new Random().nextInt(6) + 1)
  override def getSum(): Int = w1 + w2
  def wuerfeln(amount: Int): Dice =
    if (amount == 2) Dice("two") else Dice("one")
  override def toString() = "Gewuerfelt: " + w1 + " und " + w2
}

class OneDice(w1: Int, w2: Int) extends Dice(w1: Int, w2: Int) {
  def this() = this(new Random().nextInt(6) + 1, 0)
  override def getSum(): Int = w1
  def wuerfeln(amount: Int): Dice =
    if (amount == 2) Dice("two") else Dice("one")
  override def toString() = "Gewuerfelt: " + w1
}

object Dice {
  def apply(kind: String) = kind match {
    case "1" | "one" => new OneDice()
    case "2" | "two" => new TwoDice()
  }
}
