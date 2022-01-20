package controller
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import model.Game
class ControllerSpec extends AnyWordSpec {

  "A Controller" should {
    "be created" in {
      val con = new Controller(new Game())
      con.getScore(1) should be(0)
      val conObject = new Controller(Game())
      conObject.getScore(1) should be(0)
    }
    "always roll a \"2\" when beeing created as a \"mock\" implementation" in {
      val conObjectMock = new Controller(Game("Mock"))
      conObjectMock.wuerfeln.getSum should be(2)
    }
    "roll a dice" in {
      val con = new Controller(Game()).wuerfeln
      con.getSum should be <= 12
      con.getSum should be >= 2
    }
    "return a String representation of a rolled dice" in {
      var con = new Controller(Game("mock")).wuerfeln
      con.getDice should fullyMatch regex "1 und 1"
      con = new Controller(Game()).wuerfeln
      con.getDice should fullyMatch regex """[1-6]\sund\s[1-6]"""
    }
    "return the sum of the rolled dice" in {
      val con = new Controller(Game("mock")).wuerfeln
      con.getSum should be(2)
    }
    "decrease the sum if a stone is shut" in {
      val con = new Controller(Game("mock")).wuerfeln
      con.getSum should be(2)
      con.shut(2).getSum should be(0)
    }
    "shut a stone on its board" in {
      val con = new Controller(new Game()).wuerfeln.shut(1)
      con.isShut(1) should be(true)
    }
    "return wether or not a stone is shut" in {
      val con = new Controller(Game("mock")).wuerfeln.shut(2)
      con.isShut(1) should be(false)
      con.isShut(2) should be(true)
    }
    "return a string representation of its Board" in {
      val con = new Controller(Game("mock")).wuerfeln.shut(2)
      con.getBoard should include("| 1 | # | 3 | 4 | 5 | 6 | 7 | 8 | 9 |")
      con.getBoard should include("| # | 2 | # | # | # | # | # | # | # |")
    }
    "return a players score" in {
      val con = new Controller(Game("mock"))
      con.getScore(1) should be(0)
    }
    "return all players with their score and whos turn it is" in {
      val con = new Controller(Game("mock")).wuerfeln.shut(2).endMove
      con.getPlayers should include(
        "Player 1: 43 | Player 2: 0 | Player 2's turn"
      )

    }
    "increase a players score by the sum of all stones that are not shut" in {
      val con = new Controller(Game("mock")).wuerfeln.shut(2).endMove
      con.getScore(1) should be(43)
    }
    "undo the last step" in {
      val con = new Controller(Game("mock"))
      con.wuerfeln.shut(2).isShut(2) should be(true)
      con.undo.isShut(2) should be(false)
    }
  }
}
