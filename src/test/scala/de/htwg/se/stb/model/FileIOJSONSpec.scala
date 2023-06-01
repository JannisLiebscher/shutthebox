package de.htwg.se.stb.model.fileio
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.util.control.Breaks._
import de.htwg.se.stb.model.Game
import de.htwg.se.stb.model.fileioComponent.FileIOJSON
import de.htwg.se.stb.model.*
import de.htwg.se.stb.boardComponent.Board
import de.htwg.se.stb.diceComponent.Dice
import de.htwg.se.stb.playerComponent.Players
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
