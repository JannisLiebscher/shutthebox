package de.htwg.se.stb.model.fileio
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.util.control.Breaks._
import de.htwg.se.stb.model.Game
import de.htwg.se.stb.model.fileioComponent.FileIOXML
import de.htwg.se.stb.model.*
class FileIOXMLSpec extends AnyWordSpec {

  "A FileIOXML" should {
    var file = new FileIOXML
    var game = new Game(
      new Board().shut(1).shut(2).shut(9),
      Dice("two"),
      new Players(2).addScore(31).addScore(42),
      3
    )
    "save a test game in \"game.xml\"" in {
      file.save(game)
    }
    "convert a singel box of  a game's board into a xml Elem" in {
      file.boxToXML(game, 1).text.trim.toBoolean should be(true)
      file.boxToXML(game, 2).text.trim.toBoolean should be(true)
      file.boxToXML(game, 3).text.trim.toBoolean should be(false)
      file.boxToXML(game, 9).text.trim.toBoolean should be(true)
    }
    "convert a game into a xml Elem" in {
      var xml = file.gameToXML(game)
      (xml \\ "game" \ "players" \ "score1").text.trim.toInt should be(31)
      (xml \\ "game" \ "players" \ "score2").text.trim.toInt should be(42)
      (xml \\ "game" \ "players" \ "turn").text.trim.toInt should be(1)
      (xml \\ "game" \ "sum").text.trim.toInt should be(3)
    }
    "load a testgame from \"game.xml\"" in {
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
