package model
import scala.util.Random

case class Wuerfel() {
  private val w = new Random()
  private var Summe = 0

  def wuerfeln(Wuerfelanzahl: Int): (Int, Int) =
    if (Wuerfelanzahl == 1)
      val tmp = (w.nextInt(7), 0)
      Summe = tmp._1 + tmp._2
      return tmp
    else
      val tmp = (w.nextInt(7), w.nextInt(7))
      Summe = tmp._1 + tmp._2
      return tmp
  def getSum(): Int = Summe
}
