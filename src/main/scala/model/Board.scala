package model
import model.Matrix
case class Board private (var m: Matrix[Int]) {

  def this(size: Int = 9) =
    this(new Matrix[Int](size, 0))
    for (n <- (0 to m.size - 1)) m = m.replace(n, 0, n + 1)

  def shut(num: Int): Board =
    return new Board(m.replace(num - 1, 0, 0).replace(num - 1, 1, num))
  def resShut(num: Int): Board =
    return new Board(m.replace(num - 1, 0, num).replace(num - 1, 1, 0))
  def count(): Int =
    var sum = 0
    for (n <- (0 to m.size - 1)) sum += m.cell(n, 0)
    return sum
  def isShut(num: Int): Boolean = m.cell(num - 1, 0) == 0
  override def toString(): String =
    var out = "| "
    for (n <- (1 to m.size))
      if (m.cell(n - 1, 0) != 0)
        out = out + n + " | "
      else
        out = out + "# | "
    out = out + sys.props("line.separator") + "| "
    for (n <- (1 to m.size)) {
      if (m.cell(n - 1, 1) != 0) out = out + n + " | "
      else
        out = out + "# | "
    }
    return out
}
