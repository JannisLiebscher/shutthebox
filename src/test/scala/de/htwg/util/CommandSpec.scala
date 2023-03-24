package de.htwg.util

import model.*
import controller.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.util.control.Breaks._
class CommandSpec extends AnyWordSpec {
  "A Command" should {
    val controller = Controller()
    "have a do, undo and redo" should {
      controller.doAndPublish(controller.wuerfeln)
      controller.doAndPublish(controller.shut(1))
      controller.isShut(1) should be(true)
      controller.doAndPublish(controller.undo)
      controller.isShut(1) should be(false)
      controller.doAndPublish(controller.redo)
      controller.isShut(1) should be(true)
    }
  }
}
