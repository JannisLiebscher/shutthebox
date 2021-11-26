package controller
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import model.Game
class ControllerSpec extends AnyWordSpec {

  "A Controller" should {
    "be created using a Board and a Dice as parameters" in {
      val con = new Controller(new Game())

    }
    "should be able to get String representation of a rolled dice" in {
      val con = new Controller(new Game())
      //con.wuerfeln() should fullyMatch regex """[0-6]\sund\s0"""

    }
    "should be able to shut a stone on its board" in {
      val con = new Controller(new Game())
    }
  }
}
