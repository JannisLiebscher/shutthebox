package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.util.control.Breaks._
class BoardSpec extends AnyWordSpec {

  "A Board" should {
    "be created using the x dimension as an input" in {
      val bo = new Board(4)
      bo.count() should be(10)
    }
    "allow to shut down single stones" in {
      val bo = new Board(4)
      bo.shut(4).count() should be(6)
      bo.shut(1).shut(2).count() should be(7)
      bo.shut(1).shut(2).shut(3).shut(4).count() should be(0)
    }
    "cause an AssertionError when shutting down a single stone more than once" in {
      val bo = new Board(4)
      the[AssertionError] thrownBy bo
        .shut(4)
        .shut(4) should have message "assertion failed: shutting down twice"
    }
    "have a String representation depending on the shutted stones" in {
      val bo = new Board(4)
      bo.toString() should be(
        "| 1 | 2 | 3 | 4 |" + sys.props("line.separator") +
          "| # | # | # | # | "
      )
      bo.shut(1).shut(2).shut(3).toString() should be(
        "| 1 | 2 | 3 | 4 |" + sys.props("line.separator") +
          "| # | # | # | 4 | "
      )
    }

  }
}
