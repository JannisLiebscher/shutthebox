package model

case class Board private (matrix: Matrix[Int]) extends BoardInterface {

  def this(size: Int = 9) = this(Board.initialize(new Matrix[Int](size, 0), size))

  def shut(num: Int): Board =
    new Board(matrix.replace(num - 1, 0, 0).replace(num - 1, 1, num))
  def resShut(num: Int): Board =
    new Board(matrix.replace(num - 1, 0, num).replace(num - 1, 1, 0))
  def isShut(num: Int): Boolean = matrix.cell(num - 1, 0) == 0

  def count(): Int = matrix.row(0).sum

  override def toString(): String =
    var out = "| "
    for (n <- (1 to matrix.size))
      if (matrix.cell(n - 1, 0) != 0) out = out + n + " | "
      else out = out + "# | "
    out = out + sys.props("line.separator") + "| "
    for (n <- (1 to matrix.size)) {
      if (matrix.cell(n - 1, 1) != 0) out = out + n + " | "
      else out = out + "# | "
    }
    return out
}
object Board {
  private def initialize(matrix: Matrix[Int], i: Int): Matrix[Int] = i match {
    case 0 => matrix
    case _ => initialize(matrix.replace(i - 1, 0, i), i - 1)
  }
}
