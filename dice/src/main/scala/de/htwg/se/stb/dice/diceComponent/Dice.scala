package de.htwg.se.stb.diceComponent
import scala.util.Random
import play.api.libs.json._

case class TwoDice(w1: Int, w2: Int) extends DiceInterface {
  def this() = this(new Random().nextInt(6) + 1, new Random().nextInt(6) + 1)
  def getSum(): Int = w1 + w2
  def wuerfeln(amount: Int): DiceInterface = Dice.wuerfeln(amount)
  override def toString() = w1.toString() + " und " + w2.toString
  
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
  def toJson(dice: DiceInterface) = 
    Json.obj(
      "w1" -> JsNumber(dice.toString().head.asDigit),
      "w2" -> JsNumber(dice.toString().last.asDigit)
    )
  def fromJson(json: JsValue) = {
    val w1 =  (json \ "w1").get.toString.toInt
    val w2 = (json \ "w2").get.toString.toInt
    if(w2 != 0) new TwoDice(w1, w2)
    else new OneDice(w1, w2)
  }
}
