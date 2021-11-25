package model

final case class Players private (private val players: Vector[(String, Int)]) {
  require(players.size >= 1)
  def this(count: Int) = this(Vector.tabulate(count)(n => ("Player" + n, 0)))
  def addScore(player: Int, amount: Int): Players = {
    new Players(
      players.updated(
        player - 1,
        (players(player - 1)._1, players(player - 1)._2 + amount)
      )
    )
  }
  def getScore(player: Int): Int = players(player - 1)._2

}
