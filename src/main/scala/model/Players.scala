package model

case class Players private (
    count: Int,
    players: Vector[(String, Int)],
    turn: Int
) {
  require(players.size >= 1)
  def this(count: Int) =
    this(count, Vector.tabulate(count)(n => ("Player " + (n + 1), 0)), 1)

  def addScore(amount: Int): Players = {
    new Players(
      count,
      players.updated(
        turn - 1,
        (players(turn - 1)._1, players(turn - 1)._2 + amount)
      ),
      if (turn == count) 1 else turn + 1
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
    return out + "Player " + turn + "'s turn"
}
