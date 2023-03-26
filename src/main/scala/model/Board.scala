package model

case class Board private (matrix: Matrix[Int]) extends BoardInterface {

  def this(size: Int = 9) =
    this(Board.initialize(new Matrix[Int](size, 0), size))

  def shut(num: Int) =
    new Board(matrix.replace(num - 1, 0, 0).replace(num - 1, 1, num))
  def resShut(num: Int) =
    new Board(matrix.replace(num - 1, 0, num).replace(num - 1, 1, 0))
  def isShut(num: Int): Boolean = matrix.cell(num - 1, 0) == 0
  def count(): Int = matrix.row(0).sum

  override def toString() =
    val topLine = "| " + (1 to matrix.size)
      .map(n => numToString(matrix.cell(n - 1, 0)) + " | ").mkString("")
    val bottomLine = "| " + (1 to matrix.size)
      .map(n => numToString(matrix.cell(n - 1, 1)) + " | ").mkString("")
    topLine + sys.props("line.separator") + bottomLine
  private def numToString(x: Int) = if(x != 0) x.toString() else "#"
}
object Board {
  private def initialize(matrix: Matrix[Int], i: Int): Matrix[Int] = i match {
    case 0 => matrix
    case _ => initialize(matrix.replace(i - 1, 0, i), i - 1)
  }
}
