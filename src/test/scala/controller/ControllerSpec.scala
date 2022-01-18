package controller
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import model.Game
class ControllerSpec extends AnyWordSpec {

  "A Controller" should {
    "be created" in {
      val con = new Controller(new Game())
      val conObject = new Controller(Game())
      val conObjectMock = new Controller(Game("Mock"))
    }
    "roll a dice" in {
      val con = new Controller(Game()).wuerfeln
      con.getSum should be <= 12
      con.getSum should be > 2
    }
    "be return String representation of a rolled dice" in {
      val con = new Controller(Game("mock")).wuerfeln
      con.getDice should fullyMatch regex "1 und 1"
    }
    "return the sum of the rolled dice" in {
      val con = new Controller(Game("mock")).wuerfeln
      con.getSum should be(2)
    }
    "shut a stone on its board" in {
      val con = new Controller(new Game()).wuerfeln.shut(1)
      con.isShut(1) should be(true)
    }
    "return wether a stone is shut or not" in {
      val con = new Controller(Game("mock")).wuerfeln.shut(2)
      con.isShut(1) should be(false)
      con.isShut(2) should be(true)
    }
    "return a string representation of its Board" in {
      val con = new Controller(Game("mock")).wuerfeln.shut(2)
      con.getBoard should include("| 1 | # | 3 | 4 | 5 | 6 | 7 | 8 | 9 |")
      con.getBoard should include("| # | 2 | # | # | # | # | # | # | # |")
    }
    "undo the last step" in {
      val con = new Controller(Game("mock"))
      con.wuerfeln.shut(2).isShut(2) should be(true)
      con.undo.isShut(2) should be(false)
    }
  }
}
