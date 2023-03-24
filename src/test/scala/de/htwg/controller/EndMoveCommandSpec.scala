package de.htwg.controller
import model.Game
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.util.control.Breaks._
class EndMoveCommandSpec extends AnyWordSpec {
  "A EndMoveCommand" should {
    "create two default games to save the state before and after the doStep" in {
      val command = new EndMoveCommand
      command.afterDo.isShut(1) should be(false)
      command.beforeDo.isShut(1) should be(false)
    }
    "do a Step" in {
      val command = new EndMoveCommand
      command.doStep(Game("mock")).getPlayers should include("Player 2's turn")
    }
    "undo a doStep" in {
      val command = new EndMoveCommand
      val game = Game("mock")
      command.undoStep(command.doStep(game)) should be(game)
    }
    "redo an undoStep" in {
      val command = new EndMoveCommand
      val game = Game("mock")
      val step = command.doStep(game)
      command.redoStep(command.undoStep(step)) should be(step)
    }

  }

}
