package aview

import swing._
import controller.ControllerInterface
import util.Observer
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class guiStoneButtonSpec extends AnyWordSpec {
  "A guiStoneButton" when {
    "beeing a top button" should {
      "be created using the Number it represents" in {
        var b = new guiButtonTop(1)
        b.text should be("1")
      }
      "have its number as text" in {
        var b = new guiButtonTop(1)
        b.text should be("1")
        b.toString should be("1")
      }
      "change its text when the stone it represents is shut" in {
        var b = new guiButtonTop(1)
        b.text should be("1")
        b.shut
        b.text should be("#")
      }
      "change its text back to its represented number when beeing \"unshut\"" in {
        var b = new guiButtonTop(1)
        b.shut
        b.text should be("#")
        b.notShut
        b.text should be("1")
      }
    }
    "beeing a base button" should {
      "be created using the Number it represents" in {
        var b = new guiButtonBase(1)
        b.text should be("#")
      }
      "have \"#\" as text on default" in {
        var b = new guiButtonBase(1)
        b.text should be("#")
        b.toString should be("#")
      }
      "change its text to its number when the stone it represents is shut" in {
        var b = new guiButtonBase(1)
        b.text should be("#")
        b.shut
        b.text should be("1")
      }
      "change its text back to \"#\" when beeing \"unshut\"" in {
        var b = new guiButtonBase(1)
        b.shut
        b.text should be("1")
        b.notShut
        b.text should be("#")
      }
    }
  }

}
