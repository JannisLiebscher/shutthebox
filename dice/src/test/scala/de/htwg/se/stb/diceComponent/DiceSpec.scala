package de.htwg.se.stb.diceComponent

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.util.control.Breaks._
class DiceSpec extends AnyWordSpec {

  
  "A Dice" should {
    "be created using the amount of dices to roll" in {
      val wone = Dice("one")
      val wtwo = Dice("two")
      val w1 = Dice("1")
      val w2 = Dice("2")
      wone.toString should fullyMatch regex """[1-6]"""
      wone.getSum() should be <= 6
      w1.toString should fullyMatch regex """[1-6]"""
      w1.getSum() should be <= 6
      wtwo.toString should fullyMatch regex """[1-6]\sund\s[1-6]"""
      wtwo.getSum() should be <= 12
      w2.toString should fullyMatch regex """[1-6]\sund\s[1-6]"""
      w2.getSum() should be <= 12
    }
    "not return more than 6 when only one dice is used" in {
      val wu = Dice("one")
      for (n <- (0 to 100)) {
        wu.wuerfeln(1).getSum() should be <= 6
        wu.wuerfeln(1).toString should fullyMatch regex """[1-6]"""
      }
    }
    "return between 2 and 12 when two dices are used" in {
      val wu = Dice("one")
      for (n <- (0 to 100)) {
        wu.wuerfeln(2).getSum() should be <= 12
        wu.wuerfeln(2).getSum() should be >= 2
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
    "always roll two 'ones' when using the mock implementation" in {
      val wu = Dice("mock")
      for (n <- (0 to 100)) {
        wu.wuerfeln(1).getSum() should be(2)
        wu.wuerfeln(1).toString should fullyMatch regex "1 und 1"
      }
    }
  }
}
