package de.htwg.se.stb.controller
import de.htwg.se.stb.model.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.util.control.Breaks._
import de.htwg.se.stb.boardComponent.Board
import de.htwg.se.stb.diceComponent.Dice
class ShutCommandSpec extends AnyWordSpec {
  "A ShutCommand" should {
    val game = new Game(new Board(9), Dice("two"), new Players(2), 4)
    "do a Step" in {
      val command = new ShutCommand(2)
      command.doStep(game).get.isShut(2) should be(true)
    }
    "undo a doStep" in {
      val command = new ShutCommand(2)
      command.undoStep(command.doStep(game).get).get.isShut(2) should be(false)
    }
    "redo an undoStep" in {
      val command = new ShutCommand(2)
      val step = command.doStep(game).get
      step.isShut(2) should be(true)
      command.redoStep(command.undoStep(step).get).get.isShut(2) should be(true)
    }
  }

}