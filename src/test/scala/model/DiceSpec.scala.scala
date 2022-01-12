package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.util.control.Breaks._
class DiceSpec extends AnyWordSpec {

  "A Dice" should {
    "be created using the amount of dices to roll" in {
      val wu = Dice("one")
      wu.toString should fullyMatch regex """[1-6]"""
      wu.getSum() should be <= 12
    }
    "be rolled using 1 or 2 as the number of dices to use" in {
      val wu = Dice("one")
      for (n <- (0 to 100)) {
        wu.wuerfeln(1).getSum() should be <= 6
        wu.wuerfeln(1).toString should fullyMatch regex """[1-6]"""
      }
      for (n <- (0 to 100)) {
        wu.wuerfeln(2).getSum() should be <= 12
        wu.wuerfeln(2).toString should fullyMatch regex """[1-6]\sund\s[1-6]"""
      }
    }
    "print out only one dice when switching from 2 dices to 1" in {
      val wu = Dice("two")
      breakable {
        while (true) {
          wu.wuerfeln(2)
          if (wu.wuerfeln(2).getSum() > 6) break
        }
      }
      wu.wuerfeln(1).toString should fullyMatch regex """[1-6]"""
    }
  }
}
