package model

val eol = sys.props("line.separator")

case class Game(var board: Board, var w: Dice, players: Players) {
  var error = ""
  def this() = this(new Board(9), Dice("two"), new Players(2))
  def count(): Int = board.count()
  def wuerfeln(num: Int): Game = new Game(board, w.wuerfeln(num), players)
  def shut(stone: Int): Game = new Game(board.shut(stone), w, players)

  override def toString(): String =
    players.toString + eol +
      board.toString + eol +
      w.toString + " Summe: " + w.getSum() +
      error
}
