package model

case class Players(
    count: Int,
    players: Vector[(String, Int)],
    turn: Int
) extends PlayerInterface {
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
  def getTurn = turn
  override def toString = {
    count match
      case 1       => solo
      case default => multi
  }

  private def solo: String =
    if (getScore(count) > 45) "Game Over!"
    else "-----| " + players(0)._1 + ": " + players(0)._2.toString + " |-----"
  private def multi: String =
    var out = ""
    if (turn == 1)
      var min = ("empty", 100)
      var max = ("empty", 0)
      for (player <- players) {
        if (min._2 > player._2) min = (player._1, player._2)
        if (max._2 < player._2) max = (player._1, player._2)
      }
      if (max._2 > 45) return min._1 + " wins!"
    for (player <- players) {
      out = out + player._1 +
        ": " + player._2.toString + " | "
    }
    return out + "Player " + turn + "'s turn"
}
