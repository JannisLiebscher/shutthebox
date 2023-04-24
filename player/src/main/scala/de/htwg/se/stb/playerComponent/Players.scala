package de.htwg.se.stb.playerComponent
import play.api.libs.json._
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

  def getWinner: Option[String] = 
    val playerMin = players.minBy(_._2)
    val playerMax = players.maxBy(_._2)
    if (playerMax._2 > 45 && turn == 1) Some(playerMin._1)
    else None

  private def solo: String =
    if (getScore(count) > 45) "Game Over!"
    else "-----| " + players(0)._1 + ": " + players(0)._2.toString + " |-----"

  private def multi: String = {
    val outPlayers = players
      .map(player => player._1 + ": " + player._2.toString)
      .mkString(" | ") + " | "
    val min = players.minBy(_._2)
    val max = players.maxBy(_._2)
    if (max._2 > 45 && turn == 1) min._1 + " wins!"
    else outPlayers + "Player " + turn + "'s turn"
  }

}
object Players {
  def toJson(players: PlayerInterface) = 
    Json.obj(
      "score1" -> JsNumber(players.getScore(1)),
      "score2" -> JsNumber(players.getScore(2)),
      "turn" -> JsNumber(players.getTurn)
    )
  def fromJson(json: JsValue) = 
    val playersSeq = Seq[Int](
      (json \ "score1").get.toString.toInt,
      (json \ "score2").get.toString.toInt,
      (json \ "turn").get.toString.toInt
    )
    new Players(2, Vector(("Player 1", playersSeq(0)),
     ("Player 2", playersSeq(1))), playersSeq(2))
}
