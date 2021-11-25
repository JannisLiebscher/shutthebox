package aview
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import model.*
import controller.*
class TuiSpec extends AnyWordSpec {

  "A shutthebox Tui" should {
    val controller = new Controller(new Board(4), new Dice)
    val tui = new Tui(controller)
    "be subscriber to its controller" in {
      controller.subscribers should contain(tui)
    }
    "roll the dice on input 'w'" in {
      tui.getInputAndPrintLoop("w")
      controller.w.toString should fullyMatch regex """[0-6]\sund\s[0-6]"""
    }
    "shut down a stone by inputting its number" in {
      controller.board.count() should be(10)
      tui.getInputAndPrintLoop("4")
      controller.board.count() should be(6)
    }
    "do nothing on input 'q'" in {
      tui.getInputAndPrintLoop("q")
    }
    "should notify you of unknown inputs" in {
      tui.getInputAndPrintLoop("")
    }
  }
}
