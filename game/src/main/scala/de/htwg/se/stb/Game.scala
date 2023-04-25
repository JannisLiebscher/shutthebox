package de.htwg.se.stb.model
import scala.util.{Try, Success, Failure}
import fileioComponent.FileIOXML
import de.htwg.se.stb.model.fileioComponent.FileIOJSON
import de.htwg.se.stb.boardComponent.*
import de.htwg.se.stb.diceComponent.*
import de.htwg.se.stb.playerComponent.*
val eol = sys.props("line.separator")

case class Game(
    board: BoardInterface,
    w: DiceInterface,
    players: PlayerInterface,
    sum: Int
) extends GameInterface {
  def this() = this(new Board(), Dice("two"), new Players(2), 0)
  def count(): Int = board.count()
  def getDice: String = w.toString
  def getSum: Int = sum
  def getPlayers: String = players.toString
  def getWinner: Option[String] = players.getWinner
  def getTurn: Int = players.getTurn
  def getBoard: String = board.toString
  def isShut(stone: Int): Boolean = board.isShut(stone)
  def getScore(player: Int): Int = players.getScore(player)
  def wuerfeln(num: Int): Try[Game] =
    if (sum == 0)
      val tmp = w.wuerfeln(num)
      Success(new Game(board, tmp, players, tmp.getSum()))
    else
      Failure(new Exception("complete turn before rolling dice again"))

  def shut(stone: Int): Try[Game] =
    if(board.isShut(stone)) Failure(new Exception("already shut"))
    else if (board.count() < sum) 
    Failure(new Exception("Impossible to use whole Sum, let the next player take their turn "))
    else if(stone > sum) Failure(new Exception("Cant shut box bigger than whole sum"))
    else Success(new Game(board.shut(stone), w, players, sum - stone))
  

  def resShut(stone: Int): Try[Game] =
    if (board.isShut(stone))
      Success(new Game(board.resShut(stone), w, players, sum + stone))
    else Failure(new Exception("already shut"))

  def endMove: Try[Game] =
    if(w.getSum() != sum && sum != 0) Failure(new Exception("shut all boxes"))
    else Success(new Game(new Board(9), Dice("two"), players.addScore(board.count()), 0))

  override def toString(): String =
    players.toString + eol +
      board.toString + eol +
      "Gewuerfelt " + w.toString + " | Summe: " + sum
}

object Game:
  def apply(): Game = new Game(new Board(), Dice("two"), new Players(2), 0)
  def apply(kind: String) = kind match {
    case "mock" | "Mock" =>
      new Game(new Board(), Dice("mock"), new Players(2), 0)
  }
