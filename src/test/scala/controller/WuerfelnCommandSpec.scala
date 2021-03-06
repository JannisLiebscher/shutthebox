package controller
import model.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.util.control.Breaks._
import model.Board
class WuerfelnCommandSpec extends AnyWordSpec {
  "A WuerfelnCommand" should {
    val game = new Game(new Board(9), Dice("two"), new Players(2), 0)
    "do a Step" in {
      val command = new WuerfelnCommand(2)
      command.doStep(game).getSum should be >= 2
    }
    "undo a doStep" in {
      val command = new WuerfelnCommand(2)
      command.undoStep(command.doStep(game)).getSum should be(0)
    }
    "redo an undoStep" in {
      val command = new WuerfelnCommand(2)
      val step = command.doStep(game)
      command.redoStep(command.undoStep(game)) should be(step)
    }
  }

}
