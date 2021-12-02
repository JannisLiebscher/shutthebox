package model

val eol = sys.props("line.separator")

case class Game(var board: Board, var w: Dice, players: Players, sum: Int) {
  var error = ""
  def this() = this(new Board(9), Dice("two"), new Players(2), 0)
  def this(player: Int) =
    this(new Board(9), Dice("two"), new Players(player), 0)
  def count(): Int = board.count()

  def wuerfeln(num: Int): Game =
    if (sum != 0) {
      error = "complete turn before rolling dice again"
      this
    } else
      val tmp = w.wuerfeln(num)
      return new Game(board, tmp, players, tmp.getSum())

  def shut(stone: Int): Game =
    if (stone <= sum || board.count() <= sum)
      new Game(board.shut(stone), w, players, sum - stone)
    else error = "cant shut"
    this

  def endMove(): Game =
    new Game(new Board(9), Dice("two"), players.addScore(board.count()), 0)
      .wuerfeln(2)

  override def toString(): String =
    players.toString + eol +
      board.toString + eol +
      w.toString + " | Summe: " + sum +
      error
}
