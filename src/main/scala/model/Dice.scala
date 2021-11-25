package model
import scala.util.Random

case class Dice private (w1: Int, w2: Int) {
  private def this(num: Int) =
    this(new Random().nextInt(7), if (num == 2) new Random().nextInt(7) else 0)
  def this() = this(0, 0)
  def getSum(): Int = w1 + w2
  def wuerfeln(amount: Int): Dice = new Dice(amount)
  override def toString() = w1 + " und " + w2
}
