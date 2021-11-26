package model
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class PlayersSpec extends AnyWordSpec {

  "A players(list)" should {
    "be created using the number of players as a parameter" in {
      val pl = new Players(3)
      pl.getScore(1) should be(0)
      pl.getScore(3) should be(0)
      pl.toString() should be("not supported yet")
    }
    "increase a players score number by using player number and amount to add as parameters" in {
      val pl = new Players(3)
      pl.addScore(1, 10).getScore(1) should be(10)
    }
    "leave other players score the same when adding points to just on player" in {
      val pl = new Players(3)
      pl.addScore(1, 10).addScore(2, 5).getScore(1) should be(10)

    }
  }
}
