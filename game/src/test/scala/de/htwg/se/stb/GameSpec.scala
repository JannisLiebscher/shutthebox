package de.htwg.se.stb.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.util.control.Breaks._
import de.htwg.se.stb.boardComponent.Board
import de.htwg.se.stb.diceComponent.Dice
import scala.util.Failure
class GameSpec extends AnyWordSpec {
  "A Game" should {
    "be created without any parameters for a standard 2 player game" in {
      var game = new Game()
      game.count() should be(45)
      game.sum should be(0)
      var gameObj = Game()
      gameObj.count() should be(45)
      gameObj.sum should be(0)
    }
    "be created using the Mock version to get a game that gives you a 2 at every dice roll" in {
      var game = Game("Mock").wuerfeln(2).get
      game.count() should be(45)
      game.sum should be(2)
    }
    "not allow you to roll the dice again if the sum is not 0" in {
      var game = new Game(new Board(), Dice("two"), new Players(2), 1)
      game.wuerfeln(1).isFailure should be(true)
    }
    "roll the dice if sum is not 0" in {
      var game = new Game()
      game.wuerfeln(1) should not be (game)
    }
    "shut a stone" in {
      var game = new Game().wuerfeln(1).get
      game.shut(1).get.count() should be(44)
    }
    "return an exception if shutting a stone twice" in {
      val game = new Game().wuerfeln(1).get
      game.shut(1).get.shut(1).isFailure should be(true)
      
    }
    "return an exception if stone number exceeds the sum" in {
      var game = new Game()
      game = game.wuerfeln(1).get
      game.shut(9).isFailure should be(true)
    }
    "reset a shut by calling resShut with the same stone number" in {
      var game = new Game()
      game = game.wuerfeln(1).get
      game.shut(1).get.resShut(1).get should be(game)
    }
    "start next players round" in {
      var game = new Game().wuerfeln(2).get
      game.shut(1).get.count() should be(44)
      game.endMove.get.count() should be(45)
    }
    "return essential values" in {
      var game = new Game().wuerfeln(2).get.shut(1).get
      game.getSum should be >= 1
      game.getBoard should include("| # | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |")
      game.isShut(1) should be(true)
      game.getScore(1) should be(0)
      game.getPlayers should include("Player 1's turn")
    }
  }
}
