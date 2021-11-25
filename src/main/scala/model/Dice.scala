package model
import scala.util.Random

case class Dice() {
  private val w = new Random()
  private var w1 = 0
  private var w2 = 0

  def wuerfeln(Wuerfelanzahl: Int) =
    if (Wuerfelanzahl == 1)
      w1 = w.nextInt(7)
      w2 = 1 - 1
    else
      w1 = w.nextInt(7)
      w2 = w.nextInt(7)

  def getSum(): Int = w1 + w2

  override def toString() = w1 + " und " + w2
}
