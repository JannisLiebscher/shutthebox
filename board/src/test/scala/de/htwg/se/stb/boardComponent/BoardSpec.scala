package de.htwg.se.stb.boardComponent

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.util.control.Breaks._
class BoardSpec extends AnyWordSpec {

  "A Board" should {
    "be created using the x dimension as an input" in {
      val bo = new Board(4)
      bo.count() should be(10)
      bo.matrix.size should be(4)
    }
    "allow to shut down single stones" in {
      val bo = new Board(4)
      bo.shut(4).count() should be(6)
      bo.shut(1).shut(2).count() should be(7)
      bo.shut(1).shut(2).shut(3).shut(4).count() should be(0)
    }
    "return the same Board when shutting down a already shutted stone" in {
      val bo = new Board(4)
      bo.shut(4) should be(bo.shut(4).shut(4))
    }
    "have a String representation depending on the shutted stones" in {
      val bo = new Board(4)
      bo.toString() should include("| 1 | 2 | 3 | 4 |")
      bo.toString() should include("| # | # | # | # |")
      bo.shut(1).shut(2).shut(3).toString() should include("| # | # | # | 4 |")
      bo.shut(1).shut(2).shut(3).toString() should include("| 1 | 2 | 3 | # |")
    }
  }
}
