package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.util.control.Breaks._
class DiceSpec extends AnyWordSpec {

  "A Dice" should {
    val wu = new Dice()
    "be created without any parameters" in {
      wu.toString should be("0 und 0")
      wu.getSum() should be(0)
    }
    "be rolled using 1 or 2 as the number of dices to use" in {
      for (n <- (0 to 100)) {
        wu.wuerfeln(1).getSum() should be <= 6
        wu.wuerfeln(1).toString should fullyMatch regex """[0-6]\sund\s0"""
      }
      for (n <- (0 to 100)) {
        wu.wuerfeln(2).getSum() should be <= 12
        wu.wuerfeln(2).toString should fullyMatch regex """[0-6]\sund\s[0-6]"""
      }
    }
    "set the second dice to 0 if it is rolled using just one dice" in {
      breakable {
        while (true) {
          wu.wuerfeln(2)
          if (wu.wuerfeln(2).getSum() > 6) break
        }
      }
      wu.wuerfeln(1)
      wu.toString should fullyMatch regex """[0-6]\sund\s0"""
    }
  }
}
