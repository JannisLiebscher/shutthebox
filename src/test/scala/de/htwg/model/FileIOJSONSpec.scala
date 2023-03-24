package de.htwg.model.fileio
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.util.control.Breaks._
import model.Game
import model.fileioComponent.FileIOJSON
import model.*
class FileIOJSONSpec extends AnyWordSpec {

  "A FileIOJSON" should {
    var file = new FileIOJSON
    var game = new Game(
      new Board().shut(1).shut(2).shut(9),
      Dice("two"),
      new Players(2).addScore(31).addScore(42),
      3
    )
    "save a test game in \"game.json\"" in {
      file.save(game)
    }
    "convert a game's board into a sequence" in {
      var seq = file.boardToJson(game)
      seq(0) should be(true)
      seq(1) should be(true)
      seq(2) should be(false)
      seq(8) should be(true)
      seq.size should be(9)
    }
    "convert a game's players into a JSON onject" in {
      var json = file.playersToJson(game)
      (json \ "score1").get.toString.toInt should be(31)
      (json \ "score2").get.toString.toInt should be(42)
      (json \ "turn").get.toString.toInt should be(1)

    }
    "load a testgame from \"game.json\"" in {
      var game = file.load
      game.getScore(1) should be(31)
      game.getScore(2) should be(42)
      game.getSum should be(3)
      game.getTurn should be(1)
      game.isShut(1) should be(true)
      game.isShut(2) should be(true)
      game.isShut(3) should be(false)
      game.isShut(9) should be(true)
    }
  }
}
