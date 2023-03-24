package aview
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import model.*
import controller.*
class TuiSpec extends AnyWordSpec {

  "A shutthebox Tui" should {
    val controller = Controller("json")
    val tui = new Tui(controller)
    "be subscriber to its controller" in {
      controller.subscribers should contain(tui)
    }
    "roll the dice on input 'w'" in {
      tui.getInputAndPrintLoop("w")
    }
    "shut down a stone by inputting its number" in {
      controller.game.count() should be(45)
      tui.getInputAndPrintLoop("1")
      controller.game.count() should be(44)
    }
    "should notify you of unknown inputs" in {
      tui.getInputAndPrintLoop("")
    }
  }
}
