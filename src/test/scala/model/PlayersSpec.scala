package model
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class PlayersSpec extends AnyWordSpec {

  "A players(list)" should {
    "be created using the number of players as a parameter" in {
      val pl = new Players(3)
      pl.getScore(1) should be(0)
      pl.getScore(3) should be(0)
      pl.toString() should include("Player 1: 0 | Player 2: 0 | Player 3: 0 | ")
    }
    "increase the active players score by using the amount to add as a parameter" in {
      val pl = new Players(3)
      pl.addScore(10).getScore(1) should be(10)
    }
    "leave other players score the same when adding points to just on player" in {
      val pl = new Players(3)
      pl.addScore(10).addScore(5).getScore(1) should be(10)
      pl.addScore(10).addScore(5).getScore(2) should be(5)
    }
    "alter its string representation depending on wether there are one or more players" in {
      val pl = new Players(1)
      pl.toString should include("-----| Player 1: 0 |-----")
      new Players(2).toString should include("Player 1: 0 | Player 2: 0 | ")
    }
  }
}
