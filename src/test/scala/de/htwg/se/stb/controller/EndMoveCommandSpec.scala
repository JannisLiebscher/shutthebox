package de.htwg.se.stb.controller
import de.htwg.se.stb.model.Game
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.util.control.Breaks._
class EndMoveCommandSpec extends AnyWordSpec {
  "A EndMoveCommand" should {
    "create two default games to save the state before and after the doStep" in {
      val command = new EndMoveCommand
      command.memento.isShut(1) should be(false)
      command.memento.isShut(1) should be(false)
    }
    "do a Step" in {
      val command = new EndMoveCommand
      command.doStep(Game("mock")).get.getPlayers should include("Player 2's turn")
    }
    "undo a doStep" in {
      val command = new EndMoveCommand
      val game = Game("mock")
      command.undoStep(command.doStep(game).get).get should be(game)
    }
    "redo an undoStep" in {
      val command = new EndMoveCommand    
      val step = command.doStep(Game("mock")).get
      command.redoStep(command.undoStep(step).get).get should be(step)
    }

  }

}