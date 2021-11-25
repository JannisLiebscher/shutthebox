package controller
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import model.Board
import model.Dice
class ControllerSpec extends AnyWordSpec {

  "A Controller" should {
    "be created using a Board and a Dice as parameters" in {
      val con = new Controller(new Board(4), new Dice())
      con.toString() should be("Wilkommen zu ShutTheBox!")
    }
    "should be able to get String representation of a rolled dice" in {
      val con = new Controller(new Board(4), new Dice())
      //con.wuerfeln() should fullyMatch regex """[0-6]\sund\s0"""

    }
    "should be able to shut a stone on its board" in {
      val con = new Controller(new Board(4), new Dice())
    }
  }
}
