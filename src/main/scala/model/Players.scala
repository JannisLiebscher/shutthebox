package model

case class Players private (private players: Vector[(String, Int)]) {
  require(players.size >= 1)
  val count = 0
  def this(count: Int) =
    this(Vector.tabulate(count)(n => ("Player " + (n + 1), 0)))

  def addScore(player: Int, amount: Int): Players = {
    new Players(
      players.updated(
        player - 1,
        (players(player - 1)._1, players(player - 1)._2 + amount)
      )
    )
  }
  def getScore(player: Int): Int = players(player - 1)._2

  override def toString = {
    count match {
      case 1       => solo
      case default => multi
    }
  }
  private def solo: String =
    "-----| " + players(0)._1 + ": " + players(0)._2.toString + " |-----"
  private def multi: String =
    var out = ""
    for (player <- players) {
      out = out + player._1 +
        ": " + player._2.toString + " | "
    }
    return out
}
