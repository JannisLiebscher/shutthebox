package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.util.control.Breaks._
class GameSpec extends AnyWordSpec {
  "A Game" should {
    "be created without any parameters for a standard 2 player game" in {
      var game = new Game()
      game.count() should be(45)
      game.sum should be(0)
    }
    "not allow you to roll the dice again if the sum is not 0" in {
      var game = new Game(new Board(), Dice("two"), new Players(2), 1)
      game.wuerfeln(1) should be(game)
    }
    "roll the dice if sum is not 0" in {
      var game = new Game()
      game.wuerfeln(1) should not be (game)
    }
    "shut a stone" in {
      var game = new Game().wuerfeln(1)
      game.shut(1).count() should be(44)
    }
    "add an error to its string representation if shutting a stone twice" in {
      var game = new Game().wuerfeln(1)
      game = game.shut(1)
      game.shut(1).toString should include("cant shut")
    }
    "add an error to its string representation if stone number exceeds the sum" in {
      var game = new Game()
      game = game.wuerfeln(1)
      game.shut(20).toString should include("cant shut")
    }
    "reset a shut by calling resShut with the same stone number" in {
      var game = new Game()
      game = game.wuerfeln(1)
      game.shut(1).resShut(1) should be(game)
    }
    "start next players round" in {
      var game = new Game().wuerfeln(2)
      game.shut(1).count() should be(44)
      game.endMove().count() should be(45)
    }
  }
}
